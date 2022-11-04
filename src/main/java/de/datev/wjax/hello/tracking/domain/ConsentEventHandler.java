package de.datev.wjax.hello.tracking.domain;


import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ConsentEventHandler {

    private final TrackingRepository repository;

    public ConsentEventHandler(TrackingRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void trackGivenConsent(ConsentGivenEvent event) {
        try {
            Context context = Context.newBuilder("js").allowHostAccess(HostAccess.ALL).allowHostClassLookup(s -> true).build();
            Value eventHandler = context.eval(Source.newBuilder("js", new File(getClass().getClassLoader().getResource("handleGivenEvent.js").toURI())).build());
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
