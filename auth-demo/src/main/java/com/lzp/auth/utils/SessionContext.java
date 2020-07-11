package com.lzp.auth.utils;

import com.alibaba.fastjson.JSONObject;
import com.lzp.auth.exception.ServiceException;
import com.lzp.auth.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * 云路供应链科技有限公司 版权所有  Copyright 2019<br>
 *
 * @Description:
 * @Project:
 * @CreateDate: Created in 2019-08-06 11:07 <br>
 * @Author: zhipeng.liu
 */

@Slf4j
@Component
public class SessionContext {

    @Autowired
    private  RedisUtils redisUtils;

    public final static String USER_ID_KEY = "_unid";


    public UserVO getUser() {

        String uid = getUid();
        Object obj = redisUtils.get(RedisKeyEnum.OAUTH_APP_TOKEN.keyBuilder(uid));
        if (obj == null) {
            throw new ServiceException(ResultCodeEnum.AUTH_FAIL);
        }
        UserVO loginVO = JSONObject.parseObject(obj.toString(), UserVO.class);
        if (loginVO == null) {
            throw new ServiceException(ResultCodeEnum.AUTH_FAIL);
        }
        return loginVO;
    }

    protected String getUid() {
        String unid = RequestContextHolder.getRequestAttributes().getAttribute(USER_ID_KEY, 0).toString();
        if (null == unid) {
            log.error(" === 请在登录后调用此接口 ====");
            throw new ServiceException(ResultCodeEnum.AUTH_FAIL);
        }
        return unid;
    }

}
