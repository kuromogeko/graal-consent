package de.datev.wjax.hello.tracking.domain;

import java.util.Optional;
import java.util.UUID;

public interface TrackingRepository {
    void save(ConsentHistory history);

    Optional<ConsentHistory> load(UUID uuid);
}
