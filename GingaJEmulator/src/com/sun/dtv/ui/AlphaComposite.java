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

public final class AlphaComposite {

    //AlphaComposite object that implements the opaque CLEAR rule with an alpha of 1.0f.\
	public static final AlphaComposite Clear = null;

    //Porter-Duff Clear rule.
	public static final int CLEAR = 1;

    //AlphaComposite object that implements the opaque DST rule with an alpha of 1.0f.
	public static final AlphaComposite Dst = null;

    //Porter-Duff Destination rule.
	public static final int DST = 4;

    //Porter-Duff Destination Atop Source rule.
	public static final int DST_ATOP = 11;

    //Porter-Duff Destination In Source rule.
	public static final int DST_IN = 7;

    //Porter-Duff Destination Held Out By Source rule.
	public static final int DST_OUT = 9;

	//Porter-Duff Destination Over Source rule.
	public static final int DST_OVER = 5;
 
    //AlphaComposite object that implements the opaque DST_ATOP rule with an alpha of 1.0f.
	public static final AlphaComposite DstAtop = null;

    //AlphaComposite object that implements the opaque DST_IN rule with an alpha of 1.0f.
	public static final AlphaComposite DstIn = null;

    //AlphaComposite object that implements the opaque DST_OUT rule with an alpha of 1.0f.
	public static final AlphaComposite DstOut = null;

    //AlphaComposite object that implements the opaque DST_OVER rule with an alpha of 1.0f.
	public static final AlphaComposite DstOver = null;

    //AlphaComposite object that implements the opaque SRC rule with an alpha of 1.0f.
	public static final AlphaComposite Src = null;

    //Porter-Duff Source rule.
	public static final int SRC = 2;

    //Porter-Duff Source Atop Destination rule.
	public static final int SRC_ATOP = 10;

    //Porter-Duff Source In Destination rule.
	public static final int SRC_IN = 6;

    //Porter-Duff Source Held Out By Destination rule.
	public static final int SRC_OUT = 8;

   //Porter-Duff Source Over Destination rule.
    public static final int SRC_OVER = 3;

    //AlphaComposite object that implements the opaque SRC_ATOP rule with an alpha of 1.0f.
    public static final AlphaComposite SrcAtop = null;

    //AlphaComposite object that implements the opaque SRC_IN rule with an alpha of 1.0f.
    public static final AlphaComposite SrcIn = null;

    //AlphaComposite object that implements the opaque SRC_OUT rule with an alpha of 1.0f.
    public static final AlphaComposite SrcOut = null;

    //AlphaComposite object that implements the opaque SRC_OVER rule with an alpha of 1.0f.
    public static final AlphaComposite SrcOver = null;

    //AlphaComposite object that implements the opaque XOR rule with an alpha of 1.0f.
    public static final AlphaComposite Xor = null;

    //Porter-Duff Source Xor Destination rule.
    public static final int XOR = 12;


    //Determines whether the specified object is equal to this AlphaComposite.
    public boolean equals(Object obj) {
		return false;
    	
    }
	
    //Returns the alpha value of this AlphaComposite.
    public float getAlpha() {
		return 0;
    	
    }
	
    //Creates an AlphaComposite object with the specified rule.	
	public static AlphaComposite getInstance(int rule) {
		return Clear;
		
	}

    //Creates an AlphaComposite object with the specified rule and the constant alpha to multiply with the alpha of the source.
	public static AlphaComposite 	getInstance(int rule, float alpha) {
		return Clear;
		
	}
	
    //Returns the compositing rule of this AlphaComposite.
	public int 	getRule() {
		return 0;
		
	}
  
	//Returns the hashcode for this composite.
	public int 	hashCode(){
		return 0;
		
	}
	
}
