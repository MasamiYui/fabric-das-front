package org.it611.das.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class LoginController {
    @RequestMapping("/frontLogin")
    public String frontLogin(){  return "login";}

    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping("/home")
    public ModelAndView   loginHome(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        if(useType.equals("1")){   //1:表示普通用户
            modelAndView.addObject("loginName",userMap.get("name"));
            modelAndView.addObject("useType",useType);
            modelAndView.setViewName("home_userIndex");
        }else if(useType.equals("2")){  //2:企业用户
            modelAndView.addObject("loginName",userMap.get("companyName"));
            modelAndView.addObject("useType",useType);
            modelAndView.setViewName("home_companyIndex");
        }
        jedis.close();
        return modelAndView;

    }

}
