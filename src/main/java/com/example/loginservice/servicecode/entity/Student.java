package com.example.loginservice.servicecode.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@ApiModel(value = "Student对象", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long sId;

    private String password;

    @ApiModelProperty("学生名字")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("学院id号")
    private Integer instituteId;

    @ApiModelProperty("宿舍号")
    private Integer roomId;

    @ApiModelProperty("年级")
    private Integer grade;

    @ApiModelProperty("床位")
    private Integer bed;

    @ApiModelProperty("逻辑删除字段")
    @TableLogic
    private Integer isDelete;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Integer instituteId) {
        this.instituteId = instituteId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getBed() {
        return bed;
    }

    public void setBed(Integer bed) {
        this.bed = bed;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sId=" + sId +
                ", password=" + password +
                ", name=" + name +
                ", sex=" + sex +
                ", instituteId=" + instituteId +
                ", roomId=" + roomId +
                ", grade=" + grade +
                ", bed=" + bed +
                ", isDelete=" + isDelete +
                "}";
    }
}
