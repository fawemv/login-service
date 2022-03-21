package com.example.loginservice.mapper;

import com.example.loginservice.pojo.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈浪
 * @since 2022-03-20
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    //@Select("SELECT * FROM `sysuser` where username = #{username}")
    SysUser getByUsername(String username);

    SysUser getById(@Param("userId") int userId);

}
