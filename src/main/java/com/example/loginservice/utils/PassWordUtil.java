package com.example.loginservice.utils;

import cn.hutool.core.map.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Lang
 * @Desc
 * @data 2022/5/2 15:50
 **/

@Component
public class PassWordUtil {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //加密密码

    public String encrypt(String password) {

        // 密码加密
        String pass = bCryptPasswordEncoder.encode(password);

        // 密码验证
        //boolean matches = bCryptPasswordEncoder.matches("111111", pass);

        return pass;
    }

    // 验证
    public boolean encode(String password) {

        // 密码加密
        String pass = bCryptPasswordEncoder.encode(password);

        // 密码验证
        boolean matches = bCryptPasswordEncoder.matches("111111", pass);

        return matches;
    }


}
