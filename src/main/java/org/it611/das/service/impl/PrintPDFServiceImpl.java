package org.it611.das.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.domain.*;
import org.it611.das.mapper.DegreeCertificateMapper;
import org.it611.das.mapper.MusicMapper;
import org.it611.das.mapper.PhotoMapper;
import org.it611.das.mapper.VideoMapper;
import org.it611.das.service.PrintPDFService;
import org.it611.das.template.PDFTemplate;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.MapUtil;
import org.it611.das.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;

@Service
public class PrintPDFServiceImpl implements PrintPDFService {

    @Autowired
    private DegreeCertificateMapper degreeCertificateMapper;

    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String printDegreeCertification(String id, String ownerName) throws Exception {

        //HashMap dataMap = degreeCertificateMapper.selectCertDetailById(id);


        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<DegreeCertificate> resultData = mongoTemplate.find(query, DegreeCertificate.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        dataMap.put("ownerName", ownerName);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateDegreeCertification(dataMap, "www.baidu.com");

        //TODO 二次申请应该不需要重新生成

        return url;
    }

    @Override
    public String printVideo(String id, String ownerName) throws Exception {
        //HashMap dataMap = videoMapper.selectVideoDetailById(id);

        Criteria idCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(idCriteria);//条件查询
        List<Video> resultData = mongoTemplate.find(query, Video.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        dataMap.put("ownerName", ownerName);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateVideo(dataMap, "www.baidu.com");

        //TODO 二次申请应该不需要重新生成

        return url;
    }

    @Override
    public String printAudio(String id, String ownerName) throws Exception {
        //HashMap dataMap = musicMapper.selectMusicRecordById(id);

        Criteria idCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(idCriteria);//条件查询
        List<Music> resultData = mongoTemplate.find(query, Music.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        dataMap.put("ownerName", ownerName);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateMusic(dataMap, "www.baidu.com");

        //TODO 二次申请应该不需要重新生成

        return url;
    }

    @Override
    public String printPhoto(String id, String ownerName) throws Exception {
        //HashMap dataMap = photoMapper.selectPhotoRecordById(id);
        Criteria idCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(idCriteria);//条件查询
        List<Photo> resultData = mongoTemplate.find(query, Photo.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        dataMap.put("ownerName", ownerName);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateImage(dataMap, "www.baidu.com");

        return url;

    }

    @Override
    public String printDrivingLicence(String id, String ownerName) throws Exception {

        Criteria idCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(idCriteria);//条件查询
        List<DrivingLicence> resultData = mongoTemplate.find(query, DrivingLicence.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        dataMap.put("ownerName", ownerName);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateDrivingLicence(dataMap, "www.baidu.com");

        return url;
    }

    @Override
    public String printSyxxzl(String id, String ownerName) throws Exception {

        Criteria idCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(idCriteria);//条件查询
        List<Syxxzl> resultData = mongoTemplate.find(query, Syxxzl.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        dataMap.put("ownerName", ownerName);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateSyxxzl(dataMap, "www.baidu.com");

        return url;
    }


}
