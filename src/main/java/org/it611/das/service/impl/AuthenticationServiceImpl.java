package org.it611.das.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.service.AuthenticationService;
import org.it611.das.util.HttpClientUtil;
import org.it611.das.vo.DrivingLicenceVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private final String COUCHDB_QUERY_URL = "http://192.168.10.128:5984/mychannel/_find";

    @Override
    public HashMap<String, Object> authDrivingLicence(DrivingLicenceVO vo) throws Exception {

        HashMap<String, Object> resultMap = new HashMap();

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

        if(retStr == "" || retStr == null){//如果在区块链中查询不到
            resultMap.put("blockchainDataMap", "");
            resultMap.put("inputDataMap", vo);
        }

        //解析数据
        JsonNode jsonNode = objectMapper.readTree(retStr);
        JsonNode dataNode = jsonNode.findValue("data");

        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", vo);

        return resultMap;
    }
}
