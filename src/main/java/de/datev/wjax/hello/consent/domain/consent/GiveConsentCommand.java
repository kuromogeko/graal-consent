package de.datev.wjax.hello.consent.domain.consent;

public class GiveConsentCommand {
    private final ReferencedPurpose referencedPurpose;
    private final SubjectReference subjectReference;


    public GiveConsentCommand(ReferencedPurpose referencedPurpose, SubjectReference subjectReference) {
        this.referencedPurpose = referencedPurpose;
        this.subjectReference = subjectReference;
    }

    public ReferencedPurpose getReferencedPurpose() {
        return referencedPurpose;
    }


    public SubjectReference getSubjectReference() {
        return subjectReference;
    }
}
