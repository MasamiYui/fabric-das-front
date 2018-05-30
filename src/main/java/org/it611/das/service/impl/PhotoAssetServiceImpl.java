package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.Photo;
import org.it611.das.service.PhotoAssetService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.PhotoVO;
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
public class PhotoAssetServiceImpl implements PhotoAssetService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public JSONObject addPhoto(PhotoVO vo, HttpServletRequest request) throws Exception {

        Photo photo = Vo2PoUtil.photoVo2Po(request, vo);
        try{
            mongoTemplate.insert(photo);
        }catch (Exception e){
            return ResultUtil.constructResponse(400, "insert photo failed.", null);
        }
        return ResultUtil.constructResponse(200, "ok", null);

    }

    @Override
    public HashMap<String, Object> selectPhotoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        Criteria ownerCriteria = Criteria.where("ownerId").is(userId);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, Photo.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<Photo> resultData = mongoTemplate.find(query, Photo.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;

    }

    @Override
    public HashMap selectPhotoDetailById(String id) throws Exception {

        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Photo> resultData = mongoTemplate.find(query, Photo.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        return dataMap;

    }


}
