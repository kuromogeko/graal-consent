package de.datev.wjax.hello.consent.domain.actors.user;

import de.datev.wjax.hello.consent.domain.purpose.TargetCharacteristic;

public class UserCharacteristic implements TargetCharacteristic {

    private final UserType characteristic;

    public UserCharacteristic(UserType characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public UserType getCharacteristic() {
        return characteristic;
    }
}
