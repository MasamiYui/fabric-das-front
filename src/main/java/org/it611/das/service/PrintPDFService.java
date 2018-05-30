package org.it611.das.service;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PrintPDFService {

    String printDegreeCertification(String id) throws Exception;

    String printVideo(String id) throws Exception;

    String printAudio(String id) throws Exception;

    String printPhoto(String id) throws Exception;

}
