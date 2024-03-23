package io.management.ua.utility.service;

import io.management.ua.annotations.Export;
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
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostConstruct
    public void setupWebSocketTemplate() {
        this.simpMessagingTemplate.setSendTimeout(1000L);
    }

    @Export
    public void sendMessage(String topic, Object payload) {
        WebSocketMessage<Object> webSocketMessage = new WebSocketMessage<>();
        webSocketMessage.setData(payload);

        String content = "";
        try {
            if (payload != null) {
                content = UtilManager.objectMapper().writeValueAsString(webSocketMessage);
            }

            simpMessagingTemplate.convertAndSend(topic, content);
            log.debug("Message {} was sent to topic {}", payload, topic);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
