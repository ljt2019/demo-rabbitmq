package com.tiger.rabbitmq.producer.task;

import com.tiger.rabbitmq.constants.Constants;
import com.tiger.rabbitmq.dao.exp.BrokerMessageLogExpMapper;
import com.tiger.rabbitmq.entity.BrokerMessageLog;
import com.tiger.rabbitmq.entity.OrderInfo;
import com.tiger.rabbitmq.producer.RabbitOrderSender;
import com.tiger.rabbitmq.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务，定时拉取状态为0的消息重新推送到mq
 * Create by tiger on 2019/3/10
 */
@Component
public class RetryMessageTasker {

    private static final Logger logger = LoggerFactory.getLogger(RetryMessageTasker.class);

    @Autowired
    private RabbitOrderSender rabbitOrderSender;

    @Autowired
    private BrokerMessageLogExpMapper brokerMessageLogExpMapper;

    /**
     * 开启定时任务
     * mq发送ack给生产者时网络闪断
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void reSend() {

        logger.info("===开始定时任务=== ");

        /*查询消息状态为0(发送中) 且已经超时的消息集合
        会存在消息被投递两次的情况，
        因此需要设置最大超时，最大延迟时间，尽可能减少消息被重复投递的可能性*/
        List<BrokerMessageLog> list = brokerMessageLogExpMapper.selectStateAndTimeoutMessage();
        logger.info("====== 查询状态为0且超时未收到ack的消息集合：【{}】", JsonUtils.toJson(list));

        list.forEach(messageLog -> {  //遍历
            if (messageLog.getTryCount() >= 3) {
                brokerMessageLogExpMapper.changeBrokerMessageLogState(messageLog.getMessageId(), Constants.ORDER_SEND_FAILURE);
                logger.info("====== 消息重新投递次数超出限制,最多3次 ======");
            } else {
                try {
                    OrderInfo reSendOrder = JsonUtils.fromJson(messageLog.getMessage(), OrderInfo.class);
                    rabbitOrderSender.sendOrder(reSendOrder);
                    brokerMessageLogExpMapper.updateReSend(messageLog.getMessageId());
                    logger.info("====== 消息重新投递成功！ ======");
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("====== 消息重新投递失败，需要进行人工补偿【{}】", e.getMessage());
                }
            }
        });
    }
}
