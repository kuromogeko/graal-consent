package de.datev.wjax.hello.consent.inbound;

import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.actors.Scope;
import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.consent.ConsentAggregate;
import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import de.datev.wjax.hello.consent.domain.consent.ConsentService;
import de.datev.wjax.hello.consent.domain.consent.GiveConsentCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
public class ConsentController {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }


    @PostMapping("/api/consents")
    public Mono<ConsentGivenEvent> createConsent(@RequestBody GiveConsentCommand command, @RequestHeader("X-User-Id") UUID userid) {
        return consentService.giveConsent(new Actor(
                new User(userid, new UserCharacteristic(UserType.DEFAULT))
                , List.of(), List.of(Scope.USER)), command);
    }

    @GetMapping("/api/consents")
    public Flux<ConsentAggregate> getConsents(@RequestHeader("X-User-Id") UUID userid){
        return consentService.getConsents(new Actor(
                new User(userid, new UserCharacteristic(UserType.DEFAULT))
                , List.of(), List.of(Scope.USER)));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Exception> handleDomainErrors(DomainException domainException) {
        var status = domainException.getType().equals(ErrorType.USER_ERROR) ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(new Exception(domainException.getMessage()));
    }

    record Exception(String info) {

    }


}
