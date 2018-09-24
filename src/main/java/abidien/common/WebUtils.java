package abidien.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ABIDIEN on 10/12/2016.
 */
public class WebUtils {
    public static void renderJson(HttpServletResponse response, String js) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().println(js);
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderText(HttpServletResponse response, String js) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().println(js);
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
