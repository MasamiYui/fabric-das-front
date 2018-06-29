package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.CertAssetService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.DegreeCertificateVO;
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
public class DegreeCertificateAssetController {

    @Autowired
    private CertAssetService certAssetService;

    @RequestMapping(value = "/asset/degreeCertification", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addDegreeCertification(HttpServletRequest request, DegreeCertificateVO certificateVO) throws IOException {

        JSONObject resultData = certAssetService.addDegreeCertificate(certificateVO, request);
        return resultData;
    }

    //  返回学位证书申请表单
    @RequestMapping(value = "/asset/addDegreeCertification", method = RequestMethod.GET)
    public ModelAndView certificateInsert(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("insert_certificateAssert");
        return modelAndView;
    }

    //获取学位证书列表
    @RequestMapping(value = "/asset/degreeCertification/index", method = RequestMethod.GET)
    public ModelAndView certList(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("index_certificateAssetList");
        return modelAndView;
    }

    @RequestMapping("/asset/degreeCertifications")
    @ResponseBody
    public JSONObject certificatetList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = certAssetService.selectCertAssetList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    //获取学位证书记录详情
    @RequestMapping("/asset/degreeCertification/{id}")
    public ModelAndView certDetail(@PathVariable String id,HttpServletRequest request) throws Exception {
        HashMap record = certAssetService.selCertDetail(id);
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        if(userToken == null || userToken.equals("")){
            jedis.close();
            modelAndView.addObject("loginName","***");
            modelAndView.addObject("record",record);
            modelAndView.setViewName("detail_certificateAssert");
            return modelAndView;
        }
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);

        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();
        modelAndView.addObject("loginName",loginName);

        modelAndView.addObject("record",record);
        modelAndView.setViewName("detail_certificateAssert");
        return modelAndView;
    }

}
