package com.lzp.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * RedisKey 枚举类
 */
@Getter
public enum RedisKeyEnum {

    /**
     * key较多的情况：不定义具体的key, key当参数传给keyBuilder
     */
    OAUTH_APP_TOKEN("AUTH-DEMO", "USER", "TOKEN"),

    ;

    /**
     * 系统标识
     */
    private String keyPrefix;
    /**
     * 模块名称
     */
    private String module;

    /**
     * key
     */
    private String key;

    RedisKeyEnum(String keyPrefix, String module) {
        this.keyPrefix = keyPrefix;
        this.module = module;
    }

    RedisKeyEnum(String keyPrefix, String module, String key) {
        this.keyPrefix = keyPrefix;
        this.module = module;
        this.key = key;
    }

    public String keyBuilder() {
        return checkAndGetValue(key);
    }

    public String keyBuilder(String key) {
        return checkAndGetValue(key);
    }

    private String checkAndGetValue(String key) {
        Assert.notNull(keyPrefix, "RedisKeyEnum: keyPrefix can not be null");
        Assert.notNull(module, "RedisKeyEnum: module can not be null");
        Assert.notNull(key, "RedisKeyEnum: key can not be null");
        return keyPrefix + ":" + module + ":" +  key;
    }

    /***
     * Redis常量key在此枚举中申明
     *
     * @author hulilei
     * @date 2019/6/11
     */
    @Getter
    @AllArgsConstructor
    enum ConstantKeyEnum {
        /**
         * 常量Key样例
         */
        LMDM_TOKEN_EXPIRE_TIME("LMDM:TOKEN:EXPIRE:TIME", "token过期时间"),
        LMDM_SMS_CODE_EXPIRE("LMDM:SMS:CODE:EXPIRE", "短信验证码过期时间");
        String key;
        String remark;
    }

}

