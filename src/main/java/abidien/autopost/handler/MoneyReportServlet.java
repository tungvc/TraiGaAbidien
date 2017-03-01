package abidien.autopost.handler;

import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.common.Invoke;
import abidien.controllers.BaseServlet;
import abidien.handler.SmartServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ABIDIEN on 01/03/2017.
 */
public class MoneyReportServlet extends SmartServlet {

    public MoneyReportServlet() {
        super(null);
    }

    @Invoke(params = "request,response")
    public void money_report(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Helper.getUser(request).getId();
        List<Object[]> reportByUser = Environment.getReportService().getReportByUser(userId);
        request.setAttribute("report", reportByUser);
        Helper.forwardAutoPostPage(this, request, response, "APMoneyReport");
    }
}
