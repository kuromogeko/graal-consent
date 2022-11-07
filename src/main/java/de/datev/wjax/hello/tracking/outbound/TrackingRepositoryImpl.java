package de.datev.wjax.hello.tracking.outbound;

import de.datev.wjax.hello.tracking.domain.ConsentHistory;
import de.datev.wjax.hello.tracking.domain.TrackingRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Component
public class TrackingRepositoryImpl implements TrackingRepository {

    private final HashMap<UUID, ConsentHistory> repo;

    public TrackingRepositoryImpl() {
        repo = new HashMap<>();
    }

    @Override
    public void save(ConsentHistory history) {
        repo.put(history.getConsentId(), history);
    }

    @Override
    public Optional<ConsentHistory> load(UUID uuid) {
        return Optional.ofNullable(repo.get(uuid));
    }
}
