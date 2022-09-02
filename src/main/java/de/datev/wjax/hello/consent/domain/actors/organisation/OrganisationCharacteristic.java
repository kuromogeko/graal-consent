package de.datev.wjax.hello.consent.domain.actors.organisation;

import de.datev.wjax.hello.consent.domain.purpose.TargetCharacteristic;

public class OrganisationCharacteristic implements TargetCharacteristic {

    private final OrganisationType characteristic;

    public OrganisationCharacteristic(OrganisationType characteristic) {
        this.characteristic = characteristic;
    }

    @Override
    public OrganisationType getCharacteristic() {
        return characteristic;
    }
}
