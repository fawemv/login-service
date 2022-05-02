package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.loginservice.servicecode.entity.Exchange;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface ExchangeMapper extends BaseMapper<Exchange> {


    @Select("select ex.*,stu.name,stu.room_id,stu.institute_id FROM exchange as ex left join student as stu on ex.s_id = stu.s_id \n" +
            "where ex.s_id = #{studentId} order by ex.exchange_id desc ")
    List<Map<String, Object>> selectExchangeByIdInfo(@Param("studentId") String studentId);

    @Select("select ex.*,stu.name,stu.room_id,stu.institute_id FROM exchange as ex left join student as stu on ex.s_id = stu.s_id \n" +
            "where ex.s_id = #{studentId} and ex.state = '已处理'\n" +
            "order by ex.exchange_id desc ")
    List<Map<String, Object>> selectExchangeByIdResultInfo(@Param("studentId") String studentId);


    // 管理员查询所有学生的换寝申请信息

    @Select("select ex.*,stu.name,stu.room_id,stu.institute_id,stu.sex,stu.grade FROM exchange as ex left join student as stu on ex.s_id = stu.s_id \n" +
            "where ex.state = '待处理' order by ex.exchange_id desc ")
    List<Map<String, Object>> selectExchangeInfo();


    // 驳回学生的申请， 将state设置为驳回

}
