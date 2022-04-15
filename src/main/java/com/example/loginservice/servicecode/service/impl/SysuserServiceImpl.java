package com.example.loginservice.servicecode.service.impl;

import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.mapper.SysuserMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.ISysuserService;
import com.example.loginservice.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Service
public class SysuserServiceImpl extends ServiceImpl<SysuserMapper, Sysuser> implements ISysuserService {
    @Resource
    private SysuserMapper sysUserMapper;
    @Resource
    private RedisUtil redisUtil;

    // 获取用户的信息
    @Override
    public Sysuser getByUsername(String username) {
        Sysuser sysUser = sysUserMapper.getByUsername(username);
        return sysUser;
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        // 接口分为三种权限：无需权限  admin权限  student权限
        // 获取用户角色
        // 先从redis中获取

        // 数据库获取
        Sysuser sysUser = sysUserMapper.getById(userId.intValue());
        String authority = sysUser.getRole();

        // 这里的角色分为两种:最后返回的结果为；1.  ROLE_admin  2.  ROLE_student

        return "ROLE_" + authority;
    }

}
