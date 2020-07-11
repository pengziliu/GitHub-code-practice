package com.lzp.auth.utils;

/**
 * @author Robin
 * @date 2020/7/4 4:58 下午
 * Describe
 */

public enum ResultCodeEnum {
    SUCCESS(1, "请求成功"),
    FAIL(0, "失败"),
    AUTH_FAIL(999001000, "权限不足"),
    LOGIN_FAIL(999001001, "登陆失败"),
    PARAMS_IS_NULL(999001002, "参数为空错误"),
   ;

    private int code;
    private String msg;

    private ResultCodeEnum() {
    }

    private ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "ResultCodeEnum [code=" + this.code + ", msg=" + this.msg + "]";
    }
}
