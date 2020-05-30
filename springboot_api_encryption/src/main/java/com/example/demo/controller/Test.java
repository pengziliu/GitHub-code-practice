package com.example.demo.controller;

import cn.shuibo.annotation.Decrypt;
import cn.shuibo.annotation.Encrypt;
import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Test {

    /**
     * 加密
     * @return
     */
    @RequestMapping("/encryption")
    @ResponseBody
    @Encrypt
    public User encryption(){
        User user=new User("试试", "12345",1);
        return user;
    }


    /**
     * 解密
     * @param user
     * @return
     */
    @PostMapping("/decryption")
    @Decrypt
    @ResponseBody
    public String Decryption(@RequestBody User user){
        System.out.println(user.toString());
        return user.toString();
    }


}
