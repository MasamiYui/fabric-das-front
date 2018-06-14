package org.it611.das.util;

import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdUtil {


    private static String middle = "";

    static {
        try {
            middle = MathUtils.makeUpNewData(Math.abs(NetworkUtils.getHostIP().hashCode()) + "", 4) +   //4位IP地址hash
                    MathUtils.makeUpNewData(NetworkUtils.getPid(), 4);                                 //4位PID进程hash
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂时确定的18位随机数生成策略，满足Id携带随机数以及递增,前面也可添加业务码
     * @return
     */
    public static String getId() {
        String id="";
        //获取当前时间戳
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        //获取6位随机数
        int random=(int) ((Math.random()+1)*100000);
        id=temp+random;
        return id;
    }


    /**
     * 企业和普通用户的Id生成策略
     * @return
     */
    public static String getUserId(){

        return getRandomIdByUUID();
    }


    /**
     * 学历证书Id生成策略
     * @return
     */
    public static String getDegreeCertId(){

        return "xlzs"+getId();
    }


    /**
     * 实用新型专利Id生成策略
     * @return
     */
    public static String getSyxxzlId(){

        return "syxxzl"+getId();
    }


    public static String getDrivingLicenceId(){

        return "jsz"+getId();
    }

    /**
     * 图片Id资产生成策略
     * @return
     */
    public static String getImageId(){

        return "tp"+getId();
    }

    /**
     * 音频Id资产生成策略
     * @return
     */
    public static String getAudioId(){

        return "yp"+getId();
    }

    /**
     * 视频资产Id生成策略
     * @return
     */
    public static String getVideoId(){

        return "sp"+getId();
    }






    //获取二维码的验证码
    public static String getQrCode(){
        String code=String.valueOf(UUID.randomUUID().hashCode());
        return code;
    }




    /**
     * 以毫微秒做基础计数, 返回唯一有序增长ID
     * <pre>System.nanoTime()</pre>
     * <pre>
     *  线程数量:   100
     *  执行次数:   1000
     *  平均耗时:   222 ms
     *  数组长度:   100000
     *  Map Size:   100000
     * </pre>
     * @return  ID长度32位
     */
    public static String getIncreaseIdByNanoTime(){
        return System.nanoTime()+                                                       //时间戳-14位
                middle+                                                                  //标志-8位
                MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+        //3位线程标志
                MathUtils.randomDigitNumber(7);                                          //随机7位数
    }

    /**
     * 以毫秒做基础计数, 返回唯一有序增长ID, 有几率出现线程并发
     * <pre>System.currentTimeMillis()</pre>
     * <pre>
     *  线程数量:   100
     *  执行次数:   1000
     *  平均耗时:   206 ms
     *  数组长度:   100000
     *  Map Size:   99992
     * </pre>
     * @return  ID长度32位
     */
    public static String getIncreaseIdByCurrentTimeMillis(){
        return  System.currentTimeMillis()+                                             //时间戳-14位
                middle+                                                                 //标志-8位
                MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+       //3位线程标志
                MathUtils.randomDigitNumber(8);                                         //随机8位数
    }

    /**
     * 基于UUID+MD5产生唯一无序ID
     * <pre>
     *  线程数量:   100
     *  执行次数:   1000
     *  平均耗时:       591 ms
     *  数组长度:   100000
     *  Map Size:   100000
     * </pre>
     * @return  ID长度32位
     */
    public static String getRandomIdByUUID(){
        return DigestUtils.md5Hex(UUID.randomUUID().toString());
    }

    /* ---------------------------------------------分割线------------------------------------------------ */
    /** 字符串MD5处理类 */
    private static class DigestUtils {

        private static final char[] DIGITS_LOWER =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        private static final char[] DIGITS_UPPER =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        private static char[] encodeHex(final byte[] data, final char[] toDigits) {
            final int l = data.length;
            final char[] out = new char[l << 1];
            for (int i = 0, j = 0; i < l; i++) {
                out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
                out[j++] = toDigits[0x0F & data[i]];
            }
            return out;
        }

        public static String md5Hex(String str){
            return md5Hex(str, false);
        }

        public static String md5Hex(String str, boolean isUpper){
            try {
                return new String(encodeHex(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")), isUpper ? DIGITS_UPPER : DIGITS_LOWER));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {

        System.out.println(getRandomIdByUUID());
    }



}
