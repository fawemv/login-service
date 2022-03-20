package com.example.loginservice.controller;


import com.example.loginservice.service.KaptchaService;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class LoginController {
    @Resource
    private KaptchaService kaptchaService;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/goLogin")
    public Result goLogin() {

        redisUtil.incr("sumRequest",1);
        Map<String, Object> kaptcha = kaptchaService.getKaptcha();
        Result result = new Result();
        Result succ = result.succ(kaptcha);
        return succ;
    }
}
