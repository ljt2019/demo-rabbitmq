package com.tiger.rabbitmq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tiger.rabbitmq.mybatis.base.PageParameter;

import java.util.Map;

/**
 * Create by tiger on 2019/3/6
 */
public class AppPageResultObj extends AppResultObj {

    /**
     * 分页信息
     */
    @JsonProperty("page_info")
    private PageInfo pageInfo;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     * 补充信息
     */
    @JsonProperty("data_ext")
    private Map<String, Object> dataExt;

    public Map<String, Object> getDataExt() {
        return dataExt;
    }

    public void setDataExt(Map<String, Object> dataExt) {
        this.dataExt = dataExt;
    }

    public AppPageResultObj() {
    }

    public AppPageResultObj(Object data, PageInfo pageInfo) {
        setData(data);
        setPageInfo(pageInfo);
    }

    public AppPageResultObj(Object data, PageParameter pp) {
        setData(data);
        if (pp != null) {
            PageInfo pi = new PageInfo();
            pi.setTotal(pp.getTotalCount());
            pi.setHasNext((pp.getCurrentPage() + 1) < pp.getTotalPage());
            setPageInfo(pi);
        }
    }

    public AppPageResultObj(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public AppPageResultObj(int code, String msg, Object data, PageInfo pageInfo, boolean succeed) {
        setCode(code);
        setMsg(msg);
        setData(data);
        setPageInfo(pageInfo);
        setSucceed(false);
    }

    /**
     * 接口调用成功，传入需要返回的data数据
     *
     * @param json
     * @return
     */
    public static AppPageResultObj success(Object data, PageInfo pageInfo) {
        return new AppPageResultObj(data, pageInfo);
    }

    /**
     * 接口调用成功，传入需要返回的data数据
     *
     * @param json
     * @return
     */
    public static AppPageResultObj success(Object data, PageParameter pp) {
        return new AppPageResultObj(data, pp);
    }

    /**
     * 接口调用失败：不允许未登录用户调用此接口
     *
     * @return
     */
    public static AppPageResultObj notAllow() {
        return new AppPageResultObj(CODE_NOT_ALLOW, "未经许可的用户", null, null, false);
    }

    /**
     * 接口调用失败：参数错误
     *
     * @return
     */
    public static AppPageResultObj parameterError() {
        return new AppPageResultObj(CODE_PARAM_ERR, "参数解析错误", null, null, false);
    }

    /**
     * 接口调用失败：服务器异常
     *
     * @return
     */
    public static AppPageResultObj serverError() {
        return new AppPageResultObj(CODE_SERVER_ERR, "服务器异常", null, null, false);
    }

    /**
     * 接口调用失败：服务器异常
     *
     * @return
     */
    public static AppPageResultObj serverError(String msg) {
        return new AppPageResultObj(CODE_SERVER_ERR, msg, null, null, false);
    }
}
