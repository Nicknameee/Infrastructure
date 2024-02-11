package io.management.ua.utility.service;

import io.management.ua.utility.UtilManager;
import io.management.ua.utility.models.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebSocketService<T> {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostConstruct
    public void setupWebSocketTemplate() {
        this.simpMessagingTemplate.setSendTimeout(1000L);
    }

    public void sendMessage(String topic, T payload) {
        WebSocketMessage<T> webSocketMessage = new WebSocketMessage<>();
        webSocketMessage.setData(payload);

        try {
            simpMessagingTemplate.convertAndSend(topic, UtilManager.objectMapper().writeValueAsString(webSocketMessage));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
