/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import htmlunit.QuoteHandler;
import htmlunit.QuoteList;
import java.io.IOException;
import java.util.Map;
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

        switch (action) {
            case "GetSingleQuote":
                break;
        }

        JSONObject jsonObject = null;
        JSONObject returnJson = new JSONObject();
        String symbol = request.getParameter("symbol") != null ? request.getParameter("symbol") : "";
        String jsonString = request.getParameter("lastName");
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException ex) {
        }

        Map<String, QuoteList> quoteMap = QuoteHandler.INSTANCE.retreiveCurrentQuote(true, symbol.split(","));

        //test commit from netbeans
        JSONArray quoteArray = new JSONArray();

        quoteMap.values().stream().forEach((quoteList) -> {
            quoteArray.add(quoteList.toString().toUpperCase());
        });

        returnJson.put("count", quoteArray.size());
        returnJson.put("quoteInfo", quoteArray);
        response.setContentType("application/JSON");
        response.getWriter().write(returnJson.toJSONString());

    }

    private void loadStockTicker(final HttpServletRequest request,
            final HttpServletResponse response) throws IOException {
        JSONObject jsonObject = null;
        JSONObject returnJson = new JSONObject();
        String symbols = request.getParameter("symbol") != null ? request.getParameter("symbol") : "";
        String jsonString = request.getParameter("lastName");
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException ex) {
        }
        Map<String, QuoteList> quoteMap = QuoteHandler.INSTANCE.retreiveCurrentQuote(true, symbols.split(","));

        //test commit from netbeans
        JSONArray quoteArray = new JSONArray();

        quoteMap.values().stream().forEach((quoteList) -> {
            quoteArray.add(quoteList.toString().toUpperCase());
        });

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
