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
package com.sun.dtv.broadcast;

import java.io.File;
import java.io.IOException;
import javax.tv.locator.*;

/**
 * 
 */
public class BroadcastStream extends File
{
	//following variables are implicitely defined by getter- or setter-methods:
	private String type;
	private long duration;
	private long currentTime;
	private BroadcastFilesystem broadcastFilesystem;
	private Locator locator;

	/**
	 * Creates a <code>BroadcastStream</code> instance that represents the file
	 * referenced by the given <code>Locator</code>.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast stream cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast stream is accessible.
	 *
	 * 
	 * @param locator - A Locator referencing the source of the BroadcastStream.
	 * @throws InvalidLocatorException - If locator does not refer to a broadcast stream.
	 * @throws IOException - If the requested broadcast stream cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastStream( Locator locator) throws InvalidLocatorException, IOException, UnsupportedOperationException
	{
		super (locator.toString());
	}

	/**
	 * Creates a <code>BroadcastStream</code> instance that represents the
	 * stream whose path name in the broadcast is the given path argument.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast stream cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast stream is accessible.
	 *
	 * 
	 * @param path - The stream path name.
	 * @throws IOException - If the requested broadcast stream cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastStream( String path) throws IOException, UnsupportedOperationException
	{
		super(path);
	}

	/**
	 * Creates a <code>BroadcastStream</code> instance that represents the
	 * stream with the specified name in the specified broadcast directory.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast stream cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast stream is accessible.
	 *
	 * 
	 * @param dir - The directory.
	 * @param name - The stream name.
	 * @throws IOException - If the requested broadcast stream cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastStream( File dir, String name) throws IOException, UnsupportedOperationException
	{
		 super(dir, name);
	}

	/**
	 * Creates a <code>BroadcastStream</code> instance that represents the
	 * stream with the specified name in the specified broadcast directory.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast stream cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast stream is accessible.
	 *
	 * 
	 * @param path - The directory path name.
	 * @param name - The stream name.
	 * @throws IOException - If the requested broadcast stream cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastStream( String path, String name) throws IOException, UnsupportedOperationException
	{
		super(path, name);
	}

	/**
	 * Retrieve the mime type of a BroadcastStream.
	 *
	 * 
	 * 
	 * @return the mime type of the BroadcastStream, null if not available.
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Returns the duration of this <code>BroadcastStream</code>.
	 *
	 * 
	 * 
	 * @return the duration of this broadcast stream in microseconds. Returns -1 if unknown.
	 */
	public long getDuration()
	{
		return this.duration;
	}

	/**
	 * Returns the time of the next chunk in <code>BroadcastStream</code>.
	 *
	 * 
	 * 
	 * @return the time of the next chunk in microseconds. Returns -1 if unknown.
	 */
	public long getCurrentTime()
	{
		return this.currentTime;
	}

	/**
	 * Returns the <code>BroadcastFileSystem</code> this
	 * <code>BroadcastStream</code> belongs to.
	 *
	 * 
	 * 
	 * @return A BroadcastFilesystem this BroadcastStream belongs to.
	 */
	public BroadcastFilesystem getBroadcastFilesystem()
	{
		return this.broadcastFilesystem;
	}

	/**
	 * Returns a <code>Locator</code> identifying this
	 * <code>BroadcastStream</code>.
	 *
	 * 
	 * 
	 * @return A Locator identifying this BroadcastStream.
	 */
	public Locator getLocator()
	{
		return this.locator;
	}

}
