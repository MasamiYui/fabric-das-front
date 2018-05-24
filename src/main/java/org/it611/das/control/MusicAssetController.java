package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.MusicAssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.MusicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class MusicAssetController {

    @Autowired
    private MusicAssetService musicAssetService;

    @RequestMapping("/asset/music/add")
    @ResponseBody
    public JSONObject addMusic(HttpServletRequest request, MusicVO musicVO) throws IOException {

        JSONObject resultData = musicAssetService.addMusic(musicVO, request);
        return resultData;
    }

    @RequestMapping("/musicAsset/musicList")
    @ResponseBody
    public JSONObject musicList(int currentPage, int numberOfPages) {

        HashMap<String, Object> data = musicAssetService.selectMusicList(currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    @RequestMapping("/musicDetail")
    public ModelAndView musicDetail(String id){
        HashMap record = musicAssetService.selectMusicDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record",record);
        modelAndView.setViewName("musicDetail");//修改为相应页面
        return modelAndView;
    }

}
