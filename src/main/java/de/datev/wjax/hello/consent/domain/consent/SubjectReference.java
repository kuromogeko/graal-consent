package de.datev.wjax.hello.consent.domain.consent;

import java.util.UUID;

public class SubjectReference {
    private final UUID id;

    public SubjectReference(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
