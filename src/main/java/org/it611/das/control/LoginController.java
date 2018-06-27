package org.it611.das.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.it611.das.fabric.ChaincodeManager;
import org.it611.das.fabric.util.FabricManager;
import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import org.it611.das.util.TimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.HashMap;

@Controller
public class LoginController {
    @RequestMapping("/frontLogin")
    public String frontLogin(){  return "login";}

    @RequestMapping(value = "/")
    public ModelAndView index() throws InvalidArgumentException, ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, IllegalAccessException, NoSuchProviderException, TransactionException, ClassNotFoundException, ParseException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        //查询区块高度，返回给前台
        ChaincodeManager manager = FabricManager.obtain().getManager();
        Channel channel = manager.getChannelInstance();
        Long nowHeight = channel.queryBlockchainInfo().getHeight();//查询当前的区块高度
        modelAndView.addObject("blockHigh",nowHeight);//区块高度
        modelAndView.addObject("runTime",TimeUtil.serverTime());//服务时间

        return modelAndView;
    }

    @RequestMapping("/home")
    public ModelAndView   loginHome(HttpServletRequest request) throws IOException, IllegalAccessException, NoSuchAlgorithmException, InvocationTargetException, NoSuchMethodException, InstantiationException, InvalidKeySpecException, CryptoException, InvalidArgumentException, NoSuchProviderException, TransactionException, ClassNotFoundException, ProposalException, ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Jedis jedis = RedisUtil.getJedis();
        String userToken = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        HashMap userMap = new ObjectMapper().readValue(jedis.get(userToken), HashMap.class);
        String useType= String.valueOf(userMap.get("userType"));
        //查询区块高度，返回给前台
        ChaincodeManager manager = FabricManager.obtain().getManager();
        Channel channel = manager.getChannelInstance();
        Long nowHeight = channel.queryBlockchainInfo().getHeight();//查询当前的区块高度
        modelAndView.addObject("blockHigh",nowHeight);//区块高度
        modelAndView.addObject("runTime",TimeUtil.serverTime());//服务时间


        if(useType.equals("1")){   //1:表示普通用户
            modelAndView.addObject("loginName",userMap.get("name"));
            modelAndView.addObject("useType",useType);
            modelAndView.setViewName("home_userIndex");
        }else if(useType.equals("2")){  //2:企业用户
            modelAndView.addObject("loginName",userMap.get("companyName"));
            modelAndView.addObject("useType",useType);
            modelAndView.setViewName("home_companyIndex");
        }


        jedis.close();
        return modelAndView;

    }

}
