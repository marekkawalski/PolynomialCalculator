package listeners;

import controllers.MathDateJpaController;
import controllers.MathEntityJpaController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import math.PolynomialMath;


/**
 * Web application lifecycle listener. Is used to create instance of
 * polynomialMath class, which is then stored in servlet context. It also stores
 * EntityManagerFactory as not to create it multiple times.
 *
 * @author Marek Kawalski
 * @version 2.1
 */
public class ContextListener implements ServletContextListener {

    /**
     * Initialize context. Method only runs once.
     *
     * @param event event, action
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        MathEntityJpaController mathEntityJpaController = new MathEntityJpaController(emf);
        MathDateJpaController mathDateJpaController = new MathDateJpaController(emf);
        event.getServletContext().setAttribute("entityManagerFactory", emf);
        event.getServletContext().setAttribute("mathEntityJpaController", mathEntityJpaController);
        event.getServletContext().setAttribute("mathDateJpaController", mathDateJpaController);
        PolynomialMath polynomialMath = new PolynomialMath();
        event.getServletContext().setAttribute("polynomialMath", polynomialMath);

    }
}
