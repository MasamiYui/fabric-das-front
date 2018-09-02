package org.it611.das.ocr;

import com.qcloud.image.ImageClient;
import com.qcloud.image.request.*;

import java.io.*;

public class OcrExample {


    /**
     * 通用印刷体OCR
     */
    public static void ocrGeneral(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        GeneralOcrRequest request = new GeneralOcrRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/ocr_common09.jpg");
        ret = imageClient.generalOcr(request);
        System.out.println("ocrGeneral:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new GeneralOcrRequest(bucketName, new File("assets", "ocr_common09.jpg"));
        ret = imageClient.generalOcr(request);
        System.out.println("ocrGeneral:" + ret);
    }

    /**
     * OCR-营业执照识别
     */
    public static void ocrBizLicense(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. url方式
        System.out.println("====================================================");
        OcrBizLicenseRequest request = new OcrBizLicenseRequest(bucketName, "http://youtu.qq.com/app/img/experience/char_general/ocr_yyzz_02.jpg");
        ret = imageClient.ocrBizLicense(request);
        System.out.println("ocrBizLicense:" + ret);

        //2. 图片内容方式
        System.out.println("====================================================");
        request = new OcrBizLicenseRequest(bucketName, new File("assets", "ocr_yyzz_02.jpg"));
        ret = imageClient.ocrBizLicense(request);
        System.out.println("ocrBizLicense:" + ret);
    }


    /**
     * OCR-行驶证驾驶证识别
     */
    public static void ocrDrivingLicence(ImageClient imageClient, String bucketName) {
        String ret;
        // 1. 驾驶证 url方式
        System.out.println("====================================================");
        OcrDrivingLicenceRequest request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_jsz_01.jpg");
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);

        //2. 驾驶证 图片内容方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, new File("assets", "icon_ocr_jsz_01.jpg"));
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);

        // 1. 行驶证 url方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_VEHICLE_LICENSE, "http://youtu.qq.com/app/img/experience/char_general/icon_ocr_xsz_01.jpg");
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);

        //2. 行驶证 图片内容方式
        System.out.println("====================================================");
        request = new OcrDrivingLicenceRequest(bucketName, OcrDrivingLicenceRequest.TYPE_VEHICLE_LICENSE, new File("assets", "icon_ocr_xsz_01.jpg"));
        ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrDrivingLicence:" + ret);
    }

    /**
     * 身份证ocr识别操作
     */
    public static String ocrIdCard(ImageClient imageClient, String bucketName, File file) throws IOException {

        File[] idcardImageList = new File[1];
        idcardImageList[0] = file;
        IdcardDetectRequest idReq = new IdcardDetectRequest(bucketName, idcardImageList, 0);
        String ret = imageClient.idcardDetect(idReq);
        return ret;
    }




    //工具类
    public static void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

}
