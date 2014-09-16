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

import java.util.Arrays;

/**
 * 
 */
public class DataSectionDataBuffer extends Object
{
	/**
	 * A flag mask that describes the boolean attributes enabled for this <CODE>Buffer</CODE>.
	 * This mask is set to the logical sum of all of the flags that are set.
	 *
	 *
	 * 
	 */
	protected int flags;

	/**
	 * The byte array that actually holds the media data chunk for this <CODE>Buffer</CODE>.
	 *
	 * 
	 */
	protected byte[] data;

	/**
	 * States how many samples are valid in the array.
	 * (The array itself might be larger than the length).
	 *
	 * 
	 */
	protected int length;

	/**
	 * Points to the starting point (offset) into the array where the valid
	 * data begins.
	 *
	 * 
	 */
	protected int offset;

	/**
	 * Indicates that this data is a copy or clone and changing it will not
	 * affect the data it was generated from.
	 *
	 *
	 * 
	 * 
	 */
	public static final int FLAG_IS_COPY = 1;

	/**
	 * 
	 * 
	 */
	public DataSectionDataBuffer()
	{
		this.data = new byte[4096];
	}

	/**
	 * Gets the mask of the flags set for this <CODE>Buffer</CODE>.
	 * The integer value of the mask is equal to the logical sum of
	 * the flags that are set.
	 *
	 * 
	 * 
	 * @return the currently active flags
	 * @see FLAG_IS_COPY,  setFlags(int)
	 */
	public int getFlags()
	{
		return this.flags;
	}

	/**
	 * Sets the flag mask for this <CODE>Buffer</CODE>.
	 * The integer value of the mask is equal to the logical sum of
	 * the flags that are set.
	 *
	 * 
	 * @param flags - The specific flags to be set for the buffer object
	 * @see FLAG_IS_COPY,  getFlags()
	 */
	public void setFlags(int flags)
	{
		this.flags = flags;
	}

	/**
	 * Gets the internal byte array for this <CODE>Buffer</CODE>.
	 *
	 * 
	 * 
	 * @return The byte array for this Buffer.
	 * @see data,  setData(byte[])
	 */
	public byte[] getData()
	{
		return this.data;
	}

	/**
	 * Sets the internal byte array for this <CODE>Buffer</CODE>.
	 *
	 * 
	 * @param data - the byte array to be set
	 * @see data,  getData()
	 */
	public void setData(byte[] data)
	{
		this.data = data;
	}

	/**
	 * Gets the length of the valid data in this <CODE>Buffer</CODE>.
	 *
	 * 
	 * 
	 * @return The length of the valid data in the data byte array. for this Buffer.
	 * @see length, setLength(int)
	 */
	public int getLength()
	{
		return this.length;
	}

	/**
	 * Sets the length of the valid data in this <CODE>Buffer</CODE>.
	 *
	 * 
	 * @param length - The length of the valid data in the data byte array.
	 * @see length,  getLength()
	 */
	public void setLength(int length)
	{
		this.length = length;
	}

	/**
	 * Gets the offset of the valid data in this <CODE>Buffer</CODE>.
	 *
	 * 
	 * 
	 * @return The starting point of the valid data in the data byte array.
	 * @see offset,  setOffset(int)
	 */
	public int getOffset()
	{
		return this.offset;
	}

	/**
	 * Sets the offset of the valid data in this <CODE>Buffer</CODE>.
	 *
	 * 
	 * @param offset - The starting point of the valid data in the data  byte array.
	 * @see offset, getOffset()
	 */
	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	/**
	 * Copy the attributes from the specified <CODE>Buffer</CODE> into this
	 * <CODE>Buffer</CODE>.
	 *
	 * 
	 * @param buffer - The input Buffer the copy the attributes from.
	 */
	public void copy( DataSectionDataBuffer buffer)
	{
		byte[] cpy = new byte[this.getLength()];
		for(int i =0;i<this.getLength();i++)
			cpy[i] = this.data[i];
		buffer.setData(cpy);
	}

	/**
	 * Copy the attributes from the specified <CODE>Buffer</CODE> into this
	 * <CODE>Buffer</CODE>. If swapData is true, the data values are swapped
	 * between the buffers, otherwise the data value is copied.
	 *
	 * 
	 * @param buffer - The input Buffer the copy the attributes  from.
	 * @param swapData - Specifies whether the data objects are to be swapped.
	 */
	public void copy( DataSectionDataBuffer buffer, boolean swapData)
	{
		//TODO implement copy
	}

	/**
	 * Clone a buffer.
	 *
	 * 
	 * @return the cloned object
	 * @see clone in class Object
	 */
	public Object clone()
	{
		DataSectionDataBuffer cln = new DataSectionDataBuffer();
		cln.setData(getData());
		cln.setFlags(getFlags());
		cln.setLength(getLength());
		cln.setOffset(getOffset());
		return cln;
		
	}
	/**
	 * The <code>String</code> representation of the object.
	 *
	 * 
	 * @return the string representation
	 * @see toString in class Object
	 */
	public String toString() {
		return "DataSectionDataBuffer [flags=" + flags + ", length=" + length + ", offset="
				+ offset + "]";
	}

	
	

}
