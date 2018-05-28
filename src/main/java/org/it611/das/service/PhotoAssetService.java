package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.PhotoVO;
import org.it611.das.vo.VedioVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface PhotoAssetService {

    JSONObject addPhoto(PhotoVO vo, HttpServletRequest request) throws Exception;

    HashMap<String, Object> selectPhotoList(HttpServletRequest request, int currentPage, int numberOfPages)throws IOException;

    HashMap selectPhotoDetailById(String id);
}
