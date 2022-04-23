package com.example.loginservice.servicecode.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.mapper.StudentMapper;
import com.example.loginservice.servicecode.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;


    @Override
    public IPage<Student> selectYesRegisterInfo(Integer page, Integer pageSize, Long likeData) {
        String str = null;
        if (likeData != 0) {
            str = String.valueOf(likeData) + "%";
        }
        return studentMapper.selectYesRegisterInfo(new Page(page, pageSize), str);
    }

    @Override
    public IPage<Student> selectNoRegisterInfo(Integer page, Integer pageSize, Long likeData) {
        String str = null;
        if (likeData != 0) {
            str = String.valueOf(likeData) + "%";
        }
        return studentMapper.selectNoRegisterInfo(new Page(page, pageSize), str);
    }


}
