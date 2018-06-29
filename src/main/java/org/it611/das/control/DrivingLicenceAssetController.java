package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.DrivingLicenceService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.DrivingLicenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class DrivingLicenceAssetController {

    @Autowired
    private DrivingLicenceService drivingLicenceService;

    @RequestMapping(value = "/asset/drivingLicence", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addDrivingLicence(HttpServletRequest request, DrivingLicenceVO drivingLicenceVO) throws IOException {

        JSONObject resultData = drivingLicenceService.addDrivingLicence(drivingLicenceVO, request);
        return resultData;
    }

    //  返回学位证书申请表单
    @RequestMapping(value = "/asset/addDrivingLicence", method = RequestMethod.GET)
    public ModelAndView drivingLicenceInsert(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("insert_drivingLicenceAssert");
        return modelAndView;
    }

    //获取学位证书列表
    @RequestMapping(value = "/asset/drivingLicence/index", method = RequestMethod.GET)
    public ModelAndView drivingLicenceList(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("index_drivingLicenceAssetList");
        return modelAndView;
    }

    @RequestMapping("/asset/drivingLicences")
    @ResponseBody
    public JSONObject drivingLicenceList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = drivingLicenceService.selectDrivingLicenceList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    //获取学位证书记录详情
    @RequestMapping("/asset/drivingLicence/{id}")
    public ModelAndView drivingLicenceDetail(@PathVariable String id,HttpServletRequest request) throws Exception {
        HashMap record = drivingLicenceService.drivingLicenceDetail(id);
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        if(userToken == null || userToken.equals("")){
            jedis.close();
            modelAndView.addObject("loginName","***");
            modelAndView.addObject("record",record);
            modelAndView.setViewName("detail_drivingLicenceAssert");
            return modelAndView;
        }
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();
        modelAndView.addObject("loginName",loginName);

        modelAndView.addObject("record",record);
        modelAndView.setViewName("detail_drivingLicenceAssert");
        return modelAndView;
    }

}
