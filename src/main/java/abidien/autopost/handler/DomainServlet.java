package abidien.autopost.handler;

import abidien.autopost.models.DomainEntity;
import abidien.chuongga.Environment;
import abidien.common.Invoke;
import abidien.handler.RestServlet;
import abidien.handler.SmartServlet;
import abidien.services.IDataService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 02/12/2016.
 */
public class DomainServlet extends RestServlet<DomainEntity> {

    @Override
    public DomainEntity factory() {
        return new DomainEntity(null, 0);
    }

    public DomainServlet() {
        super(Environment.getDomainService());
    }

    @Invoke(params = "request,response")
    public void domain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            request.setAttribute("pageName", "APDomain");
            String url = "/autopost/Templates/CreateMainPage.jsp";
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } else
            response.sendRedirect("/web/login");
    }
}
