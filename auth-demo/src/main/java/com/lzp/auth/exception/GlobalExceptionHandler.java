package com.lzp.auth.exception;


import com.lzp.auth.vo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final static String RESPONSE_DATA = "responseData";

    /**
     * 自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public ApiResult resolveServiceException(ServiceException ex) {
        logger.error("自定义异常",  ex.getMessage());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//TODO 配合链路日志
        ApiResult result = ApiResult.error(ex.getCode(), ex.getMessage());
        request.setAttribute(RESPONSE_DATA, result);
        return result;
    }
}
