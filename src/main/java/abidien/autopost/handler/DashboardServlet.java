package abidien.autopost.handler;

import abidien.autopost.models.FakeLinkEntity;
import abidien.autopost.models.FakeLinkResponse;
import abidien.chuongga.Environment;
import abidien.common.*;
import abidien.controllers.BaseServlet;
import abidien.handler.RestServlet;
import abidien.handler.SmartServlet;
import abidien.services.IDataService;
import org.apache.commons.lang.RandomStringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ABIDIEN on 30/07/2016.
 */
public class DashboardServlet extends RestServlet<Integer, FakeLinkEntity> {

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
        int userId = Helper.getUser(request).getId();
        List<FakeLinkEntity> data = Environment.getFakeLinkService().loadAll().stream()
                .filter(p -> p.getOwnerId().equals(userId))
                .collect(Collectors.toList());
        if (data != null && data.size() > 0)
            data.sort((x1, x2) -> (x2.getId() == null ? 0 : x2.getId()) - (x1.getId() == null ? 0 : x1.getId()));
        List<FakeLinkResponse> report = Environment.getReportService().getReport(data);
        request.setAttribute("fakeLinkList", report);

        Helper.forwardAutoPostPage(this, request, response, "APDashboard");
    }

    @Invoke(params = "request,response,id", authen = false)
    public void genLink(HttpServletRequest request, HttpServletResponse response, int id) {
        int userId = Helper.getUser(request).getId();
        String param = ".html?id=" + SecurityUtils.encode(id);
        List<String> rs = Environment.getDomainService().loadAll().stream()
                .filter(p -> p.getOwnerId() == userId)
                .map(s -> s.getDomain() + "/html/" + RandomStringUtils.randomAlphanumeric(5) + param + "&v2=t")
                .collect(Collectors.toList());
        Collections.shuffle(rs);
        WebUtils.renderJson(response, JsonExt.toJson(rs));
    }
}
