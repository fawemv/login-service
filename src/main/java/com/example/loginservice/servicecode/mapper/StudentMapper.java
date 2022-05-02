package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.servicecode.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface StudentMapper extends BaseMapper<Student> {


    IPage<Student> selectYesRegisterInfo(Page page, @Param("likeData") String likeData);


    IPage<Student> selectNoRegisterInfo(Page page, @Param("likeData") String likeData);
}
