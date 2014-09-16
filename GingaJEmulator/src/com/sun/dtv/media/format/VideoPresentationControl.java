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
package com.sun.dtv.media.format;

import javax.media.Control;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;

/**
 * 
 */
public interface VideoPresentationControl extends Control
{

	/**
	 * Returns the rectangle that covers the actual video without any areas used
	 * letterboxing or pillarboxing are not part of the Rectangle. The Rectangle
	 * takes the actual scaling into account.
	 *
	 * 
	 * 
	 * @return rectangle of the actual video
	 */
	Rectangle getActiveVideoRectangle();

	/**
	 * Returns the rectangle of the video that is used on the screen without any areas used
	 * letterboxing or pillarboxing are not part of the Rectangle. The Rectangle
	 * takes the actual scaling into account.
	 *
	 * 
	 * 
	 * @return rectangle of the video on the screen
	 */
	Rectangle getActiveVideoOnScreenRectangle();

	/**
	 * This method returns the entire rectangle used for the video in the
	 * broadcast stream. Any scaling is taking into account.
	 *
	 * 
	 * 
	 * @return rectangle of the video on the screen with letterbox and  pillarbox included.
	 */
	Rectangle getTotalVideoRectangle();

	/**
	 * Returns the dimension of the video before any scaling.
	 *
	 * 
	 * 
	 * @return the dimension of the decoded video
	 */
	Dimension getInputSize();

	/**
	 * Returns the dimension of the size of the decoded video as it
	 * is presented to the user.
	 *
	 * 
	 * 
	 * @return the dimension of the video how it is presented to the user
	 */
	Dimension getDecodedVideoSize();

	/**
	 * This method allows the application to query the actual dimension of the
	 * presented video, effects like clipping and scaling are taking into
	 * account.
	 *
	 * 
	 * 
	 * @return the dimension of the presented video
	 */
	Dimension getActualVideoSize();

	/**
	 * Returns the values of the supported discrete horizontal scaling factors.
	 *
	 * 
	 * 
	 * @return array of float values giving the horizontal scaling factors, including the factor 1.
	 */
	float[] getHorizontalScalingFactors();

	/**
	 * Returns the values of the supported discrete vertical scaling factors.
	 *
	 * 
	 * 
	 * @return array of float values giving the vertical scaling factors, including the factor 1.
	 */
	float[] getVerticalScalingFactors();

	/**
	 * Add a listener to the VideoFormatControl.
	 *
	 * 
	 * @param listener - The object implementing the listener interface to be added to the Control.
	 * @see removeVideoPresentationListener(com.sun.dtv.media.format.VideoPresentationListener)
	 */
	void addVideoPresentationListener( VideoPresentationListener listener);

	/**
	 * Removes a VideoFormatListener object.
	 *
	 * 
	 * @param listener - The object to be removed from the Control.
	 * @see addVideoPresentationListener(com.sun.dtv.media.format.VideoPresentationListener)
	 */
	void removeVideoPresentationListener( VideoPresentationListener listener);

}
