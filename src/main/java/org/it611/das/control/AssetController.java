package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.AssetService;
import org.it611.das.vo.DegreeCertificateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    @RequestMapping("/asset/degreeCertification/add")
    @ResponseBody
    public JSONObject addDegreeCertification(HttpServletRequest request, DegreeCertificateVO certificateVO) throws IOException {

        JSONObject resultData = assetService.addDegreeCertificate(certificateVO, request);
        return resultData;
    }

    //     学历证书
    @RequestMapping("/certificateInsert")
    public String certificateInsert(){return "insert_certificate";}


}
