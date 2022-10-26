package de.datev.wjax.hello.purpose.outbound;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import de.datev.wjax.hello.purpose.domain.PurposeAggregate;
import de.datev.wjax.hello.purpose.domain.PurposeDomainEvent;
import de.datev.wjax.hello.purpose.domain.PurposeFactory;
import de.datev.wjax.hello.purpose.domain.PurposeRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class PurposeRepositoryImpl implements PurposeRepository {

    private final HashMap<PurposeKey, List<PurposeDomainEvent>> map;
    private final PurposeFactory factory;

    public PurposeRepositoryImpl(PurposeFactory factory) {
        this.factory = factory;
        this.map = new HashMap<>();
    }

    @Override
    public Mono<Void> save(PurposeDomainEvent event) {
        PurposeKey key = new PurposeKey(event.getId(), event.getPurposeVersion());
        return Mono.just(map.put(key, addToList(event, key))).then();
    }

    private List<PurposeDomainEvent> addToList(PurposeDomainEvent event, PurposeKey key) {
        ArrayList<PurposeDomainEvent> eventList = new ArrayList<>(map.getOrDefault(key, List.of()));
        eventList.add(event);
        return eventList;
    }

    @Override
    public Mono<PurposeAggregate> search(UUID uuid, PurposeVersion version) {
        return Mono.justOrEmpty(Optional.ofNullable(map.get(new PurposeKey(uuid,version))))
                .map(this.factory::replay);
    }

    // Strictly speaking this is impure, the domain logic of what latest means should be inside the domain?!
    @Override
    public Mono<PurposeAggregate> searchLatest(UUID id) {
        return Mono.justOrEmpty(map.keySet().stream().filter(purposeKey -> purposeKey.getUuid().equals(id))
                .reduce((leftKey, rightKey) -> {
                    if(leftKey.getVersion().compareTo(rightKey.getVersion())>=0){
                        return leftKey;
                    }
                    return rightKey;
                }).map(purposeKey -> this.factory.replay(map.get(purposeKey))));
    }
}
