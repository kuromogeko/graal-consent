package de.datev.wjax.hello.consent.inbound;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.datev.wjax.hello.consent.domain.DomainException;
import de.datev.wjax.hello.consent.domain.ErrorType;
import de.datev.wjax.hello.consent.domain.actors.Actor;
import de.datev.wjax.hello.consent.domain.actors.Scope;
import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import de.datev.wjax.hello.consent.domain.consent.ConsentService;
import de.datev.wjax.hello.consent.domain.consent.GiveConsentCommand;
import de.datev.wjax.hello.consent.inbound.transfer.Hello;
import org.graalvm.polyglot.Context;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
public class ConsentController {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @GetMapping("/api/hello")
    public Mono<Hello> sayHi() {
        Context.create("js").eval("js", "console.log('Have some javascript')");
        return Mono.just(new Hello("Hello World"));
    }

    @PostMapping("/api/consents")
    public Mono<ConsentGivenEvent> createConsent(@RequestBody GiveConsentCommand command, @RequestHeader("X-User-Id") UUID userid) {
        return consentService.giveConsent(new Actor(
                new User(userid, new UserCharacteristic(UserType.DEFAULT))
                , List.of(), List.of(Scope.USER)), command);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Exception> handleDomainErrors(DomainException domainException) {
        var status = domainException.getType().equals(ErrorType.USER_ERROR) ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(new Exception(domainException.getMessage()));
    }

    record Exception(String info){

    }


}
