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
package com.sun.dtv.security;

/*
 * 
 */
public class UnsupportedCallbackException extends Exception 
{
	//following variables are implicitely defined by getter- or setter-methods:
	private Callback callback;

	/**
	 * Creates a new instance of UnsupportedCallbackException without detail message.
	 *
	 * 
	 * @param callback - the Callback which cannot be handled
	 */
	public UnsupportedCallbackException( Callback callback)
	{
		//TODO implement UnsupportedCallbackException
	}

	/**
	 * Constructs a UnsupportedCallbackException with the specified detail message.
	 * A detail message is a String that describes this particular exception.
	 *
	 * 
	 * @param callback - the unrecognized Callback.
	 * @param msg - the detail message.
	 */
	public UnsupportedCallbackException( Callback callback, String msg)
	{
		//TODO implement UnsupportedCallbackException
	}

	/**
	 * Get the unrecognized <code>Callback</code>.
	 *
	 * 
	 * 
	 * @return the unrecognized Callback.
	 */
	public Callback getCallback()
	{
		return this.callback;
	}

}
