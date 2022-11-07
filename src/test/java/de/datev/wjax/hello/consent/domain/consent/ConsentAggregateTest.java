package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import de.datev.wjax.hello.tracking.domain.ConsentHistory;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ConsentAggregateTest {
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
        assertEquals(consentId,capped.getConsentId());
        assertEquals(purpose, capped.getReferencedPurpose());
        assertEquals(subjectReference, capped.getSubjectReference());
    }

}