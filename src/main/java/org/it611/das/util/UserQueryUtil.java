package org.it611.das.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

public class UserQueryUtil {

    private static final Jedis client = RedisUtil.getJedis();

    public static String getUserIdByCookieAndRedis(HttpServletRequest request) throws IOException {
        String token = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
        //Jedis client = RedisUtil.getJedis();
        String loginJsonStr = client.get(token);
        HashMap loginMap = new ObjectMapper().readValue(loginJsonStr, HashMap.class);
        String userId = loginMap.get("id").toString();
        return  userId;
    }
}
