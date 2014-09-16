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
package com.sun.dtv.media.language;

import javax.media.Control;

/**
 * 
 */
public interface LanguageControl extends Control
{

	/**
	 * Provides a list of all supported language of the current service to the application.
	 * 
	 * If no language is supported the array of language is of length zero
	 *
	 * 
	 * 
	 * @return Array with all the language supported or null if no language  support
	 */
	String[] getSupportedLanguages();

	/**
	 * Sets the language for the player, overrides all previously selected languages.
	 *
	 * 
	 * @param language - to be used either for Audio or Subtitles coded as three  letter String
	 * @return true if setting the language was succesful, false otherwise
	 * @throws LanguageNotSupportedException - in case a language is set that is not supported by the system
	 * @throws IllegalArgumentException - if the language String is not three characters long
	 * @see getLanguage()
	 */
	boolean setLanguage( String language) throws LanguageNotSupportedException, IllegalArgumentException;

	/**
	 * Provides the currently selected language to the application.
	 *
	 * 
	 * 
	 * @return language code of the currently selected language or  null if no language is selected
	 * @see setLanguage(java.lang.String)
	 */
	String getLanguage();

	/**
	 * Returns the default language of the stream/network signaling.
	 *
	 * 
	 * 
	 * @return the default language code of the system or null if no default  language is present
	 */
	String getDefaultLanguage();

}
