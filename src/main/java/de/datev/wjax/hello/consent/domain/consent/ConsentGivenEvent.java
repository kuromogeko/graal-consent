package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;
import de.datev.wjax.hello.consent.domain.actors.user.User;

import java.util.UUID;

public record ConsentGivenEvent(UUID consentId, ReferencedPurpose purpose, User user,
                                SubjectReference subjectReference) implements DomainEvent {
}
