package de.datev.wjax.hello.consent.domain.actors;

import de.datev.wjax.hello.consent.domain.actors.organisation.Organisation;
import de.datev.wjax.hello.consent.domain.actors.organisation.OrganisationCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.organisation.OrganisationType;
import de.datev.wjax.hello.consent.domain.actors.organisation.UserPermissionLevel;
import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.consent.SubjectReference;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    @Test
    void getsSubjectByReferenceOfUser() {
        User user = new User(UUID.fromString("fb132970-e5e5-463f-8bdf-eda9c3e14357"), new UserCharacteristic(UserType.DEFAULT));
        Organisation e1 = new Organisation(UUID.fromString("6079a5e6-9fb0-4e44-8c23-1d04e9b904b7"), new OrganisationCharacteristic(OrganisationType.DEFAULT), UserPermissionLevel.MEMBER);
        Organisation e2 = new Organisation(UUID.fromString("c0c0a000-0000-4000-a000-000000000000"), new OrganisationCharacteristic(OrganisationType.DEFAULT), UserPermissionLevel.MEMBER);
        var actor = new Actor(user,
                List.of(e1,
                        e2), List.of(Scope.USER));
        var subject = actor.getSubjectByReference(new SubjectReference(UUID.fromString("fb132970-e5e5-463f-8bdf-eda9c3e14357")));
        assertTrue(subject.isPresent());
        assertEquals(user, subject.get());
    }

    @Test
    void getsSubjectByReferenceOfOrg() {
        User user = new User(UUID.fromString("fb132970-e5e5-463f-8bdf-eda9c3e14357"), new UserCharacteristic(UserType.DEFAULT));
        Organisation e1 = new Organisation(UUID.fromString("6079a5e6-9fb0-4e44-8c23-1d04e9b904b7"), new OrganisationCharacteristic(OrganisationType.DEFAULT), UserPermissionLevel.MEMBER);
        Organisation e2 = new Organisation(UUID.fromString("c0c0a000-0000-4000-a000-000000000000"), new OrganisationCharacteristic(OrganisationType.DEFAULT), UserPermissionLevel.MEMBER);
        var actor = new Actor(user,
                List.of(e1,
                        e2), List.of(Scope.USER));
        var subject = actor.getSubjectByReference(new SubjectReference(UUID.fromString("c0c0a000-0000-4000-a000-000000000000")));
        assertTrue(subject.isPresent());
        assertEquals(e2, subject.get());
    }
    @Test
    void getsNoSubjectByReferenceIfMissing() {
        User user = new User(UUID.fromString("fb132970-e5e5-463f-8bdf-eda9c3e14357"), new UserCharacteristic(UserType.DEFAULT));
        Organisation e1 = new Organisation(UUID.fromString("6079a5e6-9fb0-4e44-8c23-1d04e9b904b7"), new OrganisationCharacteristic(OrganisationType.DEFAULT), UserPermissionLevel.MEMBER);
        Organisation e2 = new Organisation(UUID.fromString("c0c0a000-0000-4000-a000-000000000000"), new OrganisationCharacteristic(OrganisationType.DEFAULT), UserPermissionLevel.MEMBER);
        var actor = new Actor(user,
                List.of(e1,
                        e2), List.of(Scope.USER));
        var subject = actor.getSubjectByReference(new SubjectReference(UUID.fromString("ad0be000-0000-4000-a000-000000000000")));
        assertFalse(subject.isPresent());
    }

}