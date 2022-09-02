package de.datev.wjax.hello.consent.domain;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
