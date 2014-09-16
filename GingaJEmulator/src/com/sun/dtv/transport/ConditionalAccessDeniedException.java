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
package com.sun.dtv.transport;

/**
 * 
 */
public class ConditionalAccessDeniedException extends Exception
{
	/**
	 * access not possible.
	 *
	 *
	 * 
	 */
	public static final int POSSIBLE = 1;

	/**
	 * access possible under certain conditions.
	 *
	 *
	 * 
	 */
	public static final int NOT_POSSIBLE = 2;

	/**
	 * user dialog needed for payment.
	 *
	 *
	 * 
	 */
	public static final int COMMERCIAL_DIALOG = 16;

	/**
	 * user dialog needed for maturity.
	 *
	 *
	 * 
	 */
	public static final int MATURITY_RATING_DIALOG = 32;

	/**
	 * user does not have suitable maturity.
	 *
	 *
	 * 
	 */
	public static final int MATURITY_RATING = 64;

	/**
	 * user does not have an entitlement.
	 *
	 *
	 * 
	 */
	public static final int NO_ENTITLEMENT = 128;

	/**
	 * user dialog needed to explain about free preview.
	 *
	 *
	 * 
	 */
	public static final int FREE_PREVIEW_DIALOG = 256;

	/**
	 * not allowed for geographical reasons.
	 *
	 *
	 * 
	 */
	public static final int GEOGRAPHICAL_BLACKOUT = 512;

	/**
	 * user dialog needed for technical purposes.
	 *
	 *
	 * 
	 */
	public static final int TECHNICAL_DIALOG  = 1024;

	/**
	 * not allowed for some technical reason.
	 *
	 *
	 * 
	 */
	public static final int NOT_POSSIBLE_TECHNICAL = 2048;

	/**
	 * Some other reason.
	 *
	 *
	 * 
	 * 
	 */
	public static final int OTHER = 4096;

	//following variables are implicitely defined by getter- or setter-methods:
	private Object[] sources;

	/**
	 * Constructs a <code>ConditionalAccessDeniedException</code> with no
	 * detail message.
	 *
	 * 
	 */
	public ConditionalAccessDeniedException()
	{
		//TODO implement ConditionalAccessDeniedException
	}

	/**
	 * Constructs a <code>ConditionalAccessDeniedException</code> with the
	 * specified detail message.
	 *
	 * 
	 * @param s - the detail message.
	 */
	public ConditionalAccessDeniedException( String s)
	{
		//TODO implement ConditionalAccessDeniedException
	}

	/**
	 * Returns the reason(s) why descrambling was not possible.
	 *
	 * 
	 * @param index - shall refer to the Service or one elementary stream in  the set returned by getSources().
	 * @return the reason for the not authorized exception. The reason is a bitwise OR of either POSSIBLE or NOT_POSSIBLE with another reason code.
	 * @throws IndexOutOfBoundsException - This exception will be thrown where index is beyond the size of the array returned by getSources.
	 * @see getSources()
	 */
	public int getReason(int index) throws IndexOutOfBoundsException
	{
		return 0;
		//TODO implement getReason
	}

	/**
	 * This method returns an array containing either a single Service object
	 * or a set of ElementaryStreams that could not be descrambled.
	 *
	 * 
	 * 
	 * @return the set of Service or ElementaryStream objects that could not  be descrambled
	 */
	public Object[] getSources()
	{
		return this.sources;
	}

}
