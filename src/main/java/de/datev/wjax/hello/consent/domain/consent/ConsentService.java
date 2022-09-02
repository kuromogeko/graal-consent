package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ConsentService {
    private final ConsentFactory consentFactory;

    public ConsentService(ConsentFactory consentFactory) {
        this.consentFactory = consentFactory;
    }

    public Mono<ConsentGivenEvent> giveConsent(Actor actor, GiveConsentCommand command){
        return Mono.justOrEmpty(actor.getSubjectByReference(command.getSubjectReference()))
                .switchIfEmpty(Mono.error(new DomainException("Actor may not access referenced subject or it does not exist", ErrorType.USER_ERROR)))
                //TODO load from repo or create!
                .flatMap(subject -> this.consentFactory.createConsent(command,subject))
                .map(ConsentAggregate::giveConsent);
    }
}
