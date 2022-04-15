package com.example.loginservice.servicecode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.servicecode.entity.Sysuser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface ISysuserService extends IService<Sysuser> {
    Sysuser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);
}
