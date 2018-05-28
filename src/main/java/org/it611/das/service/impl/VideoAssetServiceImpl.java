package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.it611.das.domain.Video;
import org.it611.das.mapper.VideoMapper;
import org.it611.das.service.VideoAssetService;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.VedioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class VideoAssetServiceImpl implements VideoAssetService {

    @Autowired
    private VideoMapper videoMapper;


    @Override
    @Transactional
    public JSONObject addVedio(VedioVO vo, HttpServletRequest request) throws IOException {

        Video video = Vo2PoUtil.videoVo2Po(request, vo);
        int result = videoMapper.addVideo(video);
        if (result>0){
            return ResultUtil.constructResponse(200, "ok", null);
        }
        return ResultUtil.constructResponse(400, "insert video failed.", null);
    }

    @Override
    public HashMap<String, Object> selectVedioList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {

        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(currentPage, numberOfPages);
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        List<HashMap> resultData = videoMapper.selectVideoList(userId);
        long total = videoMapper.selectVideoTotal(userId);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    @Override
    public HashMap selectVedioDetailById(String id) {
        HashMap dataMap = videoMapper.selectVideoDetailById(id);
        return dataMap;
    }
}
