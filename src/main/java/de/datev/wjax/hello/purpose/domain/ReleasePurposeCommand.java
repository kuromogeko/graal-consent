package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

public class ReleasePurposeCommand {
    private final UUID uuid;
    private final PurposeVersion version;

    public ReleasePurposeCommand(UUID uuid, PurposeVersion version) {
        this.uuid = uuid;
        this.version = version;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PurposeVersion getVersion() {
        return version;
    }
}
