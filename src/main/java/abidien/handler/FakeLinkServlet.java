package abidien.handler;

import abidien.common.TemplateEngine;
import abidien.controllers.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
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
        String userAgent = request.getHeader("User-Agent");

        if (request.getParameter("TrackUA") != null)
            System.out.println("UA " + new Date() + ":" + userAgent);

        if (String.valueOf(redirectUrl.hashCode()).equals(token)) {
            HashMap hm = new HashMap();
            hm.put("redirectUrl", redirectUrl);
            if (userAgent.indexOf("facebook") >= 0) {
                hm.put("isHead", true);
                hm.put("title", title);
                hm.put("desc", desc);
                hm.put("siteUrl", siteUrl);
                hm.put("image", image);
                hm.put("isRedirect", false);
                rs = TemplateEngine.renderFakeLink(hm);
            } else if (request.getParameter("isOk") != null) {
                hm.put("isHead", false);
                hm.put("isRedirect", true);
                rs = TemplateEngine.renderFakeLink(hm);
            } else {
                hm.put("redirectUrl", "http://newstech.top/genlink?" + URLDecoder.decode(request.getQueryString(), "UTF-8") + "&isOk=true");
                hm.put("isHead", false);
                hm.put("isRedirect", true);
                rs = TemplateEngine.renderFakeLink(hm);
            }
        }
        response.setContentType("text/html");
        response.getWriter().write(rs);
    }
}
