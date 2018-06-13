package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.VideoAssetService;
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
public class VideoAssetController {

    @Autowired
    private VideoAssetService videoAssetService;

    @RequestMapping(value = "/asset/video", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addVideo(HttpServletRequest request, VedioVO vedioVO) throws IOException {

        JSONObject resultData = videoAssetService.addVedio(vedioVO, request);
        return resultData;
    }


    @RequestMapping(value = "/asset/video/index", method = RequestMethod.GET)
    public ModelAndView videoIndex(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("index_videoAssetList");
        return modelAndView;
    }

    @RequestMapping(value = "/asset/videos", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject videoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = videoAssetService.selectVedioList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    @RequestMapping(value = "/asset/video/{id}", method = RequestMethod.GET)
    public ModelAndView videoDetail(@PathVariable String id,HttpServletRequest request) throws Exception {
        HashMap record = videoAssetService.selectVedioDetailById(id);
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();
        modelAndView.addObject("loginName",loginName);

        modelAndView.addObject("record",record);
        modelAndView.setViewName("detail_videoAssert");//修改为相应页面
        return modelAndView;
    }

    @RequestMapping(value = "/asset/addVideo", method = RequestMethod.GET)
    public ModelAndView videoForm(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("insert_videoAssert");
        return modelAndView;
    }

    //视频链接地址播放
    @RequestMapping(value = "/videoPalyLink", method = RequestMethod.GET)
    public ModelAndView videoPalyLink(String linkAddress){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("play_videoAndAudio");
        return modelAndView;
    }


    //视频二维码扫描结果
    @RequestMapping("/videoQrcodeScanResult")
    public ModelAndView QrcodeScanResult(String files){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("filesPath",files);
        modelAndView.setViewName("QRPhoneVideoList");
        return modelAndView;
    }
}
