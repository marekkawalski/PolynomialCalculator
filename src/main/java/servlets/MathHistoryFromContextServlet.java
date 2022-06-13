package servlets;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import math.PolynomialMath;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class which handles calculation history. It acts as a second controller.
 *
 * @author Marek Kawalski
 * @version 1.0
 */
@WebServlet(name = "MathHistoryFromContextServlet", urlPatterns = {"/HistoryFromContext"})
public class MathHistoryFromContextServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Handle calculation history. It doesn't create model object, but
     * uses one created in context listener.
     *
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            ServletContext context;
            context = getServletContext();
            PolynomialMath polynomialMath;
            polynomialMath = (PolynomialMath) context.getAttribute("polynomialMath");
            List<String> historyArray;
            historyArray = polynomialMath.getHistoryList();

            out.print("<html>\n"
                    + "<head>\n "
                    + "<title>Calculation History</title>"
                    + "<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\" />"
                    + "</head>"
                    + "<body>"
                    + "<nav>"
                    + "<div class=\"links\">"
                    + "<a href =\"/PolynomialCalculator\" class=\"link\">Home</a>"
                    + "</div>"
                    + "</nav>"
                    + "<h2>Calculation History</h2>"
                    + "<div class=\"readingInfo\"> Reading from context:</div>");
            if (historyArray.isEmpty()) {
                out.print("<p class=\"noCalculation\">No calculation history available</p>");
            } else {
                out.print(
                        "<table>"
                                + " <tr>"
                                + "<th>Calculation</th>"
                                + "<th>Calculation value</th>"
                                + "<th>First derivative</th>"
                                + "<th>First derivative value</th>"
                                + " </tr>");
                //print data from context
                int counter = 1;
                out.print("<tr>");
                for (String i : historyArray) {
                    out.print(
                            "<td> " + i + "</td>");
                    if (counter % 4 == 0) {
                        out.print("</tr>");
                        out.print("<tr>");
                    }
                    counter++;
                }
                out.print("</tr>");

                out.print(
                        "</table>"
                );
            }
            out.print(
                    "</body>"
                            + "</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(response);
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
