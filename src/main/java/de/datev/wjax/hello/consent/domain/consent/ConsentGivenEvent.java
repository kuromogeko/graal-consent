package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;

import java.util.UUID;

public class ConsentGivenEvent implements DomainEvent {
    private final UUID consentId;
    private final ReferencedPurpose purpose;


    private final SubjectReference subjectReference;

    public ConsentGivenEvent(UUID consentId, ReferencedPurpose purpose, SubjectReference subjectReference) {
        this.consentId = consentId;
        this.purpose = purpose;
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

}
