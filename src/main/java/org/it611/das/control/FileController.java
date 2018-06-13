package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.image.ImageClient;
import com.qcloud.image.request.GeneralOcrRequest;
import org.it611.das.domain.DegreeCertificate;
import org.it611.das.fastdfs.FastDFSClient;
import org.it611.das.util.GeneralOcrParseUtil;
import org.it611.das.util.MD5Util;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResultUtil;
import org.it611.das.ocr.OcrExample;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject singleFileUpload(MultipartFile file, HttpServletRequest httpServletRequest) throws IOException {

        if (file.isEmpty()) {
            return ResultUtil.constructResponse(400,"empty file.",null);
        }
        String fileHash = MD5Util.md5HashCode(file.getInputStream());
        String path=FastDFSClient.saveFile(file);//将文件上传到fastDFS，返回http url
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("path", path);
        Jedis client = RedisUtil.getJedis();
        client.set(path, fileHash);
        client.close();
        System.out.println("url:"+path);

        return ResultUtil.constructResponse(200,"ok",dataMap);
    }

    @RequestMapping(value = "/file/idcard/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject idCardUpload(MultipartFile file) throws IOException {

        Map<String, Object> orcMap = new HashMap();
        if (file.isEmpty()) {
            return ResultUtil.constructResponse(400,"empty file.",null);
        }
        String fileHash = MD5Util.md5HashCode(file.getInputStream());
        String path=FastDFSClient.saveFile(file);//将文件上传到fastDFS，返回http url
        //腾讯ocr识别
        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        File f = File.createTempFile("tmp", null);
        file.transferTo(f);
        String ret = OcrExample.ocrIdCard(imageClient,"yui-1252836514", f);

        System.out.println(ret);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(ret);
        String resultCode = jsonNode.findValue("code").toString();
        if(!resultCode.equals("0")) {
            return ResultUtil.constructResponse(400,"Cannot identify the identity card.",null);
        }
        JsonNode dataNode = jsonNode.findValue("data");
        orcMap.put("name", dataNode.findValue("name"));
        orcMap.put("sex",dataNode.findValue("sex"));
        orcMap.put("nation", dataNode.findValue("nation"));
        orcMap.put("date",dataNode.findValue("birth"));
        orcMap.put("idcard",dataNode.findValue("id"));
        orcMap.put("address",dataNode.findValue("address"));

        //f.deleteOnExit();
        Map<String,Object> dataMap = new HashMap();
        dataMap.put("path", path);
        dataMap.put("ocrData",orcMap);
        Jedis client = RedisUtil.getJedis();
        client.set(path, fileHash);
        client.close();
        return ResultUtil.constructResponse(200,"ok",dataMap);
    }


    @RequestMapping(value = "/file/degreeCert/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject degreeCertUpload(MultipartFile file) throws Exception {

        //Map<String, Object> orcMap = new HashMap();
        if (file.isEmpty()) {
            return ResultUtil.constructResponse(400,"empty file.",null);
        }
        String fileHash = MD5Util.md5HashCode(file.getInputStream());
        String path=FastDFSClient.saveFile(file);//将文件上传到fastDFS，返回http url
        //腾讯ocr识别
        ImageClient imageClient = new ImageClient("1252836514", "AKID0tcB3ktk086uhNwGSTK1XtDQVP8gNGIR", "iMp7FE6qVzJYcVEm9f3IMFas33tcDgy5");
        File f = File.createTempFile("tmp", null);
        file.transferTo(f);
        GeneralOcrRequest request = new GeneralOcrRequest("yui-1252836514", f);
        String ret = imageClient.generalOcr(request);
        //解析数据
        DegreeCertificate dc = GeneralOcrParseUtil.parseDegreeCertData(ret);

        //f.deleteOnExit();
        Map<String,Object> dataMap = new HashMap();
        dataMap.put("path", path);
        dataMap.put("ocrData",dc);
        Jedis client = RedisUtil.getJedis();
        client.set(path, fileHash);
        client.close();
        return ResultUtil.constructResponse(200,"ok",dataMap);
    }


}
