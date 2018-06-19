package org.it611.das.service;

import org.it611.das.vo.DrivingLicenceVO;

import java.util.HashMap;

public interface AuthenticationService {

    HashMap<String, Object> authDrivingLicence(DrivingLicenceVO vo) throws Exception;

}
