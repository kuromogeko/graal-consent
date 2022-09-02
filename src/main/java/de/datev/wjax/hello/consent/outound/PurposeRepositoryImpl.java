package de.datev.wjax.hello.consent.outound;

import de.datev.wjax.hello.consent.domain.purpose.Purpose;
import de.datev.wjax.hello.consent.domain.purpose.PurposeRepository;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.UUID;

@Component
public class PurposeRepositoryImpl implements PurposeRepository {
    private final HashMap<UUID, Purpose> map;

    public PurposeRepositoryImpl() {
        map = new HashMap<>();
    }

    @Override
    public Mono<Purpose> getPurpose(UUID purposeId) {
        var result = this.map.get(purposeId);
        if(null == result){
            return Mono.empty();
        }
        return Mono.just(result);
    }
}
