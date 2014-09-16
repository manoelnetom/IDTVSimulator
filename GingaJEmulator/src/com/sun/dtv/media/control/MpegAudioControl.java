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
package com.sun.dtv.media.control;

import javax.media.Control;

public interface MpegAudioControl extends Control {
	
    //Indicates support for five channels 3-0 2-0 layout: Left, Center and Right of first program, Left and Right of second program.
    public static final int	FIVE_CHANNELS_3_0_2_0 = 128;

    //Indicates support for five channels 3-2 layout: Left, Center, Right, Left Surround and Right surround.
    public static final int	FIVE_CHANNELS_3_2 = 256;

    //Indicates support for four channels 2-0 2-0 layout: Left and Right of first program, Left and Right of second program.
    public static final int	FOUR_CHANNELS_2_0_2_0 = 16;

    //Indicates support for four channels 2-2 layout: Left, Right, Left Surround and Right Surround.
    public static final int	FOUR_CHANNELS_2_2 = 32;

    //Indicates support for four channels 3-1 layout: Left, Center, Right and single Surround.
    public static final int	FOUR_CHANNELS_3_1 = 64;

    //Indicates support for audio layer 1.
    public static final int	LAYER_1 = 1;

    //Indicates support for audio layer 2.
    public static final int	LAYER_2 = 2;

    //Indicates support for audio layer 3.
    public static final int	LAYER_3 = 4;

    //Indicates support for 16 KHz audio sampling rate.
    public static final int	SAMPLING_RATE_16 = 1;

    //Indicates support for 22.05 KHz audio sampling rate.
    public static final int	SAMPLING_RATE_22_05 = 2;

    //Indicates support for 24 KHz audio sampling rate.
    public static final int	SAMPLING_RATE_24 = 4;

    //Indicates support for 32 KHz audio sampling rate.
    public static final int	SAMPLING_RATE_32 = 8;

    //Indicates support for 44.1 KHz audio sampling rate.
    public static final int	SAMPLING_RATE_44_1 = 16;
    
    //Indicates support for 48 KHz audio sampling rate.
    public static final int	SAMPLING_RATE_48 = 32;

    //Indicates support for single channel layout.
    public static final int	SINGLE_CHANNEL = 1;

    //Indicates support for three channels 2-1 layout: Left, Right and single Surround.
    public static final int	THREE_CHANNELS_2_1 = 4;

    //Indicates support for three channels 3-0 layout: Left, Center and Right.
    public static final int	THREE_CHANNELS_3_0 = 8;

    //Indicates support for two channels dual layout.
    public static final int	TWO_CHANNELS_DUAL = 4;

    //Indicates support for two channels stereo layout.
    public static final int	TWO_CHANNELS_STEREO = 2;


    //Returns the current MPEG Audio Layer, or -1 if the Audio Layer is not available.
    public int getAudioLayer();

    //Returns the current MPEG Audio channel layout, or -1 if the channel layout is not available.
    public int getChannelLayout();

    //Returns true if Low Frequency Channel mode is turned on.
    public boolean getLowFrequencyChannel();

    //Returns true if Multilingual mode is turned on.
    public boolean getMultilingualMode();

    //Returns the audio layer support capability.
    public int getSupportedAudioLayers();

    //Returns the audio channel layout support capability.
    public int getSupportedChannelLayouts();
    //Returns the audio sampling rate support capability.
    
    public int getSupportedSamplingRates();

    //Returns the low frequency channel support capability.
    public boolean 	isLowFrequencyChannelSupported();

    //Returns the multilingual mode support capability.
    public boolean isMultilingualModeSupported();

}
