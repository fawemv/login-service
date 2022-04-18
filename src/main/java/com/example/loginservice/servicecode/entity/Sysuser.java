package com.example.loginservice.servicecode.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-17
 */
@ApiModel(value = "Sysuser对象", description = "")
public class Sysuser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    @ApiModelProperty("逻辑删除字段")
    @TableLogic
    private Integer isDelete;

    private String name;

    private String sex;

    private Integer age;

    @ApiModelProperty("管理的宿舍楼号")
    private Long buildingId;

    @ApiModelProperty("联系电话")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Sysuser{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", isDelete=" + isDelete +
                ", name=" + name +
                ", sex=" + sex +
                ", age=" + age +
                ", buildingId=" + buildingId +
                ", phone=" + phone +
                "}";
    }
}
