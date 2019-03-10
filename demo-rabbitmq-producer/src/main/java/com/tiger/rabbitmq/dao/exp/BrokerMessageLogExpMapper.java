package com.tiger.rabbitmq.dao.exp;

import com.tiger.rabbitmq.entity.BrokerMessageLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrokerMessageLogExpMapper {

    /**
     * 查询消息状态为0(发送中) 且已经超时的消息集合
     *
     * @return
     */
    List<BrokerMessageLog> selectStateAndTimeoutMessage();

    /**
     * 重新发送统计count发送次数 +1
     *
     * @param messageId
     */
    void updateReSend(@Param("messageId") String messageId);

    /**
     * 更新最终消息发送结果 成功 or 失败
     *
     * @param messageId
     * @param state
     */
    void changeBrokerMessageLogState(@Param("messageId") String messageId, @Param("state") Integer state);

}