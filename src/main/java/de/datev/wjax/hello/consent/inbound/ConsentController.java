package de.datev.wjax.hello.consent.inbound;

import de.datev.wjax.hello.consent.inbound.transfer.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConsentController {

    @GetMapping("/api/v1/hello")
    public Mono<Hello> sayHi(){
        return Mono.just(new Hello("Hello World"));
    }
}
