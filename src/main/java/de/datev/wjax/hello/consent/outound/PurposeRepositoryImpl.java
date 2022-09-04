package de.datev.wjax.hello.consent.outound;

import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.purpose.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.UUID;

@Component
public class PurposeRepositoryImpl implements PurposeRepository {
    private final HashMap<UUID, Purpose> map;

    public PurposeRepositoryImpl() {
        map = new HashMap<>();
        map.put(UUID.fromString("facade00-0000-4000-a000-000000000000"),
                new Purpose(UUID.fromString("facade00-0000-4000-a000-000000000000"),
                        new PurposeVersion(1),
                        "This is a purpose",
                        new Viability(SubjectType.USER, new UserCharacteristic(UserType.DEFAULT))));
    }

    @Override
    public Mono<Purpose> getPurpose(UUID purposeId) {
        var result = this.map.get(purposeId);
        if (null == result) {
            return Mono.empty();
        }
        return Mono.just(result);
    }
}
