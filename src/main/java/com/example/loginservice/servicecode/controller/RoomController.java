package com.example.loginservice.servicecode.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.exception.Const;
import com.example.loginservice.servicecode.entity.Room;
import com.example.loginservice.servicecode.service.IRoomService;
import com.example.loginservice.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/19 21:23
 **/
@RestController
@RequestMapping("/room")
public class RoomController {
    @Resource
    private IRoomService iRoomService;


    //  获取房间信息分页 附带模糊查询
    //  在房间页面数据量较大，请求数据时，分页 默认 /1/20/0 ,   这个0考虑到可能是通过房间id前缀模糊查询， 0则代表没有查询条件
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getRoom/{page}/{pageSize}")
    public Result getRoomInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize) {
        Result result = null;

        Page<Room> pageInfo = new Page<>(page, pageSize);

        iRoomService.page(pageInfo);

        // 查询有多少条记录,  上面的pageInfo中的total就是呀
        // QueryWrapper<Room> queryWrapper=new QueryWrapper();
        //queryWrapper.gt("salary",3500).like("name","小");
        //Integer count = iRoomService.count(queryWrapper);

        //3 返回查询的结果
        if (pageInfo.getRecords().size() != 0)
            result = Result.succ(200, "查询成功", pageInfo.getRecords(), pageInfo.getTotal());
        else
            result = Result.succ(200, "暂无数据", null);


        return result;

    }


    // 修改信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updateRoomInfo")
    public Result updateRoomInfo(@RequestBody Room data) {
        Result result = null;
        //根据id更新
        try {
            iRoomService.updateById(data);
            result = Result.succ(200, "修改成功", "");
        } catch (Exception e) {
            result = Result.succ(404, "修改失败", "");
        } finally {
            return result;
        }
    }

    // 删除宿舍楼信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/deleteRoomInfo")
    public Result deleteRoomInfo(@RequestBody Integer roomId) {


        //根据id删除
        boolean b = iRoomService.removeById(roomId);
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
    @GetMapping("/selectRoomInfo/{page}/{pageSize}/{likeData}")
    public Result selectRoomInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize, @PathVariable("likeData") Integer likeData) {
        Result result = null;
        List<Room> data = null;
        try {
            data = iRoomService.selectRoomInfo(page, pageSize, likeData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 查询一共有多少条数据满足
        Long aLong = iRoomService.selectRoomInfoSize(likeData);

        if (data != null)
            result = Result.succ(200, "查询成功", data, aLong);
        else
            result = Result.succ(200, "没有匹配的数据", null);

        return result;
    }

    // 添加信息
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/addRoomInfo")
    public Result addRoomInfo(@RequestBody Room data) {
        Result result = null;
        try {
            iRoomService.save(data);
            result = Result.succ(200, "添加成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "添加失败", null);
        } finally {
            return result;
        }
    }

    // 根据id查询
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/selectRoomById/{roomId}")
    public Result selectRoomById(@PathVariable("roomId") Integer roomId) {
        Result result = null;

        Room room = iRoomService.getById(roomId);
        if (room != null) {
            result = Result.succ(null);
        } else {
            result = Result.fail("没有这个房间");
        }

        return result;
    }
}


/**
 * 模糊查询代码
 **/
//LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
//  wrapper.like(StringUtils.isNotBlank(likeData+""), Room::getRoomId, likeData);

//        2.2 指定排序规则，按照更新时间倒序
//        wrapper.orderByAsc(Employee::getUpdateTime);
//        2.3 调用查询方法
//         iRoomService.page(pageInfo, wrapper);
