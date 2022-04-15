package com.example.loginservice.servicecode.entity;

import java.io.Serializable;

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
@ApiModel(value = "Room对象", description = "")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roomId;

    @ApiModelProperty("宿舍楼号")
    private Integer buildingId;

    @ApiModelProperty("统计入住情况")
    private Integer occupancy;

    @ApiModelProperty("入住几人的")
    private Integer roomType;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", buildingId=" + buildingId +
                ", occupancy=" + occupancy +
                ", roomType=" + roomType +
                "}";
    }
}
