package br.org.sbtvd.idevices;

import java.util.Properties;
import java.util.Vector;

abstract public class GRemoteDevice {

	public static final int KEYBOARD_FACILITY 			= 1;
	public static final int SCREEN_FACILITY 			= 2;
	public static final int SOUND_CAPTURE_FACILITY 		= 3;
	public static final int SOUND_PLAYER_FACILITY 		= 4;
	public static final int PICTURE_CAPTURE_FACILITY	= 5;
	public static final int VIDEO_CAPTURE_FACILITY 		= 6;
	public static final int VIDEO_PLAYER_FACILITY 		= 7;
	public static final int DIALING_FACILITY 			= 8;
	
	private int id;
	private String description;
	private int[] facilities;
	private Properties params;
	protected Vector listeners;

	protected GRemoteDevice(int id, String description, int[] facilities) {
		this.id = id;
		this.description = description;
		this.facilities = facilities;
		params = new Properties();
		listeners = new Vector();
	}
	
	public int getID() {
		return id; 
	}
	
	public String getDescription() {
		return description;
	}
	
	public int[] getFacilities() {
		return facilities;
	}

	public String getParameter(String name) {
		return params.getProperty(name);
	}
	
	public void setParameter(String name, String value) {
		params.setProperty(name, value);
	}
	
	abstract public boolean isActive();
	
	public void addActionListener(GRemoteDeviceActionListener lis) {
		listeners.add(lis);
	}
	
	public void removeActionListener(GRemoteDeviceActionListener lis) {
		listeners.remove(lis);
	}

	abstract public int submitFile(java.io.File file) throws java.io.IOException;
	
	abstract public void startAudioRecording() throws java.io.IOException;

	abstract public void stopAudioRecording() throws java.io.IOException;

	abstract public void startVideoRecording() throws java.io.IOException;

	abstract public void stopVideoRecording() throws java.io.IOException;
	
	abstract public int takePicture() throws java.io.IOException;

	abstract public void dialNumber(String number) throws java.io.IOException;

	/**
	 * @author erisvaldo
	 * @since 25-03-2010
	 * 
	 * Returns a HScene instance of Havi API (Ginga-J Scene A, GEM specification)
	 * @return HScene HScene instance of Havi API.
	 */
//	abstract public org.havi.ui.HScene getHScene();

	/**
	 * @author erisvaldo
	 * @since 25-03-2010
	 * 
	 * Returns a DTVContainer instance (Ginga-J Scene B, JavaDTV specification)
	 * @return DTVContainer DTVContainer instance of DTV-UI.
	 */
	abstract public com.sun.dtv.ui.DTVContainer getDTVContainer();	
}
