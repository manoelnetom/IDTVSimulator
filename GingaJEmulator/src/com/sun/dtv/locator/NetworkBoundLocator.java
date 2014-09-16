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

import javax.tv.locator.InvalidLocatorException;

/**
 * 
 * 
 */
public abstract class NetworkBoundLocator extends EntityLocator implements TransportDependentLocator
{
	/**
	 * Create a locator from the given url.
	 *
	 * 
	 * @param url - the url string
	 * @throws InvalidLocatorException - if the locator can not be created from the provided parameters.
	 */
	public NetworkBoundLocator( String url) throws InvalidLocatorException
	{
		
		super(url);
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
	 * @see toExternalForm in class EntityLocator
	 * @see Locator.toExternalForm()
	 */
	public String toExternalForm()
	{
		
		return super.toExternalForm();
	}

}
