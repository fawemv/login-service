package com.example.loginservice.servicecode.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value = "Repairs对象", description = "")
public class Repairs implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer repairsId;

    @ApiModelProperty("报修内容")
    private String repairsContent;

    @ApiModelProperty("图片描述")
    private String repairsImage;

    private Long sId;

    private String state;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("申请时间")
    private LocalDateTime creatTime;


    @ApiModelProperty("修改时间，处理时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private String studentName;

    @TableField(exist = false)
    private String buildingName;

    @TableField(exist = false)
    private String roomId;

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public String getState() {
        return state;
    }


}
