package com.kcangyan.usefulDemo.model;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器试验。");
        try {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                System.out.println(cookies[i].getValue());
                if (cookies[i].getName().equals("userName") && cookies[i].getValue().equals("admin")) {
                    System.out.println(cookies[i].getValue());
                    return true;
                }
            }
            response.sendRedirect("/druidLogin");
            return false;
        } catch (Exception e) {
            //e.printStackTrace();
            response.sendRedirect("/druidLogin");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
