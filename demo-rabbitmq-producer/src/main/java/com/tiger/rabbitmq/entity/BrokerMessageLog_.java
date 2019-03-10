package com.tiger.rabbitmq.entity;


import com.tiger.rabbitmq.mybatis.query.Property;


public class BrokerMessageLog_{

    public static final Property id =new Property("_log.id");

    public static final Property messageId =new Property("_log.message_id");

    public static final Property message =new Property("_log.message");

    public static final Property tryCount =new Property("_log.try_count");

    public static final Property nextRetry =new Property("_log.next_retry");

    public static final Property state =new Property("_log.state");

    public static final Property createDate =new Property("_log.create_date");

    public static final Property updateDate =new Property("_log.update_date");


}