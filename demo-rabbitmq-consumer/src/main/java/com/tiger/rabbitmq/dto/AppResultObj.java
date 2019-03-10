package com.tiger.rabbitmq.dto;

/**
 * @author 轻描淡写
 * @version 1.0
 * @date 2018年9月29日
 */
public class AppResultObj {

    public final static int CODE_OK = 2000;// 成功
    public final static int CODE_NOT_ALLOW = 3425;// 不允许
    public final static int CODE_PARAM_ERR = 3406;// 参数错误
    public final static int CODE_NO_DATA = 3407;// 找不到数据
    public final static int CODE_SERVER_ERR = 4000;// 服务器异常

    private int code = CODE_OK; // 状态码，默认成功
    private String msg = "OK"; // 调用结果消息，默认OK
    private Object data = null; // 结果数据
    private boolean succeed = true;

    public AppResultObj() {
    }

    public AppResultObj(Object data) {
        this.data = data;
    }

    public AppResultObj(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AppResultObj(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public AppResultObj(int code, String msg, Object data, boolean succeed) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.succeed = succeed;
    }

    /**
     * 接口调用成功
     */
    public static AppResultObj success() {
        return new AppResultObj();
    }

    /**
     * 接口调用成功，传入需要返回的data数据
     *
     * @param data
     * @return
     */
    public static AppResultObj success(Object data) {
        return new AppResultObj(data);
    }

    /**
     * 接口调用失败：不允许未登录用户调用此接口
     *
     * @return
     */
    public static AppResultObj notAllow() {
        return new AppResultObj(AppResultObj.CODE_NOT_ALLOW, "未经许可的用户", null, false);
    }

    /**
     * 接口调用失败：不允许未登录用户调用此接口
     *
     * @return
     */
    public static AppResultObj notAllow(String msg) {
        return new AppResultObj(AppResultObj.CODE_NOT_ALLOW, msg, null, false);
    }

    /**
     * 接口调用失败：参数错误
     *
     * @return
     */
    public static AppResultObj parameterError() {
        return new AppResultObj(AppResultObj.CODE_PARAM_ERR, "参数解析错误", null, false);
    }

    /***
     * 接口调用失败：找不到数据
     */
    public static AppResultObj cannotFindData() {
        return new AppResultObj(AppResultObj.CODE_NO_DATA, "找不到数据", null, false);
    }

    /**
     * 接口调用失败：参数错误
     *
     * @return
     */
    public static AppResultObj parameterError(String msg) {
//		return new AppResultObj(AppResultObj.CODE_PARAM_ERR, "参数解析错误", msg, false);

        return new AppResultObj(AppResultObj.CODE_PARAM_ERR, msg, null, false);
    }

    /**
     * 接口调用失败：服务器异常
     *
     * @return
     */
    public static AppResultObj serverError() {
        return new AppResultObj(AppResultObj.CODE_SERVER_ERR, "服务器异常", null, false);
    }

    /**
     * 接口调用失败：服务器异常
     *
     * @return
     */
    public static AppResultObj serverError(String msg) {
        return new AppResultObj(AppResultObj.CODE_SERVER_ERR, msg, null, false);
    }

    public static AppResultObj newResult(int code, String msg, Object data) {
        AppResultObj appResultObj = new AppResultObj(code, msg, data);
        appResultObj.setSucceed(false);
        return appResultObj;
    }

    public static AppResultObj newResult(int code, String msg) {
        AppResultObj appResultObj = new AppResultObj(code, msg);
        appResultObj.setSucceed(false);
        return appResultObj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }
}
