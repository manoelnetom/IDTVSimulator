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
package com.sun.dtv.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * 
 */
public final class FileProperties extends Object
{
	/**
	 * Associates the collection of properties passed in
	 * <code>properties</code> to the file identified by <code>path</code>
	 * and stores it persistently.
	 * 
	 *The argument <code>path</code> is the pathname of a (set of)
	 * file/directory to which <code>properties</code> is associated with. Any
	 * subsequent property association <span class="rfc2119">must</span>
	 * overwrite the previous property set so that any <code>path</code> is
	 * uniquely associated with one set of properties.</p>
	 * 
	 *No later then when this method returns, the platform <span
	 * class="rfc2119">must</span> have stored persistently the properties.
	 * The implementation <span class="rfc2119">should</span> use the
	 * corresponding <A HREF="http://java.sun.com/javame/reference/apis/jsr219/java/util/Properties.html?is-external=true#store(java.io.OutputStream, java.lang.String)" title="class or interface in java.util"><CODE>Properties.store(OutputStream, String)</CODE></A>
	 * method to process this request.</p>
	 * 
	 *If <code>path</code> does not exist, then the association cannot be
	 * fulfilled and the implementation <span class="rfc2119">must</span>
	 * indicate that by throwing a <code>FileNotFoundException</code>.</p>
	 * 
	 *If the collection of properties is incomplete with respect to the
	 * mandatory properties described above, it is left to the implementation
	 * to generate them at storage or at retrieval time.</p>
	 *
	 * 
	 * @param path - the pathname of the file/directory
	 * @param properties - the properties to associate with that file/directory
	 * @throws NullPointerException - if any of the argument passed is null.
	 * @throws IOException - if writing this property list to the specified output stream throws an IOException.
	 * @throws ClassCastException - if any of the properties stored in the property has either a key or value that is not a String.
	 * @throws FileNotFoundException - if file/directory referenced by path does not exists.
	 */
	public static void store( String path, Properties properties) throws NullPointerException, IOException, ClassCastException, FileNotFoundException
	{
		//TODO implement store
	}

	/**
	 * Loads a copy of the collection of properties associated with a
	 * (set of) file/directory identified by <code>path</code>.
	 * 
	 *The <code>path</code> argument follows the same definition as in
	 * <A HREF="../../../../com/sun/dtv/io/FileProperties.html#store(java.lang.String, java.util.Properties)"><CODE>store(String, Properties)</CODE></A>.
	 * 
	 *When this method is applied on a set of files/directories, it
	 * <span class="rfc2119">must</span> return the least common denominator
	 * of that set of properties. This only applies to properties for which a
	 * least common denominator is defined. For any given property for
	 * which a least common denominator is not defined, this method
	 * <span class="rfc2119">must</span> include in the returned collection
	 * only those which value matches for every single files/directories
	 * within that set.</p>
	 * 
	 *If a file corresponding to a given <code>path</code> had no
	 * collection of properties associated yet, the implementation
	 * <span class="rfc2119">must</span> provide transparently a new
	 * <code>Properties</code> object where the values of the properties are
	 * set to the default values defined in the description of that class.</p>
	 *
	 * 
	 * @param path - the pathname of the file/directory
	 * @return A copy of properties for the given path.
	 * @throws NullPointerException - if path is null.
	 * @throws FileNotFoundException - if path does not point to any existing file.
	 * @throws IllegalArgumentException - if path is an invalid path and does not conform the format given in the class description.
	 */
	public static Properties load( String path) throws NullPointerException, FileNotFoundException, IllegalArgumentException
	{
		return null;
		//TODO implement load
	}

}
