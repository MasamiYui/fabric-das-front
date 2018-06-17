package org.it611.das.util;

import org.it611.das.domain.*;

public class AssertType {
    public static Class queryAssertType(String assertId) {

        if (assertId.indexOf("xlzs") > -1) {   //学位证书
            return DegreeCertificate.class;
        } else if (assertId.indexOf("syxxzl") > -1) {//实用新型专利
            return Syxxzl.class;
        } else if (assertId.indexOf("jsz") > -1) { //驾驶证
            return DrivingLicence.class;
        } else if (assertId.indexOf("tp") > -1) {  //图片
            return Photo.class;
        } else if (assertId.indexOf("sp") > -1) { //视频
            return Video.class;
        } else if (assertId.indexOf("yp") > 0) {  //音频
            return Music.class;
        } else {
            return null;
        }
    }

    public static String queryType(String assertId) {
        if (assertId.contains("xlzs")) {   //学位证书
            return "学位证书";
        } else if (assertId.contains("syxxzl")) {//实用新型专利
            return "实用新型专利";
        } else if (assertId.contains("jsz")) { //驾驶证
            return "驾驶证";
        } else if (assertId.contains("tp")) {  //图片
            return "图片";
        } else if (assertId.contains("sp")) { //视频
            return "视频";
        } else if (assertId.contains("yp")) {  //音频
            return "音频";
        } else {
            return "errorType";
        }

    }

}
