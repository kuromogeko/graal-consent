package de.datev.wjax.hello.consent.domain.purpose;

public interface TargetCharacteristic {
    default boolean isOfType(TargetCharacteristic targetCharacteristic) {
        return this.getCharacteristic().equals(targetCharacteristic.getCharacteristic());
    }

    Object getCharacteristic();
}
