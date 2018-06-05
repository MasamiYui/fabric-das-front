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
    public String frontLogin(){  return "frontLogin";}

    @RequestMapping("/user/commonUser")
    public String  commonUserhome(){ return "home_commonUser";}

    @RequestMapping("/user/companyUser")
    public String  companyUserhome(){ return "home_companyUser";}

    @RequestMapping(value = "/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping("/loginHome")
    public ModelAndView   loginHome(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        if(useType.equals("1")){
            modelAndView.addObject("loginName",userMap.get("name"));
            modelAndView.setViewName("index_userhome");
        }else if(useType.equals("2")){
            modelAndView.addObject("loginName",userMap.get("companyName"));
            modelAndView.setViewName("index_companyhome");
        }
        return modelAndView;

    }

}
