package com.example.loginservice.servicecode.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Data
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


    // 学院的名字
    @TableField(exist = false)
    private String instituteName;
    // 宿舍楼的名字
    @TableField(exist = false)
    private String buildingName;


    public Long getsId() {
        return this.sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

}
