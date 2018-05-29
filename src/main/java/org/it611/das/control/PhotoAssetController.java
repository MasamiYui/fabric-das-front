package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.PhotoAssetService;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.PhotoVO;
import org.it611.das.vo.VedioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class PhotoAssetController {

    @Autowired
    private PhotoAssetService photoAssetService;

    @RequestMapping("/asset/photo/add")
    @ResponseBody
    public JSONObject addVideo(HttpServletRequest request, PhotoVO photoVO) throws Exception {

        JSONObject resultData = photoAssetService.addPhoto(photoVO, request);
        return resultData;
    }


    @RequestMapping("/photoAssetListIndex")
    public String videoIndex(){return "photoAsset_list";}

    @RequestMapping("/photoAsset/photoList")
    @ResponseBody
    public JSONObject videoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = photoAssetService.selectPhotoList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    @RequestMapping("/photoDetail")
    public String videoDetail(Model model,String id){
        HashMap record = photoAssetService.selectPhotoDetailById(id);
        model.addAttribute("record", record);
        return "photoDetail";
       /* ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record",record);
        modelAndView.setViewName("photoDetail");
        return modelAndView;*/
    }

    @RequestMapping("/photoForm")
    public String videoForm(){return "insert_phototAssert";}


}
