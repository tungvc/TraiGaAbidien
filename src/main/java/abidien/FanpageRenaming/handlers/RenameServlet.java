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
        String sCookie = request.getParameter("cookie");
        if (sCookie == null || sCookie.trim().isEmpty()) {
            sCookie = FacebookCookie_BO.getCookie(userName, passWord);
        }

        if (sCookie == null || sCookie.trim().isEmpty()) {
            WebUtils.renderText(response, "Không thể đăng nhập!!");
        } else if (sCookie.toLowerCase().contains("checkpoint")) {
            WebUtils.renderText(response, "Check point!!");
        } else {
            String sDTSG = FacebookCookie_BO.getDTSG(sCookie);
            if (sDTSG == null || sDTSG.trim().isEmpty()) {
                WebUtils.renderText(response, "Không thể đăng nhập!!");
            }
            else {
                String sResult = RenameFanpage_BO.changeFanpageName(sDTSG, sCookie, fanpageID, oldName, newName);
                WebUtils.renderText(response, sResult);
            }
        }
    }
}
