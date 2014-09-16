package br.org.sbtvd.si;

import br.org.sbtvd.net.SBTVDLocator;

public interface PMTElementaryStream extends SIInformation{
    
	/**
	 * 
	 * @return
	 */
	public SBTVDLocator getSBTVDLocator();
	
	/**
	 * 
	 * @return
	 */
	public int getComponentTag();
	
	/**
	 * 
	 * @return
	 */
	public short getElementaryPID();
	
	/**
	 * 
	 * @return
	 */
	public int getOriginalNetworkID();
	
	/**
	 * 	 * @return
	 */
	public int getServiceID();
	
	/**
	 * 
	 * @return
	 */
	public byte getStreamType();
	
	/**
	 * 
	 * @return
	 */
	public int getTransportStreamID();
	
	
	
}
