package com.example.loginservice.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.mapper.SysUserMapper;

import com.example.loginservice.pojo.SysUser;
import com.example.loginservice.service.SysUserService;
import com.example.loginservice.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

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
        SysUser sysUser = sysUserMapper.getById(userId.intValue());
        String authority = sysUser.getRole();
        // 获取用户权限
        // 先从redis中获取
        return authority;
    }

    //      后面的方法是用于清除用户的权限信息的，确保权限实时更新
    @Override
    public void clearUserAuthorityInfo(String username) {

    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {

    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {

    }

    @Override
    public boolean saveBatch(Collection<SysUser> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<SysUser> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<SysUser> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(SysUser entity) {
        return false;
    }

    @Override
    public SysUser getOne(Wrapper<SysUser> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<SysUser> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<SysUser> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public SysUserMapper getBaseMapper() {
        return null;
    }

    @Override
    public Class<SysUser> getEntityClass() {
        return null;
    }
}
