package com.example.loginservice.servicecode.controller;

import com.example.loginservice.servicecode.entity.Repairs;
import com.example.loginservice.servicecode.service.IRepairsService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/28 21:42
 **/
@RestController
@RequestMapping("/repairs")
public class RepairsController {
    @Resource
    private IRepairsService iRepairsService;

    @Resource
    private JwtUtils jwtUtils;


    // 修改
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updateRepairsInfo")
    public Result updateRepairsInfo(@RequestBody Repairs data) {
        Result result = null;
        //根据id更新
        try {
            iRepairsService.updateById(data);
            result = Result.succ(200, "修改成功", "");
        } catch (Exception e) {
            result = Result.succ(404, "修改失败", "");
        } finally {
            return result;
        }
    }


    // 添加信息
    @PreAuthorize("hasRole('student')")
    @PostMapping("/addRepairsInfo")
    public Result addRepairsInfo(@RequestBody Repairs data) {

        Result result = null;
        try {
            iRepairsService.save(data);
            result = Result.succ(200, "添加成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "添加失败", null);
        } finally {
            return result;
        }
    }


    //  获取学生的申请记录
    @PreAuthorize("hasRole('student')")
    @GetMapping("/selectRepairsByIdInfo")
    public Result selectRepairsByIdInfo(HttpServletRequest request) {
        Result result = null;
        List<Map<String, Object>> data = null;

        String studentIdStr = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        data = iRepairsService.selectRepairsByIdInfo(studentIdStr);

        if (data != null) {
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "没有满足条件的数据", null);
        }

        return result;
    }


}
