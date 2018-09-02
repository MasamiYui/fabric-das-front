package org.it611.das.control;


import org.it611.das.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginOutController {

    @RequestMapping(value = "/loginOut")
    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        CookieUtil.removeCookie(request,response,CookieUtil.COOKIE_TOKEN_KEY);
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
