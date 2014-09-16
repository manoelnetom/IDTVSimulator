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

import com.sun.dtv.lwuit.geom.Dimension;

/**
 * 
 */
public class Capabilities extends Object
{
	protected Dimension[] supportedScreenResolutions;
	protected Dimension[] supportedPlaneAspectRatios;
	protected Dimension[] supportedPixelAspectRatios;
	protected int bitsPerPixel;
	protected int colorCodingModel;

	/**
	 * Provide all screen sizes supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 *
	 * 
	 * 
	 * @return an array of all supported screen sizes
	 */
	public Dimension[] getSupportedScreenResolutions()
	{
		return this.supportedScreenResolutions;
	}

	/**
	 * Provide all plane aspect ratios supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 *
	 * 
	 * 
	 * @return an array of all supported screen aspect ratios
	 */
	public Dimension[] getSupportedPlaneAspectRatios()
	{
		return this.supportedPlaneAspectRatios;
	}

	/**
	 * Provide all pixel aspect ratios supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 *
	 * 
	 * 
	 * @return an array of all supported pixel aspect ratios
	 */
	public Dimension[] getSupportedPixelAspectRatios()
	{
		return this.supportedPixelAspectRatios;
	}

	/**
	 * Indicate whether alpha blending is supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value is also true if the setup does not support real
	 * (hardware supported) alpha blending, but uses a so-called switching
	 * plane. This switching plane, offered by some implementations in order
	 * to spare hardware-supported alpha blending for video planes, is an
	 * additional plane with one bit per pixel, just indicating for every
	 * pixel whether this plane or the underlying plane should be visible.
	 *
	 * To indicate whether real (hardware supported) alpha blending is
	 * available, the <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isRealAlphaBlendingSupported()"><CODE>isRealAlphaBlendingSupported()</CODE></A>
	 * method has to be called.
	 *
	 * 
	 * 
	 * @return true if alpha blending (realized by hardware support or by use of a switching plane) is supported, false otherwise
	 * @see isRealAlphaBlendingSupported()
	 */
	public boolean isAlphaBlendingSupported()
	{
		return false;
		//TODO implement isAlphaBlendingSupported
	}

	/**
	 * Indicate whether real alpha blending is supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value is only true if the plane supports real
	 * (hardware supported) alpha blending. If alpha blending is just
	 * simulated by use of a so-called switching plane, the return value is
	 * false.
	 *
	 * 
	 * 
	 * @return true if real alpha blending (realized by hardware support, not by  use of a switching plane) is supported, false otherwise
	 * @see isAlphaBlendingSupported()
	 */
	public boolean isRealAlphaBlendingSupported()
	{
		return false;
		//TODO implement isRealAlphaBlendingSupported
	}

	/**
	 * Indicate whether the rendering of images is supported for the plane
	 * associated with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value could be <code>true</code> for setups suitable for
	 * graphics or video still planes, but <code>false</code> for a setup
	 * suitable for a subtitle plane.
	 *
	 * 
	 * 
	 * @return true if image rendering is supported,  false otherwise
	 */
	public boolean isImageRenderingSupported()
	{
		return false;
		//TODO implement isImageRenderingSupported
	}

	/**
	 * Indicate whether the rendering of JPEG images is supported for the plane
	 * associated with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value can only be <code>true</code> if the return value of
	 * <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"><CODE>isImageRenderingSupported()</CODE></A>, called
	 * for the same object, is also <code>true</code>.
	 *
	 * 
	 * 
	 * @return true if JPEG image rendering is supported,  false otherwise
	 */
	public boolean isJPEGRenderingSupported()
	{
		return false;
		//TODO implement isJPEGRenderingSupported
	}

	/**
	 * Indicate whether the rendering of PNG images is supported for the plane
	 * associated with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value can only be <code>true</code> if the return value of
	 * <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"><CODE>isImageRenderingSupported()</CODE></A>, called
	 * for the same object, is also <code>true</code>.
	 *
	 * 
	 * 
	 * @return true if PNG image rendering is supported,  false otherwise
	 */
	public boolean isPNGRenderingSupported()
	{
		return false;
		//TODO implement isPNGRenderingSupported
	}

	/**
	 * Indicate whether the rendering of GIF images is supported for the plane
	 * associated with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value can only be <code>true</code> if the return value of
	 * <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"><CODE>isImageRenderingSupported()</CODE></A>, called
	 * for the same object, is also <code>true</code>.
	 *
	 * 
	 * 
	 * @return true if GIF image rendering is supported,  false otherwise
	 */
	public boolean isGIFRenderingSupported()
	{
		return false;
		//TODO implement isGIFRenderingSupported
	}

	/**
	 * Indicate whether the rendering of videos is supported for the plane
	 * associated with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The return value could be <code>true</code> for setups suitable for
	 * video planes, but <code>false</code> for a setup suitable for a background
	 * plane.
	 *
	 * 
	 * 
	 * @return true if video rendering is supported,  false otherwise
	 */
	public boolean isVideoRenderingSupported()
	{
		return false;
		//TODO implement isVideoRenderingSupported
	}

	/**
	 * Indicates how many bits are used for coding of the pixels if images are
	 * rendered on the plane associated with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>.
	 * The call to this method makes only sense if image rendering is supported
	 * by this kind of plane, i.e. if the return value of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"><CODE>isImageRenderingSupported()</CODE></A> is
	 * <code>true</code> for this <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> object,
	 * otherwise an exception is thrown.
	 *
	 * 
	 * 
	 * @return the number of bits used for coding of one single pixel
	 * @throws SetupException - if this method is called for an Capabilities object for which isImageRenderingSupported() returns false
	 */
	public int getBitsPerPixel() throws SetupException
	{
		return this.bitsPerPixel;
	}

	/**
	 * Indicate the color coding model used by the plane associated with this
	 * instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>. The return value is
	 * implementation dependent. Implementations are advised to define an
	 * enumeration containing values for all possible supported color coding
	 * models (e.g. YUV444, YUV422, YUV420, ARGB888 etc.)
	 * The call to this method makes only sense if at least one of the following
	 * methods, called for the same <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> object,
	 * returns <code>true</code>:
	 * <ul>
	 * <li><A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"><CODE>isImageRenderingSupported()</CODE></A></li>
	 * <li><A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isWidgetRenderingSupported()"><CODE>isWidgetRenderingSupported()</CODE></A></li>
	 * <li><A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isGraphicsRenderingSupported()"><CODE>isGraphicsRenderingSupported()</CODE></A></li>
	 * </ul>.
	 * Otherwise an exception is thrown.
	 *
	 * 
	 * 
	 * @return an implementation dependent value indicating which color coding  model is used
	 * @throws SetupException - if this method is called for a Capabilities object, for which all methods mentioned above return false
	 */
	public int getColorCodingModel() throws SetupException
	{
		return this.colorCodingModel;
	}

	/**
	 * Indicate whether widget rendering is supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>. A plane,
	 * for which a <A HREF="../../../../com/sun/dtv/ui/DTVContainer.html" title="class in com.sun.dtv.ui"><CODE>DTVContainer</CODE></A> will be requested by
	 * calling one of the <A HREF="../../../../com/sun/dtv/ui/DTVContainer.html#getDTVContainer(com.sun.dtv.ui.Plane)"><CODE>DTVContainer.getDTVContainer(com.sun.dtv.ui.Plane)</CODE></A>
	 * methods, must be associated with a <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>
	 * object for which this or the <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isGraphicsRenderingSupported()"><CODE>isGraphicsRenderingSupported()</CODE></A> method returns
	 * <code>true</code>.
	 *
	 * 
	 * 
	 * @return true if widget rendering is supported,  false otherwise
	 */
	public boolean isWidgetRenderingSupported()
	{
		return false;
		//TODO implement isWidgetRenderingSupported
	}

	/**
	 * Indicate whether graphics rendering is supported by the plane associated
	 * with this instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>. A plane,
	 * for which a <A HREF="../../../../com/sun/dtv/ui/DTVContainer.html" title="class in com.sun.dtv.ui"><CODE>DTVContainer</CODE></A> will be requested by
	 * calling one of the <A HREF="../../../../com/sun/dtv/ui/DTVContainer.html#getDTVContainer(com.sun.dtv.ui.Plane)"><CODE>DTVContainer.getDTVContainer(com.sun.dtv.ui.Plane)</CODE></A>
	 * methods, must be associated with a <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A>
	 * object for which this or the <A HREF="../../../../com/sun/dtv/ui/Capabilities.html#isWidgetRenderingSupported()"><CODE>isWidgetRenderingSupported()</CODE></A> method returns
	 * <code>true</code>.
	 *
	 * 
	 * 
	 * @return true if graphics rendering is supported,  false otherwise
	 */
	public boolean isGraphicsRenderingSupported()
	{
		return false;
		//TODO implement isGraphicsRenderingSupported
	}

}
