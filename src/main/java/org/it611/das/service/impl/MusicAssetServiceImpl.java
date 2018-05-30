package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.it611.das.domain.Music;
import org.it611.das.domain.Photo;
import org.it611.das.mapper.MusicMapper;
import org.it611.das.service.MusicAssetService;
import org.it611.das.util.MapUtil;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.MusicVO;
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
public class MusicAssetServiceImpl implements MusicAssetService {

    @Autowired
    private MusicMapper musicMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Transactional
    public JSONObject addMusic(MusicVO musicVO, HttpServletRequest request) throws IOException {

        Music music = Vo2PoUtil.musicVo2Po(request, musicVO);
        try{
            mongoTemplate.insert(music);
        }catch (Exception e){
            return ResultUtil.constructResponse(400, "insert audio failed.", null);
        }
        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public HashMap<String, Object> selectMusicAssetList(HttpServletRequest request,int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        Criteria ownerCriteria = Criteria.where("ownerId").is(userId);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        long total = mongoTemplate.count(query, Music.class);//查询总数
        query.skip((currentPage - 1) * numberOfPages).limit(numberOfPages);//分页查询
        List<Music> resultData = mongoTemplate.find(query, Music.class);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    @Override
    public HashMap selectMusicDetailById(String id) throws Exception {

        Criteria ownerCriteria = Criteria.where("id").is(id);
        Query query = new Query();
        query.addCriteria(ownerCriteria);//条件查询
        List<Music> resultData = mongoTemplate.find(query, Music.class);
        HashMap dataMap = MapUtil.convertToMap(resultData.get(0));
        return dataMap;
    }

}
