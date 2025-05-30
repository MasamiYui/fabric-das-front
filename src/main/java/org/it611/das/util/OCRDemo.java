package org.it611.das.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.image.ImageClient;
import com.qcloud.image.request.GeneralOcrRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OCRDemo {

    //学历证书识别
    public static void doOcrDegreeCert() throws Exception {
        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        File file = new File("/home/yui/Desktop/测试资源/学位证书模板/学历证书.jpg");
        GeneralOcrRequest request = new GeneralOcrRequest("yui-1252836514", file);//证书路径
        String ret = imageClient.generalOcr(request);
        System.out.println("原始返回信息："+ret);

        //取信息中的itemString
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ret);
        List<JsonNode> nodes = jsonNode.findValues("itemstring");
        String str = "";
        System.out.println(nodes.get(1).asText());
        for(int i =0; i<nodes.size(); i++){
            str = str + nodes.get(i).asText();
        }
        System.out.println(str);//打印itemString中的信息


        //为了更好的处理，可以去除空格
        str = str.replace(" ", "");//去除空格
        System.out.println(str);

        //为了更好的解决中英文逗号，也可以将所有的逗号切换成中文逗号
        //str = str.replace(",", "，");

        //切取信息示范,保证所有的信息都能识别
        //姓名解析：姓名位置为 学士学位证书 和 第一个逗号之间的值
        int pos1 = str.indexOf(",");//英文逗号
        System.out.println(pos1);
        String name = str.substring(6,pos1);
        System.out.println("name:"+name);
        //性别解析：  在第一个逗号和第二逗号之间的内容即位性别
        int pos2 = str.indexOf("，",pos1+1);//在第一个pos之后查询，中文逗号
        System.out.println(pos2);
        String sex = str.substring(pos1+1, pos2);
        System.out.println("sex:"+sex);
        String year = str.substring(pos2+1, pos2+1+4);//解析出生年
        System.out.println("year:"+year);

        int pos3 = pos2+1+4;
        String month = str.substring(pos3+1, pos3+1+1);
        if(Integer.valueOf(month)<10) {
            month = "0"+month;
        }
        System.out.println("month:"+month);

        int pos4 = pos3 +1+1;
        String day = str.substring(pos4+1, pos4+1+1);
        if(Integer.valueOf(day) < 10){
            day = "0" + day;
        }
        System.out.println("day:"+day);
        String birth = year+"-"+month+"-"+day;
        System.out.println(birth);

        int pos5 =pos4+1+1+4;
        //找到大学、学院、学校这个词
        int pos6 = 0;
        if(-1 != str.indexOf("大学",pos5)){
            pos6 =  str.indexOf("大学",pos5)+2;
        }else if(-1 != str.indexOf("学院", pos5)) {
            pos6 = str.indexOf("学院", pos5)+2;
        }else if(-1 != str.indexOf("学校", pos5)){
            pos6 = str.indexOf("学校", pos5)+2;
        }else{
            //其他情况
            throw new Exception("解析失败");
        }
        String schoolName = str.substring(pos5, pos6);
        System.out.println("schoolName:"+ schoolName);

        int mojorPos = str.indexOf("专业");
        String major = str.substring(pos6,mojorPos);
        System.out.println("major:"+ major);

        int pos7 =str.indexOf("学士学位");
        String degreeType = str.substring(pos7, pos7+2);
        System.out.println("degreeType:"+ degreeType);

        int pos8 = str.indexOf("证书编号");
        int pos9 = str.lastIndexOf("了");
        String certId = str.substring(pos8+5, pos9);
        System.out.println("证书编号:"+ certId);
        String timeChinese = str.substring(pos9+1, pos9+11);
        System.out.println("timeChinese:"+timeChinese);
        String yearChinese = timeChinese.substring(0,4);
        String certYear = NumberUtil.certYearTranslate(yearChinese);
        System.out.println("certYear:" + certYear);
        int pos10 = timeChinese.indexOf("月");
        String monthChinese = timeChinese.substring(5,pos10);
        System.out.println(monthChinese);
        String certMonth = String.valueOf(NumberUtil.ChineseToNumber(monthChinese));

        if (Integer.valueOf(certMonth)<10) {
            certMonth = "0"+ certMonth;
        }
        System.out.println("certMonth:"+certMonth);
        int pos11 = timeChinese.indexOf("日");
        String dayChinese = timeChinese.substring(pos10+1, pos11);
        System.out.println(dayChinese);
        String certDay = String.valueOf(NumberUtil.ChineseToNumber(dayChinese));
        System.out.println("certDay:"+certDay);
        if(Integer.valueOf(certDay)<10){
            certDay = "0"+certDay;
        }
        String certTime = certYear+"-"+certMonth+"-"+certDay;
        System.out.println("发证时间："+certTime);
    }


    public static void doSyxxzlOcr() throws IOException {

        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        File file = new File("/home/yui/Desktop/测试资源/发明专利模板/发明专利.Jpeg");
        GeneralOcrRequest request = new GeneralOcrRequest("yui-1252836514", file);//证书路径
        String ret = imageClient.generalOcr(request);
        System.out.println("原始返回信息："+ret);

        //取信息中的itemString
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ret);
        List<JsonNode> nodes = jsonNode.findValues("itemstring");
        String str = "";


        //为了更好的处理，可以去除空格
        String str2 = "";

        System.out.println(nodes.get(1).asText());
        for(int i =0; i<nodes.size(); i++){
            str2 = str2 + nodes.get(i).asText();
        }
        str2 =str2.replace(" ", "");

        //为了更好的解决中英文逗号，也可以将所有的逗号切换成中文逗号
        //str = str.replace(",", "，");

        //切取信息示范,保证所有的信息都能识别

        System.out.println(str2);
        int pos1 = str2.indexOf("第");
        int pos2 = str2.indexOf("号",pos1);
        System.out.println(pos1);
        System.out.println(pos2);
        String zsh = str2.substring(pos1+1,pos2);
        System.out.println("zsh:"+zsh);
        int pos3 = str2.indexOf("新型名称");
        int posTemp = str2.indexOf("明人");
        String syxxmc = str2.substring(pos3+5, posTemp);
        System.out.println("syxxmc:"+syxxmc);
        int pos4 = str2.indexOf("专利号",pos2);
        String fmr = str2.substring(posTemp+3, pos4);
        System.out.println("fmr:"+fmr);
        //String syxxmc = str2.substring(pos3+4, pos4);
        int pos5 = str2.indexOf("专利号");
        int pos6 = str2.indexOf("专利申请日");
        String zlh = str2.substring(pos5+4, pos6);
        System.out.println("zlh:"+zlh);

        int pos7 =str2.indexOf("专利权人");

        String zlsqr = str2.substring(pos6+6, pos7-1);
        zlsqr = zlsqr.replace("年","-");
        zlsqr = zlsqr.replace("月","-");
        System.out.println("slzqr:"+ zlsqr);

        int pos8 = str2.indexOf("授权公告日");
        String zlqr = str2.substring(pos7+5, pos8);
        System.out.println("zlqr:"+zlqr);
        int pos9 = str2.indexOf("日",pos8+5);
        String sqggr = str2.substring(pos8+6, pos9);
        sqggr = sqggr.replace("年","-");
        sqggr = sqggr.replace("月","-");
        System.out.println("sqggr:"+sqggr);
        int pos10 = str2.indexOf("第1页");
        String fzsj = str2.substring(pos10-11, pos10-1);
        fzsj = fzsj.replace("年","-");
        fzsj = fzsj.replace("月","-");
        System.out.println("fzsj:"+fzsj);


    }


    public static void main(String[] args) throws Exception {

        //doOcrDegreeCert();
        //doSyxxzlOcr();

    }
}
