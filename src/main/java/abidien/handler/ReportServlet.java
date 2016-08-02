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

        AdsenseAccountEntity adsenseAccountEntity = Environment.getAdsenseAccountService().load(adsenseId);
        if (adsenseAccountEntity != null && accountId != null && adClientId != null &&
            adsenseAccountEntity.getUserId() == ((UserEntity)request.getSession().getAttribute("user")).getId()) {
            try {
                ReportResponse resp = GenerateReport.run(adsenseAccountEntity.adsense, accountId, adClientId);
                request.setAttribute("resp", resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Helper.forwardPage(this, request, response, "Report");
    }
}
