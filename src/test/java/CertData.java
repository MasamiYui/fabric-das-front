import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;



import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;


public class CertData {

    private String assetId;

    private String ownerId;

    private String name;

    private String sex;

    private String date;

    private String degreeConferringUnit;

    private String professoinal;

    private String degreeName;

    private String certId;

    private String time;

    private String fileshash;

    private String transactionId;

    private String state;

    public CertData(String assetId, String ownerId, String name, String sex, String date, String degreeConferringUnit, String professoinal, String degreeName, String certId, String time, String fileshash, String transactionId, String state) {
        this.assetId = assetId;
        this.ownerId = ownerId;
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.degreeConferringUnit = degreeConferringUnit;
        this.professoinal = professoinal;
        this.degreeName = degreeName;
        this.certId = certId;
        this.time = time;
        this.fileshash = fileshash;
        this.transactionId = transactionId;
        this.state = state;
    }


    public CertData(){

    }


    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDegreeConferringUnit() {
        return degreeConferringUnit;
    }

    public void setDegreeConferringUnit(String degreeConferringUnit) {
        this.degreeConferringUnit = degreeConferringUnit;
    }

    public String getProfessoinal() {
        return professoinal;
    }

    public void setProfessoinal(String professoinal) {
        this.professoinal = professoinal;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileshash() {
        return fileshash;
    }

    public void setFileshash(String fileshash) {
        this.fileshash = fileshash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}