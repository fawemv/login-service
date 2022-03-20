package com.example.loginservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.pojo.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);


}
