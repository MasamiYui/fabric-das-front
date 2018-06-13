package org.it611.das.service;


import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.SyxxzlVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface SyxxzlAssetService {

    JSONObject addSyxxzl(SyxxzlVO syxxzlVO, HttpServletRequest request) throws IOException;

    HashMap<String, Object> selectSyxxzlList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException;

    HashMap syxxzlDetail(String id) throws Exception;
}
