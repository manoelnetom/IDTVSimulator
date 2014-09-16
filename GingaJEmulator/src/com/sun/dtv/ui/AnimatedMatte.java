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

import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.ui.Animated;
import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.geom.Dimension;
/**
 * 
 */
public class AnimatedMatte extends Object implements Matte, Animation, Animated
{
	private float[] opacity;
	private int[] opacityAsInts;
	private Image[] imageOpacity;
	private int position;
	private int repetitionMode;
	private int delay;
	private int animationMode;

	/**
	 * 
	 * 
	 */
	public AnimatedMatte()
	{
		//TODO implement AnimatedMatte
	}

	/**
	 * Sets opacity values for this matte, causing an animation of changing
	 * opacity, while the opacity at a certain point of time is unitary for
	 * all pixels.
	 * A call to this method overwrites a previously set animation of images
	 * or unitary opacity values.
	 * This method has exactly the same aim as <A HREF="../../../../com/sun/dtv/ui/AnimatedMatte.html#setOpacity(float[])"><CODE>setOpacity(float[])</CODE></A>, where an integer value
	 * <code>i</code> in the array passed to this method has the same effect as
	 * a float value <code>(float)(i/255)</code> at the same position in the
	 * array passed to <A HREF="../../../../com/sun/dtv/ui/AnimatedMatte.html#setOpacity(float[])"><CODE>setOpacity(float[])</CODE></A>.
	 *
	 * 
	 * @param opacities - the values for the changing opacity during the animation of this matte. The array elements should be integer values between 0 and 255, where 0 stands for full transparency, and 255 for full opacity. Values below 0 will be interpreted as 0 (fully transparent), values greater 255 as 255 (fully opaque). If the parameter is null, previously opacity values are removed.
	 * @throws IllegalArgumentException - if the length of the opacity array is 0.
	 * @see getOpacityAsInts()
	 */
	public void setOpacity(int[] opacities)
	{
		this.opacityAsInts = opacities;
	}

	/**
	 * Sets opacity values for this matte, causing an animation of changing
	 * opacity, while the opacity at a certain point of time is unitary for
	 * all pixels.
	 * A call to this method overwrites a previously set animation of images
	 * or unitary opacity values.
	 * This method has exactly the same aim as <A HREF="../../../../com/sun/dtv/ui/AnimatedMatte.html#setOpacity(int[])"><CODE>setOpacity(int[])</CODE></A>, where a floating point
	 * value <code>f</code> in the array passed to this method has the same
	 * effect as an integer value <code>(int)(f * 255)</code> at the same
	 * position in the array passed to <A HREF="../../../../com/sun/dtv/ui/AnimatedMatte.html#setOpacity(int[])"><CODE>setOpacity(int[])</CODE></A>.
	 *
	 * 
	 * @param opacities - the values for the changing opacity during the animation of this matte. The array elements should be a floating point values between 0.0f and 1.0f, where 0.0f stands for full transparency, and 1.0f for full opacity. Values below 0.0f will be interpreted as 0.0f (fully transparent), values greater 1.0f as 1.0f (fully opaque). If the parameter is null, previously opacity values are removed.
	 * @throws IllegalArgumentException - if the length of the opacity array is 0.
	 * @see getOpacity()
	 */
	public void setOpacity(float[] opacities)
	{
		this.opacity = opacities;
	}

	/**
	 * Sets the opacity of the matte to a row of images causing an animation
	 * of changing opacity images. The provided images may be smaller than
	 * the affected component, in this case the remaining area is shown as it
	 * would be covered by a <A HREF="../../../../com/sun/dtv/ui/StaticMatte.html" title="class in com.sun.dtv.ui"><CODE>StaticMatte</CODE></A>
	 * with opacity 1.0. By default the images matte is aligned with the top
	 * left corner of the components area. This can be changed (for any image
	 * separately) using the <A HREF="../../../../com/sun/dtv/ui/AnimatedMatte.html#setOffset(int, com.sun.dtv.lwuit.geom.Dimension)"><CODE>setOffset()</CODE></A> method.
	 * A call to this method overwrites a previously set animation of images
	 * or unitary opacity values.
	 *
	 * 
	 * @param images - the values for the opacity, which have to be an array of  images, containing pixel values between 0.0 and 1.0.  0.0 stands for full transparency, and 1.0 for full opacity. Values below 0.0 will be interpreted as 0.0 (fully transparent), values greater 1.0 as 1.0 (fully opaque). Note that if an image is not available while the animation is running (because the array element is null or the image is still loading) this image will be skipped.
	 * @see getOpacity()
	 */
	public void setOpacity( Image[] images)
	{
		
	}

	/**
	 * Gets the opacity values for the animation of this matte, if a row of
	 * unitary opacity values is assigned to this matte at the moment,
	 * otherwise null.
	 *
	 * 
	 * 
	 * @return the opacity values for the animation of this matte. The array elements are always floating point values in the range between 0.0 and 1.0, where 0.0 stands for full transparency, and 1.0 for full opacity. If null is returned, no unitary opacity values are assigned to this matte at the moment, but rather a row of opacity images.
	 * @see setOpacity(float[])
	 */
	public float[] getOpacity()
	{
		return this.opacity;
	}

	/**
	 * Gets the opacity values for the animation of this matte, if a row of
	 * unitary opacity values is assigned to this matte at the moment,
	 * otherwise null.
	 *
	 * 
	 * 
	 * @return the opacity values for the animation of this matte. The array elements are always integer values in the range between 0 and 255, where 0 stands for full transparency, and 255 for full opacity. If null is returned, no unitary opacity values are assigned to this matte at the moment, but rather a row of opacity images.
	 * @see setOpacity(int[])
	 */
	public int[] getOpacityAsInts()
	{
		return this.opacityAsInts;
	}

	/**
	 * Gets the opacity value images for this matte, if an image animation
	 * has been previously assigned to this matte, null otherwise.
	 *
	 * 
	 * 
	 * @return the opacity values for this matte in form of an image array,  where each image contains pixel values between 0.0 and 1.0.  0.0 stands for full transparency, and 1.0 for full opacity. If null is returned, there is no opacity image animation assigned to this matte at the moment.
	 * @see setOpacity(com.sun.dtv.lwuit.Image[])
	 */
	public Image[] getImageOpacity()
	{
		return this.imageOpacity;
	}

	/**
	 * Determines the offset for the opacity image to be assigned with this
	 * matte for a certain position in the animation. While per default the
	 * images matte is aligned with the top left corner of the components
	 * area, this can be changed using this method - separately for any
	 * position of the animation.
	 * If currently an animation based on unitary opacity values is assigned
	 * to this matte, a call to this method has no effect.
	 *
	 * 
	 * @param index - index of the content image array for which the offset  should be set
	 * @param dimension - a Dimension object determining the offset toward the top left corner of the component's area. if the parameter is null, no offset will be determined, and a previously assigned offset will be removed.
	 * @see getOffset(int)
	 */
	public void setOffset(int index, Dimension dimension)
	{
		//TODO implement setOffset
	}

	/**
	 * Obtains the offset the image matte has relatively to the top left
	 * corner of the component's area for a certain position within the
	 * animation. If currently an animation based ion unitary opacity values
	 * is assigned to this matte, <code>null</code> is returned.
	 *
	 * 
	 * @param index - index of the animation position the offset value should be obtained for
	 * @return the offset for this animation position in form of a Dimension object or null (in this case there is no offset for this animation position, or the currently valid animation is based on unitary opacity values)
	 * @see setOffset(int, com.sun.dtv.lwuit.geom.Dimension)
	 */
	public Dimension getOffset(int index)
	{
		return null;
		//TODO implement getOffset
	}

	/**
	 * Starts the matte's animation. Please consider that an animation is in
	 * the stopped mode per default, so it has to be started explicitly once
	 * it has been created.
	 * In case the animation is already running, a call to <code>start()</code>
	 * causes a restart of the animation.
	 *
	 * 
	 * @see start in interface Animated
	 */
	public void start()
	{
		//TODO implement start
	}

	/**
	 * Stops the matte's animation. In case the animation is already stopped,
	 * the call to <code>stop()</code> has no effect.
	 *
	 * 
	 * @see stop in interface Animated
	 */
	public void stop()
	{
		//TODO implement stop
	}

	/**
	 * Obtains the running mode of the matte's animation. Returns true if the
	 * animation is running, false otherwise.
	 *
	 * 
	 * @return true if the animation is running, false otherwise
	 * @see isRunning in interface Animated
	 */
	public boolean isRunning()
	{
		return false;
		//TODO implement isRunning
	}

	/**
	 * Forces the matte's animation to jump to the position indicated by the
	 * parameter. An animation can be considered as a row of images, the
	 * parameter of this method provides the index within this row.
	 * If the animation is stopped, a call to this method causes the animation
	 * to show the image at the indicated position, but not to start.
	 * If the animation is running, a call to this method causes the animation
	 * to jump to the image at the indicated position and continue running
	 * immediately.
	 *
	 * 
	 * @param index - the index in the row of images building the animation to which the animation is forced to jump
	 * @see jumpTo in interface Animated
	 */
	public void jumpTo(int index)
	{
		//TODO implement jumpTo
	}

	/**
	 * Obtains the current position the matte's animation is at the point of
	 * time of the method call. An animation can be considered as a row of
	 * images, this method provides the index within this row.
	 *
	 * 
	 * @return the current position of the image within the row of images building this animation
	 * @see getPosition in interface Animated
	 */
	public int getPosition()
	{
		return this.position;
	}

	/**
	 * Determines how often the matte's animation is repeated once it has been
	 * started.
	 *
	 * 
	 * @param repetitions - number of repetitions to be determined. This can be any number larger than zero or LOOP alternatively. LOOP means that the animation runs in an endless loop until the stop() method has been called.
	 * @see setRepetitionMode in interface Animated
	 * @see getRepetitionMode()
	 */
	public void setRepetitionMode(int repetitions)
	{
		this.repetitionMode = repetitions;
	}

	/**
	 * Returns the repetition mode of the matte's  animation in form of a
	 * number. The number reads how many repetitions have been determined for
	 * this animation.
	 *
	 * 
	 * @return number of repetitions determined for this animation or LOOP which means the animation runs in an endless loop until the stop() method has been called.
	 * @see getRepetitionMode in interface Animated
	 * @see setRepetitionMode(int)
	 */
	public int getRepetitionMode()
	{
		return this.repetitionMode;
	}

	/**
	 * Determines the delay after every image when running the matte's
	 * animation.
	 *
	 * 
	 * @param delay - delay in milliseconds
	 * @see setDelay in interface Animated
	 * @see getDelay()
	 */
	public void setDelay(int delay)
	{
		this.delay = delay;
	}

	/**
	 * Obtains the delay after every image for the matte's animation when
	 * running.
	 *
	 * 
	 * @return the delay in milliseconds
	 * @see getDelay in interface Animated
	 * @see setDelay(int)
	 */
	public int getDelay()
	{
		return this.delay;
	}

	/**
	 * Sets the animation mode for the matte's animation. The animation mode
	 * determines how the animation is running: always forwards, or forwards
	 * and backwards alternating.
	 *
	 * 
	 * @param mode - the animation mode. Possible values are: REPEATING and ALTERNATING.
	 * @see setAnimationMode in interface Animated
	 * @see getAnimationMode()
	 */
	public void setAnimationMode(int mode)
	{
		this.animationMode = mode;
	}

	/**
	 * Obtains the animation mode for the matte's animation. The animation
	 * mode determines how the animation is running: always forwards, or
	 * forwards and backwards alternating.
	 *
	 * 
	 * @return the animation mode. Possible values are: REPEATING and ALTERNATING.
	 * @see getAnimationMode in interface Animated
	 * @see setAnimationMode(int)
	 */
	public int getAnimationMode()
	{
		return this.animationMode;
	}

	/**
	 * Obtains whether the currently assigned animation to this matte is based
	 * on a row of images or not.
	 *
	 * 
	 * 
	 * @return true is current animation is based on a row of opacity images, false if it is based on a row of unitary opacity values
	 */
	public boolean hasImageAnimation()
	{
		return false;
		//TODO implement hasImageAnimation
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/lwuit/animations/Animation.html#animate()">Animation</A></CODE></B></DD>
	 * Allows the animation to reduce "repaint" calls when it returns false. It is
	 * called once for every frame.
	 *
	 * 
	 * @return true if a repaint is desired or false if no repaint is necessary
	 * @see animate in interface Animation
	 */
	public boolean animate()
	{
		return false;
		//TODO implement animate
	}

	/**
	 * Draws the animation, within a component the standard paint method would be
	 * invoked since it bares the exact same signature.
	 *
	 * 
	 * @see paint in interface Animation
	 */
	public void paint( Graphics g)
	{
		//TODO implement paint
	}

}
