package com.tiger.rabbitmq.mybatis.base;

/**
 * 分页器
 * Create by ljt on 20190306
 */
public class PageParameter {

    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 每页显示记录数
     */
    private int pageSize;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 总记录数
     */
    private int totalCount;

    public PageParameter() {
        this.currentPage = 1;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    /**
     * @param currentPage
     * @param pageSize
     */
    public PageParameter(int currentPage, int pageSize) {
        if (currentPage < 0) {
            currentPage = 0;
        }
        this.currentPage = currentPage;
        if (0 == pageSize) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
