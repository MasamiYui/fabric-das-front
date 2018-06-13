package org.it611.das.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.domain.DegreeCertificate;

import java.util.List;

public class GeneralOcrParseUtil {

    public static DegreeCertificate parseDegreeCertData(String rawData) throws Exception {

        DegreeCertificate dc = new DegreeCertificate();

        //解析逻辑
        try{
            System.out.println(rawData);
            //字段解析
            DegreeCertificate degreeCertificate = new DegreeCertificate();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(rawData);
            List<JsonNode> nodes = jsonNode.findValues("itemstring");
            String str = "";

            System.out.println(nodes.get(1).asText());
            for(int i =0; i<nodes.size(); i++){
                str = str + nodes.get(i).asText();
            }
            str =str.replace(" ", "");
            System.out.println(str);

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
            System.out.println("certId:"+ certId);
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
            System.out.println(certTime);

            dc.setName(name);
            dc.setCertId(certId);
            dc.setDate(birth);
            dc.setDegreeConferringUnit(schoolName);
            dc.setDegreeName(degreeType);
            dc.setProfessional(major);
            dc.setSex(sex);
            dc.setTime(certTime);
            return dc;
        }catch (Exception e){
            return null;
        }

    }

}
