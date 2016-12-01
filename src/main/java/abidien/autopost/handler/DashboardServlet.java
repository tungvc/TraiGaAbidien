package abidien.autopost.handler;

import abidien.autopost.models.FakeLinkEntity;
import abidien.chuongga.Environment;
import abidien.common.Invoke;
import abidien.controllers.BaseServlet;
import abidien.handler.RestServlet;
import abidien.handler.SmartServlet;
import abidien.services.IDataService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 30/07/2016.
 */
public class DashboardServlet extends RestServlet<FakeLinkEntity> {

    @Override
    public FakeLinkEntity factory() {
        return new FakeLinkEntity(null, 0);
    }

    public DashboardServlet() {
        super(Environment.getFakeLinkService());
    }

    @Invoke(params = "request,response")
    public void dashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            request.setAttribute("pageName", "APDashboard");
            String url = "/autopost/Templates/CreateMainPage.jsp";
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);
        } else
            response.sendRedirect("/web/login");
    }


}
