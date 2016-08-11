package abidien.handler;

import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.controllers.BaseServlet;
import abidien.models.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 11/08/2016.
 */
public class UserServelt extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request, response))
            return;

        String uri = request.getRequestURI().toLowerCase();
        String methodName = uri.substring(Math.min(uri.length(), request.getServletPath().length() + 1));
        switch (methodName) {
            case "create": createAccount(request, response);
            case "save": saveAccount(request, response);
            case "list": listAccount(request, response);
        }
    }

    private void listAccount(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("listAccount", Environment.getUserService().loadAll());
        try {
            Helper.forwardPage(this, request, response, "ListAccount");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAccount(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if (email != null && email.length() > 3 && name != null && !name.isEmpty() && password != null && password.length() > 5)
            Environment.getUserService().saveOrUpdate(new UserEntity(name, email, String.valueOf(password.hashCode())));
        listAccount(request, response);
    }

    void createAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Helper.forwardPage(this, request, response, "CreateAccount");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
