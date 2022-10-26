package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import de.datev.wjax.hello.consent.domain.purpose.Viability;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PurposeFactory {

    private final DomainEventPublisher domainEventPublisher;

    public PurposeFactory(DomainEventPublisher domainEventPublisher) {
        this.domainEventPublisher = domainEventPublisher;
    }

    public PurposeAggregate createPurpose(CreatePurposeCommand command) {
        return createPurpose(UUID.randomUUID(), new PurposeVersion(1), command.getText(), command.getViability());
    }

    private PurposeAggregate createPurpose(UUID id, PurposeVersion version, String text, Viability viability) {
        return new PurposeAggregate(id, version, text, viability, ReleaseStatus.CREATED, domainEventPublisher);
    }

    public PurposeAggregate replay(List<PurposeDomainEvent> events) {
        PurposeCreatedEvent creation = (PurposeCreatedEvent) events.stream().filter(event -> event instanceof PurposeCreatedEvent).findFirst().orElseThrow(() -> new RuntimeException("Aggregate was created without Creation Event!"));
        var aggregate = this.createPurpose(creation.getId(), creation.getPurposeVersion(), creation.getText(), creation.getViability());
        events.forEach(event -> {
            if (event instanceof PurposeReleasedEvent) {
                aggregate.applyRelease((PurposeReleasedEvent) event);
            }
            if (event instanceof PurposeVersionUpdatedEvent) {
                aggregate.applyVersionUpdate((PurposeVersionUpdatedEvent) event);
            }
        });
        return aggregate;
    }
}
