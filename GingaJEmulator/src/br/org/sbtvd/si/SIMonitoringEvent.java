package br.org.sbtvd.si;

import java.util.EventObject;
import java.util.Date;


/**
 * O objeto da classe SIMonitoringEvent é transmitido para que o objeto ouvinte
 * notifique a aplicação da mudança da informação monitorada.
 *
 * @author jonatas
 *
 */
public class SIMonitoringEvent extends EventObject{

    /**
     * 
     * @param source
     * @param objectType
     * @param networkId
     * @param bouquetId
     * @param originalNetworkId
     * @param transportStreamId
     * @param broadcasterId
     * @param serviceId
     * @param startTime
     * @param endTime
     */	
	public SIMonitoringEvent(SIDatabase source,byte objectType,	int networkId,
			int bouquetId, int originalNetworkId,int transportStreamId,
			int broadcasterId, int serviceId, Date startTime,Date endTime){
		
		super(source);
		//TODO
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBouquetId(){
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getBroadcasterID(){
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getEndTime(){
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNetworkID(){
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getOriginalNetworkID(){
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getServiceID(){
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public byte getSIInformationType(){
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object getSource(){
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public Date getStartTime(){
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTransportStreamID(){
	    return 0;	
	}	
	
}
