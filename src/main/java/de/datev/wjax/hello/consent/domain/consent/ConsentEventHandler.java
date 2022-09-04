package de.datev.wjax.hello.consent.domain.consent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConsentEventHandler {
    private final ConsentRepository repository;

    public ConsentEventHandler(ConsentRepository repository) {
        this.repository = repository;
    }

    //TODO This would probably be way better if it could work exclusively on an Event list and not a repo state
    // So consider changing it up
    // Just gotta find out how to fix List<T> maybe?
    @EventListener(classes = {ConsentGivenEvent.class})
    public void consentGivenEventListener(ConsentGivenEvent consentGivenEvent) {
        this.repository.getById(consentGivenEvent.getConsentId())
                .map(consentAggregate -> consentAggregate.applyGivenConsent(consentGivenEvent))
                .flatMap(repository::save)
                .subscribe()
                .dispose();
    }
}
