package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;
import de.datev.wjax.hello.consent.domain.actors.user.User;

import java.util.UUID;

public class ConsentWithdrawnEvent implements DomainEvent {
    private final UUID consentId;
    private final ReferencedPurpose purpose;

    private final User user;
    private final SubjectReference reference;

    public ConsentWithdrawnEvent(UUID consentId, ReferencedPurpose purpose, User user, SubjectReference reference) {
        this.consentId = consentId;
        this.purpose = purpose;
        this.user = user;
        this.reference = reference;
    }

    public SubjectReference getReference() {
        return reference;
    }

    public UUID getConsentId() {
        return consentId;
    }

    public ReferencedPurpose getPurpose() {
        return purpose;
    }

    public User getUser() {
        return user;
    }
}
