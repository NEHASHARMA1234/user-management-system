package com.example.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaUserProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaUserProducer.class);

    public void publishUserEvent(String eventMessage) {
        try {
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("user-events", eventMessage);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    logger.error("Failed to publish Kafka event: {}", eventMessage, ex);
                } else {
                    logger.info("Published event: {}", eventMessage);
                }
            });
        } catch (Exception e) {
            logger.error("Kafka send threw an exception: {}", eventMessage, e);
        }
    }

}
