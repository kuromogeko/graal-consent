package de.datev.wjax.hello.tracking.domain;


import de.datev.wjax.hello.consent.domain.consent.ConsentGivenEvent;
import de.datev.wjax.hello.consent.domain.consent.ConsentInvalidatedEvent;
import de.datev.wjax.hello.consent.domain.consent.ConsentWithdrawnEvent;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Component
public class ConsentEventHandler {

    private final TrackingRepository repository;
    private final Context context;

    public ConsentEventHandler(TrackingRepository repository) {
        this.repository = repository;
        this.context = setupHandlerContext();
    }

    private Context setupHandlerContext() {

        Context context = Context
                .newBuilder("js")
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        context.eval(Source.create("js", textBuilder.toString()));
        return context;
    }

    @EventListener
    public void trackGivenConsent(ConsentGivenEvent event) {
        try {
            var eventHandler = context.getBindings("js").getMember("handleGivenEvent");
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void trackWithdrawnConsent(ConsentWithdrawnEvent event) {
        try {
            var eventHandler = context.getBindings("js").getMember("handleWithdrawnEvent");
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener
    public void trackInvalidatedConsent(ConsentInvalidatedEvent event) {
        try {
            var eventHandler = context.getBindings("js").getMember("handleInvalidatedEvent");
            eventHandler.execute(repository, event);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
