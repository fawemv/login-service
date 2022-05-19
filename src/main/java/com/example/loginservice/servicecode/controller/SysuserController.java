package com.example.loginservice.servicecode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.mapper.SysuserMapper;
import com.example.loginservice.servicecode.service.ISysuserService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Lang
 * @Desc
 * @data 2022/5/6 21:26
 **/
@RestController
@RequestMapping("/sysuser")
public class SysuserController {
    @Resource
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private ISysuserService iSysuserService;

    @Resource
    private SysuserMapper sysuserMapper;


    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updatePass")
    public Result updatePass(@RequestBody Map<String, String> map, HttpServletRequest request) {
        Result result = null;
        String sysId = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        Sysuser sys = iSysuserService.getByUsername(sysId);
        // 验证密码
        boolean oldpass = bCryptPasswordEncoder.matches(map.get("oldpass"), sys.getPassword());

        if (!oldpass)
            result = Result.fail("旧密码不正确");
        else {
            Sysuser sysuser = new Sysuser();
            sysuser.setId(sys.getId());
            sysuser.setPassword(bCryptPasswordEncoder.encode(map.get("newpass1")));
            iSysuserService.updateById(sysuser);
            result = Result.succ(200, "修改成功", null);
        }

        return result;
    }

    // 获取管理员的信息
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getInitData")
    public Result getInitData() {
        QueryWrapper<Sysuser> wrapper = Wrappers.query();
        wrapper.ne("username", "root");
        List<Sysuser> sysusers = sysuserMapper.selectList(wrapper);
        for (int i = 0; i < sysusers.size(); i++) {
            sysusers.get(i).setPassword(null);
        }

        return Result.succ(200, "查询成功", sysusers);
    }

    //
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/addAdmin")
    public Result addAdmin(@RequestBody Sysuser sysuser) {

        boolean save = false;
        try {
            save = iSysuserService.save(sysuser);
        } catch (Exception e) {
            return Result.succ(400,"添加失败,宿舍楼号错误",null);
        }
        if (save == true){
            return Result.succ(200,"添加成功",null);
        }else {
            return Result.succ(400,"添加失败",null);
        }

    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updateAdmin")
    public Result updateAdmin(@RequestBody Sysuser sysuser) {
        boolean save = false;
        try {
            save = iSysuserService.updateById(sysuser);
        } catch (Exception e) {
            return Result.succ(400,"修改失败,宿舍楼号错误",null);
        }
        if (save == true){
            return Result.succ(200,"修改成功",null);
        }else {
            return Result.succ(400,"修改失败",null);
        }

    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/deleteSysuser/{id}")
    public Result deleteSysuser(@PathVariable("id") Long id){
        iSysuserService.removeById(id);
        return Result.succ(200,"删除成功",null);
    }



}
