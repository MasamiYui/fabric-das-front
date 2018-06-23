package org.it611.das.control;

import org.it611.das.service.AuthenticationService;
import org.it611.das.vo.*;
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

    //驾驶证
    @RequestMapping("/auth/drivingLicenseForm")
    public ModelAndView authDrivingLicenseIndex(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("authentication_drivingLicenseForm");
        return modelAndView;
    }
    @RequestMapping(value = "/auth/drivingLicense")  //, method = RequestMethod.GET
    public ModelAndView doDrivingLicenseAuth(DrivingLicenceVO vo, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);

        HashMap<String, Object> resultMap = authenticationService.authDrivingLicence(vo);
        if (resultMap.get("blockchainDataMap").equals("NoAssert")) {
            modelAndView.addObject("resultMap", "对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }
        //resultMap 添加文件Hash
        modelAndView.addObject("resultMap", resultMap);
        modelAndView.setViewName("authentication_compareDrivingLicenceAssert");
        return modelAndView;

    }

    //学位证书
    @RequestMapping("/auth/certificateForm")
    public ModelAndView authenticatinoCertificate(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("authentication_certificateForm");
        return modelAndView;
    }
    @RequestMapping(value = "/auth/degreeCertificate")  //, method = RequestMethod.GET
    public ModelAndView doDegreeCertificateAuth(DegreeCertificateVO vo, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);

        HashMap<String, Object> resultMap = authenticationService.authDegreeCertificate(vo);
        if (resultMap.get("blockchainDataMap").equals("NoAssert")) {
            modelAndView.addObject("resultMap", "对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }
        //resultMap 添加文件Hash
        modelAndView.addObject("resultMap", resultMap);
        modelAndView.setViewName("authentication_compareCertificateAssert");
        return modelAndView;
    }


    //视频资产
    @RequestMapping("/auth/videoForm")
    public ModelAndView authVideoIndex(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("authentication_videotForm");
        return modelAndView;
    }

    @RequestMapping(value = "/auth/video")  //, method = RequestMethod.GET
    public ModelAndView doVideoAuth(VedioVO vo, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);

        HashMap<String, Object> resultMap = authenticationService.authVideo(vo);
        if (resultMap.get("blockchainDataMap").equals("NoAssert")) {
            modelAndView.addObject("resultMap", "对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }
        modelAndView.addObject("resultMap", resultMap);
        modelAndView.setViewName("authentication_compareVideoAssert");
        return modelAndView;

    }

    //实用新型专利
    @RequestMapping("/auth/syxxzlForm")
    public ModelAndView authenticatinosyxxzl(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("authentication_syxxzlForm");
        return modelAndView;
    }

    @RequestMapping(value = "/auth/syxxzl")  //, method = RequestMethod.GET
    public ModelAndView doSyxxzlAuth(SyxxzlVO vo, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);

        HashMap<String, Object> resultMap = authenticationService.authSyxxzl(vo);
        if (resultMap.get("blockchainDataMap").equals("NoAssert")) {
            modelAndView.addObject("resultMap", "对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }

        //resultMap 添加文件Hash
        modelAndView.addObject("resultMap", resultMap);
        modelAndView.setViewName("authentication_compareSyxxzlAssert");
        return modelAndView;
    }


    //图片资产
    @RequestMapping("/auth/photoForm")
    public ModelAndView authentiphoto(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("authentication_phototForm");
        return modelAndView;
    }

    @RequestMapping(value = "/auth/image")  //, method = RequestMethod.GET
    public ModelAndView doImageAuth(PhotoVO vo, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);

        HashMap<String, Object> resultMap = authenticationService.authImage(vo);
        if (resultMap.get("blockchainDataMap").equals("NoAssert")) {
            modelAndView.addObject("resultMap", "对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }
        modelAndView.addObject("resultMap", resultMap);
        modelAndView.setViewName("authentication_comparePhotoAssert");
        return modelAndView;
    }


    //音频资产
    @RequestMapping("/auth/audioForm")
    public ModelAndView authenticationAudio(HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);
        modelAndView.setViewName("authencation_musicForm");
        return modelAndView;
    }

    @RequestMapping(value = "/auth/audio")  //, method = RequestMethod.GET
    public ModelAndView doAudioAuth(MusicVO vo, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request, CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType = String.valueOf(userMap.get("userType"));
        jedis.close();
        modelAndView.addObject("useType", useType);

        HashMap<String, Object> resultMap = authenticationService.authAudio(vo);
        if (resultMap.get("blockchainDataMap").equals("NoAssert")) {
            modelAndView.addObject("resultMap", "对不起，不存在该资产");
            modelAndView.setViewName("authentication_noErrorAssert");
            return modelAndView;
        }
        modelAndView.addObject("resultMap", resultMap);
        modelAndView.setViewName("authentication_compareMusicAsset");
        return modelAndView;

    }

}