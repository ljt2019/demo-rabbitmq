package com.tiger.rabbitmq.producer;

import com.tiger.rabbitmq.config.ExchangeAndQueueConfig;
import com.tiger.rabbitmq.entity.OrderInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create by tiger on 2019/3/9
 */
@Component
public class OrderSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ExchangeAndQueueConfig exchangeAndQueueConfig;

    /**
     * 发送消息到队列
     * 1、在控制台创建一个exchange：【order.exchange】
     * 2、在控制台创建一个队列：【order.queue】
     * 3、将exchange与queue绑定上，【order.*】表示可以匹配，order.abc,但不能匹配order.abc.cdf,而【order.#】可以匹配任意后缀
     *
     * @param orderInfo
     */
    public void sendOut(OrderInfo orderInfo) throws Exception {

        //消息唯一id，这里指订单id
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(orderInfo.getId());

        rabbitTemplate.convertAndSend(
                exchangeAndQueueConfig.getExchange()        //指定交换器
                , exchangeAndQueueConfig.getRoutingKey()    //指定队列路由key，匹配上则发送
                , orderInfo                                 //消息载体
                , correlationData);                         //消息标识
        System.out.println("========发送消息到mq成功！=========");
    }
}
