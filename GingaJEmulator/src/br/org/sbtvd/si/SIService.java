package br.org.sbtvd.si;

import java.util.Date;
import br.org.sbtvd.net.SBTVDLocator;

public interface SIService extends SIInformation{


	/**
	 * 
	 * @return
	 */
	public SBTVDLocator getSBTVDLocator();
	
	/**
	 * 
	 * @return
	 */
	public boolean getEITScheduleFlag();
	
	/**
	 * 
	 * @return
	 */
	public boolean getEITPresentFollowingFlag();
	
	/**
	 * 
	 * @return
	 */
	public int getEITUserDefinedFlag();	
	
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
	 * @return
	 */
	public int getServiceID();
	
	/**
	 * 
	 * @return
	 */
	public short getSIServiceType();
	
	/**
	 * 
	 * @return
	 */
	public String getName();
	
	
	/**
	 * 
	 * @return
	 */
	public String getProviderName();
	

	
	/**
	 * 
	 * @return
	 */
	public byte getRunningStatus();
	
	/**
	 * 
	 * @return
	 */
	public boolean getFreeCAMode();
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @return
	 * @throws SIIllegalArgumentException
	 */ 
	public SIRequest retrievePresentSIEvent(short retrieveMode,Object appData,
            SIRetrievalListener listener,short[] someDescriptorTags)
     		throws SIIllegalArgumentException;
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @return
	 * @throws SIIllegalArgumentException
	 */
	public SIRequest retrieveFollowingSIEvent(short retrieveMode,Object appData,
            SIRetrievalListener listener, short[] someDescriptorTags)
     		throws SIIllegalArgumentException;
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws SIIllegalArgumentException
	 * @throws SIInvalidPeriodException
	 */
	public SIRequest retrieveScheduledSIEvents(short retrieveMode, Object appData,
            SIRetrievalListener listener, short[] someDescriptorTags,
            Date startTime, Date endTime)
     		throws SIIllegalArgumentException, SIInvalidPeriodException;
	
	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @return
	 * @throws SIIllegalArgumentException
	 */
	public SIRequest retrievePMTService(short retrieveMode, Object appData,
            SIRetrievalListener listener,short[] someDescriptorTags)
     		throws SIIllegalArgumentException;
	

}
 


