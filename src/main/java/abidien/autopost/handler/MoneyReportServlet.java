package abidien.autopost.handler;

import abidien.autopost.models.ReportEntity;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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


        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (startDate == null || endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            endDate = sdf.format(new Date());
            startDate = sdf.format(new Date(System.currentTimeMillis() - 29L*24*3600*1000));
        }

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        int userId = Helper.getUser(request).getId();
        List<Object[]> reportByUser = Environment.getReportService().getReportByUser(userId, ReportEntity.parseDateToInt(startDate), ReportEntity.parseDateToInt(endDate));
        request.setAttribute("report", reportByUser);

        Helper.forwardAutoPostPage(this, request, response, "APMoneyReport");
    }
}
