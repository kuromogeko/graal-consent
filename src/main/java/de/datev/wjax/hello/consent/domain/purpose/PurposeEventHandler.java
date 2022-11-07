package de.datev.wjax.hello.consent.domain.purpose;

import de.datev.wjax.hello.consent.domain.consent.ConsentRepository;
import de.datev.wjax.hello.purpose.domain.PurposeCreatedEvent;
import de.datev.wjax.hello.purpose.domain.PurposeReleasedEvent;
import de.datev.wjax.hello.purpose.domain.PurposeVersionUpdatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PurposeEventHandler {
    private final ConsentPurposeRepository purposeRepository;
    private final ConsentRepository repository;

    public PurposeEventHandler(ConsentPurposeRepository purposeRepository, ConsentRepository repository) {
        this.purposeRepository = purposeRepository;
        this.repository = repository;
    }

    @EventListener
    public void handlePurposeCreation(PurposeReleasedEvent event) {
        var purpose = new Purpose(event.getId(), event.getPurposeVersion(), event.getText(), event.getViability());
        this.purposeRepository.save(purpose).subscribe().dispose();
    }

    @EventListener
    public void handlePurposeUpdate(PurposeVersionUpdatedEvent event) {
        purposeRepository.getPurpose(event.getId()).flatMap(purpose -> {
            if (!purpose.updateVersion(event.getPurposeVersion(), event.getUpdatedText())) {
                return Mono.empty();
            }
            return purposeRepository.save(purpose);
            //TODO Flatmap doesnt trigger
        }).then(this.repository.getByPurpose(event.getId()).flatMap(consentAggregate -> {
            consentAggregate.invalidateForSmallerVersions(event.getPurposeVersion());
            return this.repository.save(consentAggregate);
        }).then()).subscribe().dispose();

    }
}
