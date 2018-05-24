package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.it611.das.domain.Music;
import org.it611.das.mapper.MusicMapper;
import org.it611.das.service.MusicAssetService;
import org.it611.das.util.ResultUtil;
import org.it611.das.util.Vo2PoUtil;
import org.it611.das.vo.MusicVO;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    @Transactional
    public JSONObject addMusic(MusicVO vo, HttpServletRequest request) throws IOException {

        Music music = Vo2PoUtil.musicVo2Po(request, vo);
        int result = musicMapper.addMusic(music);
        if (result>0){
            return ResultUtil.constructResponse(200, "ok", null);
        }
        return ResultUtil.constructResponse(400, "insert music failed.", null);
    }

    @Override
    public HashMap<String, Object> selectMusicAssetList(int currentPage, int numberOfPages) {
        HashMap<String, Object> dataMap = new HashMap<String,Object>();
        PageHelper.startPage(currentPage, numberOfPages);
        List<HashMap> rows=musicMapper.selectMusicAssertList();
        Long total=musicMapper.selectMusicAssertTotal();
        dataMap.put("rows", rows);
        dataMap.put("total", total);
        return dataMap;
    }

    @Override
    public HashMap selectMusicDetailById(String id) {

        HashMap record=musicMapper.selectMusicRecordById(id);
        return record;
    }

}
