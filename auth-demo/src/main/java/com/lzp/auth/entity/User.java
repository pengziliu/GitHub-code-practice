package com.lzp.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    /**
     * 用户id
     */
    private String uid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;


}
