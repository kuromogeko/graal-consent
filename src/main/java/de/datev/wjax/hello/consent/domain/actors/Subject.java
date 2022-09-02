package de.datev.wjax.hello.consent.domain.actors;

import de.datev.wjax.hello.consent.domain.consent.SubjectReference;
import de.datev.wjax.hello.consent.domain.purpose.SubjectType;
import de.datev.wjax.hello.consent.domain.purpose.TargetCharacteristic;

import java.util.UUID;

public interface Subject {
    boolean isOfType(SubjectType type);
    boolean hasCharacteristic(TargetCharacteristic characteristic);
    UUID getId();
    default SubjectReference getReference(){
        return new SubjectReference(this.getId());
    }
}
