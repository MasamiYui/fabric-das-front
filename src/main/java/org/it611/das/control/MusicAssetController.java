package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.MusicAssetService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.MusicVO;
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
public class MusicAssetController {

    @Autowired
    private MusicAssetService musicAssetService;

    //新增音频资产
    @RequestMapping(value = "/asset/audio", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject addMusic(HttpServletRequest request, MusicVO musicVO) throws IOException {

        JSONObject resultData = musicAssetService.addMusic(musicVO, request);
        return resultData;
    }

    //返回音频资产详情
    @RequestMapping(value = "/asset/audio/{id}", method = RequestMethod.GET)
    public String musicAssetDetail(Model model, @PathVariable String id,HttpServletRequest request) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();
        model.addAttribute("loginName", loginName);

        HashMap record = musicAssetService.selectMusicDetailById(id);
        model.addAttribute("record",record );
        return "detail_audioAssert";
    }


    //返回音频资产列表
    @RequestMapping(value = "/asset/audio/index", method = RequestMethod.GET)
    public ModelAndView musicAssetList(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("index_musicAssetList");
        return modelAndView;
    }

    @RequestMapping("/asset/audios")
    @ResponseBody
    public JSONObject musicList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String ,Object> result=musicAssetService.selectMusicAssetList(request, currentPage,numberOfPages);
        return ResponseUtil.constructResponse(200, "OK", result);
    }

    //返回音频资产申请表单
    @RequestMapping(value = "/asset/addAudio", method = RequestMethod.GET)
    public ModelAndView addMusic(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType",useType);
        modelAndView.setViewName("insert_musicAssert");
        return modelAndView;
    }


    //音频链接地址播放
    @RequestMapping("/musicPalyLink")
    public ModelAndView videoPalyLink(String linkAddress){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("play_videoAndAudio");
        return modelAndView;
    }

    //音二维码扫描结果
    @RequestMapping("/musicQrcodeScanResult")
    public ModelAndView QrcodeScanResult(String files){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("filesPath",files);
        modelAndView.setViewName("QRPhoneMusicList");
        return modelAndView;
    }

}
