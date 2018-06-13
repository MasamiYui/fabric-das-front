package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.PhotoAssetService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class PhotoAssetController {

    @Autowired
    private PhotoAssetService photoAssetService;

    @RequestMapping(value = "/asset/image", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addVideo(HttpServletRequest request, PhotoVO photoVO) throws Exception {

        JSONObject resultData = photoAssetService.addPhoto(photoVO, request);
        return resultData;
    }


    @RequestMapping(value = "/asset/image/index", method = RequestMethod.GET)
    public ModelAndView videoIndex(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("index_photoAssetList");
        return modelAndView;
    }

    @RequestMapping(value = "/asset/images", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject videoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = photoAssetService.selectPhotoList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    @RequestMapping(value = "/asset/image/{id}", method = RequestMethod.GET)
    public String videoDetail(Model model, @PathVariable String id,HttpServletRequest request) throws Exception {


        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();
        model.addAttribute("loginName",loginName);

        HashMap record = photoAssetService.selectPhotoDetailById(id);
        model.addAttribute("record", record);
        return "detail_photoAssert";
    }

    @RequestMapping(value = "/asset/addImage", method = RequestMethod.GET)
    public ModelAndView videoForm(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("insert_phototAssert");

        return modelAndView;
    }


}
