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
package com.sun.dtv.broadcast;

/**
 *
 */
public class BroadcastException extends Exception
{
	/**
	 * This reason code is used when an inconsistent message is received
	 * from the broadcast stream.
	 *
	 *
	 * 
	 */
	public static final int REASON_INCONSISTENT_MESSAGE = 1;

	/**
	 * This reason code is used if the requested object does not exist
	 * in the broadcast stream.
	 *
	 *
	 * 
	 */
	public static final int REASON_NONEXISTENT_OBJECT = 2;

	/**
	 * This reason code is used if the requested path name does not exist
	 * in the broadcast stream.
	 *
	 *
	 * 
	 */
	public static final int REASON_INVALID_PATH = 3;

	/**
	 * This reason code is used if timeout occurs while reading
	 * from the broadcast stream.
	 *
	 *
	 * 
	 */
	public static final int REASON_TIMEOUT = 4;

	/**
	 * This reason code is used if this operation is not supported.
	 *
	 *
	 * 
	 */
	public static final int REASON_UNSUPPORTED = 5;

	/**
	 * This reason code is used if this operation is not supported.
	 *
	 *
	 * 
	 */
	public static final int REASON_INVALID_OPERATION = 6;

	/**
	 * This reason code is used if no other reason code applies.
	 * Additional information should be retrieved from the detailed text message.
	 *
	 *
	 * 
	 * 
	 */
	public static final int REASON_OTHER = -1;

	//following variables are implicitely defined by getter- or setter-methods:
	private int reason;

	/**
	 * 
	 * 
	 */
	public BroadcastException()
	{
		super();
	}

	/**
	 * Retrieves the reason of this exception.
	 *
	 * 
	 * 
	 * @return the reason
	 */
	public int getReason()
	{
		return this.reason;
	}

}
