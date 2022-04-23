package com.example.loginservice.servicecode.service;


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
    List<Room> selectRoomInfo(Integer page, Integer pageSize, Integer likeData);

    Long selectRoomInfoSize(Integer likeData);

}
