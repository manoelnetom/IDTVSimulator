package br.org.sbtvd.si;

public class SIIllegalArgumentException extends SIException{
	
	/**
	 * 
	 *
	 */
	public SIIllegalArgumentException(){
	}
	
	/**
	 * 
	 * @param reason
	 */
	public SIIllegalArgumentException(String reason){
		
		super(reason);
		
	}

}
