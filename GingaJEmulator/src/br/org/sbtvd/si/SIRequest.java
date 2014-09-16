package br.org.sbtvd.si;

public class SIRequest {
	
	private static long Id = 0;
	private long requestId;
	
	/**
	 * 
	 */
	public SIRequest(){
		requestId = SIRequest.Id++;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAvailableInCache(){
		//TODO
		return false;
	}
	
	/**
	 *  
	 * @return
	 */
	public boolean cancelRequest(){
		//TODO
		return false;
	}

}
