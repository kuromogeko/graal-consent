package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.DomainEvent;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

public class PurposeReleasedEvent implements PurposeDomainEvent {
    private final UUID uuid;
    private final PurposeVersion purposeVersion;

    public PurposeReleasedEvent(UUID uuid, PurposeVersion purposeVersion) {
        this.uuid = uuid;
        this.purposeVersion = purposeVersion;
    }

    public UUID getId() {
        return uuid;
    }

    public PurposeVersion getPurposeVersion() {
        return purposeVersion;
    }
}
