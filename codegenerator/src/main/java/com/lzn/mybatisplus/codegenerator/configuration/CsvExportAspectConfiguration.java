package com.lzn.mybatisplus.codegenerator.configuration;

import com.lzn.mybatisplus.codegenerator.utils.entity.JsonMapper;
import com.lzn.mybatisplus.codegenerator.utils.entity.Resp;
import com.lzn.mybatisplus.codegenerator.utils.export.AbstractCSVExportService;
import com.lzn.mybatisplus.codegenerator.utils.export.ExportMethod;
import com.lzn.mybatisplus.codegenerator.utils.export.ExportService;
import com.lzn.mybatisplus.codegenerator.utils.redis.IRedisService;
import com.lzn.mybatisplus.codegenerator.utils.redis.RedisServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * csv导出aop实现
 * @auther liuzhinan 741941534@qq.com
 * @date 2020-07-20
 * */
@Aspect
@Configuration
public class CsvExportAspectConfiguration {

    private final Logger logger = LoggerFactory.getLogger(CsvExportAspectConfiguration.class);


    protected static final String EXPORT_TOTAL_COUNT_CACHE_PREFIX = "EXPORT_TOTAL_COUNT_CACHE_PREFIX:";
    protected static final String EXPORT_FINISH_COUNT_CACHE_PREFIX = "EXPORT_FINISH_COUNT_CACHE_PREFIX:";

    @Pointcut("@annotation(com.lzn.mybatisplus.codegenerator.utils.export.ExportMethod)")
    private void exportPoint(){

    }

    @Around("exportPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        // 判断请求参数中是否有 导出 字段,如果没有的话的则走正常的流程
        String export = request.getParameter("export");
        if (export == null) {
            return pjp.proceed();
        }
        request.removeAttribute("export");
        // 并且目标方法中必须得有  ExportExcelMethod 的注解 没有注解则走正常的流程
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if (!targetMethod.isAnnotationPresent(ExportMethod.class)) {
            return pjp.proceed();
        }
        String sequenceId = request.getParameter("sequenceId");
        if(sequenceId == null){
            return pjp.proceed();
        }
        // 获取对应注解指定的导出Excel处理类
        ExportMethod annotation = targetMethod.getAnnotation(ExportMethod.class);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request
                .getServletContext());
        IRedisService redisService  = applicationContext.getBean(RedisServiceImpl.class);

        String memo = annotation.memo();
        // 如果是请求导出
        if(export.equals("export")){
            if (!redisService.exists(EXPORT_TOTAL_COUNT_CACHE_PREFIX.concat(sequenceId))){
                ExportService exportService = applicationContext.getBean(annotation.serviceClass());

                // 设置下载的响应头
                response.setContentType("applicatoin/octet-stream");
                exportService.prepareExport(getParameterMap(request));
                String fileName = exportService.getFileName();
                if(StringUtils.isBlank(fileName)){
                    fileName = "导出";
                }
                String prefix = "";
                if(exportService instanceof AbstractCSVExportService){
                    prefix = ".csv";
                }
//                else if(exportManager instanceof ExcelExportManager){
//                    prefix = ".xlsx";
//                }
                response.setHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1") + prefix);
                exportService.setSequenceId(sequenceId);
                exportService.download(response.getOutputStream());
            }
            return null;
        }else if(export.equals("getProcess")){
            int process = getProcess(redisService,sequenceId);
            logger.info("当前导出进度:{}",process);
            if(process == 100){
                //清空下进度数据
                clearExportProgressData(redisService,sequenceId);
            }
            responseWithJson(response,new Resp("SUCCESS",String.valueOf(process)));
            return null;
        }
        return null;
    }


    private void clearExportProgressData(IRedisService redisService, String sequenceId) {
       redisService.del(EXPORT_TOTAL_COUNT_CACHE_PREFIX.concat(sequenceId));
    }

    protected void responseWithJson(HttpServletResponse response, Object data) {
        //将实体对象转换为JSON Object转换
        String jsonData = new JsonMapper().toJson(data);;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private Map<String,Object> getParameterMap(HttpServletRequest request){
        Map<String,Object> searchMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if(value == null || value.length <= 0){
                continue;
            }
            if (value.length == 1){
                searchMap.put(key,value[0]);
            }else {
                searchMap.put(key, Arrays.asList(value));
            }
        }
        return searchMap;
    }

    public int getProcess(IRedisService redisService,String sequenceId) {
        String totalCache = EXPORT_TOTAL_COUNT_CACHE_PREFIX.concat(sequenceId);
        Long totalCount;
        if (redisService.exists(totalCache)) {
            totalCount = redisService.incrBy(totalCache, 0);
        }else {
            totalCount = -1L;
        }
        if(totalCount < 0){
            return 0;
        }
        if(totalCount == 0){
            return 100;
        }
        Long finishCount = redisService.incrBy(EXPORT_FINISH_COUNT_CACHE_PREFIX.concat(sequenceId), 0);
        if(finishCount >= totalCount){
            return 100;
        }
        return (int)(finishCount * 100L /totalCount);
    }



}
