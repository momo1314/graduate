package org.redrock.graduate.Controller;

import org.redrock.graduate.bean.Stu;
import org.redrock.graduate.util.FindStu;
import org.redrock.graduate.util.PropertyUtil;
import org.redrock.graduate.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by momo on 2018/6/11
 */
@RestController
public class IndexController {
    private String apiUrl = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/Api/Api/oauth&redirect=";
    private String bindUrl = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/Bind/Bind/bind/openid/";
    private String fUrl = "https://wx.idsbllp.cn/nodejs/graduationSeason/";
    @GetMapping("/")
    public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Stu stu = new Stu();
        if (session.getAttribute("stu")==null) {
            String isDubug = PropertyUtil.getProperty("weixin.debug");
            if (isDubug.equalsIgnoreCase("true")) {
                stu.setOpenid(PropertyUtil.getProperty("weixin.openid"));
                stu.setNickname(PropertyUtil.getProperty("weixin.nickname"));
                stu.setHeadimgurl(PropertyUtil.getProperty("weixin.imgurl"));
            } else {
                stu.setOpenid(request.getParameter("openid"));
                if (StringUtil.isBlank(stu.getOpenid())) {
                    String indexUrl = PropertyUtil.getProperty("url.index");
                    String redirectUrl = apiUrl + URLEncoder.encode(indexUrl, "UTF-8");
                    response.sendRedirect(redirectUrl);
                    return;
                }
                stu.setNickname(URLDecoder.decode(request.getParameter("nickname"),"UTF-8"));
                stu.setHeadimgurl(URLDecoder.decode(request.getParameter("headimgurl"),"UTF-8"));
            }
            stu.setUsernumber(FindStu.findStu(stu.getOpenid()));
            if(stu.getUsernumber()==null){
                response.sendRedirect(bindUrl + stu.getOpenid());
                return;
            }
            session.setAttribute("stu", stu);
            response.sendRedirect(fUrl);
        } else {
            response.sendRedirect(fUrl);
        }
    }
}
