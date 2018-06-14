package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.Syxxzl;
import org.it611.das.service.SyxxzlAssetService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.SyxxzlVO;
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
public class SyxxzlAssetServiceImpl implements SyxxzlAssetService {



    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JSONObject addSyxxzl(SyxxzlVO syxxzlVO, HttpServletRequest request) throws IOException {

        Syxxzl syxxzl = Vo2PoUtil.syxxzlVo2Po(request, syxxzlVO);
        try{
            mongoTemplate.insert(syxxzl);
        }catch (Exception e){
            return ResultUtil.constructResponse(400, "insert syxxzl failed.", null);
        }
        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public HashMap<String, Object> selectSyxxzlList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        Criteria ownerCriteria = Criteria.where("ownerId").is(userId);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, Syxxzl.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<Syxxzl> resultData = mongoTemplate.find(query, Syxxzl.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }


    @Override
    public HashMap syxxzlDetail(String id) throws Exception {
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Syxxzl> resultData = mongoTemplate.find(query, Syxxzl.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        return dataMap;
    }
}
