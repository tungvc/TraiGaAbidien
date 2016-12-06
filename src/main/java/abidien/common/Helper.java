package abidien.common;

import abidien.models.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class Helper {
    public static void forwardPage(HttpServlet httpServlet, HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
        String url = "/Templates/CreateMainPage.jsp";
        request.setAttribute("pageName", pageName);
        RequestDispatcher rd = httpServlet.getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    public static void forwardAutoPostPage(HttpServlet httpServlet, HttpServletRequest request, HttpServletResponse response, String pageName) throws ServletException, IOException {
        String url = "/autopost/Templates/CreateMainPage.jsp";
        request.setAttribute("pageName", pageName);
        RequestDispatcher rd = httpServlet.getServletContext().getRequestDispatcher(url);
        rd.forward(request, response);
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    public static UserEntity getUser(HttpServletRequest request) {
        return (UserEntity)request.getSession().getAttribute("user");
    }
}
