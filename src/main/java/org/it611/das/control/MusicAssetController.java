package org.it611.das.control;

import com.alibaba.fastjson.JSONObject;
import org.it611.das.service.MusicAssetService;
import org.it611.das.util.ResponseUtil;
import org.it611.das.vo.MusicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class MusicAssetController {

    @Autowired
    private MusicAssetService musicAssetService;

    //新增音频资产
    @RequestMapping("/asset/music/add")
    @ResponseBody
    public JSONObject addMusic(HttpServletRequest request, MusicVO musicVO) throws IOException {

        JSONObject resultData = musicAssetService.addMusic(musicVO, request);
        return resultData;
    }

    //返回音频资产详情
    @RequestMapping("/musicDetail")
    public String musicDetail(Model model, String id){
        HashMap record = musicAssetService.selectMusicDetailById(id);

        model.addAttribute("record",record );
        return "musicDetail";
    }


    //返回音频资产列表
    @RequestMapping("/musicAssetListIndex")
    public String musicAssetList(){return "musicAsset_list";}
    @RequestMapping("/musicAsset/musicList")
    @ResponseBody
    public JSONObject musicList(HttpServletRequest request, int currentPage, int numberOfPages) throws IOException {
        HashMap<String ,Object> result=musicAssetService.selectMusicAssetList(request, currentPage,numberOfPages);
        return ResponseUtil.constructResponse(200, "OK", result);
    }

    //返回音频资产申请表单
    @RequestMapping("/musicForm")
    public String videoForm(){return "insert_musicAssert";}


    //音频链接地址播放
    @RequestMapping("/musicPalyLink")
    public ModelAndView videoPalyLink(String linkAddress){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("videoUrl", linkAddress);
        modelAndView.setViewName("videoDetailPlay");
        return modelAndView;
    }


    //音二维码扫描结果
    @RequestMapping("/musicQrcodeScanResult")
    public ModelAndView QrcodeScanResult(String files){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("filesPath",files);
        modelAndView.setViewName("QRPhoneMusicList");
        return modelAndView;
    }

}
