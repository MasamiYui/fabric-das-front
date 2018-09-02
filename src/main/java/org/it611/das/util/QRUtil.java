package org.it611.das.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.util.Hashtable;
import java.util.UUID;

public class QRUtil {
    public static String GenerateQR(String text) {
        int width = 100;
        int height = 100;
        String format = "png";
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            String tmpFileName = UUID.randomUUID().toString()+".png";
            String basePath = QRUtil.class.getClass().getResource("/").getPath()+"tmp/";
            basePath = basePath.substring(1, basePath.length());//TODO 部署到linux下可能不需要这个操作
            String tmpFilePath =basePath + tmpFileName;
            System.out.println(tmpFilePath);
            //MatrixToImageWriter.writeToPath(bitMatrix, format, new File(tmpFilePath).toPath());//windows
            MatrixToImageWriter.writeToPath(bitMatrix, format, new File("/"+tmpFilePath).toPath());//linux
            //删除这个临时文件
            //FileUtil.deleteFile(tmpFilePath);
            //生成成功
            return  tmpFilePath;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        GenerateQR("www.baidu.com");
    }


}
