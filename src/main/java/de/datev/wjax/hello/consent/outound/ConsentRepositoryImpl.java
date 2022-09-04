package de.datev.wjax.hello.consent.outound;

import de.datev.wjax.hello.consent.domain.consent.ConsentAggregate;
import de.datev.wjax.hello.consent.domain.consent.ConsentRepository;
import de.datev.wjax.hello.consent.domain.consent.ReferencedPurpose;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.UUID;

@Component
public class ConsentRepositoryImpl implements ConsentRepository {

    private final HashMap<UUID, ConsentAggregate> map;

    public ConsentRepositoryImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public Mono<ConsentAggregate> getById(UUID id) {
        var result = this.map.get(id);
        if (null == result) {
            return Mono.empty();
        }
        return Mono.just(result);
    }

    @Override
    public Mono<Void> save(ConsentAggregate consentAggregate) {
       return Mono.just(consentAggregate)
                .mapNotNull(consentAggregate1 -> this.map.put(consentAggregate1.getConsentId(),consentAggregate1))
                .then();
    }

    @Override
    public Mono<ConsentAggregate> getBySubjectAndPurposeRef(UUID subjectId, ReferencedPurpose referencedPurpose) {
        return Mono.justOrEmpty(this.map.values().stream().filter(consentAggregate -> consentAggregate.getSubjectReference().getId().equals(subjectId) && consentAggregate.getPurpose().equals(referencedPurpose))
                .findFirst());
    }
}
