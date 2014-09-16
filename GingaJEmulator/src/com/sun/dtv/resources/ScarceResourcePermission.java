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
package com.sun.dtv.resources;

import java.security.BasicPermission;

/**
 * 
 */
public final class ScarceResourcePermission extends BasicPermission
{
	//following variables are implicitely defined by getter- or setter-methods:
	private String actions;

	/**
	 * Creates a new <code>ScarceResourcePermission</code> object with the
	 * specified name and actions. The <code>name</code> is the name of the
	 * specialized scarce resource (e.g. "<code>networkdevice</code>" ...) and
	 * the <code>actions</code> contains a comma-separated list of the
	 * desired actions granted for that scarce resource.
	 *
	 * 
	 * @param name - The name of the ScarceResourcePermission.
	 * @param actions - The actions string.
	 */
	public ScarceResourcePermission( String name, String actions)
	{
		super(name);
	}

	/**
	 * Returns the "Canonical string representation" of the actions.
	 * That is, this method always returns present actions in the
	 * alphabetic order: <code>force</code>, <code>reserve</code>.
	 * For example, if this <code>ScarceResourcePermission</code> object allows
	 * both <code>reserve</code>, <code>force</code> actions, a call to
	 * <code>getActions</code> will return the string
	 * "<code>force,reserve</code>".
	 *
	 * 
	 * @return The canonical string representation of the actions.
	 * @see getActions in class BasicPermission
	 */
	public String getActions()
	{
		return this.actions;
	}

}
