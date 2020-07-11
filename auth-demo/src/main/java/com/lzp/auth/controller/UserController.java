package com.lzp.auth.controller;

import com.lzp.auth.dto.LoginDTO;
import com.lzp.auth.service.UserService;
import com.lzp.auth.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 登陆
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public UserVO login(@RequestBody LoginDTO loginDTO)  {
        return userService.login(loginDTO);
    }

    /**
     * 接口测试
     * @return
     */
    @GetMapping("/test")
    public String test()  {
        log.info("测试当前访问用户为:{}",getUser());
        return "success";
    }


}
