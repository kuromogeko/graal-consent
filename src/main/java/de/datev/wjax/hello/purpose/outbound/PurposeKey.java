package de.datev.wjax.hello.purpose.outbound;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

public class PurposeKey {
    private final UUID uuid;
    private final PurposeVersion version;

    public PurposeKey(UUID uuid, PurposeVersion version) {
        this.uuid = uuid;
        this.version = version;
    }

    public boolean equals(PurposeKey obj) {
        return this.getUuid().equals(obj.getUuid()) && this.getVersion().equals(obj.getVersion());
    }

    protected UUID getUuid() {
        return uuid;
    }

    protected PurposeVersion getVersion() {
        return version;
    }
}
