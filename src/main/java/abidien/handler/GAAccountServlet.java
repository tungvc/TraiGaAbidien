package abidien.handler;

import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.controllers.BaseServlet;
import abidien.models.AdClientsEntity;
import abidien.models.AdsenseAccountEntity;
import abidien.models.GAAccountResponse;
import abidien.models.UserEntity;
import abidien.services.adsense.api.AdsenseService;
import abidien.services.adsense.api.GenerateReport;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.adsense.AdSense;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class GAAccountServlet extends BaseServlet {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLogin(request, response))
            return;
        String uri = request.getRequestURI().toLowerCase();
        String methodName = uri.substring(Math.min(uri.length(), request.getServletPath().length() + 1));
        UserEntity user = (UserEntity)request.getSession().getAttribute("user");
        if (request.getParameter("add_account") != null) {
            response.sendRedirect(AdsenseService.getInstance().genLinkAddGAAccount());
            return;
        }
        if (methodName.equals("callback")) {
            String adsenseAccountId = user.getId() + "_" + System.currentTimeMillis();
            Credential credential = AdsenseService.getInstance().addGAAccount(adsenseAccountId, request.getParameter("code"));
            if (credential != null) {
                Environment.getAdsenseAccountService().saveOrUpdate(new AdsenseAccountEntity(adsenseAccountId, user.getId()));
            }
            response.sendRedirect("/web/ga_account");
        }
        else {
            try {
                ArrayList<GAAccountResponse> resp = new ArrayList<>();
                Collection<AdsenseAccountEntity> adsenseAccountList = Environment.getAdsenseAccountService().all.values();
                for (AdsenseAccountEntity adsenseAccount: adsenseAccountList) {
                    if (adsenseAccount.getUserId() == user.getId()) {
                        AdSense adsense = adsenseAccount.adsense;
                        if (adsense != null) {
                            try {
                                for (AdClientsEntity adClient : adsenseAccount.adClients) {
                                    resp.add(new GAAccountResponse(adsenseAccount.getId(), adClient.accountId, adClient.adClientId));
//                                    GenerateReport.run(adsense, adClient.accountId, adClient.adClientId);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                request.setAttribute("accountAdsenses", resp);
                Helper.forwardPage(this, request, response, "GAAccount");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
