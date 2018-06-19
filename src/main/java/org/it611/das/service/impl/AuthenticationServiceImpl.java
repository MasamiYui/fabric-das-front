package org.it611.das.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.domain.*;
import org.it611.das.service.AuthenticationService;
import org.it611.das.util.HttpClientUtil;
import org.it611.das.util.MapUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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

        //HashMap<String, Object> inputDataMap = objectMapper.readValue(vo.toString(),HashMap.class);
        HashMap<String, Object> inputDataMap = MapUtil.convertToMap(vo);
        //上传文件的文件Hash
        Jedis client = RedisUtil.getJedis();
        String path = vo.getFiles();
        String filesHash = client.get(path);
        client.close();
        inputDataMap.put("filesHash", filesHash);



        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("drivingLicenceId").is(vo.getDrivingLicenceId());//查询条件为drivingLicence
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<DrivingLicence> drivingLicenceList = mongoTemplate.find(query, DrivingLicence.class);
        //通过size来判断是否有该资产
        if(drivingLicenceList == null || drivingLicenceList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = drivingLicenceList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //获取交易Hash
        String transactionHash = drivingLicenceList.get(0).getTransactionId();


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
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("transactionHash", transactionHash);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", inputDataMap);
        resultMap.put("transactionId", transactionHash);
        resultMap.put("state", state);

        return resultMap;
    }

    @Override
    public HashMap<String, Object> authDegreeCertificate(DegreeCertificateVO vo) throws Exception {

        HashMap<String, Object> resultMap = new HashMap();
        String state = "-1";

        //HashMap<String, Object> inputDataMap = objectMapper.readValue(vo.toString(),HashMap.class);
        HashMap<String, Object> inputDataMap = MapUtil.convertToMap(vo);
        //上传文件的文件Hash
        Jedis client = RedisUtil.getJedis();
        String path = vo.getFiles();
        String filesHash = client.get(path);
        client.close();
        inputDataMap.put("filesHash", filesHash);



        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("certId").is(vo.getCertId());
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<DegreeCertificate> degreeCertificateList = mongoTemplate.find(query, DegreeCertificate.class);
        //通过size来判断是否有该资产
        if(degreeCertificateList == null || degreeCertificateList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = degreeCertificateList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //获取交易Hash
        String transactionHash = degreeCertificateList.get(0).getTransactionId();



        String certIdId = vo.getCertId();


        //构造json参数
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", certIdId);
        map2.put("data.certId", map1);
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
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("transactionHash", transactionHash);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", inputDataMap);
        resultMap.put("transactionId", transactionHash);
        resultMap.put("state", state);

        return resultMap;
    }

    @Override
    public HashMap<String, Object> authSyxxzl(SyxxzlVO vo) throws Exception {


        HashMap<String, Object> resultMap = new HashMap();
        String state = "-1";

        //HashMap<String, Object> inputDataMap = objectMapper.readValue(vo.toString(),HashMap.class);
        HashMap<String, Object> inputDataMap = MapUtil.convertToMap(vo);
        //上传文件的文件Hash
        Jedis client = RedisUtil.getJedis();
        String path = vo.getFiles();
        String filesHash = client.get(path);
        client.close();
        inputDataMap.put("filesHash", filesHash);



        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("zlh").is(vo.getZlh());
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Syxxzl> syxxzlList = mongoTemplate.find(query, Syxxzl.class);
        //通过size来判断是否有该资产
        if(syxxzlList == null || syxxzlList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = syxxzlList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //获取交易Hash
        String transactionHash = syxxzlList.get(0).getTransactionId();


        String certIdId = vo.getZlh();


        //构造json参数
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", certIdId);
        map2.put("data.zlh", map1);
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
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("transactionHash", transactionHash);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", inputDataMap);
        resultMap.put("transactionId", transactionHash);
        resultMap.put("state", state);

        return resultMap;
    }

    @Override
    public HashMap<String, Object> authAudio(MusicVO vo) throws Exception {

        HashMap<String, Object> resultMap = new HashMap();
        String state = "-1";

        //HashMap<String, Object> inputDataMap = objectMapper.readValue(vo.toString(),HashMap.class);
        HashMap<String, Object> inputDataMap = MapUtil.convertToMap(vo);
        //上传文件的文件Hash
        Jedis client = RedisUtil.getJedis();
        String path = vo.getFiles();
        String filesHash = client.get(path);
        client.close();
        inputDataMap.put("filesHash", filesHash);



        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("filesHash").is(filesHash);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Music> audioList = mongoTemplate.find(query, Music.class);
        //通过size来判断是否有该资产
        if(audioList == null || audioList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = audioList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //获取交易Hash
        String transactionHash = audioList.get(0).getTransactionId();


        //String certIdId = vo.getZlh();


        //构造json参数
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", filesHash);
        map2.put("data.filesHash", map1);
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
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("transactionHash", transactionHash);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", inputDataMap);
        resultMap.put("transactionId", transactionHash);
        resultMap.put("state", state);

        return resultMap;
    }

    @Override
    public HashMap<String, Object> authVideo(VedioVO vo) throws Exception {

        HashMap<String, Object> resultMap = new HashMap();
        String state = "-1";

        //HashMap<String, Object> inputDataMap = objectMapper.readValue(vo.toString(),HashMap.class);
        HashMap<String, Object> inputDataMap = MapUtil.convertToMap(vo);
        //上传文件的文件Hash
        Jedis client = RedisUtil.getJedis();
        String path = vo.getFiles();
        String filesHash = client.get(path);
        client.close();
        inputDataMap.put("filesHash", filesHash);



        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("filesHash").is(filesHash);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Video> videoList = mongoTemplate.find(query, Video.class);
        //通过size来判断是否有该资产
        if(videoList == null || videoList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = videoList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //获取交易Hash
        String transactionHash = videoList.get(0).getTransactionId();


        //String certIdId = vo.getZlh();


        //构造json参数
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", filesHash);
        map2.put("data.filesHash", map1);
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
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("transactionHash", transactionHash);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", inputDataMap);
        resultMap.put("transactionId", transactionHash);
        resultMap.put("state", state);

        return resultMap;
    }

    @Override
    public HashMap<String, Object> authImage(PhotoVO vo) throws Exception {

        HashMap<String, Object> resultMap = new HashMap();
        String state = "-1";

        //HashMap<String, Object> inputDataMap = objectMapper.readValue(vo.toString(),HashMap.class);
        HashMap<String, Object> inputDataMap = MapUtil.convertToMap(vo);
        //上传文件的文件Hash
        Jedis client = RedisUtil.getJedis();
        String path = vo.getFiles();
        String filesHash = client.get(path);
        client.close();
        inputDataMap.put("filesHash", filesHash);



        //查看该资产的状态
        Criteria ownerCriteria = Criteria.where("filesHash").is(filesHash);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Photo> photoList = mongoTemplate.find(query, Photo.class);
        //通过size来判断是否有该资产
        if(photoList == null || photoList.size() == 0){
            //没有该资产
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //如果有多条资产相同（则是审核过程的问题，暂取第一条）
        state = photoList.get(0).getState();
        if(!state.equals("1")){
            //如果state不等于1,后面不需要进行，直接返回
            resultMap.put("blockchainDataMap", "NoAssert");
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("state", state);
            return  resultMap;
        }

        //获取交易Hash
        String transactionHash = photoList.get(0).getTransactionId();


        //String certIdId = vo.getZlh();


        //构造json参数
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", filesHash);
        map2.put("data.filesHash", map1);
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
            resultMap.put("inputDataMap", inputDataMap);
            resultMap.put("transactionHash", transactionHash);
            resultMap.put("state", state);
            return  resultMap;
        }
        //jsonNode转HashMap
        HashMap fabricDataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);//couchDB数据

        //传入进来的对象转HashMap
        //HashMap inputDataMap = objectMapper.readValue(objectMapper.writeValueAsString(vo), HashMap.class);

        resultMap.put("blockchainDataMap", fabricDataMap);
        resultMap.put("inputDataMap", inputDataMap);
        resultMap.put("transactionId", transactionHash);
        resultMap.put("state", state);

        return resultMap;
    }
}