package org.it611.das.service;


import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.DrivingLicenceVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface DrivingLicenceService {

    JSONObject addDrivingLicence(DrivingLicenceVO drivingLicenceVO, HttpServletRequest request) throws IOException;

    HashMap<String, Object> selectDrivingLicenceList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException;

    HashMap drivingLicenceDetail(String id) throws Exception;
}
