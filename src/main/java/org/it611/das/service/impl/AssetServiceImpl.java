package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.mapper.DegreeCertificateMapper;
import org.it611.das.service.AssetService;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.DegreeCertificateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private DegreeCertificateMapper degreeCertificateMapper;



    @Override
    public JSONObject addDegreeCertificate(DegreeCertificateVO vo, HttpServletRequest request) throws IOException {

        DegreeCertificate dc = Vo2PoUtil.degreeCertificateVo2Po(request, vo);
        int result = degreeCertificateMapper.addDegreeCertificate(dc);
        if (result>0){
            return ResultUtil.constructResponse(200, "ok", null);
        }
        return ResultUtil.constructResponse(400, "insert asset failed.", null);
    }
}
