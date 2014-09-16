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
package com.sun.dtv.application;

import java.io.IOException;
import java.util.Enumeration;

/**
 * 
 */
public abstract class AppManager extends Object
{
	//following variables are implicitely defined by getter- or setter-methods:
	private static AppManager instance = new br.org.sbtvd.application.AppManagerImpl();

	/**
	 * Gets the singleton AppManager.
	 *
	 * 
	 * 
	 * @return the Singleton AppManager instance.
	 * @throws SecurityException - if the Application does not have AppManagerPermission("*", "read").
	 */
	public static AppManager getInstance()
	{
			return instance;
	}

	/**
	 * Returns an enumeration of the available Applications in the current Service.
	 * The results are the same as:
	 * <pre>getApplications(new AppFilter)</pre>
	 *
	 * 
	 * 
	 * @return an Enumeration of Application s.
	 */
	public abstract Enumeration getApplications();

	/**
	 * Returns an enumeration of the AppProxys for active applications
	 * in the current Service.
	 * The results are the same as:
	 * <pre>getApplications(new AppFilter)</pre>
	 *
	 * 
	 * 
	 * @return an Enumeration of ApplicationProxy s.
	 * @throws SecurityException - if the Application does not have AppManagerPermission("*", "manage").
	 */
	public abstract Enumeration getActiveApplications();

	/**
	 * Returns an enumeration of the applications filtered by the
	 * <A HREF="../../../../com/sun/dtv/application/AppFilter.html" title="class in com.sun.dtv.application"><CODE>AppFilter</CODE></A>.  All known Applications are passed to
	 * the filter and the filter <code>accept</code>s them or not.
	 *
	 * 
	 * @param appFilter - an AppFilter used to select Applications.
	 * @return an Enumeration of Application s.
	 */
	public abstract Enumeration getApplications( AppFilter appFilter);

	/**
	 * Returns an enumeration of the AppProxys for active applications
	 * filtered by the <A HREF="../../../../com/sun/dtv/application/AppFilter.html" title="class in com.sun.dtv.application"><CODE>AppFilter</CODE></A>. All known Applications are passed to
	 * the filter and the filter <code>accept</code>s them or not.
	 *
	 * 
	 * @param appFilter - an AppFilter used to select Applications.
	 * @return an Enumeration of Application s.
	 * @throws SecurityException - if the Application does not have AppManagerPermission("*", "manage").
	 */
	public abstract Enumeration getActiveApplications( AppFilter appFilter);

	/**
	 * Makes an <code>appId</code> for the application with the
	 * indicated organization id and application id.
	 * The organization id and application id are concatenated to form a
	 * 48 bit unsigned value.  All 32 bits are used from the organization id
	 * and become the most significant bits of the result.
	 * Only the least significant 16 bits from the application id are used and
	 * they become the least significant bits of the result.
	 * That value is converted to a hexadecimal string using only
	 * digits and lowercase characters ('0'..'9','a'..'f').
	 * The string MUST NOT have any leading '0' characters.
	 *
	 * 
	 * @param organization - an organization id as an int
	 * @param applicationid - an application id as an int
	 * @return a String formatted properly with the organization id and application id.
	 */
	public abstract String makeApplicationId(int organization, int applicationid);

	/**
	 * Gets the Application for the requested <code>appId</code>.
	 *
	 * 
	 * @param appId - the String appId
	 * @return the Application with the matching appId; otherwise null.
	 * @see makeApplicationId(int, int)
	 */
	public abstract Application getApplication( String appId);

	/**
	 * Gets the application proxy for the requested active Application.
	 *
	 * 
	 * @param appId - the String appId
	 * @return the ApplicationProxy of an active application with the matching appId; otherwise null.
	 * @throws SecurityException - if the Application does not have AppManagerPermission("*", "manage").
	 * @see makeApplicationId(int, int)
	 */
	public abstract ApplicationProxy getApplicationProxy( String appId);

	/**
	 * Fetch an application from a locator.
	 * The method blocks the calling thread until sufficient information
	 * is available for the application. The application is entered
	 * into the database.
	 * Calls to <code>fetch</code> are internally synchronized so that
	 * the application is fetched only once and the same Application object
	 * is returned.
	 *
	 * 
	 * @param locator - referencing an application.
	 * @return the Application reference.
	 * @throws SecurityException - if the Application does not have AppManagerPermission("*", "fetch").
	 * @throws SecurityException - if this Application does not have permission to use the locator to fetch the application.
	 * @throws IOException - if attempts to fetch the application results in an exception.
	 */
	public abstract Application fetch( String locator) throws IOException;

	/**
	 * Unload the application, freeing any resources that can be
	 * acquired if needed.
	 * If the application is active, the unload is deferred until the
	 * application is no longer active. The unload request is canceled
	 * if the application is restarted.
	 *
	 * 
	 * @param appId - the appId of the application to unload
	 * @throws SecurityException - if the Application does not have AppManagerPermission("*", "manage").
	 */
	public abstract void unload( String appId);

	/**
	 * Adds a listener for changes in the available applications.
	 *
	 * 
	 * @param listener - the listener to add.
	 * @see removeListener(com.sun.dtv.application.AppManagerListener)
	 */
	public abstract void addListener( AppManagerListener listener);

	/**
	 * Remove a listener registered for changes in available applications.
	 * If the listener was not added, no action is taken.
	 *
	 * 
	 * @param listener - the listener to remove.
	 * @see addListener(com.sun.dtv.application.AppManagerListener)
	 */
	public abstract void removeListener( AppManagerListener listener);

}
