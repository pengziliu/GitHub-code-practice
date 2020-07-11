package com.lzp.auth.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uid;

    private String name;

    private String token;
}
