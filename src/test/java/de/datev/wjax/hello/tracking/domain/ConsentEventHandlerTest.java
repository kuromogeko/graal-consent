package de.datev.wjax.hello.tracking.domain;

import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.consent.*;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsentEventHandlerTest {
    @Test
    void savesAGivenHistory() {
        ArgumentCaptor<ConsentHistory> captor = ArgumentCaptor.forClass(ConsentHistory.class);
        var uuid = UUID.randomUUID();
        var repository = Mockito.mock(TrackingRepository.class);
//        Mockito.when(repository.load(uuid)).thenReturn(Optional.of(new ConsentHistory(uuid)));
        Mockito.when(repository.load(uuid)).thenReturn(new ConsentHistory(uuid));
        var handler = new ConsentEventHandler(repository);
        var event = new ConsentGivenEvent(uuid, new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), new User(UUID.randomUUID(), new UserCharacteristic(UserType.DEFAULT)), new SubjectReference(UUID.randomUUID()));

        handler.trackGivenConsent(event);
        Mockito.verify(repository, Mockito.times(1)).load(uuid);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
        var history = captor.getValue();
        assertEquals(history.getEvents().get(0).getEvent(), "Consent was given by " + event.user().getId() + " for subject " + event.subjectReference().getId() + " with the purpose of " + event.purpose().getPurposeId() + " in version " + event.purpose().getAgreedVersion().getValue() + "");
    }

    @Test
    void savesAGivenHistoryWithNoObjectExistingBeforehand() {
        ArgumentCaptor<ConsentHistory> captor = ArgumentCaptor.forClass(ConsentHistory.class);
        var uuid = UUID.randomUUID();
        var repository = Mockito.mock(TrackingRepository.class);
        Mockito.when(repository.load(uuid)).thenReturn(null);
        var handler = new ConsentEventHandler(repository);
        var event = new ConsentGivenEvent(uuid, new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), new User(UUID.randomUUID(), new UserCharacteristic(UserType.DEFAULT)), new SubjectReference(UUID.randomUUID()));

        handler.trackGivenConsent(event);
        Mockito.verify(repository, Mockito.times(1)).load(uuid);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
        var history = captor.getValue();
        assertEquals(history.getEvents().get(0).getEvent(), "Consent was given by " + event.user().getId() + " for subject " + event.subjectReference().getId() + " with the purpose of " + event.purpose().getPurposeId() + " in version " + event.purpose().getAgreedVersion().getValue() + "");
    }

    @Test
    void savesAWithdrawnHistory() {
        ArgumentCaptor<ConsentHistory> captor = ArgumentCaptor.forClass(ConsentHistory.class);
        var uuid = UUID.randomUUID();
        var repository = Mockito.mock(TrackingRepository.class);
        Mockito.when(repository.load(uuid)).thenReturn((new ConsentHistory(uuid)));
        var handler = new ConsentEventHandler(repository);
        var event = new ConsentWithdrawnEvent(uuid, new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), new User(UUID.randomUUID(), new UserCharacteristic(UserType.DEFAULT)), new SubjectReference(UUID.randomUUID()));

        handler.trackWithdrawnConsent(event);
        Mockito.verify(repository, Mockito.times(1)).load(uuid);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
        var history = captor.getValue();
        assertEquals(history.getEvents().get(0).getEvent(), "Consent was withdrawn by " + event.getUser().getId() + " for subject " + event.getReference().getId() + " with the purpose of " + event.getPurpose().getPurposeId() + " in version " + event.getPurpose().getAgreedVersion().getValue() + "");
    }

    @Test
    void savesAnInvalidationHistory() {
        ArgumentCaptor<ConsentHistory> captor = ArgumentCaptor.forClass(ConsentHistory.class);
        var uuid = UUID.randomUUID();
        var repository = Mockito.mock(TrackingRepository.class);
        Mockito.when(repository.load(uuid)).thenReturn((new ConsentHistory(uuid)));
        var handler = new ConsentEventHandler(repository);
        var event = new ConsentInvalidatedEvent(uuid, new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)), new SubjectReference(UUID.randomUUID()));

        handler.trackInvalidatedConsent(event);
        Mockito.verify(repository, Mockito.times(1)).load(uuid);
        Mockito.verify(repository, Mockito.times(1)).save(captor.capture());
        var history = captor.getValue();
        assertEquals(history.getEvents().get(0).getEvent(), "Consent was invalidated for subject " + event.subjectReference().getId() + " with the purpose of " + event.referencedPurpose().getPurposeId() + " in version " + event.referencedPurpose().getAgreedVersion().getValue() + " due to a new version");
    }
}