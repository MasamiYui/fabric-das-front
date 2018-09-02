package org.it611.das.vo;

public class SyxxzlVO {

    private String ownerId;

    private String zsh;//证书号

    private String syxxmc;//实用新类名称

    private String fmr;//发明人

    private String zlh;//专利号

    private String zlsqr;

    private String zlqr;//专利权人

    private String sqggr;//授权公告日

    private String fzsj;//发证时间

    private String files;//文件地址

    public SyxxzlVO(String ownerId, String zsh, String syxxmc, String fmr, String zlh, String zlsqr, String zlqr, String sqggr, String fzsj, String files) {
        this.ownerId = ownerId;
        this.zsh = zsh;
        this.syxxmc = syxxmc;
        this.fmr = fmr;
        this.zlh = zlh;
        this.zlsqr = zlsqr;
        this.zlqr = zlqr;
        this.sqggr = sqggr;
        this.fzsj = fzsj;
        this.files = files;
    }

    public SyxxzlVO(){}

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getZsh() {
        return zsh;
    }

    public void setZsh(String zsh) {
        this.zsh = zsh;
    }

    public String getSyxxmc() {
        return syxxmc;
    }

    public void setSyxxmc(String syxxmc) {
        this.syxxmc = syxxmc;
    }

    public String getFmr() {
        return fmr;
    }

    public void setFmr(String fmr) {
        this.fmr = fmr;
    }

    public String getZlh() {
        return zlh;
    }

    public void setZlh(String zlh) {
        this.zlh = zlh;
    }

    public String getZlsqr() {
        return zlsqr;
    }

    public void setZlsqr(String zlsqr) {
        this.zlsqr = zlsqr;
    }

    public String getZlqr() {
        return zlqr;
    }

    public void setZlqr(String zlqr) {
        this.zlqr = zlqr;
    }

    public String getSqggr() {
        return sqggr;
    }

    public void setSqggr(String sqggr) {
        this.sqggr = sqggr;
    }

    public String getFzsj() {
        return fzsj;
    }

    public void setFzsj(String fzsj) {
        this.fzsj = fzsj;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
