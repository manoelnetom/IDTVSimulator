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
package com.sun.dtv.net;

import java.net.NetworkInterface;


/**
 * 
 * 
 */
public interface NetworkDeviceStatusListener
{
	/**
	 * Reports when <code>device</code> has been connected.
	 * 
	 *Along that call, the implementation passes a <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/net/NetworkInterface.html?is-external=true" title="class or interface in java.net"><CODE>NetworkInterface</CODE></A> object that represents the interface that
	 * has been assigned to that connection. Among other facilities, this
	 * class allows to retrieve the associated Internet addresses.</p>
	 *
	 * 
	 * @param device - The network device which has been connected.
	 * @param netif - The associated NetworkInterface object.
	 */
	void connected( NetworkDevice device, NetworkInterface netif);

	/**
	 * Reports when <code>device</code> could not connect for some reason.
	 *
	 * 
	 * @param device - The network device which connection failed.
	 * @param reason - A short string describing the error that occurred.
	 */
	void connectionFailed( NetworkDevice device, String reason);

	/**
	 * Reports the current data rate of a given <code>device</code> if it has
	 * changed since the last report. Data rate is informed only when the
	 * network device has been connected beforehand. Right after a
	 * connection, the platform <span class="rfc2119">must</span> call this
	 * method at least one time.
	 * 
	 *The implementation <span class="rfc2119">should</span> not
	 * excessively call this method and by no means oftener than every
	 * one second.</p>
	 *
	 * 
	 * @param device - The network device which data rate has changed.
	 * @param dataRate - The current data rate in kbps.
	 * @see NetworkDevice.getMaximumSpeed()
	 */
	void currentDataRate( NetworkDevice device, long dataRate);

	/**
	 * Reports when <code>device</code> is about to be disconnected
	 * because the inactivity timeout period given at construction time
	 * has elapsed.
	 * 
	 *The application is given hereby the chance to react accordingly
	 * in the case it does not want to be disconnect: if the implementation
	 * of this method returns <code>true</code>, then the implementation
	 * <span class="rfc2119">must</span> disconnect the device on return of
	 * this method. If the method returns <code>false</code>, then the
	 * implementation <span class="rfc2119">must</span> reset the
	 * timer to the original timeout value allowing it to re-trigger the
	 * application at the next timeout.</p>
	 * 
	 *When calling this method, certain platforms <span
	 * class="rfc2119">may</span> define a timespan until when this method
	 * is expected to return. If it does not return within that timeframe,
	 * the implementation <span class="rfc2119">must</span> behave as if
	 * the method returned <code>true</code>. This mechanism allows to avoid
	 * blocking this method unnecessarily.</p>
	 *
	 * 
	 * @param device - The network device which timeout has expired.
	 * @return A boolean to indicate whether the application is willing to accept the timeout. The value true indicates the implementation to disconnect the given device.
	 */
	boolean timingOut( NetworkDevice device);

	/**
	 * Reports when <code>device</code> has been disconnected.
	 *
	 * 
	 * @param device - The network device which has been disconnected.
	 */
	void disconnected( NetworkDevice device);

}
