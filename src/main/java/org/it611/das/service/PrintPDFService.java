package org.it611.das.service;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PrintPDFService {

    String printDegreeCertification(String id) throws IOException, DocumentException;

}
