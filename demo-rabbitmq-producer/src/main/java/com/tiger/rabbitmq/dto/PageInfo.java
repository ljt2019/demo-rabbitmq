package com.tiger.rabbitmq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tiger.rabbitmq.mybatis.base.PageParameter;

/**
 * Create by tiger on 2019/3/6
 */
public class PageInfo {

    /**
     * 总记录数
     */
    @JsonProperty("total")
    private Integer total;

    /**
     * 是否有下一页
     */
    @JsonProperty("has_next")
    private boolean hasNext;

    public PageInfo() {

    }

    public PageInfo(PageParameter pageParameter) {
        this.setTotal(pageParameter.getTotalCount());
        this.setHasNext(pageParameter.getCurrentPage() < pageParameter.getTotalPage());
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
