package de.datev.wjax.hello.tracking.domain;


import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import org.graalvm.polyglot.Context;
import org.springframework.context.event.EventListener;

public class ConsentEventHandler {
    @EventListener
    public void trackGivenConsent(ConsentGivenEvent event){
        Context.create("js").eval("js", "console.log('hi')");
    }
}
