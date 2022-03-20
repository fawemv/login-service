package com.example.loginservice.controller;

import com.example.loginservice.pojo.User;
import com.example.loginservice.service.IUserService;
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

    @Resource
    private IUserService service;

    @PostMapping("/getUserList")
    public List<User> getUserList() {
        return service.list();
    }

}
