package br.org.sbtvd.si;


import java.util.EventListener;

public interface SIRetrievalListener extends EventListener {
	/**
	 * 
	 * @param event
	 */
	public void postRetrievalEvent(SIRetrievalEvent event);

}
