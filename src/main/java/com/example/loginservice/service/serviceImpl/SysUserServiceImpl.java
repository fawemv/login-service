package com.example.loginservice.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.mapper.SysUserMapper;

import com.example.loginservice.pojo.SysUser;
import com.example.loginservice.service.SysUserService;
import com.example.loginservice.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisUtil redisUtil;

    // 获取用户的信息
    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = sysUserMapper.getByUsername(username);
        return sysUser;
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        // 获取用户角色
        // 先从redis中获取

        // 数据库获取
        SysUser sysUser = sysUserMapper.getById(userId.intValue());
        String authority = sysUser.getRole();

        //
        return "ROLE_" + authority;
    }

}
