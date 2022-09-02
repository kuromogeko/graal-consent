package de.datev.wjax.hello.consent.domain.actors.organisation;

import de.datev.wjax.hello.consent.domain.actors.Subject;
import de.datev.wjax.hello.consent.domain.purpose.SubjectType;
import de.datev.wjax.hello.consent.domain.purpose.TargetCharacteristic;

import java.util.UUID;

public class Organisation implements Subject {
    private final UUID id;
    private final OrganisationCharacteristic organisationCharacteristic;
    private final UserPermissionLevel permissionLevel;

    public UUID getId() {
        return id;
    }


    public OrganisationCharacteristic getOrganisationCharacteristic() {
        return organisationCharacteristic;
    }

    public UserPermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public Organisation(UUID id, OrganisationCharacteristic organisationCharacteristic, UserPermissionLevel permissionLevel) {
        this.id = id;
        this.organisationCharacteristic = organisationCharacteristic;
        this.permissionLevel = permissionLevel;
    }

    @Override
    public boolean isOfType(SubjectType type) {
        return SubjectType.ORGANIZATION.equals(type);
    }

    @Override
    public boolean hasCharacteristic(TargetCharacteristic characteristic) {
        return this.organisationCharacteristic.isOfType(characteristic);
    }
}
