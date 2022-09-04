package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.actors.Subject;
import de.datev.wjax.hello.consent.domain.purpose.PurposeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static de.datev.wjax.hello.consent.domain.ErrorType.USER_ERROR;

@Service
public class ConsentService {
    private final ConsentFactory consentFactory;
    private final PurposeRepository purposeRepository;
    private final ConsentRepository repository;


    public ConsentService(ConsentFactory consentFactory, PurposeRepository purposeRepository, ConsentRepository repository) {
        this.consentFactory = consentFactory;
        this.purposeRepository = purposeRepository;
        this.repository = repository;
    }

    //Side effect, purpose repository, consent repository
    public Mono<ConsentGivenEvent> giveConsent(Actor actor, GiveConsentCommand command){
        var purposeReference = purposeRepository.getPurpose(command.getReferencedPurpose().getPurposeId());

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
                .flatMap(subject -> this.repository.getBySubjectAndPurposeRef(subject.getId(),command.getReferencedPurpose())
                        .switchIfEmpty(this.consentFactory.createConsent(command,subject)))
                .map(ConsentAggregate::giveConsent);
    }
}
