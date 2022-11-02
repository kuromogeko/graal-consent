package de.datev.wjax.hello.consent.domain.purpose;

public class PurposeVersion {
    private final Integer value;

    public PurposeVersion(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Compares the versions of purposes
     * @param purposeVersion Version to compare to
     * @return 0 if equal, 1 if Input is smaller version, -1 if Input is greater version
     */
    public int compareTo(PurposeVersion purposeVersion){
        return this.getValue().compareTo(purposeVersion.getValue());
    }

    public boolean equals(PurposeVersion version) {
        return this.getValue().equals(version.getValue());
    }

    public PurposeVersion increaseVersion(){
        return new PurposeVersion(this.getValue()+1);
    }
}
