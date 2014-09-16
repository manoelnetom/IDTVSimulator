package br.org.sbtvd.si;

import java.util.EventObject;


/** 
 * A classe SIRetrievalEvent é a classe básica para o evento de conclusão do 
 * pedido da aquisição do SI. 
 * Somente um evento é retornado para um pedido da aquisição do SI.
 * 
 * @author jonatas
 *
 */
public class SIRetrievalEvent extends EventObject{
	
	
	private Object appData;
	
	/**
	 * 
	 * @param appData
	 * @param request
	 */
	public SIRetrievalEvent(Object appData, SIRequest request){
		//TODO
		super(appData);
		this.appData = appData;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getAppData(){
		
		return appData;
	}
	
	/**
	 * 
	 */
	public Object getSource(){
		//TODO
		return null;
	}

}
