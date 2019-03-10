package com.tiger.rabbitmq.service.impl;


import com.tiger.rabbitmq.constants.Constants;
import com.tiger.rabbitmq.dao.BrokerMessageLogMapper;
import com.tiger.rabbitmq.dao.OrderInfoMapper;
import com.tiger.rabbitmq.entity.BrokerMessageLog;
import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.entity.OrderInfoExample;
import com.tiger.rabbitmq.mybatis.base.BaseServiceImpl;
import com.tiger.rabbitmq.mybatis.base.IBaseMapper;
import com.tiger.rabbitmq.mybatis.base.PageParameter;
import com.tiger.rabbitmq.mybatis.query.Query;
import com.tiger.rabbitmq.producer.RabbitOrderSender;
import com.tiger.rabbitmq.service.OrderInfoService;
import com.tiger.rabbitmq.utils.JsonUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, OrderInfoExample> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper baseMapper;

    @Autowired
    BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    RabbitOrderSender rabbitOrderSender;

    @Override
    public IBaseMapper<OrderInfo, OrderInfoExample> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public List<OrderInfo> query(Query query, PageParameter pageParameter) {
        return baseMapper.query(query, pageParameter);
    }

    @Override
    public int createOrder(OrderInfo orderInfo) throws Exception {

        //1、插入订单业务记录
        String messageId = System.currentTimeMillis() + "$" + UUID.randomUUID();
        orderInfo.setMessageId(messageId);//消息唯一标识
        Date orderTime = new Date();
        orderInfo.setCreateDate(orderTime);
        orderInfo.setState(1);//显示
        int infoFlag = baseMapper.insertSelective(orderInfo);

        //2、插入消息日志记录
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息唯一ID
        brokerMessageLog.setMessageId(orderInfo.getMessageId());
        // 保存消息整体 转为JSON 格式存储入库
        brokerMessageLog.setMessage(JsonUtils.toJson(orderInfo));
        // 设置消息状态为0 表示发送中
        brokerMessageLog.setState(Constants.ORDER_SENDING);
        // 设置消息未确认超时时间窗口为 一分钟
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime, Constants.ORDER_TIMEOUT));
        brokerMessageLog.setCreateDate(new Date());
        brokerMessageLog.setUpdateDate(new Date());
        int logFlag = brokerMessageLogMapper.insertSelective(brokerMessageLog);

        //3、订单创建成功，将消息发送到mq
        rabbitOrderSender.sendOrder(orderInfo);

        return (infoFlag > 0 && logFlag > 0) ? 1 : 0;
    }
}