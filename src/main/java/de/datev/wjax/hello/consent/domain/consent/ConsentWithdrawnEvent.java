package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;

import java.util.UUID;

public class ConsentWithdrawnEvent implements DomainEvent {
    private final UUID consentId;
    private final ReferencedPurpose purpose;


    private final SubjectReference reference;

    public ConsentWithdrawnEvent(UUID consentId, ReferencedPurpose purpose, SubjectReference reference) {
        this.consentId = consentId;
        this.purpose = purpose;
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
}
