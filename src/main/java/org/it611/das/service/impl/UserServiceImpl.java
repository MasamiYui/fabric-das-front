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
        if(CheckUtil.checkMobileNumber(loginStr) == true) {
            userMap = userDao.findUserByPhone(loginStr);
        }else if(CheckUtil.checkEmail(loginStr) == true) {
            userMap = userDao.findUserByEmail(loginStr);
        }

/*        if((int)userMap.get("state") != 1) {
            return ResultUtil.constructResponse(400,"the state not 1, can do anything", null);
        }*/

        if(userMap == null){
            return ResultUtil.constructResponse(400,"fail.error phone and email", null);
        }

        if(!MD5Util.verify(password,userMap.get("password").toString())) {
            return  ResultUtil.constructResponse(400, "password error.", null);
        }


        String token = UUID.randomUUID().toString();
        userMap.remove("password");
        Jedis client = RedisUtil.getJedis();
        client.set(token, new ObjectMapper().writeValueAsString(userMap));
        return ResultUtil.constructResponse(200,"ok", token);
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
    public JSONObject companyLogin(String loginStr, String password) {
        return null;
    }

    @Override
    public JSONObject companyRegister(CompanyVO companyVO) {

        Company company = Vo2PoUtil.ConpanyVo2Po(companyVO);
        int result = companyDao.addCompany(company);

        if (result == 0) {
            return ResultUtil.constructResponse(400, "failed to register company user.", null);
        }
        //TODO 其他验证还未完成

        return ResultUtil.constructResponse(200, "ok", null);
    }
}
