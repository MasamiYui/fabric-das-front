package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.AuthenticationService;
import org.it611.das.util.ResultUtil;
import org.it611.das.vo.DrivingLicenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/auth/degreeCertificate", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject doAuth(DrivingLicenceVO vo) throws Exception {


        HashMap<String, Object> resultMap = authenticationService.authDrivingLicence(vo);
        return ResultUtil.constructResponse(200, "ok", resultMap);
    }



}
