package org.redrock.graduate.Controller;

import org.redrock.graduate.bean.Stu;
import org.redrock.graduate.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by momo on 2018/6/9
 */
@RestController
public class InfoController {
    protected static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @PostMapping("/c/setsinfo")
    public ResponseEntity<Stu> SetSInfo(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
        Stu stu = new Stu();
        logger.info("开始检查参数");
        if(!StringUtil.isBlank(request.getParameter("openid"))&&!StringUtil.isBlank(request.getParameter("headimgurl"))&&!StringUtil.isBlank(request.getParameter("nickname"))) {
            logger.info("参数存在,写入session");
            stu.setOpenid(request.getParameter("openid"));
            stu.setHeadimgurl(URLDecoder.decode(request.getParameter("headimgurl"), "UTF-8"));
            stu.setNickname(URLDecoder.decode(request.getParameter("nickname"), "UTF-8"));
            stu.setState(200);
            session.setAttribute("stu", stu);
//            Connection connection = DBCPFactory.getConnection();
//            String sql="";
//            try {
//                PreparedStatement pst = connection.prepareStatement(sql);
//                pst.setString(1, stu.getOpenid());
//                pst.setString(2, stu.getNickname());
//                pst.setString(3, stu.getHeadimgurl());
//                pst.execute();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            stu.setOpenid(null);
            return new ResponseEntity<>(stu, HttpStatus.valueOf(200));
        }else {
            logger.info("参数残缺,错误");
            stu.setState(500);
            return new ResponseEntity<>(stu, HttpStatus.valueOf(500));
        }
    }

    @PostMapping("/c/getsinfo")
    public ResponseEntity<Stu> GetSInfo(HttpSession session){
        Stu stu=new Stu();
        logger.info("检查是否存在session");
        if(session.getAttribute("stu")!=null&&session.getAttribute("stu")!="") {
            logger.info("session存在,返回数据");
            stu = (Stu) session.getAttribute("stu");
            if (stu.getState() == 200) {
                stu.setOpenid(null);
                return new ResponseEntity<>(stu, HttpStatus.valueOf(200));
            } else {
                logger.info("参数残缺(x");
                stu.setState(300);
                return new ResponseEntity<>(stu, HttpStatus.valueOf(300));
            }
        }else{
            logger.info("session不存在,需要调用接口设置");
            stu.setState(300);
            return new ResponseEntity<>(stu, HttpStatus.valueOf(300));
        }
    }


}
