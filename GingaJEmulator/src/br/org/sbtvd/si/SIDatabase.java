package br.org.sbtvd.si;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import br.org.sbtvd.net.SBTVDLocator;

public class SIDatabase {

	private static SIDatabase[] allSIDatabase;
	private static SIDatabase siDatabase;
	
	private ArrayList siListeners;
	private LinkedList siEvents;

	private SBTVDLocator locator = null;

	/**
	 * 
	 */
	public static final int RETRIEVE_ALL_INFORMATIONS = -1;

	/**
	 * 
	 */
	public static final int RETRIEVE_CURRENT_SELECTED = -2;

	protected SIDatabase() {
		
		siListeners = new ArrayList();
		siEvents = new LinkedList();

	}

	/**
	 * @return
	 * 
	 */
	public static SIDatabase[] getSIDatabase() {

		if (allSIDatabase == null) {
			allSIDatabase = new SIDatabase[1];
			siDatabase = new SIDatabase();
			allSIDatabase[0] = siDatabase;
		}
		return allSIDatabase;

	}

	/**
	 * @param listener
	 * @param bouquetId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void addBouquetMonitoringListener(SIMonitoringListener listener,
			int bouquetId) throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param broadcasterId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void addBroadcasterMonitoringListener(SIMonitoringListener listener,
			int broadcasterId) throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	public void addEventPresentFollowingMonitoringListener(
			SIMonitoringListener listener, int originalNetworkId,
			int transportStreamId, int serviceId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @param startTime
	 * @param endTime
	 * @throws SIIllegalArgumentException
	 * @throws SIInvalidPeriodException
	 * 
	 */
	void addEventScheduleMonitoringListener(SIMonitoringListener listener,
			int originalNetworkId, int transportStreamId, int serviceId,
			Date startTime, Date endTime) throws SIIllegalArgumentException,
			SIInvalidPeriodException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param networkId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void addNetworkMonitoringListener(SIMonitoringListener listener,
			int networkId) throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}
	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @throws SIIllegalArgumentException
	 */
	void addPMTServiceMonitoringListener(SIMonitoringListener listener,
			int originalNetworkId, int transportStreamId, int serviceId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void addServiceMonitoringListener(SIMonitoringListener listener,
			int originalNetworkId, int transportStreamId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param bouquetId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void removeBouquetMonitoringListener(SIMonitoringListener listener,
			int bouquetId) throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}
	}

	/**
	 * @param listener
	 * @param broadcasterId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void removeBroadcasterMonitoringListener(SIMonitoringListener listener,
			int broadcasterId) throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void removeEventPresentFollowingMonitoringListener(
			SIMonitoringListener listener, int originalNetworkId,
			int transportStreamId, int serviceId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void removeEventScheduleMonitoringListener(SIMonitoringListener listener,
			int originalNetworkId, int transportStreamId, int serviceId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void removeNetworkMonitoringListener(SIMonitoringListener listener,
			int networkId) throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @throws SIIllegalArgumentException
	 * 
	 */
	void removePMTServiceMonitoringListener(SIMonitoringListener listener,
			int originalNetworkId, int transportStreamId, int serviceId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}
	}

	/**
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @throws SIIllegalArgumentException
	 */
	void removeServiceMonitoringListener(SIMonitoringListener listener,
			int originalNetworkId, int transportStreamId)
			throws SIIllegalArgumentException {

		if (listener == null) {
			throw new SIIllegalArgumentException("SIMonitoringListener null");
		}

	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 */
	SIRequest retrieveActualSINetwork(short retrieveMode, Object appData,
			SIRetrievalListener listener, short[] someDescriptorTags)
			throws SIIllegalArgumentException {

		return null;

	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveActualSIServices(short retrieveMode, Object appData,
			SIRetrievalListener listener, short[] someDescriptorTags)
			throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveActualSITransportStream(short retrieveMode,
			Object appData, SIRetrievalListener listener,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param sbtvdlocator
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrievePMTElementaryStreams(short retrieveMode, Object appData,
			SIRetrievalListener listener, SBTVDLocator sbtvdLocator,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param serviceId
	 * @param componentTag
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrievePMTElementaryStreams(short retrieveMode, Object appData,
			SIRetrievalListener listener, int serviceId, int componentTag,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param sbtvdlocator
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrievePMTService(short retrieveMode, Object appData,
			SIRetrievalListener listener, SBTVDLocator sbtvdLocator,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param serviceId
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrievePMTServices(short retrieveMode, Object appData,
			SIRetrievalListener listener, int serviceId,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param bouquetId
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveSIBouquets(short retrieveMode, Object appData,
			SIRetrievalListener listener, int bouquetId,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param originalNetworkId
	 * @param broadcasterId
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveSIBroadcaster(short retrieveMode, Object appData,
			SIRetrievalListener listener, int originalNetworkId,
			int broadcasterId, short[] someDescriptorTags)
			throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param originalNetworkId
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveSIBroadcasters(short retrieveMode, Object appData,
			SIRetrievalListener listener, int originalNetworkId,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param networkId
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveSINetworks(short retrieveMode, Object appData,
			SIRetrievalListener listener, int networkId,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param sbtvdlocator
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	public SIRequest retrieveSIService(short retrieveMode, Object appData,
			SIRetrievalListener listener, SBTVDLocator sbtvdLocator,
			short[] someDescriptorTags) throws SIIllegalArgumentException {

		SIRequest request = new SIRequest();

                /*
		SIInformationImpl si = new SIInformationImpl();
		int[] servicesIds = si.getNativeServicesIDs();
		SIIteratorImpl sit = new SIIteratorImpl(servicesIds.length);

		for (int i = 0; i < servicesIds.length; i++) {
			SIServiceImpl service = new SIServiceImpl(locator, servicesIds[i]);
			sit.addItem(service);
		}

		SISuccessfulRetrieveEvent event = new SISuccessfulRetrieveEvent(
				appData, request, sit);
		listener.postRetrievalEvent(event);
                */
                
		return request;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param originalNetworkId
	 * @param transportStreamId
	 * @param serviceId
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	public SIRequest retrieveSIServices(short retrieveMode, Object appData,
			SIRetrievalListener listener, int originalNetworkId,
			int transportStreamId, int serviceId, short[] someDescriptorTags)
			throws SIIllegalArgumentException {

		SIRequest request = new SIRequest();

                /*
		SIInformationImpl si = new SIInformationImpl();
		int[] servicesIds = si.getNativeServicesIDs();
		SIIteratorImpl sit = new SIIteratorImpl(1);
		
		for (int i = 0; i < servicesIds.length;i++){
			if (servicesIds[i] == serviceId){
				SIServiceImpl service = new SIServiceImpl(locator, serviceId);
				sit.addItem(service);
				break;
			}
		}

		SISuccessfulRetrieveEvent event = new SISuccessfulRetrieveEvent(
				appData, request, sit);
		listener.postRetrievalEvent(event);
                */

		return request;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @throws SIIllegalArgumentException
	 * 
	 */
	SIRequest retrieveSITimeFromTDT(short retrieveMode, Object appData,
			SIRetrievalListener listener) throws SIIllegalArgumentException {

		return null;
	}

	/**
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @throws SIIllegalArgumentException
	 * 
	 */
	public SIRequest retrieveSITimeFromTOT(short retrieveMode, Object appData,
			SIRetrievalListener listener, short[] someDescriptorTags)
			throws SIIllegalArgumentException {

		SIRequest request = new SIRequest();

                /*
		SIInformationImpl si = new SIInformationImpl();
		SIIteratorImpl sit = new SIIteratorImpl(1);

		SITimeImpl time = new SITimeImpl();
			sit.addItem(time);
	
		SISuccessfulRetrieveEvent event = new SISuccessfulRetrieveEvent(
				appData, request, sit);
		listener.postRetrievalEvent(event);
                */

		return request;	
	}

}// / end of class
