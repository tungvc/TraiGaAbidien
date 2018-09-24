package abidien.FanpageRenaming.handlers;

import abidien.FanpageRenaming.bo.FacebookCookie_BO;
import abidien.FanpageRenaming.bo.RenameFanpage_BO;
import abidien.common.Invoke;
import abidien.common.WebUtils;
import abidien.handler.SmartServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RenameServlet extends SmartServlet {

    public RenameServlet() {
        super(null);
    }

    @Invoke(params = "request,response", authen = false)
    public void rename_fanpage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String fanpageID = request.getParameter("pageID");
        String oldName = request.getParameter("oldName");
        String newName = request.getParameter("newName");

        FacebookCookie_BO.FacebookCookie oCookie = FacebookCookie_BO.getCookie(userName, passWord);
        if (oCookie.msCookie.toLowerCase().contains("checkpoint")) {
            WebUtils.renderText(response, "Check point!!");
        } else if (oCookie.msDtsg == null || oCookie.msDtsg.trim().isEmpty()) {
            WebUtils.renderText(response, "Không thể đăng nhập!!");
        } else {
            String sResult = RenameFanpage_BO.changeFanpageName(oCookie.msDtsg, oCookie.msCookie, fanpageID, oldName, newName);
            WebUtils.renderText(response, sResult);
        }

    }
}
