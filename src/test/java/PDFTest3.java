import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PDFTest3 {
    public static void main(String[] args) throws Exception {
        // 模板文件路径
        String templatePath = "C:\\Users\\YUI\\Desktop\\资产模板3.pdf";
        // 生成的文件路径
        String targetPath = "C:\\Users\\YUI\\Desktop\\result3.pdf";
        // 书签名
        String fieldName = "image1";
        // 图片路径
        String imagePath = "C:\\Users\\YUI\\Desktop\\timg.jpg";

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
        form.setField("assetId", "20180521165335145310");
        form.setField("owner", "123123");
        form.setField("name", "张琳");
        form.setField("sex", "女");
        form.setField("date", "1996-09-2");
        form.setField("degreeConferringUnit", "四川农业大学");
        form.setField("professional", "工商管理");
        form.setField("degreeName","学士");
        form.setField("certId","232323123");
        form.setField("fileshash", "asdas12dsfgsdhgd3t4fsdfasd");
        form.setField("transactionId", "asdas12dsfgsdhgd3t4fsdfasd");
        form.setField("state", "1");
        form.setField("time", "2009-06-20");



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
    }

}
