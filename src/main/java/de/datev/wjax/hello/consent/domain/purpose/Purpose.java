package de.datev.wjax.hello.consent.domain.purpose;

import de.datev.wjax.hello.consent.domain.actors.Subject;

import java.util.UUID;

//Entity
public class Purpose {
    private final UUID id;
    private PurposeVersion purposeVersion;
    private String text;
    private final Viability viability;

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

    public Purpose(UUID id, PurposeVersion purposeVersion, String text, Viability viability) {
        this.id = id;
        this.purposeVersion = purposeVersion;
        this.text = text;
        this.viability = viability;
    }

    public boolean subjectIsViable(Subject subject) {
        return subject.isOfType(this.getViability().getSubjectType()) && subject.hasCharacteristic(this.getViability().getTargetCharacteristic());
    }

    public boolean versionMatchesCurrentVersion(PurposeVersion agreedVersion) {
        return this.purposeVersion.equals(agreedVersion);
    }


    public boolean updateVersion(PurposeVersion purposeVersion, String updatedText) {
        if (this.getPurposeVersion().compareTo(purposeVersion) >= 0) {
            return false;
        }
        this.purposeVersion = purposeVersion;
        this.text = updatedText;
        return true;
    }
}
