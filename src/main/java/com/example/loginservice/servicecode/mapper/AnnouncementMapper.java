package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.loginservice.servicecode.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;
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
public interface AnnouncementMapper extends BaseMapper<Announcement> {

    @Select("select announcement.*,`all`.`name`,`all`.buildingName  from announcement left join " +
            "(select sysuser.`name`,building.`name` as buildingName,building.building_id  from building left join sysuser on building.building_id = sysuser.building_id) as `all` " +
            "on " +
            "announcement.building_id = `all`.building_id  order by announcement.creat_time desc limit #{lens},8")
    List<Map<String, Object>> pageAnnouncementInfo(@Param("lens") Integer lens);
}
