package org.it611.das.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.image.ImageClient;
import com.qcloud.image.request.GeneralOcrRequest;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.domain.DrivingLicence;
import org.it611.das.domain.Syxxzl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GeneralOcrParseUtil {

    public static DegreeCertificate parseDegreeCertData(String rawData) throws Exception {

        DegreeCertificate dc = new DegreeCertificate();


        //取信息中的itemString
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(rawData);
        List<JsonNode> nodes = jsonNode.findValues("itemstring");
        String str = "";
        System.out.println(nodes.get(1).asText());
        for(int i =0; i<nodes.size(); i++){
            str = str + nodes.get(i).asText();
        }
        System.out.println(str);//打印itemString中的信息


        //为了更好的处理，可以去除空格
        str = str.replace(" ", "");//去除空格
        //将所有的字母，标点符号都去除
        str=str.replaceAll("[`qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM~!@#$%^&*()+=|{}';',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】《》‘；”“’。，、？|-]", "");
        System.out.println(str);

        //为了更好的解决中英文逗号，也可以将所有的逗号切换成中文逗号
        //str = str.replace(",", "，");

        //切取信息示范,保证所有的信息都能识别
        //姓名解析：姓名位置为 学士学位证书 和 第一个逗号之间的值
        //String str="" ;
        //str="学士学位证书熊志,男，1985年6月6日生。在重庆邮电大学软件工程专业完成了本科学习计划，业已毕业，经审核符合《中华人民共和国学位条例》的规定，授予工学学士学位。校长张三重庆邮电大学学位评定委员会主席证书编号:123123123123123123了二0一三年二月二十日(成人高等教育本科毕业生)ssu62人u≤wk";
        int pos1=0;
        if(str.lastIndexOf("男",str.indexOf("19"))!=-1){
            pos1=str.lastIndexOf("男",str.indexOf("19"));
        }else{
            pos1=str.lastIndexOf("女",str.indexOf("19"));
        }
//        if (str.indexOf(",")!=-1){
//            pos1 = str.indexOf(",");//英文逗号
//        }
//        else{
//            pos1=str.indexOf("，");//中文逗号
//        }
        int front_pos1=str.indexOf("书");
        System.out.println(pos1);
        String name="";
        //只有这个存在，才取出来
        if(front_pos1>0&&pos1>0){
            try{//为了健壮性，加try语句
                name = str.substring(front_pos1+1,pos1);}
            catch(Exception e){
                //不操作
            }
        }
        System.out.println("name:"+name);
        dc.setName(name);//设置姓名

        //性别解析：  在第一个逗号和第二逗号之间的内容即位性别
        //int pos2 = str.indexOf("，",pos1+1);//在第一个pos之后查询，中文逗号
        int pos2=0;
        if(str.lastIndexOf("男",str.indexOf("19"))!=-1){
            pos2=str.lastIndexOf("男",str.indexOf("19"));
        }else{
            pos2=str.lastIndexOf("女",str.indexOf("19"));
        }

        System.out.println(pos2);
        String sex="";
        //只有这个存在，才取出来
        if(pos2>0){
            try{//为了健壮性，加try语句
                sex = str.substring(pos2, pos2+1);}
            catch(Exception e){
                //不操作
            }
        }
        System.out.println("sex:"+sex);
        //设置性别
        dc.setSex(sex);
        int pos3=str.indexOf("年",pos2);
        String year="";
        //只有这个存在，才取出来
        if(pos3>0){
            try{//为了健壮性，加try语句
                year=str.substring(pos3-4,pos3);}
            catch(Exception e){
                //不操作
            }
        }
        //String year = str.substring(pos2+1, pos2+1+4);//解析出生年
        System.out.println("year:"+year);

        //int pos3 = pos2+1+4;
        String month="";
        //只有这个存在，才取出来
        if(pos3>0&&str.indexOf("月",pos3)>0){
            try{//为了健壮性，加try语句
                month = str.substring(pos3+1, str.indexOf("月",pos3));}
            catch(Exception e){
                //不操作
            }
        }
        if(month.length()==1){//有可能出现前面已经补全0的情况了。
            if(Integer.valueOf(month)<10) {
                month = "0"+month;
            }}
        System.out.println("month:"+month);

        int pos4 = str.indexOf("月",pos3);
        String day="";
        //只有这个存在，才取出来
        if(pos4>0&&str.indexOf("日",pos4)>0){
            try{//为了健壮性，加try语句
                day = str.substring(pos4+1, str.indexOf("日",pos4));}
            catch(Exception e){
                //不操作
            }
        }
        System.out.println(day);
        if(day.length()==1){//有可能出现前面已经补全0的情况了。
            if(Integer.valueOf(day) < 10){
                day = "0" + day;
            }}
        System.out.println("day:"+day);
        String birth = year+"-"+month+"-"+day;
        System.out.println(birth);
        //设置出生年月
        dc.setDate(birth);

        //int pos5 =pos4+1+1+4;
        int pos5=str.indexOf("在",pos4)+1;
        // System.out.println(str.indexOf("学"));
        //找到大学、学院、学校这个词
        int pos6 = 0;
        if(-1 != str.indexOf("大学",pos5)){
            if(-1!=str.indexOf("学院",str.indexOf("大学"))){//有可能出现xx大学xx学院的情况
                pos6 =  str.indexOf("学院",pos5)+2;
            }else{
                pos6 =  str.indexOf("大学",pos5)+2;}
        }else if(-1 != str.indexOf("学院", pos5)) {
            pos6 = str.indexOf("学院", pos5)+2;
        }else if(-1 != str.indexOf("学校", pos5)){
            if(-1!=str.indexOf("学院",str.indexOf("学校"))){//有可能出现xx学校xx学院的情况
                pos6 =  str.indexOf("学院",pos5)+2;
            }else{
                pos6 = str.indexOf("学校", pos5)+2;}
        }
//        else{
//            //其他情况
//            throw new Exception("解析失败");
//        }
        String schoolName="";
        //只有这个存在，才取出来
        if(pos5>0&&pos6>0){
            try{//为了健壮性，加try语句
                schoolName = str.substring(pos5, pos6);}
            catch(Exception e){
                //不操作
            }
        }

        dc.setDegreeConferringUnit(schoolName);//设置学校

        System.out.println("schoolName:"+ schoolName);

        int mojorPos = 0;
        if(str.indexOf("学科")!=-1){
            mojorPos=str.indexOf("学科");
        }else{
            mojorPos=str.indexOf("专业");}
        String major="";
        //只有这个存在，才取出来
        if(pos6>0&&mojorPos>0){
            try{//为了健壮性，加try语句
                major = str.substring(pos6,mojorPos);}
            catch(Exception e){
                //不操作
            }}
        System.out.println("major:"+ major);
        dc.setProfessional(major);

        //int degreeType_pos=str.indexOf("授予")+2;

        int pos7 =str.indexOf("学位");
        String degreeType="";
        //只有这个存在，才取出来
        if(pos7>0){
            try{//为了健壮性，加try语句
                degreeType = str.substring(pos7-2, pos7);}
            catch(Exception e){
                //不操作
            }
        }
        System.out.println("degreeType:"+ degreeType);
        dc.setDegreeName(degreeType);

        int pos8 = str.indexOf("编号")+2;
        //int pos9 =pos8+18;
        String certId="";
        //只有这个存在，才取出来
        if(pos8>0){
            try{//为了健壮性，加try语句
                certId = str.substring(pos8, pos8+16);}
            catch(Exception e){
                //不操作
            }
        }//学位编号只有16位
        System.out.println("证书编号:"+ certId);
        dc.setCertId(certId);
        int pos9=str.indexOf("二",pos8);
        int pos9_end=str.indexOf("日",pos8)+1;
        try{//为了健壮性，加try语句
            String timeChinese = str.substring(pos9, pos9_end);
            System.out.println("timeChinese:"+timeChinese);
            String yearChinese1 = timeChinese.substring(0,1);
            String yearChinese2=timeChinese.substring(1,2);
            String yearChinese3=timeChinese.substring(2,3);
            String yearChinese4=timeChinese.substring(3,4);

            //System.out.println(yearChinese);
            String year1=String.valueOf(NumberUtil.ChineseToNumber(yearChinese1));
            String year2="";
            if (Character.isDigit(yearChinese2.charAt(0))){
                year2=yearChinese2;
            }else{
                year2=String.valueOf(NumberUtil.ChineseToNumber(yearChinese2));
            }

            String year3=String.valueOf(NumberUtil.ChineseToNumber(yearChinese3));
            String year4=String.valueOf(NumberUtil.ChineseToNumber(yearChinese4));
            String certYear=year1+year2+year3+year4;
            //String certYear = NumberUtil.certYearTranslate(yearChinese);
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
            dc.setTime(certTime);
        }catch (Exception error){
            String certTime="";
            System.out.println("发证时间："+certTime);
            dc.setTime(certTime);
        }


       /* //解析逻辑
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
*/

        return dc;

    }


    public static Syxxzl parseSYFMZLData(String rawData) throws IOException {

       /* System.out.println(rawData);
        //字段解析
        try{
            DegreeCertificate degreeCertificate = new DegreeCertificate();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(rawData);
            List<JsonNode> nodes = jsonNode.findValues("itemstring");
            String str2 = "";

            System.out.println(nodes.get(1).asText());
            for(int i =0; i<nodes.size(); i++){
                str2 = str2 + nodes.get(i).asText();
            }
            str2 =str2.replace(" ", "");
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
            Syxxzl syfmzl = new Syxxzl();
            syfmzl.setFmr(fmr);
            syfmzl.setFzsj(fzsj);
            syfmzl.setSqggr(sqggr);
            syfmzl.setZlh(zlh);
            syfmzl.setSyxxmc(syxxmc);
            syfmzl.setZsh(zsh);
            syfmzl.setZlqr(zlqr);
            syfmzl.setZlsqr(zlsqr);
            return syfmzl;

        }catch (Exception e){
            return null;
        }*/

/*        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        File file = new File("/home/yui/Desktop/zl8.jpeg");
        GeneralOcrRequest request = new GeneralOcrRequest("yui-1252836514", file);//证书路径
        String ret = imageClient.generalOcr(request);
        System.out.println("原始返回信息："+ret);*/

        Syxxzl syxxzl = new Syxxzl();

        //取信息中的itemString
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(rawData);
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
        syxxzl.setZsh(zsh);//设置证书号
        int pos3 = str2.indexOf("名称");
        int posTemp=0;
        if (str2.indexOf("发明人")!=-1){
            posTemp = str2.indexOf("发明人");
        }else if(str2.indexOf("明人")!=-1){
            posTemp = str2.indexOf("明人");}
        else if(str2.indexOf("设计人")!=-1){
            posTemp = str2.indexOf("设计人");
        }else if(str2.indexOf("设计人")!=-1){
            posTemp = str2.indexOf("计人");
        }else if(str2.indexOf("殳计人")!=-1){//识别的初始结果可能出现这种情况
            posTemp = str2.indexOf("殳计人");
        }
        String syxxmc="";
        //只有这个存在，才取出来
        if(pos3>0&&posTemp>0){
            syxxmc = str2.substring(pos3+3, posTemp);}

        System.out.println("syxxmc:"+syxxmc);
        syxxzl.setSyxxmc(syxxmc);
        int pos4=0;
        if(str2.indexOf("专利号",pos2)!=-1){
            pos4 = str2.indexOf("专利号",pos2);
        }else{
            pos4 = str2.indexOf("利号",pos2);}//找不到专利号，找利号。
        if(str2.indexOf("计人")!=-1){
            posTemp = str2.indexOf("计人");
        }else if(str2.indexOf("明人")!=-1){
            posTemp = str2.indexOf("明人");
        }
        //posTemp = str2.indexOf("明人");
        String fmr="";
        //只有这个存在，才取出来
        if(posTemp>0&&pos4>0){
            fmr = str2.substring(posTemp+3, pos4);}
        System.out.println("fmr:"+fmr);

        syxxzl.setFmr(fmr);
        //String syxxmc = str2.substring(pos3+4, pos4);
        int pos5 = str2.indexOf("利号");

        int pos6 = str2.indexOf("专利申请日");
        String zlh="";
        //只有这个存在，才取出来
        if(pos5>0&&pos6>0){
            zlh = str2.substring(pos5+3, pos6);}
        System.out.println("zlh:"+zlh);
        syxxzl.setZlh(zlh);

        int pos7=0;
        if (pos6>0) {//如果专利申请日存在，则从专利申请日开始查询，如果不存在，则从权人往前查询
            pos7 = str2.indexOf("日", pos6 + 5);//在专利申请日后面一个的日}
        }else{
            pos6=str2.lastIndexOf("20",str.indexOf("权人"));
            pos7=str2.lastIndexOf("日",str.indexOf("权人"));
        }
        String zlsqr="";
        //只有这个存在，才取出来
        if(pos6>0&&pos7>0){
            zlsqr = str2.substring(str2.indexOf("20",pos6), pos7);}
        zlsqr = zlsqr.replace("年","-");
        zlsqr = zlsqr.replace("月","-");
        System.out.println("slzqr:"+ zlsqr);
        syxxzl.setZlsqr(zlsqr);
        int pos8 = 0;
        if(str2.indexOf("授权公告日")!=-1){
            pos8 = str2.indexOf("授权公告日");
        }else if(str2.indexOf("投权公告日")!=-1) {
            pos8 = str2.indexOf("投权公告日");}//找不到专利号，找利号

        String zlqr="";
        //只有这个存在，才取出来
        if(pos8>0&&str2.indexOf("权人",pos7)>0){
            zlqr = str2.substring(str2.indexOf("权人",pos7)+3, pos8);}
        System.out.println("zlqr:"+zlqr);
        syxxzl.setSqggr(zlqr);
        int pos9 = str2.indexOf("日",pos8+5);
        String sqggr="";
        //只有这个存在，才取出来
        if(pos8>0&&pos9>0){
            sqggr = str2.substring(pos8+6, pos9);}
        sqggr = sqggr.replace("年","-");
        sqggr = sqggr.replace("月","-");
        System.out.println("sqggr:"+sqggr);
        int pos10 = str2.indexOf("第1页");
        String fzsj="";
        //只有这个存在，才取出来
        if(pos10>0&&str2.lastIndexOf("20",pos10)>0){
            fzsj = str2.substring(str2.lastIndexOf("20",pos10), pos10-1);}
        if(fzsj.length()>10|fzsj.length()<9){
            fzsj="";
        }else{
            fzsj = fzsj.replace("年","-");
            fzsj = fzsj.replace("月","-");}
        System.out.println("fzsj:"+fzsj);
        syxxzl.setFzsj(fzsj);
        return syxxzl;

    }


    public static DrivingLicence parseDrivingLicenceData(String rawData) throws IOException {

        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(rawData);
            List<JsonNode> itemNodes = jsonNode.findParents("item");

            System.out.println("drivingLicence size:"+itemNodes.size());

            if(itemNodes.size() <11){
                return null;
            }
            DrivingLicence drivingLicence = new DrivingLicence();
            drivingLicence.setDrivingLicenceId(itemNodes.get(0).get("itemstring").asText());
            drivingLicence.setName(itemNodes.get(1).get("itemstring").asText());
            drivingLicence.setSex(itemNodes.get(2).get("itemstring").asText());
            drivingLicence.setNation(itemNodes.get(3).get("itemstring").asText());
            drivingLicence.setAddress(itemNodes.get(4).get("itemstring").asText());
            drivingLicence.setDate(itemNodes.get(5).get("itemstring").asText());
            drivingLicence.setLzDate(itemNodes.get(6).get("itemstring").asText());
            drivingLicence.setZjcx(itemNodes.get(7).get("itemstring").asText());
            drivingLicence.setStartDate(itemNodes.get(8).get("itemstring").asText());
            drivingLicence.setValidTime(itemNodes.get(9).get("itemstring").asText());
            drivingLicence.setHz(itemNodes.get(10).asText());


            return drivingLicence;
        }catch (Exception e){
            return null;
        }

    }

    public static HashMap<String,String> parseBizLicence(String rawData) throws IOException {

        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(rawData);
            List<JsonNode> itemNodes = jsonNode.findParents("item");
            if(itemNodes.size()< 5){
                return null;
            }
            System.out.println(itemNodes.size());
            HashMap<String, String> resultMap = new HashMap();
            resultMap.put("creditId", itemNodes.get(0).get("itemstring").asText());
            resultMap.put("representative",itemNodes.get(1).get("itemstring").asText());
            resultMap.put("companyName", itemNodes.get(2).get("itemstring").asText());
            resultMap.put("companyAddress", itemNodes.get(3).get("itemstring").asText());
            String time = itemNodes.get(4).get("itemstring").asText();
            String[] timeArr = time.split("至");
            resultMap.put("startTime",timeArr[0]);
            resultMap.put("endTime", timeArr[1]);
            if(resultMap.size()<6){
                return null;
            }

            return resultMap;
        }catch (Exception e){

            return null;
        }

    }


}
