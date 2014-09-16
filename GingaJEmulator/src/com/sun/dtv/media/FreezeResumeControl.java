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
package com.sun.dtv.media;

import javax.media.Control;

/**
 * 
 */
public interface FreezeResumeControl extends Control
{
	/**
	 * Invocation of this method freezes the decoding of the media stream as soon
	 * as possible and leaves the last decoded frame visible to the end-user.
	 * The decoding of the audio is stopped. The actual timebase of the underlying
	 * media is however not altered.
	 *
	 * 
	 * 
	 * @throws FreezeResumeException - if the freeze is unsuccessful
	 */
	void freeze() throws FreezeResumeException;

	/**
	 * Invocation of this method resumes the decoding of the media stream following
	 * a freeze operation. If the player is started and if decoding of the media
	 * stream was not previously frozen then calls to this method must have no effect.
	 * If the media of the Player is a broadcast stream the presentation will start
	 * at the respective mediaTime when the resume method was invoked. That means
	 * some part of the stream will not be displayed.
	 *
	 * 
	 * 
	 * @throws FreezeResumeException - if the resume is unsuccessful
	 */
	void resume() throws FreezeResumeException;

	/**
	 * Add a freeze,resume listener interface.
	 *
	 * 
	 * @param freezeResumeListener - the interface that will receive all the freeze, resume events.
	 * @see removeFreezeResumeListener(com.sun.dtv.media.FreezeResumeListener)
	 */
	void addFreezeResumeListener( FreezeResumeListener freezeResumeListener);

	/**
	 * Removes a freeze,resume listener interface.
	 *
	 * 
	 * @param freezeResumeListener - the listener to remove.
	 * @see addFreezeResumeListener(com.sun.dtv.media.FreezeResumeListener)
	 */
	void removeFreezeResumeListener( FreezeResumeListener freezeResumeListener);

}
