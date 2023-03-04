package com.example.springbootauthdemo.reservation.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private final ApplicationEventPublisher publisher;

    Publisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishTestResultUploadedEvent(final Long reservationId) {
        // Publishing event created by extending ApplicationEvent
        publisher.publishEvent(new TestResultUploadedEvent(this, reservationId));
    }
}