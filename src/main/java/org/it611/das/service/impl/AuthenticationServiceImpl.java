package org.it611.das.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.domain.DrivingLicence;
import org.it611.das.service.AuthenticationService;
import org.it611.das.util.HttpClientUtil;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.DrivingLicenceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String COUCHDB_QUERY_URL = "http://192.168.10.128:5984/mychannel/_find";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public HashMap<String, Object> authDrivingLicence(DrivingLicenceVO vo) throws Exception {

        HashMap<String, Object> resultMap = new HashMap();
        String state = "-1";


        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("drivingLicenceId").is(vo.getDrivingLicenceId());//查询条件为drivingLicence
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<DrivingLicence> drivingLicenceList = mongoTemplate.find(query, DrivingLicence.class);
        //通过size来判断是否有该资产
        if(drivingLicenceList == null || drivingLicenceList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", vo);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = drivingLicenceList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", vo);
            resultMap.put("state", state);
            return  resultMap;
        }


        //获取DrivingLicenceId
        String drivinglicenceId = vo.getDrivingLicenceId();

        //构造json参数
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", drivinglicenceId);
        map2.put("data.drivingLicenceId", map1);
        map3.put("selector", map2);


        String jsonStr = objectMapper.writeValueAsString(map3);

        //调用CouchDB HTTP API，获取fabric的state数据库
        String retStr = HttpClientUtil.doPostJson(COUCHDB_QUERY_URL, jsonStr);
        System.out.println("retStr:"+retStr);
        //解析数据
        JsonNode jsonNode = objectMapper.readTree(retStr);
        JsonNode dataNode = jsonNode.findValue("data");

        if(dataNode == null){
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", vo);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", vo);
        resultMap.put("state", state);
        return resultMap;
    }
}