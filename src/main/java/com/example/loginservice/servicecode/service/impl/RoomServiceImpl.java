package com.example.loginservice.servicecode.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.exception.Const;
import com.example.loginservice.servicecode.entity.Room;
import com.example.loginservice.servicecode.mapper.RoomMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.IRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {


    @Resource
    private RoomMapper roomMapper;

    @Override
    public IPage<Room> selectRoomInfo(Integer page, Integer pageSize, Integer likeData) {
        String str = likeData + "%";
        Page<Room> page1 = new Page(page, pageSize);

        return roomMapper.selectRoomInfo(page1, str);
    }

    @Override
    public Long selectRoomInfoSize(Integer likeData) {
        Long aLong = roomMapper.selectRoomInfoSize(likeData + "%");
        return aLong;
    }

    @Override
    public IPage<Room> getRoomInfo(Page page) {

        return roomMapper.getRoomInfo(page);
    }

    @Override
    public Integer getYesRoomCount(Integer buildingId) {

        return roomMapper.getYesRoomCount(buildingId);
    }

    @Override
    public Integer getNoRoomCount(Integer buildingId) {
        return roomMapper.getNoRoomCount(buildingId);
    }
}
