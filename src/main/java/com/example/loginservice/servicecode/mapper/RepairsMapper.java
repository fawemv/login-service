package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.loginservice.servicecode.entity.Repairs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
public interface RepairsMapper extends BaseMapper<Repairs> {


    @Select("select re.*,stu.name,stu.room_id,stu.institute_id FROM repairs as re left join student as stu on re.s_id = stu.s_id " +
            "            where re.s_id = #{studentIdStr} order by  re.state desc,re.repairs_id desc")
    List<Map<String, Object>> selectRepairsByIdInfo(@Param("studentIdStr") String studentIdStr);


    @Select("SELECT repairs.*,student.`name` as studentName,build.buildingName,build.room_id  FROM student right join repairs on student.s_id = repairs.s_id\n" +
            "left join (select building.`name` as buildingName,room.room_id from room left join building on room.building_id = building.building_id ) as build \n" +
            "on student.room_id = build.room_id WHERE repairs.state = '待处理' order by repairs.creat_time desc")
    List<Repairs> getInitData();
}
