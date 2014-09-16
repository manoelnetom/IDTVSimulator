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
package com.sun.dtv.tuner;

import java.util.LinkedList;
import java.util.ArrayList;
import java.awt.EventQueue;

import javax.tv.locator.InvalidLocatorException;
import javax.tv.locator.Locator;
import javax.tv.service.navigation.DeliverySystemType;


import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResource;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;
import com.sun.dtv.transport.TransportStream;

/**
 * 
 */
public class Tuner extends Object implements ScarceResource, TunerListener
{
	//Variaveis referentes a responsabilidade de tuner da classe
	private TransportStream currentTransportStream;
	private TransportStream[] availableTransportStreams;
	private DeliverySystemType deliverySystemType;
	private LinkedList tunerEvents;
	private ArrayList tunerListeners;
	private Boolean reserved;
	private ScarceResourceListener owner;

	//Variaveis referentes a responsabilidade de gerente de tuners da classe
	private static ArrayList resourceListeners;
	private static Tuner masterTuner;
	private static Tuner[] instances;

	private Tuner()
	{
		tunerEvents = new LinkedList();
		tunerListeners = new ArrayList();
		reserved = new Boolean(false);
	}

	/**
	 * Tunes the network interface to the specified transport stream.
	 * This method completes asynchronously.
	 * If the tuning operation is successfully initiated,
	 * <code>TuningInitiatedEvent</code> is sent to the listeners of this
	 * Tuner.
	 * Upon successful completion of the tuning operation,
	 * <code>TuningCompletedEvent</code> is sent to the listeners, and
	 * <code>transportStream</code> will be the Tuner's current transport
	 * stream.
	 * If the tuning operation does not complete successfully,
	 * <code>TuningFailedEvent</code> is sent to the listeners, and
	 * the Tuner's current transport stream is undefined.
	 * If the requested tuning operation cannot be initiated,
	 * <code>TuningException</code> is thrown, no events are posted,
	 * and the current transport stream of the Tuner remains unchanged.
	 *
	 * 
	 * @param transportStream - the transport stream to which to tune
	 * @throws TuningException - if tuning cannot be initiated
	 * @throws IllegalStateException - if the caller has not reserved this Tuner
	 * @throws NullPointerException - if transportStream is null
	 * @see TunerListener
	 */
	public void tune( TransportStream transportStream) throws TuningException
	{
		//TODO implement tune
	}

	/**
	 * Tunes the network interface to the transport stream referenced by
	 * the specified locator.
	 * This method completes asynchronously.
	 * If the tuning operation is successfully initiated,
	 * <code>TuningInitiatedEvent</code> is sent to the listeners of this
	 * Tuner.
	 * Upon successful completion of the tuning operation,
	 * <code>TuningCompletedEvent</code> is sent to the listeners, and
	 * <code>transportStream</code> will be the Tuner's current transport
	 * stream.
	 * If the tuning operation does not complete successfully,
	 * <code>TuningFailedEvent</code> is sent to the listeners, and
	 * the Tuner's current transport stream is undefined.
	 * If the requested tuning operation cannot be initiated,
	 * <code>TuningException</code> is thrown, no events are posted,
	 * and the current transport stream of the Tuner remains unchanged.
	 *
	 * 
	 * @param tsLocator - a locator referencing the transport stream to which to tune
	 * @throws TuningException - if tuning cannot be initiated
	 * @throws InvalidLocatorException - if the specified locator does not reference a transport stream.
	 * @throws IllegalStateException - if the caller has not reserved this Tuner
	 * @throws NullPointerException - if transportStream is null
	 * @see TunerListener
	 */
	public void tune( Locator tsLocator) throws TuningException, InvalidLocatorException
	{
	}

	/**
	 * Returns an array of handlers representing each a separate
	 * <code>Tuner</code> resource available on the platform corresponding
	 * to the network interfaces which may be used for tuning. Each handler
	 * object is unique to both the application and the platform. Each
	 * handler object <span class="rfc2119">may</span> be different to each
	 * other in subsequent calls to the <code>getInstances()</code> method.
	 * The list contains all instances whether they are already reserved or
	 * not.
	 * 
	 *If no network interfaces exist, this method returns a zero-length
	 * array.</p>
	 *
	 * 
	 * 
	 * @return an array of instances of resources of type Tuner.
	 */
	public static Tuner[] getInstances()
	{
		if(instances == null){
			resourceListeners = new ArrayList();
			instances = new Tuner[1];
			masterTuner = new Tuner();
			instances[0] = masterTuner;
		}
		return instances;
	}

	/**
	 * Returns a reserved instance out of the pool of all
	 * physical <code>Tuner</code> instances.
	 * This method either returns with the instance or throws an exception
	 * according to the situation.
	 * 
	 *This method behaves exactly as the <code>reserve()</code> method.</p>
	 *
	 * 
	 * @param force - If true, this method is allowed to withdraw any given resource from its current owner. If false, the implementation will block and wait until a resource of the given type is made available (using release()) or until timeoutms milliseconds.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for any resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @return The instance of type Tuner that has been reserved.
	 * @throws SecurityException - If the application has no permission for the reserve action for the resource it is about to reserve. Also thrown if force is set to true but the application has no permission for the force action.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener)
	 */
	public static Tuner reserveOne(boolean force, long timeoutms, ScarceResourceListener listener) throws SecurityException, IllegalArgumentException, TimeoutException
	{
		return instances[0];
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
		if(listener == null)
			throw new NullPointerException();
		resourceListeners.add(listener);
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
		if(listener == null)
			throw new NullPointerException();
		resourceListeners.remove(listener);
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
	 * @throws SecurityException - If the caller does not have ScarceResourcePermission("tuner", "reserve"), or if force is true and the caller does not have ScarceResourcePermission("tuner", "force").
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve in interface ScarceResource
	 */
	public void reserve(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException, SecurityException
	{
		synchronized(reserved){
			//TODO rever exceções
			if(reserved == Boolean.TRUE){
				return;
			}else{
				owner = listener;
				reserved = Boolean.TRUE;
				dispatchResourceEvent(true);
			}
		}
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
		synchronized(reserved){
			reserved = Boolean.TRUE;
			owner = null;
		}
		dispatchResourceEvent(false);
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

		synchronized(reserved){
			return !reserved.booleanValue() ;
		}
	}

	/**
	 * Reports the transport stream to which the tuner is currently associated.
	 * If the tuner is not currently tuned to a transport stream,
	 * this method returns <code>null</code>.
	 *
	 * 
	 * 
	 * @return Transport stream to which the tuner is currently tuned
	 */
	public TransportStream getCurrentTransportStream()
	{
		return this.currentTransportStream;
	}

	/**
	 * Provides the transport streams that can be accessed through this tuner.
	 * If no such transport streams exist, a zero-length array is returned.
	 *
	 * 
	 * 
	 * @return array of transport streams accessible through this tuner
	 */
	public TransportStream[] getAvailableTransportStreams()
	{
		return this.availableTransportStreams;
	}

	/**
	 * Provides the delivery system type of the Tuner.
	 *
	 * 
	 * 
	 * @return the delivery system type
	 */
	public DeliverySystemType getDeliverySystemType()
	{
		return this.deliverySystemType;
	}

	/**
	 * Subscribes a TunerListener to receive tuning events from this Tuner.
	 *
	 * 
	 * @param listener - the TunerListener to subscribe
	 * @throws NullPointerException - if listener is null
	 * @see removeTunerListener(com.sun.dtv.tuner.TunerListener)
	 */
	public void addTunerListener( TunerListener listener)
	{
		synchronized(tunerListeners){
			if ( !tunerListeners.contains(listener) )
				tunerListeners.add(listener);
		}
	}

	/**
	 * Unsubscribes a TunerListener from receiving tuning events from this Tuner.
	 * If the specified listener is not currently subscribed, this method
	 * does nothing.
	 *
	 * 
	 * @param listener - the TunerListener to unsubscribe
	 * @throws NullPointerException - if listener is null
	 * @see addTunerListener(com.sun.dtv.tuner.TunerListener)
	 */
	public void removeTunerListener( TunerListener listener)	{
		synchronized(tunerListeners){
			if ( !tunerListeners.contains(listener) )
				tunerListeners.remove(listener);
		}
	}

	//Este metodo tem a funcionalidade de receber um evento do parte
	//nativa, de modo a repassar para os listeners java
	public void receiveTuningEvent( TuningEvent e){
		if (e != null)
			dispatchTuningEvent(e);
	}


	private void dispatchTuningEvent(TuningEvent e){
		//Dispachar eventos
		synchronized (tunerListeners) {
			final int SIZE = tunerListeners.size();
			for (int i = 0; i < SIZE; i++) {
				final TunerListener l = (TunerListener) tunerListeners.get(i);
				final TuningEvent event = e;

				EventQueue.invokeLater(new Runnable() {
					public void run() {
					l.receiveTuningEvent( event);
					}
				});

			}
		}
	}

	private void dispatchResourceEvent(boolean tunerReserved ){
		synchronized (resourceListeners) {
			final int SIZE = resourceListeners.size();
			final boolean status = tunerReserved;
			for (int i = 0; i < SIZE; i++) {
				final ResourceTypeListener l = (ResourceTypeListener) resourceListeners.get(i);

				EventQueue.invokeLater(new Runnable() {
					public void run() {
						//TODO Rever ess contador passado com parametro
						if(status)
							l.reserved(0);
						else 
							l.released(0);
					}
				});

			}
		}

	}

}
