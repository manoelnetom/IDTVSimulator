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

import java.util.EventListener;

/**
 *
 */
public interface BroadcastFileListener extends EventListener	
{
	/**
	 * Notifies the <code>BroadcastFileListener</code> that the
	 * <code>BroadcastFile</code> has changed in the broadcast.
	 * 
	 * If the contents of a <code>BroadcastFile</code> change while an
	 * application is reading its data from the local cache, the
	 * cached data shall either (a) remain entirely unchanged or (b)
	 * be flushed from the cache.  If the data is flushed from the
	 * cache, attempts to read from this <code>BroadcastFile</code>
	 * using pre-existing file reading objects
	 * (e.g. <code>FileInputStream</code>, <code>FileReader</code>, or
	 * <code>RandomAccessFile</code>) will fail.
	 * 
	 * To read the new data, the application must create a new
	 * file reading object.  To ensure that this data is the most recent
	 * version from the broadcast, the application should first invoke
	 * the <code>BroadcastFile.refreshCache()</code> method.
	 * 
	 * No guarantees are provided concerning the ability of the
	 * receiver to detect changes to the broadcast
	 * <code>BroadcastFile</code> or the latency of event notification
	 * if a change is detected.
	 *
	 * 
	 * @param event - Event indicating BroadcastFile that has changed.
	 * @see BroadcastFile.refreshCache()
	 */
	void broadcastFileChanged( BroadcastFileEvent event);

	/**
	 * Notifies the <code>BroadcastFileListener</code> that the
	 * <code>BroadcastFile</code> has been lost from the broadcast.
	 * Reason for losing <code>BroadcastFile</code> from the broadcast
	 * could be selection of another service, transmission errors,
	 * discontinuation of the broadcast or others.
	 * 
	 * If the contents of a <code>BroadcastFile</code> is lost while an
	 * application is reading its data from the local cache, the
	 * cached data shall either (a) remain available or (b)
	 * be cleared from the cache.  If the data is cleared from the
	 * cache, attempts to read from this <code>BroadcastFile</code>
	 * using pre-existing file reading objects
	 * (e.g. <code>FileInputStream</code>, <code>FileReader</code>, or
	 * <code>RandomAccessFile</code>) will fail. Creation of new file
	 * reading object MUST also fail. To ensure information about file
	 * availability is the most recent from the broadcast, the application should first invoke
	 * the <code>BroadcastFile.refreshCache()</code> method on the parent directory.
	 * 
	 * No guarantees are provided concerning the ability of the
	 * receiver to detect changes to the broadcast
	 * <code>BroadcastFile</code> or the latency of event notification
	 * if a lost is detected.
	 *
	 * 
	 * @param event - Event indicating BroadcastFile that has changed.
	 * @see BroadcastFile.refreshCache()
	 */
	void broadcastFileLost( BroadcastFileEvent event);

}
