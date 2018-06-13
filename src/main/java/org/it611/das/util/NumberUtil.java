package org.it611.das.util;

public class NumberUtil {

    private static String certYearCharTranslate(char ch) throws Exception {
        switch (ch) {
            case '0':
                return "0";
            case '一':
                return "1";
            case '二':
                return "2";
            case '三':
                return "1";
            case '四':
                return "0";
            case '五':
                return "1";
            case '六':
                return "0";
            case '七':
                return "1";
            case '八':
                return "0";
            case '九':
                return "1";
            default:
                throw new Exception("解析错误.");
        }
    }

    public static String certYearTranslate(String year) throws Exception {
        if(year.length() != 4){
            throw new Exception("input year error.");
        }

        String result = "";
        for(int i=0; i<4; i++){
            result = result + certYearCharTranslate(year.charAt(i));
        }

        return result;
    }



    public static int ChineseToNumber(String str){
        String str1 = new String();
        String str2 = new String();
        String str3 = new String();
        int k = 0;
        boolean dealflag = true;
        for(int i=0;i<str.length();i++){//先把字符串中的“零”除去
            if('零' == (str.charAt(i))){
                str = str.substring(0, i) + str.substring(i+1);
            }
        }
        String chineseNum = str;
        for(int i=0;i<chineseNum.length();i++){
            if(chineseNum.charAt(i) == '亿'){
                str1 = chineseNum.substring(0,i);//截取亿前面的数字，逐个对照表格，然后转换
                k = i+1;
                dealflag = false;//已经处理
            }
            if(chineseNum.charAt(i) == '万'){
                str2 = chineseNum.substring(k,i);
                str3 = str.substring(i+1);
                dealflag = false;//已经处理
            }
        }
        if(dealflag){//如果没有处理
            str3 =  chineseNum;
        }
        int result = sectionChinese(str1) * 100000000 +
                sectionChinese(str2) * 10000 + sectionChinese(str3);
        return result;
    }

    public static int sectionChinese(String str){
        int value = 0;
        int sectionNum = 0;
        for(int i=0;i<str.length();i++){
            int v = (int) Tool.intList.get(str.charAt(i));
            if( v == 10 || v == 100 || v == 1000 ){//如果数值是权位则相乘
                sectionNum = v * sectionNum;
                value = value + sectionNum;
            }else if(i == str.length()-1){
                value = value + v;
            }else{
                sectionNum = v;
            }
        }
        return value;
    }






}
