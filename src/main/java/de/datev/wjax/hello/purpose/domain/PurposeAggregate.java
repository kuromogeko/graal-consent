package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import de.datev.wjax.hello.consent.domain.purpose.Viability;

import java.util.UUID;

public class PurposeAggregate {
    private final UUID id;
    private PurposeVersion purposeVersion;
    private String text;
    private Viability viability;

    private ReleaseStatus releaseStatus;

    public UUID getId() {
        return id;
    }

    public PurposeVersion getPurposeVersion() {
        return purposeVersion;
    }

    public String getText() {
        return text;
    }

    public Viability getViability() {
        return viability;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public final transient DomainEventPublisher domainEventPublisher;

    public PurposeAggregate(UUID id, PurposeVersion purposeVersion, String text, Viability viability, ReleaseStatus releaseStatus, DomainEventPublisher domainEventPublisher) {
        this.id = id;
        this.purposeVersion = purposeVersion;
        this.text = text;
        this.viability = viability;
        this.releaseStatus = releaseStatus;
        this.domainEventPublisher = domainEventPublisher;
    }

    public PurposeReleasedEvent release() {
        if(this.releaseStatus != ReleaseStatus.CREATED){
            throw new DomainException("Purpose is not in a state where a release is possible", ErrorType.USER_ERROR);
        }
        var event = new PurposeReleasedEvent(this.getId(), this.getPurposeVersion());
        this.domainEventPublisher.publish(event);
        this.applyRelease(event);
        return event;
    }

    public PurposeVersionUpdatedEvent createNewVersion(String updatedText) {
        if(this.releaseStatus != ReleaseStatus.RELEASED){
            throw new DomainException("A new version of a purpose can only be created if it has been released", ErrorType.USER_ERROR);
        }
        PurposeVersionUpdatedEvent event = new PurposeVersionUpdatedEvent(this.getId(), this.purposeVersion.increaseVersion(), updatedText);
        this.domainEventPublisher.publish(event);
        this.applyVersionUpdate(event);
        return event;
    }

    public void applyRelease(PurposeReleasedEvent event) {
        if(this.releaseStatus != ReleaseStatus.CREATED){
            return;
        }
        this.releaseStatus = ReleaseStatus.RELEASED;
    }

    public void applyVersionUpdate(PurposeVersionUpdatedEvent event){
        if(this.releaseStatus != ReleaseStatus.RELEASED || this.purposeVersion == event.getPurposeVersion()){
            return;
        }
        this.purposeVersion = event.getPurposeVersion();
        this.text = event.getUpdatedText();
    }
}
