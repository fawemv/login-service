package com.example.loginservice.servicecode.controller;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/20 21:18
 **/


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.servicecode.entity.Exchange;
import com.example.loginservice.servicecode.entity.Repairs;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.mapper.StudentMapper;
import com.example.loginservice.servicecode.service.IExchangeService;
import com.example.loginservice.servicecode.service.IStudentService;
import com.example.loginservice.servicecode.service.ISysuserService;
import com.example.loginservice.utils.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.EasyExcelFactory;


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

    @Resource
    private PassWordUtil passWordUtil;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private ISysuserService iSysuserService;

    //存储需要下载的学生信息数据

    private Map<String, List<Map<String, String>>> map = new HashMap<>();


    //  获取信息分页 附带模糊查询
    //  在房间页面数据量较大，请求数据时，分页 默认 /1/20/0 ,   这个0考虑到可能是通过房间id前缀模糊查询， 0则代表没有查询条件
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getStudent/{page}/{pageSize}")
    public Result getStudentInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize) {
        Result result = null;

       /* Page<Student> pageInfo = new Page<>();
        iStudentService.page(pageInfo);*/
        // 分页查询需要宿舍楼的名字和学院的名字
        //total   records
        IPage<Student> studentIPage = iStudentService.pageInfo(page, pageSize);


        // 查询有多少条记录,  上面的pageInfo中的total就是呀
        // QueryWrapper<Student> queryWrapper=new QueryWrapper();
        //queryWrapper.gt("salary",3500).like("name","小");
        //Integer count = iStudentService.count(queryWrapper);

        //3 返回查询的结果
        if (studentIPage.getRecords() != null)
            result = Result.succ(200, "查询成功", studentIPage.getRecords(), studentIPage.getTotal());
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
        // 如果变换的宿舍为null， 则调用, 将宿舍号和床位设置为空
        if (data.getRoomId() == null) {
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("s_id", data.getsId())
                    .set("room_id", null)
                    .set("bed", null);
            boolean update = iStudentService.update(updateWrapper);

            return Result.succ(200, "修改成功", null);

        }else if (data.getRoomId() != null && data.getBed() == null) {
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper
                    .eq("s_id", data.getsId())
                    .set("bed", null);
            boolean update = iStudentService.update(updateWrapper);

            return Result.succ(200, "修改成功", null);
        }


        // 修改房间号，和床位
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
    public Result selectStudentInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @PathVariable("likeData") String likeData) {
        Result result = null;
        // Page<Student> objectPage = new Page<>(page, pageSize);

        // QueryWrapper<Student> wrapper = Wrappers.query();
        //   wrapper.likeRight("s_id",likeData);
        //IPage<Student> data = iStudentService.page(objectPage, wrapper);

        //修改返回的数据，联表查询

        IPage<Student> studentIPage = iStudentService.selectStudentInfo(page, pageSize, likeData);
        if (studentIPage.getRecords() != null)
            result = Result.succ(200, "查询成功", studentIPage);
        else
            result = Result.succ(404, "没有匹配的数据", null);

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

    // 导入学生数据的请求
    // 处理文件上传
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        Result result = null;
        //检查文件是否为空
        if (file.isEmpty()) {
            result = Result.fail("上传文件不能为空");
            return result;
        }
        //检查文件大小  2097152 =2M
        if (file.getSize() > 2097152 * 50) {
            result = Result.fail("文件大于100M");
            return result;
        }
        // 将文件交给工具类处理
        List<Student> excelInfo = null;

        excelInfo = ReadPatientExcelUtil.getExcelInfo(file);

        if (excelInfo == null) {
            result = Result.fail("文件或文件内容为空！");
            return result;
        }


        // 错误的数据行数
        String error = "";

        // 循环添加学生信息
        for (int i = 0; i < excelInfo.size(); i++) {
            Student student = excelInfo.get(i);
            try {
                iStudentService.save(student);
            } catch (Exception e) {
                error += i + 2 + ",";
            }
        }
        if (error != null) {
            result = Result.fail("部分数据导入失败", error);
        } else
            result = Result.succ(200, "导入成功", null);

        return result;
    }

    // 学生 修改密码
    @PreAuthorize("hasRole('student')")
    @PostMapping("/updatePass")
    public Result updatePass(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Result result = null;
        String stuId = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        Student byId = iStudentService.getById(stuId);
        // 验证密码
        boolean oldpass = bCryptPasswordEncoder.matches(map.get("oldpass"), byId.getPassword());

        if (!oldpass)
            result = Result.fail("旧密码不正确");
        else {
            Student student = new Student();
            student.setsId(byId.getsId());
            student.setPassword(bCryptPasswordEncoder.encode(map.get("newpass1")));
            iStudentService.updateById(student);
            result = Result.succ(200, "修改成功", null);
        }

        return result;
    }

    // getNowStudentInfo
    // 获取当前登录的学生信息
    @PreAuthorize("hasRole('student')")
    @GetMapping("/getNowStudentInfo")
    public Result getNowStudentInfo(HttpServletRequest request) {
        Result result = null;
        String sysId = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        Student stu = iStudentService.getNowStudentInfo(sysId);
        result = Result.succ(200, "查询成功", stu);
        return result;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/studentSexCount")
    public Result studentSexCount() {
        Result result = null;
        List<Map<String, Object>> list = iStudentService.studentSexCount();
        Map<String, Integer> map = new HashMap<>();
        result = Result.succ(200, "查询成功", list);
        return result;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/studentGradeCount")
    public Result studentGradeCount() {
        Result result = null;
        List<Map<String, Object>> list = iStudentService.studentGradeCount();
        Map<String, Integer> map = new HashMap<>();
        result = Result.succ(200, "查询成功", list);
        return result;
    }

    // 下载学生的信息excel
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/studentInfoLoad")
    public Result studentInfoLoad(@RequestBody List<Map<String, String>> data) throws Exception {

        // 文件输出位置
        String fileName = UUID.randomUUID().toString();
        map.put(fileName, data);
        return Result.succ(fileName);
    }


    @GetMapping("/studentInfoLoad2/{fileName}")
    public void studentInfoLoad2(@PathVariable("fileName") String fileName, HttpServletResponse response) throws ServletException, IOException {


        List<Map<String, String>> data = map.remove(fileName);
        // 生成excel文件
        HSSFWorkbook workbook = iStudentService.exportStudent(data);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学生信息表.xls", "UTF-8"));

        workbook.write(response.getOutputStream());
    }


    // 统计图表统计每个学院的学生人数
    //
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/studentInstituteCount")
    public Result studentInstituteCount() {
        List<Map<String, Object>> list = iStudentService.studentInstituteCount();

        return Result.succ(list);
    }


}

