package org.it611.das.service;

import org.it611.das.vo.*;

import java.util.HashMap;

public interface AuthenticationService {

    HashMap<String, Object> authDrivingLicence(DrivingLicenceVO vo) throws Exception;

    HashMap<String, Object> authDegreeCertificate(DegreeCertificateVO vo) throws Exception;

    HashMap<String, Object> authSyxxzl(SyxxzlVO vo) throws Exception;

    HashMap<String, Object> authAudio(MusicVO vo) throws Exception;

    HashMap<String, Object> authVideo(VedioVO vo) throws Exception;

    HashMap<String, Object> authImage(PhotoVO vo) throws Exception;

}
