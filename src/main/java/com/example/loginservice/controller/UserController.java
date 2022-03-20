package com.example.loginservice.controller;

import cn.hutool.core.map.MapUtil;
import com.example.loginservice.pojo.User;
import com.example.loginservice.service.IUserService;
import com.example.loginservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈浪
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/pass")
    public Result passEncode() {
        // 密码加密
        String pass = bCryptPasswordEncoder.encode("111111");

        // 密码验证
        boolean matches = bCryptPasswordEncoder.matches("111111", pass);

        return Result.succ(MapUtil.builder()
                .put("pass", pass)
                .put("marches", matches)
                .build()
        );
    }

}
