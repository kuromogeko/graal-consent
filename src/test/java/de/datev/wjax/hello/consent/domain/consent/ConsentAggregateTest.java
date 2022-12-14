package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ConsentAggregateTest {

    User user = new User(UUID.fromString("fb132970-e5e5-463f-8bdf-eda9c3e14357"), new UserCharacteristic(UserType.DEFAULT));

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"WITHDRAWN","NONE"})
    void canGiveConsent(Status status) {
        var publisherMock = Mockito.mock(DomainEventPublisher.class);
        ArgumentCaptor<ConsentGivenEvent> captor = ArgumentCaptor.forClass(ConsentGivenEvent.class);
        UUID consentId = UUID.randomUUID();
        SubjectReference subjectReference = new SubjectReference(UUID.randomUUID());
        PurposeVersion agreedVersion = new PurposeVersion(1);
        ReferencedPurpose purpose = new ReferencedPurpose(UUID.randomUUID(), agreedVersion);
        var consent = new ConsentAggregate(purpose, status, subjectReference, consentId, publisherMock);
        var event = consent.giveConsent(user);
        verify(publisherMock,times(1)).publish(captor.capture());
        //actually tests for same instance which is ok
        assertEquals(event, captor.getValue());
        assertEquals(consentId,event.consentId());
        assertEquals(purpose,event.purpose());
        assertEquals(subjectReference,event.subjectReference());
        assertEquals(user, event.user());
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"GIVEN","INVALID"})
    void canNotGiveConsentInIllegalState(Status status){
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), status, new SubjectReference(UUID.randomUUID()), UUID.randomUUID(), null);
        assertThrows(DomainException.class, () -> {
            consent.giveConsent(user);
        });
    }

    @Test
    void canWithdrawConsent() {
        var publisherMock = Mockito.mock(DomainEventPublisher.class);
        ArgumentCaptor<ConsentWithdrawnEvent> captor = ArgumentCaptor.forClass(ConsentWithdrawnEvent.class);
        UUID consentId = UUID.randomUUID();
        SubjectReference subjectReference = new SubjectReference(UUID.randomUUID());
        PurposeVersion agreedVersion = new PurposeVersion(1);
        ReferencedPurpose purpose = new ReferencedPurpose(UUID.randomUUID(), agreedVersion);
        var consent = new ConsentAggregate(purpose, Status.GIVEN, subjectReference, consentId, publisherMock);
        var event = consent.withdrawConsent(user);
        verify(publisherMock,times(1)).publish(captor.capture());
        //actually tests for same instance which is ok
        assertEquals(event, captor.getValue());
        assertEquals(consentId,event.getConsentId());
        assertEquals(purpose,event.getPurpose());
        assertEquals(subjectReference,event.getReference());
        assertEquals(user, event.getUser());
    }

    @ParameterizedTest
    @EnumSource(value = Status.class, names = {"WITHDRAWN", "INVALID"})
    void consentNotWithdrawnIfNotGiven(Status status) {
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), status, new SubjectReference(UUID.randomUUID()), UUID.randomUUID(), null);
        assertThrows(DomainException.class, () -> {
            consent.withdrawConsent(user);
        });
    }

    @Test
    void ignoreOnSmallerNewVersion() {
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), Status.GIVEN, new SubjectReference(UUID.randomUUID()), UUID.randomUUID(), null);
        consent.invalidateForSmallerVersions(new PurposeVersion(0));
        assertEquals(Status.GIVEN, consent.getStatus());
    }

    @Test
    void ignoreOnEvenNewVersion() {
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), Status.GIVEN, new SubjectReference(UUID.randomUUID()), UUID.randomUUID(), null);
        consent.invalidateForSmallerVersions(new PurposeVersion(1));
        assertEquals(Status.GIVEN, consent.getStatus());
    }

    @Test
    void invalidateOnGreaterNewVersion() {
        var publisherMock = Mockito.mock(DomainEventPublisher.class);
        ArgumentCaptor<ConsentInvalidatedEvent> captor = ArgumentCaptor.forClass(ConsentInvalidatedEvent.class);
        UUID consentId = UUID.randomUUID();
        ReferencedPurpose purpose = new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1));
        SubjectReference subjectReference = new SubjectReference(UUID.randomUUID());
        var consent = new ConsentAggregate(purpose, Status.GIVEN, subjectReference, consentId, publisherMock);
        consent.invalidateForSmallerVersions(new PurposeVersion(3));
        assertEquals(Status.INVALID, consent.getStatus());
        verify(publisherMock, times(1)).publish(captor.capture());
        var capped = captor.getValue();
        assertEquals(consentId, capped.consentId());
        assertEquals(purpose, capped.referencedPurpose());
        assertEquals(subjectReference, capped.subjectReference());
    }

}