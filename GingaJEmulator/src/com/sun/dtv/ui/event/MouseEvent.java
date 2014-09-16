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
package com.sun.dtv.ui.event;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;

/**
 * 
 */
public class MouseEvent extends java.awt.event.MouseEvent implements UserInputEvent
{
	/**
	 * Constant used for specification of a mouse code. Means that the left
	 * mouse button is clicked.
	 *
	 * 
	 */
	public static int MOUSE_LEFT_CLICK ;

	/**
	 * Constant used for specification of a mouse code. Means that the right
	 * mouse button is clicked.
	 *
	 * 
	 */
	public static int MOUSE_RIGHT_CLICK;

	/**
	 * Constant used for specification of a mouse code. Means that the left
	 * mouse button is pressed.
	 *
	 * 
	 */
	public static int MOUSE_LEFT_PRESS;

	/**
	 * Constant used for specification of a mouse code. Means that the right
	 * mouse button is pressed.
	 *
	 * 
	 */
	public static int MOUSE_RIGHT_PRESS;

	/**
	 * Constant used for specification of a mouse code. Means that the left
	 * mouse button is released.
	 *
	 * 
	 */
	public static int MOUSE_LEFT_RELEASE;

	/**
	 * Constant used for specification of a mouse code. Means that the right
	 * mouse button is released.
	 *
	 * 
	 */
	public static int MOUSE_RIGHT_RELEASE;

	/**
	 * Constant used for specification of a mouse code. Means that the mouse
	 * is moved.
	 *
	 * 
	 */
	public static int MOUSE_MOVE;

	/**
	 * Constant used for specification of a mouse code. Means that the mouse
	 * is dragged.
	 *
	 * 
	 */
	public static int MOUSE_DRAG;

	/**
	 * Constant used for specification of a mouse code. Means that the mouse
	 * cursor enters the visible area of the screen controlled by this mouse.
	 *
	 * 
	 */
	public static int MOUSE_ENTER;

	/**
	 * Constant used for specification of a mouse code. Means that the mouse
	 * cursor exits the visible area of the screen controlled by this mouse.
	 *
	 * 
	 * 
	 */
	public static int MOUSE_EXIT;

	//following variables are implicitely defined by getter- or setter-methods:
	private static MouseEvent[] instances;

	/**
	 * Constructs a MouseEvent object.
	 *
	 * 
	 * @param source - the Component that originated the event
	 * @param id - the integer that identifies the event
	 * @param when - a long int that gives the time the event occurred
	 * @param modifiers - the modifier keys down during event (e.g. shift, ctrl,  alt, meta) Either extended _DOWN_MASK or old _MASK modifiers should be used, but both models should not be mixed in one event. Use of the extended modifiers is preferred.
	 * @param x - the horizontal x coordinate for the mouse location
	 * @param y - the vertical y coordinate for the mouse location
	 * @param clickCount - the number of mouse clicks associated with event
	 * @param popupTrigger - a boolean, true if this event is a trigger for a popup menu
	 * @see MouseEvent
	 */
	public MouseEvent( Component source, int id, long when, int modifiers, int x, int y, int clickCount, boolean popupTrigger)
	{
		super(null, id, when, modifiers, x, y, clickCount, popupTrigger);
	}

	/**
	 * Constructs a MouseEvent object.
	 *
	 * 
	 * @param source - the Component that originated the event
	 * @param id - the integer that identifies the event
	 * @param when - a long int that gives the time the event occurred
	 * @param modifiers - the modifier keys down during event (e.g. shift, ctrl,  alt, meta) Either extended _DOWN_MASK or old _MASK modifiers should be used, but both models should not be mixed in one event. Use of the extended modifiers is preferred.
	 * @param x - the horizontal x coordinate for the mouse location
	 * @param y - the vertical y coordinate for the mouse location
	 * @param clickCount - the number of mouse clicks associated with event
	 * @param popupTrigger - a boolean, true if this event is a trigger for a popup menu
	 * @param button - which of the mouse buttons has changed state. NOBUTTON, BUTTON1, BUTTON2 or BUTTON3 (as defined in java.awt.event.MouseEvent.
	 * @see MouseEvent
	 */
	public MouseEvent( Component source, int id, long when, int modifiers, int x, int y, int clickCount, boolean popupTrigger, int button)
	{
		super(null, id, when, modifiers, x, y, clickCount, popupTrigger, button);
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
	 * <a href="../../../../../com/sun/dtv/application/package-summary.html#resourcecleanup">Resource Cleanup</a>
	 * section of the application lifecycle.</p>
	 *
	 * 
	 * @param force - If true, this method will withdraw the resource from the current owner. If false, the implementation will block and wait until the resource is made available (using release()) or until timeoutms.
	 * @param timeout - A positive amount of time in millisecond until which this method will wait for the resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve in interface ScarceResource
	 */
	public void reserve(boolean force, long timeout, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		//TODO implement reserve
	}

	/**
	 * Returns a reserved instance out of the pool of all <code>MouseEvent</code>
	 * instances. This method either returns with the instance or throws an
	 * exception according to the situation.
	 * 
	 *This method behaves exactly as the <code>reserve()</code> method.</p>
	 *
	 * 
	 * @param force - If true, this method is allowed to withdraw any given resource from its current owner. If false, the implementation will block and wait until a resource of the given type is made available (using release()) or until timeoutms milliseconds.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for any resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @return The instance of type MouseEvent that has been reserved.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener)
	 */
	public static MouseEvent reserveOne(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		return null;
		//TODO implement reserveOne
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
		return false;
		//TODO implement isAvailable
	}

	/**
	 * Return the list of all existing instances of <code>MouseEvent</code>,
	 * whether they are already reserved or not.
	 *
	 * 
	 * 
	 * @return list of all existing instances of MouseEvent.
	 */
	public static MouseEvent[] getInstances()
	{
		return instances;
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
		//TODO implement addResourceTypeListener
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
		//TODO implement removeResourceTypeListener
	}

}
