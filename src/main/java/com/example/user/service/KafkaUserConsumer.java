package com.example.user.service;

import com.example.user.model.AuditLog;
import com.example.user.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;

@Service
public class KafkaUserConsumer {
    @Autowired
    private AuditLogRepository auditLogRepository;

    private static final Logger logger = LoggerFactory.getLogger(KafkaUserConsumer.class);

    @KafkaListener(topics = "user-events", groupId = "journal-group")
    public void consumeUserEvent(String message) {
        try {
            logger.info("Received event: {}", message);

            // Extract action (text before the first ":")
            String[] parts = message.split(":", 2);
            String action = parts.length > 1 ? parts[0].trim() : "UNKNOWN";
            String details = parts.length > 1 ? parts[1].trim() : message;

            AuditLog log = new AuditLog();
            log.setAction(action);
            log.setDetails(details);
            log.setTimestamp(LocalDateTime.now());

            auditLogRepository.save(log);
        } catch (Exception e) {
            logger.error("Error processing Kafka event: {}", message, e);
        }
    }

}