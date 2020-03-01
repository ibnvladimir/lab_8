package ru.ncedu.course.catalog_example.servlet;

import ru.ncedu.course.catalog_example.model.dao.CommentDAO;
import ru.ncedu.course.catalog_example.model.entity.CommentEntity;
import ru.ncedu.course.catalog_example.service.AuthorizationBean;
import ru.ncedu.course.catalog_example.service.CommentService;
import ru.ncedu.course.catalog_example.util.PathConstants;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(PathConstants.LIKE_PATH)
public class LikeServlet extends HttpServlet {

    private static final String OFFERING_JSP = "/offering.jsp";
    private static final String FROM_OFFERING_ATTR = "fromOffering";

    @Inject
    private AuthorizationBean authorizationBean;
    @Inject
    private CommentService commentService;
    @Inject
    private CommentDAO commentDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(authorizationBean.isAuthorized()) {
            commentService.addLike(commentDAO.findById(Long.parseLong(req.getParameter("id"))).get());
        }
        String fromOffering = req.getParameter("idOffering");
        if(fromOffering == null) {
            resp.sendRedirect(PathConstants.CATALOG_PATH);
        } else {
            resp.sendRedirect(PathConstants.OFFERING_PATH + "?id=" + fromOffering);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
