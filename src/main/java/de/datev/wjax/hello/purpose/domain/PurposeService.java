package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.actors.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurposeService {

    private final PurposeFactory purposeFactory;
    private final PurposeRepository purposeRepository;
    private final DomainEventPublisher domainEventPublisher;

    public PurposeService(PurposeFactory purposeFactory, PurposeRepository purposeRepository, DomainEventPublisher domainEventPublisher) {
        this.purposeFactory = purposeFactory;
        this.purposeRepository = purposeRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Mono<PurposeCreatedEvent> createPurpose(Actor actor, CreatePurposeCommand command) {
        if (!actor.getScopes().contains(Scope.ADMIN)) {
            return Mono.error(new DomainException("User is not allowed to create purposes", ErrorType.USER_ERROR));
        }

        return Mono.just(purposeFactory.createPurpose(command)).map(purpose -> {
            var event = new PurposeCreatedEvent(purpose.getId(), purpose.getPurposeVersion(), purpose.getText(), purpose.getViability());
            domainEventPublisher.publish(event);
            return event;
        }).doOnNext(purposeRepository::save);
    }

    public Mono<PurposeReleasedEvent> releasePurpose(Actor actor, ReleasePurposeCommand command) {
        if (!actor.getScopes().contains(Scope.ADMIN)) {
            return Mono.error(new DomainException("User is not allowed to release purposes", ErrorType.USER_ERROR));
        }

        return purposeRepository.search(command.getUuid(), command.getVersion())
                .switchIfEmpty(Mono.error(new DomainException("The purpose could not be found", ErrorType.USER_ERROR)))
                .map(PurposeAggregate::release)
                .doOnNext(purposeRepository::save);
    }

    public Mono<PurposeVersionUpdatedEvent> createPurposeVersion(Actor actor, CreatePurposeVersionCommand command) {
        if (!actor.getScopes().contains(Scope.ADMIN)) {
            return Mono.error(new DomainException("User is not allowed to release purposes", ErrorType.USER_ERROR));
        }
        return purposeRepository.searchLatest(command.getId())
                .switchIfEmpty(Mono.error(new DomainException("The purpose could not be found", ErrorType.USER_ERROR)))
                .map(purposeAggregate -> purposeAggregate.createNewVersion(command.getUpdatedText()))
                .doOnNext(purposeRepository::save);
    }

    public Flux<PurposeAggregate> getPurposes(Actor actor) {
        if (!actor.getScopes().contains(Scope.ADMIN)) {
            return Flux.error(new DomainException("User is not allowed to view purposes", ErrorType.USER_ERROR));
        }
        return purposeRepository.all();
    }
}
