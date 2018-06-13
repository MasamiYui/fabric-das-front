import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.it611.das.template.PDFTemplate;
import org.it611.das.util.FileUtil;
import org.it611.das.util.QRUtil;

import java.io.*;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class JZ {

    public static void main(String[] args) throws IOException, DocumentException {
        //学历证书模板路径
        String templatePath = "/home/yui/Desktop/驾照模板/222.pdf";
        System.out.println(templatePath);
        InputStream input = new FileInputStream(new File(templatePath));//linux
        PdfReader reader = new PdfReader(input);
        System.out.println(reader.getFileLength());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("/home/yui/Desktop/aaa.pdf"));//linux

        //权限控制
        stamper.setEncryption(null, null, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);

//      System.out.println(targetPath);

        //return targetPath;
    }
}
