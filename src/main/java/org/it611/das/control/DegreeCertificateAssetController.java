package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.CertAssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.DegreeCertificateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class DegreeCertificateAssetController {

    @Autowired
    private CertAssetService certAssetService;

    @RequestMapping("/asset/degreeCertification/add")
    @ResponseBody
    public JSONObject addDegreeCertification(HttpServletRequest request, DegreeCertificateVO certificateVO) throws IOException {

        JSONObject resultData = certAssetService.addDegreeCertificate(certificateVO, request);
        return resultData;
    }

    //  返回学位证书申请表单
    @RequestMapping("/certificateInsert")
    public String certificateInsert() {
        return "insert_certificate";
    }

    //获取学位证书列表
    @RequestMapping("/certList")
    public String certList(){return "certificateAsset_list";}

    @RequestMapping("/certficateAsset/certList")
    @ResponseBody
    public JSONObject certificatetList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap<String, Object> data = certAssetService.selectCertAssetList(request, currentPage, numberOfPages);
        return ResponseUtil.constructResponse(200, "ok", data);
    }

    //获取学位证书记录详情
    @RequestMapping("/certDetail")
    public ModelAndView certDetail(String recordId){
        HashMap record = certAssetService.selCertDetail(recordId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record",record);
        modelAndView.setViewName("certDetail");
        return modelAndView;
    }


}
