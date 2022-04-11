package com.example.loginservice.controller;

import cn.hutool.core.lang.hash.Hash;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UtilsController {

    @Resource
    private RedisUtil redisUtil;

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
    @PreAuthorize("hasAuthority('student')")
    public Result testRole() {
        return new Result().succ("请求成功");
    }
}
