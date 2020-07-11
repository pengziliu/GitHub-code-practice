package com.lzp.auth.exception;

import com.lzp.auth.utils.ResultCodeEnum;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;


    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
