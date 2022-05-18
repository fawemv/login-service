package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Select("select room.*,building.`name` as buildingName,case when room.occupancy <= room.room_type  then '正常' else '异常' end as roomState from room left join building on room.building_id = building.building_id where room_id like #{likeData}")
    IPage<Room> selectRoomInfo(Page page, @Param("likeData") String likeData);

    @Select("select count(1) from room where room_id like #{likeData}")
    Long selectRoomInfoSize(@Param("likeData") String likeData);

    @Select("select room.*,building.`name` as buildingName,case when room.occupancy <= room.room_type  then '正常' else '异常' end as roomState from room left join building on room.building_id = building.building_id")
    IPage<Room> getRoomInfo(Page page);

    @Select("SELECT count(*) FROM `room` where building_id = #{buildingId} and occupancy != 0")
    Integer getYesRoomCount(@Param("buildingId") Integer buildingId);

    @Select("SELECT count(*) FROM `room` where building_id = #{buildingId} and occupancy = 0")
    Integer getNoRoomCount(@Param("buildingId") Integer buildingId);


}
