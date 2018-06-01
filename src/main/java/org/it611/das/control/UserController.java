package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.UserService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    public UserService userService;


    @RequestMapping(value = "/user/login")
    @ResponseBody
    public JSONObject userLogin(@RequestParam("loginStr") String loginStr, @RequestParam("password")String password, HttpServletResponse response) throws Exception {

        JSONObject result = userService.userLogin(loginStr, password);

        if((int)result.get("code") == 400) {
            return result;
        }
        String token = ((Map)result.get("data")).get("token").toString();
        CookieUtil.addCookie(response,CookieUtil.COOKIE_TOKEN_KEY, token, CookieUtil.COOKIE_MAX_AGE);

        return result;
    }

    @RequestMapping(value = "/company/login")
    @ResponseBody
    public JSONObject companyLogin(@RequestParam("loginStr") String loginStr, @RequestParam("password")String password, HttpServletResponse response) throws JsonProcessingException {

        JSONObject result = userService.companyLogin(loginStr, password);
        if((int)result.get("code") == 400) {
            return result;
        }
        String token = result.get("data").toString();
        CookieUtil.addCookie(response,CookieUtil.COOKIE_TOKEN_KEY, token, CookieUtil.COOKIE_MAX_AGE);
        return result;
    }


    @RequestMapping(value = "/user/register")
    @ResponseBody
    public JSONObject userRegister(UserVO userVO){

        JSONObject result = userService.userRegister(userVO);
        return result;
    }

    @RequestMapping(value = "/company/register")
    @ResponseBody
    public JSONObject companyRegister(CompanyVO companyVO){

        JSONObject result = userService.companyRegister(companyVO);
        return result;
    }


    @RequestMapping(value = "/login/index")
    public ModelAndView loginIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }




    //测试用户注册====>test
    @RequestMapping("/userReg")
    public String userReg(){
        return "userRegistration";
    }
    //测试企业注册====>test
    @RequestMapping("/companyReg")
    public String companyReg(){
        return "companyRegistration";
    }
    //测试用户主页====>test
    @RequestMapping("/userHome")
    public ModelAndView userHome(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        String userJsonStr = RedisUtil.getJedis().get(token);
        ObjectMapper mapper = new ObjectMapper();
        HashMap userMap = mapper.readValue(userJsonStr, HashMap.class);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userHome");
        modelAndView.addObject(userMap);
        return modelAndView;
    }

}
