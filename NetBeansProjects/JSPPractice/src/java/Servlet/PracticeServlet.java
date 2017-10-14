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
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
        JSONObject jsonObject = null;
//        String param1 = request.getParameter("param1");
//        param1 = param1 != null ? param1 : "";
//        out.println("<html><head><title>Hello World!</title></head>");
//        out.println("<body><h1>Hello World! param1: " + param1 + " </h1></body></html>");
        JSONObject returnJson = new JSONObject();
        String symbol = request.getParameter("symbol") != null ? request.getParameter("symbol") : "";
        String jsonString = request.getParameter("lastName");
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(jsonString);
        } catch (ParseException ex) {
        }
        Map<String, QuoteList> quoteMap = QuoteHandler.INSTANCE.retreiveCurrentQuote(true, symbol);

        //test commit from netbeans
        JSONArray quoteArray = new JSONArray();
        

        quoteMap.values().stream().forEach((quoteList) -> {
            quoteArray.add(quoteList.toString());
        });

        returnJson.put("count", quoteArray.size());
        returnJson.put("quoteInfo", quoteArray);
        response.setContentType("application/JSON");
        response.getWriter().write(returnJson.toJSONString());

//        String jsonObject = "{ key1: 'value1', key2: 'value2' }";
//        String stuff = request.getParameter("data");
//        String stuff2 = request.getParameter("teststuff");
//        response.setContentType("application/json");
//// Get the printwriter object from response to write the required json object to the output stream      
//        PrintWriter printout = response.getWriter();
//// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
//        printout.write(jsonObject);
//        
//        printout.flush();
//        response.setContentType("text/html;charset=UTF-8");
////        request.getRequestDispatcher("/newjspmain.jsp").forward(request, response);
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet PracticeServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
////            try {
////                out.println("<h1>Servlet PracticeServlet at " + request.getContextPath() + ""
////                        + " " + DatabaseConnector.INSTANCE.testQuery(false) + "</h1>");
////            } catch (SQLException ex) {
////                Logger.getLogger(PracticeServlet.class.getName()
////                ).log(Level.SEVERE, null, ex);
////            }
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

//    @Override
//    public void service(HttpServletRequest req,
//            HttpServletResponse res) throws IOException {
//        res.setContentType("text/html");
//        PrintWriter out = res.getWriter();
//        out.println("<html><head><title>Hello World!</title></head>");
//        out.println("<body><h1>Hello World!</h1></body></html>");
//    }
    private void forwardToPage(final HttpServletRequest request,
            final HttpServletResponse response,
            String url)
            throws IOException, ServletException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
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
