package org.it611.das.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.it611.das.domain.Company;
import org.it611.das.domain.User;
import org.it611.das.mapper.CompanyMapper;
import org.it611.das.mapper.UserMapper;
import org.it611.das.service.UserService;
import org.it611.das.util.*;
import org.it611.das.vo.CompanyVO;
import org.it611.das.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    @Autowired
    private CompanyMapper companyDao;


    @Override
    public JSONObject userLogin(String loginStr, String password) throws JsonProcessingException {
        Map<String, Object> userMap = null;
        Map<String, Object> companyMap = null;
        Map<String, Object> loginMap = null;
        Map<String, Object> resultMap = new HashMap();
        if(CheckUtil.checkMobileNumber(loginStr)) {
            userMap = userDao.findUserByPhone(loginStr);
            companyMap = companyDao.findCompanyByPhone(loginStr);

        }else if(CheckUtil.checkEmail(loginStr)) {
            userMap = userDao.findUserByEmail(loginStr);
            companyMap = companyDao.findCompanyByEmail(loginStr);
        }

        if(userMap != null && companyMap == null){//普通用户登陆
            loginMap = userMap;
            loginMap.put("userType", 1);//1---普通用户
        }else if(userMap ==null && companyMap!= null){//企业用户登陆
            loginMap = companyMap;
            loginMap.put("userType", 2);//2---企业用户
        }else{
            return ResultUtil.constructResponse(400,"fail.error phone and email", null);
        }


        if(Integer.parseInt(loginMap.get("state").toString()) != 1) {  //code:405 表示用户帐号未激活
            return ResultUtil.constructResponse(405,"the state not 1, can not do anything", null);
        }


        if(!MD5Util.verify(password,loginMap.get("password").toString())) {
            return  ResultUtil.constructResponse(400, "password error.", null);
        }

        String token = UUID.randomUUID().toString();
        loginMap.remove("password");
        loginMap.put("userType", loginMap.get("userType"));

        Jedis client = RedisUtil.getJedis();
        client.set(token, new ObjectMapper().writeValueAsString(loginMap));
        client.close();

        resultMap.put("token", token);
        resultMap.put("userType",loginMap.get("userType"));
        return ResultUtil.constructResponse(200,"ok", resultMap);
    }

    @Override
    public JSONObject userRegister(UserVO userVO) {
        User user = Vo2PoUtil.userVo2Po(userVO);

        if(userDao.findUserByPhone(userVO.getPhone()) != null) {
            return ResultUtil.constructResponse(400, "the phone have registed.", null);
        }

        if(userDao.findUserByPhone(userVO.getEmail()) != null) {
            return ResultUtil.constructResponse(400, "the email have registed.", null);
        }

        if(userDao.findUserByIdCard(userVO.getIdcard()) != null) {
            return ResultUtil.constructResponse(400, "the idcard have registed.", null);
        }

        int result = userDao.addUser(user);
        if(result == 0){
            return ResultUtil.constructResponse(400, "insert user failed.", null);
        }

        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public JSONObject companyLogin(String loginStr, String password) throws JsonProcessingException {

        HashMap<String, Object> companyMap = null;
        if(CheckUtil.checkEmail(loginStr)){
            companyMap = companyDao.findCompanyByEmail(loginStr);
        }else if(CheckUtil.checkMobileNumber(loginStr)) {
            companyMap = companyDao.findCompanyByPhone(loginStr);
        }else {
            companyMap = companyDao.findCompanyByUsername(loginStr);
        }
        if(companyMap == null) {
            return ResultUtil.constructResponse(400, "not current username phone or email.", null);
        }

        if(!MD5Util.verify(password, companyMap.get("password").toString())) {
            return ResultUtil.constructResponse(400, "password error.", null);
        }

        String token = UUID.randomUUID().toString();
        companyMap.remove("password");
        Jedis client = RedisUtil.getJedis();
        client.set(token, new ObjectMapper().writeValueAsString(companyMap));
        client.close();

        return ResultUtil.constructResponse(200, "ok", token);

    }

    @Override
    public JSONObject companyRegister(CompanyVO companyVO) {

        Company company = Vo2PoUtil.ConpanyVo2Po(companyVO);

        HashMap<String, Object> resultMap = companyDao.findCompanyByCreditId(company.getCreditId());

        if(resultMap!=null) {

            return ResultUtil.constructResponse(400, "the credit id have registered.", null);
        }

        String ss=company.getCompanyEmail();

        resultMap = companyDao.findCompanyByEmail(ss);


        if(resultMap!=null) {
            return ResultUtil.constructResponse(400, "the email have registered.", null);
        }

        resultMap = companyDao.findCompanyByUsername(company.getUsername());
        if(resultMap!=null) {
            return ResultUtil.constructResponse(400, "the username have registered.", null);
        }

        int result = companyDao.addCompany(company);

        if (result == 0) {
            return ResultUtil.constructResponse(400, "failed to register company.", null);
        }


        return ResultUtil.constructResponse(200, "ok", null);
    }

    @Override
    public JSONObject updatePass(String id, String useType, String oldPass, String newPass) {
        Map<String, Object> userMap = null;
       int result=-1 ;
        String dataBasePass = "";
        if("1".equals(useType)){
            userMap = userDao.findUserById(id);
            dataBasePass = (String) userMap.get("password");
        }else if("2".equals(useType)){
            userMap = companyDao.findUserById(id);
            dataBasePass = (String) userMap.get("password");
        }else{
            return ResultUtil.constructResponse(400, "无此用户类型", null);
        }
        if(!MD5Util.verify(oldPass,dataBasePass)) {
            return  ResultUtil.constructResponse(400, "password error.", null);
        }
        if("1".equals(useType)){
            result = userDao.updateUserById(id,MD5Util.generate(newPass));
        }else if("2".equals(useType)){

            result = companyDao.updateUserById(id,MD5Util.generate(newPass));
        }
        if(result>0){
            return  ResultUtil.constructResponse(200, "ok", null);
        }else{
            return  ResultUtil.constructResponse(400, "failed", null);
        }
    }
}
