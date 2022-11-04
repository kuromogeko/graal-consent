package de.datev.wjax.hello.tracking.domain;

import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import de.datev.wjax.hello.consent.domain.consent.ReferencedPurpose;
import de.datev.wjax.hello.consent.domain.consent.SubjectReference;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsentEventHandlerTest {
    @Test
    void savesAHistory() {
        ArgumentCaptor<ConsentHistory> captor = ArgumentCaptor.forClass(ConsentHistory.class);
        var uuid= UUID.randomUUID();
        var repository = Mockito.mock(TrackingRepository.class);
        Mockito.when(repository.load(uuid)).thenReturn(new ConsentHistory(uuid));
        var handler = new ConsentEventHandler(repository);
        var event = new ConsentGivenEvent(uuid, new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)),new User(UUID.randomUUID(), new UserCharacteristic(UserType.DEFAULT)),new SubjectReference(UUID.randomUUID()));

        handler.trackGivenConsent(event);
        Mockito.verify(repository,Mockito.times(1)).load(uuid);
        Mockito.verify(repository,Mockito.times(1)).save(captor.capture());
        var history = captor.getValue();
        assertEquals(history.getEvents().get(0).getEvent(), "Consent was given by "+event.getUser().getId()+" for subject "+event.getSubjectReference().getId()+" with the purpose of "+event.getPurpose().getPurposeId()+" in version "+event.getPurpose().getAgreedVersion().getValue()+"");
    }

    @Test
    void savesAHistoryWithNoObjectExistingBeforehand() {
        ArgumentCaptor<ConsentHistory> captor = ArgumentCaptor.forClass(ConsentHistory.class);
        var uuid= UUID.randomUUID();
        var repository = Mockito.mock(TrackingRepository.class);
        Mockito.when(repository.load(uuid)).thenReturn(new ConsentHistory(uuid));
        var handler = new ConsentEventHandler(repository);
        var event = new ConsentGivenEvent(uuid, new ReferencedPurpose(UUID.randomUUID(), new PurposeVersion(1)),new User(UUID.randomUUID(), new UserCharacteristic(UserType.DEFAULT)),new SubjectReference(UUID.randomUUID()));

        handler.trackGivenConsent(event);
        Mockito.verify(repository,Mockito.times(1)).load(uuid);
        Mockito.verify(repository,Mockito.times(1)).save(captor.capture());
        var history = captor.getValue();
        assertEquals(history.getEvents().get(0).getEvent(), "Consent was given by "+event.getUser().getId()+" for subject "+event.getSubjectReference().getId()+" with the purpose of "+event.getPurpose().getPurposeId()+" in version "+event.getPurpose().getAgreedVersion().getValue()+"");
    }
}