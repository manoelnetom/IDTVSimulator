package br.org.sbtvd.si;

import java.util.Date;

import br.org.sbtvd.net.SBTVDLocator;

/**
 * A interface SIEvent indica um programa específico no serviço. Cada objeto que
 * implementa a interface SIEvent é identificado pela combinação do
 * original_network_id, transport_stream_id, service_id e event_id. Se o valor
 * de retorno do método for adquirido da forma simples do descritor de evento e
 * mais de um descritor está presente, o algoritmo seguinte deve ser usado. No
 * caso da linguagem retornada por
 * javax.tv.service.SIManager#getPreferredLanguage ser usada no descritor
 * simples de evento, o valor é retornado do descritor. Caso contrário, ele
 * depende do estado da montagem que deve ser usado além dos descritores simples
 * de evento disponíveis.
 * 
 * @author jonatas
 * 
 */
public interface SIEvent extends SIInformation {

	/**
	 * 
	 * @return
	 */
	public SBTVDLocator getSBTVDLocator();

	/**
	 * 
	 * @return
	 */
	public String[] getAudioComponentDescriptions();

	/**
	 * 
	 * @return
	 */
	public String[] getComponentDescriptions();

	/**
	 * 
	 * @return
	 */
	public byte[] getContentNibbles();

	/**
	 * 
	 * @return
	 */
	public String[] getDataContentDescriptions();

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
	public long getDuration();

	/**
	 * 
	 * @return
	 */
	public int getEventID();

	/**
	 * 
	 * @return
	 */
	public Date getStartTime();

	/**
	 * 
	 * @return
	 */
	public SIExEventInformation[] getExEventInformations();

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
	 * @return
	 */
	public byte[] getLevel1ContentNibbles();

	/**
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 
	 * @return
	 */
	public String getSeriesName();

	/**
	 * 
	 * @return
	 */
	public String getShortDescription();

	/**
	 * 
	 * @return
	 */
	public byte[] getUserNibbles();

	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param listener
	 * @param someDescriptorTags
	 * @return
	 * @throws SIIllegalArgumentException
	 */
	public SIRequest retrieveSIService(short retrieveMode, Object appData,
			SIRetrievalListener listener, short[] someDescriptorTags)
			throws SIIllegalArgumentException;

}
