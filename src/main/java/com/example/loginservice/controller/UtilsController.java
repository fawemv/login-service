package com.example.loginservice.controller;

import cn.hutool.core.lang.hash.Hash;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UtilsController {

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/sumRequest")
    public Result sumRequest(){
        System.out.println("zhixing");
        Result result = new Result();
        //  请求添加到redis sumRequest
        redisUtil.incr("sumRequest",1);
        Object sumRequest = redisUtil.get("sumRequest");
        Result succ = result.succ(sumRequest);

        return succ;
    }
}
