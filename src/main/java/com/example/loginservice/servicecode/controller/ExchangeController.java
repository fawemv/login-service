package com.example.loginservice.servicecode.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.servicecode.entity.Building;
import com.example.loginservice.servicecode.entity.Exchange;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.service.IExchangeService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.Result;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/25 23:04
 **/
@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    @Resource
    private IExchangeService iExchangeService;

    @Resource
    private JwtUtils jwtUtils;


    // 修改
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updateExchangeInfo")
    public Result updateExchangeInfo(@RequestBody Exchange data) {
        Result result = null;
        //根据id更新
        try {
            iExchangeService.updateById(data);
            result = Result.succ(200, "修改成功", "");
        } catch (Exception e) {
            result = Result.succ(404, "修改失败", "");
        } finally {
            return result;
        }
    }


    // 添加信息
    @PreAuthorize("hasRole('student')")
    @PostMapping("/addExchangeInfo")
    public Result addExchangeInfo(@RequestBody Exchange data) {
        Result result = null;
        // 查询，如果已经有该学生的申请记录在待处理中，则不让申请了
        QueryWrapper<Exchange> wrapper = Wrappers.query();
        wrapper.eq("s_id", data.getsId())
                .eq("state", "待处理");
        Map<String, Object> map = iExchangeService.getMap(wrapper);
        if (map != null) {
            // 已经存在申请记录了，请耐心等待
            result = Result.succ(200, "已经存在申请记录，请耐心等待", null);
            return result;
        }
        try {
            iExchangeService.save(data);
            result = Result.succ(200, "申请成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "申请失败", null);
        } finally {
            return result;
        }
    }

    //  获取学生的申请记录
    @PreAuthorize("hasRole('student')")
    @GetMapping("/selectExchangeByIdInfo")
    public Result selectExchangeByIdInfo(HttpServletRequest request) {
        Result result = null;
        List<Map<String, Object>> data = null;

        String studentIdStr = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        data = iExchangeService.selectExchangeByIdInfo(studentIdStr);

        if (data != null) {
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "没有满足条件的数据", null);
        }

        return result;
    }

    //  获学生申请记录的处结果
    //  当学生的0ld 房间和现在的房间不一致时，极为已处理，返回时按申请的时间倒序排列,主键进行排序
    @PreAuthorize("hasRole('student')")
    @GetMapping("/selectExchangeByIdResultInfo")
    public Result selectExchangeByIdResultInfo(HttpServletRequest request) {
        Result result = null;
        List<Map<String, Object>> data = null;

        String studentIdStr = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        data = iExchangeService.selectExchangeByIdResultInfo(studentIdStr);

        if (data != null) {
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "没有满足条件的数据", null);
        }

        return result;
    }

    //  管理员获取所有需要处理换寝室申请的学生
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/selectExchangeInfo")
    public Result selectExchangeInfo() {
        Result result = null;
        List<Map<String, Object>> data = null;

        data = iExchangeService.selectExchangeInfo();

        if (data != null) {
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "暂无申请数据", null);
        }

        return result;
    }

    //  驳回学生的调换寝室申请
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/deleteExchangeInfo/{exchangeId}")
    public Result deleteExchangeInfo(@PathVariable("exchangeId") Integer exchangeId) {
        Result result = null;
        Exchange exchange = new Exchange();
        exchange.setExchangeId(exchangeId);
        exchange.setState("驳回");

        try {
            iExchangeService.updateById(exchange);
            result = Result.succ(200, "驳回成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            result = Result.succ(404, "驳回失败", null);
        } finally {
            return result;
        }
    }


}
