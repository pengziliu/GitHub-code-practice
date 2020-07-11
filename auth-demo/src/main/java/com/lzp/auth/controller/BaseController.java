package com.lzp.auth.controller;

import com.lzp.auth.utils.SessionContext;
import com.lzp.auth.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private SessionContext sessionContext;

    public UserVO getUser() {
        return sessionContext.getUser();
    }

}
