package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.animations.CommonTransitions;
import com.sun.dtv.lwuit.animations.Transition;

import com.sun.dtv.ui.Device;
import com.sun.dtv.ui.event.RemoteControlEvent;
import com.sun.dtv.ui.event.UserInputEvent;
import com.sun.dtv.ui.event.UserInputEventListener;
import com.sun.dtv.ui.event.UserInputEventManager;

import java.awt.event.KeyEvent;

import java.util.Enumeration;
import java.util.Vector;

import br.org.sbtvd.config.Settings;

/**
 * Central class for the API that manages rendering/events and is used to place top
 * level components ({@link Form}) on the "display". Before any Form is shown the Developer must
 * invoke Display.init(Object m) in order to register the current MIDlet.
 * <p>This class handles the main thread for the toolkit referenced here on as the EDT 
 * (Event Dispatch Thread) similar to the Swing EDT. This thread encapsulates the platform
 * specific event delivery and painting semantics and enables threading features such as
 * animations etc...
 * <p>The EDT should not be blocked since paint operations and events would also be blocked 
 * in much the same way as they would be in other platforms. In order to serialize calls back
 * into the EDT use the methods {@link Display#callSerially} &amp; {@link Display#callSeriallyAndWait}.
 * <p>Notice that all LWUIT calls occur on the EDT (events, painting, animations etc...), LWUIT 
 * should normally be manipulated on the EDT as well (hence the {@link Display#callSerially} &amp; 
 * {@link Display#callSeriallyAndWait} methods). Theoretically it should be possible to manipulate
 * some LWUIT features from other threads but this can't be guaranteed to work for all use cases.
 * 
 * @author Chen Fishbein, Shai Almog
 */
public final class Display implements UserInputEventListener {

	// Unknown keyboard type is the default indicating the software should try to detect the keyboard type if necessary
	public static final int KEYBOARD_TYPE_UNKNOWN = 0;
	// Numeric keypad keyboard type
	public static final int KEYBOARD_TYPE_NUMERIC = 1;
	// Full QWERTY keypad keyboard type, even if a numeric keyboard also exists
	public static final int KEYBOARD_TYPE_QWERTY = 2;
	// Touch device without a physical keyboard that should popup a keyboad
	public static final int KEYBOARD_TYPE_VIRTUAL = 3;
	// Half QWERTY which needs software assistance for completion
	public static final int KEYBOARD_TYPE_HALF_QWERTY = 4;

	// ...
	static final int POINTER_PRESSED = 1;
	// ...
	static final int POINTER_RELEASED = 2;
	// ...
	static final int POINTER_DRAGGED = 3;
	// ...
	static final int POINTER_HOVER = 8;
	// ...
	static final int KEY_PRESSED = 4;
	// ...
	static final int KEY_RELEASED = 5;
	// ...
	static final int KEY_LONG_PRESSED = 6;
	// ...
	static final int SIZE_CHANGED = 7;

	// Ignore all calls to show occurring during edit, they are discarded immediately
	public static final int SHOW_DURING_EDIT_IGNORE = 1;
	// If show is called while editing text in the native text box an exception is thrown
	public static final int SHOW_DURING_EDIT_EXCEPTION = 2;
	// Allow show to occur during edit and discard all user input at this moment
	public static final int SHOW_DURING_EDIT_ALLOW_DISCARD = 3;
	// Allow show to occur during edit and save all user input at this moment
	public static final int SHOW_DURING_EDIT_ALLOW_SAVE = 4;
	// Show will update the current form to which the OK button of the text box will return
	public static final int SHOW_DURING_EDIT_SET_AS_NEXT = 5;

	// Game action for fire
	public static final int GAME_FIRE = 8;
	// Game action for left key
	public static final int GAME_LEFT = 2;
	// Game action for right key
	public static final int GAME_RIGHT = 5;
	// Game action for UP key
	public static final int GAME_UP = 1;
	// Game action for left key
	public static final int GAME_DOWN = 6;

	/**
	 * An attribute that encapsulates '#' int value.
	 */
	public static final int KEY_POUND = '#';

	private static Display instance = new Display();

	private DTVImplementation impl = new DTVImplementation();

	/**
	 * Indicates whether this is a touch device
	 */
	private boolean touchScreen;

	/**
	 * Indicates the maximum drawing speed of no more than 10 frames per second
	 * by default (this can be increased or decreased) the advantage of limiting
	 * framerate is to allow the CPU to perform other tasks besides drawing.
	 * Notice that when no change is occurring on the screen no frame is drawn and
	 * so a high/low FPS will have no effect then.
	 */
	private static final int FRAMERATE_FAST = 20;
	private static final int FRAMERATE_SLOW = 250;
	private static final int FRAMERATE_COUNT = 80;
	private static final int FRAMERATE_THRESHOLD = 10;
	
	volatile private int framerateLock = FRAMERATE_FAST;
	private long frameRateControlTime = 0, deltaControlTime = 0, count = 0;
	

	/**
	 * Light mode allows the UI to adapt and show less visual effects/lighter versions
	 * of these visual effects to work properly on low end devices.
	 */
	private boolean lightMode;

	static int transitionDelay = -1;

	/**
	 * Contains the call serially pending elements
	 */
	private Vector pendingSerialCalls = new Vector();

	/**
	 * This is the instance of the EDT used internally to indicate whether
	 * we are executing on the EDT or some arbitrary thread
	 */ 
	private Thread edt; 

	/**
	 * Contains animations that must be played in full by the EDT before anything further
	 * may be processed. This is useful for transitions/intro's etc... that animate without
	 * user interaction.
	 */
	private Vector animationQueue;

	/**
	 * Indicates whether the 3rd softbutton should be supported on this device
	 */
	private boolean thirdSoftButton = false;

	private boolean editingText;

	private int showDuringEdit;

	static final Object lock = new Object();
	static final Object lockDialog = new Object();

	/**
	 * Events to broadcast on the EDT
	 */
	private Vector inputEvents = new Vector();

	private boolean keyRepeatCharged;
	private boolean longPressCharged;
	private int longPressInterval = 1000;
	private long nextKeyRepeatEvent;
	private int keyRepeatValue;
	private int keyRepeatInitialIntervalTime = 800;
	private int keyRepeatNextIntervalTime = 10;
	private boolean block = false;
	private int dragActivationCounter = 0;

	private boolean processingSerialCalls;

	private boolean isInitialized = false;
	private final Object initMutex = new Object();
	
	private java.awt.Container rootContainer;

	private boolean killEDT = false;

	/** 
	 * Private constructor to prevent instanciation
	 */
	private Display() {
		int x = Integer.parseInt(Settings.getInstance().getProperty("tv.x"));
		int y = Integer.parseInt(Settings.getInstance().getProperty("tv.y"));
		int width = Integer.parseInt(Settings.getInstance().getProperty("tv.screenwidth"));
	 	int height = Integer.parseInt(Settings.getInstance().getProperty("tv.screenheight"));
		
		rootContainer = new java.awt.Container();

		rootContainer.setBounds(x, y, width, height);
	}

	public java.awt.Container getRootContainer() {
		return rootContainer;
	}

	public boolean isInitialized()
	{
		synchronized(initMutex)
		{
			return this.isInitialized;
		}
	}

	public void init() {
		if(this.isInitialized())
		{
			return;
		}

		synchronized(initMutex)
		{
			this.isInitialized = true;
		}

		impl.setDisplayLock(lock);
		impl.init(this);
		if(impl.getSoftkeyCount() > 0) {
			Form.leftSK = impl.getSoftkeyCode(0)[0];
			if(impl.getSoftkeyCount() > 1) {
				Form.rightSK = impl.getSoftkeyCode(1)[0];
				if(impl.getSoftkeyCode(1).length > 1){
					Form.rightSK2 = impl.getSoftkeyCode(1)[1];
				}
			}
			Form.backSK = impl.getBackKeyCode();
			Form.backspaceSK = impl.getBackspaceKeyCode();
			Form.clearSK = impl.getClearKeyCode();
		}

		int width = getDisplayWidth();
		int height = getDisplayHeight();
		int colors = numColors();

		// if the resolution is very high and the amount of memory is very low while the device 
		// itself has many colors (requiring 32 bits per pixel) then we should concerve memory
		// by activating light mode.
		lightMode = colors > 65536 && width * height * 30 > Runtime.getRuntime().totalMemory();

		// this can happen on some cases where an application was restarted etc...
		// generally its probably a bug but we can let it slide...
		if(edt == null) {
			// initialize the JWT EDT which from now on will take all responsibility
			// for the event delivery.
			edt = new Thread("EDT") {
				public void run() {
					mainEDTLoop();
				}
			};                 
			edt.setPriority(Thread.NORM_PRIORITY + 1);
			edt.start();
		}

		UserInputEventManager manager = UserInputEventManager.
			getUserInputEventManager(Device.getInstance().getDefaultScreen());
		UserInputEvent[] events = RemoteControlEvent.getInstances();
		for(int i=0; i<events.length; i++)
		{
			if(events[i] instanceof RemoteControlEvent)
			{
				if(((RemoteControlEvent)events[i]).getKeyCode() == RemoteControlEvent.VK_ARROWS
					||
				((RemoteControlEvent)events[i]).getKeyCode() == RemoteControlEvent.VK_COLORED
					||
				((RemoteControlEvent)events[i]).getKeyCode() == RemoteControlEvent.VK_CONFIRM
					||
				((RemoteControlEvent)events[i]).getKeyCode() == RemoteControlEvent.VK_BACK_SPACE
					||
				((RemoteControlEvent)events[i]).getKeyCode() == RemoteControlEvent.VK_ESCAPE)
				{
					manager.addUserInputEventListener(this, events[i]);
				}
			}
		}
	}

	Vector getAnimationQueue() {
		return animationQueue;
	}

	/**
	 * Return the Display instance
	 * 
	 * @return the Display instance
	 */
	public static Display getInstance() {
//		System.err.println("############# NO JAVAAAAAAAAAAAAAAAAAAA -- DISPLA - STOP - DEBUG 1.1");
//		if (instance == null) {
//			instance = new Display();
//
//			//instance.init();
//		}

		return instance;
	}

	/**
	 * Indicates the maximum frames the API will try to draw every second
	 * by default this is set to 10. The advantage of limiting
	 * framerate is to allow the CPU to perform other tasks besides drawing.
	 * Notice that when no change is occurring on the screen no frame is drawn and
	 * so a high/low FPS will have no effect then.
	 * 10FPS would be very reasonable for a business application.
	 * 
	 * @param rate the frame rate
	 */
	public void setFramerate(int rate) {
		framerateLock = 1000 / rate;
	}

	void blockEvents(boolean block){
		this.block = block;
	}

	/**
	 * Invoking the show() method of a form/dialog while the user is editing
	 * text in the native text box can have several behaviors: SHOW_DURING_EDIT_IGNORE, 
	 * SHOW_DURING_EDIT_EXCEPTION, SHOW_DURING_EDIT_ALLOW_DISCARD, 
	 * SHOW_DURING_EDIT_ALLOW_SAVE, SHOW_DURING_EDIT_SET_AS_NEXT
	 * 
	 * @param showDuringEdit one of the following: SHOW_DURING_EDIT_IGNORE, 
	 * SHOW_DURING_EDIT_EXCEPTION, SHOW_DURING_EDIT_ALLOW_DISCARD, 
	 * SHOW_DURING_EDIT_ALLOW_SAVE, SHOW_DURING_EDIT_SET_AS_NEXT
	 */
	public void setShowDuringEditBehavior(int showDuringEdit) {
		this.showDuringEdit = showDuringEdit;
	}

	/**
	 * Returns the status of the show during edit flag
	 * 
	 * @return one of the following: SHOW_DURING_EDIT_IGNORE, 
	 * SHOW_DURING_EDIT_EXCEPTION, SHOW_DURING_EDIT_ALLOW_DISCARD, 
	 * SHOW_DURING_EDIT_ALLOW_SAVE, SHOW_DURING_EDIT_SET_AS_NEXT
	 */
	public int getShowDuringEditBehavior() {
		return showDuringEdit;
	}

	/**
	 * Indicates the maximum frames the API will try to draw every second
	 * 
	 * @return the frame rate
	 */
	public int getFrameRate() {
		return 1000 / framerateLock;
	}

	/**
	 * Returns true if we are currently in the event dispatch thread.
	 * This is useful for generic code that can be used both with the
	 * EDT and outside of it.
	 * 
	 * @return true if we are currently in the event dispatch thread; 
	 * otherwise false
	 */
	public boolean isEdt() {
		return edt == Thread.currentThread();
	}

	/**
	 * Causes the runnable to be invoked on the event dispatch thread. This method
	 * returns immediately and will not wait for the serial call to occur 
	 * 
	 * @param r runnable (NOT A THREAD!) that will be invoked on the EDT serial to
	 * the paint and key handling events 
	 * @throws IllegalStateException if this method is invoked on the event dispatch thread (e.g. during
	 * paint or event handling).
	 */
	public void callSerially(Runnable r){
		if(isEdt()) {
			throw new IllegalStateException("Call serially must never be invoked from the EDT");
		}
		synchronized(pendingSerialCalls)
		{
			pendingSerialCalls.addElement(r);
			synchronized (lock) {
				lock.notify();
			}
			synchronized (lockDialog){
				lockDialog.notifyAll();
			}
		}
	}


	/**
	 * Identical to callSerially with the added benefit of waiting for the Runnable method to complete.
	 * 
	 * @param r runnable (NOT A THREAD!) that will be invoked on the EDT serial to
	 * the paint and key handling events 
	 * @throws IllegalStateException if this method is invoked on the event dispatch thread (e.g. during
	 * paint or event handling).
	 */
	public void callSeriallyAndWait(Runnable r){
		RunnableWrapper c = new RunnableWrapper(r, 0);
		callSerially(c);
		while(!c.isDone()) {
			try {
				synchronized(lock){
					lock.wait(50);
				}
			} catch (InterruptedException e) {
				//do nothing here
			}
		}
	}

	/**
	 * Allows us to "flush" the edt to allow any pending transitions and input to go
	 * by before continuing with our other tasks.
	 */
	void flushEdt() {
		while(!shouldEDTSleepNoFormAnimation()) {
			edtLoopImpl();
		}
		while(animationQueue != null && animationQueue.size() > 0){
			edtLoopImpl();
		}
	}

	/**
	 * Restores the menu in the given form
	 *
	 */
	private void restoreMenu(Form f) {
		if(f != null) {
			f.restoreMenu();
		}
	}

	private void paintTransitionAnimation() {
		Animation ani = (Animation) animationQueue.elementAt(0);
		if (!ani.animate()) {
			animationQueue.removeElementAt(0);
			if (ani instanceof Transition) {
				Form source = (Form) ((Transition)ani).getSource();
				restoreMenu(source);

				if (animationQueue.size() > 0) {
					ani = (Animation) animationQueue.elementAt(0);
					if (ani instanceof Transition) {
						((Transition) ani).initTransition();
					}
				}else{
					Form f = (Form) ((Transition)ani).getDestination();
					restoreMenu(f);
					if (source == null || source == impl.getCurrentForm() || source == getCurrent()) {
						setCurrentForm(f);
					}
					((Transition) ani).cleanup();
				}
				return;
			}
		}

		ani.paint(impl.getNativeGraphics());
		impl.flushGraphics();

		if(transitionDelay > 0) {
			// yield for a fraction, some devices don't "properly" implement
			// flush and so require the painting thread to get CPU too.
			try {
				
				synchronized(lock){
					lock.wait(transitionDelay);
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * This method represents the event thread for the UI library on which 
	 * all events are carried out. It differs from the MIDP event thread to 
	 * prevent blocking of actual input and drawing operations. This also
	 * enables functionality such as "true" modal dialogs etc...
	 */
	void mainEDTLoop() {
		try {
			// when there is no current form the EDT is useful only for features such as call serially
			while(impl.getCurrentForm() == null && !killEDT) {
				if(shouldEDTSleep()) {
					synchronized(lock){
						lock.wait();
					}
				}
				
				// paint transition or intro animations and don't do anything else if such animations are in progress...
				if(animationQueue != null && animationQueue.size() > 0) {
					paintTransitionAnimation();
					continue;
				}
				processSerialCalls();
			}
		} catch(Throwable err) {
			err.printStackTrace();
//			if(!impl.handleEDTException(err)) {
//				Dialog.show("Error", "An internal application error occurred: " + err.toString(), "OK", null);
//			}
		}

		while(true && !killEDT) {
			
			
			try {
				
				frameRateControlTime = System.currentTimeMillis();

				// wait indefinetly but no more than the framerate if there are no animations... If animations exist then
				// only wait for the framerate Lock surrounds the should method to prevent serial calls from getting "lost"
				if(shouldEDTSleep()) {
					synchronized(lock){
						lock.wait();
					}
				}
				
				deltaControlTime = Math.abs(frameRateControlTime - System.currentTimeMillis());
				
				if(deltaControlTime < FRAMERATE_THRESHOLD){
					count++;
					if(count > FRAMERATE_COUNT){
						framerateLock = FRAMERATE_SLOW;
						//System.out.println("JVM --- FRAME RATE UP -- TO "+framerateLock);
						count = 0;
					}
				}
				
				edtLoopImpl();
				
			} catch(Throwable err) {
				err.printStackTrace();
//				if(!impl.handleEDTException(err)) {
//					Dialog.show("Error", "An internal application error occurred: " + err.toString(), "OK", null);
//				}
			}
		}
	}

	long time;

	/**
	 * Implementation of the event dispatch loop content
	 */
	void edtLoopImpl() {
		try {
			// transitions shouldn't be bound by framerate
			if(animationQueue == null || animationQueue.size() == 0) {
				// prevents us from waking up the EDT too much and thus exhausting the systems resources. The + 1
				// prevents us from ever waiting 0 milliseconds which is the same as waiting with no time limit
				synchronized(lock){
					lock.wait(Math.max(1, framerateLock - (time)));
				}

			} else {
				// paint transition or intro animations and don't do anything else if such animations are in progress...
				paintTransitionAnimation();
				return;
			}
		} catch(InterruptedException ignor) {
			ignor.printStackTrace();
		}

		long currentTime = System.currentTimeMillis();

		synchronized (inputEvents) {
			while(inputEvents.size() > 0 && !block) {
				int[] i = (int[])inputEvents.elementAt(0);
				inputEvents.removeElementAt(0);
				handleEvent(i);
			}
		}
		impl.paintDirty();
		// draw the animations
		Form current = impl.getCurrentForm();
		
		current.repaintAnimations();

		// check key repeat events
		if(!block) {
			long t = System.currentTimeMillis();
			
			if(keyRepeatCharged && nextKeyRepeatEvent <= t) {
				current.keyRepeated(keyRepeatValue);
				nextKeyRepeatEvent = t + keyRepeatNextIntervalTime;
			}
			if(longPressCharged && longPressInterval <= t) {
				longPressCharged = false;
				current.longKeyPress(keyRepeatValue);
			}
		}
		processSerialCalls();
		time = System.currentTimeMillis() - currentTime;
	}

	
	boolean hasNoSerialCallsPending() {
		synchronized (pendingSerialCalls) {
			return pendingSerialCalls.size() == 0;
		}
	}

	/**
	 * Called by the underlying implementation to indicate that editing in the native
	 * system has completed and changes should propogate into LWUIT
	 * 
	 * @param c edited component
	 * @param text new text for the component
	 */
	public void onEditingComplete(Component c, String text) {
		c.onEditComplete(text);
		c.fireActionEvent();
	}

	public DTVImplementation getImplementation() {
		return impl;
	}

	/**
	 * Used by the EDT to process all the calls submitted via call serially
	 */
	void processSerialCalls() {
		synchronized (pendingSerialCalls) {
			processingSerialCalls = true;
			int size = pendingSerialCalls.size();
			if(size > 0) {
				Runnable[] array = new Runnable[size];
	
				// copy all elements to an array and remove them otherwise invokeAndBlock from
				// within a callSerially() can cause an infinite loop...
				for(int iter = 0 ; iter < size ; iter++) {
					array[iter] = (Runnable)pendingSerialCalls.elementAt(iter);
				}
	
				pendingSerialCalls.removeAllElements();
	
				for(int iter = 0 ; iter < size ; iter++) {
					array[iter].run();
				}
			}
			processingSerialCalls = false;
		}
	}

	boolean isProcessingSerialCalls() {
		return processingSerialCalls;
	}

	/**
	 * Invokes runnable and blocks the current thread, if the current thread is the
	 * edt it will still be blocked however a separate thread would be launched
	 * to perform the duties of the EDT while it is blocked. Once blocking is finished
	 * the EDT would be restored to its original position. This is very similar to the
	 * "foxtrot" Swing toolkit and allows coding "simpler" logic that requires blocking
	 * code in the middle of event sensitive areas.
	 * 
	 * @param r runnable (NOT A THREAD!) that will be invoked synchroniously by this method
	 */
	public void invokeAndBlock(Runnable r){
		if(isEdt()) {
			// this class allows a runtime exception to propogate correctly out of the internal thread
			RunnableWrapper w = new RunnableWrapper(r, 1);
			Thread t = new Thread(w);
			t.start();

			// loop over the EDT until the thread completes then return
			while(t.isAlive()) {
				try {
					synchronized(lock) {
						lock.wait(framerateLock);
					}
					edtLoopImpl();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			// if the thread thew an exception we need to throw it onwards
			if(w.getErr() != null) {
				throw w.getErr();
			}
		} else {
			r.run();
		}
	}

	/**
	 * Indicates if this is a touch screen device that will return pen events,
	 * defaults to true if the device has pen events but can be overriden by
	 * the developer.
	 * 
	 * @return true if this device supports touch events
	 */
	public boolean isTouchScreenDevice() {
		return touchScreen;
	}

	/**
	 * Indicates if this is a touch screen device that will return pen events,
	 * defaults to true if the device has pen events but can be overriden by
	 * the developer.
	 * 
	 * @param touchScreen false if this is not a touch screen device
	 */
	public void setTouchScreenDevice(boolean touchScreen) {
		this.touchScreen = touchScreen;
	}

	/**
	 * Displays the given Form on the screen.
	 * 
	 * @param newForm the Form to Display
	 */
	public void setCurrent(final Form newForm){
		if(edt == null) {
			throw new IllegalStateException("Initialize must be invoked before setCurrent!");
		}

		if(editingText) {
			switch(showDuringEdit) {
				case SHOW_DURING_EDIT_ALLOW_DISCARD:
					break;
				case SHOW_DURING_EDIT_ALLOW_SAVE:
					impl.saveTextEditingState();
					break;
				case SHOW_DURING_EDIT_EXCEPTION:
					throw new IllegalStateException("Show during edit");
				case SHOW_DURING_EDIT_IGNORE:
					return;
				case SHOW_DURING_EDIT_SET_AS_NEXT:
					impl.setCurrentForm(newForm);
					return;
			}
		}

		if(!isEdt()) {
			callSerially(new RunnableWrapper(newForm, null));
			return;
		}

		Form current = impl.getCurrentForm();
		if(current != null){
			current.hidePopups();
			if(current.isInitialized()) {
				current.deinitializeImpl();
			}
		}
		if(!newForm.isInitialized()) {
			newForm.initComponentImpl();
		}

		newForm.setShouldCalcPreferredSize(true);
		newForm.layoutContainer();

		boolean transitionExists = false;
		if(animationQueue != null && animationQueue.size() > 0) {
			Object o = animationQueue.lastElement();
			if(o instanceof Transition) {
				current = (Form)((Transition)o).getDestination();
				impl.setCurrentForm(current);
			}
		}

		// make sure the fold menu occurs as expected then set the current to the correct parent!
		if(current != null && current instanceof Dialog && ((Dialog)current).isMenu()) {
			Transition t = current.getTransitionOutAnimator();
			if(t != null) {
				// go back to the parent form first
				if(((Dialog)current).getPreviousForm() != null) {
					initTransition(t.copy(), current, ((Dialog)current).getPreviousForm());
				}
			}
			current = ((Dialog)current).getPreviousForm();
			impl.setCurrentForm(current);
		}

		// prevent the transition from occurring from a form into itself
		if(newForm != current) {
			if((current != null && current.getTransitionOutAnimator() != null) || newForm.getTransitionInAnimator() != null) {
				if(animationQueue == null) {
					animationQueue = new Vector();
				}
				// prevent form transitions from breaking our dialog based
				// transitions which are a bit sensitive
				if(current != null && (!(newForm instanceof Dialog))) {
					Transition t = current.getTransitionOutAnimator();
					if(current != null && t != null) {
						initTransition(t.copy(), current, newForm);
						transitionExists = true;
					}
				}
				if(current != null && !(current instanceof Dialog)) {
					Transition t = newForm.getTransitionInAnimator();
					if(t != null) {
						initTransition(t.copy(), current, newForm);
						transitionExists = true;
					}
				}
			}
		}
		
		synchronized (lock) {
			lock.notify();
		}
		
		synchronized (lockDialog){
			lockDialog.notifyAll();
		}

		if(!transitionExists) {
			if(animationQueue == null || animationQueue.size() == 0) {
				setCurrentForm(newForm);
			} else {
				// we need to add an empty transition to "serialize" this screen change...
				Transition t = CommonTransitions.createEmpty();
				initTransition(t, current, newForm);
			}
		}
	}

	/**
	 * Initialize the transition and add it to the queue
	 */
	private void initTransition(Transition transition, Form source, Form dest) {
		dest.setVisible(true);
		transition.init(source, dest);
		animationQueue.addElement(transition);

		if(animationQueue.size() == 1) {
			transition.initTransition();
		}
	}

	void setCurrentForm(Form newForm){
		Form current = impl.getCurrentForm();
		if(current != null){
			current.setVisible(false);
		}
		current = newForm;
		impl.setCurrentForm(current);
		current.setVisible(true);

		impl.confirmControlView();
		int w = current.getWidth();
		int h = current.getHeight();
		if(isEdt() && ( w != impl.getDisplayWidth() || h != impl.getDisplayHeight())){
//			current.setSize(new Dimension(impl.getDisplayWidth(), impl.getDisplayHeight()));

			current.setShouldCalcPreferredSize(true);
			current.layoutContainer();
		}
		lastKeyPressed = 0;
		repaint(current);
	}

	/**
	 * Indicate to the implementation whether the flush graphics bug exists on this
	 * device. By default the flushGraphics bug is set to "true" and only disabled
	 * on handsets known 100% to be safe
	 * 
	 * @param flushGraphicsBug true if the bug exists on this device (the safe choice)
	 * false for slightly higher performance.
	 * @deprecated this method is no longer supported use GameCanvasImplementation.setFlashGraphicsBug(f) instead
	 */
	public void setFlashGraphicsBug(boolean flushGraphicsBug) {
	}

	/**
	 * Indicates whether a delay should exist between calls to flush graphics during
	 * transition. In some devices flushGraphics is asynchronious causing it to be
	 * very slow with our background thread. The solution is to add a short wait allowing
	 * the implementation time to paint the screen. This value is set automatically by default
	 * but can be overriden for some devices.
	 * 
	 * @param transitionD -1 for no delay otherwise delay in milliseconds
	 */
	public void setTransitionYield(int transitionD) {
		transitionDelay = transitionD;
	}

	/**
	 * Encapsulates the editing code which is specific to the platform, some platforms
	 * would allow "in place editing" MIDP does not.
	 * 
	 * @param cmp the {@link TextArea} component
	 */
	void editString(Component cmp, int maxSize, int constraint, String text) {
		editingText = true;
		keyRepeatCharged = false;
		longPressCharged = false;
		impl.editString(cmp, maxSize, constraint, text);
		editingText = false;
	}

	private void addInputEvent(int[] ev) {
		synchronized(inputEvents) {
			inputEvents.addElement(ev);
			synchronized (lock) {
				lock.notify();
			}
			synchronized (lockDialog){
				lockDialog.notifyAll();
			}
		}
	}

	/**
	 * Creates a pointer event with the following properties
	 */
	private int[] createPointerEvent(int[] x, int[] y, int eventType) {
		if(x.length == 1) {
			return new int[] {eventType, x[0], y[0]};
		}
		int[] arr = new int[1 + x.length * 2];
		arr[0] = eventType;
		int arrayOffset = 1;
		for(int iter = 0 ; iter < x.length ; iter++) {
			arr[arrayOffset] = x[iter];
			arrayOffset++;
			arr[arrayOffset] = y[iter];
			arrayOffset++;
		}
		return arr;
	}


	private int[] createKeyEvent(int keyCode, boolean pressed) {
		if(pressed) {
			return new int[] {KEY_PRESSED, keyCode};
		} else {
			return new int[] {KEY_RELEASED, keyCode};
		}
	}

	private int lastKeyPressed;

	/**
	 * Pushes a key press event with the given keycode into LWUIT
	 * 
	 * @param keyCode keycode of the key event
	 */
	public void keyPressed(final int keyCode){
		count = 0;
		framerateLock = FRAMERATE_FAST;
		//System.out.println("JVM --- FRAME RATE DOWN -- TO "+framerateLock);
		
		if(impl.getCurrentForm() == null){
			return;
		}

		addInputEvent(createKeyEvent(keyCode, true));
		keyRepeatCharged = true;
		longPressCharged = true;
		keyRepeatValue = keyCode;
		nextKeyRepeatEvent = System.currentTimeMillis() + keyRepeatInitialIntervalTime;
		lastKeyPressed = keyCode;
	}

	/**
	 * Pushes a key release event with the given keycode into LWUIT
	 * 
	 * @param keyCode keycode of the key event
	 */
	public void keyReleased(final int keyCode){
		count = 0;
		framerateLock = FRAMERATE_FAST;
		//System.out.println("JVM --- FRAME RATE DOWN -- TO "+framerateLock);
		
		keyRepeatCharged = false;
		longPressCharged = false;
		
		if(impl.getCurrentForm() == null){
			return;
		}
		// this can happen when traversing from the native form to the current form caused by a keypress
		if(keyCode != lastKeyPressed) {
			return;
		}
		addInputEvent(createKeyEvent(keyCode, false));
	}

	void keyRepeatedInternal(final int keyCode){
	}

	/**
	 * Pushes a pointer drag event with the given coordinates into LWUIT
	 * 
	 * @param x the x position of the pointer
	 * @param y the y position of the pointer
	 */
	public void pointerDragged(final int[] x, final int[] y) {
		if(impl.getCurrentForm() == null){
			return;
		}

		//send the drag events to the form only after latency of 7 drag events, 
		//most touch devices are too sensitive and send too many drag events.
		//7 is just a latency const number that is pretty good for most devices
		//this may be tuned for specific devices.
		dragActivationCounter++;
		if(dragActivationCounter < 7){
			return;
		}
		addInputEvent(createPointerEvent(x, y, POINTER_DRAGGED));
	}

	/**
	 * Pushes a pointer hover event with the given coordinates into LWUIT
	 * 
	 * @param x the x position of the pointer
	 * @param y the y position of the pointer
	 */
	public void pointerHover(final int[] x, final int[] y){
		if(impl.getCurrentForm() == null){
			return;
		}
		addInputEvent(createPointerEvent(x, y, POINTER_HOVER));
	}

	/**
	 * Pushes a pointer press event with the given coordinates into LWUIT
	 * 
	 * @param x the x position of the pointer
	 * @param y the y position of the pointer
	 */
	public void pointerPressed(final int[] x,final int[] y){
		if(impl.getCurrentForm() == null){
			return;
		}
		addInputEvent(createPointerEvent(x, y, POINTER_PRESSED));
	}

	/**
	 * Pushes a pointer release event with the given coordinates into LWUIT
	 * 
	 * @param x the x position of the pointer
	 * @param y the y position of the pointer
	 */
	public void pointerReleased(final int[] x, final int[] y){
		dragActivationCounter = 0;
		if(impl.getCurrentForm() == null){
			return;
		}
		addInputEvent(createPointerEvent(x, y, POINTER_RELEASED));
	}

	/**
	 * Notifies LWUIT of display size changes, this method is invoked by the implementation
	 * class and is for internal use
	 * 
	 * @param w the width of the drawing surface
	 * @param h the height of the drawing surface
	 */
	public void sizeChanged(int w, int h){
		Form current = impl.getCurrentForm();
		if(current == null) {
			return;
		}
		if(w == current.getWidth() && h == current.getHeight()) {
			return;
		}

		addInputEvent(createSizeChangedEvent(w, h));
	}

	private int[] createSizeChangedEvent(int w, int h) {
		return new int[] {SIZE_CHANGED, w, h};
	}

	/**
	 * Used by the flush functionality which doesn't care much about component
	 * animations
	 */
	boolean shouldEDTSleepNoFormAnimation() {
		synchronized(inputEvents){
			synchronized (inputEvents) {
				return inputEvents.size() == 0 &&
					hasNoSerialCallsPending();
			}
		}
	}

	/**
	 * Invoked on the EDT to propagate the event
	 */
	private void handleEvent(int[] ev) {
		switch(ev[0]) {
			case KEY_PRESSED:
				getCurrent().keyPressed(ev[1]);
				break;
			case KEY_RELEASED:
			    getCurrent().keyReleased(ev[1]);
				break;
			case POINTER_PRESSED:
				break;
			case POINTER_RELEASED:
				break;
			case POINTER_DRAGGED:
				break;
			case POINTER_HOVER:
				break;
			case SIZE_CHANGED:
				getCurrent().sizeChanged(ev[1], ev[2]);
				break;
		}
	}

	private int[] pointerEvent(int off, int[] event) {
		int[] peX = new int[event.length / 2];
		int offset = 0;
		for(int iter = off ; iter < event.length ; iter+=2 ) {
			peX[offset] = event[iter];
			offset++;
		}
		return peX;
	}

	/**
	 * Returns true for a case where the EDT has nothing at all to do
	 */
	boolean shouldEDTSleep() {
		Form current = impl.getCurrentForm();
		
		synchronized (inputEvents) {
			return (current == null || (!current.hasAnimations())) &&
				(animationQueue == null || animationQueue.size() == 0) &&
				inputEvents.size() == 0 &&
				(!impl.hasPendingPaints()) &&
				hasNoSerialCallsPending() && !killEDT;
		}
	}


	/**
	 * Returns the video control for the media player
	 * 
	 * @param player the media player
	 * @return the video control for the media player
	 */
	Object getVideoControl(Object player) {
		return impl.getVideoControl(player);
	}

	Form getCurrentInternal() {
		return impl.getCurrentForm();
	}

	/**
	 * Same as getCurrent with the added exception of looking into the future
	 * transitions and returning the last current in the transition (the upcoming
	 * value for current)
	 * 
	 * @return the form currently displayed on the screen or null if no form is
	 * currently displayed
	 */
	Form getCurrentUpcoming() {
		Form upcoming = null;

		// we are in the middle of a transition so we should extract the next form
		if(animationQueue != null) {
			Enumeration e = animationQueue.elements();
			while(e.hasMoreElements()) {
				Object o = e.nextElement();
				if(o instanceof Transition) {
					upcoming = (Form)((Transition)o).getDestination();
				}
			}
		}
		if(upcoming == null) {
			return getCurrent();
		}
		return upcoming;
	}

	/**
	 * Return the form currently displayed on the screen or null if no form is
	 * currently displayed.
	 * 
	 * @return the form currently displayed on the screen or null if no form is
	 * currently displayed
	 */
	public Form getCurrent(){
		Form current = impl.getCurrentForm();
		if(current != null && current instanceof Dialog && ((Dialog)current).isMenu()) {
			Form p = current.getPreviousForm();
			if(p != null) {
				return p;
			}

			// we are in the middle of a transition so we should extract the next form
			Enumeration e = animationQueue.elements();
			while(e.hasMoreElements()) {
				Object o = e.nextElement();
				if(o instanceof Transition) {
					return (Form)((Transition)o).getDestination();
				}
			}
		}
		return current;
	}

	/**
	 * Return the number of alpha levels supported by the implementation.
	 * 
	 * @return the number of alpha levels supported by the implementation
	 */
	public int numAlphaLevels(){
		return impl.numAlphaLevels();
	}

	/**
	 * Returns the number of colors applicable on the device, note that the API
	 * does not support gray scale devices.
	 * 
	 * @return the number of colors applicable on the device
	 */
	public int numColors() {
		return impl.numColors();
	}

	/**
	 * Light mode allows the UI to adapt and show less visual effects/lighter versions
	 * of these visual effects to work properly on low end devices.
	 * 
	 * @return true if this is light mode
	 * @deprecated this method is no longer used, it was too unreliable
	 */
	public boolean isLightMode() {
		return lightMode;
	}

	/**
	 * Light mode allows the UI to adapt and show less visual effects/lighter versions
	 * of these visual effects to work properly on low end devices.
	 * 
	 * @param lightMode true to activate light mode
	 * @deprecated this method is no longer used, it was too unreliable
	 */
	public void setLightMode(boolean lightMode) {
		this.lightMode = lightMode;
	}


	/**
	 * Return the width of the display
	 * 
	 * @return the width of the display
	 */
	public int getDisplayWidth(){
		return impl.getDisplayWidth();
	}

	/**
	 * Return the height of the display
	 * 
	 * @return the height of the display
	 */
	public int getDisplayHeight(){
		return impl.getDisplayHeight();
	}

	/**
	 * Causes the given component to repaint, used internally by Form
	 * 
	 * @param cmp the given component to repaint
	 */
	void repaint(final Animation cmp){
		impl.repaint(cmp);
	}

	/**
	 * Returns the game action code matching the given key combination
	 * 
	 * @param keyCode key code received from the event
	 * @return game action matching this keycode
	 */
	public int getGameAction(int keyCode){
		return impl.getGameAction(keyCode);
	}

	/**
	 * Returns the keycode matching the given game action constant (the opposite of getGameAction).
	 * On some devices getKeyCode returns numeric keypad values for game actions,
	 * this breaks the code since we filter these values (to prevent navigation on '2'). 
	 * We pick unused negative values for game keys and assign them to game keys for 
	 * getKeyCode so they will work with getGameAction.
	 * 
	 * @param gameAction game action constant from this class
	 * @return keycode matching this constant
	 * @deprecated this method doesn't work properly across device and is mocked up here
	 * mostly for the case of unit testing. Do not use it for anything other than that! Do
	 * not rely on getKeyCode(GAME_*) == keyCodeFromKeyEvent, this will never actually happen!
	 */
	public int getKeyCode(int gameAction){
		return impl.getKeyCode(gameAction);
	}

	/**
	 * Allows overriding the softkeys initialized by the software to a different value.
	 * This method MUST be invoked after init() has completed. 
	 * <p>In order to maintain the default value 0 can be passed as a value for a softkey
	 * thus resulting in no effect e.g. setSoftkeyCodes(0, 0, 0, -8); will only affect the back key.
	 * @param left the left softkey code
	 * @param right the right softkey code
	 * @param clear the clear softkey code
	 * @param back the back softkey code
	 * 
	 * @deprecated the fix for this is to work with the DTVImplementation classes and GameCanvasImplementation,
	 * extend those classes to return the values you want or alternatively use the JAD file attributes
	 * where appropriate
	 */
	public void setSoftkeyCodes(int left, int right, int clear, int back) {
	}


	/**
	 * Indicates whether the 3rd softbutton should be supported on this device
	 * 
	 * @return true if a third softbutton should be used
	 */
	public boolean isThirdSoftButton() {
		return thirdSoftButton;
	}

	/**
	 * Indicates whether the 3rd softbutton should be supported on this device
	 * 
	 * @param thirdSoftButton true if a third softbutton should be used
	 */
	public void setThirdSoftButton(boolean thirdSoftButton) {
		this.thirdSoftButton = thirdSoftButton;
	}


	/**
	 * Displays the virtual keyboard on devices that support manually poping up
	 * the vitual keyboard
	 * 
	 * @param show toggles the virtual keyboards visibility
	 */
	public void setShowVirtualKeyboard(boolean show) {
		impl.setShowVirtualKeyboard(show);
	}

	/**
	 * Indicates whether showing a virtual keyboard programmatically is supported 
	 * 
	 * @return false by default
	 */
	public boolean isVirtualKeyboardShowingSupported() {
		return impl.isVirtualKeyboardShowingSupported();
	}

	/**
	 * Returns the type of the input device one of:
	 * KEYBOARD_TYPE_UNKNOWN, KEYBOARD_TYPE_NUMERIC, KEYBOARD_TYPE_QWERTY, 
	 * KEYBOARD_TYPE_VIRTUAL, KEYBOARD_TYPE_HALF_QWERTY
	 * 
	 * @return KEYBOARD_TYPE_UNKNOWN
	 */
	public int getKeyboadType() {
		return impl.getKeyboadType();
	}

	/**
	 * Indicates whether the device supports native in place editing in which case
	 * lightweight input logic shouldn't be used for input.
	 * 
	 * @return false by default
	 */
	public boolean isNativeInputSupported() {
		return false;
	}

	public void userInputEventReceived(UserInputEvent event) {
		if(event instanceof RemoteControlEvent)
		{
			if (((RemoteControlEvent)event).getID() == KeyEvent.KEY_PRESSED)
			{
				keyPressed(((RemoteControlEvent)event).getKeyCode());
			} else if (((RemoteControlEvent)event).getID() == KeyEvent.KEY_PRESSED) {
				keyReleased(((RemoteControlEvent)event).getKeyCode());
			}
		}
	}
    
    public void stopEDT(){
    	killEDT = true;
    	if(edt == null || lock == null){
    		return;
    	}
    	
    	synchronized (lock) {
    		lock.notify();	
			}
			
			while(edt.isAlive()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

}

