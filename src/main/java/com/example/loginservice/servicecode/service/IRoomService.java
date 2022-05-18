package com.example.loginservice.servicecode.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.loginservice.servicecode.entity.Room;
import com.example.loginservice.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface IRoomService extends IService<Room> {

    //
    IPage<Room> selectRoomInfo(Integer page, Integer pageSize, Integer likeData);

    Long selectRoomInfoSize(Integer likeData);

    IPage<Room> getRoomInfo(Page page);

    // 获取某栋楼已入住的房间数量
    Integer getYesRoomCount(Integer buildingId);

    // 获取某栋楼未入住的房间数量
    Integer getNoRoomCount(Integer buildingId);

}
