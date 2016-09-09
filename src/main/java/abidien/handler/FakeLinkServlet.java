package abidien.handler;

import abidien.common.TemplateEngine;
import abidien.controllers.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ABIDIEN on 09/09/2016.
 */
public class FakeLinkServlet extends BaseServlet {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        String siteUrl = request.getParameter("siteUrl");
        String image = request.getParameter("image");
        String redirectUrl = request.getParameter("redirectUrl");
        String token = request.getParameter("token");
        String rs = "Token is not valid!!";
        if (String.valueOf(redirectUrl.hashCode()).equals(token)) {
            HashMap hm = new HashMap();
            hm.put("title", title);
            hm.put("desc", desc);
            hm.put("siteUrl", siteUrl);
            hm.put("image", image);
            hm.put("redirectUrl", redirectUrl);
            rs = TemplateEngine.renderFakeLink(hm);
        }
        response.setStatus(503);
        response.setContentType("text/html");
        response.getWriter().write(rs);
    }
}
