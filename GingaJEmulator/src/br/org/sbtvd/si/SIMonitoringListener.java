package br.org.sbtvd.si;


import java.util.EventListener;

public interface SIMonitoringListener extends EventListener {
	
	/**
	 * 
	 * @param anEvent
	 */
	public void postMonitoringEvent(SIMonitoringEvent anEvent);	
}
