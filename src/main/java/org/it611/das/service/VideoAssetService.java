package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.VedioVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface VideoAssetService {

    JSONObject addVedio(VedioVO vo, HttpServletRequest request) throws IOException;

    HashMap<String, Object> selectVedioList(HttpServletRequest request,int currentPage, int numberOfPages)throws IOException;

    HashMap selectVedioDetailById(String id);
}
