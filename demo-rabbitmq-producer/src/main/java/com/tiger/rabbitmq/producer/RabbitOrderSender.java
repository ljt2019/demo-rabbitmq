package com.tiger.rabbitmq.producer;

import com.tiger.rabbitmq.config.ExchangeAndQueueConfig;
import com.tiger.rabbitmq.constants.Constants;
import com.tiger.rabbitmq.dao.exp.BrokerMessageLogExpMapper;
import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create by tiger on 2019/3/10
 */
@Component
public class RabbitOrderSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitOrderSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ExchangeAndQueueConfig exchangeAndQueueConfig;

    @Autowired
    private BrokerMessageLogExpMapper brokerMessageLogExpMapper;

    /**
     * 回调函数: confirm确认
     */
    final ConfirmCallback confirmCallback = new ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            logger.info("====== 确认函数入参，correlationData:【{}】；ack:【{}】；cause:【{}】"
                    , JsonUtils.toJson(correlationData), ack, cause);

            String messageId = correlationData.getId();

            //收到 ack 为 true 的确认信息，说明发送数据到mq成功
            if (ack) {
                //如果confirm返回成功 则进行更新
                brokerMessageLogExpMapper.changeBrokerMessageLogState(messageId, Constants.ORDER_SEND_SUCCESS);
                logger.info("====== 发送数据到mq成功，更新订单状态为已发送状态成功！======");
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                logger.info("====== 发送数据到mq失败！======");
            }
        }
    };

    /**
     * 发送消息方法调用: 构建自定义对象消息
     * ack
     *
     * @param orderInfo
     * @throws Exception
     */
    public void sendOrder(OrderInfo orderInfo) throws Exception {
        //设置回调监听
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //消息唯一ID
        CorrelationData correlationData = new CorrelationData(orderInfo.getMessageId());
        rabbitTemplate.convertAndSend(
                exchangeAndQueueConfig.getExchange()
                , exchangeAndQueueConfig.getRoutingKey()
                , orderInfo
                , correlationData
        );
    }

}
