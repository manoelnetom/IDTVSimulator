package br.org.sbtvd.si;

import javax.tv.service.navigation.ServiceList;
import java.util.Date;

/** 
 * A interface SIInformation é uma coleção de funções comuns a SIBouquet, SIBroadcaster, SINetwork,
 * SITransportStream, SIService, PMTService, SIEvent, SITime e PMTElementaryStream.
 */


public interface SIInformation {
   
	public static final short FROM_CACHE_ONLY=0;
	public static final short FROM_CACHE_OR_STREAM=1;
	public static final short FROM_STREAM_ONLY=2;
	
	/**
	 * 
	 * @return
	 */
	public boolean fromActual();
	
	/**
	 * 
	 * @return
	 */
	public ServiceList getDataSource();
	
	/**
	 * 
	 * @return
	 */
	public short[] getDescriptorTags();
	
	/**
	 * 
	 * @return
	 */
	public SIDatabase getSIDatabase();
    
	/**
	 * 
	 * @return
	 */
	public Date getUpdateTime();
    
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @return
	 * @throws SIIllegalArgumentException
	 */
	public SIRequest retrieveDescriptors(short retrieveMode, Object appData, 
    		SIRetrievalListener listener) throws SIIllegalArgumentException;
    /**
     * 
     * @param retrieveMode
     * @param appData
     * @param listener
     * @param someDescriptorTags
     * @return
     * @throws SIIllegalArgumentException
     */
	public SIRequest retrieveDescriptors(short retrieveMode, Object appData,
    		SIRetrievalListener listener, short[] someDescriptorTags) 
	        throws SIIllegalArgumentException;
    
		
		
}
	