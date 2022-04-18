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

    public Integer getRepairsId() {
        return repairsId;
    }

    public void setRepairsId(Integer repairsId) {
        this.repairsId = repairsId;
    }
    public String getRepairsContent() {
        return repairsContent;
    }

    public void setRepairsContent(String repairsContent) {
        this.repairsContent = repairsContent;
    }
    public String getRepairsImage() {
        return repairsImage;
    }

    public void setRepairsImage(String repairsImage) {
        this.repairsImage = repairsImage;
    }
    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Repairs{" +
                "repairsId=" + repairsId +
                ", repairsContent=" + repairsContent +
                ", repairsImage=" + repairsImage +
                ", sId=" + sId +
                ", state=" + state +
                "}";
    }
}
