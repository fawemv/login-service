package com.example.loginservice.servicecode.controller;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/20 21:18
 **/


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.servicecode.entity.Exchange;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.service.IExchangeService;
import com.example.loginservice.servicecode.service.IStudentService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/19 21:23
 **/
@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private IStudentService iStudentService;

    @Resource
    private IExchangeService iExchangeService;

    @Resource
    private JwtUtils jwtUtils;


    //  获取信息分页 附带模糊查询
    //  在房间页面数据量较大，请求数据时，分页 默认 /1/20/0 ,   这个0考虑到可能是通过房间id前缀模糊查询， 0则代表没有查询条件
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getStudent/{page}/{pageSize}")
    public Result getStudentInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize) {
        Result result = null;

        Page<Student> pageInfo = new Page<>(page, pageSize);

        iStudentService.page(pageInfo);

        // 查询有多少条记录,  上面的pageInfo中的total就是呀
        // QueryWrapper<Student> queryWrapper=new QueryWrapper();
        //queryWrapper.gt("salary",3500).like("name","小");
        //Integer count = iStudentService.count(queryWrapper);

        //3 返回查询的结果
        if (pageInfo.getRecords().size() != 0)
            result = Result.succ(200, "查询成功", pageInfo.getRecords(), pageInfo.getTotal());
        else
            result = Result.succ(200, "暂无数据", null);


        return result;

    }


    // 修改信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updateStudentInfo")
    public Result updateStudentInfo(@RequestBody Student data) {
        Result result = null;
        //根据id更新
        try {
            iStudentService.updateById(data);
            result = Result.succ(200, "修改成功", "");
        } catch (Exception e) {
            result = Result.succ(404, "修改失败", "");
        } finally {
            return result;
        }
    }

    // 删除信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/deleteStudentInfo")
    public Result deleteStudentInfo(@RequestBody String studentId) {


        //根据id删除
        boolean b = iStudentService.removeById(Long.parseLong(studentId));
        Result result = null;
        if (b) {
            result = Result.succ(200, "删除成功", "");
        } else
            result = Result.succ(200, "删除失败", "");

        return result;
    }

    //  模糊查询与房间id前缀相同的号
    //  获取房间信息分页 附带模糊查询
    //  在房间页面数据量较大，请求数据时，分页 默认,
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/selectStudentInfo/{page}/{pageSize}/{likeData}")
    public Result selectStudentInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @PathVariable("likeData") Integer likeData) {
        Result result = null;
        List<Student> data = null;
        //LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
//  wrapper.like(StringUtils.isNotBlank(likeData+""), Student::getStudentId, likeData);

//        2.2 指定排序规则，按照更新时间倒序
//        wrapper.orderByAsc(Employee::getUpdateTime);
//        2.3 调用查询方法
//         iStudentService.page(pageInfo, wrapper);

/*        if (data != null)
            result = Result.succ(200, "查询成功", data,aLong);
        else
            result = Result.succ(200, "没有匹配的数据", null);*/

        return result;
    }

    // 添加信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/addStudentInfo")
    public Result addStudentInfo(@RequestBody Student data) {
        Result result = null;
        try {
            iStudentService.save(data);
            result = Result.succ(200, "添加成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "添加失败", null);
        } finally {
            return result;
        }
    }


    //   已入住管理的页面数据，
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/selectYesRegisterInfo/{page}/{pageSize}/{likeData}")
    public Result selectYesRegisterInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @PathVariable("likeData") Long likeData) {
        Result result = null;
        try {
            IPage<Student> studentIPage = iStudentService.selectYesRegisterInfo(page, pageSize, likeData);
            result = Result.succ(200, "查询成功", studentIPage);
        } catch (Exception e) {
            result = Result.succ(404, "查询失败", null);
        } finally {
            return result;
        }
    }


    //   未入住管理的页面数据，
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/selectNoRegisterInfo/{page}/{pageSize}/{likeData}")
    public Result selectNoRegisterInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @PathVariable("likeData") Long likeData) {
        Result result = null;
        try {
            IPage<Student> studentIPage = iStudentService.selectNoRegisterInfo(page, pageSize, likeData);
            result = Result.succ(200, "查询成功", studentIPage);
        } catch (Exception e) {
            result = Result.succ(404, "查询失败", null);
        } finally {
            return result;
        }
    }

    // 根据学生id查询学生的信息
    @GetMapping("/selectById")
    public Result selectById(HttpServletRequest request) {
        Result result = null;
        Student data = null;

        String studentIdStr = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        data = iStudentService.getById(studentIdStr);

        if (data != null) {
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "没有满足条件的数据", null);
        }

        return result;
    }

    // 修改学生换寝室的申请，
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/updateExchangeInfo/{sId}/{exchangeId}/{newRoomId}")
    public Result updateExchangeInfo(@PathVariable("sId") Long sId, @PathVariable("exchangeId") Integer exchangeId, @PathVariable("newRoomId") Integer newRoomId) {
        Result result = null;
        // student表的修改
        Student student = new Student();
        student.setsId(sId);
        student.setRoomId(newRoomId);

        // 申请表的状态修改
        Exchange exchange = new Exchange();
        exchange.setExchangeId(exchangeId);
        exchange.setState("已处理");
        try {
            iStudentService.updateById(student);
            iExchangeService.updateById(exchange);
            result = Result.succ(200, "处理成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "处理失败", null);
        } finally {
            return result;
        }
    }

}


/**
 * 模糊查询代码
 **/
//LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
//  wrapper.like(StringUtils.isNotBlank(likeData+""), Student::getStudentId, likeData);

//        2.2 指定排序规则，按照更新时间倒序
//        wrapper.orderByAsc(Employee::getUpdateTime);
//        2.3 调用查询方法
//         iStudentService.page(pageInfo, wrapper);

