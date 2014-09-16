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
package com.sun.dtv.filtering;

import java.util.EventObject;

/**
 * 
 */
public class DataSectionFilterEvent extends EventObject
{
	//following variables are implicitely defined by getter- or setter-methods:

	private Object refObj;

	/**
	 * This constructs a <code>DataSectionFilterEvent</code> for the specified
	 * <code>DataSectionFilter</code> object.
	 *
	 * 
	 * @param f - the DataSectionFilter oject where the event originated.
	 * @param refObj - application data that was passed to the startFiltering  method.
	 */
	public DataSectionFilterEvent( DataSectionFilter f, Object refObj)
	{
		super(f);
		this.refObj = refObj; 
	}

	/**
	 * This returns the <code>DataSectionFilter</code> object which was
	 * the source of the event.
	 *
	 * 
	 * @return the DataSectionFilter.
	 * @see getSource in class EventObject
	 */
	public Object getSource()
	{
		return this.source;
	}

	/**
	 * Returns the application data that was passed to the
	 * <code>startFiltering</code> method.
	 *
	 * 
	 * 
	 * @return the application data.
	 */
	public Object getrefObj()
	{
		return this.refObj;
	}

}
