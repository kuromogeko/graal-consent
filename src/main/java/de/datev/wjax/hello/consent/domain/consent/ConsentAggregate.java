package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

//Aggregate
//Write Model
public class ConsentAggregate {
    private final ReferencedPurpose purpose;
    private Status status;
    private final SubjectReference subjectReference;
    private final UUID consentId;

    private transient final DomainEventPublisher domainEventPublisher;

    protected ConsentAggregate(ReferencedPurpose purpose, Status status, SubjectReference subjectReference, UUID consentId, DomainEventPublisher domainEventPublisher) {
        this.purpose = purpose;
        this.status = status;
        this.subjectReference = subjectReference;
        this.consentId = consentId;
        this.domainEventPublisher = domainEventPublisher;
    }

    public ConsentGivenEvent giveConsent() {
        if (this.status == Status.GIVEN || this.status == Status.INVALID) {
            throw new DomainException("Consent is either already given or no longer valid", ErrorType.USER_ERROR);
        }
        this.status = Status.GIVEN;
        ConsentGivenEvent event = new ConsentGivenEvent(this.getConsentId(), this.getPurpose(), this.getSubjectReference());
        this.domainEventPublisher.publish(event);
        return event;
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


    public Status getStatus() {
        return this.status;
    }

    public ConsentWithdrawnEvent withdrawConsent() {
        if (this.status != Status.GIVEN) {
            throw new DomainException("Consent is not given, so cannot be withdrawn", ErrorType.USER_ERROR);
        }
        this.status = Status.WITHDRAWN;
        ConsentWithdrawnEvent event = new ConsentWithdrawnEvent(this.getConsentId(), this.getPurpose(), this.getSubjectReference());
        this.domainEventPublisher.publish(event);
        return event;
    }

    public void invalidateForSmallerVersions(PurposeVersion version) {
        if (this.purpose.getAgreedVersion().compareTo(version) >= 0) {
            return;
        }
        this.status = Status.INVALID;
    }
}
