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


}
