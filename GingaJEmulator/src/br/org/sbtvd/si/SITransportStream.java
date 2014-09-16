package br.org.sbtvd.si;

import br.org.sbtvd.net.SBTVDLocator;


public interface SITransportStream extends SIInformation{
	/**
	 * 
	 * @return
	 */
	public SBTVDLocator getSBTVDLocator();
	
	/**
	 * 
	 * @return
	 */
	public int getOriginalNetworkID();
	
	/**
	 * 
	 * @return
	 */
	public int getTransportStreamID();
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @return
	 * @throws SIIllegalArgumentException
	 */
	public SIRequest retrieveSIServices(short retrieveMode, Object appData,
            SIRetrievalListener listener,short[] someDescriptorTags)
     		throws SIIllegalArgumentException;	
}
