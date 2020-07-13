package com.lzp.auth.service;


import com.alibaba.fastjson.JSONObject;
import com.lzp.auth.dto.LoginDTO;
import com.lzp.auth.entity.User;
import com.lzp.auth.exception.ServiceException;
import com.lzp.auth.utils.JWTUtils;
import com.lzp.auth.utils.RedisKeyEnum;
import com.lzp.auth.utils.RedisUtils;
import com.lzp.auth.utils.ResultCodeEnum;
import com.lzp.auth.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Value("${server.session.timeout:3000}")
    private Long timeout;

    @Autowired
    private RedisUtils redisUtils;

    final static String USER_NAME = "admin";

    //密码 演示用就不做加密处理了
    final static String PWD = "admin";


    public UserVO login(LoginDTO loginDTO) {

        User user = getByName(loginDTO.getUserName());

        //用户信息校验和查询
        if (user == null) {
            throw new ServiceException(ResultCodeEnum.LOGIN_FAIL);
        }
        //密码校验
        if (!PWD.equals(loginDTO.getPwd())) {
            throw new ServiceException(ResultCodeEnum.LOGIN_FAIL);
        }

        //缓存用户信息并设置过期时间
        UserVO userVO = new UserVO();
        userVO.setName(user.getName());
        userVO.setUid(user.getUid());
        userVO.setToken(JWTUtils.generate(user.getUid()));

        //信息入库redis
        redisUtils.set(RedisKeyEnum.OAUTH_APP_TOKEN.keyBuilder(userVO.getUid()), JSONObject.toJSONString(userVO), timeout);

        return userVO;
    }


    /**
     * 通过用户名获取用户
     *
     * @param name
     * @return
     */
    public User getByName(String name) {
        User user = null;
        if (USER_NAME.equals(name)) {
            user = new User("1", "张三", "Aa123456");
        }
        return user;
    }

}
