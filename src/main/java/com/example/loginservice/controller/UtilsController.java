package com.example.loginservice.controller;

import cn.hutool.core.lang.hash.Hash;
import cn.hutool.core.map.MapUtil;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UtilsController {

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //@PreAuthorize("hasRole('admin')")
    @GetMapping("/sumRequest")
    public Result sumRequest() {
        System.out.println("zhixing");
        Result result = new Result();
        //  请求添加到redis sumRequest
        redisUtil.incr("sumRequest", 1);
        Object sumRequest = redisUtil.get("sumRequest");
        Result succ = result.succ(sumRequest);

        return succ;
    }

    @GetMapping("/testRole")
    @PreAuthorize("hasRole('admin')")   //  用户需要这样的角色才能访问
    //@PreAuthorize("hasAuthority('student')")
    public Result testRole() {
        return new Result().succ("请求成功");
    }


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
