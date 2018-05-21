package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.fastdfs.FastDFSClient;
import org.it611.das.util.MD5Util;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject singleFileUpload(MultipartFile file) throws IOException {

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
        return ResultUtil.constructResponse(200,"ok",dataMap);
    }
}
