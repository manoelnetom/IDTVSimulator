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

import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.geom.Dimension;

/**
 * 
 */
public class StaticMatte extends Object implements Matte
{
	//following variables are implicitely defined by getter- or setter-methods:
	private float opacity;
	private int opacityAsInt;
	private Image imageOpacity;
	private Dimension offset;

	/**
	 * 
	 * 
	 */
	public StaticMatte()
	{
		//TODO implement StaticMatte
	}

	/**
	 * Sets the opacity of the matte to a single value, valid for all pixels.
	 * A previously assigned opacity value or opacity image will be overwritten
	 * by the unitary opacity caused by a call to this method.
	 * This method has exactly the same aim as <A HREF="../../../../com/sun/dtv/ui/StaticMatte.html#setOpacity(float)"><CODE>setOpacity(float)</CODE></A>, where a call to
	 * <code>setOpacity(i)</code> has the same effect as a call to
	 * <code>setOpacity((float)(i/255))</code>.
	 *
	 * 
	 * @param opacity - the value for the opacity, which has to be an integer  value between 0 and 255, where 0 stands for full transparency, and 255 for full opacity. Values below 0 will be interpreted as 0 (fully transparent), values greater 255 as 255 (fully opaque).
	 * @see getOpacityAsInt(), setOpacity(float)
	 */
	public void setOpacity(int opacity)
	{
		this.opacity = opacity;
	}

	/**
	 * Sets the opacity of the matte to a single value, valid for all pixels.
	 * A previously assigned opacity value or opacity image will be overwritten
	 * by the unitary opacity caused by a call to this method.
	 * This method has exactly the same aim as <A HREF="../../../../com/sun/dtv/ui/StaticMatte.html#setOpacity(int)"><CODE>setOpacity(int)</CODE></A>, where a call to
	 * <code>setOpacity(f)</code> has the same effect as a call to
	 * <code>setOpacity((int)(f * 255))</code>.
	 *
	 * 
	 * @param opacity - the value for the opacity, which has to be a floating point value between 0.0 and 1.0, where 0.0 stands for full transparency, and 1.0 for full opacity. Values below 0.0 will be interpreted as 0.0 (fully transparent), values greater 1.0 as 1.0 (fully opaque).
	 * @see getOpacity(), setOpacity(int)
	 */
	public void setOpacity(float opacity)
	{
		this.opacity = opacity;
	}

	/**
	 * Sets the opacity of the matte. The provided image may be smaller than the
	 * affected component, in this case the remaining area is shown as it would
	 * be covered by a matte with unitary opacity of 1.0 (even if this image
	 * overwrites a previously set unitary opacity with a different value).
	 * By default the images matte is aligned with the top left corner of the
	 * components area. This can be changed using the <A HREF="../../../../com/sun/dtv/ui/StaticMatte.html#setOffset(com.sun.dtv.lwuit.geom.Dimension)"><CODE>setOffset()</CODE></A> method.
	 * A previously assigned opacity value or opacity image will be overwritten
	 * by the opacity image provided by a call to this method.
	 *
	 * 
	 * @param image - the value for the opacity, which has to be an image,  containing pixel values between 0.0 and 1.0.  0.0 stands for full transparency, and 1.0 for full opacity. Values below 0.0 will be interpreted as 0.0 (fully transparent), values greater 1.0 as 1.0 (fully opaque).
	 * @see getImageOpacity(), getOpacity()
	 */
	public void setOpacity( Image image)
	{
		
	}

	/**
	 * Gets the opacity value for this matte. If the matte has an opacity image
	 * assigned, always 1.0 is returned. In order to find out whether this
	 * really means an unitary opacity of 1.0, or rather an image opacity
	 * <A HREF="../../../../com/sun/dtv/ui/StaticMatte.html#hasOpacityImage()"><CODE>hasOpacityImage()</CODE></A> has
	 * to be called.
	 *
	 * 
	 * 
	 * @return the opacity value for this matte. This is always a floating point value in the range between 0.0 and 1.0, where 0.0 stands for full transparency, and 1.0 for full opacity. 1.0 could also mean that there is an opacity image assigned to this matte.
	 * @see setOpacity(float)
	 */
	public float getOpacity()
	{
		return this.opacity;
	}

	/**
	 * Gets the opacity value for this matte as integer. If the matte has an
	 * opacity image assigned, always 255 is returned. In order to find out
	 * whether this really means an unitary opacity of 255, or rather an image
	 * opacity <A HREF="../../../../com/sun/dtv/ui/StaticMatte.html#hasOpacityImage()"><CODE>hasOpacityImage()</CODE></A>
	 * has to be called.
	 *
	 * 
	 * 
	 * @return the opacity value for this matte. This is always an integer value in the range between 0 and 255, where 0 stands for full transparency, and 255 for full opacity. 255 could also mean that there is an opacity image assigned to this matte.
	 * @see setOpacity(int), getOpacity()
	 */
	public int getOpacityAsInt()
	{
		return this.opacityAsInt;
	}

	/**
	 * Gets the opacity value image for this matte.
	 *
	 * 
	 * 
	 * @return the opacity value for this matte in form of an image, containing pixel values between 0.0 and 1.0.  0.0 stands for full transparency, and 1.0 for full opacity. If null is returned, there is an unitary opacity value assigned to the matte at the moment.
	 * @see setOpacity(com.sun.dtv.lwuit.Image)
	 */
	public Image getImageOpacity()
	{
		return this.imageOpacity;
	}

	/**
	 * Determines the offset for the opacity image to be assigned with this
	 * matte. While per default the images matte is aligned with the top left
	 * corner of the components area, this can be changed using this method.
	 * If there is not an opacity image assigned to this matte, a call to this
	 * method has no effect.
	 *
	 * 
	 * @param d - a Dimension object determining the offset toward the top left corner of the component's area  if the parameter is null, no offset will be determined, and a previously assigned offset will be removed
	 * @see getOffset()
	 */
	public void setOffset( Dimension d)
	{
		this.offset = d;
	}

	/**
	 * Obtains the offset the image matte has relatively to the top left corner
	 * of the component's area.
	 * In case that there is no opacity image assigned to this matte,
	 * <code>null</code> is returned.
	 *
	 * 
	 * 
	 * @return the offset in form of a Dimension object or null (in this case there is either no offset or it is an unitary opacity value assigned to the matte rather than an image)
	 * @see setOffset(com.sun.dtv.lwuit.geom.Dimension)
	 */
	public Dimension getOffset()
	{
		return this.offset;
	}

	/**
	 * Obtains whether an opacity image is assigned to this matte. Returns
	 * true if this is the case, false otherwise.
	 *
	 * 
	 * 
	 * @return true if an opacity image is assigned to this matte, false if an unitary opacity value is assigned instead.
	 */
	public boolean hasOpacityImage()
	{
		return false;
		//TODO implement hasOpacityImage
	}

}
