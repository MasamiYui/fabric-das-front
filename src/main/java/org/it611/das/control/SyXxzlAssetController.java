package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.SyxxzlAssetService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.SyxxzlVO;
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
public class SyXxzlAssetController {

    @Autowired
    private SyxxzlAssetService syxxzlAssetService;

    @RequestMapping(value = "/asset/syxxzl", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addSyxxzl(HttpServletRequest request, SyxxzlVO syxxzlVO) throws IOException {

        JSONObject resultData = syxxzlAssetService.addSyxxzl(syxxzlVO, request);
        return resultData;
    }

    //  返回学位证书申请表单
    @RequestMapping(value = "/asset/addSyxxzl", method = RequestMethod.GET)
    public ModelAndView syxxzlInsert(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("insert_syxxzl");
        return modelAndView;
    }

    //获取学位证书列表
    @RequestMapping(value = "/asset/syxxzl/index", method = RequestMethod.GET)
    public ModelAndView syxxzlList(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("index_syxxzlAssetList");
        return modelAndView;
    }

    @RequestMapping("/asset/syxxzls")
    @ResponseBody
    public JSONObject syxxzlList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = syxxzlAssetService.selectSyxxzlList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    //获取学位证书记录详情
    @RequestMapping("/asset/syxxzl/{id}")
    public ModelAndView syxxzlDetail(@PathVariable String id,HttpServletRequest request) throws Exception {
        HashMap record = syxxzlAssetService.syxxzlDetail(id);
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();
        modelAndView.addObject("loginName",loginName);

        modelAndView.addObject("record",record);
        modelAndView.setViewName("detail_syxxzlAssert");
        return modelAndView;
    }

}
