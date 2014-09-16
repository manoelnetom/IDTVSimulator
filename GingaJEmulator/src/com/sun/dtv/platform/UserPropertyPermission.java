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
package com.sun.dtv.platform;

import java.security.BasicPermission;
import java.security.Permission;
import java.security.PermissionCollection;

/**
 * 
 */
public final class UserPropertyPermission extends BasicPermission
{
	private String actions;

	/**
	 * Creates a new <code>UserPropertyPermission</code> object with the
	 * specified name and actions. The <code>name</code> is the name of the
	 * property, and <code>action</code> contains a comma-separated list
	 * of the desired actions granted on the property. Possible actions are
	 * <code>read</code> and <code>write</code>.
	 *
	 * 
	 * @param name - The name of the UserPropertyPermission.
	 * @param actions - The actions string.
	 */
	public UserPropertyPermission( String name, String actions)
	{
		super(name);
	}

	/**
	 * Checks if this <code>UserPropertyPermission</code> object "implies" the
	 * specified permission.
	 * 
	 *More specifically, this method returns true if:</p>
	 * 
	 * <ol>
	 * <li><code>p</code> is an instance of
	 * <code>UserPropertyPermission</code>,</li>
	 * <li><code>p</code>'s actions are a proper subset of this object's
	 * actions, and</li>
	 * <li><code>p</code>'s name is implied by this object's name.
	 * For example, "<code>com.sun.dtv.user.*</code>" implies
	 * "<code>com.sun.dtv.user.name</code>".</li>
	 * </ol>
	 *
	 * 
	 * @param p - The permission to check against.
	 * @return true if the specified permission is implied by this object, false if not.
	 * @see implies in class BasicPermission
	 */
	public boolean implies( Permission p)
	{
		return false;
		//TODO implement implies
	}

	/**
	 * Checks two <code>UserPropertyPermission</code> objects for equality.
	 * Checks that <code>obj</code> is a <code>UserPropertyPermission</code>,
	 * and has the same name and actions as this object.
	 *
	 * 
	 * @param obj - The object we are testing for equality with this object.
	 * @return true if obj is a UserPropertyPermission, and has the same name and actions as this UserPropertyPermission object.
	 * @see equals in class BasicPermission
	 */
	public boolean equals( Object obj)
	{
		return false;
		//TODO implement equals
	}

	/**
	 * Returns the hash code value for this object. The hash code used is the
	 * hash code of this permissions name, that is,
	 * <code>getName().hashCode()</Code>, where <code>getName</code> is from
	 * the <code>Permission</code> superclass.
	 *
	 * 
	 * @return A hash code value for this object.
	 * @see hashCode in class BasicPermission
	 */
	public int hashCode()
	{
		return 0;
		//TODO implement hashCode
	}

	/**
	 * Returns the "canonical string representation" of the actions.
	 * That is, this method always returns present actions in the
	 * alphabetic order: <code>read</code>, <code>write</code>.
	 * For example, if this <code>UserPropertyPermission</code> object allows
	 * both <code>write</code> and <code>read</code> actions, a call to
	 * <code>getActions</code> will return the string
	 * "<code>read,write</code>".
	 *
	 * 
	 * @return The canonical string representation of the actions.
	 * @see getActions in class BasicPermission
	 */
	public String getActions()
	{
		return this.actions;
	}

	/**
	 * Returns a new <code>PermissionCollection</code> object for storing
	 * <code>UserPropertyPermission</code> objects.
	 *
	 * 
	 * @return A new PermissionCollection object suitable for storing UserPropertyPermission s.
	 * @see newPermissionCollection in class BasicPermission
	 */
	public PermissionCollection newPermissionCollection()
	{
		return null;
		//TODO implement newPermissionCollection
	}

}
