package servlets;

import controllers.MathDateJpaController;
import controllers.MathEntityJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import math.MathDate;
import math.MathEntity;
import math.PolynomialException;
import math.PolynomialMath;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class is used as a controller for all calculations. It doesn't create model
 * object. Model is read from servlet context and used withing this class.
 *
 * @author Marek Kawalski
 * @version 2.1
 */
@WebServlet(name = "MathServlet", urlPatterns = {"/Calculate"})
public class MathServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. This class acts as a controller.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //check if polynomials are null
            if (request.getParameter("polynomialValues") == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No polynomial values! Input values first.");
                return;
            }
            String polynomialValues = request.getParameter("polynomialValues");
            PolynomialMath polynomialMath;
            List<String> arrayOfCurrentCalculations = new ArrayList<>();

            //read model object from servlet context
            ServletContext context = getServletContext();
            polynomialMath = (PolynomialMath) context.getAttribute("polynomialMath");

            //process input
            Stream<String> givenNumbersArray = Stream.of(polynomialValues.split(" "));
            List<String> argsStringTab = new ArrayList<>();
            givenNumbersArray.forEach(argsStringTab::add);
            //check if input is correct
            for (String arg : argsStringTab) {
                if (!(arg.matches("\\d+") || arg.matches("-\\d+"))) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect input!");
                    return;
                }
            }

            //parse to Integer
            List<Integer> argsIntegerTab = new ArrayList<>();
            argsStringTab.stream()
                    .mapToInt(Integer::parseInt)
                    .forEach(argsIntegerTab::add);

            //handle data from model
            int calculatedValue;
            int calculatedFirstDerivativeValue;
            List<Integer> listOfFirstDerivativeFactors;
            //check if number of arguments is correct
            if (argsIntegerTab.size() < 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Not enough arguments!");
                return;
            }

            try {
                calculatedValue = polynomialMath.calculatePolynomialValue(argsIntegerTab);
                listOfFirstDerivativeFactors = polynomialMath.calculatePolynomialFirstDerivative(argsIntegerTab);
                calculatedFirstDerivativeValue = polynomialMath.calculatePolynomialFirstDerivativeValue(listOfFirstDerivativeFactors);
                arrayOfCurrentCalculations.add(polynomialOnScreen(argsIntegerTab));
                arrayOfCurrentCalculations.add(("y(" + argsIntegerTab.get(argsIntegerTab.size() - 1) + ") = " + calculatedValue));
                arrayOfCurrentCalculations.add(firstDerivativeOnScreen(listOfFirstDerivativeFactors));
                arrayOfCurrentCalculations.add("y'(" + listOfFirstDerivativeFactors.get(listOfFirstDerivativeFactors.size() - 1) + ") = " + calculatedFirstDerivativeValue);

                out.print("<html>\n"
                        + "<head>\n "
                        + "<title>Calculation Results</title>"
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"./styles.css\" />"
                        + "</head>"
                        + "<body>"
                        + "<nav>"
                        + "<div class=\"links\">"
                        + "<a href =\"/PolynomialCalculator\" class=\"link\">Home</a>"
                        + "</div>"
                        + "</nav>"
                        + "<h2>Calculation Results</h2>"
                        + "<div class=\"output\">"
                        + "<div class=\"outputInnerContainer\">"
                        + "<div class=\"element\">"
                        + arrayOfCurrentCalculations.get(0)
                        + "</div>"
                        + "<div class=\"element\">"
                        + arrayOfCurrentCalculations.get(1)
                        + "</div>"
                        + "<div class=\"element\">"
                        + arrayOfCurrentCalculations.get(2)
                        + "</div>"
                        + "<div class=\"element\">"
                        + arrayOfCurrentCalculations.get(3)
                        + "</div>"
                        + "</div>"
                        + "</div>"
                );

                out.print("</body>"
                        + "</html>");

                //adding calculation results to list which is stored in context
                polynomialMath.addToHistoryList(arrayOfCurrentCalculations.get(0));
                polynomialMath.addToHistoryList(arrayOfCurrentCalculations.get(1));
                polynomialMath.addToHistoryList(arrayOfCurrentCalculations.get(2));
                polynomialMath.addToHistoryList(arrayOfCurrentCalculations.get(3));

                EntityManagerFactory emf = (EntityManagerFactory) context.getAttribute("entityManagerFactory");
                if (emf == null) {
                    return;
                }
                //handling adding data to database
                MathEntityJpaController mathEntityJpaController = (MathEntityJpaController) context.getAttribute("mathEntityJpaController");
                MathDateJpaController mathDateJpaController = (MathDateJpaController) context.getAttribute("mathDateJpaController");

                MathDate mathDate = new MathDate();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                mathDate.setDate(dtf.format(now));
                mathDateJpaController.create(mathDate);

                MathEntity mathEntity = new MathEntity();
                mathEntity.setCalculationBase(arrayOfCurrentCalculations.get(0));
                mathEntity.setCalculationValue(arrayOfCurrentCalculations.get(1));
                mathEntity.setPolynomialFirstDerivative(arrayOfCurrentCalculations.get(2));
                mathEntity.setPolynomialFirstDerivativeValue(arrayOfCurrentCalculations.get(3));
                mathEntity.setDateId(mathDate.getId());
                mathEntityJpaController.create(mathEntity);

            } catch (PolynomialException myException) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, myException.getMessage());
                out.print("exception\n");
            }
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
        processRequest(request, response);
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

    /**
     * Method which returns polynomial equation on screen
     *
     * @param argsIntegerTab list of first polynomial factors
     * @return polynomial equation as String
     */
    private String polynomialOnScreen(List<Integer> argsIntegerTab) {
        return polynomialString(argsIntegerTab, new StringBuilder("y(x) = "));
    }

    /**
     * Method which returns first derivative equation on screen
     *
     * @param listOfFactors list of first derivative factors
     * @return first derivative equation as String
     */
    private String firstDerivativeOnScreen(List<Integer> listOfFactors) {
        return polynomialString(listOfFactors, new StringBuilder("y'(x) = "));
    }

    /**
     * Method responsible for creating polynomial and polynomial first derivative equation (as String)
     *
     * @param listOfFactors list of factors for the equation
     * @param equation      polynomial equation (its beginning for example y'(x)=
     * @return polynomial equation string (without the beginning)
     */
    @NotNull
    private String polynomialString(List<Integer> listOfFactors, StringBuilder equation) {
        int length = listOfFactors.size() - 1;
        int degree = listOfFactors.size() - 2;
        for (int i = 0; i < length; i++) {
            if (degree - i == 1) {
                equation.append(listOfFactors.get(i).toString()).append("x");
            } else if (degree - i == 0) {
                equation.append(listOfFactors.get(i).toString());
            } else {
                equation.append(listOfFactors.get(i).toString()).append("x^").append(degree - i);
            }
            if (i < length - 1) {
                equation.append(" + ");
            }
        }
        return equation.toString();
    }
}
