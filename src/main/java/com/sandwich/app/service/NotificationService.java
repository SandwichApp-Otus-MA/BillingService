package com.sandwich.app.service;

import com.sandwich.app.domain.dto.notificator.PaymentNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaTemplate<String, PaymentNotification> kafkaTemplate;

    @Value("${sandwich.kafka.producer.topic.notification}")
    private String notificationTopic;

    public void send(PaymentNotification notification) {
        try {
            kafkaTemplate.send(notificationTopic, notification);
        } catch (Exception ex) {
            log.error("Не удалось отправить уведомление со статусом платежа {}", notification.getUserId());
        }
    }
}
