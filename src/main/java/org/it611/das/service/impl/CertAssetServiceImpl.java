package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.service.CertAssetService;
import org.it611.das.util.*;
import org.it611.das.vo.DegreeCertificateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class CertAssetServiceImpl implements CertAssetService {



    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JSONObject addDegreeCertificate(DegreeCertificateVO degreeCertificateVO, HttpServletRequest request) throws IOException {

        DegreeCertificate degreeCertificate = Vo2PoUtil.degreeCertificateVo2Po(request, degreeCertificateVO);
        try{
            mongoTemplate.insert(degreeCertificate);
        }catch (Exception e){
            return ResultUtil.constructResponse(400, "insert degreeCertificate failed.", null);
        }
        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public HashMap<String, Object> selectCertAssetList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        Criteria ownerCriteria = Criteria.where("ownerId").is(userId);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, DegreeCertificate.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<DegreeCertificate> resultData = mongoTemplate.find(query, DegreeCertificate.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    //获取到学位证书详情
    @Override
    public HashMap selCertDetail(String id) throws Exception {
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<DegreeCertificate> resultData = mongoTemplate.find(query, DegreeCertificate.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        return dataMap;
    }
}
