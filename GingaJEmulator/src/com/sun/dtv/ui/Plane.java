package com.sun.dtv.ui;

import br.org.sbtvd.resources.ScarceResourceImpl;

import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;
import com.sun.dtv.ui.event.PlaneSetupListener;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Plane extends ScarceResourceImpl
{

	private List setupListeners;
	protected List resourceListeners;

	protected String id;
	protected PlaneSetup[] setups;
	protected Capabilities capabilities;
	protected PlaneSetup currentSetup;
        
        protected DTVContainer dtvContainer;
	
	/**
	 * For use of specific plane classes subclassing <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A>.
	 *
	 * 
	 */
	protected Plane(String id)
	{
		this.id = id;

		setupListeners = new ArrayList();
		resourceListeners = new ArrayList();
	}

	/**
	 * Returns this <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane's</CODE></A> identification string.
	 *
	 * 
	 * 
	 * @return an identification string
	 */
	public String getID()
	{
		return this.id;
	}

	/**
	 * Returns all available <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A> objects
	 * associated with this <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A>.
	 * Some of these may be valid for this plane only at particular times
	 * or for particular modes of the plane.
	 *
	 * 
	 * 
	 * @return an array of PlaneSetup objects
	 * @see PlaneSetup
	 */
	public PlaneSetup[] getSetups()
	{
		return this.setups;
	}

	/**
	 * Returns the default <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>
	 * associated with this <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A>. There is only
	 * one default setup, this could be e.g. a minimal setup.
	 *
	 * 
	 * 
	 * @return the default PlaneSetup of this  Plane
	 * @see PlaneSetup
	 */
	public PlaneSetup getDefaultSetup()
	{
		PlaneSetup setups[] = getSetups();

		if (setups != null) {
			return setups[0];
		}

		return null;
	}

	/**
	 * Returns the best possible setup taking into account the criteria defined
	 * in this <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetupPattern</CODE></A> or null.
	 *
	 * For details of the algorithm for the selection of a setup, see <A HREF="../../../../com/sun/dtv/ui/Screen.html#getBestSetup(com.sun.dtv.ui.PlaneSetupPattern[])"><CODE>getBestSetup(PlaneSetupPattern[])</CODE></A>
	 *
	 * 
	 * @param pattern - - a PlaneSetupPattern object used to deliver criteria for a valid  PlaneSetup. If this parameter is null the  default setup is choosen.
	 * @return a PlaneSetup object fulfilling the criteria as specified in the PlaneSetupPattern or null if there is none.
	 */
	public PlaneSetup getBestSetup(PlaneSetupPattern pattern)
	{
		for(int i = 0; i < setups.length; i++){
			/* takes in consideration only REQUIRED and REQUIRED_NOT priorities. */
			if(setups[i].getPattern().implies(pattern)){
				return setups[i];
			}
		}
		
		return null;
	}

	/**
	 * Returns the best possible setup taking into account the criteria
	 * defined in one of the <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetupPattern</CODE></A> objects within the array argument or  <code>null</code>.
	 * The <code>PlaneSetupPattern</code> objects are considered in the order
	 * they appear in the parameter array.
	 *
	 * For details of the algorithm for the selection of a setup, see <A HREF="../../../../com/sun/dtv/ui/Screen.html#getBestSetup(com.sun.dtv.ui.PlaneSetupPattern[])"><CODE>getBestSetup(PlaneSetupPattern[])</CODE></A>
	 *
	 * 
	 * @param patterns - the PlaneSetupPattern array used to deliver criteria for a valid  PlaneSetup.
	 * @return a PlaneSetup object fulfilling the criteria as specified in one of the PlaneSetupPattern objects within the paramter array
	 */
	public PlaneSetup getBestSetup(PlaneSetupPattern[] patterns)
	{
		/* FIXME: algorithm return when the first pattern fits in plane setup. */
		for(int i = 0; i < patterns.length; i++){
			for(int j = 0; i < setups.length; j++){
				/* takes in consideration only REQUIRED and REQUIRED_NOT priorities. */
				if(setups[j].getPattern().implies(patterns[i])){
					return setups[j];
				}
			}
		}
		
		return null;
	}

	/**
	 * Returns the current <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>
	 * for this <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A>.
	 *
	 * 
	 * 
	 * @return the current PlaneSetup for this  Plane.
	 * @see PlaneSetup
	 */
	public PlaneSetup getCurrentSetup()
	{
		return getDefaultSetup();
	}

	/**
	 * Set the setup for this Plane.
	 *
	 * As the Plane of a Screen is a ScarceResource, this method can only be
	 * called by an application which has reserved this plane before using
	 * the appropriate means provided by the ScarceResource mechanism.
	 * Calling this method is also subject to the platform's security policy,
	 * and the following rules decide about a successful call:
	 * <ul>
	 * <li>Choosing a setup being not valid for that plane at that time or due
	 * to a particular mode the plane is in causes an <code>SetupException</code>
	 * to be thrown.
	 * <li>Choosing a setup that is not conflicting with any setup of one of the
	 * other planes on the same <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> causes this
	 * setup to be selected.
	 * <li>Choosing a setup that conflicts with any setup of one of the other
	 * planes on this <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> not being under control
	 * of the calling application due to platform security policy causes a
	 * <code>SecurityException</code> to be thrown.
	 * <li>Choosing a setup that conflicts with any setup of one of the other
	 * planes on this <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> not being under control
	 * of the calling application due to another application owning the right to
	 * control the other plane and the platform not giving that right to this
	 * application causes a <code>SecurityException</code> to be thrown.
	 * <li>Choosing a setup that conflicts with any setup of one of the other
	 * planes on this <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> being under control
	 * of the calling application due to either this or no application
	 * having reserved the plane, then the setup of the other plane changes
	 * automatically (without any effect to the resource ownership of the other
	 * plane).
	 * </ul>
	 * If the setup of another plane has been changed as a consequence to the
	 * call of this method, the change should be reflected by calling an
	 * appropriate method of the concerned plane's API.
	 *
	 * Applications can prevent or limit changes to setups of other, not
	 * intended, planes by using constants
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#NO_GRAPHICS_IMPACT"><CODE>PlaneSetupPattern.NO_GRAPHICS_IMPACT</CODE></A>,
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#NO_SUBTITLE_IMPACT"><CODE>PlaneSetupPattern.NO_SUBTITLE_IMPACT</CODE></A>,
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#NO_VIDEO_IMPACT"><CODE>PlaneSetupPattern.NO_VIDEO_IMPACT</CODE></A>,
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#NO_STILLVIDEO_IMPACT"><CODE>PlaneSetupPattern.NO_STILLVIDEO_IMPACT</CODE></A> and
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#NO_BACKGROUND_IMPACT"><CODE>PlaneSetupPattern.NO_BACKGROUND_IMPACT</CODE></A>
	 * in their setup patterns.
	 *
	 * If changing the specified setup has been successful, the plane
	 * shall fire at least one <A HREF="../../../../com/sun/dtv/ui/event/PlaneSetupEvent.html" title="class in com.sun.dtv.ui.event"><CODE>PlaneSetupEvents</CODE></A> for all currently registered listeners.
	 *
	 * 
	 * @param setup - the PlaneSetup to which this plane should  be set.
	 * @return A boolean indicating whether the setup has been successfully changed. Please be aware that in case the return value is false, there is no warranty that the setup of the plane is the same one as it has been before the call
	 * @throws SecurityException - if the application currently does not have sufficient rights to change the setup for this plane
	 * @throws SetupException - if the specified setup is either not a valid one for this plane, or if it conflicts with other planes' setups and this conflict cannot be solved
	 */
	public boolean setPlaneSetup(PlaneSetup setup) throws SecurityException, SetupException
	{
		if(getDefaultSetup().equals(setup)){
			this.currentSetup = setup;
			return true;
		}else{
			return false;	
		}
	}

	/**
	 * Add an <A HREF="../../../../com/sun/dtv/ui/event/PlaneSetupListener.html" title="interface in com.sun.dtv.ui.event"><CODE>PlaneSetupListener</CODE></A> to
	 * this plane. This listener will be notifier whenever the plane's setup
	 * will be modified.  If one listener has already been added before, further
	 * calls to this method will add further references to the same listener.
	 * As a consequence, these references will then receive multiple copies of
	 * one single event.
	 *
	 * 
	 * @param listener - the PlaneSetupListener to be added to this plane.
	 * @see removePlaneSetupListener(com.sun.dtv.ui.event.PlaneSetupListener)
	 */
	public void addPlaneSetupListener(PlaneSetupListener listener)
	{
		setupListeners.add((Object)new PlaneSetupListenerAndPattern(listener));
	}

	/**
	 * Add an <A HREF="../../../../com/sun/dtv/ui/event/PlaneSetupListener.html" title="interface in com.sun.dtv.ui.event"><CODE>PlaneSetupListener</CODE></A> to
	 * this plane. This listener will be notified whenever the plane's setup
	 * will be modified so that it is no longer compatible with the specified
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetupPattern</CODE></A>.
	 * If one listener has already been added before, further
	 * calls to this method will add further references to the same listener.
	 * As a consequence, these references will then receive multiple copies of
	 * one single event.
	 *
	 * Note that if the plane setup does not match the specified pattern, then
	 * the listener should be added and a <A HREF="../../../../com/sun/dtv/ui/event/PlaneSetupEvent.html" title="class in com.sun.dtv.ui.event"><CODE>PlaneSetupEvent</CODE></A> immediately generated
	 * for the specified <A HREF="../../../../com/sun/dtv/ui/event/PlaneSetupListener.html" title="interface in com.sun.dtv.ui.event"><CODE>PlaneSetupListener</CODE></A>.
	 *
	 * 
	 * @param listener - the PlaneSetupListener to be added to this plane.
	 * @param pattern - the PlaneSetupPattern  which is to be used to determine compatibility with the plane's setup.
	 * @see removePlaneSetupListener(com.sun.dtv.ui.event.PlaneSetupListener)
	 */
	public void addPlaneSetupListener(PlaneSetupListener listener, PlaneSetupPattern pattern)
	{
		setupListeners.add((Object)new PlaneSetupListenerAndPattern(listener, pattern));
	}

	/**
	 * Remove an <A HREF="../../../../com/sun/dtv/ui/event/PlaneSetupListener.html" title="interface in com.sun.dtv.ui.event"><CODE>PlaneSetupListener</CODE></A>
	 * from this plane. If the specified listener has not been registered
	 * before, the method has no effect.
	 * If multiple references to a single listener have been
	 * registered, any call to this method will only remove one reference.
	 *
	 * 
	 * @param listener - the PlaneSetupListener to be removed from this plane.
	 * @see addPlaneSetupListener(com.sun.dtv.ui.event.PlaneSetupListener),  addPlaneSetupListener(com.sun.dtv.ui.event.PlaneSetupListener, com.sun.dtv.ui.PlaneSetupPattern)
	 */
	public void removePlaneSetupListener(PlaneSetupListener listener)
	{
		for (Iterator i=setupListeners.iterator(); i.hasNext(); ) {
			PlaneSetupListenerAndPattern o = (PlaneSetupListenerAndPattern)i.next();

			if (o.getPlaneSetupListener() == listener) {
				setupListeners.remove((Object)o);

				break;
			}
		}
	}

	/**
	 * Register a listener for events about changes in the state of the
	 * ownership of this plane. If this listener has already been added before,
	 * further calls will add further references to the listener. As a
	 * consequence, these references will receive multiple copies of one single
	 * event.
	 *
	 * 
	 * @param listener - the object to be informed of state changes
	 * @see removeScarceResourceListener(com.sun.dtv.resources.ScarceResourceListener)
	 */
	public void addScarceResourceListener(ScarceResourceListener listener)
	{
		if (!resourceListeners.contains((Object)listener)) {
			resourceListeners.add((Object)listener);
		}
	}

	/**
	 * Remove a listener for events about changes in the state of the ownership
	 * of this plane. This method has no effect if the specified listener has
	 * not been registered before.
	 *
	 * 
	 * @param listener - the object which is no longer interested
	 * @see addScarceResourceListener(com.sun.dtv.resources.ScarceResourceListener)
	 */
	public void removeScarceResourceListener( ScarceResourceListener listener)
	{
		if (resourceListeners.contains((Object)listener)) {
			resourceListeners.remove((Object)listener);
		}
	}


	/**
	 * Returns a reserved instance out of the pool of all <code>Plane</code>
	 * instances. This method either returns with the instance or throws an
	 * exception according to the situation.
	 * 
	 *This method behaves exactly as the <code>reserve()</code> method.</p>
	 *
	 * 
	 * @param force - If true, this method is allowed to withdraw any given resource from its current owner. If false, the implementation will block and wait until a resource of the given type is made available (using release()) or until timeoutms milliseconds.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for any resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @return The instance of type Plane that has been reserved.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener)
	 */
	public static Plane reserveOne(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		Plane plane = Device.getInstance().getDefaultScreen().getAllPlanes()[0];
		plane.reserve(force, timeoutms, listener);
		
		return plane;
	}
	/**
	 * Returns the <A HREF="../../../../com/sun/dtv/ui/Capabilities.html" title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> object associated with
	 * this <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A>.
	 *
	 * 
	 * 
	 * @return the Capabilities object associated with this Plane
	 */
	public Capabilities getCapabilities()
	{
		return this.capabilities;
	}

	/**
	 * Returns the <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A> the caller
	 * is displayed on.
	 *
	 * 
	 * 
	 * @return the current screen
	 */
	public static Plane getCurrentPlane()
	{
		Plane[] planes = getInstances();

		for (int i=0; i<planes.length; i++) {
			if (planes[i].getID().equals("GraphicPlane"))
			{
				return planes[i];
			}
		}

		return null;
	}

	/**
	 * Return the list of all existing instances of <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A>, whether they are already reserved or not.
	 *
	 * 
	 * 
	 * @return list of all existing instances of Plane
	 */
	public static Plane[] getInstances()
	{
		return Device.getInstance().getDefaultScreen().getAllPlanes();
	}

	class PlaneSetupListenerAndPattern {
			private PlaneSetupListener listener;
			private PlaneSetupPattern pattern;

			public PlaneSetupListenerAndPattern(PlaneSetupListener listener) {
				this.listener = listener;
				this.pattern = null;
			}

			public PlaneSetupListenerAndPattern(PlaneSetupListener listener, PlaneSetupPattern pattern) {
				this.listener = listener;
				this.pattern = pattern;
			}

			public PlaneSetupListener getPlaneSetupListener() {
				return listener;
			}

			public PlaneSetupPattern getPlaneSetupPattern() {
				return pattern;
			}
	}
        
        //Provides the DTVContainer for this plane
        DTVContainer getDTVContainer()
        {
            return this.dtvContainer;
        }
}

