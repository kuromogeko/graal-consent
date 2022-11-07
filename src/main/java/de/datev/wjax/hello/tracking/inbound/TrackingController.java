package de.datev.wjax.hello.tracking.inbound;

import de.datev.wjax.hello.tracking.domain.ConsentHistory;
import de.datev.wjax.hello.tracking.domain.TrackingRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class TrackingController {

    private final TrackingRepository repository;

    public TrackingController(TrackingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/history")
    public Mono<ConsentHistory> getHistory(@RequestParam(required = true,name = "consentId") UUID consentId){
        return Mono.justOrEmpty(repository.load(consentId));
    }
}
