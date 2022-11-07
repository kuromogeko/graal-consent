package de.datev.wjax.hello.consent.domain.consent;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;
import java.util.UUID;

public class SubjectReference {
    private final UUID id;

    @JsonCreator
    public SubjectReference(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectReference that = (SubjectReference) o;
        return Objects.equals(getId(), that.getId());
    }

}
