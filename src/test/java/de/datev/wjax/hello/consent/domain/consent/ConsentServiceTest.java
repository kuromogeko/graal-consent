package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEventPublisher;
import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("ConstantConditions")
@SpringBootTest
class ConsentServiceTest {

    @Autowired
    ConsentService consentService;
    @Autowired
    ConsentRepository consentRepository;
    @Autowired
    DomainEventPublisher domainEventPublisher;
    ReferencedPurpose referencedPurpose = new ReferencedPurpose(UUID.fromString("facade00-0000-4000-a000-000000000000"), new PurposeVersion(1));
    Actor actor = new Actor(new User(UUID.fromString("c0c0a000-0000-4000-a000-000000000000"), new UserCharacteristic(UserType.DEFAULT)), new ArrayList<>(), new ArrayList<>());
    private final SubjectReference subjectReference = new SubjectReference(UUID.fromString("c0c0a000-0000-4000-a000-000000000000"));
    @Test
    void consentCanBeCreated() {

        GiveConsentCommand command = new GiveConsentCommand(
                referencedPurpose,
                subjectReference);
        var event = consentService.giveConsent(actor, command).block();
        var resultingWriteAggregate = consentRepository.getById(event.getConsentId()).block();
        assertEquals(Status.GIVEN, resultingWriteAggregate.getStatus());
    }

    @Test
    void consentCanBeWithdrawn() {
        UUID consentId = UUID.fromString("f100ded0-0000-4000-a000-000000000000");
        ConsentAggregate aggregate = new ConsentAggregate(referencedPurpose, Status.GIVEN, subjectReference, consentId,domainEventPublisher);
        consentRepository.save(aggregate).block();
        Actor actor = new Actor(new User(UUID.fromString("c0c0a000-0000-4000-a000-000000000000"), new UserCharacteristic(UserType.DEFAULT)), new ArrayList<>(), new ArrayList<>());
        WithdrawConsentCommand command = new WithdrawConsentCommand(consentId);
        var event = consentService.withdrawConsent(actor,command).block();
        var result = consentRepository.getById(event.getConsentId()).block();
        assertEquals(Status.WITHDRAWN, result.getStatus());
    }
}