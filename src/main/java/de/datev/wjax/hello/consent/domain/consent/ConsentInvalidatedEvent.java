package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;

import java.util.Objects;
import java.util.UUID;

public class ConsentInvalidatedEvent implements DomainEvent {
    private final UUID consentId;
    private final ReferencedPurpose referencedPurpose;
    private final SubjectReference subjectReference;

    public ConsentInvalidatedEvent(UUID consentId, ReferencedPurpose referencedPurpose, SubjectReference subjectReference) {
        this.consentId = consentId;
        this.referencedPurpose = referencedPurpose;
        this.subjectReference = subjectReference;
    }

    public UUID getConsentId() {
        return consentId;
    }

    public ReferencedPurpose getReferencedPurpose() {
        return referencedPurpose;
    }

    public SubjectReference getSubjectReference() {
        return subjectReference;
    }

}
