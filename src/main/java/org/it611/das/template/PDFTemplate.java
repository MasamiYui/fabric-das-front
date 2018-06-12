package org.it611.das.template;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.it611.das.util.FileUtil;
import org.it611.das.util.QRUtil;

import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;


public class PDFTemplate {

    public static String generateDegreeCertification(HashMap degreeCertificateMap, String QRText) throws IOException, DocumentException {

        //学历证书模板路径
        String templatePath = PDFTemplate.class.getResource("/").getPath()+"pdf/"+"degreeCertificate.pdf";
        templatePath = templatePath.substring(1, templatePath.length());//TODO linux下可能不需要
        System.out.println(templatePath);

        // 生成的文件路径
        String targetPath = PDFTemplate.class.getResource("/").getPath()+"tmp/"+UUID.randomUUID().toString()+".pdf";//TODO：linux下路径可能需要修改
        targetPath = targetPath.substring(1, targetPath.length());

        // 书签名
        String fieldName = "image1";


        // 读取模板文件
        //InputStream input = new FileInputStream(new File(templatePath));//windwos
        InputStream input = new FileInputStream("/"+new File(templatePath));//linux
        PdfReader reader = new PdfReader(input);
        //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));//windows
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/"+targetPath));//linux

        //权限控制
        stamper.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        form.addSubstitutionFont(baseFont);

        //表单处理
        form.setField("assetType", "学位证书");
        form.setField("assetId", degreeCertificateMap.get("id").toString());
        form.setField("owner", degreeCertificateMap.get("ownerId").toString());
        form.setField("name", degreeCertificateMap.get("name").toString());
        form.setField("sex", degreeCertificateMap.get("sex").toString());
        form.setField("date", degreeCertificateMap.get("date").toString());
        form.setField("degreeConferringUnit", degreeCertificateMap.get("degreeConferringUnit").toString());
        form.setField("professional", degreeCertificateMap.get("professional").toString());
        form.setField("degreeName",degreeCertificateMap.get("degreeName").toString());
        form.setField("certId",degreeCertificateMap.get("certId").toString());
        form.setField("fileshash", degreeCertificateMap.get("filesHash").toString());
        form.setField("transactionId", degreeCertificateMap.get("transactionId").toString());
        form.setField("state", degreeCertificateMap.get("state").toString());
        form.setField("time", degreeCertificateMap.get("time").toString());
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        form.setField("year", String.valueOf(c.get(Calendar.YEAR)));
        form.setField("month", String.valueOf(c.get(Calendar.MONTH) + 1));
        form.setField("day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));



        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        //生成二维码
        String QRFilePath = QRUtil.GenerateQR(QRText);//生成QR，并且将temp文件保存到QRFilePath；

        // 读图片
        //Image image = Image.getInstance(QRFilePath);//windows
        Image image = Image.getInstance("/"+QRFilePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
        // 将图片提交到FastDFS获得一个URL
        //String fileName = UUID.randomUUID().toString();//暂定
        //String pdfFileUrl=FastDFSClient.saveFile(new File(targetPath));//将文件上传到fastDFS，返回http url
        FileUtil.deleteFile(QRFilePath);//删除本地临时图片 TODO 暂时不处理异常
        //FileUtil.deleteFile(targetPath);//删除本地临时PDF  TODO 暂时不处理异常
        return targetPath;
    }


    public static String generateMusic(HashMap MusicMap, String QRText) throws IOException, DocumentException {

        String templatePath = PDFTemplate.class.getResource("/").getPath()+"pdf/"+"videoAndAudio.pdf";
        templatePath = templatePath.substring(1, templatePath.length());//TODO linux下可能不需要
        System.out.println(templatePath);

        // 生成的文件路径
        String targetPath = PDFTemplate.class.getResource("/").getPath()+"tmp/"+UUID.randomUUID().toString()+".pdf";//TODO：linux下路径可能需要修改
        targetPath = targetPath.substring(1, targetPath.length());

        // 书签名
        String fieldName = "image1";


        // 读取模板文件
        InputStream input = new FileInputStream(new File("/"+templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/"+targetPath));

        //权限控制
        stamper.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        form.addSubstitutionFont(baseFont);

        //表单处理
        form.setField("assetType", "音频资产");
        form.setField("assetId", MusicMap.get("id").toString());
        form.setField("owner", MusicMap.get("ownerId").toString());
        form.setField("title",MusicMap.get("title").toString());
        form.setField("author",MusicMap.get("author").toString());
        form.setField("fileshash", MusicMap.get("filesHash").toString().replace(",","\n"));
        form.setField("transactionId", MusicMap.get("transactionId").toString());
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        form.setField("year", String.valueOf(c.get(Calendar.YEAR)));
        form.setField("month", String.valueOf(c.get(Calendar.MONTH) + 1));
        form.setField("day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        //生成二维码
        String QRFilePath = QRUtil.GenerateQR(QRText);//生成QR，并且将temp文件保存到QRFilePath；

        // 读图片
        Image image = Image.getInstance("/"+QRFilePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
        // 将图片提交到FastDFS获得一个URL
        //String fileName = UUID.randomUUID().toString();//暂定
        //String pdfFileUrl=FastDFSClient.saveFile(new File(targetPath));//将文件上传到fastDFS，返回http url
        FileUtil.deleteFile(QRFilePath);//删除本地临时图片 TODO 暂时不处理异常
        //FileUtil.deleteFile(targetPath);//删除本地临时PDF  TODO 暂时不处理异常
        return targetPath;
    }


    public static String generateVideo(HashMap videoMap, String QRText) throws IOException, DocumentException {

        String templatePath = PDFTemplate.class.getResource("/").getPath()+"pdf/"+"videoAndAudio.pdf";
        templatePath = templatePath.substring(1, templatePath.length());//TODO linux下可能不需要
        System.out.println(templatePath);

        // 生成的文件路径
        String targetPath = PDFTemplate.class.getResource("/").getPath()+"tmp/"+UUID.randomUUID().toString()+".pdf";//TODO：linux下路径可能需要修改
        targetPath = targetPath.substring(1, targetPath.length());

        // 书签名
        String fieldName = "image1";


        // 读取模板文件
        InputStream input = new FileInputStream(new File("/"+templatePath));//linux
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/"+targetPath));

        //权限控制
        stamper.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        form.addSubstitutionFont(baseFont);

        //表单处理
        form.setField("assetType", "视频资产");
        form.setField("assetId", videoMap.get("id").toString());
        form.setField("owner", videoMap.get("ownerId").toString());
        form.setField("title",videoMap.get("title").toString());
        form.setField("author",videoMap.get("author").toString());
        form.setField("fileshash", videoMap.get("filesHash").toString().replace(",","\n"));
        form.setField("transactionId", videoMap.get("transactionId").toString());
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        form.setField("year", String.valueOf(c.get(Calendar.YEAR)));
        form.setField("month", String.valueOf(c.get(Calendar.MONTH) + 1));
        form.setField("day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        //生成二维码
        String QRFilePath = QRUtil.GenerateQR(QRText);//生成QR，并且将temp文件保存到QRFilePath；

        // 读图片
        Image image = Image.getInstance("/"+QRFilePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
        // 将图片提交到FastDFS获得一个URL
        //String fileName = UUID.randomUUID().toString();//暂定
        //String pdfFileUrl=FastDFSClient.saveFile(new File(targetPath));//将文件上传到fastDFS，返回http url
        FileUtil.deleteFile(QRFilePath);//删除本地临时图片 TODO 暂时不处理异常
        //FileUtil.deleteFile(targetPath);//删除本地临时PDF  TODO 暂时不处理异常
        return targetPath;
    }


    public static String generateImage(HashMap videoMap, String QRText) throws IOException, DocumentException {

        String templatePath = PDFTemplate.class.getResource("/").getPath()+"pdf/"+"videoAndAudio.pdf";
        templatePath = templatePath.substring(1, templatePath.length());//TODO linux下可能不需要
        System.out.println(templatePath);

        // 生成的文件路径
        String targetPath = PDFTemplate.class.getResource("/").getPath()+"tmp/"+UUID.randomUUID().toString()+".pdf";//TODO：linux下路径可能需要修改
        targetPath = targetPath.substring(1, targetPath.length());

        // 书签名
        String fieldName = "image1";


        // 读取模板文件
        InputStream input = new FileInputStream(new File("/"+templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/"+targetPath));

        //权限控制
        stamper.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        form.addSubstitutionFont(baseFont);

        //表单处理
        form.setField("assetType", "图片资产");
        form.setField("assetId", videoMap.get("id").toString());
        form.setField("owner", videoMap.get("ownerId").toString());
        form.setField("title",videoMap.get("title").toString());
        form.setField("author",videoMap.get("author").toString());
        form.setField("fileshash", videoMap.get("filesHash").toString().replace(",","\n"));
        form.setField("transactionId", videoMap.get("transactionId").toString());
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        form.setField("year", String.valueOf(c.get(Calendar.YEAR)));
        form.setField("month", String.valueOf(c.get(Calendar.MONTH) + 1));
        form.setField("day", String.valueOf(c.get(Calendar.DAY_OF_MONTH)));

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        //生成二维码
        String QRFilePath = QRUtil.GenerateQR(QRText);//生成QR，并且将temp文件保存到QRFilePath；

        // 读图片
        Image image = Image.getInstance("/"+QRFilePath);
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(pageNo);
        // 根据域的大小缩放图片
        image.scaleToFit(signRect.getWidth(), signRect.getHeight());
        // 添加图片
        image.setAbsolutePosition(x, y);
        under.addImage(image);
        stamper.close();
        reader.close();
        // 将图片提交到FastDFS获得一个URL
        //String fileName = UUID.randomUUID().toString();//暂定
        //String pdfFileUrl=FastDFSClient.saveFile(new File(targetPath));//将文件上传到fastDFS，返回http url
        FileUtil.deleteFile(QRFilePath);//删除本地临时图片 TODO 暂时不处理异常
        //FileUtil.deleteFile(targetPath);//删除本地临时PDF  TODO 暂时不处理异常
        return targetPath;
    }


}
