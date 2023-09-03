package io.management.ua.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisher<T> {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(T event) {
        log.debug("Event occurred: {}", event);
        applicationEventPublisher.publishEvent(event);
    }

}
