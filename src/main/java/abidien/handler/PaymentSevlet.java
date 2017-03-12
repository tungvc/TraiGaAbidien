package abidien.handler;

import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.controllers.BaseServlet;
import abidien.models.AdsenseAccountEntity;
import com.google.api.services.adsense.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ABIDIEN on 12/03/2017.
 */
public class PaymentSevlet extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adsenseId = request.getParameter("id");
        AdsenseAccountEntity adsenseAccount = Environment.getAdsenseAccountService().load(adsenseId);
        List<Payment> payments = adsenseAccount.adsense.payments().list().execute().getItems();
        request.setAttribute("payments", payments);
        Helper.forwardPage(this, request, response, "Payments");
    }
}
