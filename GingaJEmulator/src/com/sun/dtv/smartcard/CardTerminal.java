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
package com.sun.dtv.smartcard;

/**
 * 
 */
public abstract class CardTerminal
{
	/**
	 * 
	 * 
	 */
	public CardTerminal()
	{
		//TODO implement CardTerminal
	}

	/**
	 * Retrieves the slot number of this CardTerminal object, the framework
	 * is responsible to assign each card terminal build in or connected to
	 * the device a slot number.
	 *
	 * 
	 * 
	 * @return the slot number that is assigned to this CardTerminal
	 */
	public abstract int getSlotNumber();

	/**
	 * Adds a new CardTerminalListener to the CardTerminal.
	 *
	 * 
	 * @param listener - will receive events from this CardTerminal object
	 * @see removeCardTerminalListener(com.sun.dtv.smartcard.CardTerminalListener)
	 */
	public abstract void addCardTerminalListener( CardTerminalListener listener);

	/**
	 * Removes a CardTerminalListener to the CardTerminal.
	 *
	 * 
	 * @param listener - be removed from the CardTerminal
	 * @see addCardTerminalListener(com.sun.dtv.smartcard.CardTerminalListener)
	 */
	public abstract void removeCardTerminalListener( CardTerminalListener listener);

	/**
	 * Creates an APDUConnection for the given CardTerminal with the given
	 * slot number. This is a specific APDUConnections that lets all kind of
	 * APDU commands passthrough. No checking is performed on the framework
	 * level like it is described in JSR 177 for this connection.
	 *
	 * 
	 * @param slotNumber - The identifier of the slot.
	 * @return A connection to send Command APDUs to the card and receive Response APDUs from the card.
	 */
	public abstract PassThroughAPDUConnection getConnection(int slotNumber);

}
