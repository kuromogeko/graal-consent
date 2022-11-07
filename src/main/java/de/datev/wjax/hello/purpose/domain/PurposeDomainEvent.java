package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.DomainEvent;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

public interface PurposeDomainEvent extends DomainEvent {
    UUID getId();

    PurposeVersion getPurposeVersion();
}
