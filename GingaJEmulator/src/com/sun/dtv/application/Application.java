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

import javax.tv.locator.Locator;

public interface Application {

	// Global scope for inter-xlet communication.
	public static final int APP_IXC_SCOPE_SECURED = 1;

	// Unsecured scope for inter-xlet communication.
	public static final int APP_IXC_SCOPE_UNSECURED = 2;
	
	// Application type for an HTML application.
	public static final int APP_TYPE_HTML = 2;
	
	// Application type for Java DTV application.
	public static final int APP_TYPE_JAVA = 1;

	// Application type for NCL.
	public static final int APP_TYPE_NCL = 9;

	// Application type for native or resident applications.
	public static final int APP_TYPE_RESIDENT = 32768;

	// Gets the appId as a string.

	public String getAppId();

	// Gets the flags that describe the available sizes of the icons.
	public int getIconFlags();

	// Gets the locator for the Icon.

	public Locator getIconLocator();

	// Generates a fully qualified reference to the object name in the given
	// application namespace for the purpose of inter-xlet communication (IXC)
	// within a given scope.

	public String getIxcFullyQualifiedName(String name, int scope);

	// Gets the name of the application in the default locale.

	public String getName();

	// Gets the name of the application in the requested locale.

	public String getName(String locale);

	// Gets the locales for which the application has locale specific names.

	public String[] getNameLocales();

	// Gets the priority of the application.

	public int getPriority();

	// Gets the application profiles required to run this application.

	public String[] getProfiles();

	// Gets the Locator for the service of this application.

	public Locator getServiceLocator();

	// Gets the application type.

	public int getType();

	// Gets the version number of the application in the requested profile.

	public short[] getVersion(String appProfile);

	// Gets the binding of the Application to the service context.

	public boolean isServiceBound();

	// Gets the startable flag for the application.

	public boolean isStartable();

	// Gets the visibility of the application to users

	public boolean isVisible();

}
