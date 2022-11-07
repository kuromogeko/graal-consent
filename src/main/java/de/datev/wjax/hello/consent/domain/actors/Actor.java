package de.datev.wjax.hello.consent.domain.actors;

import de.datev.wjax.hello.consent.domain.actors.organisation.Organisation;
import de.datev.wjax.hello.consent.domain.actors.user.User;
import de.datev.wjax.hello.consent.domain.consent.SubjectReference;

import java.util.List;
import java.util.Optional;

public class Actor {
    private final User user;
    private final List<Organisation> organisations;
    private List<Scope> scopes;

    public Actor(User user, List<Organisation> organisations, List<Scope> scopes) {
        this.user = user;
        this.organisations = organisations;
        this.scopes = scopes;
    }


    public User getUser() {
        return user;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public Optional<Subject> getSubjectByReference(SubjectReference reference) {
        if (this.user.getId().equals(reference.getId())) {
            return Optional.of(this.user);
        }
        return this.organisations.stream().filter(organisation -> organisation.getId().equals(reference.getId())).findFirst()
                //java does not recognize the implements keyword unless we remap to the same type, so we do :(
                .map(organisation -> organisation);

    }
}
