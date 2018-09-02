package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.DrivingLicence;
import org.it611.das.service.DrivingLicenceService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.DrivingLicenceVO;
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
public class DrivingLicenceAssetImpl implements DrivingLicenceService {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JSONObject addDrivingLicence(DrivingLicenceVO drivingLicenceVO, HttpServletRequest request) throws IOException {

        DrivingLicence drivingLicence = Vo2PoUtil.drivingLicenceVo2Po(request, drivingLicenceVO);
        try{
            mongoTemplate.insert(drivingLicence);
        }catch (Exception e){
            return ResultUtil.constructResponse(400, "insert driving licence failed.", null);
        }
        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public HashMap<String, Object> selectDrivingLicenceList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        Criteria ownerCriteria = Criteria.where("ownerId").is(userId);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, DrivingLicence.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<DrivingLicence> resultData = mongoTemplate.find(query, DrivingLicence.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    //获取到学位证书详情
    @Override
    public HashMap drivingLicenceDetail(String id) throws Exception {
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<DrivingLicence> resultData = mongoTemplate.find(query, DrivingLicence.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        return dataMap;
    }
}
