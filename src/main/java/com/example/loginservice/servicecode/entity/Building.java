package com.example.loginservice.servicecode.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Building对象", description = "")
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer buildingId;

    @ApiModelProperty("宿舍名称")
    private String name;

    @ApiModelProperty("楼层")
    private Integer floor;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("收费标准")
    private Float price;

    @ApiModelProperty("男/女")
    private String type;

    @ApiModelProperty("几人间")
    private Integer roomType;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingId=" + buildingId +
                ", name=" + name +
                ", floor=" + floor +
                ", description=" + description +
                ", price=" + price +
                ", type=" + type +
                ", roomType=" + roomType +
                "}";
    }
}
