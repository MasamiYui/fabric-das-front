package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.domain.Video;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
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
public class VideoAssetServiceImpl implements VideoAssetService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional
    @Override
    public JSONObject addVedio(VedioVO vedioVO, HttpServletRequest request) throws IOException {

        Video video = Vo2PoUtil.videoVo2Po(request, vedioVO);
        try{
            mongoTemplate.insert(video);
        }catch (Exception e){
            return ResultUtil.constructResponse(400, "insert video failed.", null);
        }
        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public HashMap<String, Object> selectVedioList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        Criteria ownerCriteria = Criteria.where("ownerId").is(userId);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, Video.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<Video> resultData = mongoTemplate.find(query, Video.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    @Override
    public HashMap selectVedioDetailById(String id) throws Exception {

        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Video> resultData = mongoTemplate.find(query, Video.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        return dataMap;
    }
}
