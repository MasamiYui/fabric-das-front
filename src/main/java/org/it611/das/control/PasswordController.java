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
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PasswordController {

    @Autowired
    public UserService userService;

    @RequestMapping("/pass/index")
    public ModelAndView updatePassword(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        String userId = String.valueOf(userMap.get("id"));
        if(useType.equals("1")){   //1:表示普通用户
            modelAndView.addObject("loginName",userMap.get("name"));
        }else if(useType.equals("2")){  //2:企业用户
            modelAndView.addObject("loginName",userMap.get("companyName"));
        }
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("update_password");
        jedis.close();
        return modelAndView;
    }

    @RequestMapping(value = "/pass/update")
    @ResponseBody
    public JSONObject companyLogin(@RequestParam("userId") String id, String useType,String oldPass,String newPass) throws JsonProcessingException {

        JSONObject result = userService.updatePass(id, useType,oldPass,newPass);
        return result;
    }


}
