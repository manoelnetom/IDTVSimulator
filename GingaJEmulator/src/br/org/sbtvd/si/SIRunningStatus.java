package br.org.sbtvd.si;

public interface SIRunningStatus {
	
	/**
	 * 
	 */
	public static final byte UNDEFINED = 0;
	
	/**
	 * 
	 */
	public static final byte NOT_RUNNING = 1;
	
	/**
	 * 
	 */
	public static final byte STARTS_IN_A_FEW_SECONDS = 2;
	
	/**
	 * 
	 */
	public static final byte PAUSING = 3;
	
	/**
	 * 
	 */
	public static final byte RUNNING = 4;

}
