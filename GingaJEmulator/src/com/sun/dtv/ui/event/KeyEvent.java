package com.sun.dtv.ui.event;

import com.sun.dtv.lwuit.Component;

import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;

public class KeyEvent extends java.awt.event.KeyEvent implements UserInputEvent, Serializable
{
	private static HashMap resourceHoldersMap = new HashMap();
	private static HashMap newReserveEventMap = new HashMap();
	
	protected static ArrayList resourceListenersList = new ArrayList();
	
	protected static Vector vector = new Vector();
	private static int availableCount;
	
	static
	{
		//Numeric keys
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_0, '0'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_1, '1'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_2, '2'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_3, '3'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_4, '4'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_5, '5'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_6, '6'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_7, '7'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_8, '8'));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_9, '9'));

		// Back key
		//vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
		//		0, com.sun.dtv.ui.event.RemoteControlEvent.VK_BACK,
		//		java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_BACK_SPACE,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));

		// Colored keys
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_0,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_1,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_2,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_3,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));

		// Arrow keys
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_ARROWS,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_DOWN,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_UP,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_LEFT,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, java.awt.event.KeyEvent.VK_RIGHT,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		
		// OK Key
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_CONFIRM,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));

		//EXIT Key
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_ESCAPE,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));

		// Functions
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_GUIDE,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_MENU,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_INFO,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_PLAY,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_STOP,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		vector.add(new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
				0, com.sun.dtv.ui.event.RemoteControlEvent.VK_RECORD,
				java.awt.event.KeyEvent.CHAR_UNDEFINED));
		
		availableCount = vector.size();
	}

	private Component dtvSource;

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getKeyCode();
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if(!(obj instanceof KeyEvent) && !(obj instanceof RemoteControlEvent))
			return false;
		KeyEvent other = (KeyEvent) obj;
		if (getKeyCode()!= other.getKeyCode())
			return false;
		return true;
	}

	/**
	 * Constructs a KeyEvent object.
	 *
	 * 
	 * @param source - the Component that originated the event
	 * @param id - an integer identifying the type of event
	 * @param when - a long integer that specifies the time the event occurred
	 * @param modifiers - the modifier keys down during event (shift, ctrl, alt,  meta) Either extended _DOWN_MASK or old _MASK modifiers should be used, but both models should not be mixed in one event. Use of the extended modifiers is preferred.
	 * @param keyCode - the integer code for an actual key, or VK_UNDEFINED (for a key-typed event) (as defined in java.awt.event.KeyEvent)
	 * @param keyChar - the Unicode character generated by this event, or CHAR_UNDEFINED (for key-pressed and key-released events which do not map to a valid Unicode character) (as defined in java.awt.event.KeyEvent)
	 * @see KeyEvent
	 */
	public KeyEvent(Component source, int id, long when, int modifiers, int keyCode, char keyChar)
	{
		super(new java.awt.Container(), id, when, modifiers, java.awt.event.KeyEvent.VK_UNDEFINED, keyChar);

		setKeyCode(keyCode);

		this.dtvSource = source;
	}

	public Object getSource() {
		return (Object)dtvSource;
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
	public synchronized void reserve(boolean force, long timeout, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		ScarceResourceListener currentListener = (ScarceResourceListener)resourceHoldersMap.get(this);
		if(listener == null || timeout < -1)
			throw new IllegalArgumentException();
		if(currentListener == null){
			resourceHoldersMap.put(this, listener);
		}else{
			if(force){
				currentListener.releaseForced(this);
			}
			else{
				boolean request = currentListener.releaseRequested(this);
				if(!request){
					//wait for timeoutms - milliseconds
					try {
						if(timeout == -1)
						{
							while(currentListener.releaseRequested(this) != true)
								Thread.sleep(5000);	
						}
						else
						{
							Thread.sleep(timeout);
							request = currentListener.releaseRequested(this);
							if(!request)
								throw new TimeoutException();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			currentListener.released(this);
		}
		resourceHoldersMap.put(this, listener);
		dispatchReservedEvent();
	}
	
	/**
	 * Returns a reserved instance out of the pool of all <code>KeyEvent</code>
	 * instances matching the specified key code. This method either returns with
	 * the instance or throws an exception according to the situation.
	 * 
	 *This method behaves exactly as the <code>reserve()</code> method.</p>
	 *
	 * 
	 * @param keyCode - the integer code for an actual key, or VK_UNDEFINED  (for a key-typed event) (as defined in java.awt.event.KeyEvent)
	 * @param force - If true, this method is allowed to withdraw any given resource from its current owner. If false, the implementation will block and wait until a resource of the given type is made available (using release()) or until timeoutms milliseconds.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for any resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @return The instance of type KeyEvent that has been reserved.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener)
	 */
	public static KeyEvent reserveOne(int keyCode, boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		KeyEvent keyEvent = new KeyEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L, 0, keyCode,
				java.awt.event.KeyEvent.CHAR_UNDEFINED);
		
		keyEvent.reserve(force, timeoutms, listener);
		
		return keyEvent;
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
	public synchronized void release()
	{
		resourceHoldersMap.remove(this);
		dispatchReleasedEvent();
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
		return resourceHoldersMap.get(this) == null;
	}

	/**
	 * Return the list of all existing instances of <A HREF="../../../../../com/sun/dtv/ui/event/KeyEvent.html" title="class in com.sun.dtv.ui.event"><CODE>KeyEvent</CODE></A>, whether they are already reserved or
	 * not.
	 *
	 * 
	 * @return list of all existing instances of KeyEvent
	 */
	public static KeyEvent[] getInstances()
	{
		KeyEvent[] keyEventArray = new KeyEvent[vector.size()];
		for(int i = 0; i < vector.size(); i++){
			keyEventArray[i] = (KeyEvent)vector.get(i);
		}
		
		return keyEventArray;
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
	public static void addResourceTypeListener(ResourceTypeListener listener) throws NullPointerException
	{
		if (!resourceListenersList.contains((Object)listener)) {
			resourceListenersList.add((Object)listener);
		}
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
	public static void removeResourceTypeListener(ResourceTypeListener listener) throws NullPointerException
	{
		if (resourceListenersList.contains((Object)listener)) {
			resourceListenersList.remove((Object)listener);
		}
	}
	
	public void dispatchReservedEvent(){
		availableCount--;
		newReserveEventMap.put(this, new Boolean(true));
		for(int i = 0; i < resourceListenersList.size(); i++){
			((ResourceTypeListener)resourceListenersList.get(i)).reserved(availableCount);
			System.out.println("CHAMOU O PRIMEIRO NEW RESERVED EVENT");
		}
	}
	
	public void dispatchReleasedEvent(){
		availableCount++;
		newReserveEventMap.put(this, new Boolean(false));
		for(int i = 0; i < resourceListenersList.size(); i++){
			((ResourceTypeListener)resourceListenersList.get(i)).released(availableCount);
		}
	}
	
	public boolean wasNewReseved(){
		Boolean b = (Boolean)newReserveEventMap.get(this);
		return b != null && b.booleanValue();
	}
	
	public void setNewReservedEvent(boolean value){
		newReserveEventMap.put(this, new Boolean(value));
	}
}
