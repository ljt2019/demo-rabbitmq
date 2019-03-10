package com.tiger.rabbitmq;

import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.producer.OrderSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqProducerApplicationTests {

    @Autowired
    OrderSender orderSender;

    @Test
    public void sendOut() throws Exception{
        String order = "44";
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("201903090000000000000000000000"+order);
        orderInfo.setName("测试订单"+order);
        String messageId = System.currentTimeMillis() + "$" + UUID.randomUUID();
        orderInfo.setMessageId(messageId);
        orderSender.sendOut(orderInfo);
    }
}
