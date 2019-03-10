package com.tiger.rabbitmq.service;

import com.tiger.rabbitmq.entity.BrokerMessageLog;
import com.tiger.rabbitmq.entity.BrokerMessageLogExample;
import com.tiger.rabbitmq.mybatis.base.IBaseService;
import com.tiger.rabbitmq.mybatis.base.PageParameter;
import com.tiger.rabbitmq.mybatis.query.Query;
import java.util.List;

public interface BrokerMessageLogService extends IBaseService<BrokerMessageLog, BrokerMessageLogExample> {
    List<BrokerMessageLog> query(Query query, PageParameter pageParameter);
}