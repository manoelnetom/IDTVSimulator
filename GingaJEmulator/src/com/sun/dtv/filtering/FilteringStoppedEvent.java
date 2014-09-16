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

/**
 * 
 */
public class FilteringStoppedEvent extends DataSectionFilterEvent
{
	/**
	 * Unknown cause
	 *
	 *
	 * 
	 */
	public static final int CAUSE_UNKNOWN = 0;

	/**
	 * Incomplete filter cause
	 *
	 *
	 * 
	 */
	public static final int CAUSE_FILTER_INCOMPLETE = 1;

	/**
	 * This cause is generated if section filter operations time out within the period specified
	 * by the <code>setTimeOut()</code> method.
	 *
	 * 
	 * For a <code>SimpleSectionFilter</code> it will be generated if no sections arrive within
	 * the specified period.
	 *
	 * 
	 * For a <code>TableSectionFilter</code>, it will be generated if the complete table does not
	 * arrive within the specified time.
	 *
	 * 
	 * For a <code>RingSectionFilter<code>, it will be generated if the specified time has elapsed
	 * since the arrival of the last section being successfully filtered.
	 *
	 *
	 * 
	 */
	public static final int CAUSE_FILTER_TIMEOUT = 2;

	/**
	 * This cause is used by <code>ListFilter</code> to report that a section has been
	 * encountered which has a different version_number from earlier sections. It is generated
	 * only once per filtering action. The section with a different version_number is ignored.
	 *
	 *
	 * 
	 */
	public static final int CAUSE_VERSION_CHANGE_DETECTED = 3;

	/**
	 * Section filter resources are removed from a connected <code>DataSectionFilterCollection</code>.
	 *
	 *
	 * 
	 */
	public static final int CAUSE_FILTER_RESOURCE_LOST = 4;

	/**
	 * A TransportStream which was attached to a <code>DataSectionFilterCollection</code>
	 * gets disconnected or becomes unavailable, causing filtering to stop.
	 *
	 *
	 * 
	 * 
	 */
	public static final int CAUSE_TRANSPORT_STREAM_DISCONNECTED = 5;

	//following variables are implicitely defined by getter- or setter-methods:
	private int cause;

	/**
	 * This constructs an <code>FilteringStoppedEvent</code> for the specified <code>DataSectionFilter</code> object.
	 *
	 * 
	 * @param f - the DataSectionFilter object where the event originated
	 * @param refObj - application data that was passed to the startFiltering method
	 */
	public FilteringStoppedEvent( DataSectionFilter f, Object refObj)
	{
		super(f, refObj);
	}

	/**
	 * This constructs an <code>FilteringStoppedEvent</code> for the specified <code>DataSectionFilter</code> object.
	 *
	 * 
	 * This constructor permits the cause of the filter stopping to be specified
	 *
	 * 
	 * @param f - the DataSectionFilter object where the event originated
	 * @param refObj - application data that was passed to the startFiltering method
	 * @param cause - the cause of the filter stopping
	 */
	public FilteringStoppedEvent( DataSectionFilter f, Object refObj, int cause)
	{
		super(f, refObj);
		this.cause = cause;
	}

	/**
	 * Set the cause for the filtering operation being stopped.
	 *
	 *
	 * 
	 * 
	 * @return the cause enumeration
	 */
	public int getCause()
	{
		return this.cause;
	}

}
