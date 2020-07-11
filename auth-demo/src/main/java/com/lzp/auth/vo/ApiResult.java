package com.lzp.auth.vo;

import com.lzp.auth.utils.ResultCodeEnum;

import java.util.Map;

public class ApiResult<T> {
    private Integer code;
    private String msg;
    private String version;
    private T data;

    private ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ApiResult(ResultCodeEnum ResultCodeEnum) {
        if (ResultCodeEnum != null) {
            this.code = ResultCodeEnum.getCode();
            this.msg = ResultCodeEnum.getMsg();
        }

    }

    private ApiResult(T data, String version) {
        this.code = ResultCodeEnum.SUCCESS.getCode();
        this.msg = ResultCodeEnum.SUCCESS.getMsg();
        this.version = version;
        this.data = data;
    }


    public static <T> ApiResult<T> success(T data, String version) {
        return new ApiResult(data, version);
    }

    public static <T> ApiResult<T> success(Map<Object, String> obj) {
        return new ApiResult(obj.get("data"), (String)obj.get("version"));
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult(ResultCodeEnum.SUCCESS);
    }

    public static <T> ApiResult<T> success(Integer code, String msg) {
        return new ApiResult(code, msg);
    }

    public static <T> ApiResult<T> error(ResultCodeEnum ResultCodeEnum) {
        return new ApiResult(ResultCodeEnum);
    }

    public static <T> ApiResult<T> error(Integer code, String msg) {
        return new ApiResult(code, msg);
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getVersion() {
        return this.version;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public void setData(final T data) {
        this.data = data;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiResult)) {
            return false;
        } else {
            ApiResult<?> other = (ApiResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$version = this.getVersion();
                Object other$version = other.getVersion();
                if (this$version == null) {
                    if (other$version != null) {
                        return false;
                    }
                } else if (!this$version.equals(other$version)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ApiResult;
    }


    @Override
    public String toString() {
        return "ApiResult(code=" + this.getCode() + ", msg=" + this.getMsg() + ", version=" + this.getVersion() + ", data=" + this.getData() + ")";
    }

    public ApiResult() {
    }
}
