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
package com.sun.dtv.ui;

import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class DownloadableFont
{
	/**
	 * 
	 * 
	 */
	public DownloadableFont()
	{
		//TODO implement DownloadableFont
	}

	/**
	 * Downloads a font from the specified URL and installs the font (or fonts,
	 * if informations for a font family are found) in the system. The installed
	 * font or fonts can be afterwards used like pre-installed fonts, using the
	 * <A HREF="../../../../com/sun/dtv/lwuit/Font.html" title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> class.
	 *
	 * Be aware that this method works synchronous, i.e. it just returns after
	 * the Font information has been successfully downloaded.
	 *
	 * The file found at the locations specified by the URL must contain the
	 * font information in the open font format.
	 *
	 * 
	 * @param url - the URL specifying the font file to be downloaded; any type of URL is valid, including any form of locators
	 * @throws IOException - if an error attempts while trying to  access the data the URL is referring to
	 * @throws IllegalArgumentException - if there is no valid URL provided
	 * @throws SecurityException - if current security policy does not allow for access to the specified URL
	 * @throws FontFileException - if the file the URL is referring to cannot be recognized as a valid font file in open font format
	 */
	public static void installFont( URL url) throws IOException, IllegalArgumentException, SecurityException, FontFileException
	{
		//TODO implement installFont
	}

	/**
	 * Downloads a font from the specified URL and installs the font (or fonts,
	 * if informations for a font family are found) in the system. The installed
	 * font or fonts can be afterwards used like pre-installed fonts, using the
	 * <A HREF="../../../../com/sun/dtv/lwuit/Font.html" title="class in com.sun.dtv.lwuit"><CODE>Font</CODE></A> class.
	 *
	 * Be aware that this method works synchronous, i.e. it just returns after
	 * the Font information has been successfully downloaded, or the time as
	 * specified in the timeout parameter has gone by without a successful
	 * download of the font information.
	 *
	 * The file found at the locations specified by the URL must contain the
	 * font information in the open font format.
	 *
	 * 
	 * @param url - the URL specifying the font file to be downloaded; any type of URL is valid, including any form of locators
	 * @param timeout - timeout in milliseconds
	 * @throws IOException - if an error attempts while trying to  access the data the URL is referring to, including timeout as specified in the timeout parameter
	 * @throws IllegalArgumentException - if there is no valid URL provided
	 * @throws SecurityException - if current security policy does not allow for access to the specified URL
	 * @throws FontFileException - if the file the URL is referring to cannot be recognized as a valid font file in open font format
	 */
	public static void installFont( URL url, int timeout) throws IOException, IllegalArgumentException, SecurityException, FontFileException
	{
		//TODO implement installFont
	}

}
