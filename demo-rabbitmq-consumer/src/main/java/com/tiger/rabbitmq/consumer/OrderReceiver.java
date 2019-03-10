package com.tiger.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.service.OrderInfoService;
import com.tiger.rabbitmq.utils.JsonUtils;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Create by tiger on 2019/3/9
 */
@Component
public class OrderReceiver {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 手动签收模式，依赖channel
     * 注解方式实现消费,
     * 注解 @RabbitListener 可以自动创建exchange和queuey以及添加绑定关系
     * 监听消费mq中存在符合条件的消息
     *
     * @param orderInfo
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "${spring.rabbitmq.listener.order.queue.name}"
                            , durable = "${spring.rabbitmq.listener.order.queue.durable}"
                    ),
                    exchange = @Exchange(
                            value = "${spring.rabbitmq.listener.order.exchange.name}"
                            , durable = "${spring.rabbitmq.listener.order.exchange.durable}"
                            , type = "${spring.rabbitmq.listener.order.exchange.type}"
                            , ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"
                    ),
                    key = "${spring.rabbitmq.listener.order.routingKey}"
            )
    )
    @RabbitHandler
    public void receive(@Payload OrderInfo orderInfo
            , @Headers Map<String, Object> headers
            , Channel channel) throws Exception {
        System.out.println("====== 消费者消费：订单id = " + orderInfo.getId());
        System.out.println("====== 消费者消费：orderInfo【" + JsonUtils.toJson(orderInfo) + "】");

        //消费成功，修改订单状态
        orderInfo.setState(0);//不显示
        orderInfo.setUpdateDate(new Date());
        orderInfoService.updateByPrimaryKey(orderInfo);

        // TODO 手动签收 ACK,通知mq将消息在队列中删除
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);//不支持批量签收

    }
}
