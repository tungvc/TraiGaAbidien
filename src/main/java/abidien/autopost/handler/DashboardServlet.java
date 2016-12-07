package abidien.autopost.handler;

import abidien.autopost.models.FakeLinkEntity;
import abidien.chuongga.Environment;
import abidien.common.Helper;
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
import java.util.List;

/**
 * Created by ABIDIEN on 30/07/2016.
 */
public class DashboardServlet extends RestServlet<FakeLinkEntity> {

    @Override
    public FakeLinkEntity factory() {
        return new FakeLinkEntity(null, null, null, null, 0);
    }

    public DashboardServlet() {
        super(Environment.getFakeLinkService());
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.process(request, response);
        response.sendRedirect("/web/dashboard");
    }

    @Invoke(params = "request,response")
    public void dashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            List<FakeLinkEntity> data = Environment.getFakeLinkService().loadAll();
            request.setAttribute("fakeLinkList", data);

            Helper.forwardAutoPostPage(this, request, response, "APDashboard");
        } else
            response.sendRedirect("/web/login");
    }


}
