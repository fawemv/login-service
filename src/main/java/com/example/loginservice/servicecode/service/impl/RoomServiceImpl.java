package com.example.loginservice.servicecode.service.impl;


import com.example.loginservice.servicecode.entity.Room;
import com.example.loginservice.servicecode.mapper.RoomMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.service.IRoomService;
import org.springframework.stereotype.Service;

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

}
