package br.org.sbtvd.si;

import br.org.sbtvd.net.SBTVDLocator;

/**
 * A interface PMTService indica o serviço específico a ser transmitido pelo
 * fluxo de transporte. A informaçAão é recuperada da PMT. Cada objeto montado
 * com a interface PMTService é identificado pela combinação de
 * original_network_id, transport_stream_id, e service_id.
 * 
 * @author jonatas
 * 
 */
public interface PMTService extends SIInformation {

	/**
	 * 
	 * @return
	 */
	public SBTVDLocator getSBTVDLocator();

	/**
	 * 
	 * @return
	 */
	public int getOriginalNetwork();

	/**
	 * 
	 * @return
	 */
	public int getPcrPID();

	/**
	 * 
	 * @return
	 */
	public int getServiceID();

	/**
	 * 
	 * @return
	 */
	public int getTransportStreamID();

	/**
	 * 
	 * @param retrieveMode
	 * @param appData
	 * @param somePMTDescriptorTags
	 * @return
	 */
	public SIRequest retrievePMTElementaryStreams(short retrieveMode,
			Object appData, short[] somePMTDescriptorTags);
}
