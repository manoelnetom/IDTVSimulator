/******************************************************************************
 * Este arquivo eh parte da implementacao do Projeto OpenGinga
 *
 * Direitos Autorais Reservados (c) 2005-2009 UFPB/LAVID
 *
 * Este programa eh software livre; voce pode redistribui-lo e/ou modificah-lo sob
 * os termos da Licenca Publica Geral GNU versao 2 conforme publicada pela Free
 * Software Foundation.
 *
 * Este programa eh distribuido na expectativa de que seja util, porem, SEM
 * NENHUMA GARANTIA; nem mesmo a garantia implicita de COMERCIABILIDADE OU
 * ADEQUACAO A UMA FINALIDADE ESPECIFICA. Consulte a Licenca Publica Geral do
 * GNU versao 2 para mais detalhes.
 *
 * Voce deve ter recebido uma copia da Licenca Publica Geral do GNU versao 2 junto
 * com este programa; se nao, escreva para a Free Software Foundation, Inc., no
 * endereco 59 Temple Street, Suite 330, Boston, MA 02111-1307 USA.
 *
 * Para maiores informacoes:
 * ginga @ lavid.ufpb.br
 * http://www.openginga.org
 * http://www.ginga.org.br
 * http://www.lavid.ufpb.br
 * ******************************************************************************
 * This file is part of OpenGinga Project
 *
 * Copyright: 2005-2009 UFPB/LAVID, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License version 2 for more
 * details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 *
 * For further information contact:
 * ginga @ lavid.ufpb.br
 * http://www.openginga.org
 * http://www.ginga.org.br
 * http://www.lavid.ufpb.br
 * *******************************************************************************/
package com.sun.dtv.locator;

import jregex.Pattern;
import javax.media.MediaLocator;
import javax.tv.locator.InvalidLocatorException;

/**
 * 
 */
public abstract class EntityLocator extends URLLocator implements TransportDependentLocator
{

	/* Constantes usadas para verificar com que casamento foi feito */
	
	//dtv://<original_network_id>.<transport_stream_id>
	private static final int OID_TSID = 0;
	//dtv://<frequency>:<modulation>.<symbol_rate>
	private static final int FREQ_MOD_SR = 1;
	//dtv://<original_network_id>.<transport_stream_id>.<service_id>
	private static final int OID_TSID_SID = 2;
	//dtv://<original_network_id>.<transport_stream_id>.<service_id>[;<content_id>][.<event_id>]/<component_tag>[;<channel_id>]{&<component_tag>[;<channel_id>]}
	private static final int OID_TSID_SID_CID_EVID_TAG_CHID = 3;
	//dtv://<original_network_id>.<transport_stream_id>.<service_id>[;<content_id>][.<event_id>]/<component_tag> 
	private static final int OID_TSID_SID_CID_EVID_TAG = 4;
	//dtv://<original_network_id>.<transport_stream_id>.<service_id>.<event_id>
	private static final int OID_TSID_SID_EVID = 5;
	//http: ou file: ou https:
	private static final int OTHERS = 6;	
	
	
	//following variables are implicitely defined by getter- or setter-methods:
	protected int originalNetworkId = -1;
	protected int transportStreamId = -1;
	protected int serviceId = -1;
	protected int contentId = -1;
	protected int eventId = -1;
	protected MediaLocator mediaLocator = null;

	/**
	 * Create a locator from the given url.
	 *
	 * 
	 * @param url - the url string
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public EntityLocator(String url) throws InvalidLocatorException
	{
		
		super(url);
		
		//Verifica qual padrão a url casou para preencher os ID's
		for (int p = 0; p < patterns.length; p++) {
			
			Pattern reg = new Pattern(patterns[p]);
			if (reg.matches(url)) {
				
				if (p == OID_TSID || p == OID_TSID_SID 
					|| p == OID_TSID_SID_EVID) {
					
					String[] parts = new String[4];
					char[] caracteres = url.toCharArray();
					int indexInicial = 6; //começa após dtv://
					int finalIndex = 6;
					int partAtual = 0;
					
					while (finalIndex < caracteres.length) {
						
						if (finalIndex + 1 == caracteres.length) {
							
							parts[partAtual] = url.substring(indexInicial);
							finalIndex++;
							partAtual++;
						} else {
							
							if (caracteres[finalIndex] == '.') {
								
								parts[partAtual] = url.substring(indexInicial, finalIndex);
								indexInicial = finalIndex + 1;
								finalIndex = indexInicial;
								partAtual++;
							} else {
								
								finalIndex++;
							}
						}
					}
					
					this.originalNetworkId = (parts[0] != null
												?Integer.parseInt(parts[0], 16)
												:-1);
					this.transportStreamId = (parts[1] != null
							?Integer.parseInt(parts[1], 16)
							:-1);
					this.serviceId = (parts[2] != null
							?Integer.parseInt(parts[2], 16)
							:-1);
					this.eventId = (parts[3] != null
							?Integer.parseInt(parts[3], 16)
							:-1);
				} else if (p == OID_TSID_SID_CID_EVID_TAG_CHID 
						|| p == OID_TSID_SID_CID_EVID_TAG) {
					
					int indexInicial = 6; //começa após dtv
					int indexFinal = url.indexOf('.');
					int atual = 0; //Part atual
					String[] parts = new String[5];
					
					//Pega os três primeiros componente
					for (int i = 0; i < 3; i++) {
						
						//Verifica se não é o terceiro elemento
						if (i != 2) {
							
							parts[atual++] = url.substring(indexInicial, indexFinal);
							indexInicial = indexFinal + 1;
							indexFinal = url.indexOf('.', indexInicial);
						} else {
							
							//Verifica o caractere com a menor posição que é um caractere delimitador
							indexFinal = url.indexOf('/', indexInicial);
							int indexPonto = url.indexOf('.', indexInicial);
							int indexPontoVirgula = url.indexOf(';', indexInicial);
							
							if (indexPonto > -1 && indexPonto < indexFinal) {
								
								indexFinal = indexPonto;
							}
							
							if (indexPontoVirgula > -1 && indexPontoVirgula < indexFinal) {
								
								indexFinal = indexPontoVirgula;
							}
							parts[atual++] = url.substring(indexInicial, indexFinal);
						}
					}
					
					//Verifica a presença do valores opcionais
					if (url.charAt(indexFinal) != '/') {
						
						//Presença de content_id
						if (url.charAt(indexFinal) == ';') {
							
							indexInicial = url.indexOf(';', indexInicial) + 1;
							indexFinal = url.indexOf('/', indexInicial);
							int indexPonto = url.indexOf('.', indexInicial);
							if (indexPonto > -1 && indexPonto < indexFinal) {
								
								indexFinal = indexPonto;
							}
							
							parts[atual] = url.substring(indexInicial, indexFinal);
						}
						atual++;
						
						//Presença de eventid
						if (url.charAt(indexFinal) == '.') {
							
							indexInicial = url.indexOf('.', indexInicial) + 1;
							indexFinal = url.indexOf('/', indexInicial);
							parts[atual++] = url.substring(indexInicial, indexFinal);
						}
					}
					
					this.originalNetworkId = (parts[0] != null
											  ?Integer.parseInt(parts[0], 16)
											  :-1);
					this.transportStreamId = (parts[1] != null
											  ?Integer.parseInt(parts[1], 16)
											  :-1);
					this.serviceId = (parts[2] != null
									  ?Integer.parseInt(parts[2], 16)
									  :-1);
					
					this.contentId = (parts[3] != null
									 ?Integer.parseInt(parts[3], 16)
									 :-1);
					this.eventId = (parts[4] != null
									?Integer.parseInt(parts[4], 16)
									:-1);
				}
				
				break;
			} //Matche
		} //For patterns
		
		mediaLocator = new MediaLocator(url);
	}

	/**
	 * Create a locator for a transport stream.
	 *
	 * 
	 * @param originalNetworkId - original network Id, �-1� must be used to specify the default network id
	 * @param transportId - transport stream Id
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public EntityLocator(int originalNetworkId, int transportId) throws InvalidLocatorException
	{
		
		super("dtv://" + Integer.toHexString(originalNetworkId) + "." + Integer.toHexString(transportId));
		this.originalNetworkId = originalNetworkId;
		this.transportStreamId = transportId;
	}

	/**
	 * Create a locator for a service.
	 *
	 * 
	 * @param originalNetworkId - original network Id, �-1� must be used to specify the default network id
	 * @param transportId - transport stream Id
	 * @param serviceId - service Id
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public EntityLocator(int originalNetworkId, int transportId, int serviceId) throws InvalidLocatorException
	{
		
		super("dtv://" + Integer.toHexString(originalNetworkId) + "." + Integer.toHexString(transportId)
				+ "." + Integer.toHexString(serviceId));
		this.originalNetworkId = originalNetworkId;
		this.transportStreamId = transportId;
		this.serviceId = serviceId;
	}

	/**
	 * Create a locator for an elementary stream.
	 *
	 * 
	 * @param originalNetworkId - original network ID, �-1� must be used to specify the default network id
	 * @param transportId - transport stream ID
	 * @param serviceId - service Id
	 * @param contentId - contents Id of the elementary stream
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public EntityLocator(int originalNetworkId, int transportId, int serviceId, int contentId) throws InvalidLocatorException
	{
		
		super("dtv://" + Integer.toHexString(originalNetworkId) + "." + Integer.toHexString(transportId)
				+ "." + Integer.toHexString(serviceId) + ";" + Integer.toHexString(contentId));
		this.originalNetworkId = originalNetworkId;
		this.transportStreamId = transportId;
		this.serviceId = serviceId;
		this.contentId = contentId;
	}

	/**
	 * Create a locator for an event.
	 *
	 * 
	 * @param originalNetworkId - original network ID, �-1� must be used to specify the default network id
	 * @param transportId - transport stream Id.
	 * @param serviceId - the service Id.
	 * @param contentId - the content Id of the elementary stream.
	 * @param eventId - event Id.
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public EntityLocator(int originalNetworkId, int transportId, int serviceId, int contentId, int eventId) throws InvalidLocatorException
	{
		
		super("dtv://" + Integer.toHexString(originalNetworkId) + "." + Integer.toHexString(transportId)
				+ "." + Integer.toHexString(serviceId) + ";" + Integer.toHexString(contentId)
				+ "." + Integer.toHexString(eventId));
		this.originalNetworkId = originalNetworkId;
		this.transportStreamId = transportId;
		this.serviceId = serviceId;
		this.contentId = contentId;
		this.eventId = eventId;
	}

	/**
	 * Gets the original network Id of this locator.
	 *
	 * 
	 * 
	 * @return the Original Network ID. �-1� if not available.
	 */
	public int getOriginalNetworkId()
	{
		return this.originalNetworkId;
	}

	/**
	 * Get the transport stream Id of this locator.
	 *
	 * 
	 * 
	 * @return the transport stream Id. �-1� if not available.
	 */
	public int getTransportStreamId()
	{
		return this.transportStreamId;
	}

	/**
	 * Get the service Id of this locator.
	 *
	 * 
	 * 
	 * @return the service Id. �-1� if not available.
	 */
	public int getServiceId()
	{
		return this.serviceId;
	}

	/**
	 * Get the content Id of this locator.
	 *
	 * 
	 * 
	 * @return the content Id. �-1� if not available.
	 */
	public int getContentId()
	{
		return this.contentId;
	}

	/**
	 * Get the event Id of this locator.
	 *
	 * 
	 * 
	 * @return the event Id. �-1� if not available.
	 */
	public int getEventId()
	{
		return this.eventId;
	}

	/**
	 * Generates a canonical, string-based representation of this
	 * <code>Locator</code>. The string returned may be entirely
	 * platform-dependent.  If two locators have identical external
	 * forms, they refer to the same resource.  However, two locators
	 * that refer to the same resource may have different external
	 * forms.<p>
	 * 
	 * This method returns the canonical
	 * form of the string that was used to create the Locator (via
	 * <code>LocatorFactory.createLocator()</code>).  In generating
	 * canonical external forms, the implementation will make its best
	 * effort at resolving locators to one-to-one relationships
	 * with the resources that they reference.<p>
	 * 
	 * The result of this method can be used to create new
	 * <code>Locator</code> instances as well as other types of
	 * locators, such as JMF <code>MediaLocator</code>s and
	 * <code>URL</code>s.
	 *
	 * 
	 * @return A string-based representation of this Locator.
	 * @see toExternalForm in interface Locator
	 * @see toExternalForm in class URLLocator
	 * @see Locator.toExternalForm()
	 */
	public String toExternalForm()
	{
		
		return super.toExternalForm();
	}

	/**
	 * Provides the <code>MediaLocator</code> representation if this
	 * instance is a locator to a <code>TransportStream</code> or an
	 * <code>ElementaryStream</code> in a format which is supported by
	 * the JMF implementation.
	 *
	 * 
	 * 
	 * @return the MediaLocator if this EntityLocator is a locator to media that can be rendered by a JMF Player instance, otherwise null.
	 */
	public MediaLocator getMediaLocator()
	{
		return this.mediaLocator;
	}

}
