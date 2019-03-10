package com.tiger.rabbitmq.entity;

import com.tiger.rabbitmq.mybatis.base.EntityBase;
import java.util.Date;

public class BrokerMessageLog extends EntityBase {
    /**
     * 消息id
     *
     * @mbggenerated
     */
    private String messageId;

    /**
     * 消息json数据
     *
     * @mbggenerated
     */
    private String message;

    /**
     * 重试次数
     *
     * @mbggenerated
     */
    private Integer tryCount;

    /**
     * 下次重试时间或超时时间
     *
     * @mbggenerated
     */
    private Date nextRetry;

    /**
     * 消息id
     *
     * @mbggenerated
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 消息id
     *
     * @mbggenerated
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 消息json数据
     *
     * @mbggenerated
     */
    public String getMessage() {
        return message;
    }

    /**
     * 消息json数据
     *
     * @mbggenerated
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 重试次数
     *
     * @mbggenerated
     */
    public Integer getTryCount() {
        return tryCount;
    }

    /**
     * 重试次数
     *
     * @mbggenerated
     */
    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    /**
     * 下次重试时间或超时时间
     *
     * @mbggenerated
     */
    public Date getNextRetry() {
        return nextRetry;
    }

    /**
     * 下次重试时间或超时时间
     *
     * @mbggenerated
     */
    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }
}