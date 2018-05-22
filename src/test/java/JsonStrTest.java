import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class JsonStrTest {

    @Test
    public void Test1() throws IOException {
        String str = "{\n" +
                "    \"result_list\": [\n" +
                "        {\n" +
                "            \"code\": 0,\n" +
                "            \"message\": \"OK\",\n" +
                "            \"filename\": \"tmp3828762133631802472.tmp\",\n" +
                "            \"data\": {\n" +
                "                \"name\": \"邓欲国\",\n" +
                "                \"sex\": \"男\",\n" +
                "                \"nation\": \"汉\",\n" +
                "                \"birth\": \"1973/9/4\",\n" +
                "                \"address\": \"江苏省昆山市玉山镇汛塘商苑10幢203室\",\n" +
                "                \"id\": \"320523197309040018\",\n" +
                "                \"name_confidence_all\": [\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99\n" +
                "                ],\n" +
                "                \"sex_confidence_all\": [\n" +
                "                    99\n" +
                "                ],\n" +
                "                \"nation_confidence_all\": [\n" +
                "                    99\n" +
                "                ],\n" +
                "                \"birth_confidence_all\": [\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100\n" +
                "                ],\n" +
                "                \"address_confidence_all\": [\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99,\n" +
                "                    99\n" +
                "                ],\n" +
                "                \"id_confidence_all\": [\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100,\n" +
                "                    100\n" +
                "                ]\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(str);
        System.out.println(jsonNode.findValue("code"));
        System.out.println(jsonNode.findValue("data"));
        System.out.println(jsonNode.findValue("name"));
    }
}
