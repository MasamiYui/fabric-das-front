package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.PhotoAssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String videoIndex(){

        return "index_photoAssetList";
    }

    @RequestMapping(value = "/asset/images", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject videoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = photoAssetService.selectPhotoList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }


    @RequestMapping(value = "/asset/image/{id}", method = RequestMethod.GET)
    public String videoDetail(Model model, @PathVariable String id) throws Exception {

        HashMap record = photoAssetService.selectPhotoDetailById(id);
        model.addAttribute("record", record);
        return "detail_photoAssert";
    }

    @RequestMapping(value = "/asset/addImage", method = RequestMethod.GET)
    public String videoForm(){

        return "insert_phototAssert";
    }


}
