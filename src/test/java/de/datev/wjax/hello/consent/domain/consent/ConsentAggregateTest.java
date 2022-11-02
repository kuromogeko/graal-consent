package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConsentAggregateTest {
    @Test
    void invalidateOnGreaterNewVersion() {
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)),Status.GIVEN,new SubjectReference(UUID.randomUUID()),UUID.randomUUID(),null);
        consent.invalidateForSmallerVersions(new PurposeVersion(2));
        assertEquals(Status.INVALID, consent.getStatus());
    }
    @Test
    void ignoreOnEvenNewVersion() {
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)),Status.GIVEN,new SubjectReference(UUID.randomUUID()),UUID.randomUUID(),null);
        consent.invalidateForSmallerVersions(new PurposeVersion(1));
        assertEquals(Status.GIVEN, consent.getStatus());
    }

    @Test
    void ignoreOnSmallerNewVersion() {
        var consent = new ConsentAggregate(new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)),Status.GIVEN,new SubjectReference(UUID.randomUUID()),UUID.randomUUID(),null);
        consent.invalidateForSmallerVersions(new PurposeVersion(0));
        assertEquals(Status.GIVEN, consent.getStatus());
    }
}