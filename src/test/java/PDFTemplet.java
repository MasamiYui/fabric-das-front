import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PDFTemplet {

    private String templatePdfPath;
    private String ttcPath;
    private String targetPdfpath;
    private CertData certData;

    public PDFTemplet() {

    }

    public PDFTemplet(String templatePdfPath, String ttcPath, String targetPdfpath, CertData certData) {
        this.templatePdfPath = templatePdfPath;
        this.ttcPath = ttcPath;
        this.targetPdfpath = targetPdfpath;
        this.certData = certData;
    }

    public String getTemplatePdfPath() {
        return templatePdfPath;
    }

    public void setTemplatePdfPath(String templatePdfPath) {
        this.templatePdfPath = templatePdfPath;
    }

    public String getTtcPath() {
        return ttcPath;
    }

    public void setTtcPath(String ttcPath) {
        this.ttcPath = ttcPath;
    }

    public String getTargetPdfpath() {
        return targetPdfpath;
    }

    public void setTargetPdfpath(String targetPdfpath) {
        this.targetPdfpath = targetPdfpath;
    }

    public CertData getCertData() {
        return certData;
    }

    public void setCertData(CertData certData) {
        this.certData = certData;
    }

    public void templetCert(File file) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(templatePdfPath);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PdfStamper ps = new PdfStamper(reader, bos);

        /*使用中文字体 */
        //BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        AcroFields form = ps.getAcroFields();
        form.addSubstitutionFont(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));

        form.setField("assetId", certData.getAssetId());
        form.setField("owner", certData.getOwnerId());
        form.setField("name", certData.getName());
        form.setField("sex", certData.getDate());
        form.setField("date", certData.getDate());
        form.setField("degreeConferringUnit", certData.getDegreeConferringUnit());
        form.setField("professional", certData.getProfessoinal());
        form.setField("degreeName",certData.getDegreeName());
        form.setField("certId", certData.getCertId());
        form.setField("fileshash", certData.getFileshash());
        form.setField("transactionId", certData.getTransactionId());
        form.setField("state", certData.getState());
        form.setField("time", certData.getTime());




        ps.setFormFlattening(true);
        ps.close();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bos.toByteArray());
        fos.close();



    }

}
