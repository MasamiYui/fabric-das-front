package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.it611.das.domain.Photo;
import org.it611.das.mapper.PhotoMapper;
import org.it611.das.service.PhotoAssetService;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.UserQueryUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.PhotoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class PhotoAssetServiceImpl implements PhotoAssetService {

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    @Transactional
    public JSONObject addPhoto(PhotoVO vo, HttpServletRequest request) throws Exception {
        Photo photo = Vo2PoUtil.photoVo2Po(request, vo);
        int result = photoMapper.addPhoto(photo);
        if (result>0){
            return ResultUtil.constructResponse(200, "ok", null);
        }
        return ResultUtil.constructResponse(400, "insert video failed.", null);
    }

    @Override
    public HashMap<String, Object> selectPhotoList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {
        HashMap dataMap = new HashMap<String, Object>();
        PageHelper.startPage(currentPage, numberOfPages);
        String userId = UserQueryUtil.getUserIdByCookieAndRedis(request);
        List<HashMap> resultData = photoMapper.selectPhotoList(userId);
        long total = photoMapper.selectPhotoTotal(userId);
        dataMap.put("rows", resultData);
        dataMap.put("total", total);
        return dataMap;
    }

    @Override
    public HashMap selectPhotoDetailById(String id) {
        HashMap dataMap = photoMapper.selectPhotoDetailById(id);
        return dataMap;
    }


}
