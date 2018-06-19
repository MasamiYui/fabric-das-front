package org.it611.das.control;

import org.it611.das.service.AuthenticationService;
import org.it611.das.vo.DrivingLicenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/auth/drivingLicense")  //, method = RequestMethod.GET
    public ModelAndView doAuth(DrivingLicenceVO vo,HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);

        HashMap<String, Object> resultMap = authenticationService.authDrivingLicence(vo);
        if(resultMap.get("blockchainDataMap").equals("NoAssert")){
            modelAndView.addObject("resultMap","对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }
        //resultMap 添加文件Hash
        modelAndView.addObject("resultMap",resultMap);
        modelAndView.setViewName("authentication_comparedrivingLicenceAssert");
        return modelAndView;

    }


    @RequestMapping("/auth/drivingLicenseForm")
    public ModelAndView  authenticatinoDrivingLicense(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("authentication_drivingLicenseAssert");
        return modelAndView;
    }

    //学位证书
    @RequestMapping("/auth/certificateForm")
    public ModelAndView  authenticatinoCertificate(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("authentication_certificateAssert");
        return modelAndView;
    }



}