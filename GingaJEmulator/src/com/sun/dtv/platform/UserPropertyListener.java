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

import java.util.EventListener;

/**
 * 
 */
public interface UserPropertyListener extends EventListener
{
	/**
	 * Reports that the value of the property identified by <code>key</code>
	 * has changed from <code>oldValue</code> to <code>newValue</code>.
	 * This method <span class="rfc2119">must</span> only be triggered when
	 * <code>newValue</code> is different from <code>oldValue</code>.
	 *
	 * 
	 * @param key - The key of the property which value has changed.
	 * @param oldValue - The value associated to key before the change occurred.
	 * @param newValue - The value associated to key after the change occurred.
	 */
	void propertyChanged( String key, String oldValue, String newValue);

	/**
	 * Reports that the property identified by <code>key</code> has been
	 * removed from the user properties. It also gives along the previous
	 * value that was associated with this key.
	 * 
	 *Note: specifically in the case where a whole subtree of the user
	 * properties is removed, only properties that actually have a value
	 * <span class="rfc2119">must</span> trigger this interface.</p>
	 *
	 * 
	 * @param key - The key of the property which value has changed.
	 * @param oldValue - The value associated to key before that property was removed.
	 */
	void propertyRemoved( String key, String oldValue);

}
