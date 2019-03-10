package com.tiger.rabbitmq.mybatis.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public abstract class EntityBase implements Serializable {

    /**
     * 主键id
     */
    private String id;

    /**
     * 状态（0:不显示，1:显示）
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改编辑更新时间
     */
    private Date updateDate;

    /**
     * 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 状态（0:不显示，1:显示）
     */
    public Integer getState() {
        return state;
    }

    /**
     * 状态（0:不显示，1:显示）
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 修改编辑更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改编辑更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
