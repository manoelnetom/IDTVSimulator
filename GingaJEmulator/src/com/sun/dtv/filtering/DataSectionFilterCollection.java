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

import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResource;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;
import com.sun.dtv.transport.TransportStream;
import com.sun.dtv.tuner.TuningException;
import java.util.Vector;
import java.util.LinkedList;

/**
 * 
 */
public class DataSectionFilterCollection extends Object implements ScarceResource
{
	//following variables are implicitely defined by getter- or setter-methods:
	private TransportStream source;
	private Vector filters;
	private boolean priority;
	private Object rObj;
	private boolean avaliable;
	private static LinkedList listeners;

	/**
	 * Creates a section filter collection object with an associated number of data section
	 * filters needed to be reserved when the object is to be connected to an active
	 * source of section data.
	 *
	 * 
	 * The object will have a default high resource priority
	 * should the number of section filters available to the package become insufficient.
	 *
	 * 
	 * @param numberOfFilters - the number of section filters needed for the object.
	 */
	public DataSectionFilterCollection(int numberOfFilters)
	{
		source = null;
		this.priority=false;
		this.source=null;
		this.filters = new Vector(numberOfFilters);
		avaliable= false;
	}

	/**
	 * Creates a section filter collection object with an associated number of section
	 * filters needed to be reserved when the object is to be connected to an active
	 * source of data sections.
	 *
	 * 
	 * @param numberOfFilters - the number of section filters needed for the object.
	 * @param highPriority - the resource priority of the object should the number of section filters available to the package become insufficient. High priority is indicated by true and low priority by false.
	 */
	public DataSectionFilterCollection(int numberOfFilters, boolean highPriority)
	{
		this(numberOfFilters);
		this.priority=highPriority;
	}

	/**
	 * Creates a new single section filter object within the parent section
	 * filter collection. On activation (successful
	 * <code>startFiltering</code>) the <code>SingleFilter</code>
	 * object will use section filters from the total specified when the
	 * parent <code>DataSectionFilterCollection</code> was created. The
	 * section filter object will have a buffer suitable to hold a default
	 * long section. newSimpleSectionFilter
	 *
	 * 
	 * 
	 * @return the new SingleFilter object.
	 */
	public SingleFilter newSingleFilter()
	{
		if(this.avaliable)
			return null;
		SingleFilter sf = new SingleFilter();
		filters.add(sf);
		return sf;
		
	}

	/**
	 * Creates a new simple section filter object within the parent section filter collection.
	 * On activation (successful <code>startFiltering</code>) the <code>SingleFilter</code>
	 * object will use section filters from the total specified when the parent
	 * <code>DataSectionFilterCollection</code> was created.
	 *
	 * 
	 * @param sectionSize - specifies the size in bytes of the buffer to be created to hold data captured by the DataSectionFilter. If sections are filtered which are larger than this then the extra data will be dropped and filtering continue without any notification to the application.
	 * @return the new SingleFilter object.
	 */
	public SingleFilter newSingleFilter(int sectionSize)
	{
		if(this.avaliable)
			return null;
		SingleFilter sf = new SingleFilter(sectionSize);
		filters.add(sf);
		return sf;
	}

	/**
	 * Creates a new ring section filter within the parent section filter collection. On
	 * activation (successful <code>startFiltering</code>) the new <code>CircularFilter</code>
	 * object will use section filters from the total specified when the parent
	 * <code>DataSectionFilterCollection</code> was created.
	 *
	 * 
	 * @param numberOfEntries - The number of DataSection objects to be created for use in the circular buffer.
	 * @return the new CircularFilter object.
	 */
	public CircularFilter newCircularFilter(int numberOfEntries)
	{
		if(this.avaliable)
			return null;
		CircularFilter cf = new CircularFilter(numberOfEntries);
		this.filters.add(cf);
		return cf;
		
	}

	/**
	 * Creates a new ring section filter within the parent section filter collection. On
	 * activation (successful <code>startFiltering</code>) the new <code>CircularFilter</code>
	 * object will use section filters from the total specified when the parent
	 * <code>DataSectionFilterCollection</code> was created.
	 *
	 * 
	 * @param numberOfEntries - the number of DataSection objects to be created for use in the circular buffer.
	 * @param sectionSize - the size in bytes of the buffer for each Section object. If sections are filtered which are larger than this then the extra data will be dropped and filtering continue without any notification to the application.
	 * @return the new CircularFilter object.
	 */
	public CircularFilter newCircularFilter(int numberOfEntries, int sectionSize)
	{
		if(this.avaliable)
			return null;
		CircularFilter cf = new CircularFilter(numberOfEntries,sectionSize);
		this.filters.add(cf);
		return cf;
	}

	/**
	 * Creates a new table section filter object within the parent section filter collection. On
	 * activation (successful <code>startFiltering</code>) the new <code>ListFilter</code>
	 * object will use section filters from the total specified when the parent
	 * <code>DataSectionFilterCollection</code> was created. Each <code>DataSection</code> created for the
	 * table section filter object will have a buffer suitable to hold a default long section.
	 *
	 * 
	 * 
	 * @return the new ListFilter object.
	 */
	public ListFilter newListFilter()
	{
		if(this.avaliable)
			return null;
		ListFilter lf = new ListFilter();
		this.filters.add(lf);
		return lf;
	}

	/**
	 * Creates a new table section filter object within the parent section filter collection. On
	 * activation (successful <code>startFiltering</code>) the new <code>ListFilter</code>
	 * object will use section filters from the total specified when the parent
	 * <code>DataSectionFilterCollection</code> was created.
	 *
	 * 
	 * @param sectionSize - specifies the size in bytes of the buffer to be created to hold data captured by the DataSectionFilter. When the first section has been captured and the total number of sections in the table known, each DataSection created will have a buffer of this size. If sections are filtered which are larger than this then the extra data will be dropped and filtering continue without any notification to the application.
	 * @return the new ListFilter object.
	 */
	public ListFilter newListFilter(int sectionSize)
	{
		if(this.avaliable)
			return null;
		ListFilter lf = new ListFilter(sectionSize);
		this.filters.add(lf);
		return lf;
	}

	/**
	 * Connects a DataSectionFilterCollection to a transport stream. The <code>DataSectionFilterCollection</code>
	 * has to reserve the number of section filters specified before.
	 * Any <code>DataSectionFilter</code> objects which are part of the collection concerned and whose
	 * filtering has been started will become active and start filtering the source for sections
	 * matching the specified patterns. A call to <code>connect</code> on a connected <code>DataSectionFilterCollection</code>
	 * will be treated as a disconnect followed by the new connect.
	 *
	 * 
	 * @param stream - specifies the source of data sections for filtering
	 * @param requestData - application specific data for use by the resource notification API
	 * @throws IllegalStateException - if the specified collection of section filters has not been reserved before.
	 * @throws IncompatibleSourceException - if the source is not a valid source of data sections.
	 * @throws TuningException - if the source is not currently tuned to
	 */
	public void connect( TransportStream stream, Object requestData) throws IllegalStateException, IncompatibleSourceException, TuningException
	{
		if(this.avaliable)
			throw new IllegalStateException();
		if(stream==null)
			throw new IncompatibleSourceException();
		
		
		this.rObj=requestData;
		this.source=stream;
	}

	/**
	 * Returns a <code>DataSectionFilterCollection</code> to the disconnected
	 * state. When called for a <code>DataSectionFilterCollection</code> in
	 * the connected state, it disconnects a
	 * <code>DataSectionFilterCollection</code> from a source of data
	 * sections. The section filters held by the
	 * <code>DataSectionFilterCollection</code> will be not be released back to
	 * the environment. Any running filtering operations will be terminated.
	 * This method will have no effect for
	 * <code>DataSectionFilterCollection</code>s already in the
	 * disconnected state.
	 *
	 * 
	 * 
	 * @throws IllegalStateException - if the DataSectionFilterCollection has not been connected before.
	 */
	public void disconnect() throws IllegalStateException
	{
		if(this.avaliable)
			return ;
		if(source==null)
			throw new IllegalStateException();
		this.rObj=null;
		this.source=null;
	}

	/**
	 * Returns the transport stream to which a <code>DataSectionFilterCollection</code> is currently
	 * connected. If the <code>DataSectionFilterCollection</code> is not connected to a transport stream
	 * then the method will return null.
	 *
	 * 
	 * 
	 * @return the Transport Stream source currently connected
	 */
	public TransportStream getSource()
	{
		return this.source;
	}

	/**
	 * Requests reservation of the given scarce resource instance. The
	 * method will block until this instance is available. The method
	 * returns normally only if the reservation succeeded. All other cases
	 * are handled by exceptions.
	 * 
	 *First, if there is a security manager, its
	 * <code>checkPermission</code> method is called with the permission
	 * <code>ScarceResourcePermission(name, "reserve")</code>. If
	 * <code>force</code> is moreover set to <code>true</code>, then
	 * the permission is also checked on <code>ScarceResourcePermission(name,
	 * "force")</code>.</p>
	 * 
	 *During the reservation process, if that resource instance is already
	 * allocated, the implementation <span class="rfc2119">must</span> notify
	 * the current owner of that resource about the reservation request via
	 * the <code>ScarceResourceListener</code> interface:</p>
	 * 
	 * <ul>
	 * 
	 * <li>either by
	 * <code>ScarceResourceListener.releaseRequested()</code> if
	 * <code>force</code> is <code>false</code>,</li>
	 * 
	 * <li>or by
	 * <code>ScarceResourceListener.releaseForced()</code> in the
	 * other case.</li>
	 * 
	 * </ul>
	 * 
	 *The listener will be used for such notification only until this
	 * resource is released. After releasing, the implementation <span
	 * class="rfc2119">must not</span> call any of the listener's interface
	 * methods.</p>
	 * 
	 *After that first event, the implementation will proceed accordingly
	 * and release (or not) the requested resource. In case the
	 * implementation releases the resource, it will trigger a
	 * <code>ScarceResourceListener.released()</code> event to the
	 * original listening owner of the resource to inform him that the
	 * resource does not belong to him anymore.</p>
	 * 
	 *The application may control the time to wait for such a resource
	 * to be available by setting <code>timeoutms</code>. In case the
	 * duration of the reservation exceeds the time expressed in
	 * <code>timeoutms</code>, a <code>TimeoutException</code> is thrown.</p>
	 * 
	 *Under normal operation, resources are released using the
	 * <code>release</code> method. However, in the case where the application
	 * does not release resources when requested or the application is
	 * terminated, the implementation <span class="rfc2119">must</span> release
	 * all resources allocated to the application to allow other applications
	 * to be notified of changes in resource allocation and to be able to
	 * reserve them. See the
	 * <a href="../../../../com/sun/dtv/application/package-summary.html#resourcecleanup">Resource Cleanup</a>
	 * section of the application lifecycle.</p>
	 *
	 * 
	 * @param force - If true, this method will withdraw the resource from the current owner. If false, the implementation will block and wait until the resource is made available (using release()) or until timeoutms.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for the resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @throws SecurityException - If the application has no permission for the reserve action for the resource it is about to reserve. Also thrown if force is set to true but the application has no permission for the force action.
	 * @see reserve in interface ScarceResource
	 */
	public void reserve(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException, SecurityException
	{
		if(listener == null || timeoutms < 0)
			throw new IllegalArgumentException();
		if(force){
			listener.releaseForced(this);
			this.release();
		}else{
			listener.releaseRequested(this);
		}
		this.avaliable=false;
		listener.released(this);
	}

	/**
	 * Releases this resource.
	 * 
	 *The resource will be made available to another application across
	 * the platform. After calling this method, it is no more possible to
	 * interact with the given resource: calls to critical methods of that
	 * scarce resource must be ignored and <span class="rfc2119">may</span>
	 * throw <code>IllegalStateException</code>. This assertion is valid and
	 * the behavior required for any class implementing the
	 * <code>ScarceResource</code> interface. In order to interact
	 * again with the given resource, the application must call the
	 * <code>reserve()</code> method and become the owner again.</p>
	 * 
	 *The implementation <span class="rfc2119">may</span> dispose any
	 * system resources that this object is using. After the implementation
	 * <span class="rfc2119">must</span> not call any of the methods of the
	 * listener that was attached at reservation time.</p>
	 * 
	 *If the resource was already available (i.e. not reserved), this
	 * method does nothing.</p>
	 *
	 * 
	 * @see release in interface ScarceResource
	 */
	public void release()
	{
		if(this.avaliable)
			return;
		this.avaliable=true;
		this.filters.clear();
		//TODO implement release
	}

	/**
	 * Checks whether the given resource is currently available for
	 * reservation. The returned value gives the current situation
	 * and does not guarantee that the resource will still be available
	 * at a later moment e.g. at reservation time: another application may
	 * have taken that resource in the meantime.
	 *
	 * 
	 * @return A boolean set to true if the given resource is currently available for reservation.
	 * @see isAvailable in interface ScarceResource
	 */
	public boolean isAvailable()
	{
		return this.avaliable;
		
	}

	/**
	 * Adds a <code>ResourceTypeListener</code> to the implementation.
	 * Whenever a <code>reserve()</code> or a <code>release()</code> is
	 * called on any resources of the same type, the implementation will
	 * call the listener's corresponding methods.
	 *
	 * 
	 * @param listener - The listener that is triggered for events on resources of the same type.
	 * @throws NullPointerException - If listener is null.
	 * @see removeResourceTypeListener(com.sun.dtv.resources.ResourceTypeListener)
	 */
	public static void addResourceTypeListener( ResourceTypeListener listener) throws NullPointerException
	{
		if(listener==null)
			throw new NullPointerException();
		DataSectionFilterCollection.listeners.add(listener);
	}

	/**
	 * Removes a previously attached listener. If the listener was not attached
	 * before, this method does nothing.
	 *
	 * 
	 * @param listener - The listener that is triggered for events on resources of the same type.
	 * @throws NullPointerException - If listener is null.
	 * @see addResourceTypeListener(com.sun.dtv.resources.ResourceTypeListener)
	 */
	public static void removeResourceTypeListener( ResourceTypeListener listener) throws NullPointerException
	{
		if(listener==null)
			throw new NullPointerException();
		DataSectionFilterCollection.listeners.remove(listener);
	}

}
