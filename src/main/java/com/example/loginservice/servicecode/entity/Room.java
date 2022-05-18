package com.example.loginservice.servicecode.entity;

import java.io.Serializable;

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
@ApiModel(value = "Room对象", description = "")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer roomId;

    @ApiModelProperty("宿舍楼号")
    private Integer buildingId;

    @ApiModelProperty("统计入住情况")
    private Integer occupancy;

    @ApiModelProperty("入住几人的")
    private Integer roomType;

    // 宿舍楼的名字
    @TableField(exist = false)
    private String buildingName;
    // 异常情况： 已入住人数大于可入住人数
    @TableField(exist = false)
    private String roomState;


}
