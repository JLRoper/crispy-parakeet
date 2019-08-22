/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import htmlunit.QuoteBean;
import htmlunit.QuoteHandler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jacob
 */
@WebServlet(name = "PracticeServlet", urlPatterns = {"/PracticeServlet"})
public class PracticeServlet extends HttpServlet {

    SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss");
    Map<String, QuoteBean> lastPollData = new HashMap<String, QuoteBean>();
    Map<String, Long> lastPollTime = new HashMap<String, Long>();

    long lastPoll = 1;
    long thisPoll = 1;
    QuoteBean lastQuote;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response+
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = blankNullTrim(request.getParameter("action"));
        switch (ServletAction.getAction(action)) {
            case GET_SINGLE_QUOTE:
                loadStockTicker(request, response);
                break;
            case ANOTHER_ACTION:
                anotherAction(request, response);
                break;
        }

    }

    public enum ServletAction {
        GET_SINGLE_QUOTE,
        ANOTHER_ACTION,
        TEST_ACTION_1,
        TEST_ACTION_2;

        public static ServletAction getAction(String actionString) {
            return ServletAction.valueOf(actionString.trim().toUpperCase());
        }
    }

    private void anotherAction(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        JSONObject jsonObject = null;
        JSONObject returnJson = new JSONObject();
        String symbol = request.getParameter("symbol") != null ? request.getParameter("symbol") : "";
        String jsonString = request.getParameter("lastName");
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException ex) {
        }

        String cleanSymbol = symbol.split(",")[0].trim().toUpperCase();
        lastPoll = lastPollTime.get(cleanSymbol) != null ? lastPollTime.get(cleanSymbol) : 0;
        lastQuote = lastPollData.get(cleanSymbol) != null ? lastPollData.get(cleanSymbol) : new QuoteBean("", "", 0.0, "0");

        try {
            if (System.currentTimeMillis() - lastPoll > 10000) {
                lastQuote = QuoteHandler.INSTANCE.retrieveRobinhoodQuote(cleanSymbol);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println("- Submitting web request to Robinhoodd: " + symbol);
                lastPoll = System.currentTimeMillis();
                lastPollTime.put(cleanSymbol, lastPoll);
                lastPollData.put(cleanSymbol, lastQuote);
                System.out.println("-   This Submit: " + sdf.format(new Date(lastPoll)));
            }
        } catch (Exception ex) {
            Logger.getLogger(PracticeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONArray quoteArray = new JSONArray();
        quoteArray.add(lastQuote.toString());
        returnJson.put("count", quoteArray.size());
        returnJson.put("quoteInfo", quoteArray);
        response.setContentType("application/JSON");
        response.getWriter().write(returnJson.toJSONString());
    }

    private void loadStockTicker(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        JSONObject jsonObject = null;
        JSONObject returnJson = new JSONObject();
        String jsonString = request.getParameter("payload");
        String symbol = ""; 
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
            symbol = (String) jsonObject.get("symbol");
        } catch (Throwable ex) {
        }

        String cleanSymbol = symbol.split(",")[0].trim().toUpperCase();
        lastPoll = lastPollTime.get(cleanSymbol) != null ? lastPollTime.get(cleanSymbol) : 0;
        lastQuote = lastPollData.get(cleanSymbol) != null ? lastPollData.get(cleanSymbol) : new QuoteBean("", "", 0.0, "0");

        try {
            if (System.currentTimeMillis() - lastPoll > 10000) {
                lastQuote = QuoteHandler.INSTANCE.retrieveRobinhoodQuote(cleanSymbol);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                System.out.println("- Submitting web request to Robinhoodd: " + symbol);
                lastPoll = System.currentTimeMillis();
                lastPollTime.put(cleanSymbol, lastPoll);
                lastPollData.put(cleanSymbol, lastQuote);
                System.out.println("-   This Submit: " + sdf.format(new Date(lastPoll)));
            }
        } catch (Exception ex) {
            Logger.getLogger(PracticeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONArray quoteArray = new JSONArray();
        quoteArray.add(lastQuote.toString());
        returnJson.put("count", quoteArray.size());
        returnJson.put("quoteInfo", quoteArray);
        response.setContentType("application/JSON");
        response.getWriter().write(returnJson.toJSONString());
    }

    private void forwardToPage(final HttpServletRequest request,
            final HttpServletResponse response,
            String url)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    private String blankNullTrim(String input) {
        return input != null ? input.trim() : "";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
