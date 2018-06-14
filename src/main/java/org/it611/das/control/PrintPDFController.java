package org.it611.das.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import org.it611.das.service.PrintPDFService;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.FileUtil;
import org.it611.das.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;


@Controller
public class PrintPDFController {

    @Autowired
    private PrintPDFService printPDFService;


    //  学历资产输出接口   http://localhost:8086/pdfPrint/degreeCertificate?id=20180521165335145310
    @RequestMapping("/pdfPrint/degreeCertificate")
    public void printDegreeCertificatePDF(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();


        String pdfFilePath = printPDFService.printDegreeCertification(id, loginName);

        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        //pdf = new File("/"+pdfFilePath);//windows
        pdf = new File("/"+pdfFilePath);//linux
        response.setContentLength((int) pdf.length());
        //fis = new FileInputStream("/"+pdf);//windows
        fis = new FileInputStream("/"+pdf);//linux
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        //FileUtil.deleteFile(pdfFilePath);//删除临时文件 windows
        FileUtil.deleteFile("/"+pdfFilePath);//删除临时文件 linux
    }



    @RequestMapping("/pdfPrint/video")
    public void printVideoPDF(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();

        String pdfFilePath = printPDFService.printVideo(id, loginName);

        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File("/"+pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile("/"+pdfFilePath);//删除临时文件
    }


    @RequestMapping("/pdfPrint/audio")
    public void printAudioPDF(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();

        String pdfFilePath = printPDFService.printAudio(id, loginName);

        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File("/"+pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile("/"+pdfFilePath);//删除临时文件
    }

    //打印图片资产验证报告
    @RequestMapping("/pdfPrint/photo")
    public void printPhotoPDF(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();

        String pdfFilePath = printPDFService.printPhoto(id, loginName);
        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File("/"+pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile("/"+pdfFilePath);//删除临时文件
    }

    @RequestMapping("/pdfPrint/drivingLicence")
    public void printDrivingLicencePDF(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();

        String pdfFilePath = printPDFService.printDrivingLicence(id, loginName);
        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File("/"+pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile("/"+pdfFilePath);//删除临时文件
    }


    @RequestMapping("/pdfPrint/syxxzl")
    public void printSyxxzlPDF(String id,HttpServletRequest request, HttpServletResponse response) throws Exception {

        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String loginName= String.valueOf(userMap.get("name"));
        jedis.close();

        String pdfFilePath = printPDFService.printSyxxzl(id, loginName);
        //设置响应内容类型为PDF类型
        response.setContentType("application/pdf");
        ServletOutputStream sos = response.getOutputStream();
        File pdf = null;
        FileInputStream fis = null;
        byte[] buffer = new byte[1024*1024];
        pdf = new File("/"+pdfFilePath);
        response.setContentLength((int) pdf.length());
        fis = new FileInputStream(pdf);
        int readBytes = -1;
        while((readBytes = fis.read(buffer, 0, 1024*1024)) != -1){
            sos.write(buffer, 0, 1024*1024);
        }
        sos.close();
        fis.close();
        FileUtil.deleteFile("/"+pdfFilePath);//删除临时文件
    }

}
