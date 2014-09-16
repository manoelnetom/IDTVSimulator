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
public class Mouse extends UserInputDevice
{
	/**
	 * It should not be possible to create a Mouse for everybody.
	 * As any <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html" title="class in com.sun.dtv.ui"><CODE>UserInputDevice</CODE></A>, it is bound to a
	 * <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A>.
	 *
	 * 
	 * 
	 */
	protected Mouse()
	{
		//TODO implement Mouse
	}

	/**
	 * Implements abstract method <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html#getInitiatedEvent(int)"><CODE>UserInputDevice.getInitiatedEvent(int)</CODE></A> defined in <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html" title="class in com.sun.dtv.ui"><CODE>UserInputDevice</CODE></A>.
	 *
	 * 
	 * @param code - the code the input event is initiated for if put in This has to be one of the following: MouseEvent.MOUSE_LEFT_CLICK, MouseEvent.MOUSE_RIGHT_CLICK, MouseEvent.MOUSE_LEFT_PRESS, MouseEvent.MOUSE_RIGHT_PRESS, MouseEvent.MOUSE_LEFT_RELEASE, MouseEvent.MOUSE_RIGHT_RELEASE, MouseEvent.MOUSE_MOVE, MouseEvent.MOUSE_DRAG, MouseEvent.MOUSE_ENTER or MouseEvent.MOUSE_EXIT.
	 * @return the initiated input event
	 * @throws IllegalArgumentException - if the specified code is not supported by this InputDevice; always thrown if the code is not one of the codes mentioned in the description of the code parameter
	 * @see getInitiatedEvent in class UserInputDevice
	 */
	public UserInputEvent getInitiatedEvent(int code) throws IllegalArgumentException
	{
		return null;
		//TODO implement getInitiatedEvent
	}

	/**
	 * Indicates whether the specified mouse code is supported by this particular
	 * input device.
	 *
	 * 
	 * @param mouseCode - the mouse code to be checked Any of the following codes may be supported or not: MouseEvent.MOUSE_LEFT_CLICK, MouseEvent.MOUSE_RIGHT_CLICK, MouseEvent.MOUSE_LEFT_PRESS, MouseEvent.MOUSE_RIGHT_PRESS, MouseEvent.MOUSE_LEFT_RELEASE, MouseEvent.MOUSE_RIGHT_RELEASE, MouseEvent.MOUSE_MOVE, MouseEvent.MOUSE_DRAG, MouseEvent.MOUSE_ENTER or MouseEvent.MOUSE_EXIT. Any other code won't be supported and will always produce false.
	 * @return true if the mouse code is supported by this particular input device, false otherwise
	 */
	public boolean isSupported(int mouseCode)
	{
		return false;
		//TODO implement isSupported
	}

}
