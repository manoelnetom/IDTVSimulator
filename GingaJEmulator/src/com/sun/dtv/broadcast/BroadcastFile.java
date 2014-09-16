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

import javax.tv.locator.InvalidLocatorException;
import javax.tv.locator.Locator;

/**
 * 
 */
public class BroadcastFile extends File{

	private BroadcastFilesystem broadcastFilesystem;
	private Locator locator;
	private String type;

	/**
	 * Creates a <code>BroadcastFile</code> instance that represents the file
	 * referenced by the given <code>Locator</code>. This method does not initiate
	 * to load or block on loading files from the broadcast.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast file cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast file is accessible.
	 *
	 * 
	 * @param locator - A Locator referencing the source of the BroadcastFile.
	 * @throws InvalidLocatorException - If locator does not refer to a broadcast file.
	 * @throws IOException - If the requested broadcast file cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastFile( Locator locator) throws InvalidLocatorException, IOException, UnsupportedOperationException
	{
		super(locator.toString());
	}

	/**
	 * Creates a <code>BroadcastFile</code> instance that represents the
	 * file whose path name in the broadcast is the given path argument.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast file cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast file is accessible.
	 *
	 * 
	 * @param path - The file path name.
	 * @throws IOException - If the requested broadcast file cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastFile( String path) throws IOException, UnsupportedOperationException
	{
		super(path);
	}

	/**
	 * Creates a <code>BroadcastFile</code> instance that represents the
	 * file with the specified name in the specified broadcast directory.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast file cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast file is accessible.
	 *
	 * 
	 * @param dir - The directory.
	 * @param name - The file name.
	 * @throws IOException - If the requested broadcast file cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastFile( File dir, String name) throws IOException, UnsupportedOperationException
	{
		super(name);
	}

	/**
	 * Creates a <code>BroadcastFile</code> instance that represents the
	 * file with the specified name in the specified broadcast directory.
	 *
	 * 
	 * This constructor throws <code>java.io.IOException</code> if it
	 * determines immediately that the requested broadcast file cannot
	 * be accessed.  Since this constructor may complete its work
	 * asynchronously, absence of an <code>IOException</code> is not a
	 * guarantee that the requested broadcast file is accessible.
	 *
	 * 
	 * @param path - The directory path name.
	 * @param name - The file name.
	 * @throws IOException - If the requested broadcast file cannot be accessed or the received data is invalid.
	 * @throws UnsupportedOperationException - If broadcast filesystem is not supported.
	 */
	public BroadcastFile( String path, String name) throws IOException, UnsupportedOperationException
	{
		super(name);
	}

	/**
	 * Lists the directory contents of this <code>BroadcastFile</code> object.
	 * This list does not include the current or parent directories.
	 * This list may also be incomplete in case not all entries in this directory
	 * have been loaded yet.
	 *
	 * 
	 * 
	 * @return An array of file names contained in the directory specified by this BroadcastFile object.  If this BroadcastFile object does not refer to a directory, this method returns null.
	 * @throws IOException - If the directory cannot be accessed or the received data is invalid.
	 * @throws SecurityException - If a security manager exists and its SecurityManager.checkRead(String) method denies read access to the file.
	 */
	public String[] listDirectory() throws IOException
	{
		return null;
		//TODO implement listDirectory
	}

	/**
	 * Subscribes a <code>BroadcastFileListener</code> to receive
	 * notifications of changes to this <code>BroadcastFile</code>.  If
	 * the specified listener is currently subscribed then no action is
	 * performed.
	 *
	 * 
	 * @param listener - The BroadcastFileListener to be notified.
	 * @throws IOException - If there are insufficient resources to support this listener.
	 * @throws SecurityException - If a security manager exists and its SecurityManager.checkRead(java.lang.String) method denies read access to the file.
	 * @see removeListener(com.sun.dtv.broadcast.BroadcastFileListener)
	 */
	public void addListener( BroadcastFileListener listener) throws IOException
	{
		//TODO implement addListener
	}

	/**
	 * Unsubscribes a <code>BroadcastFileListener</code> from receiving
	 * notifications of changes to this <code>BroadcastFile</code>.  If
	 * the given <code>BroadcastFileListener</code> is not currently
	 * subscribed for notification then no action is performed.
	 *
	 * 
	 * @param listener - A currently registered BroadcastFileListener.
	 * @see addListener(com.sun.dtv.broadcast.BroadcastFileListener)
	 */
	public void removeListener( BroadcastFileListener listener)
	{
		//TODO implement removeListener
	}

	/**
	 * Returns the <code>BroadcastFileSystem</code> this
	 * <code>BroadcastFile</code> belongs to.
	 *
	 * 
	 * 
	 * @return A BroadcastFilesystem this BroadcastFile belongs to.
	 */
	public BroadcastFilesystem getBroadcastFilesystem()
	{
		return this.broadcastFilesystem;
	}

	/**
	 * Returns a <code>Locator</code> identifying this
	 * <code>BroadcastFile</code>.
	 *
	 * 
	 * 
	 * @return A Locator identifying this BroadcastFile.
	 */
	public Locator getLocator()
	{
		return this.locator;
	}

	/**
	 * Retrieve whether or not a BroadcastFile is completely available in filesystem
	 * cache locally in the system.
	 *
	 * 
	 * 
	 * @return true if the BroadcastFile is available in filesystem cache
	 */
	public boolean isCached()
	{
		return false;
		//TODO implement isCached
	}

	/**
	 * Retrieve the mime type of a BroadcastFile.
	 *
	 * 
	 * 
	 * @return the mime type of the BroadcastFile, null if not available.
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Requests that the cached contents of this <code>BroadcastFile</code> be
	 * updated with the version currently in the broadcast stream.  If the
	 * <code>BroadcastFile</code> data does not currently reside in the broadcast
	 * stream, subsequent attempts to access its contents will fail.
	 *
	 * 
	 * 
	 * @throws SecurityException - If a security manager exists and its SecurityManager.checkRead(java.lang.String) method denies read access to the file.
	 */
	public void refreshCache() throws SecurityException
	{
		//TODO implement refreshCache
	}

}
