import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.util.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;

public class JsonTest {


    @Test
    public void Test1() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", "440825198912061711");
        map2.put("data.drivingLicenceId", map1);
        map3.put("selector", map2);


        String result = objectMapper.writeValueAsString(map3);
        System.out.println(result);

    }


    @Test
    public void Test2() throws Exception {

        //构造json参数
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map1 = new HashMap();
        HashMap<String, Object> map2 = new HashMap();
        HashMap<String, Object> map3 = new HashMap();

        map1.put("$eq", "440825198912061712");
        map2.put("data.drivingLicenceId", map1);
        map3.put("selector", map2);


        String jsonStr = objectMapper.writeValueAsString(map3);

        //调用CouchDB HTTP API，获取fabric的state数据库
        String retStr = HttpClientUtil.doPostJson("http://192.168.10.128:5984/mychannel/_find", jsonStr);
/*
        JsonNode jsonNode = objectMapper.readTree(retStr);
        JsonNode dataNode = jsonNode.findValue("data");

        //jsonNode转HashMap
        HashMap dataMap = objectMapper.readValue(dataNode.toString(), HashMap.class);
*/





    }

}
