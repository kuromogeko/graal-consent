package de.datev.wjax.hello.purpose.domain;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import de.datev.wjax.hello.consent.domain.purpose.Viability;

import java.util.UUID;

public class CreatePurposeCommand {
    private final String text;
    private final Viability viability;



    public CreatePurposeCommand(UUID id, String text, PurposeVersion purposeVersion, Viability viability, ReleaseStatus releaseStatus) {
        this.text = text;
        this.viability = viability;
    }

    public String getText() {
        return text;
    }


    public Viability getViability() {
        return viability;
    }

}
