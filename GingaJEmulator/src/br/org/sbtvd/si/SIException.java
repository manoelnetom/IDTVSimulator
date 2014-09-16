package br.org.sbtvd.si;

import java.lang.Exception;

public abstract class SIException extends Exception{
	
	/**
	 * 
	 *
	 */
	public SIException(){
	}
	
	/**
	 * 
	 * @param reason
	 */
	public SIException(String reason){
		
		super(reason);		
		
	}

}