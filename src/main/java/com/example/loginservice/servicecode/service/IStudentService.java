
package com.example.loginservice.servicecode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface IStudentService extends IService<Student> {
    IPage<Student> selectYesRegisterInfo(Integer page, Integer pageSize, Long likeData);

    IPage<Student> selectNoRegisterInfo(Integer page, Integer pageSize, Long likeData);


}
