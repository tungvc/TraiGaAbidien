package abidien.handler;

import abidien.controllers.BaseServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class LoginServlet extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/Login.jsp";
        if (request.getParameter("logout") != null)
            request.getSession().invalidate();
        if (request.getParameter("userName") != null && request.getParameter("password") != null && request.getParameter("userName").equals("admin") && request.getParameter("password").equals("admin")) {
            request.getSession().setAttribute("user", request.getParameter("userName"));
            url = "/web/dashboard";
            String redirectUrl = request.getParameter("redirectUrl");
            if (redirectUrl != null)
                url = URLDecoder.decode(redirectUrl, "UTF-8");
        }
        response.sendRedirect(url);
    }
}
