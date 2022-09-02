package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.actors.Subject;
import de.datev.wjax.hello.consent.domain.purpose.PurposeRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static de.datev.wjax.hello.consent.domain.ErrorType.USER_ERROR;

@Component
public class ConsentFactory {

    private final PurposeRepository purposeRepository;
    private final DomainEventPublisher domainEventPublisher;

    public ConsentFactory(PurposeRepository purposeRepository, DomainEventPublisher domainEventPublisher) {
        this.purposeRepository = purposeRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public Mono<ConsentAggregate> createConsent(GiveConsentCommand command, Subject subject) {
        return purposeRepository.getPurpose(command.getReferencedPurpose().getPurposeId())
                .flatMap(purpose -> {
                    if (!purpose.subjectIsViable(subject)) {
                        return Mono.error(new DomainException("Referenced Subject is not of right type to consent to this purpose", USER_ERROR));
                    }
                    if(!purpose.versionMatchesCurrentVersion(command.getReferencedPurpose().getAgreedVersion())){
                        return Mono.error(new DomainException("The version of the purpose you intend to agree to is not the current version", USER_ERROR));
                    }
                    return Mono.just(new ConsentAggregate(command.getReferencedPurpose(), command.getStatus(), subject.getReference(), UUID.randomUUID(), domainEventPublisher));
                });
    }


}
