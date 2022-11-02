package de.datev.wjax.hello.consent.domain.purpose;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ConsentPurposeRepository {

    Mono<Purpose> getPurpose(UUID purposeId);
    Mono<Void> save(Purpose purpose);
}
