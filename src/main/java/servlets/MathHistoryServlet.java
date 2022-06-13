package servlets;

import controllers.MathDateJpaController;
import controllers.MathEntityJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import math.MathEntity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class which handles calculation history. It acts as a second controller.
 * Class displays contents of database. There are two tables in the database.
 * They are displayed as one in html.
 *
 * @author Marek Kawalski
 * @version 2.1
 */
@WebServlet(name = "MathHistoryServlet", urlPatterns = {"/History"})
public class MathHistoryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Handle calculation history. It doesn't create model object, but
     * uses one created in context listener.
     *
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(@NotNull HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            ServletContext context;
            context = getServletContext();

            EntityManagerFactory emf = (EntityManagerFactory) context.getAttribute("entityManagerFactory");
            if (emf == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No database connection!");
                return;
            }
            MathEntityJpaController mathEntityJpaController = (MathEntityJpaController) context.getAttribute("mathEntityJpaController");
            MathDateJpaController mathDateJpaController = (MathDateJpaController) context.getAttribute("mathDateJpaController");

            List<MathEntity> mathEntityEntities = mathEntityJpaController.findMathEntityEntities();

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
                    + "<div class=\"readingInfo\">Reading from database</div>"
            );
            if (mathEntityEntities.isEmpty()) {
                out.print("<p class=\"noCalculation\">No calculation history available</p>");
            } else {
                out.print(
                        "<table>"
                                + " <tr>"
                                + "<th>Calculation</th>"
                                + "<th>Calculation value</th>"
                                + "<th>First derivative</th>"
                                + "<th>First derivative value</th>"
                                + "<th>Date of calculation</th>"
                                + " </tr>");
                //print data from database
                for (MathEntity i : mathEntityEntities) {
                    math.MathDate date = mathDateJpaController.findMathDate(i.getDateId());
                    out.print("<tr>"
                            + "  <td> " + i.getCalculationBase() + "</td>"
                            + "  <td> " + i.getCalculationValue() + "</td>"
                            + "  <td> " + i.getPolynomialFirstDerivative() + "</td>"
                            + "  <td> " + i.getPolynomialFirstDerivativeValue() + "</td>");
                    if (date != null) {
                        out.print("  <td> " + date.getDate() + "</td>");
                    } else {
                        out.print("<td> no date </td>");
                    }
                    out.print("</tr>");

                }
                out.print(
                        "</table>"
                );
            }
            out.print("</body>"
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
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
