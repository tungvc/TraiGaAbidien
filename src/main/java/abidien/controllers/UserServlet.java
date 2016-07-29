package abidien.controllers;

import abidien.models.UserEntity;
import abidien.services.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ABIDIEN on 05/07/2016.
 */
public class UserServlet extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from UserEntity";
        Query query = session.createQuery(hql);
        List<UserEntity> rs = query.list();
        request.setAttribute("dm", rs);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        try {
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
