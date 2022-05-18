package com.example.loginservice.servicecode.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.servicecode.entity.Room;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.mapper.StudentMapper;
import com.example.loginservice.servicecode.service.IRoomService;
import com.example.loginservice.servicecode.service.IStudentService;
import com.example.loginservice.utils.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private IStudentService iStudentService;

    @Resource
    private StudentMapper studentMapper;


    //  获取房间信息分页 附带模糊查询
    //  在房间页面数据量较大，请求数据时，分页 默认 /1/20/0 ,   这个0考虑到可能是通过房间id前缀模糊查询， 0则代表没有查询条件
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getRoom/{page}/{pageSize}")
    public Result getRoomInfo(@PathVariable("page") Integer page, @PathVariable("pageSize") Integer pageSize) {
        Result result = null;

        Page<Room> pageInfo = new Page<>(page, pageSize);

        // iRoomService.page(pageInfo);
        IPage<Room> roomInfo = iRoomService.getRoomInfo(pageInfo);

        //

        //3 返回查询的结果
        if (roomInfo.getRecords().size() != 0)
            result = Result.succ(200, "查询成功", roomInfo.getRecords(), roomInfo.getTotal());
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
        IPage<Room> data = null;
        try {
            data = iRoomService.selectRoomInfo(page, pageSize, likeData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data.getRecords() != null)
            result = Result.succ(200, "查询成功", data, data.getTotal());
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

    // 床位分配
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/allotBed")
    public Result allotBed(@RequestBody List<Room> data) {
        Result result = null;
        List<String> errors = new ArrayList<>();
        data.stream()
                .forEach(room -> {
                            // 查询每个房间的学生的学生，将学生的床位数据初始化
                            QueryWrapper<Student> wrapper = Wrappers.query();

                            wrapper.eq("room_id", room.getRoomId());
                            List<Student> students = studentMapper.selectList(wrapper);

                            if (students.size() != 0 && students.size() <= room.getRoomType()) {
                                for (int i = 0; i < students.size(); i++) {
                                    Student student = students.get(i);
                                    Student temp = new Student();
                                    temp.setsId(student.getsId());
                                    temp.setBed(i + 1);
                                    // 修改床位信息
                                    iStudentService.updateById(temp);
                                }
                            } else if (students.size() > room.getRoomType()) {
                                // 如果是入住人数超过了寝室容量
                                errors.add(room.getRoomId() + "宿舍入住的学生人数超过了房间人数，床位分配失败\n");
                            }

                        }
                );
        if (errors.size() == 0) {
            result = Result.succ(200, "分配成功", null);
        } else {
            // result = Result.fail("部分房间分配失败","cuowu ");
            result = Result.succ(200, "部分房间分配失败", errors);
        }

        return result;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/roomCount/{buildingId}")
    public Result roomCount(@PathVariable("buildingId") Integer buildingId) {
        Result result = null;
        try {
            // 已使用的房间
            Integer yesRoomCount = iRoomService.getYesRoomCount(buildingId);
            // 未使用的房间
            Integer noRoomCount = iRoomService.getNoRoomCount(buildingId);
            HashMap<String, Integer> map = new HashMap<>();
            map.put("yesRoomCount", yesRoomCount);
            map.put("noRoomCount", noRoomCount);
            result = Result.succ(200, "查询成功", map);
        } catch (Exception e) {
            result = Result.succ(404, "查询失败", null);
        } finally {
            return result;
        }
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
