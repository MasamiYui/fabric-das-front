package org.it611.das.vo;

public class DrivingLicenceVO {

    private String ownerId;

    private String drivingLicenceId;//驾驶证证号
    private String name;//姓名
    private String sex;//性别
    private String nation;//国籍
    private String address;//住址
    private String date;//出生日期
    private String lzDate;//领证日期
    private String zjcx;//准驾车型
    private String startDate;//起始时间
    private String validTime;//有效时间
    private String hz;//红章

    private String files;//文件地址


    public DrivingLicenceVO() {}

    public DrivingLicenceVO(String ownerId, String drivingLicenceId, String name, String sex, String nation, String address, String date, String lzDate, String zjcx, String startDate, String validTime, String hz, String files) {
        this.ownerId = ownerId;
        this.drivingLicenceId = drivingLicenceId;
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.address = address;
        this.date = date;
        this.lzDate = lzDate;
        this.zjcx = zjcx;
        this.startDate = startDate;
        this.validTime = validTime;
        this.hz = hz;
        this.files = files;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDrivingLicenceId() {
        return drivingLicenceId;
    }

    public void setDrivingLicenceId(String drivingLicenceId) {
        this.drivingLicenceId = drivingLicenceId;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLzDate() {
        return lzDate;
    }

    public void setLzDate(String lzDate) {
        this.lzDate = lzDate;
    }

    public String getZjcx() {
        return zjcx;
    }

    public void setZjcx(String zjcx) {
        this.zjcx = zjcx;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getHz() {
        return hz;
    }

    public void setHz(String hz) {
        this.hz = hz;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
