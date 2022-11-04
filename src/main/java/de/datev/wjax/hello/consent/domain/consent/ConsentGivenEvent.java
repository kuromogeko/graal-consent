package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;
import de.datev.wjax.hello.consent.domain.actors.user.User;

import java.util.UUID;

public class ConsentGivenEvent implements DomainEvent {
    private final UUID consentId;
    private final ReferencedPurpose purpose;

    private final User user;

    private final SubjectReference subjectReference;

    public ConsentGivenEvent(UUID consentId, ReferencedPurpose purpose, User user, SubjectReference subjectReference) {
        this.consentId = consentId;
        this.purpose = purpose;
        this.user = user;
        this.subjectReference = subjectReference;
    }

    public UUID getConsentId() {
        return consentId;
    }

    public ReferencedPurpose getPurpose() {
        return purpose;
    }

    public SubjectReference getSubjectReference() {
        return subjectReference;
    }

    public User getUser() {
        return user;
    }
}
