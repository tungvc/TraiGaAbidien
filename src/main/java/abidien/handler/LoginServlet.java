package abidien.handler;

import abidien.chuongga.Environment;
import abidien.controllers.BaseServlet;
import abidien.models.UserEntity;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class LoginServlet extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/Login.jsp";
        if (request.getParameter("logout") != null)
            request.getSession().invalidate();
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            List<UserEntity> users = Environment.getUserDataService().loadAll();
            for (UserEntity u: users) {
                if (u.getEmail().equals(username)) {
                    if (u.getPassword().equals(String.valueOf(password.hashCode()))) {
                        request.getSession().setAttribute("user", u);
                    }
                    break;
                }
            }
            url = "/web/dashboard";
            String redirectUrl = request.getParameter("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty())
                url = URLDecoder.decode(redirectUrl, "UTF-8");
        }
        response.sendRedirect(url);
    }
}
