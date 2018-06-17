package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.bson.types.ObjectId;
import org.it611.das.domain.Video;
import org.it611.das.service.AssertIdService;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.*;
import org.it611.das.vo.VedioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class AssertIdServiceImpl implements AssertIdService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public HashMap selectAssertDetailById(String assertId) throws Exception {
        HashMap dataResult = new HashMap();
        String type = AssertType.queryType(assertId);
        if(type.equals("errorType")){
            dataResult.put("data",null);
            dataResult.put("type","errorType");
            return dataResult;
        }
        Class assertClass=AssertType.queryAssertType(assertId);

        if(assertClass==null){
            dataResult.put("data",null);
            dataResult.put("type",type);
            return dataResult;
        }
        Object object= mongoTemplate.findById(assertId, assertClass);
        dataResult.put("data",object);
        dataResult.put("type",type);
        return dataResult;
    }
}
