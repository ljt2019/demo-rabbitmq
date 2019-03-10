package com.tiger.rabbitmq.service;

import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.entity.OrderInfoExample;
import com.tiger.rabbitmq.mybatis.base.IBaseService;
import com.tiger.rabbitmq.mybatis.base.PageParameter;
import com.tiger.rabbitmq.mybatis.query.Query;
import java.util.List;

public interface OrderInfoService extends IBaseService<OrderInfo, OrderInfoExample> {
    List<OrderInfo> query(Query query, PageParameter pageParameter);
}