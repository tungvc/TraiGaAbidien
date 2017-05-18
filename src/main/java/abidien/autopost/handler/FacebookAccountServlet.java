package abidien.autopost.handler;

import abidien.autopost.models.DomainEntity;
import abidien.autopost.models.FacebookAccountEntity;
import abidien.chuongga.Environment;
import abidien.handler.RestServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 19/05/2017.
 */
public class FacebookAccountServlet extends RestServlet<Integer, FacebookAccountEntity> {
    @Override
    public FacebookAccountEntity factory() {
        return new FacebookAccountEntity();
    }

    public FacebookAccountServlet() {
        super(Environment.getFacebookAccountDataDriver());
    }


}
