package com.lzn.mybatisplus.codegenerator.utils.entity;

import java.io.Serializable;

public class Resp implements Serializable {
    private static final long serialVersionUID = -2057059851081120139L;
    private String status;
    private String message;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Resp(String status, String msg) {
        this.status = status;
        this.message = msg;
    }

    public Resp(String status, String msg, Object data) {
        this.status = status;
        this.message = msg;
        this.data = data;
    }

    public Resp() {

    }
}
