package com.tiger.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * Create by ljt on 2019/3/10
 */
@Configuration
@ConfigurationProperties(prefix = "order")
public class ExchangeAndQueueConfig {

    private String exchange;    //交换器名称
    private String routingKey;  //队列路由key

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}
