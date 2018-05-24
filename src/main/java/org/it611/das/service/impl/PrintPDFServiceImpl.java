package org.it611.das.service.impl;

import com.itextpdf.text.DocumentException;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.mapper.DegreeCertificateMapper;
import org.it611.das.service.PrintPDFService;
import org.it611.das.template.PDFTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class PrintPDFServiceImpl implements PrintPDFService {

    @Autowired
    private DegreeCertificateMapper degreeCertificateMapper;

    @Override
    public String printDegreeCertification(String id) throws IOException, DocumentException {

        HashMap dataMap = degreeCertificateMapper.selectCertDetailById(id);

        //首次生成调用PDF生成工具生成
        String url = PDFTemplate.generateDegreeCertification(dataMap, "www.baidu.com");

        //TODO 二次申请应该不需要重新生成

        return url;
    }
}
