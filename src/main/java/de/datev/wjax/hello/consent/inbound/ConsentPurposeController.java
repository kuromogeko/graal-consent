package de.datev.wjax.hello.consent.inbound;

import de.datev.wjax.hello.consent.domain.purpose.ConsentPurposeRepository;
import de.datev.wjax.hello.consent.domain.purpose.Purpose;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ConsentPurposeController {

    private final ConsentPurposeRepository purposeRepository;

    public ConsentPurposeController(ConsentPurposeRepository purposeRepository) {
        this.purposeRepository = purposeRepository;
    }

    @GetMapping("/api/purposes")
    public Flux<Purpose> getPurposes(){
        return purposeRepository.all();
    }
}
