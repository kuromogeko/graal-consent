package de.datev.wjax.hello.consent.domain.purpose;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class PurposeVersion {
    private final Integer value;

    @JsonCreator
    public PurposeVersion(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Compares the versions of purposes
     *
     * @param purposeVersion Version to compare to
     * @return 0 if equal, 1 if Input is smaller version, -1 if Input is greater version
     */
    public int compareTo(PurposeVersion purposeVersion) {
        return this.getValue().compareTo(purposeVersion.getValue());
    }

    public boolean equals(PurposeVersion version) {
        return this.getValue().equals(version.getValue());
    }

    public PurposeVersion increaseVersion() {
        return new PurposeVersion(this.getValue() + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurposeVersion that = (PurposeVersion) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
