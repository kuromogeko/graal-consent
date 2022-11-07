package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

public class PurposeVersionUpdatedEvent implements PurposeDomainEvent {
    private final UUID id;
    private final PurposeVersion updatedVersion;
    private final String updatedText;

    public PurposeVersionUpdatedEvent(UUID id, PurposeVersion updatedVersion, String updatedText) {
        this.id = id;
        this.updatedVersion = updatedVersion;
        this.updatedText = updatedText;
    }

    public UUID getId() {
        return id;
    }

    public String getUpdatedText() {
        return updatedText;
    }

    public PurposeVersion getPurposeVersion() {
        return updatedVersion;
    }
}
