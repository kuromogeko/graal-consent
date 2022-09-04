package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;

import java.util.UUID;

public class ReferencedPurpose {
    private final UUID purposeId;
    private final PurposeVersion agreedVersion;

    public ReferencedPurpose(UUID purposeId, PurposeVersion agreedVersion) {
        this.purposeId = purposeId;
        this.agreedVersion = agreedVersion;
    }

    public PurposeVersion getAgreedVersion() {
        return agreedVersion;
    }

    public boolean equals(ReferencedPurpose obj) {
        return this.purposeId == obj.getPurposeId() && this.agreedVersion == obj.getAgreedVersion();
    }

    public UUID getPurposeId() {
        return purposeId;
    }
}
