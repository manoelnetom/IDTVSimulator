package br.org.sbtvd.si;

import br.org.sbtvd.net.SBTVDLocator;

public interface SIBroadcaster extends SIInformation{
	
	/**
	 * 
	 * @return
	 */
	public int getBroadcasterID();
	
	/**
	 * 
	 * @return
	 */
	public boolean getBroadcastViewProperty();
	
	/**
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * 
	 * @return
	 */
	public int getOriginalNetworkID();
	
	/**
	 * 
	 * @return
	 */
	public SBTVDLocator[] getSIServiceLocators();
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @return
	 */
	public SIRequest retrieveOriginalNetworkDescriptors(short retrieveMode, 
			Object appData,	SIRetrievalListener listener);
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @return
	 */
	public SIRequest retrieveOriginalNetworkDescriptors(short retrieveMode,
			Object appData,SIRetrievalListener listener, 
			short someDescriptorTags);

}
