package org.it611.das.service;


import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.DegreeCertificateVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface CertAssetService {

    JSONObject addDegreeCertificate(DegreeCertificateVO vo, HttpServletRequest request) throws IOException;

    HashMap<String, Object> selectCertAssetList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException;

    HashMap selCertDetail(String id) throws Exception;
}
