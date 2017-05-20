package abidien.autopost.handler;

import abidien.autopost.models.DomainEntity;
import abidien.autopost.models.FacebookAccountEntity;
import abidien.chuongga.Environment;
import abidien.common.Helper;
import abidien.common.Invoke;
import abidien.handler.RestServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ABIDIEN on 19/05/2017.
 */
public class FacebookAccountServlet extends RestServlet<String, FacebookAccountEntity> {
    @Override
    public FacebookAccountEntity factory() {
        return new FacebookAccountEntity();
    }

    public FacebookAccountServlet() {
        super(Environment.getFacebookAccountService());
    }

    @Invoke(params = "request,response")
    public void fb_acc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Helper.getUser(request).getId();
        List<FacebookAccountEntity> data = Environment.getFacebookAccountService().loadAll().stream()
                .filter(p -> p.getOwnerId() == userId)
                .collect(Collectors.toList());
        request.setAttribute("accList", data);
        Helper.forwardAutoPostPage(this, request, response, "APFBAcc");
    }

}
