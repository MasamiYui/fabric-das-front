package org.it611.das.vo;

public class PhotoVO {

    private String title;//作品名称----  图片

    private String des;//作品描述 ---自己描述一下这个作品

    private String ownerId;//著作权人的Id（申请人）

    private String author;//创作者（作者），可以是几个，用，隔开-------小明 小红

    private String files;//文件url  ，隔开

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public PhotoVO(String title, String des, String ownerId, String author, String files) {
        this.title = title;
        this.des = des;
        this.ownerId = ownerId;
        this.author = author;
        this.files = files;
    }

    public PhotoVO() {
    }
}
