package com.sun.dtv.ui.event;

import com.sun.dtv.ui.Screen;
import java.util.*;

public final class UserInputEventManager
{
	private static HashMap eventListenersMap = new HashMap();
	private static Map managers = new HashMap();
	
	private Screen screen;

	UserInputEventManager(Screen screen) {
		this.screen = screen;
	}

	/**
	 * Returns the unique <A HREF="../../../../../com/sun/dtv/ui/event/UserInputEventManager.html" title="class in com.sun.dtv.ui.event"><CODE>UserInputEventManager</CODE></A>
	 * for the specified <A HREF="../../../../../com/sun/dtv/ui/Screen.html" title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A>. As a consequence,
	 * this method will create the UserInputEventManager if firstly called
	 * for the specified Screen (usually this will happen out of the Screen's
	 * constructor), and return the identical instance for any subsequent call.
	 *
	 * 
	 * @param screen - the Screen we want to get the UserInputEventManager for
	 * @return the UserInputEventManager instance for the specified Screen
	 */
	public static UserInputEventManager getUserInputEventManager(Screen screen)
	{
		UserInputEventManager manager = (UserInputEventManager)managers.get(screen);

		if (manager == null) {
			managers.put(screen, new UserInputEventManager(screen));
		}

		return (UserInputEventManager)managers.get(screen);
	}

	public void removeAll() {
		managers.remove(this);
	}

	/**
	 * Add the specified listener which would like to be notified about the
	 * <A HREF="../../../../../com/sun/dtv/ui/event/UserInputEvent.html" title="interface in com.sun.dtv.ui.event"><CODE>UserInputEvent</CODE></A> specified.
	 * Please be aware that the listener will not be notified about received
	 * UserInputEvents in the case that another application has exclusively
	 * reserved this event using the scarce resource mechanism before and this
	 * reservation is still valid.
	 * If this method is called more than once for the same listener, registering
	 * for a notification about the same user input event, after the first calls
	 * subsequent calls have no effect, i.e. a listener can only once register
	 * for one user input event.
	 * If several listeners register for the same user input event, but one has
	 * exclusively reserved this event, the other listeners won't be notified
	 * at all, even if they are still registered. If more than one listener has
	 * tried to exclusively reserve the event, this is managed following the
	 * rules of scarce resource management.
	 * IMPLEMENTATION NOTE: The implementation has to ensure that notifications
	 * about all events are processed sequentially. It has to be guaranteed that
	 * no more than one notification is handled at the same time. Any single
	 * listener shall only be notified about one event instance at one time. If
	 * a new event is generated before the notified listener method has returned
	 * from processing the previous event, the implementation SHOULD wait for the
	 * return before processing the next notification call.
	 *
	 * 
	 * @param listener - the UserInputEventListener to be added
	 * @param event - the UserInputEvent the listener should listen to
	 * @see KeyEvent.reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener), MouseEvent.reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener), KeyEvent.reserve(boolean, long, com.sun.dtv.resources.ScarceResourceListener), removeUserInputEventListener(com.sun.dtv.ui.event.UserInputEventListener)
	 */
	public void addUserInputEventListener(UserInputEventListener listener, UserInputEvent event)
	{
		//Handle KeyEvent objects
		if(event instanceof KeyEvent)
		{
			ArrayList eventList = new ArrayList(4);
			boolean reserved = ((KeyEvent)event).wasNewReseved();
			
			if(((KeyEvent) event).getKeyCode() == RemoteControlEvent.VK_COLORED){
				reserved = false;
				KeyEvent e;
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_0,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_1,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_2,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_3,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
			}else if(((KeyEvent) event).getKeyCode() == RemoteControlEvent.VK_ARROWS){
				reserved = false;
				KeyEvent e;
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_UP,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_DOWN,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_LEFT,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
				e = new RemoteControlEvent(null, java.awt.event.KeyEvent.KEY_PRESSED, 0L,
						0, com.sun.dtv.ui.event.RemoteControlEvent.VK_RIGHT,
						java.awt.event.KeyEvent.CHAR_UNDEFINED);
				eventList.add(e);
			}else{
				KeyEvent e = (KeyEvent)event;
				eventList.add(e);
			}
			
			addUserInputEventListener(listener, eventList, reserved);
		}
	}
	
	private void addUserInputEventListener(UserInputEventListener listener, ArrayList eventList, boolean reserved){
		ArrayList list, tmp;
		int size = eventList.size();
		
		for(int i = 0; i < size; i++){
			if(reserved){
				Iterator it = eventListenersMap.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry entry = (Map.Entry)it.next();
					tmp = (ArrayList)entry.getValue();
					int index = tmp.indexOf(new MapUIEvent((UserInputEvent)eventList.get(i)));
					if(index != -1){		
						if(((UserInputEventListener)entry.getKey()) != listener){
							((MapUIEvent)tmp.get(index)).setEventReserve(false);
						}else{
							((MapUIEvent)tmp.get(index)).setEventReserve(reserved);
						}
					}
				}
			}
			
			if(eventListenersMap.containsKey(listener)){
				list = (ArrayList)eventListenersMap.get(listener);
			}else{
				list = new ArrayList(3);
			}
			
			MapUIEvent insertEvent = new MapUIEvent((UserInputEvent)eventList.get(i), reserved);
			
			if(!list.contains(insertEvent)){
				list.add(insertEvent);
			}
			eventListenersMap.put(listener, list);
			((KeyEvent)eventList.get(i)).setNewReservedEvent(false);
		}
	}

	/**
	 * Remove the specified listener from the UserInputEventManager for this
	 * screen. If the specified <A HREF="../../../../../com/sun/dtv/ui/event/UserInputEventListener.html" title="interface in com.sun.dtv.ui.event"><CODE>UserInputEventListener</CODE></A>
	 * has not been added before to this manager, the call to this method has no
	 * effect.
	 *
	 * 
	 * @param listener - the UserInputEventListener to be removed
	 * @see addUserInputEventListener(com.sun.dtv.ui.event.UserInputEventListener, com.sun.dtv.ui.event.UserInputEvent)
	 */
	public void removeUserInputEventListener(UserInputEventListener listener) {
		if (eventListenersMap.containsKey(listener)) {
			eventListenersMap.remove(listener);
		}
	}

	public boolean dispatchUserInputEvent(UserInputEvent event) {
		KeyEvent targetEvent = null;

		ArrayList tmp;
		ArrayList listUIEventListener = new ArrayList(3);

		boolean reserved = !event.isAvailable();

		Iterator it = eventListenersMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			tmp = (ArrayList) entry.getValue();
			MapUIEvent aux = new MapUIEvent(event);
			int index = tmp.indexOf(aux);
			if (index != -1) {
				MapUIEvent target = ((MapUIEvent) tmp.get(index));

				if (reserved && !target.getEventReserve()) {
					continue;
				}

				if (targetEvent == null) {
					targetEvent = target.getKeyEvent();
				}
				listUIEventListener.add((UserInputEventListener) entry.getKey());

				/* o recurso estah reservado e jah encontrou o listener que possui o evento
				 * reservado. */
				if (reserved && target.getEventReserve()) {
					break;
				}
			}
		}

		for (int i = 0; i < listUIEventListener.size(); i++) {
			boolean flag = false;

			if (targetEvent instanceof com.sun.dtv.ui.event.KeyEvent && event instanceof com.sun.dtv.ui.event.KeyEvent) {
				com.sun.dtv.ui.event.KeyEvent e1 = (com.sun.dtv.ui.event.KeyEvent) targetEvent,
					e2 = (com.sun.dtv.ui.event.KeyEvent) event;

				if (targetEvent instanceof com.sun.dtv.ui.event.RemoteControlEvent) {
					com.sun.dtv.ui.event.KeyEvent re = (com.sun.dtv.ui.event.KeyEvent) targetEvent;

					//This code converts VK_F1, VK_F2, VK_F3 and VK_F4 to the respective colored keys. This adaptation if for test purpose only (used to test on PC)
					if ((re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_0 && e2.getKeyCode() == java.awt.event.KeyEvent.VK_F1)
						|| (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_1 && e2.getKeyCode() == java.awt.event.KeyEvent.VK_F2)
						|| (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_2 && e2.getKeyCode() == java.awt.event.KeyEvent.VK_F3)
						|| (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_3 && e2.getKeyCode() == java.awt.event.KeyEvent.VK_F4)) {
						e2.setKeyCode(re.getKeyCode());

						flag = true;
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_0) { //User registered for this key
						if (e2.getKeyCode() == 48) { //  48 is the "zero" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_0); //Fires this event

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_1) { //User registered for this key
						if (e2.getKeyCode() == 49) { //  49 is the "one" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_1);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_2) { //User registered for this key
						if (e2.getKeyCode() == 50) { //  50 is the "two" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_2);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_3) { //User registered for this key
						if (e2.getKeyCode() == 51) { //  51 is the "three" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_3);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_4) { //User registered for this key
						if (e2.getKeyCode() == 52) { //  52 is the "four" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_4);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_5) { //User registered for this key
						if (e2.getKeyCode() == 53) { //  53 is the "five" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_5);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_6) { //User registered for this key
						if (e2.getKeyCode() == 54) { //  54 is the "six" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_6);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_7) { //User registered for this key
						if (e2.getKeyCode() == 55) { //  55 is the "seven" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_7);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_8) { //User registered for this key
						if (e2.getKeyCode() == 56) { //  56 is the "eight" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_8);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_9) { //User registered for this key
						if (e2.getKeyCode() == 57) { //  57 is the "nine" key code sent by MStar platform
							e2.setKeyCode(java.awt.event.KeyEvent.VK_9);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_BACK
						|| re.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
						if (e2.getKeyCode() == 61512 || e2.getKeyCode() == 8) { //TODO:: This code must be changed to the back code return by MStar platform. Currently MStar platform do not support bavk key
							e2.setKeyCode(RemoteControlEvent.VK_BACK);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_0) {
						if (e2.getKeyCode() == 33) {
							e2.setKeyCode(RemoteControlEvent.VK_COLORED_KEY_0);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_1) {
						if (e2.getKeyCode() == 34) {
							e2.setKeyCode(RemoteControlEvent.VK_COLORED_KEY_1);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_2) {
						if (e2.getKeyCode() == 35) {
							e2.setKeyCode(RemoteControlEvent.VK_COLORED_KEY_2);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_COLORED_KEY_3) {
						if (e2.getKeyCode() == 36) {
							e2.setKeyCode(RemoteControlEvent.VK_COLORED_KEY_3);

							flag = true;
						}
					} else if (re.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
						if (e2.getKeyCode() == 40) {
							e2.setKeyCode(KeyEvent.VK_DOWN);

							flag = true;
						}
					} else if (re.getKeyCode() == KeyEvent.VK_UP) {
						if (e2.getKeyCode() == 38) {
							e2.setKeyCode(KeyEvent.VK_UP);

							flag = true;
						}
					} else if (re.getKeyCode() == KeyEvent.VK_LEFT) {
						if (e2.getKeyCode() == 37) {
							e2.setKeyCode(RemoteControlEvent.VK_LEFT);

							flag = true;
						}
					} else if (re.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (e2.getKeyCode() == 39) {
							e2.setKeyCode(RemoteControlEvent.VK_RIGHT);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_CONFIRM) {
						if (e2.getKeyCode() == 80) {
							e2.setKeyCode(RemoteControlEvent.VK_CONFIRM);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_ESCAPE) {
						if (e2.getKeyCode() == 61538) {
							e2.setKeyCode(RemoteControlEvent.VK_ESCAPE);

							flag = true;
						} else if (e2.getKeyCode() == RemoteControlEvent.VK_ESCAPE) {
							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_GUIDE) {
						if (e2.getKeyCode() == 61564) {
							e2.setKeyCode(RemoteControlEvent.VK_GUIDE);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_MENU) {
						if (e2.getKeyCode() == 61701) {
							e2.setKeyCode(RemoteControlEvent.VK_MENU);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_INFO) {
						if (e2.getKeyCode() == 61702) {
							e2.setKeyCode(RemoteControlEvent.VK_INFO);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_PLAY) {
						if (e2.getKeyCode() == 61521) {
							e2.setKeyCode(RemoteControlEvent.VK_PLAY);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_PAUSE) {
						if (e2.getKeyCode() == 61450) {
							e2.setKeyCode(RemoteControlEvent.VK_PAUSE);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_STOP) {
						if (e2.getKeyCode() == 61522) {
							e2.setKeyCode(RemoteControlEvent.VK_STOP);

							flag = true;
						}
					} else if (re.getKeyCode() == com.sun.dtv.ui.event.RemoteControlEvent.VK_RECORD) {
						if (e2.getKeyCode() == 61526) {
							e2.setKeyCode(RemoteControlEvent.VK_RECORD);

							flag = true;
						}
					}
				} else {
					if (e1.getKeyCode() == e2.getKeyCode()) {
						flag = true;
					}
				}

				if (flag == true) {
					((UserInputEventListener) listUIEventListener.get(i)).userInputEventReceived(event);
				}
			}
		}
		
		//Checking if exists a serial runnable object registered to be called after event dispatching
		Runnable runnable = screen.getSerialRunnable();
		if(runnable != null)
		{
			runnable.run();
		}
		screen.notifySerialRunnableCalled();
		
		//Checking if exists a block runnable object registered to be called after event dispatching
		runnable = screen.getBlockedRunnable();
		if(runnable != null)
		{
			runnable.run();
		}
		screen.notifyBlockedRunnableCalled();

		if(!listUIEventListener.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	//Inner class #####################################################
	class MapUIEvent {
		private UserInputEvent event;
		private KeyEvent keyEvent;
		private boolean eventReserve = false;
		
		public MapUIEvent(UserInputEvent event){
			this(event, false);
		}
		
		public MapUIEvent(UserInputEvent event, boolean eventReserve){
			this.event = event;
			this.eventReserve = eventReserve;
			this.keyEvent = (KeyEvent)event;
		}
		
		public UserInputEvent getEvent(){
			return this.event;
		}
		
		public KeyEvent getKeyEvent(){
			return this.keyEvent;
		}
		
		public boolean getEventReserve(){
			return this.eventReserve;
		}
		
		public void setEventReserve(boolean reserve){
			this.eventReserve = reserve;
		}

		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((keyEvent == null) ? 0 : keyEvent.hashCode());
			return result;
		}

		public boolean equals(Object obj) {
			if (this == obj){
				return true;
			}
			if (obj == null){
				return false;
			}
			if (getClass() != obj.getClass()){
				return false;
			}
			MapUIEvent other = (MapUIEvent) obj;
			if (!getOuterType().equals(other.getOuterType())){
				return false;
			}
			if (keyEvent == null) {
				if (other.keyEvent != null){
					return false;
				}
			} else if (!keyEvent.equals(other.keyEvent)){
				return false;
			}
			return true;
		}

		private UserInputEventManager getOuterType() {
			return UserInputEventManager.this;
		}
	}
}

