package org.it611.das.domain;

public class DegreeCertificate {

    private String id;
    private String userId;
    private String name;//姓名
    private String sex;//性别
    private String date;//出生日期
    private String degreeConferringUnit;//学位授予单位
    private String professional;//专业
    private String degreeName;//学位名称
    //private byte[] headPortrait;//头像（做不出）
    private String certId;//证书Id
    private String time;//时间

    private String files;//文件地址
    private String fileHash;//文件Hash
    private String transactionId;
    private String state;//文件状态
    private String submitTime;//提交时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
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

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public DegreeCertificate(String id, String userId, String name, String sex, String date, String degreeConferringUnit, String professional, String degreeName, String certId, String time, String files, String fileHash, String transactionId, String state, String submitTime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.date = date;
        this.degreeConferringUnit = degreeConferringUnit;
        this.professional = professional;
        this.degreeName = degreeName;
        this.certId = certId;
        this.time = time;
        this.files = files;
        this.fileHash = fileHash;
        this.transactionId = transactionId;
        this.state = state;
        this.submitTime = submitTime;
    }
}