package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.service.JourneyBean;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.JOURNEY_PATH)
public class JourneyServlet extends HttpServlet {

    private static final String JOURNEY_ATTR = "journey";
    private static final String JOURNEY_JSP = "/journey.jsp";

    @Inject
    private JourneyBean journeyBean;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().setAttribute(JOURNEY_ATTR, journeyBean.getList());
        getServletContext().getRequestDispatcher(JOURNEY_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        journeyBean.clear();
        getServletContext().getRequestDispatcher(JOURNEY_JSP).forward(req, resp);
    }
}
