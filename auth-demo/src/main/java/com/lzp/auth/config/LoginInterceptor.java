package com.lzp.auth.config;


import com.lzp.auth.exception.ServiceException;
import com.lzp.auth.utils.JWTUtils;
import com.lzp.auth.utils.ResultCodeEnum;
import com.lzp.auth.utils.SessionContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String requestURI = request.getRequestURI().replaceAll("/+", "/");

        log.info("requestURI:{}",requestURI);

        if (StringUtils.isEmpty(token)) {
            throw new ServiceException(ResultCodeEnum.AUTH_FAIL);
        }

        Claims claim = JWTUtils.getClaim(token);
        if(claim == null){
            throw new ServiceException(ResultCodeEnum.AUTH_FAIL);
        }

        String uid = null;
        try {
            uid = JWTUtils.getOpenId(token);
        } catch (Exception e) {
            throw new ServiceException(ResultCodeEnum.AUTH_FAIL);
        }

        //用户id放到上下文 可以当前请求进行传递
        request.setAttribute(SessionContext.USER_ID_KEY, uid);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}
