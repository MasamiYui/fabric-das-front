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
public class PhotoAssetController {

    @Autowired
    private VideoAssetService videoAssetService;

    @RequestMapping("/asset/photo/add")
    @ResponseBody
    public JSONObject addVideo(HttpServletRequest request, VedioVO vedioVO) throws IOException {

        JSONObject resultData = videoAssetService.addVedio(vedioVO, request);
        return resultData;
    }


    @RequestMapping("/photoAssetListIndex")
    public String videoIndex(){return "videoAsset_list";}

    @RequestMapping("/photoAsset/photoList")
    @ResponseBody
    public JSONObject videoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = videoAssetService.selectVedioList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    @RequestMapping("/photoDetail")
    @ResponseBody
    public ModelAndView videoDetail(String id){
        HashMap record = videoAssetService.selectVedioDetailById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record",record);
        modelAndView.setViewName("videoDetail");//修改为相应页面
        return modelAndView;
    }

    @RequestMapping("/photoForm")
    public String videoForm(){return "insert_videoAssert";}

   /* //视频链接地址播放
    @RequestMapping("/videoPalyLink")
    public ModelAndView videoPalyLink(String linkAddress){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("videoDetailPlay");
        return modelAndView;
    }

    //视频二维码扫描结果
    @RequestMapping("/videoQrcodeScanResult")
    public ModelAndView QrcodeScanResult(String files){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("filesPath",files);
        modelAndView.setViewName("QRPhoneVideoList");
        return modelAndView;
    }*/
}
