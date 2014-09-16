package br.org.sbtvd.idevices;

import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;
import com.sun.dtv.ui.event.UserInputEvent;

public class GRemoteUserEvent implements UserInputEvent {
	
	private GRemoteDevice source;
	private String deviceDesc;
	private int code;
	private int id;
	
	/**
	 * @author erisvaldo
	 * @since 25-03-2010
	 * GRemoteUserEvent new constructor
	 */
	public GRemoteUserEvent(Object source, int id, int keyCode, GRemoteDevice dev) {
		this.deviceDesc = dev.getDescription();
		this.code = keyCode;
		this.id = id;
		this.source = dev;
	}
	
	public GRemoteDevice getSourceDevice() {
		return source;
	}

	// @Override
	public boolean isAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	// @Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	// @Override
	public void reserve(boolean force, long timeoutms,
			ScarceResourceListener listener) throws IllegalArgumentException,
			TimeoutException, SecurityException {
		// TODO Auto-generated method stub
		
	}
}
