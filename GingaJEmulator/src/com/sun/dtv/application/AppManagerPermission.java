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
package com.sun.dtv.application;

import java.security.Permission;

/**
 * 
 */
public class AppManagerPermission extends Permission
{
	private String actions;

	/**
	 * Construct a new AppManagerPermission with the requested resource
	 * and actions.  The resource name is the <code>appId</code> of
	 * the application to be managed.  It may be "*" to allow access
	 * to all applications.
	 *
	 * 
	 * @param name - the name of the application to allow access to or "*" to allow access to all applications.
	 * @param actions - contains a comma-separated list of the desired actions granted. Possible actions are "read", "fetch", and "manage".
	 */
	public AppManagerPermission( String name, String actions)
	{
		super(name);
	}

	/**
	 * Returns the "canonical string representation" of the actions.
	 * That is, this method always returns present actions in the following order:
	 * read, fetch, manage.
	 * For example, if this AppManagerPermission object allows both
	 * manage, fetch and read actions, a call to getActions will
	 * return the string "read,fetch,manage".
	 *
	 * 
	 * @return the actions in canonical string representation.
	 * @see getActions in class Permission
	 */
	public String getActions()
	{
		return this.actions;
	}

	/**
	 * Checks two AppManagerPermission objects for equality.
	 * Checks that obj is a AppManagerPermission, and has the same
	 * name and actions as this object.
	 *
	 * 
	 * @param obj - the object to be tested for equality with this object.
	 * @return true if obj is a AppManagerPermission  and has the same name and actions as this AppManagerPermission object.
	 * @see equals in class Permission
	 */
	public boolean equals( Object obj)
	{
		return false;
		//TODO implement equals
	}

	/**
	 * Returns the hash code value for this object.
	 * The hash code used is the hash code of this permissions name
	 * plus the hashcode of the actions,
	 * that is, <code>getName().hashCode() + getActions.hashCode()</code>,
	 * where getName is from the Permission superclass and getActions is
	 * in this class.
	 *
	 * 
	 * @return a hash code value for this object.
	 * @see hashCode in class Permission
	 */
	public int hashCode()
	{
		return 0;
		//TODO implement hashCode
	}

	/**
	 * Checks if this AppManagerPermission object "implies" the specified
	 * permission.
	 * More specifically, this method returns true if:
	 * <UL>
	 * <LI>p is an instanceof AppManagerPermission, and</LI>
	 * <LI>p's actions are a subset of this object's actions, and</LI>
	 * <LI>this objects name is equal to "*" or
	 * p's name is equal to this object's name.</LI>
	 * </UL>
	 *
	 * 
	 * @param p - the Permission to check.
	 * @return true iff this permission implies Permission p.
	 * @see implies in class Permission
	 */
	public boolean implies( Permission p)
	{
		return false;
		//TODO implement implies
	}

}
