package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.MusicVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface MusicAssetService {

    JSONObject addMusic(MusicVO vo, HttpServletRequest request) throws IOException;

    HashMap<String, Object> selectMusicAssetList(HttpServletRequest request,int currentPage, int numberOfPages) throws IOException;

    HashMap selectMusicDetailById(String id) throws Exception;
}