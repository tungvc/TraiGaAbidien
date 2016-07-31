package abidien.controllers;

import abidien.common.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by ABIDIEN on 05/07/2016.
 */
public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    public abstract void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession().getAttribute("user") != null)
            return true;
        try {
            response.sendRedirect("/Login.jsp?redirectUrl=" + URLEncoder.encode(Helper.getFullURL(request), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
