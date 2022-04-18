package com.example.loginservice.security;

import cn.hutool.json.JSONUtil;
import com.example.loginservice.service.serviceImpl.UserDetailsServiceImpl;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.service.IStudentService;
import com.example.loginservice.servicecode.service.ISysuserService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtUtils jwtUtils;

    @Resource
    RedisUtil redisUtil;

    @Resource
    UserDetailsServiceImpl userDetailsService;

    @Resource
    ISysuserService sysUserService;

    @Resource
    IStudentService iStudentService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        // 生成jwt返回
        String jwt = jwtUtils.generateToken(authentication.getName());
        response.setHeader(jwtUtils.getHeader(), jwt);


        // 获取用户的名字返回
        String name = null;
        Sysuser sysUser = sysUserService.getByUsername(authentication.getName());
        if (sysUser == null) {
            Long aLong = null;
            aLong = new Long(authentication.getName());
            Student stu = iStudentService.getById(aLong);
            name = stu.getName();
        } else {
            name = sysUser.getName();
        }

        // 登录成功后，获取用户权限，并存入缓存返回
        String userAuthority = userDetailsService.getUserAuthority(authentication.getName());

        redisUtil.set(jwt, userAuthority, 60 * 30);


        //Result result = Result.succ("{'role':'"+userAuthority+"','username':'"+name+"'}");

        // 只返回了用户的权限
        Result result = Result.succ(userAuthority);

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
