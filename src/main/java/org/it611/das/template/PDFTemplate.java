package org.it611.das.template;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.it611.das.fastdfs.FastDFSClient;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;


public class PDFTemplate {

    public static String generateDegreeCertification(String templatePath, HashMap degreeCertificateMap) throws IOException, DocumentException {

        // 生成的文件路径
       // String targetPath = "C:\\Users\\YUI\\Desktop\\result3.pdf";
        String targetPath = PDFTemplate.class.getResource("/").getPath()+"tmp/";//TODO：linux下路径可能需要修改（到时记得修改）

        System.out.println(targetPath);
        // 书签名
        String fieldName = "image1";

        // 图片路径
        String imagePath = PDFTemplate.class.getResource("/").getPath()+"pdf/"+"degreeCertificate.pdf";

        // 读取模板文件
        InputStream input = new FileInputStream(new File(templatePath));
        PdfReader reader = new PdfReader(input);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetPath));

        //权限控制
        stamper.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
        // 提取pdf中的表单
        AcroFields form = stamper.getAcroFields();
        BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        form.addSubstitutionFont(baseFont);

        //表单处理
        form.setField("assetId", degreeCertificateMap.get("assetId").toString());
        form.setField("owner", degreeCertificateMap.get("owner").toString());
        form.setField("name", degreeCertificateMap.get("name").toString());
        form.setField("sex", degreeCertificateMap.get("sex").toString());
        form.setField("date", degreeCertificateMap.get("date").toString());
        form.setField("degreeConferringUnit", degreeCertificateMap.get("degreeConferringUnit").toString());
        form.setField("professional", degreeCertificateMap.get("professional").toString());
        form.setField("degreeName",degreeCertificateMap.get("degreeName").toString());
        form.setField("certId",degreeCertificateMap.get("certId").toString());
        form.setField("fileshash", degreeCertificateMap.get("fileshash").toString());
        form.setField("transactionId", degreeCertificateMap.get("transactionId").toString());
        form.setField("state", degreeCertificateMap.get("state").toString());
        form.setField("time", degreeCertificateMap.get("time").toString());

        // 通过域名获取所在页和坐标，左下角为起点
        int pageNo = form.getFieldPositions(fieldName).get(0).page;
        Rectangle signRect = form.getFieldPositions(fieldName).get(0).position;
        float x = signRect.getLeft();
        float y = signRect.getBottom();

        // 读图片
        Image image = Image.getInstance(imagePath);
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
        String fileName = UUID.randomUUID().toString();//暂定
        String pdfFileUrl = FastDFSClient.uploadFile(new File(targetPath),fileName);
        deleteFile(targetPath);//暂时不处理异常
        return pdfFileUrl;
    }


    //工具类
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

}
