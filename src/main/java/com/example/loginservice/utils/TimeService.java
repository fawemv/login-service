package com.example.loginservice.utils;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.loginservice.servicecode.entity.Repairs;
import com.example.loginservice.servicecode.entity.Room;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.mapper.StudentMapper;
import com.example.loginservice.servicecode.service.IRepairsService;
import com.example.loginservice.servicecode.service.IRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

/**
 * @author Lang
 * @Desc
 * @data 2022/5/2 13:00
 **/
@Component
@Slf4j
public class TimeService {

    @Resource
    private IRepairsService iRepairsService;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private IRoomService iRoomService;


    // 定时执行，参数：cron表达式
    //@Scheduled(cron = "0/2 * * * * ?")    //每过两秒执行一次
    @Scheduled(cron = "0 0 20 * * ?")      // 每天晚上20点执行一次
    public void count() {
        // 定时处理学生得报修申请
        // 每天晚上八点，将前一天申请得未处理得报修记录状态设置为已经处理

        // 当前天得时间戳减去一天得时间戳
        LocalDate now = LocalDate.now();
        long l = now.atStartOfDay().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        l -= 86400000;
        LocalDateTime time = LocalDateTime.ofEpochSecond(l / 1000, 0, ZoneOffset.ofHours(8));
        UpdateWrapper<Repairs> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("state", "待处理")
                .le("creat_time", time)
                .set("state", "已处理");
        iRepairsService.update(updateWrapper);
        log.info("学生得报修处理完成");
    }


    // 定时统计房间的入住情况
    @Scheduled(cron = "0 0 */1 * * ?")      // 每1个小时执行一次
    public void occupancy() {
        // 统计学生的的房间使用情况， 更新到room表中
        List<Map<String, Object>> list = studentMapper.countRoomId();

        // 更新room表中的数据

        for (Map<String, Object> map : list) {
            Integer roomId = (Integer) map.get("roomId");
            Integer count = Integer.parseInt("" + map.get("count"));
            Room room = new Room();
            room.setRoomId(roomId);
            room.setOccupancy(count);
            // 更新到表中
            iRoomService.updateById(room);
        }

    }
}
