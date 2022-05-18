package com.example.loginservice.servicecode.mapper;


import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.loginservice.servicecode.entity.Student;
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
public interface StudentMapper extends BaseMapper<Student> {


    IPage<Student> selectYesRegisterInfo(Page page, @Param("likeData") String likeData);


    IPage<Student> selectNoRegisterInfo(Page page, @Param("likeData") String likeData);

    // 返回学生的房间使用情况

    @Select("SELECT room_id as roomId,count(1) as count\n" +
            "FROM `student` \n" +
            "WHERE is_delete = 0 and room_id is not null GROUP BY room_id")
    List<Map<String, Object>> countRoomId();


    @Select("select stu.s_id,stu.sex,stu.room_id,stu.`name`,stu.institute_id,stu.grade,stu.bed,ins.institute_name from student as stu left join institute as ins on\n" +
            "stu.institute_id = ins.institute_id where stu.is_delete = 0  ")
    IPage<Student> pageInfo(Page page);


    @Select("select stu.s_id,stu.sex,stu.room_id,stu.`name`,stu.institute_id,stu.grade,stu.bed,ins.institute_name from student as stu left join institute as ins on\n" +
            "stu.institute_id = ins.institute_id where stu.is_delete = 0  and  stu.s_id like #{likeData}")
    IPage<Student> selectStudentInfo(Page page, @Param("likeData") String likeData);

    // 获取学生信息

    @Select("select stu.s_id,stu.`name`,stu.sex,stu.grade,stu.bed,stu.room_id,ins.institute_name,build.`name`as buildingName from student as stu LEFT JOIN institute as ins \n" +
            " on stu.institute_id = ins.institute_id \n" +
            " left join \n" +
            "(select building.`name`,room.room_id from room left join building on room.building_id = building.building_id ) as build on \n" +
            " stu.room_id = build.room_id where stu.s_id = #{stuId}")
    Student getNowStudentInfo(Long stuId);

    @Select("select sex,count(*) as num from student WHERE is_delete = 0 GROUP BY sex")
    List<Map<String, Object>> studentSexCount();

    @Select("select grade,count(*) as num from student WHERE is_delete = 0 GROUP BY grade ORDER BY grade")
    List<Map<String, Object>> studentGradeCount();


}
