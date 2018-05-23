package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.VedioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class VideoAssetController {

    @Autowired
    private VideoAssetService videoAssetService;

    @RequestMapping("/asset/video/add")
    @ResponseBody
    public JSONObject addVideo(HttpServletRequest request, VedioVO vedioVO) throws IOException {

        JSONObject resultData = videoAssetService.addVedio(vedioVO, request);
        return resultData;
    }


    @RequestMapping("/videoAssetListIndex")
    public String videoIndex(){return "videoAsset_list";}

    @RequestMapping("/videoAsset/videoList")
    @ResponseBody
    public JSONObject videoList(int currentPage, int numberOfPages) {

        HashMap<String, Object> data = videoAssetService.selectVedioList(currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    @RequestMapping("/videoDetail")
    @ResponseBody
    public ModelAndView videoDetail(String id){
        HashMap record = videoAssetService.selectVedioDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record",record);
        modelAndView.setViewName("videoDetail");//修改为相应页面
        return modelAndView;
    }

}
