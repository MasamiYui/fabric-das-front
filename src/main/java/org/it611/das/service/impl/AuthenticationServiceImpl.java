package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.domain.*;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.service.AuthenticationService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public JSONObject selectDegreeCertificationDetailById(String id) throws InvalidArgumentException, NoSuchAlgorithmException, IOException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {

            DegreeCertificate degreeCertificate= mongoTemplate.find(query, DegreeCertificate.class).get(0);
            mongoDataMap = MapUtil.convertToMap(degreeCertificate);

//            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, DegreeCertificate.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject selectDrivingLicenceDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {
            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, DrivingLicence.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject selectSyxxzlDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {
            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, Syxxzl.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject selectImageDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {
            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, Photo.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject selectAudioDetailById(String id) throws IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {
            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, Music.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }

    @Override
    public JSONObject selectVideoDetailById(String id) throws IOException, InvalidArgumentException, NoSuchAlgorithmException, NoSuchProviderException, TransactionException, ProposalException, CryptoException, InvalidKeySpecException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        HashMap<String, Object> resultMap = new HashMap();
        HashMap mongoDataMap = null;
        HashMap<String, Object> fabricDataMap = null;
        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        try {
            mongoDataMap = MapUtil.convertToMap(mongoTemplate.find(query, Video.class).get(0));
        } catch (Exception e) {
            return ResponseUtil.constructResponse(400, "query database failed.", resultMap);
        }

        if (mongoDataMap.get("state").equals("0")) {//如果是未审核状态
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        ChaincodeManager manager = FabricManager.obtain().getManager();
        String[] argQuery = new String[]{id};
        //如果是已审核状态，表明已有资产信息录入到区块链中
        Map<String, String> queryData = manager.query("query", argQuery);
        String resultState = queryData.get("code");

        String dataJson = queryData.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!resultState.equals("success")) {//如果向区块链请求发生错误
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", fabricDataMap);//fabricDataMap==null
            return ResponseUtil.constructResponse(400, "query blockchain failed.", resultMap);
        }
        if("".equals(dataJson)){
            resultMap.put("mongoData", mongoDataMap);
            resultMap.put("fabricData", MapUtil.setMapValue(mongoDataMap));
            return ResponseUtil.constructResponse(200, "ok", resultMap);
        }
        fabricDataMap = objectMapper.readValue(dataJson, HashMap.class);
        resultMap.put("mongoData", mongoDataMap);
        resultMap.put("fabricData", fabricDataMap);
        return ResponseUtil.constructResponse(200, "ok", resultMap);
    }
}
