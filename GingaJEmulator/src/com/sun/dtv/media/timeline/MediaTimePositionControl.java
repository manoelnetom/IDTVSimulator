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
package com.sun.dtv.media.timeline;

import javax.media.Control;
import javax.media.Time;

/**
 * 
 */
public interface MediaTimePositionControl extends Control
{


	/**
	 * Sets the media time as closely as possible to the requested media time.
	 * Allows to jump back and forth in the media. Invocation of this method must
	 * raise a MediaTimeChangeEvent when the action is performed.
	 *
	 * 
	 * @param mediaTime - the desired mediaTime.
	 * @return the position in time that was actually set.
	 * @see getMediaTimePosition()
	 */
	public Time setMediaTimePosition( Time mediaTime);

	/**
	 * Returns the mediaTime of the media currently handled by the Player.
	 *
	 * 
	 * 
	 * @return the current mediaTime of the media.
	 * @see setMediaTimePosition(javax.media.Time)
	 */
	public Time getMediaTimePosition();

	/**
	 * Adds a <code>mediaTimeListener</code> to the Control, the player will raise an event with
	 * if the media reaches the given <code>Time</code>, the event will have the ID that is given back
	 * during the registration process for easier identification and management on the application level
	 * The system is responsible to raise the event as close as possible to the given <code>Time</code>.
	 *
	 * 
	 * @param listener - the listener to be added.
	 * @param time - when the player should raise the event.
	 * @return ID an identifier provided by the Control to identify the event.
	 * @see removeMediaTimeListener(com.sun.dtv.media.timeline.MediaTimeListener)
	 */
	public int addMediaTimeListener( MediaTimeListener listener, Time time);

	/**
	 * Removes a <code>MediaTimeListener</code> from a player and all the associated
	 * data (times and IDs) from the system.
	 *
	 * 
	 * @param listener - to be removed from the system.
	 * @see addMediaTimeListener(com.sun.dtv.media.timeline.MediaTimeListener, javax.media.Time)
	 */
	public void removeMediaTimeListener( MediaTimeListener listener);

	/**
	 * Removes the given <code>ID</code> from the system. Must be an ID that was
	 * given as return value from the <code>addMediaTimeListener</code>
	 *
	 * 
	 * @param ID - of the event that has to removed from the system.
	 * @return true is remove was succesful, false if the ID is not present in the system.
	 */
	public boolean removeMediaTimeEventID(int ID);

}
