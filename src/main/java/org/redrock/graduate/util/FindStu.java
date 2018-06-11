package org.redrock.graduate.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by momo on 2018/6/11
 */
public class FindStu {
    public static String findStu(String openid) {
        String url = "https://wx.idsbllp.cn/MagicLoop/index.php?s=/addon/UserCenter/UserCenter/getStuInfoByOpenId&openId=" + openid;
        String res = CurlUtil.getContent(url, null, "GET");
        JSONObject jsonObject = JSON.parseObject(res);
        if (jsonObject.getInteger("status") == 200) {
            JSONObject jsonObject1 = JSON.parseObject(jsonObject.getString("data"));
            return jsonObject1.getString("usernumber");
        }else {
            return null;
        }
    }

    public static void main(String[] args) {
        String test = findStu("ouRCyjpwNN0vK5zROz6ygn9oe5I");
        System.out.println(test);

    }
}
