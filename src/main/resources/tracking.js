function handleGivenEvent(repository, event) {
    var history = loadHistoryByIdOrCreate(repository, event.consentId());
    var HistoricalEventClass = Java.type('de.datev.wjax.hello.tracking.domain.HistoricalEvent');
    var happening = new HistoricalEventClass("" + Date.now(), `Consent was given by ${event.user().getId()} for subject ${event.subjectReference().getId()} with the purpose of ${event.purpose().getPurposeId()} in version ${event.purpose().getAgreedVersion().getValue()}`);

    history.addEventToHistory(happening);
    repository.save(history);
}

function handleWithdrawnEvent(repository, event) {
    var history = loadHistoryByIdOrCreate(repository, event.getConsentId());
    var HistoricalEventClass = Java.type('de.datev.wjax.hello.tracking.domain.HistoricalEvent');
    var happening = new HistoricalEventClass("" + Date.now(), `Consent was withdrawn by ${event.getUser().getId()} for subject ${event.getReference().getId()} with the purpose of ${event.getPurpose().getPurposeId()} in version ${event.getPurpose().getAgreedVersion().getValue()}`);

    history.addEventToHistory(happening);
    repository.save(history);
}

function handleInvalidatedEvent(repository, event) {
    var history = loadHistoryByIdOrCreate(repository, event.consentId());
    var HistoricalEventClass = Java.type('de.datev.wjax.hello.tracking.domain.HistoricalEvent');
    var happening = new HistoricalEventClass("" + Date.now(), `Consent was invalidated for subject ${event.subjectReference().getId()} with the purpose of ${event.referencedPurpose().getPurposeId()} in version ${event.referencedPurpose().getAgreedVersion().getValue()} due to a new version`);

    history.addEventToHistory(happening);
    repository.save(history);
}


function loadHistoryByIdOrCreate(repository, id) {
    var historyOpt = repository.load(id);
    if (null == historyOpt) {
        var historyClass = Java.type('de.datev.wjax.hello.tracking.domain.ConsentHistory');
        return new historyClass(id);
    }
    return historyOpt;
}