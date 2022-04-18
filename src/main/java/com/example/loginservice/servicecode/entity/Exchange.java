package com.example.loginservice.servicecode.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
@ApiModel(value = "Exchange对象", description = "")
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请表id")
    private Integer exchangeId;

    @ApiModelProperty("学生id")
    private Long sId;

    @ApiModelProperty("申请时间")
    private LocalDate creatTime;

    @ApiModelProperty("状态")
    private String state;

    public Integer getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Integer exchangeId) {
        this.exchangeId = exchangeId;
    }
    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }
    public LocalDate getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(LocalDate creatTime) {
        this.creatTime = creatTime;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "exchangeId=" + exchangeId +
                ", sId=" + sId +
                ", creatTime=" + creatTime +
                ", state=" + state +
                "}";
    }
}
