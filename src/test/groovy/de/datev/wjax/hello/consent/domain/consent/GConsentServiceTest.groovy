package de.datev.wjax.hello.consent.domain.consent

import de.datev.wjax.hello.consent.domain.DomainEventPublisher
import de.datev.wjax.hello.consent.domain.actors.Actor
import de.datev.wjax.hello.consent.domain.actors.user.User
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic
import de.datev.wjax.hello.consent.domain.actors.user.UserType
import de.datev.wjax.hello.consent.domain.purpose.*
import reactor.core.publisher.Mono
import spock.lang.Specification

class GConsentServiceTest extends Specification {
    ConsentRepository consentRepository = Mock(ConsentRepository)
    DomainEventPublisher domainEventPublisher = Mock(DomainEventPublisher)
    PurposeRepository purposeRepository = Mock(PurposeRepository)

    ConsentFactory factory = new ConsentFactory(domainEventPublisher, consentRepository)
    ConsentService service = new ConsentService(factory, purposeRepository, consentRepository)
    def purposeId = UUID.fromString("facade00-0000-4000-a000-000000000000")
    ReferencedPurpose referencedPurpose = new ReferencedPurpose(purposeId, new PurposeVersion(1));
    def subjectId  = UUID.fromString("c0c0a000-0000-4000-a000-000000000000")
    Actor actor = new Actor(new User(subjectId, new UserCharacteristic(UserType.DEFAULT)), new ArrayList<>(), new ArrayList<>());
    private final SubjectReference subjectReference = new SubjectReference(subjectId);

    def "service creates consent and publishes event accordingly"() {
        given: "A command"
        GiveConsentCommand command = new GiveConsentCommand(
                referencedPurpose,
                subjectReference)
        and: "The referenced purpose exists"
        1 * purposeRepository.getPurpose(purposeId) >> Mono.just(new Purpose(purposeId,
                new PurposeVersion(1),
                "This is a purpose",
                new Viability(SubjectType.USER, new UserCharacteristic(UserType.DEFAULT))))
        and: "no consent exists yet"
        1 * consentRepository.getBySubjectAndPurposeRef(subjectId, referencedPurpose) >> Mono.empty()

        when: "The command is given"
        def event = service.giveConsent(actor, command).block();

        then: "It was executed, saved and published about"
        1 * domainEventPublisher.publish(_) >> {
            ConsentGivenEvent arg ->
                assert arg.purpose == referencedPurpose
                assert arg.subjectReference == subjectReference
        }

        and: "The consent was saved"
        1 * consentRepository.save(_ as ConsentAggregate) >> {
            ConsentAggregate arg ->
                assert arg.getStatus() == Status.GIVEN
                return Mono.just(arg)
        }

    }
}
