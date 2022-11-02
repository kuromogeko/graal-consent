package de.datev.wjax.hello.consent.domain.consent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ConsentRepository {
    Mono<ConsentAggregate> getById(UUID id);

    Mono<Void> save(ConsentAggregate consentAggregate);

    Mono<ConsentAggregate> getBySubjectAndPurposeRef(UUID subjectId, ReferencedPurpose referencedPurpose);

    Flux<ConsentAggregate> getByPurpose(UUID id);
}
