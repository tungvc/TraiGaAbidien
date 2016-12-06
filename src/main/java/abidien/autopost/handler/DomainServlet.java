package abidien.autopost.handler;

import abidien.autopost.models.DomainEntity;
import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.common.Invoke;
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

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.process(request, response);
        response.sendRedirect("/web/domain");
    }

    @Invoke(params = "request,response")
    public void domain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            List<DomainEntity> data = Environment.getDomainService().loadAll();
            request.setAttribute("domainList", data);
            Helper.forwardAutoPostPage(this, request, response, "APDomain");
        } else
            response.sendRedirect("/web/login");
    }


}
