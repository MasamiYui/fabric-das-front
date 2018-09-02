package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.*;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.VedioVO;
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
public class AssetIdQueryController {

    @Autowired
    private AssertIdService assertIdService;

    @RequestMapping("/asset/queryId")
    public ModelAndView assertId( String assertId) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        HashMap<String, Object> data = assertIdService.selectAssertDetailById(assertId);
        Object result = data.get("data");
        String assetType = (String)data.get("type");

        if(result==null){
            modelAndView.addObject("errorInfo", "对不起，查找不到该资产");
            modelAndView.setViewName("detail_HomeAssertQueryError");
            return modelAndView;
        }

        modelAndView.addObject("record", result);
        modelAndView.addObject("type", assetType);
        modelAndView.setViewName("detail_HomeAssertQuery");
        return modelAndView;
    }



}
