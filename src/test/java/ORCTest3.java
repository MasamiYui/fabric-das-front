import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.result.BizcardResultParser;
import com.qcloud.image.ImageClient;
import com.qcloud.image.request.OcrBizLicenseRequest;
import com.qcloud.image.request.OcrDrivingLicenceRequest;
import org.it611.das.domain.DrivingLicence;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ORCTest3 {

    public static void main(String[] args) throws IOException {
/*        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        OcrDrivingLicenceRequest request = new OcrDrivingLicenceRequest("yui-1252836514", OcrDrivingLicenceRequest.TYPE_DRIVER_LICENSE, new File("/home/yui/Desktop/驾照模板/驾照模板2.jpg"));
        String ret = imageClient.ocrDrivingLicence(request);
        System.out.println(ret);*/

/*        String ret = "{\"code\":0,\"message\":\"OK\",\"data\":{\"session_id\":\"12528365141647509034\",\"items\":[{\"item\":\"证号\",\"itemcoord\":{\"x\":429,\"y\":107,\"width\":422,\"height\":58},\"itemconf\":0.8524576425552368,\"itemstring\":\" \"},{\"item\":\"性别\",\"itemcoord\":{\"x\":592,\"y\":182,\"width\":47,\"height\":53},\"itemconf\":0.9999585151672364,\"itemstring\":\"女\"},{\"item\":\"国籍\",\"itemcoord\":{\"x\":784,\"y\":185,\"width\":89,\"height\":49},\"itemconf\":0.9999997615814208,\"itemstring\":\"中国\"},{\"item\":\"住址\",\"itemcoord\":{\"x\":128,\"y\":245,\"width\":837,\"height\":108},\"itemconf\":0.8440147638320923,\"itemstring\":\"河北省唐山丰润区区右家坞镇石窝村五区33号\"},{\"item\":\"出生日期\",\"itemcoord\":{\"x\":426,\"y\":384,\"width\":236,\"height\":48},\"itemconf\":0.7925066947937012,\"itemstring\":\"1969-01-02\"},{\"item\":\"领证日期\",\"itemcoord\":{\"x\":487,\"y\":449,\"width\":253,\"height\":52},\"itemconf\":0.8072455525398254,\"itemstring\":\"2012-06-19\"},{\"item\":\"准驾车型\",\"itemcoord\":{\"x\":531,\"y\":527,\"width\":96,\"height\":45},\"itemconf\":1.0,\"itemstring\":\"B2\"},{\"item\":\"起始日期\",\"itemcoord\":{\"x\":257,\"y\":601,\"width\":229,\"height\":47},\"itemconf\":0.9223758578300476,\"itemstring\":\"2012-06-19\"},{\"item\":\"红章\",\"itemcoord\":{\"x\":32,\"y\":385,\"width\":216,\"height\":191},\"itemconf\":0.9997324347496032,\"itemstring\":\"河北省唐山市公安局交通警察支队\"}]}}";
        //解析数据
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ret);
        List<JsonNode> itemNodes = jsonNode.findParents("item");
        System.out.println(itemNodes.size());
        DrivingLicence drivingLicence = new DrivingLicence();
        drivingLicence.setDrivingLicenceId(itemNodes.get(0).get("itemstring").asText());
        drivingLicence.setSex(itemNodes.get(1).get("itemstring").asText());
        drivingLicence.setNation(itemNodes.get(2).get("itemstring").asText());
        drivingLicence.setAddress(itemNodes.get(3).get("itemstring").asText());
        drivingLicence.setDate(itemNodes.get(4).get("itemstring").asText());
        drivingLicence.setLzDate(itemNodes.get(5).get("itemstring").asText());
        drivingLicence.setZjcx(itemNodes.get(6).get("itemstring").asText());
        drivingLicence.setStartDate(itemNodes.get(7).get("itemstring").asText());
        drivingLicence.setHz(itemNodes.get(8).asText());

        System.out.println(drivingLicence);*/


/*        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        OcrBizLicenseRequest request = new OcrBizLicenseRequest("yui-1252836514",  new File("//home/yui/Desktop/营业执照/3.jpeg"));
        String ret = imageClient.ocrBizLicense(request);
        System.out.println(ret);*/

/*        String str = "{\"code\":0,\"message\":\"OK\",\"data\":{\"session_id\":\"12528365141186129408\",\"items\":[{\"item\":\"注册号\",\"itemcoord\":{\"x\":681,\"y\":568,\"width\":188,\"height\":21},\"itemconf\":0.9999757409095764,\"itemstring\":\"91310113688758337E\",\"coords\":[],\"words\":[],\"candword\":[]},{\"item\":\"法定代表人\",\"itemcoord\":{\"x\":286,\"y\":746,\"width\":89,\"height\":26},\"itemconf\":0.9995861053466796,\"itemstring\":\"蔡元胜\",\"coords\":[],\"words\":[],\"candword\":[]},{\"item\":\"公司名称\",\"itemcoord\":{\"x\":286,\"y\":632,\"width\":241,\"height\":27},\"itemconf\":0.9998612403869628,\"itemstring\":\"上海君希鞋业有限公司\",\"coords\":[],\"words\":[],\"candword\":[]},{\"item\":\"地址\",\"itemcoord\":{\"x\":294,\"y\":710,\"width\":351,\"height\":28},\"itemconf\":0.9636003375053406,\"itemstring\":\"上海市宝山区盛桥新村1弄30号\",\"coords\":[],\"words\":[],\"candword\":[]},{\"item\":\"营业期限\",\"itemcoord\":{\"x\":268,\"y\":868,\"width\":451,\"height\":26},\"itemconf\":0.9996572136878968,\"itemstring\":\"2009年5月11日至2019年5月10日\",\"coords\":[],\"words\":[],\"candword\":[]}]}}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(str);
        List<JsonNode> itemNodes = jsonNode.findParents("item");
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
        System.out.println(resultMap);*/
        //size = 5
        //return resultMap;


    }
}
