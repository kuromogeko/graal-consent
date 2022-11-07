package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PurposeRepository {
    Mono<Void> save(PurposeDomainEvent purposeAggregate);

    Mono<PurposeAggregate> search(UUID uuid, PurposeVersion version);

    Mono<PurposeAggregate> searchLatest(UUID id);
}
