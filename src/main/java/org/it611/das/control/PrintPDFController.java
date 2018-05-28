package org.it611.das.control;

import com.itextpdf.text.DocumentException;
import org.it611.das.service.PrintPDFService;
import org.it611.das.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Controller
public class PrintPDFController {

    @Autowired
    private PrintPDFService printPDFService;


    //  学历资产输出接口   http://localhost:8086/pdfPrint/degreeCertificate?id=20180521165335145310
    @RequestMapping("/pdfPrint/degreeCertificate")
    public void printDegreeCertificatePDF(String id, HttpServletResponse response) throws IOException, DocumentException {

        String pdfFilePath = printPDFService.printDegreeCertification(id);

        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File(pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile(pdfFilePath);//删除临时文件
    }


    @RequestMapping("/pdfPrint/video")
    public void printVideoPDF(String id, HttpServletResponse response) throws IOException, DocumentException {

        String pdfFilePath = printPDFService.printVideo(id);

        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File(pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile(pdfFilePath);//删除临时文件
    }

    //  学历资产输出接口   http://localhost:8086/pdfPrint/degreeCertificate?id=20180521165335145310
    @RequestMapping("/pdfPrint/audio")
    public void printAudioPDF(String id, HttpServletResponse response) throws IOException, DocumentException {

        String pdfFilePath = printPDFService.printAudio(id);

        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File(pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile(pdfFilePath);//删除临时文件
    }


}
