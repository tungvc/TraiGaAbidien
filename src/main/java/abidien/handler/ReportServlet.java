package abidien.handler;

import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.controllers.BaseServlet;
import abidien.models.AdsenseAccountEntity;
import abidien.models.ReportResponse;
import abidien.models.UserEntity;
import abidien.services.adsense.api.GenerateReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ABIDIEN on 02/08/2016.
 */
public class ReportServlet extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLogin(request, response))
            return;
        String adsenseId = request.getParameter("id");
        String accountId = request.getParameter("accountId");
        String adClientId = request.getParameter("adClientId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        if (startDate == null || endDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            endDate = sdf.format(new Date());
            startDate = sdf.format(new Date(System.currentTimeMillis() - 6l*24*3600*1000));
        }

        AdsenseAccountEntity adsenseAccountEntity = Environment.getAdsenseAccountService().load(adsenseId);
        UserEntity user = (UserEntity)request.getSession().getAttribute("user");
        if (adsenseAccountEntity != null && accountId != null && adClientId != null &&
                (adsenseAccountEntity.getOwnerId() == user.getId() || adsenseAccountEntity.getShareUsers().contains(user.getId())                )) {
            try {
                ReportResponse resp = GenerateReport.run(adsenseAccountEntity.adsense, accountId, adClientId, startDate, endDate);
                request.setAttribute("resp", resp);
                request.setAttribute("adsenseId", adsenseId);
                request.setAttribute("accountId", accountId);
                request.setAttribute("adClientId", adClientId);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Helper.forwardPage(this, request, response, "Report");
    }
}
