package de.datev.wjax.hello.consent.domain.consent;

import de.datev.wjax.hello.consent.domain.DomainEvent;

import java.util.UUID;

public record ConsentInvalidatedEvent(UUID consentId, ReferencedPurpose referencedPurpose,
                                      SubjectReference subjectReference) implements DomainEvent {

}

