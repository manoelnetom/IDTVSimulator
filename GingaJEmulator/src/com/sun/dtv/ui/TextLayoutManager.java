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
public interface TextLayoutManager
{
	/**
	 * Render a string. The passed <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> can be used to determine any additional information
	 * needed in order to render the string properly (e.g. Font, Color etc.),
	 * if the TextLayoutManagerClass implementing this interface does not
	 * provide additional information for that.
	 *
	 * The <A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html" title="interface in com.sun.dtv.ui"><CODE>ViewOnlyComponent</CODE></A> also
	 * defines the layout area by its bounds, while of course the provided
	 * insets have also to be taken into account if not null. The clipping
	 * rectangle of the <code>Graphics</code> object should not be subject
	 * of change by the TextLayoutManager, though.
	 *
	 * 
	 * @param text - the string to be rendered
	 * @param g - the graphics context. This includes of course also a clipping  rectangle, which should be respected as borders within which the rendering is permitted. An insets parameter not equal to null must be additionally taken into account.
	 * @param component - the ViewOnlyComponent into which to render.
	 * @param insets - the insets to define the area in which the text should be laid out. This parameter can also be null: in this case the ViewOnlyComponent parameter defines the borders by its bounds.
	 */
	void render( String text, Graphics g, ViewOnlyComponent component, Insets insets);

}
