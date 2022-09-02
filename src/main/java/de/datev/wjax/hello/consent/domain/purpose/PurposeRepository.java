package de.datev.wjax.hello.consent.domain.purpose;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface PurposeRepository {

    Mono<Purpose> getPurpose(UUID purposeId);
}
