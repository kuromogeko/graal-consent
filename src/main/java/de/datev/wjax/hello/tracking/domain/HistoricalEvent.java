package de.datev.wjax.hello.tracking.domain;

import java.util.Objects;

public class HistoricalEvent {
    private final String timestamp;
    private final String event;


    public HistoricalEvent(String timestamp, String event) {
        this.timestamp = timestamp;
        this.event = event;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricalEvent that = (HistoricalEvent) o;
        return Objects.equals(getTimestamp(), that.getTimestamp()) && Objects.equals(getEvent(), that.getEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getEvent());
    }
}
