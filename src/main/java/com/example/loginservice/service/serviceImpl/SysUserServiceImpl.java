package com.example.loginservice.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.loginservice.mapper.SysUserMapper;
import com.example.loginservice.pojo.SysUser;
import com.example.loginservice.service.SysUserService;
import com.example.loginservice.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysMenuService sysMenuService;

    @Resource
    RedisUtil redisUtil;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());

        } else {
            // 获取角色编码
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }

            // 获取菜单操作编码
            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }

            redisUtil.set("GrantedAuthority:" + sysUser.getUsername(), authority, 60 * 60);
        }

        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {

        List<SysUser> sysUsers = this.list(new QueryWrapper<SysUser>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });

    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }
}
*/
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    // 获取用户的信息
    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = sysUserMapper.getByUsername(username);
        return sysUser;
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        return null;
    }

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
    public BaseMapper<SysUser> getBaseMapper() {
        return null;
    }

    @Override
    public Class<SysUser> getEntityClass() {
        return null;
    }
}
