package org.it611.das.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.image.ImageClient;
import com.qcloud.image.request.GeneralOcrRequest;
import com.qcloud.image.request.OcrBizLicenseRequest;
import com.qcloud.image.request.OcrDrivingLicenceRequest;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.ocr.OcrExample;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ORCTest {


    public static void main(String[] args) throws Exception {
        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
/*        File file = new File("/home/yui/Desktop/测试资源/营业执照.jpeg");
        String ret = OcrExample.ocrIdCard(imageClient,"yui-1252836514", f);
        OcrBizLicenseRequest request = new OcrBizLicenseRequest("yui-1252836514", new File("/home/yui/Desktop/测试资源/营业执照.jpeg"));
        String ret = imageClient.ocrBizLicense(request);
        System.out.println("ocrBizLicense:" + ret);*/
       // File file = new File("/home/yui/Desktop/测试资源/jiashizheng.jpeg");
/*        OcrDrivingLicenceRequest request = new OcrDrivingLicenceRequest("yui-1252836514", OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, new File("/home/yui/Desktop/测试资源/jiashizheng.jpeg"));
        String ret = imageClient.ocrDrivingLicence(request);
        System.out.println(ret);*/

/*        System.out.println("====================================================");
        OcrDrivingLicenceRequest request = new OcrDrivingLicenceRequest("yui-1252836514", 1,new File("/home/yui/Desktop/驾照模板/驾照模板2.jpg"));
        //String ret = imageClient.generalOcr(request);
        String ret = imageClient.ocrDrivingLicence(request);
        System.out.println("ocrGeneral:" + ret);*/

/*        System.out.println("====================================================");
        GeneralOcrRequest request = new GeneralOcrRequest("yui-1252836514", new File("/home/yui/Desktop/测试资源/aaaa.jpg"));
        String ret = imageClient.generalOcr(request);
        System.out.println("ocrGeneral:" + ret);*/

/*        String str = "{\"code\":0,\"message\":\"OK\",\"data\":{\"class\":[],\"angle\":0.0,\"items\":[{\"itemcoord\":{\"x\":316,\"y\":221,\"width\":404,\"height\":48},\"words\":[{\"character\":\"学\",\"confidence\":0.9999953508377076},{\"character\":\"士\",\"confidence\":0.999170422554016},{\"character\":\"学\",\"confidence\":0.9999853372573853},{\"character\":\"位\",\"confidence\":0.9999417066574096},{\"character\":\"证\",\"confidence\":0.9999291896820068},{\"character\":\"书\",\"confidence\":0.9999920129776}],\"itemstring\":\"学士学位证书\"},{\"itemcoord\":{\"x\":180,\"y\":303,\"width\":216,\"height\":25},\"words\":[{\"character\":\"熊\",\"confidence\":0.9999698400497437},{\"character\":\"志\",\"confidence\":0.9999223947525024},{\"character\":\",\",\"confidence\":0.6499601602554321},{\"character\":\"男\",\"confidence\":0.9999271631240844},{\"character\":\"，\",\"confidence\":0.9697611927986144},{\"character\":\"\",\"confidence\":1.0},{\"character\":\"1\",\"confidence\":0.9999061822891237},{\"character\":\"9\",\"confidence\":0.99998939037323},{\"character\":\"8\",\"confidence\":0.999974012374878},{\"character\":\"5\",\"confidence\":0.9999830722808838},{\"character\":\"年\",\"confidence\":0.9999943971633912},{\"character\":\"6\",\"confidence\":0.9990166425704956}],\"itemstring\":\"熊志,男， 1985年6\"},{\"itemcoord\":{\"x\":426,\"y\":304,\"width\":34,\"height\":22},\"words\":[{\"character\":\"月\",\"confidence\":0.9999946355819702},{\"character\":\"6\",\"confidence\":0.9195330142974854}],\"itemstring\":\"月6\"},{\"itemcoord\":{\"x\":486,\"y\":303,\"width\":244,\"height\":25},\"words\":[{\"character\":\"日\",\"confidence\":0.9995243549346924},{\"character\":\"生\",\"confidence\":0.9993934631347656},{\"character\":\"。\",\"confidence\":0.9999741315841676},{\"character\":\"在\",\"confidence\":0.9998984336853029},{\"character\":\"重\",\"confidence\":0.9999823570251464},{\"character\":\"庆\",\"confidence\":0.9999953508377076},{\"character\":\"邮\",\"confidence\":0.9999994039535524},{\"character\":\"电\",\"confidence\":0.9999786615371704},{\"character\":\"大\",\"confidence\":0.9998039603233336},{\"character\":\"学\",\"confidence\":0.9999852180480956}],\"itemstring\":\"日生。在重庆邮电大学\"},{\"itemcoord\":{\"x\":145,\"y\":356,\"width\":95,\"height\":23},\"words\":[{\"character\":\"软\",\"confidence\":0.9999979734420776},{\"character\":\"件\",\"confidence\":0.9999376535415648},{\"character\":\"工\",\"confidence\":0.999966025352478},{\"character\":\"程\",\"confidence\":0.9999995231628418}],\"itemstring\":\"软件工程\"},{\"itemcoord\":{\"x\":469,\"y\":353,\"width\":323,\"height\":24},\"words\":[{\"character\":\"专\",\"confidence\":0.9999953508377076},{\"character\":\"业\",\"confidence\":0.9999597072601318},{\"character\":\"完\",\"confidence\":0.9999884366989136},{\"character\":\"成\",\"confidence\":0.9999982118606569},{\"character\":\"了\",\"confidence\":0.9999922513961792},{\"character\":\"本\",\"confidence\":0.9999549388885498},{\"character\":\"科\",\"confidence\":0.9999982118606569},{\"character\":\"学\",\"confidence\":0.9999974966049194},{\"character\":\"习\",\"confidence\":0.9999996423721314},{\"character\":\"计\",\"confidence\":0.9999982118606569},{\"character\":\"划\",\"confidence\":1.0},{\"character\":\"，\",\"confidence\":0.9904704093933106},{\"character\":\"业\",\"confidence\":0.999937891960144},{\"character\":\"已\",\"confidence\":0.9903026223182678}],\"itemstring\":\"专业完成了本科学习计划，业已\"},{\"itemcoord\":{\"x\":84,\"y\":403,\"width\":691,\"height\":26},\"words\":[{\"character\":\"毕\",\"confidence\":0.9999997615814208},{\"character\":\"业\",\"confidence\":0.999992847442627},{\"character\":\"，\",\"confidence\":0.9992928504943848},{\"character\":\"经\",\"confidence\":0.9999927282333374},{\"character\":\"审\",\"confidence\":0.9986969828605652},{\"character\":\"核\",\"confidence\":0.9998788833618164},{\"character\":\"符\",\"confidence\":0.9999995231628418},{\"character\":\"合\",\"confidence\":0.9995323419570924},{\"character\":\"《\",\"confidence\":0.9999988079071044},{\"character\":\"中\",\"confidence\":0.9999898672103882},{\"character\":\"华\",\"confidence\":0.9999994039535524},{\"character\":\"人\",\"confidence\":0.9999594688415529},{\"character\":\"民\",\"confidence\":0.999997854232788},{\"character\":\"共\",\"confidence\":0.9999675750732422},{\"character\":\"和\",\"confidence\":0.9999774694442748},{\"character\":\"国\",\"confidence\":0.9999899864196776},{\"character\":\"学\",\"confidence\":0.999990940093994},{\"character\":\"位\",\"confidence\":0.9998984336853029},{\"character\":\"条\",\"confidence\":0.9999732971191406},{\"character\":\"例\",\"confidence\":0.9999960660934448},{\"character\":\"》\",\"confidence\":0.9999693632125856},{\"character\":\"的\",\"confidence\":0.9999773502349854},{\"character\":\"规\",\"confidence\":0.9997580647468568},{\"character\":\"定\",\"confidence\":1.0},{\"character\":\"，\",\"confidence\":0.9973220229148864},{\"character\":\"授\",\"confidence\":0.9980418682098388},{\"character\":\"予\",\"confidence\":0.9767683744430542},{\"character\":\"工\",\"confidence\":0.9999886751174928},{\"character\":\"学\",\"confidence\":0.999911904335022}],\"itemstring\":\"毕业，经审核符合《中华人民共和国学位条例》的规定，授予工学\"},{\"itemcoord\":{\"x\":83,\"y\":452,\"width\":102,\"height\":27},\"words\":[{\"character\":\"学\",\"confidence\":0.9999842643737792},{\"character\":\"士\",\"confidence\":0.9999767541885376},{\"character\":\"学\",\"confidence\":0.9999953508377076},{\"character\":\"位\",\"confidence\":0.9999611377716065},{\"character\":\"。\",\"confidence\":0.9998718500137328}],\"itemstring\":\"学士学位。\"},{\"itemcoord\":{\"x\":465,\"y\":487,\"width\":19,\"height\":24},\"words\":[{\"character\":\"校\",\"confidence\":0.999772012233734}],\"itemstring\":\"校\"},{\"itemcoord\":{\"x\":539,\"y\":487,\"width\":26,\"height\":24},\"words\":[{\"character\":\"长\",\"confidence\":0.9999991655349731}],\"itemstring\":\"长\"},{\"itemcoord\":{\"x\":604,\"y\":481,\"width\":76,\"height\":40},\"words\":[{\"character\":\"张\",\"confidence\":0.999988317489624},{\"character\":\"三\",\"confidence\":0.9998420476913452}],\"itemstring\":\"张三\"},{\"itemcoord\":{\"x\":151,\"y\":512,\"width\":189,\"height\":30},\"words\":[{\"character\":\"重\",\"confidence\":0.9999853372573853},{\"character\":\"庆\",\"confidence\":0.9999868869781494},{\"character\":\"邮\",\"confidence\":0.9999986886978148},{\"character\":\"电\",\"confidence\":0.9999903440475464},{\"character\":\"大\",\"confidence\":0.999018669128418},{\"character\":\"学\",\"confidence\":0.999941349029541}],\"itemstring\":\"重庆邮电大学\"},{\"itemcoord\":{\"x\":436,\"y\":540,\"width\":165,\"height\":21},\"words\":[{\"character\":\"学\",\"confidence\":0.9999929666519164},{\"character\":\"位\",\"confidence\":0.9999829530715942},{\"character\":\"评\",\"confidence\":0.9999980926513672},{\"character\":\"定\",\"confidence\":0.999996781349182},{\"character\":\"委\",\"confidence\":0.9999722242355348},{\"character\":\"员\",\"confidence\":0.999994158744812},{\"character\":\"会\",\"confidence\":0.9999951124191284},{\"character\":\"主\",\"confidence\":0.9999827146530153},{\"character\":\"席\",\"confidence\":0.9999998807907105}],\"itemstring\":\"学位评定委员会主席\"},{\"itemcoord\":{\"x\":83,\"y\":598,\"width\":311,\"height\":21},\"words\":[{\"character\":\"证\",\"confidence\":0.9996412992477416},{\"character\":\"书\",\"confidence\":0.9999622106552124},{\"character\":\"编\",\"confidence\":0.9999592304229736},{\"character\":\"号\",\"confidence\":0.9999998807907105},{\"character\":\":\",\"confidence\":0.955706000328064},{\"character\":\"\",\"confidence\":1.0},{\"character\":\"1\",\"confidence\":0.9998382329940796},{\"character\":\"2\",\"confidence\":0.999991774559021},{\"character\":\"3\",\"confidence\":0.9999518394470216},{\"character\":\"1\",\"confidence\":0.9999626874923706},{\"character\":\"2\",\"confidence\":0.9999771118164064},{\"character\":\"3\",\"confidence\":0.999971866607666},{\"character\":\"1\",\"confidence\":0.99990713596344},{\"character\":\"2\",\"confidence\":0.999979853630066},{\"character\":\"3\",\"confidence\":0.999932050704956},{\"character\":\"\",\"confidence\":1.0},{\"character\":\"1\",\"confidence\":0.999754011631012},{\"character\":\"2\",\"confidence\":0.9999336004257202},{\"character\":\"3\",\"confidence\":0.999834418296814},{\"character\":\"1\",\"confidence\":0.9998595714569092},{\"character\":\"2\",\"confidence\":0.9999653100967408},{\"character\":\"3\",\"confidence\":0.9999687671661376},{\"character\":\"1\",\"confidence\":0.9997356534004213},{\"character\":\"2\",\"confidence\":0.9999876022338868},{\"character\":\"3\",\"confidence\":0.9998337030410768}],\"itemstring\":\"证书编号: 123123123 123123123\"},{\"itemcoord\":{\"x\":588,\"y\":599,\"width\":352,\"height\":25},\"words\":[{\"character\":\"了\",\"confidence\":0.4035078287124634},{\"character\":\"二\",\"confidence\":0.9744670391082764},{\"character\":\"0\",\"confidence\":0.4801431596279145},{\"character\":\"一\",\"confidence\":0.9986901879310608},{\"character\":\"三\",\"confidence\":0.9999860525131226},{\"character\":\"\",\"confidence\":1.0},{\"character\":\"年\",\"confidence\":0.9999974966049194},{\"character\":\"二\",\"confidence\":0.9986225366592408},{\"character\":\"月\",\"confidence\":0.9999979734420776},{\"character\":\"二\",\"confidence\":0.9984059929847716},{\"character\":\"十\",\"confidence\":0.9995089769363404},{\"character\":\"日\",\"confidence\":0.9987095594406128}],\"itemstring\":\"了二0一三 年二月二十日\"},{\"itemcoord\":{\"x\":422,\"y\":632,\"width\":195,\"height\":17},\"words\":[{\"character\":\"(\",\"confidence\":0.9987860321998596},{\"character\":\"成\",\"confidence\":0.9999902248382568},{\"character\":\"人\",\"confidence\":0.999831795692444},{\"character\":\"高\",\"confidence\":0.9999775886535645},{\"character\":\"等\",\"confidence\":0.9996421337127686},{\"character\":\"教\",\"confidence\":0.9999979734420776},{\"character\":\"育\",\"confidence\":1.0},{\"character\":\"本\",\"confidence\":0.9999465942382813},{\"character\":\"科\",\"confidence\":0.99940025806427},{\"character\":\"毕\",\"confidence\":0.9999998807907105},{\"character\":\"业\",\"confidence\":0.9999960660934448},{\"character\":\"生\",\"confidence\":0.999936819076538},{\"character\":\")\",\"confidence\":0.9883082509040833}],\"itemstring\":\"(成人高等教育本科毕业生)\"},{\"itemcoord\":{\"x\":304,\"y\":690,\"width\":111,\"height\":19},\"words\":[{\"character\":\"s\",\"confidence\":0.5702601075172424},{\"character\":\"s\",\"confidence\":0.6175743937492371},{\"character\":\"u\",\"confidence\":0.3447211980819702}],\"itemstring\":\"ssu\"},{\"itemcoord\":{\"x\":577,\"y\":691,\"width\":153,\"height\":18},\"words\":[{\"character\":\"6\",\"confidence\":0.46765390038490298},{\"character\":\"2\",\"confidence\":0.7424090504646301},{\"character\":\"人\",\"confidence\":0.7350156307220459},{\"character\":\"u\",\"confidence\":0.3746616840362549},{\"character\":\"≤\",\"confidence\":0.2451540529727936},{\"character\":\"w\",\"confidence\":0.9323235154151917},{\"character\":\"k\",\"confidence\":0.2357440292835236}],\"itemstring\":\"62人u≤wk\"}],\"session_id\":\"1252836514-684524916\"}}";
        //解析数据
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(str);
        *//*JsonNode jsonNode1 = jsonNode.findValue("itemstring");
        System.out.println(jsonNode1);*//*
        List<JsonNode> nodes = jsonNode.findValues("itemstring");
        String result = "";
*//*        System.out.println(nodes.size());
        System.out.println(nodes.get(1).asText());*//*
        for(int i =0; i<nodes.size(); i++){
            result = result + nodes.get(i).asText();
        }
        System.out.println(result);
        System.out.println(result.replace(" ",""));*/

        String str = "学士学位证书熊志,男，1985年6月6日生。在重庆邮电大学软件工程专业完成了本科学习计划，业已毕业，经审核符合《中华人民共和国学位条例》的规定，授予工学学士学位。校长张三重庆邮电大学学位评定委员会主席证书编号:123123123123123123了二0一三年二月二十日(成人高等教育本科毕业生)ssu62人u≤wk";
        System.out.println(str);
        //字段解析
        DegreeCertificate degreeCertificate = new DegreeCertificate();
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


    }
}
