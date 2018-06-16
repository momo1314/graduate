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

//    @PostMapping("/c/setsinfo")
//    public ResponseEntity<Stu> SetSInfo(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
//        Stu stu = new Stu();
//        logger.info("start setsinfo");
//        if(!StringUtil.isBlank(request.getParameter("openid"))&&!StringUtil.isBlank(request.getParameter("headimgurl"))&&!StringUtil.isBlank(request.getParameter("nickname"))) {
//            logger.info("参数存在,写入session");
//            stu.setOpenid(request.getParameter("openid"));
//            stu.setHeadimgurl(URLDecoder.decode(request.getParameter("headimgurl"), "UTF-8"));
//            stu.setNickname(URLDecoder.decode(request.getParameter("nickname"), "UTF-8"));
//            stu.setState(200);
//            session.setAttribute("stu", stu);
//            stu.setOpenid(null);
//            return new ResponseEntity<>(stu, HttpStatus.valueOf(200));
//        }else {
//            logger.info("参数残缺,错误");
//            stu.setState(500);
//            return new ResponseEntity<>(stu, HttpStatus.valueOf(500));
//        }
//    }

    @PostMapping("/c/getsinfo")
    public ResponseEntity<Stu> GetSInfo(HttpSession session){
        Stu stu=new Stu();
        logger.info("check session");
        if(session.getAttribute("stu")!=null&&session.getAttribute("stu")!="") {
            logger.info("have session");
            stu = (Stu) session.getAttribute("stu");
            stu.setState(200);
            return new ResponseEntity<>(stu, HttpStatus.valueOf(200));
        }else{
            logger.info("haven't session");
            stu.setState(300);
            return new ResponseEntity<>(stu, HttpStatus.valueOf(300));
        }
    }


}
