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
package com.sun.dtv.security;

import java.security.Principal;
import java.security.Provider;

/**
 * 
 */
public abstract class AuthProvider extends Provider
{
	/**
	 * Constructs a provider with the specified name, version number, and information.
	 *
	 * 
	 * @param name - the provider name
	 * @param version - the provider version number.
	 * @param info - a description of the provider and its services.
	 */
	protected AuthProvider( String name, double version, String info)
	{
		//TODO implement AuthProvider
		super(name, version, info);
	}

	/**
	 * Log in to this provider.
	 * 
	 * The provider relies on a CallbackHandler  to obtain authentication information
	 * from the caller (a PIN, for example). If the caller passes a null  handler
	 * to this method, the provider uses the handler set in the setCallbackHandler
	 * method.
	 *
	 * 
	 * @param principal - the Principal which may contain names/login ids used for authentication, or may be populated with additional names/login after successful authentication has completed. This parameter may be null.
	 * @param handler - he CallbackHandler used by this provider to obtain authentication information from the caller, which may be null
	 * @throws LoginException - if the login operation fails
	 */
	public abstract void login( Principal principal, CallbackHandler handler) throws LoginException;

	/**
	 * Log out from this provider.
	 *
	 * 
	 * 
	 * @throws LoginException - if the logout operation fails
	 */
	public abstract void logout() throws LoginException;

	/**
	 * Set a CallbackHandler.
	 * The provider uses this handler if one is not passed to the login method.
	 * The provider also uses this handler if it invokes login on behalf of callers.
	 * If the CallbackHandler is not set, the provider is assumed to have alternative
	 * means for obtaining authentication information.
	 *
	 * 
	 * @param handler - a CallbackHandler for obtaining authentication information, which may be null
	 */
	public abstract void setCallbackHandler( CallbackHandler handler);

}
