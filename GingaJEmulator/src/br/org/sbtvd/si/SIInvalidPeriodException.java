package br.org.sbtvd.si;

public class SIInvalidPeriodException extends SIException{
	
	/**
	 * 
	 *
	 */
	public SIInvalidPeriodException(){
	}
	
	/**
	 * 
	 * @param reason
	 */
	public SIInvalidPeriodException(String reason){
		
		super (reason);
	}

}