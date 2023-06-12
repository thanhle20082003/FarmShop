package com.web.farm.FarmShop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SiteAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("pre handle of request" +request.getRequestURI());

        //kiểm tra username có tồn tại trong session không
        // -> có tồn tại
        if (session.getAttribute("customer") != null) {
            return true;
        }


        //thiết lập biến redirect-uri ở trong session
        session.setAttribute("redirect-uri", request.getRequestURI());
        System.out.println("Ruri slogin: " + session.getAttribute("redirect-uri"));

        //yêu cầu login
        response.sendRedirect("/slogin");

        //k tiếp tục thực hiện yêu cầu
        // -> phải login
        return false;
    }

}
