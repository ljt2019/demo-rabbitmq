package com.tiger.rabbitmq.entity;


import com.tiger.rabbitmq.mybatis.query.Property;


public class OrderInfo_{

    public static final Property id =new Property("_info.id");

    public static final Property messageId =new Property("_info.message_id");

    public static final Property name =new Property("_info.name");

    public static final Property state =new Property("_info.state");

    public static final Property createDate =new Property("_info.create_date");

    public static final Property updateDate =new Property("_info.update_date");


}