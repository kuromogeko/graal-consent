package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import de.datev.wjax.hello.consent.domain.purpose.Viability;

import java.util.UUID;

public class Purpose {
    private final UUID id;
    private final PurposeVersion purposeVersion;
    private String text;
    private Viability viability;

    private ReleaseStatus releaseStatus;

    public UUID getId() {
        return id;
    }

    public PurposeVersion getPurposeVersion() {
        return purposeVersion;
    }

    public String getText() {
        return text;
    }

    private Viability getViability() {
        return viability;
    }

    public ReleaseStatus getReleaseStatus() {
        return releaseStatus;
    }

    public Purpose(UUID id, PurposeVersion purposeVersion, String text, Viability viability, ReleaseStatus releaseStatus) {
        this.id = id;
        this.purposeVersion = purposeVersion;
        this.text = text;
        this.viability = viability;
        this.releaseStatus = releaseStatus;
    }

}
