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

public interface VideoFormatControl extends Control {
	
    //Constant representing an active format description of Box 14:9.
	public static final int	AF_149 = 10;

    //Constant representing an active format description of Box 14:9 (central).
	public static final int	AF_149_CENTRAL = 7;
	
	//Constant representing an active format description of 14:9 (upper).
	public static final int	AF_149_UPPER = 2;
 
    //Constant representing an active format description of Box 16:9.
	public static final int	AF_169 = 9;

    //Constant representing an active format description of Box 16:9 (central).
    public static final int	AF_169_BOX_CENTRAL = 3;

    //Constant representing an active format description of Box 16:9 (central).
    public static final int	AF_169_CENTRAL = 6;

    //Constant representing an active format description of 16:9 (upper).
    public static final int	AF_169_UPPER = 1;

    //Constant representing an active format description of Box 4:3.    
    public static final int	AF_43 = 8;

    //Constant representing an active format description of Box 4:3 (central).
    public static final int	AF_43_CENTRAL = 5;

    //Constant representing an active format as the coded frame. 
    public static final int	AF_AS_THE_CODED_FRAME = 4;

    //Constant representing a not present active format description.
    public static final int	AF_NOT_PRESENT = 0;

    //The 16x9 aspect ratio.
    public static final int	ASPECT_RATIO_169 = 169;

    //The 4x3 aspect ratio.
    public static final int	ASPECT_RATIO_43 = 43;

    //No aspect ratio not known.
    public static final int	ASPECT_RATIO_NOT_PRESENT = 0;

    //Constant representing a decoder format description of center (only the central part of the 16:9 video will be displayed on a 4:3 frame.
    public static final int	DF_CENTER = 5;

    //Constant representing a decoder format description of fullscreen
    public static final int	DF_FULL = 2;

    //Constant representing a decoder format description of letterbox (the full 16:9 video will be displayed in the central part of a 4:3 frame black bars on top and bottom of the frame.
    public static final int	DF_LETTERBOX = 3;

    //Constant representing a not present decoder format description.
    public static final int	DF_NOT_PRESENT = 0;
  
    //Constant representing a decoder format description of pan scan (only the central part of the 16:9 video will be displayed on a 4:3 frame using pan scan vectors).    
    public static final int	DF_PANSCAN = 6;
  
    //Constant representing that the decoder format description is set by the system.
    public static final int	DF_SYSTEM = 1;

    //Constant representing a decoder format description of zoom (only the central part of the 4:3 video will be displayed on a 16:9 frame.
    public static final int	DF_ZOOM = 4;

    //Add a listener to the VideoFormatControl.
    public void addVideoFormatListener(VideoFormatListener listener);

    //Gives the active_format of the Active Format Description (in ABNT NBR 15602-1:2007) if present as one of the defined AFD constant values.
    public int getActiveFormatDefinition();

    //Gives the aspect ratio defined for the media under the control of this Player.
    public int getAspectRatio();

    //Gives the actual format definition that is currently used by the decoder.
    public int getDecoderFormatDefinition();

    //Gives the original aspect ratio defined for the media.
    public int getOriginalAspectRatio();

    //Returns the Transformation that describes the given decoder format conversion.
    public Transformation getTransformation(int decoderFormatConversion);
    
    //Removes a VideoFormatListener object.
    public void removeVideoFormatListener(VideoFormatListener listener);



}
