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

import java.util.EventObject;

/**
 * 
 */
public abstract class CardTerminalEvent extends EventObject
{
	/**
	 * Corresponds to the event when a card is removed.
	 *
	 *
	 * 
	 */
	public static final int CARD_REMOVED = 1;

	/**
	 * Corresponds to the event when a card has been inserted.
	 *
	 *
	 * 
	 */
	public static final int CARD_INSERTED = 2;

	/**
	 * Corresponds to the event when a card is mute.
	 *
	 *
	 * 
	 */
	public static final int CARD_MUTE = 3;

	/**
	 * Corresponds to the event when no card is present.
	 *
	 *
	 * 
	 */
	public static final int CARD_NOT_PRESENT = 4;

	/**
	 * Corresponds to the event when the card terminal is removed.
	 *
	 *
	 * 
	 * 
	 */
	public static final int CARD_TERMINAL_REMOVED = 5;

	//following variables are implicitely defined by getter- or setter-methods:
	private CardTerminal cardTerminal;
	private int eventType;

	/**
	 * Construct a CardTerminalEvent from the source.
	 *
	 * 
	 * @param sourceTerminal - the source of the event.
	 */
	public CardTerminalEvent( CardTerminal sourceTerminal)
	{
		super(sourceTerminal);
	}

	/**
	 * Returns the <code>CardTerminal</code> object where this event occurred.
	 *
	 * 
	 * 
	 * @return the CardTerminal object where the event happened.
	 */
	public CardTerminal getCardTerminal()
	{
		return this.cardTerminal;
	}

	/**
	 * Returns the type of event that happened at the physical card terminal.
	 * MUST be one the defined pre-defined integer values in this class.
	 *
	 * 
	 * 
	 * @return the event type
	 */
	public int getEventType()
	{
		return this.eventType;
	}

}
