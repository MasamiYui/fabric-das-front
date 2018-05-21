package org.it611.das.service;


import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.DegreeCertificateVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AssetService {

    JSONObject addDegreeCertificate(DegreeCertificateVO vo, HttpServletRequest request) throws IOException;



}
