package de.datev.wjax.hello.consent.domain.purpose;

public class Viability {
    public SubjectType getSubjectType() {
        return subjectType;
    }

    public TargetCharacteristic getTargetCharacteristic() {
        return targetCharacteristic;
    }

    private final SubjectType subjectType;
    private final TargetCharacteristic targetCharacteristic;

    public Viability(SubjectType subjectType, TargetCharacteristic targetCharacteristic) {
        this.subjectType = subjectType;
        this.targetCharacteristic = targetCharacteristic;
    }
}
