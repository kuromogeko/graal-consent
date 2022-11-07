package de.datev.wjax.hello.tracking.domain;

import java.util.Optional;
import java.util.UUID;

public interface TrackingRepository {
    void save(ConsentHistory history);

    ConsentHistory load(UUID uuid);
}
