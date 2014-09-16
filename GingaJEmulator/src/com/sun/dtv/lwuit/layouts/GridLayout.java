/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.sun.dtv.lwuit.layouts;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.plaf.Style;

/**
 * 
 */
public class GridLayout extends Layout
{
	int rows;
	int cols;

	/**
	 * Creates a new instance of GridLayout with the given rows and columns.
	 *
	 * 
	 * @param rows - - the rows, with the value zero meaning any number of rows.
	 * @param columns - - the columns, with the value zero meaning any number of  columns.
	 */
	public GridLayout(int rows, int columns)
	{
		if ((rows == 0) && (cols == 0)) {
			throw new IllegalArgumentException("rows and cols cannot both be zero");
		}

		this.rows = rows;
		this.cols = columns;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#layoutContainer(com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Layout the given parent container children.
	 *
	 * 
	 * @param parent - the given parent container
	 * @see layoutContainer in class Layout
	 */
	public void layoutContainer( Container parent)
	{
		int width = parent.getLayoutWidth() - parent.getSideGap() - parent.getStyle().getPadding(Component.RIGHT) - parent.getStyle().getPadding(Component.LEFT);
		int height = parent.getHeight() - parent.getBottomGap() - parent.getStyle().getPadding(Component.BOTTOM) - parent.getStyle().getPadding(Component.TOP);
		int x = parent.getStyle().getPadding(Component.LEFT);
		int y = parent.getStyle().getPadding(Component.TOP);
		int numOfcomponents = parent.getComponentCount();
		int cmpWidth = (width)/this.cols;
		int cmpHeight;
		
		if (numOfcomponents > rows * this.cols) {
			cmpHeight  = (height)/rows;//(height)/(numOfcomponents/this.cols +1);//actual rows number
		} else {
			cmpHeight  = (height)/rows;  
		}
		
		int row = 0;        
		
		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			Style cmpStyle = cmp.getStyle();
			int marginLeft = cmpStyle.getMargin(Component.LEFT);
			int marginTop = cmpStyle.getMargin(Component.TOP);
			cmp.setWidth(cmpWidth - marginLeft - cmpStyle.getMargin(Component.RIGHT));
			cmp.setHeight(cmpHeight - marginTop - cmpStyle.getMargin(Component.BOTTOM));
			cmp.setX(x + (i%this.cols)*cmpWidth + marginLeft);
			cmp.setY(y + row*cmpHeight + marginTop);
			if((i + 1)%this.cols == 0){
				row++;
			}
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/layouts/Layout.html#getPreferredSize(com.sun.dtv.lwuit.Container)">Layout</A></CODE></B></DD>
	 * Returns the container preferred size.
	 *
	 * 
	 * @param parent - the parent container
	 * @return the container preferred size
	 * @see getPreferredSize in class Layout
	 */
	public Dimension getPreferredSize(Container parent)
	{
		int width = 0;
		int height = 0;
		int numOfcomponents = parent.getComponentCount();
		
		for(int i=0; i< numOfcomponents; i++){
			Component cmp = parent.getComponentAt(i);
			Dimension d = cmp.getPreferredSize();
			width = Math.max(width, d.getWidth() + cmp.getStyle().getMargin(Component.LEFT)+ cmp.getStyle().getMargin(Component.RIGHT));
			height = Math.max(height, d.getHeight()+ cmp.getStyle().getMargin(Component.TOP)+ cmp.getStyle().getMargin(Component.BOTTOM));
		}
		
		if(this.cols > 1){
			width = width*this.cols;
		}
		
		if(rows > 1){
			if(numOfcomponents>rows*this.cols){ //if there are more components than planned
				height = height * (numOfcomponents/rows +1);
			}else{
				height = height*rows;
			}
		}

		return new Dimension(width + parent.getStyle().getPadding(Component.LEFT)+ parent.getStyle().getPadding(Component.RIGHT), height + parent.getStyle().getPadding(Component.TOP)+ parent.getStyle().getPadding(Component.BOTTOM));
	}
}

