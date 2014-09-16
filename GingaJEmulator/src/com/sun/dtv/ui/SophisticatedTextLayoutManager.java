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

/**
 * 
 */
public class SophisticatedTextLayoutManager extends Object implements TextLayoutManager
{
	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setHorizontalAlignment(int)"><CODE>setHorizontalAlignment</CODE></A> method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates that all
	 * content will be left aligned.
	 * Left aligned means the text is aligned to the left side if the
	 * line orientation is horizontal, text starts at upper left corner, and is
	 * read from left to right and from top to the bottom.
	 *
	 *
	 * 
	 */
	public static final int HORIZONTAL_ALIGN_LEFT = 0;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setHorizontalAlignment(int)"><CODE>setHorizontalAlignment</CODE></A> method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates that all content
	 * will be centered horizontally.
	 * Centered horizontally means the text is centered horizontally if the
	 * line orientation is horizontal, text starts at upper left corner, and is
	 * read from left to right and from top to the bottom.
	 *
	 *
	 * 
	 */
	public static final int HORIZONTAL_ALIGN_CENTER = 1;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setHorizontalAlignment(int)"><CODE>setHorizontalAlignment</CODE></A> method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates that all content
	 * will be right aligned.
	 * Right aligned means the text is aligned to the right side if the
	 * line orientation is horizontal, text starts at upper left corner, and is
	 * read from left to right and from top to the bottom.
	 *
	 *
	 * 
	 */
	public static final int HORIZONTAL_ALIGN_RIGHT = 2; 

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setHorizontalAlignment(int)"><CODE>setHorizontalAlignment</CODE></A> method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates that all content
	 * will be justified horizontally.
	 * Horizontal justified means the text is written as a horizontal block if
	 * the line orientation is horizontal, text starts at upper left corner, and
	 * is read from left to right and from top to the bottom.
	 *
	 *
	 * 
	 */
	public static final int HORIZONTAL_ALIGN_JUSTIFIED = 3;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setVerticalAlignment(int)"><CODE>setVerticalAlignment</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that all content will be top aligned.
	 *
	 *
	 * 
	 */
	public static final int VERTICAL_ALIGN_TOP = 4;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setVerticalAlignment(int)"><CODE>setVerticalAlignment</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that all content will be centered vertically.
	 *
	 *
	 * 
	 */
	public static final int VERTICAL_ALIGN_CENTER = 5;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setVerticalAlignment(int)"><CODE>setVerticalAlignment</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that all content will be bottom aligned.
	 *
	 *
	 * 
	 */
	public static final int VERTICAL_ALIGN_BOTTOM = 6;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setVerticalAlignment(int)"><CODE>setVerticalAlignment</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that all content will be justified vertically.
	 *
	 *
	 * 
	 */
	public static final int VERTICAL_ALIGN_JUSTIFIED = 7;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setLineOrientation(int)"><CODE>setLineOrientation</CODE></A> method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates that the line
	 * orientation is horizontal.
	 *
	 *
	 * 
	 */
	public static final int LINE_ORIENTATION_HORIZONTAL = 8;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setLineOrientation(int)"><CODE>setLineOrientation</CODE></A> method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates that the line
	 * orientation is vertical.
	 *
	 *
	 * 
	 */
	public static final int LINE_ORIENTATION_VERTICAL = 9;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setStartCorner(int)"><CODE>setStartCorner</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that the text starts in the upper left corner.
	 *
	 *
	 * 
	 */
	public static final int START_UPPER_LEFT = 10;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setStartCorner(int)"><CODE>setStartCorner</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that the text starts in the upper right corner.
	 *
	 *
	 * 
	 */
	public static final int START_UPPER_RIGHT = 11;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setStartCorner(int)"><CODE>setStartCorner</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that the text starts in the lower left corner.
	 *
	 *
	 * 
	 */
	public static final int START_LOWER_LEFT = 12;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setStartCorner(int)"><CODE>setStartCorner</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that the text starts in the lower right corner.
	 *
	 *
	 * 
	 */
	public static final int START_LOWER_RIGHT = 13;

	/**
	 * Constant to be used with the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html#setLineSpace(int)"><CODE>setLineSpace</CODE></A>
	 * method of <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>. It indicates
	 * that the line space should be set to the default value, determined by the
	 * default font.
	 *
	 *
	 * 
	 * 
	 */
	public static final int DEFAULT_LINE_SPACE = -1;

	//following variables are implicitely defined by getter- or setter-methods:
	private int horizontalAlignment;
	private int verticalAlignment;
	private int lineOrientation;
	private int startCorner;
	private boolean textWrapping;
	private int lineSpace;
	private int letterSpace;
	private int horizontalTabSpacing;
	private Insets insets;

	/**
	 * 
	 * 
	 */
	public SophisticatedTextLayoutManager()
	{
		//TODO implement SophisticatedTextLayoutManager
	}

	/**
	 * Render a string. The passed <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> can be used to determine any additional information
	 * needed in order to render the string properly (e.g. Font, Color etc.),
	 * if the TextLayoutManagerClass implementing this interface does not
	 * provide additional information for that.
	 *
	 * The <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> also
	 * defines
	 * the layout area by its bounds, while of course the provided insets have
	 * also to be taken into account if not null. The clipping rectangle of
	 * the <code>Graphics</code> object should not be subject of change by the
	 * TextLayoutManager, though.
	 *
	 * 
	 * @param text - the string to be rendered
	 * @param g - the graphics context. This includes of course also a clipping rectangle, which should be respected as borders within which the rendering is permitted. An insets parameter not equal to null must be additionally taken into account.
	 * @param component - the ViewOnlyComponent into which to render.
	 * @param insets - the insets to define the area in which the text should be laid out. This parameter can also be null: in this case the ViewOnlyComponent parameter defines the borders by its bounds.
	 * @see render in interface TextLayoutManager
	 */
	public void render( String text, Graphics g, ViewOnlyComponent component, Insets insets)
	{
		//TODO implement render
	}

	/**
	 * Determine the horizontal alignment.
	 *
	 * 
	 * @param alignment - the value for the horizontal alignment. Possible values are: HORIZONTAL_ALIGN_LEFT, HORIZONTAL_ALIGN_RIGHT, HORIZONTAL_ALIGN_CENTER and HORIZONTAL_ALIGN_JUSTIFIED.
	 * @see getHorizontalAlignment()
	 */
	public void setHorizontalAlignment(int alignment)
	{
		this.horizontalAlignment = alignment;
	}

	/**
	 * Determine the vertical alignment.
	 *
	 * 
	 * @param alignment - the value for the vertical alignment. Possible values are: VERTICAL_ALIGN_TOP, VERTICAL_ALIGN_BOTTOM, VERTICAL_ALIGN_CENTER and VERTICAL_ALIGN_JUSTIFIED.
	 * @see getVerticalAlignment()
	 */
	public void setVerticalAlignment(int alignment)
	{
		this.verticalAlignment = alignment;
	}

	/**
	 * Determine the line orientation.
	 *
	 * 
	 * @param orientation - the value for the line orientation. Possible values are: LINE_ORIENTATION_HORIZONTAL and LINE_ORIENTATION_VERTICAL.
	 */
	public void setLineOrientation(int orientation)
	{
		this.lineOrientation = orientation;
	}

	/**
	 * Determine the starting corner for text writing.
	 *
	 * 
	 * @param corner - the value for the starting corner. Possible values are: START_UPPER_LEFT, START_UPPER_RIGHT, START_LOWER_LEFT and START_LOWER_RIGHT.
	 */
	public void setStartCorner(int corner)
	{
		this.startCorner = corner;
	}

	/**
	 * Determines whether text is wrapped.
	 *
	 * 
	 * @param wrap - true if text should be wrapped,  false otherwise
	 */
	public void setTextWrapping(boolean wrap)
	{
		this.textWrapping = wrap;
	}

	/**
	 * Determines space between lines.
	 *
	 * 
	 * @param spacing - value for line spacing; an integer or DEFAULT_LINE_SPACE
	 */
	public void setLineSpace(int spacing)
	{
		this.lineSpace = spacing;
	}

	/**
	 * Determines the space between letters.
	 *
	 * 
	 * @param spacing - letter space setting in units of 1/256th point
	 */
	public void setLetterSpace(int spacing)
	{
		this.letterSpace = spacing;
	}

	/**
	 * Determines the horizontal tabulation spacing.
	 *
	 * 
	 * @param spacing - horizontal tabulator spacing in points
	 */
	public void setHorizontalTabSpacing(int spacing)
	{
		this.horizontalTabSpacing = spacing;
	}

	/**
	 * Retrieve horizontal alignment.
	 *
	 * 
	 * 
	 * @return horizontal alignment
	 * @see setHorizontalAlignment(int)
	 */
	public int getHorizontalAlignment()
	{
		return this.horizontalAlignment;
	}

	/**
	 * Retrieve  vertical alignment.
	 *
	 * 
	 * 
	 * @return vertical alignment
	 * @see setVerticalAlignment(int)
	 */
	public int getVerticalAlignment()
	{
		return this.verticalAlignment;
	}

	/**
	 * Retrieve  line orientation.
	 *
	 * 
	 * 
	 * @return line orientation
	 */
	public int retrieveLineOrientation()
	{
		return 0;
		//TODO implement retrieveLineOrientation
	}

	/**
	 * Retrieve  starting corner.
	 *
	 * 
	 * 
	 * @return starting corner
	 */
	public int retrieveStartCorner()
	{
		return 0;
		//TODO implement retrieveStartCorner
	}

	/**
	 * Retrieve  text wrapping.
	 *
	 * 
	 * 
	 * @return text wrapping
	 */
	public boolean retrieveTextWrapping()
	{
		return false;
		//TODO implement retrieveTextWrapping
	}

	/**
	 * Retrieve  line spacing.
	 *
	 * 
	 * 
	 * @return line spacing: a number or DEFAULT_LINE_SPACE, if the default  line spacing is determined
	 */
	public int retrieveLineSpace()
	{
		return 0;
		//TODO implement retrieveLineSpace
	}

	/**
	 * Retrieve  letter space .
	 *
	 * 
	 * 
	 * @return letter spacing in 1/256th points
	 */
	public int retrieveLetterSpace()
	{
		return 0;
		//TODO implement retrieveLetterSpace
	}

	/**
	 * Retrieve  horizontal tabulator spacing.
	 *
	 * 
	 * 
	 * @return horizontal tabulator spacing
	 */
	public int retrieveHorizontalTabSpacing()
	{
		return 0;
		//TODO implement retrieveHorizontalTabSpacing
	}

	/**
	 * Determines the insets to be used by the <A HREF="../../../../com/sun/dtv/ui/SophisticatedTextLayoutManager.html" title="class in com.sun.dtv.ui"><CODE>SophisticatedTextLayoutManager</CODE></A>
	 * to provide a virtual margin. The insets determined by this method have to
	 * be added to the insets passed to the <code>render</code> method.
	 * If this method has not been called, the default insets are 0 at each edge.
	 *
	 * 
	 * @param insets - the determined insets
	 * @see getInsets()
	 */
	public void setInsets( Insets insets)
	{
		this.insets = insets;
	}

	/**
	 * Returns the insets as set by the <code>setInsets</code> method. When not
	 * previously set, zero insets are returned.
	 *
	 * 
	 * 
	 * @return insets as set by the setInsets method
	 * @see setInsets(java.awt.Insets)
	 */
	public Insets getInsets()
	{
		return this.insets;
	}

	/**
	 * Register a <A HREF="../../../../com/sun/dtv/ui/TextOverflowListener.html" title="interface in com.sun.dtv.ui"><CODE>TextOverflowListener</CODE></A>.
	 * This listener will be notified if a text string won't fit into the
	 * component during an attempt to render it.
	 *
	 * 
	 * @param listener - the listener to be registered
	 * @see removeTextOverflowListener(com.sun.dtv.ui.TextOverflowListener)
	 */
	public void addTextOverflowListener( TextOverflowListener listener)
	{
		//TODO implement addTextOverflowListener
	}

	/**
	 * Removes a previously registered <A HREF="../../../../com/sun/dtv/ui/TextOverflowListener.html" title="interface in com.sun.dtv.ui"><CODE>TextOverflowListener</CODE></A> from this TextLayoutManager.
	 *
	 * 
	 * @param listener - the listener to be removed
	 * @see addTextOverflowListener(com.sun.dtv.ui.TextOverflowListener)
	 */
	public void removeTextOverflowListener( TextOverflowListener listener)
	{
		//TODO implement removeTextOverflowListener
	}

}
