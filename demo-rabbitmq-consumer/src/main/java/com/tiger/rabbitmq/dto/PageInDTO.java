package com.tiger.rabbitmq.dto;

/**
 * 分页接收参数
 * Create by tiger on 2019/3/6
 */
public class PageInDTO {
    private Integer page;
    private Integer count;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
