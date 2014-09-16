package br.org.sbtvd.si;

public class SISuccessfulRetrieveEvent extends SIRetrievalEvent {

    /**
     *
     * @param appData
     * @param request
     * @param result
     */
    public SISuccessfulRetrieveEvent(Object appData, SIRequest request,
            SIIterator result) {
        super(appData, request);
    }

    public SIIterator getResult() {
        return null;
    }
}
