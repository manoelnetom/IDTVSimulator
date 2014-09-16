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

import com.sun.dtv.lwuit.geom.Point;
import com.sun.dtv.lwuit.geom.Rectangle;

/**
 * 
 */
public class Transformation extends Object
{
	private Rectangle region;
	private float horizontalScalingFactor;
	private float verticalScalingFactor;
	private Point position;

	/**
	 * Creates a new instance of a Transformation for a video stream.
	 *
	 * 
	 * @param clippingRectangle - defines the clipping rectangle for the video
	 * @param horizontalScalingFactor - the horizontal scaling factor for this video
	 * @param verticalScalingFactor - the vertical scaling factor for this video
	 * @param position - the position on the screen for the video
	 */
	public Transformation( Rectangle clippingRectangle, float horizontalScalingFactor, float verticalScalingFactor, Point position)
	{
		//TODO implement Transformation
	}

	/**
	 * This method returns a rectangle giving the display region of the video.
	 * If clipping is not supported, the returned rectangle will have the
	 * same size as the displayed video.
	 *
	 * 
	 * 
	 * @return rectangle of the decoded video that will be displayed
	 */
	public Rectangle getRegion()
	{
		return this.region;
	}

	/**
	 * Returns the horizontal scaling factor.
	 *
	 * 
	 * 
	 * @return the horizontal scaling factor
	 */
	public float getHorizontalScalingFactor()
	{
		return this.horizontalScalingFactor;
	}

	/**
	 * Returns the vertical scaling factor.
	 *
	 * 
	 * 
	 * @return the vertical scaling factor
	 */
	public float getVerticalScalingFactor()
	{
		return this.verticalScalingFactor;
	}

	/**
	 * Returns the position of the video.
	 *
	 * 
	 * 
	 * @return the location of the video on the screen coordinate space.
	 */
	public Point getPosition()
	{
		return this.position;
	}

}
