package abidien.handler;

import abidien.common.Invoke;
import abidien.controllers.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 29/11/2016.
 */
public class RestServlet extends SmartServlet {

    @Invoke(params = "request")
    public void print(String request) {
        System.out.println(request);
    }
}
