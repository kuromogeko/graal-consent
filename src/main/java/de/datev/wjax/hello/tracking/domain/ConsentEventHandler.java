package de.datev.wjax.hello.tracking.domain;


import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import de.datev.wjax.hello.consent.domain.consent.ConsentInvalidatedEvent;
import de.datev.wjax.hello.consent.domain.consent.ConsentWithdrawnEvent;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.springframework.context.event.EventListener;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
@ResourceHint(patterns = "tracking.js")
public class ConsentEventHandler {

    private final TrackingRepository repository;

    public ConsentEventHandler(TrackingRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void trackGivenConsent(ConsentGivenEvent event) {
        try {
            Context context = Context
                    .newBuilder("js")
                    .allowHostAccess(HostAccess.ALL)
                    .allowHostClassLookup(s -> true)
                    .allowAllAccess(true)
                    .build();
            var stream = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("tracking.js");

            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (stream, StandardCharsets.UTF_8))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            System.out.println(textBuilder.toString());
            context.eval(Source.create("js", textBuilder.toString()));

            var eventHandler = context.getBindings("js").getMember("handleGivenEvent");
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void trackWithdrawnConsent(ConsentWithdrawnEvent event) {
        try {
            Context context = Context.newBuilder("js").allowHostAccess(HostAccess.ALL).allowHostClassLookup(s -> true).build();
            context.eval(Source.newBuilder("js",
                            new File(getClass().getClassLoader().getResource("tracking.js").toURI()))
                    .build());

            var eventHandler = context.getBindings("js").getMember("handleWithdrawnEvent");
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void trackInvalidatedConsent(ConsentInvalidatedEvent event) {
        try {
            Context context = Context.newBuilder("js").allowHostAccess(HostAccess.ALL).allowHostClassLookup(s -> true).build();
            context.eval(Source.newBuilder("js",
                            new File(getClass().getClassLoader().getResource("tracking.js").toURI()))
                    .build());

            var eventHandler = context.getBindings("js").getMember("handleInvalidatedEvent");
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
