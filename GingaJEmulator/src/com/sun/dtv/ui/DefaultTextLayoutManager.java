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

import java.awt.Insets;

import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.geom.Dimension;

/**
 * 
 * 
 */
public class DefaultTextLayoutManager extends Object implements TextLayoutManager
{
	/**
	 * 
	 * 
	 */
	public DefaultTextLayoutManager()
	{
		//TODO implement DefaultTextLayoutManager
	}

	/**
	 * Provides the minimum size required to render the provided text content
	 * in the specified <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A>.
	 * In this version of the method, it is assumed that the
	 * <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> parameter defines
	 * the borders by its bounds.
	 *
	 * 
	 * @param component - the ViewOnlyComponent to look at
	 * @param text - the provided text to be rendered
	 * @return the minimum size as com.sun.dtv.lwuit.geom.Dimension object
	 */
	public Dimension getMinimumSize( ViewOnlyComponent component, String text)
	{
		return null;
		//TODO implement getMinimumSize
	}

	/**
	 * Provides the minimum size required to render the provided text content
	 * in the specified <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A>.
	 *
	 * 
	 * @param component - the ViewOnlyComponent to look at
	 * @param text - the provided text to be rendered
	 * @param insets - the insets to define the area in which the text should be laid out
	 * @return the minimum size as com.sun.dtv.lwuit.geom.Dimension object
	 */
	public Dimension getMinimumSize( ViewOnlyComponent component, String text, Insets insets)
	{
		return null;
		//TODO implement getMinimumSize
	}

	/**
	 * Provides the maximum size required to render the provided text content
	 * in the specified <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A>.
	 * In this version of the method, it is assumed that the
	 * <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> parameter defines
	 * the borders by its bounds.
	 *
	 * 
	 * @param component - the ViewOnlyComponent to look at
	 * @param text - the provided text to be rendered
	 * @return the maximum size as com.sun.dtv.lwuit.geom.Dimension object
	 */
	public Dimension getMaximumSize( ViewOnlyComponent component, String text)
	{
		return null;
		//TODO implement getMaximumSize
	}

	/**
	 * Provides the maximum size required to render the provided text content
	 * in the specified <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A>.
	 *
	 * 
	 * @param component - the ViewOnlyComponent to look at
	 * @param text - the provided text to be rendered
	 * @param insets - the insets to define the area in which the text should be laid out
	 * @return the maximum size as com.sun.dtv.lwuit.geom.Dimension object
	 */
	public Dimension getMaximumSize( ViewOnlyComponent component, String text, Insets insets)
	{
		return null;
		//TODO implement getMaximumSize
	}

	/**
	 * Provides the preferred size required to render the provided text content
	 * in the specified <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A>.
	 * In this version of the method, it is assumed that the
	 * <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> parameter defines
	 * the borders by its bounds.
	 *
	 * 
	 * @param component - the ViewOnlyComponent to look at
	 * @param text - the provided text to be rendered
	 * @return the preferred size as com.sun.dtv.lwuit.Dimension object
	 */
	public Dimension getPreferredSize( ViewOnlyComponent component, String text)
	{
		return null;
		//TODO implement getPreferredSize
	}

	/**
	 * Provides the preferred size required to render the provided text content
	 * in the specified <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A>.
	 *
	 * 
	 * @param component - the ViewOnlyComponent to look at
	 * @param text - the provided text to be rendered
	 * @param insets - the insets to define the area in which the text should be laid out
	 * @return the preferred size as com.sun.dtv.lwuit.Dimension object
	 */
	public Dimension getPreferredSize( ViewOnlyComponent component, String text, Insets insets)
	{
		return null;
		//TODO implement getPreferredSize
	}

	/**
	 * Render a string. The passed <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> can be used to determine any additional information
	 * needed in order to render the string properly (e.g. Font, Color etc.),
	 * if the TextLayoutManager class implementing this interface does not
	 * provide additional information for that.
	 *
	 * The <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> also defines
	 * the layout area by its bounds, while of course the provided insets have
	 * also to be taken into account if not null. The clipping rectangle of
	 * the <code>Graphics</code> object should not be subject of change by the
	 * TextLayoutManager, though.
	 *
	 * 
	 * @param text - the string to be rendered. This string may be multi-line, where each line is separated by a &quot;\n&quot;(0x0A). If the string does not fit in the space available, it will be truncated and an ellipsis (&quot;...&quot;) appended to indicate the truncation.
	 * @param g - the graphics context. This includes of course also a clipping rectangle, which should be respected as borders within which the rendering is permitted. An insets parameter not equal to null must be additionally taken into account.
	 * @param component - the ViewOnlyComponent into which to render. Color and font information to be taken from here. If the specified font is not available, it will be replaced by the nearest built-in font. Each missing character is replaced by an &quot;*&quot; character.
	 * @param insets - the insets to define the area in which the text should be laid out. This parameter can also be null: in this case the ViewOnlyComponent parameter defines the borders by its bounds.
	 * @see render in interface TextLayoutManager
	 */
	public void render( String text, Graphics g, ViewOnlyComponent component, Insets insets)
	{
		//TODO implement render
	}

}
