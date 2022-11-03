package de.datev.wjax.hello.tracking.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsentEventHandlerTest {
    @Test
    void runsJS() {
        var handler = new ConsentEventHandler();
        handler.trackGivenConsent(null);
    }
}