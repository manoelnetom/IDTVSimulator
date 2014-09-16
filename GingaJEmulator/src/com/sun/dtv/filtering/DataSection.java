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
public class DataSection extends Object implements Cloneable
{
	//following variables are implicitely defined by getter- or setter-methods:
//	private DataSectionDataBuffer sectionData;
	private int tableId;
	private boolean sectionSyntaxIndicator;
	private boolean privateIndicator;
	private int sectionLength;
	private int tableIdExtension;
	private short versionNumber;
	private boolean currentNextIndicator;
	private int sectionNumber;
	private int lastSectionNumber;
	private boolean available;
	private DataSectionDataBuffer buffer;
	
	
	public DataSection(DataSectionDataBuffer buffer){
		this.buffer = buffer;
		this.available=true;
	}
	public DataSection(){
		this.buffer = new DataSectionDataBuffer();
		this.available=false;
	}
	/**
	 * Retrieve the data from the filtered section in the <code>DataSection</code> object.
	 * A new copy of the section data will be created for each call
	 * (i.e. everything after the length field, including the section header and not including a CRC check).
	 *
	 * 
	 * 
	 * @return A DataSectionDataBuffer containing the requested data
	 * @throws DataUnavailableException - if no valid data is available.
	 */
	public DataSectionDataBuffer getSectionData() throws DataUnavailableException
	{
		if(!this.getAvailable())
			throw new DataUnavailableException();
		byte newData[] = new byte[this.getSectionLength()-4];
		byte data[] = this.buffer.getData();
		for(int i =0;i<this.sectionLength-4;i++){
			newData[i] = data[i];
		}
		DataSectionDataBuffer newBuffer = new DataSectionDataBuffer();
		newBuffer.setData(newData);
		return newBuffer;
		
	}

	/**
	 * Retrieve data from the filtered section using the provided index and length.
	 * A new copy of the section data will be created for each call
	 * (i.e. everything after the length field, not including a CRC check).
	 *
	 * 
	 * @param index - start index (the first byte of section (the table_id field) has index 1)
	 * @param length - the length
	 * @return A DataSectionDataBuffer containing the requested data
	 * @throws DataUnavailableException - if no valid data is available.
	 * @throws IndexOutOfBoundsException - if the requested data is not in the range of data in the section.
	 */
	public DataSectionDataBuffer getSectionData(int index, int length) throws DataUnavailableException, IndexOutOfBoundsException
	{
		if(!this.getAvailable())
			throw new DataUnavailableException();
		if(index<1 || length>this.getSectionLength())
			throw new IndexOutOfBoundsException();
		byte newData[] = new byte[this.getSectionLength()];
		byte data[] = this.buffer.getData();
		for(int i =0;i<this.sectionLength-4;i++){
			newData[i] = data[i];
		}
		DataSectionDataBuffer newBuffer = new DataSectionDataBuffer();
		newBuffer.setData(newData);
		return newBuffer;
		
	}

	/**
	 * Retrieve a single data byte at the provided index from the filtered section.
	 *
	 * 
	 * @param index - the index (the first byte of section (the table_id field) has index 1)
	 * @return the byte value at the specified index of section data
	 * @throws DataUnavailableException - if no valid data is available.
	 * @throws IndexOutOfBoundsException - if the requested data is not in the range of data in the section.
	 */
	public byte getSectionByte(int index) throws DataUnavailableException, IndexOutOfBoundsException
	{
		if(!this.getAvailable())
			throw new DataUnavailableException();
		if(index<1 || index>this.getSectionLength())
			throw new IndexOutOfBoundsException();
		byte data[] = this.buffer.getData();
		return data[index-1];
	}

	/**
	 * Retrieve the value of the corresponding field from an data section
	 * header.
	 * 
	 *Note: No exceptions are thrown as this field should ALWAYS be
	 * available.</p>
	 *
	 * 
	 * 
	 * @return the table_id value
	 */
	public int getTableId()
	{
		byte data[] = this.buffer.getData();
		this.tableId = data[0];
		return this.tableId;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section
	 * header.
	 * 
	 *Note: No exceptions are thrown as this field should ALWAYS be
	 * available.</p>
	 *
	 * 
	 * 
	 * @return whether the section syntax indicator is set in the section data
	 */
	public boolean getSectionSyntaxIndicator()
	{
		byte data[] = this.buffer.getData();
		if((data[1] & 0x80)==0x80)
			this.sectionSyntaxIndicator = true;
		else
			this.sectionSyntaxIndicator = false;
		return this.sectionSyntaxIndicator;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section
	 * header.
	 * 
	 *Note: No exceptions are thrown as this field should ALWAYS be
	 * available.</p>
	 *
	 * 
	 * 
	 * @return whether the private indicator flag is set in the section data
	 */
	public boolean getPrivateIndicator()
	{
		byte data[] = this.buffer.getData();
		if((data[1] & 0x40)==0x40)
			this.privateIndicator = true;
		else
			this.privateIndicator = false;
		return this.privateIndicator;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section
	 * header.
	 * 
	 *Note: No exceptions are thrown as this field should ALWAYS be
	 * available.</p>
	 *
	 * 
	 * 
	 * @return the section length field
	 */
	public int getSectionLength()
	{
		
		byte[] data = this.buffer.getData();
		this.sectionLength = data[1]& 0x0F;
	        this.sectionLength <<= 8;
		this.sectionLength = this.sectionLength | data[2];
		return this.sectionLength;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section header.
	 *
	 * 
	 * 
	 * @return the table_id_extension field
	 * @throws DataUnavailableException - if no valid data is available.
	 */
	public int getTableIdExtension() throws DataUnavailableException
	{
		if((!this.getAvailable()) || !this.getPrivateIndicator())
			throw new DataUnavailableException();
		byte[] data = this.buffer.getData();
		this.tableIdExtension = data[3]& 0x0F;
		this.tableIdExtension <<= 8;
		this.tableIdExtension = this.tableIdExtension | data[4] ;
		
		return this.tableIdExtension;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section header.
	 *
	 * 
	 * 
	 * @return the version number field
	 * @throws DataUnavailableException - if no valid data is available.
	 */
	public short getVersionNumber() throws DataUnavailableException{

		if(!this.getAvailable())
			throw new DataUnavailableException();
		byte[] data = this.buffer.getData();
		this.versionNumber = (short)(data[5] & 0x3E);
		return this.versionNumber;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section header.
	 *
	 * 
	 * 
	 * @return whether the current_next_indicator flag is set in the section data
	 * @throws DataUnavailableException - if no valid data is available.
	 */
	public boolean getCurrentNextIndicator() throws DataUnavailableException
	{
		if(!this.getAvailable())
			throw new DataUnavailableException();
		byte[] data = this.buffer.getData();
		if((data[5] & 0x01)==0x01)
			this.currentNextIndicator = true;
		else
			this.currentNextIndicator=false;
		return this.currentNextIndicator;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section header.
	 *
	 * 
	 * 
	 * @return the section number
	 * @throws DataUnavailableException - if no valid data is available.
	 */
	public int getSectionNumber() throws DataUnavailableException
	{
		if(!this.getAvailable())
			throw new DataUnavailableException();
		byte[] data = this.buffer.getData();
		this.sectionNumber = data[6];
		return this.sectionNumber;
	}

	/**
	 * Retrieve the value of the corresponding field from a data section header.
	 *
	 * 
	 * 
	 * @return the last_section_number field
	 * @throws DataUnavailableException - if no valid data is available.
	 */
	public int getLastSectionNumber() throws DataUnavailableException
	{
		if(!this.getAvailable())
			throw new DataUnavailableException();
		byte[] data = this.buffer.getData();
		this.lastSectionNumber = data[7];
		return this.lastSectionNumber;
	}

	/**
	 * Retrieve whether a <code>DataSection</code> object contains valid data.
	 *
	 * 
	 * 
	 * @return whether the section data is valid and available for reading
	 */
	public boolean getAvailable()
	{
		return this.available;
	}

	/**
	 * This method marks section data object for use because the data is no longer useful
	 * or it has been cloned. This allows <code>RingFilter</code> objects to re-use
	 * the section data object for a subsequent filter match.
	 *
	 * 
	 */
	public void release()
	{
		this.available=true;
	}

	/**
	 * Create a copy of this <code>DataSection</code> object.
	 * 
	 * Any modifications to this copied object will not affect the original object that
	 * has been cloned.
	 *
	 * 
	 * @return The cloned object
	 * @see clone in class Object
	 */
	public Object clone()
	{
		DataSection newSection = new DataSection(this.buffer);
		this.release();
		return newSection;
		
	}
	
	

}
