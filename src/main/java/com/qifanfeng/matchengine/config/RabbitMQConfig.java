package com.qifanfeng.matchengine.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "app.rabbitmq.enabled", havingValue = "true")
public class RabbitMQConfig {
    // RabbitMQ auto-config handles the rest when enabled.
    // Custom exchanges/queues can be defined here for production use.
    public static final String MATCH_QUEUE = "match.results";
    public static final String MATCH_EXCHANGE = "match.exchange";
    public static final String MATCH_ROUTING_KEY = "match.routing";
}
