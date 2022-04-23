package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.loginservice.servicecode.entity.Room;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface RoomMapper extends BaseMapper<Room> {

    @Select("select * from room where room_id like #{likeData} limit #{page}, #{pageSize}")
    List<Room> selectRoomInfo(@Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("likeData") String likeData);

    @Select("select count(1) from room where room_id like #{likeData}")
    Long selectRoomInfoSize(@Param("likeData") String likeData);

}
