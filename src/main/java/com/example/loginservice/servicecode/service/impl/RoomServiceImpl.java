package com.example.loginservice.servicecode.service.impl;


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
    public List<Room> selectRoomInfo(Integer page, Integer pageSize, Integer likeData) {
        String str = likeData + "%";
        List<Room> rooms = roomMapper.selectRoomInfo((page - 1) * pageSize, pageSize, str);
        return rooms;
    }

    @Override
    public Long selectRoomInfoSize(Integer likeData) {
        Long aLong = roomMapper.selectRoomInfoSize(likeData + "%");
        return aLong;
    }
}
