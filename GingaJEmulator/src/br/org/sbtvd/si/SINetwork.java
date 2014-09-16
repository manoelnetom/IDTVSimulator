package br.org.sbtvd.si;

/**
 * A interface SINetwork indica a subtabela da Network Information Table (NIT)
 * que descreve uma rede específica (junto com a SITransportStreamNIT). Cada
 * objeto que implementa a interface SINetwork é identificado pelo network_id.
 * 
 * @author jonatas
 * 
 */

public interface SINetwork extends SIInformation {

	/**
	 * 
	 */
	public short[] getDescriptorTags();

	/**
	 * 
	 * @return
	 */
	public int getNetworkID();

	/**
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 
	 */
	public SIRequest retrieveDescriptors(short retrieveMode, Object appData,
			SIRetrievalListener listener) throws SIIllegalArgumentException;

	/**
	 * 
	 */
	public SIRequest retrieveDescriptors(short retrieveMode, Object appData,
			SIRetrievalListener listener, short[] someDescriptorTags)
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
	public SIRequest retrieveSITransportStreams(short retrieveMode,
			Object appData, SIRetrievalListener listener,
			short[] someDescriptorTags) throws SIIllegalArgumentException;

}
