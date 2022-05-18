package com.example.loginservice.servicecode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.loginservice.servicecode.entity.Announcement;
import com.example.loginservice.servicecode.entity.Sysuser;
import com.example.loginservice.servicecode.service.IAnnouncementService;
import com.example.loginservice.servicecode.service.ISysuserService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.RedisUtil;
import com.example.loginservice.utils.Result;
import io.jsonwebtoken.Claims;
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
 * @data 2022/4/23 0:42
 **/

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Resource
    private IAnnouncementService iAnnouncementService;

    @Resource
    private ISysuserService iSysuserService;

    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtil redisUtil;


    // 删除信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/deleteAnnouncementInfo")
    public Result deleteAnnouncementInfo(@RequestBody Integer aId) {
        //根据id删除
        boolean b = iAnnouncementService.removeById(aId);
        Result result = null;
        if (b) {
            result = Result.succ(200, "删除成功", "");
        } else
            result = Result.succ(200, "删除失败", "");

        return result;
    }

    //查询满足id
    // 可以扩展为查询满足条件的宿舍楼号
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/selectAnnouncementInfo/{announcementId}")
    public Result selectAnnouncementInfo(@PathVariable Integer announcementId) {
        Result result = null;
        List<Announcement> data = null;


        Announcement announcementInfo = iAnnouncementService.getById(announcementId);
        if (announcementInfo != null) {
            data = new ArrayList<>();
            data.add(announcementInfo);
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "没有满足条件的数据", null);
        }

        return result;
    }

    // 添加
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/addAnnouncementInfo")
    public Result addAnnouncementInfo(@RequestBody Announcement data, HttpServletRequest request) {
        Result result = null;
        //  获取到创建公告的管理员是谁，并且和宿舍楼进行绑定
        String token = request.getHeader(jwtUtils.getHeader());
        Claims claimByToken = jwtUtils.getClaimByToken(token);
        String username = claimByToken.getSubject();
        Sysuser sysUser = iSysuserService.getByUsername(username);

        //组装数据，将添加需要的信息柱状到实体类中
        data.setBuildingId(sysUser.getBuildingId());
        data.setSysId(sysUser.getId());
        try {
            iAnnouncementService.save(data);
            result = Result.succ(200, "发布成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "发布失败", null);
        } finally {
            return result;
        }
    }

    //  懒加载，每次请求，返回四条数据
    // 管理员需要的数据有: 公告标题，内容，发布人，宿舍楼，发布时间
    //@PreAuthorize("hasRole('admin')")
    @GetMapping("/pageAnnouncementInfo/{lens}")
    public Result pageAnnouncementInfo(@PathVariable("lens") Integer lens) {
        Result result = null;
        List<Map<String, Object>> list = null;
        try {
            list = iAnnouncementService.pageAnnouncementInfo(lens);
            result = Result.succ(200, "", list);
        } catch (Exception e) {
            result = Result.succ(404, "加载失败", null);
        } finally {
            return result;
        }
    }

    // 获取一条最新的数据
    @GetMapping("/getAnnonOne")
    public Result getAnnonOne() {
        List<Map<String, Object>> list = iAnnouncementService.pageAnnouncementInfo(0);
        return Result.succ(list.get(0));
    }

}
