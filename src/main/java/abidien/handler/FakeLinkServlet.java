package abidien.handler;

import abidien.autopost.models.FakeLinkEntity;
import abidien.chuongga.Environment;
import abidien.common.*;
import abidien.controllers.BaseServlet;
import javafx.util.Pair;
import org.apache.commons.lang.RandomStringUtils;
import org.eclipse.jetty.server.Request;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

/**
 * Created by ABIDIEN on 09/09/2016.
 */

//facebookexternalhit/1.1 (+http://www.facebook.com/externalhit_uatext.php)
public class FakeLinkServlet extends BaseServlet {
    final ConcurrentHashMap<String, String> contentMap = new ConcurrentHashMap<>();
    final ConcurrentLinkedQueue<Pair<String, Long>> removeContentQueue = new ConcurrentLinkedQueue<>();
    final long lifeTime = 10 * 60 * 1000;

    public FakeLinkServlet() {
        Spawn.run(()->{
            while (true) {
                Pair<String, Long> pair = removeContentQueue.peek();
                if (pair != null && pair.getValue() + lifeTime < System.currentTimeMillis()) {
                    contentMap.remove(pair.getKey());
                    removeContentQueue.poll();
                } else try {
                    Thread.sleep(60* 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String endcodeId = request.getParameter("id");
        if (endcodeId != null) {
            int id = SecurityUtils.decode(endcodeId);
            FakeLinkEntity fakeLinkEntity = Environment.getFakeLinkService().load(id);
            if (fakeLinkEntity == null)
                return;

            String userAgent = request.getHeader("User-Agent");
            if (request.getParameter("TrackUA") != null)
                System.out.println("UA " + new Date() + ":" + userAgent);

            String targetUrl = fakeLinkEntity.getTargetUrl();
            String currentDomain = request.getHeader("X-Forwarded-Host");
            if (currentDomain == null)
                currentDomain = ((Request)request).getRootURL().toString();
            else currentDomain = "http://" + currentDomain;

            if (userAgent.indexOf("facebook") >= 0) {
                HashMap hm = new HashMap();
                String title = fakeLinkEntity.getTitle();
                String desc = fakeLinkEntity.getDescription();
                String image = fakeLinkEntity.getLocalImage(currentDomain);
                hm.put("title", title);
                hm.put("desc", desc);
                hm.put("image", image);

                //get content
                String content = getContent(targetUrl, currentDomain);
                int index = content.indexOf("</head>");
                String body = content.substring(index + 7);
                hm.put("body", body);

                String rs = TemplateEngine.renderFakeLink(hm);
                response.setContentType("text/html");
                response.getWriter().write(rs);
                return;
            } else {
                response.addCookie(new Cookie("link", endcodeId));
                response.sendRedirect(currentDomain + "/html/" + RandomStringUtils.randomAlphanumeric(5) + ".html");
                return;
            }
        }
        Cookie cookie = Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("link")).findFirst().orElse(null);
        if (cookie != null) {
            int id = SecurityUtils.decode(cookie.getValue());
            String targetUrl = Environment.getFakeLinkService().load(id).getTargetUrl();

            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            response.sendRedirect(targetUrl);
            return;
        }
        response.sendRedirect("https://google.com");
    }

    public String getContent(String url,  String currentDomain) {
        if (contentMap.containsKey(url)) {
            return contentMap.get(url);
        }
        String content = HttpUtils.execute(url);
        String host = "";
        try {
            host = new URL(url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        content = content.replaceAll(host, currentDomain.split("//")[1]);
        contentMap.put(url, content);
        removeContentQueue.add(new Pair(url, System.currentTimeMillis()));
        return content;
    }
}
