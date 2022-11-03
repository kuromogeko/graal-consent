package de.datev.wjax.hello.consent.inbound;

import de.datev.wjax.hello.consent.inbound.transfer.Hello;
import org.graalvm.polyglot.Context;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ConsentController {

    @GetMapping("/api/v1/hello")
    public Mono<Hello> sayHi() {
        Context.create("js").eval("js","console.log('Have some javascript')");
        return Mono.just(new Hello("Hello World"));
    }


}
