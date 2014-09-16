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
import javax.tv.locator.InvalidLocatorException;
import javax.tv.locator.Locator;
import javax.tv.locator.LocatorFactory;
import javax.tv.locator.MalformedLocatorException;


/**
 */
public class URLLocator implements Locator
{
	
	//Strings usadas para a validação do url
	protected static final String[] patterns = {
		
		//Casa com dtv://<original_network_id>.<transport_stream_id>
		"dtv://[0-9a-fA-F]+.[0-9a-fA-F]+",
		//Casa com dtv://<frequency>:<modulation>.<symbol_rate>
		"dtv://[0-9]+:([dD][qQ][pP][sS][kK]|[qQ][pP]"
			+ "[sS][kK]|16[qQ][aA][mM]|64[qQ][aA][mM]).(12|23|34|56|78)",
		//Casa com dtv://<original_network_id>.<transport_stream_id>.<service_id> 
		"dtv://[0-9a-fA-F]+.[0-9a-fA-F]+.[0-9a-fA-F]+",
		//Casa com dtv://<original_network_id>.<transport_stream_id>.<service_id>[;<content_id>][.<event_id>]/<component_tag>[;<channel_id>]{&<component_tag>[;<channel_id>]}
		"dtv://[0-9a-fA-F]+.[0-9a-fA-F]+.[0-9a-fA-F]+(;[0-9a-fA-F]+)?(.[0-9a-fA-F]+)?/.*(;[0-9a-fA-F]+)?\\x7B&.*(;[0-9a-fA-F]+)?\\x7D",
		//Casa com dtv://<original_network_id>.<transport_stream_id>.<service_id>[;<content_id>][.<event_id>]/<component_tag> 
		"dtv://[0-9a-fA-F]+.[0-9a-fA-F]+.[0-9a-fA-F]+(;[0-9a-fA-F]+)?(.[0-9a-fA-F]+)?/.*",
		//Casa com dtv://<original_network_id>.<transport_stream_id>.<service_id>.<event_id>
		"dtv://[0-9a-fA-F]+.[0-9a-fA-F]+.[0-9a-fA-F]+.[0-9a-fA-F]+", 
		//Casa com http: ou file: ou https:
		"(http:|file:|https:){1,1}.*"
	};
	
	private String _url = null;
	//Indica se o lacator é válido. Se toExternalForm retornar null indica que o lacator é inválido
	private boolean validLocator = false;
	
	// Construtor usado para criação do locator caso haja uma exceção
	private URLLocator() {		
	}
	
	/**
	 * Constructor for a URL based locator.
	 *
	 * 
	 * @param url - the URL string
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public URLLocator( String url) throws InvalidLocatorException
	{
		
		if (url == null) {
			
			URLLocator loc = new URLLocator();
			loc._url = url;
			throw new InvalidLocatorException(loc, "URL can not be null");
		}
		
		//Veririfica se a url casa com algum padrão válido
		boolean matched = false;
		for (int p = 0; p < patterns.length; p++) {
		
			Pattern reg = new Pattern(patterns[p]);
			if (reg.matches(url)) {
			
				matched = true;
				break;
			}			
		}
		
		//Verifica se não houve casamento
		if (!matched) {
		
			URLLocator loc = new URLLocator();
			loc._url = url;
			throw new InvalidLocatorException(loc, "URL is not a valid url");
		}
		
		_url = new String(url);
		validLocator = true;
	}

	/**
	 * Indicates whether this Locator has a mapping to multiple transports.
	 *
	 * 
	 * @return true if multiple transformations exist for this Locator, false otherwise.
	 * @see hasMultipleTransformations in interface Locator
	 * @see Locator.hasMultipleTransformations()
	 */
	public boolean hasMultipleTransformations()
	{
		
		Locator[] transports = null;
		try {
			
			LocatorFactory factory = LocatorFactory.getInstance();
			if (factory == null) {
				
				return false;
			}
			
			transports = factory.transformLocator(this);
		} catch (InvalidLocatorException ile) {
			
			return false;
		}
		
		return (transports == null
				?false
				:transports.length > 1);
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
	 * @return A string-based representation of this Locator or null to an invalid locator.
	 * @see toExternalForm in interface Locator
	 * @see Locator.toExternalForm()
	 */
	public String toExternalForm()
	{
		
		return (validLocator?this.toString():null);
	}

	/**
	 * Compares this <code>Locator</code> with the specified object for
	 * equality.  The result is <code>true</code> if and only if the
	 * specified object is also a <code>Locator</code> and has an
	 * external form identical to the external form of this
	 * <code>Locator</code>.
	 *
	 * 
	 * @param o - The object against which to compare this Locator.
	 * @return true if the specified object is equal to this Locator.
	 * @see equals in interface Locator
	 * @see equals in class Object
	 * @see String.equals(Object)
	 */
	public boolean equals( Object o)
	{
		
		if (o == null) {
			
			return false;
		}
		
		if (!(o instanceof Locator)) {
			
			return false;
		}
		
		Locator loc = (Locator) o;
		return this.toExternalForm().equals(loc.toExternalForm());
	}

	/**
	 * Generates a hash code value for this <code>Locator</code>.
	 * Two <code>Locator</code> instances for which <code>Locator.equals()</code>
	 * is <code>true</code> will have identical hash code values.
	 *
	 * 
	 * @return The hash code value for this Locator.
	 * @see hashCode in interface Locator
	 * @see hashCode in class Object
	 * @see equals(Object)
	 */
	public int hashCode()
	{
		
		return _url.hashCode();
	}

	/**
	 * Returns the string used to create this locator.
	 *
	 * 
	 * @return The string used to create this locator.
	 * @see toString in interface Locator
	 * @see toString in class Object
	 */
	public String toString()
	{
		
		return _url;
	}

}
