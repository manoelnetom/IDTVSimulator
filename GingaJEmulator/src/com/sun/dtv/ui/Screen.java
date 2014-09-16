package com.sun.dtv.ui;

import br.org.sbtvd.resources.ScarceResourceImpl;
import com.sun.dtv.lwuit.Display;

import com.sun.dtv.lwuit.geom.Dimension;

import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;

import com.sun.dtv.ui.event.UserInputEventManager;

import java.util.ArrayList;

public class Screen extends ScarceResourceImpl
{
	private static Screen[] availableScreens;
	
	private static java.util.List scarceResourceListeners = new ArrayList();

	private Plane[] allPlanes;
	private Dimension screenAspectRatio;
	private UserInputDevice[] supportedUserInputDevices;
	private Keyboard keyboard = null;
	private RemoteControl remoteControl = null;
	private Mouse mouse = null;
	private UserInputEventManager userInputEventManager;
	
	private Runnable serialRunnable = null;
	private final Object serialSyncObject = new Object();
	private Runnable blockedRunnable = null;
	private final Object blockedSyncObject = new Object();
	private volatile boolean serialRunnableCalled = false;
	private volatile boolean blockedRunnableCalled = false;

	private Screen() {
		if(!Display.getInstance().isInitialized())
		{
			Display.getInstance().init();
		}
		
		allPlanes = new Plane[3];//4];

		allPlanes[0] = new br.org.sbtvd.ui.GraphicPlane();
		allPlanes[1] = new br.org.sbtvd.ui.SwitchingPlane();
		allPlanes[2] = new br.org.sbtvd.ui.StillPicturePlane();
		
		remoteControl = new RemoteControl();
		
		userInputEventManager = UserInputEventManager.getUserInputEventManager(this);
	}

	/**
	 * Returns all <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screens</CODE></A> in this system.
	 *
	 * 
	 * @return an array of Screens representing  all Screens in this system.
	 */
	public static Screen[] getAvailableScreens()
	{
		if (Screen.availableScreens == null) {
			Screen.availableScreens = new Screen[1];
			
			Screen.availableScreens[0] = new Screen();
		}

		return availableScreens;
	}

	/**
	 * Returns the default <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> for this
	 * application.
	 *
	 * 
	 * @return the default Screen for this  application.
	 */
	public static Screen getDefaultScreen()
	{
		return getAvailableScreens()[0];
	}

	/**
	 * Returns a list of all planes for this screen.
	 *
	 * 
	 * @return an array of Plane objects or null if  none exist.
	 */
	public Plane[] getAllPlanes()
	{
		return this.allPlanes;
	}

	/**
	 * Return a coherent set of <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetups</CODE></A>
	 * matching a set of patterns. One <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>
	 * will be returned for each <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetupPattern</CODE></A> provided as input.
	 *
	 * Coherent means that all the required properties are respected
	 * in all of the patterns provided and that a setup can be returned for each
	 * pattern provided.
	 *
	 * Conflicts between patterns are resolved as discussed in the description
	 * of <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetupPattern</CODE></A>.
	 *
	 * 
	 * @param patterns - an array of objects describing desired setups.
	 * @return an array of non-null objects describing a coherent set of screen plane setups or null if no such coherent set is possible.
	 * @throws IllegalArgumentException - if a zero-length array is passed as an argument
	 * @see setCoherentPlaneSetups(com.sun.dtv.ui.PlaneSetup[])
	 */
	public PlaneSetup[] getCoherentPlaneSetups(PlaneSetupPattern[] patterns) throws IllegalArgumentException
	{
		int cont = 0;
		Plane plane = Plane.getCurrentPlane();
		PlaneSetup[] planeSetups = new PlaneSetup[patterns.length];
		
		for(int i = 0; i < patterns.length; i++){
			for(int j = 0; i < plane.setups.length; j++){
				/* takes in consideration only REQUIRED and REQUIRED_NOT priorities. */
				if(plane.setups[j].getPattern().implies(patterns[i])){
					planeSetups[cont] = plane.setups[j];
					cont++;
				}
			}
		}
		
		if(cont != 0){
			return planeSetups;
			
		}else{
			return null;
		}
	}

	/**
	 * Modify the settings for a set of <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Planes</CODE></A>, based
	 * on their <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetups</CODE></A> supplied.
	 *
	 * 
	 * @param setups - the array of setups that should be applied (where possible).
	 * @return A boolean indicating whether all PlaneSetups could be applied successfully. If none of the PlaneSetups could be applied successfully, the setup after this method may not match the setup of the planes prior to this method being called: applications should investigate whether a partial change of settings has been made for each plane.
	 * @throws IllegalArgumentException - if the parameter array is empty.
	 * @throws SecurityException - if the application does not have sufficient rights to set the PlaneSetup for any of the planes.
	 * @throws SetupException - if an element of the specified PlaneSetup array is not valid for any of the planes.
	 * @see getCoherentPlaneSetups(com.sun.dtv.ui.PlaneSetupPattern[])
	 */
	/* TODO: Needs to be reviewed. Don't have information enough to implement this method with security. */
	public boolean setCoherentPlaneSetups(PlaneSetup[] setups) throws SecurityException, IllegalArgumentException, SetupException
	{
		boolean matched = true;
		Plane[] planes = Plane.getInstances();
		for(int i = 0; i < setups.length; i++){
			for(int j = 0; j < planes.length; j++){
				if(!planes[i].setPlaneSetup(setups[i])){
					matched = false;
				}
			}
		}
		
		return matched;
	}

	/**
	 * Returns a <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A> from an
	 * <A HREF="../../../../com/sun/dtv/ui/Plane.html" title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A> which is present on
	 * this <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> that best matches at least one
	 * of the specified <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html" title="class in com.sun.dtv.ui"><CODE>PlaneSetupPatterns</CODE></A>. If this is not possible, null is returned.
	 *
	 * Best in the sense of this method means that the setups satisfy a number
	 * as high as possible of preferences with priorities <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#PREFERRED"><CODE>PREFERRED</CODE></A> and <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#PREFERRED_NOT"><CODE>PREFERRED_NOT</CODE></A> as well as
	 * all preferences with priorities <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED"><CODE>REQUIRED</CODE></A> and <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED_NOT"><CODE>REQUIRED_NOT</CODE></A>.
	 * Setups are chosen by applying the following algorithm, based on the
	 * priorities as supplied to <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#setPreference(int, int)"><CODE>setPreference</CODE></A>.
	 * The setup of choice:
	 * <ul>
	 * <li> MUST satisfy all the preferences whose priority was <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED"><CODE>REQUIRED</CODE></A></li>
	 * <li> MUST NOT satisfy any of the preferences whose priority was
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED_NOT"><CODE>REQUIRED_NOT</CODE></A></li>
	 * <li> SHOULD satisfy as many as possible of the preferences whose priority
	 * was <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#PREFERRED"><CODE>PREFERRED</CODE></A>.</li>
	 * <li> SHOULD satisfy as few as possible of the preferences whose priority
	 * was <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#PREFERRED_NOT"><CODE>PREFERRED_NOT</CODE></A>.</li>
	 * </ul>
	 * Preferences whose priority was <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#DONT_CARE"><CODE>DONT_CARE</CODE></A> are ignored.
	 *
	 * This method returns null if no setup exists that satisfies all <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED"><CODE>REQUIRED</CODE></A> and <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED_NOT"><CODE>REQUIRED_NOT</CODE></A> priorities.
	 *
	 * 
	 * @param patterns - the array of PlaneSetupPattern objects to choose from.
	 * @return an  PlaneSetup object that is the best matching possible setup, or null if none can be found.
	 */
	public PlaneSetup getBestSetup(PlaneSetupPattern[] patterns)
	{
		return Plane.getCurrentPlane().getBestSetup(patterns);
	}

	/**
	 * Returns the aspect ratio of the video output signal represented by this
	 * Screen.
	 *
	 * 
	 * 
	 * @return the aspect ration of the video output signal.
	 */
	public Dimension getScreenAspectRatio()
	{
		return this.screenAspectRatio;
	}

	
	public static void addResourceTypeListener(ResourceTypeListener listener) throws NullPointerException{
	}
	
	
	public static void removeResourceTypeListener(ResourceTypeListener listener) throws NullPointerException{
	}
	
	/**
	 * Register a listener for events about changes in the state of the
	 * ownership of this screen. If this listener has already been added before,
	 * further calls will add further references to the listener. As a
	 * consequence, these references will receive multiple copies of one single
	 * event.
	 *
	 * 
	 * @param listener - the object to be informed of state changes.
	 * @see removeScarceResourceListener(com.sun.dtv.resources.ScarceResourceListener)
	 */
	public void addScarceResourceListener(ScarceResourceListener listener)
	{
	}

	/**
	 * Remove a listener for events about changes in the state of the ownership
	 * of this screen. This method has no effect if the specified listener has
	 * not been registered before.
	 *
	 * 
	 * @param listener - the object which is no longer interested.
	 * @see addScarceResourceListener(com.sun.dtv.resources.ScarceResourceListener)
	 */
	public void removeScarceResourceListener(ScarceResourceListener listener)
	{
	}

	

	/**
	 * Returns a reserved instance out of the pool of all <code>Screen</code>
	 * instances. This method either returns with the instance or throws an
	 * exception according to the situation.
	 * 
	 *This method behaves exactly as the <code>reserve()</code> method.</p>
	 *
	 * 
	 * @param force - If true, this method is allowed to withdraw any given resource from its current owner. If false, the implementation will block and wait until a resource of the given type is made available (using release()) or until timeoutms milliseconds.
	 * @param timeoutms - A positive amount of time in millisecond until which this method will wait for any resource to be released by its current owner. The value of -1 indicates that the implementation will wait forever.
	 * @param listener - The listener to be attached to receive notification about the status of the resource.
	 * @return The instance of type Screen that has been reserved.
	 * @throws IllegalArgumentException - If listener is null or if the value specified in timeoutms is not valid i.e. not either a positive integer or -1.
	 * @throws TimeoutException - If the time specified in timeoutms is over and the resource could not be reserved.
	 * @see reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener)
	 */
	public static Screen reserveOne(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		Screen screen = getDefaultScreen();
		screen.reserve(force, timeoutms, listener);
		return screen;
	}

	/**
	 * Returns a list of <A HREF="../../../../com/sun/dtv/ui/UserInputDevice.html" title="class in com.sun.dtv.ui"><CODE>UserInputDevices</CODE></A>
	 * that are associated to this <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A>.
	 *
	 * 
	 * 
	 * @return a list of UserInputDevices that are associated to this Screen
	 */
	public UserInputDevice[] getSupportedUserInputDevices()
	{
		return this.supportedUserInputDevices;
	}

	/**
	 * Indicates whether this screen can be controlled using a keyboard.
	 *
	 * 
	 * 
	 * @return true if a keyboard is supported, false otherwise.
	 */
	public boolean isKeyboardSupported()
	{
		return false;
	}

	/**
	 * Returns the keyboard which can be used to control this screen if any.
	 *
	 * 
	 * 
	 * @return the Keyboard object representing the keyboard which can be used to control this screen, null if there is none.
	 */
	public Keyboard getKeyboard()
	{
		return this.keyboard;
	}

	/**
	 * Indicates whether this screen can be controlled using a TV remote
	 * control.
	 *
	 * 
	 * 
	 * @return true if TV remote control is supported, false otherwise.
	 */
	public boolean isRemoteControlSupported()
	{
		return (remoteControl != null);
	}

	/**
	 * Returns the TV remote control which can be used to control this screen
	 * if any.
	 *
	 * 
	 * 
	 * @return the RemoteControl object  representing the TV remote control which can be used to control this screen, null if there is none.
	 */
	public RemoteControl getRemoteControl()
	{
		return this.remoteControl;
	}

	/**
	 * Indicates whether this screen can be controlled using a mouse.
	 *
	 * 
	 * 
	 * @return true if a mouse is supported, false otherwise.
	 */
	public boolean isMouseSupported()
	{
		return false;
	}

	/**
	 * Returns the mouse which can be used to control this screen if any.
	 *
	 * 
	 * 
	 * @return the Mouse object representing the mouse which can be used to control this screen, null if there is none.
	 */
	public Mouse getMouse()
	{
		return this.mouse;
	}

	/**
	 * Return the UserInputEventManager of this screen.
	 * Any screen owns a fixed instance of UserInputEventManager in order to
	 * be able to handle user input events coming from several applications.
	 * The <A HREF="../../../../com/sun/dtv/ui/event/UserInputEventManager.html" title="class in com.sun.dtv.ui.event"><CODE>UserInputEventManager</CODE></A> is created together
	 * with the screen, it cannot be set or changed.
	 * Applications should use this method in order to gain access to the manager
	 * in order to apply for exclusive or non-exclusive access to <A HREF="../../../../com/sun/dtv/ui/event/UserInputEvent.html" title="interface in com.sun.dtv.ui.event"><CODE>UserInputEvents</CODE></A>.
	 *
	 * 
	 * 
	 * @return the UserInputEventManager of this screen.
	 */
	public UserInputEventManager getUserInputEventManager()
	{
		return this.userInputEventManager;
	}

	/**
	 * Returns the <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> the caller
	 * is displayed on.
	 *
	 * 
	 * 
	 * @return the current screen.
	 */
	public synchronized static Screen getCurrentScreen()
	{
		if (Screen.availableScreens == null) {
			getAvailableScreens();
		}
		return getDefaultScreen();
	}

	/**
	 * Return the list of all existing instances of <A HREF="../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A>, whether they are already reserved or not.
	 *
	 * 
	 * 
	 * @return list of all existing instances of Screen.
	 */
	public static Screen[] getInstances()
	{
		return getAvailableScreens();
	}

	/**
	 * Returns true if we are currently in the event dispatch thread.
	 * This is useful for generic code that can be used both with the
	 * EDT and outside of it.
	 *
	 * 
	 * 
	 * @return true if we are currently in the event dispatch thread; otherwise false
	 */
	public boolean isEdt()
	{
            return false;
        }

	/**
	 * Causes the runnable to be invoked on the event dispatch thread. This method
	 * returns immediately and will not wait for the serial call to occur
	 *
	 * 
	 * @param r - runnable (NOT A THREAD!) that will be invoked on the EDT serial to the paint and key handling events
	 * @throws IllegalStateException - if this method is invoked on the event dispatch thread (e.g. during paint or event handling).
	 */
	public void callSerially( Runnable r)
	{
		Display.getInstance().callSerially(r);
		/*if(this.isEdt())
		{
			throw new IllegalStateException();
		}
		
		synchronized(serialSyncObject)
		{
			this.serialRunnable = r;
		}*/
	}

	/**
	 * Identical to callSerially with the added benefit of waiting for the Runnable method to complete.
	 *
	 * 
	 * @param r - runnable (NOT A THREAD!) that will be invoked on the EDT serial to the paint and key handling events
	 * @throws IllegalStateException - if this method is invoked on the event dispatch thread (e.g. during paint or event handling).
	 */
	public void callSeriallyAndWait( Runnable r)
	{
		Display.getInstance().callSeriallyAndWait(r);
		/*if(this.isEdt())
		{
			throw new IllegalStateException();
		}
		
		synchronized(serialSyncObject)
		{
			this.serialRunnable = r;
		}
		
		while(!serialRunnableCalled)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		serialRunnableCalled = false;*/
	}
	
	public Runnable getSerialRunnable()
	{
		synchronized (serialSyncObject) {
			return this.serialRunnable;
		}
	}
	
	public void notifySerialRunnableCalled()
	{
		serialRunnableCalled = true;
		synchronized (serialSyncObject) {
			serialRunnable = null;
		}
	}

	/**
	 * Invokes runnable and blocks the current thread, if the current thread is the
	 * edt it will still be blocked however a separate thread would be launched
	 * to perform the duties of the EDT while it is blocked. Once blocking is finished
	 * the EDT would be restored to its original position. This is very similar to the
	 * "foxtrot" Swing toolkit and allows coding "simpler" logic that requires blocking
	 * code in the middle of event sensitive areas.
	 *
	 * 
	 * @param r - runnable (NOT A THREAD!) that will be invoked synchronously by this method
	 */
	public void invokeAndBlock(Runnable r)
	{
		Display.getInstance().invokeAndBlock(r);
		/*if(isEdt())
		{
			synchronized(blockedSyncObject)
			{
				this.blockedRunnable = r;
			}
			
			while(!blockedRunnableCalled)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			blockedRunnableCalled = false;
		}
		else
		{
			r.run();
		}*/
	}
	
	public Runnable getBlockedRunnable()
	{
		synchronized (blockedSyncObject) {
			return this.blockedRunnable;
		}
	}
	
	public void notifyBlockedRunnableCalled()
	{
		blockedRunnableCalled = true;
		synchronized (blockedSyncObject) {
			blockedRunnable = null;
		}
	}
	
	protected void notifyScarceResourceTypeListeners(boolean reserved) {
	}
}
