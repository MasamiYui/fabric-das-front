package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.mapper.DegreeCertificateMapper;
import org.it611.das.service.CertAssetService;
import org.it611.das.util.*;
import org.it611.das.vo.DegreeCertificateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class CertAssetServiceImpl implements CertAssetService {

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

    @Override
    public HashMap<String, Object> selectCertAssetList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {
        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(currentPage, numberOfPages);

        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        List<HashMap> resultData = degreeCertificateMapper.selectCertAssetList(userId);
        long total = degreeCertificateMapper.selectCertTotal(userId);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    //获取到学位证书详情
    @Override
    public HashMap selCertDetail(String id) {
        HashMap dataMap=degreeCertificateMapper.selectCertDetailById(id);
        /*
        String QRcode = IdUtil.getQrCode();
        dataMap.put("QRcode",QRcode);
        */
        return dataMap;
    }
}
