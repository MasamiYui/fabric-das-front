package org.it611.das.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String getLocalTime () {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
       return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static int serverTime() throws ParseException {
        String initTime = "2018-06-24 23:59:59";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date initDate = format.parse(initTime);



        int days = (int) ((new Date().getTime() - initDate.getTime()) / (1000*3600*24));
        return days;
    }

}
