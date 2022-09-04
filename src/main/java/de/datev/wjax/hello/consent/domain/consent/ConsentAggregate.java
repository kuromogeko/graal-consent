package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;

import java.util.UUID;

//Aggregate
//Write Model
public class ConsentAggregate {
    private ReferencedPurpose purpose;
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

    public ConsentGivenEvent giveConsent(){
        if(this.status == Status.GIVEN){
            throw new DomainException("Consent is already given", ErrorType.USER_ERROR);
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

    protected ConsentAggregate applyGivenConsent(ConsentGivenEvent consentGivenEvent) {
        this.status = Status.GIVEN;
        return this;
    }

    public Status getStatus() {
        return this.status;
    }
}
