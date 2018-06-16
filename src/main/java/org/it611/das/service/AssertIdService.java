package org.it611.das.service;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.vo.VedioVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public interface AssertIdService {

    HashMap selectAssertDetailById(String id) throws Exception;
}
