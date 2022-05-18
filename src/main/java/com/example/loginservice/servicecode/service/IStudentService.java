
package com.example.loginservice.servicecode.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.utils.Result;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

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

    //  获取学生所以有的相关信息
    IPage<Student> pageInfo(Integer page, Integer pageSize);

    IPage<Student> selectStudentInfo(Integer page, Integer pageSize, String likeData);

    Student getNowStudentInfo(String stuId);

    //
    List<Map<String, Object>> studentSexCount();

    List<Map<String, Object>> studentGradeCount();

    // 生成excel表格
    HSSFWorkbook exportStudent(List<Map<String, String>> data);

}
