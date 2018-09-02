package org.it611.das.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 *
 */
public class CookieUtil {

    public static final int COOKIE_MAX_AGE = 7 * 24 * 3600;
    public static final int COOKIE_HALF_HOUR = 30 * 60;
    public static final int COOKIE_ONE_HOUR= 60*60;
    public static final int COOKIE_ONE_DAY= 60*60*24;
    public static final String COOKIE_TOKEN_KEY = "loginToken";

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param name
     */
    public static void removeCookie(HttpServletResponse response, String name) {
        Cookie uid = new Cookie(name, null);
        uid.setPath("/");
        uid.setMaxAge(0);
        response.addCookie(uid);
    }


    //删除cookie方法2
    public static void removeCookie(HttpServletRequest request,HttpServletResponse response,String name) {
        try {

            Cookie[] cookies = request.getCookies();

            for (int i = 0; i < (cookies == null ? 0 : cookies.length); i++) {
                if ((name).equalsIgnoreCase(cookies[i].getName())) {

                    Cookie cookie = new Cookie(name, "");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);// 设置保存cookie最大时长为0，即使其失效
                    response.addCookie(cookie);
                }
            }

        } catch (Exception e) {
            System.out.println(" -------删除cookie失败！--------" + e.getMessage());
        }
    }


    /**
     * 获取cookie值
     *
     * @param request
     * @return
     */
    public static String getCookie(HttpServletRequest request,String cookieName) {
        Cookie cookies[] = request.getCookies();

        if(cookies == null){
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}