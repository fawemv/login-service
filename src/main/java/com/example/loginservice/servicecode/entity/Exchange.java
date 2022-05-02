package com.example.loginservice.servicecode.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-27
 */
@Data
@ApiModel(value = "Exchange对象", description = "")
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请表id")
    @TableId(value = "exchange_id", type = IdType.AUTO)
    private Integer exchangeId;

    @ApiModelProperty("学生id")
    private Long sId;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("申请时间")
    private LocalDateTime creatTime;

    @ApiModelProperty("状态")
    private String state;


    @ApiModelProperty("修改时间，处理时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("原来的旧寝室")
    private Integer oldRoomId;

    private String description;

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }


}
