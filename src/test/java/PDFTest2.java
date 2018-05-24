import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFTest2 {
    public static void main(String[] args) throws IOException, DocumentException {

        CertData certData = new CertData();
        certData.setAssetId("123");
        certData.setCertId("1234");
        certData.setDate("2012-02-02");
        certData.setDegreeConferringUnit("123123123");
        certData.setFileshash("asdasdasdasdads");
        certData.setName("asdasdasdasdasd");
        certData.setDegreeName("123");
        certData.setProfessoinal("123");
        certData.setSex("男");
        certData.setOwnerId("asdasdasd");
        certData.setTransactionId("asdasda");
        certData.setTime("2012-03-06");


        PDFTemplet pdftt = new PDFTemplet();
        pdftt.setTemplatePdfPath("C:\\Users\\YUI\\Desktop\\资产模板2.pdf");
        pdftt.setTargetPdfpath("C:\\Users\\YUI\\Desktop\\aaabbbccc.pdf");
        pdftt.setCertData(certData);
        File file = new File("C:\\Users\\YUI\\Desktop\\aaabbbccc.pdf");
        file.createNewFile();
        pdftt.templetCert(file);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\YUI\\Desktop\\aaabbbccc.pdf"));
        document.open();
        Image image = Image.getInstance("C:\\Users\\YUI\\Desktop\\timg.jpg");
        document.add(image);



    }
}
