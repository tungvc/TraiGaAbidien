package abidien.handler;

import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.controllers.BaseServlet;
import abidien.models.AdsenseAccountEntity;
import abidien.models.UserEntity;
import abidien.services.adsense.api.AdsenseService;
import abidien.services.adsense.api.GetAllAccounts;
import abidien.services.adsense.api.GetAllAdClients;
import abidien.models.GAAccountResponse;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.model.Account;
import com.google.api.services.adsense.model.Accounts;
import com.google.api.services.adsense.model.AdClient;
import com.google.api.services.adsense.model.AdClients;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        } if (methodName.equals("callback")) {
            String adsenseAccountId = user.getId() + "_" + System.currentTimeMillis();
            if (AdsenseService.getInstance().addGAAccount(adsenseAccountId, request.getParameter("code")) != null) {
                Environment.getAdsenseAccountDataService().saveOrUpdate(new AdsenseAccountEntity(adsenseAccountId, user.getId()));
            }
            response.sendRedirect("/web/ga_account");
        }
        else {
            try {
                ArrayList<GAAccountResponse> resp = new ArrayList<>();
                List<AdsenseAccountEntity> adsenseAccountList = Environment.getAdsenseAccountDataService().loadAll();
                for (AdsenseAccountEntity adsenseAccount: adsenseAccountList) {
                    AdSense adsense = AdsenseService.getInstance().getAdsense(adsenseAccount.getId());
                    if (adsense != null) {
                        Accounts accounts = GetAllAccounts.run(adsense, 50);
                        if (accounts != null) {
                            for (Account account : accounts.getItems()) {
                                AdClients adClients = GetAllAdClients.run(adsense, account.getId(), 50);
                                String rs = "";
                                for (AdClient adClient : adClients.getItems()) {
                                    rs += String.format("%s - %s | ", adClient.getProductCode(), adClient.getId());
                                }
                                if (rs.length() > 0) rs = rs.substring(0, rs.length() - 3);
                                resp.add(new GAAccountResponse(account.getId(), account.getName(), rs));
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

