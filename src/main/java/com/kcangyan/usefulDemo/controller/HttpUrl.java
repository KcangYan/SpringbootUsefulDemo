package com.kcangyan.usefulDemo.controller;

import com.kcangyan.usefulDemo.config.MyselfConfig;
import com.kcangyan.usefulDemo.model.TestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@RestController
@Controller
public class HttpUrl {
    @GetMapping(value = "/")
    public @ResponseBody
    String HomePage() {
        return "你好！";
    }

    @GetMapping(value = "/relict")
    public String relict() {
        return "forward:/smallCalc";
    }

    @GetMapping(value = "/smallCalc")
    public String smallCalcPage() {
        return "smallCalcPage";
    }

    @GetMapping(value = "/druidLogin")
    public String druidLogin() {

        return "druidLogin";
    }

    @PostMapping(value = "/submitDruidLogin")//存储cookies值
    public @ResponseBody
    String submitDruidLogin(HttpServletRequest request, HttpServletResponse response) {
        //from-data数据获取
        try {
            String username = request.getParameter("loginUsername");
            String password = request.getParameter("loginPassword");
            //System.out.println(username);
            //System.out.println(password);
            Cookie cookieName = new Cookie("userName", username);
            Cookie cookiePwd = new Cookie("userPwd", password);
            cookiePwd.setMaxAge(10);
            cookieName.setMaxAge(10);
            response.addCookie(cookieName);
            response.addCookie(cookiePwd);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }

    @GetMapping(value = "/druidIndex")//cookies登陆验证
    public String druidIndex(HttpServletRequest request) {
        try {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].getValue());
                if (cookies[i].getName().equals("userName") && cookies[i].getValue().equals("admin")) {
                    System.out.println(cookies[i].getValue());
                    return "druidIndex";
                }
            }
            return "druidLogin";
        } catch (Exception e) {
            return "druidLogin";
        }
    }
    /*
    public @ResponseBody String submitDruidLogin(@ResponseBody Map jsonData){
        //json数据获取
        System.out.println(jsondata.toString());
        return "";
    }
     */

    @GetMapping(value = "/uploadpage")
    public String uploadPage(){
        return "ForBaiduOcr";
    }

}
