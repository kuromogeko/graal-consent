package de.datev.wjax.hello.consent.domain.consent;

public class GiveConsentCommand {
    private final ReferencedPurpose referencedPurpose;
    private final Status status = Status.GIVEN;
    private final SubjectReference subjectReference;


    public GiveConsentCommand(ReferencedPurpose referencedPurpose, SubjectReference subjectReference) {
        this.referencedPurpose = referencedPurpose;
        this.subjectReference = subjectReference;
    }

    public ReferencedPurpose getReferencedPurpose() {
        return referencedPurpose;
    }

    public Status getStatus() {
        return status;
    }

    public SubjectReference getSubjectReference() {
        return subjectReference;
    }
}
