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

import com.sun.dtv.ui.event.UserInputEvent;

/**
 * 
 */
public class Keyboard extends UserInputDevice
{
	/**
	 * It should not be possible to create a Keyboard for everybody.
	 * As any <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html" title="class in com.sun.dtv.ui"><CODE>UserInputDevice</CODE></A>, it is bound to a
	 * <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A>.
	 *
	 * 
	 * 
	 */
	protected Keyboard()
	{
		//TODO implement Keyboard
	}

	/**
	 * Implements abstract method <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html#getInitiatedEvent(int)"><CODE>UserInputDevice.getInitiatedEvent(int)</CODE></A> defined in <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html" title="class in com.sun.dtv.ui"><CODE>UserInputDevice</CODE></A>.
	 *
	 * 
	 * @param code - the code the input event is initiated for if put in
	 * @return the initiated input event
	 * @throws IllegalArgumentException - if the specified code is not supported by this UserInputDevice
	 * @see getInitiatedEvent in class UserInputDevice
	 */
	public UserInputEvent getInitiatedEvent(int code) throws IllegalArgumentException
	{
		return null;
		//TODO implement getInitiatedEvent
	}

	/**
	 * Indicates whether the specified key code is supported by this particular
	 * input device.
	 *
	 * 
	 * @param keyCode - the key code to be checked
	 * @return true if the key code is supported by this particular input device, false otherwise
	 */
	public boolean isSupported(int keyCode)
	{
		return false;
		//TODO implement isSupported
	}

}
