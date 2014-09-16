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
package com.sun.dtv.platform;

import java.util.Properties;

public final class User {

	//Default property name for location country code.
	public static String PROPKEY_LOCATION_COUNTRY = "";

    //Default property name for parental rating.
	public static String PROPKEY_RATING_PARENTAL = "";

    //Default property name for UI font size.
	public static String PROPKEY_UI_FONT_SIZE = "26";

    //Default property name for user address.
	public static String PROPKEY_USER_ADDRESS = "";

    //Default property name for user email.
	public static String PROPKEY_USER_EMAIL = "";

    //Default property name for user languages.
	public static String PROPKEY_USER_LANGUAGES = "";

    //Default property name for user name.
	public static String PROPKEY_USER_NAME = "";

    //Attaches a listener that is triggered for changes to any properties within the range defined by filter.
    public static void addListener(UserPropertyListener listener, String filter){
		
	}

    //Retrieves a copy of all user properties that matches filter and for which the application has read access right.
	public static Properties getProperties(String filter){
		return null;
		
	}

    //Gets the user property indicated by the specified key.
    public static String getProperty(String key){
		return key;
    	
    }

    //Gets the user property indicated by the specified key.
    public static String getProperty(String key, String def){
		return def;
    	
    }

    //Detaches a user property listener that was previously attached using addListener().
	public static void removeListener(UserPropertyListener listener){
		
	}

    //Removes this property and all of its descendants, invalidating any properties contained in the removed nodes.
    public static void removeProperties(String filter){
    	
    }

    //Adds new properties and/or replaces existing properties.
    public static void setProperties(Properties properties){
    	
    }

    //Sets the user property indicated by the specified key.
    public static String setProperty(String key, String value){
		return value;
    	
    }

}
