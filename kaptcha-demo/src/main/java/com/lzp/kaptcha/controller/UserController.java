package com.lzp.auth.controller;

import com.lzp.auth.dto.LoginDTO;
import com.lzp.auth.utils.RedisUtils;
import com.lzp.auth.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/login")
    public UserVO login(@RequestBody LoginDTO loginDTO)  {
        Object captch = redisUtils.get(loginDTO.getCaptchaKey());
        if(captch == null){
            // throw 验证码已过期
        }
        if(!loginDTO.getCaptcha().equals(captch)){
            // throw 验证码错误
        }
        // 查询用户信息

        //判断用户是否存在 不存在抛出用户名密码错误

        //判断密码是否正确，不正确抛出用户名密码错误

        //构造返回到前端的用户对象并封装信息和生成token

        return new UserVO();
    }



}
