package org.it611.das.config;


import org.it611.das.util.CookieUtil;
import org.it611.das.util.RedisUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginFilter",urlPatterns = {"/*"})
public class LoginFilter implements Filter {


    //不需要登录就可以访问的路径(比如:注册登录等)
    String[] includeUrls = new String[]{"/","/frontLogin","/userReg","/companyReg","/user/login","/comapny/login","/user/register","/comapny/register","/file/idcard/upload",
    "/file/bizLicence/upload"};


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
       // HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        System.out.println("filter url:"+uri);
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);


        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            //获取token
            String token = CookieUtil.getCookie(request,CookieUtil.COOKIE_TOKEN_KEY);
            if (token == null || token.equals("")){
                response.sendRedirect(request.getContextPath()+"/frontLogin");//跳转到登陆页
                //return false;
                return;
            }
            //与redis中的数据进行比较
            Jedis redisClient = RedisUtil.getJedis();
            if(redisClient.get(token) == null || redisClient.get(token).equals("")) {
                response.sendRedirect(request.getContextPath()+"/frontLogin");//跳转到登陆页
                //return false;
                return;
            }
            redisClient.close();
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }


    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if(includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}