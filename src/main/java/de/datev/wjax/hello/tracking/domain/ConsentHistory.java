package de.datev.wjax.hello.tracking.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ConsentHistory {
    private final UUID consentId;
    private List<HistoricalEvent> events;


    public ConsentHistory(UUID consentId) {
        this.consentId = consentId;
        this.events = new ArrayList<>();
    }

    public ConsentHistory(UUID consentId, List<HistoricalEvent> events) {
        this.consentId = consentId;
        this.events = events;
    }
    public UUID getConsentId() {
        return consentId;
    }

    public List<HistoricalEvent> getEvents() {
        return events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsentHistory that = (ConsentHistory) o;
        return Objects.equals(getConsentId(), that.getConsentId()) && Objects.equals(getEvents(), that.getEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConsentId(), getEvents());
    }
}
