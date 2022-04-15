package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.loginservice.servicecode.entity.Sysuser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface SysuserMapper extends BaseMapper<Sysuser> {

    Sysuser getByUsername(@Param("username") String username);

    Sysuser getById(@Param("userId") int userId);
}
