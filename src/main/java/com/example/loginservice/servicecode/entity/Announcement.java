package com.example.loginservice.servicecode.entity;

import java.io.Serializable;
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
 * @since 2022-04-14
 */
@Data
@ApiModel(value = "Announcement对象", description = "")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    //  主键自增策略， 明明一开始都能用，现在就不能用了，真是奇怪
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("公告编号")
    private Integer aId;

    private String title;

    @ApiModelProperty("内容")
    private String content;

    private Long sysId;

    private Integer buildingId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime creatTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    Integer getaId() {
        return aId;
    }

    void setaId(Integer aId) {
        this.aId = aId;
    }
}
