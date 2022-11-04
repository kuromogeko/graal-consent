(function (repository, event){
    var history = repository.load(event.getConsentId());
    if(null==history){
        var historyClass = Java.type('de.datev.wjax.hello.tracking.domain.ConsentHistory');
        history = new historyClass(event.getConsentId());
    }
    var HistoricalEventClass = Java.type('de.datev.wjax.hello.tracking.domain.HistoricalEvent');
    var happening = new HistoricalEventClass(""+Date.now(),`Consent was given by ${event.getUser().getId()} for subject ${event.getSubjectReference().getId()} with the purpose of ${event.getPurpose().getPurposeId()} in version ${event.getPurpose().getAgreedVersion().getValue()}`);

    history.getEvents().add(happening);
    repository.save(history);
})