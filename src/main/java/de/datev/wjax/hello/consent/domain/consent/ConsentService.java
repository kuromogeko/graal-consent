package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.actors.Subject;
import de.datev.wjax.hello.consent.domain.actors.organisation.Organisation;
import de.datev.wjax.hello.consent.domain.purpose.ConsentPurposeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static de.datev.wjax.hello.consent.domain.ErrorType.USER_ERROR;

@Service
public class ConsentService {
    private final ConsentFactory consentFactory;
    private final ConsentPurposeRepository consentPurposeRepository;
    private final ConsentRepository repository;


    public ConsentService(ConsentFactory consentFactory, ConsentPurposeRepository consentPurposeRepository, ConsentRepository repository) {
        this.consentFactory = consentFactory;
        this.consentPurposeRepository = consentPurposeRepository;
        this.repository = repository;
    }

    //Side effect, purpose repository, consent repository
    public Mono<ConsentGivenEvent> giveConsent(Actor actor, GiveConsentCommand command) {
        var purposeReference = consentPurposeRepository.getPurpose(command.getReferencedPurpose().getPurposeId())
                .switchIfEmpty(Mono.error(new DomainException("Purpose not found.", ErrorType.USER_ERROR)));

        return Mono.justOrEmpty(actor.getSubjectByReference(command.getSubjectReference()))
                .switchIfEmpty(Mono.error(new DomainException("Actor may not access referenced subject or it does not exist", ErrorType.USER_ERROR)))
                .zipWith(purposeReference)
                .flatMap(tuple -> {
                    var purpose = tuple.getT2();
                    Subject subject = tuple.getT1();
                    if (!purpose.subjectIsViable(subject)) {
                        return Mono.error(new DomainException("Referenced Subject is not of right type to consent to this purpose", USER_ERROR));
                    }
                    if (!purpose.versionMatchesCurrentVersion(command.getReferencedPurpose().getAgreedVersion())) {
                        return Mono.error(new DomainException("The version of the purpose you intend to agree to is not the current version", USER_ERROR));
                    }
                    return Mono.just(subject);
                })
                .flatMap(subject -> this.repository.getBySubjectAndPurposeRef(subject.getId(), command.getReferencedPurpose())
                        .switchIfEmpty(this.consentFactory.createConsent(command, subject)))
                .flatMap(consentAggregate -> {
                    var event = consentAggregate.giveConsent(actor.getUser());
                    return repository.save(consentAggregate).thenReturn(event);
                });
    }

    public Mono<ConsentWithdrawnEvent> withdrawConsent(Actor actor, WithdrawConsentCommand command) {
        return this.repository.getById(command.getConsentId())
                .switchIfEmpty(Mono.error(new DomainException("Actor may not access referenced consent or it does not exist", ErrorType.USER_ERROR)))
                .flatMap(consentAggregate -> {
                    if (actor.getSubjectByReference(consentAggregate.getSubjectReference()).isEmpty()) {
                        return Mono.error(new DomainException("Actor may not access referenced consent or it does not exist", ErrorType.USER_ERROR));
                    }
                    var event = consentAggregate.withdrawConsent(actor.getUser());
                    return repository.save(consentAggregate).thenReturn(event);
                });
    }

    public Flux<ConsentAggregate> getConsents(Actor actor) {
        var relevantSubjects = actor.getOrganisations().stream().map(Organisation::getId)
                .collect(Collectors.toList());
        relevantSubjects.add(actor.getUser().getId());
        return repository.getBySubjects(relevantSubjects);
    }


}
