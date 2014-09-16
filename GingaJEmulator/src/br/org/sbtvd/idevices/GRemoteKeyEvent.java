package br.org.sbtvd.idevices;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.ui.event.KeyEvent;

public class GRemoteKeyEvent extends KeyEvent {
	
	private GRemoteDevice source;
	
	public GRemoteKeyEvent(Component source, int id, long when, int modifiers, int keyCode, char keyChar, GRemoteDevice dev) {
		super(source, id, when, modifiers, keyCode, keyChar);
		this.source = dev;
	}
	
	public GRemoteDevice getSourceDevice() {
		return source;
	}

}
