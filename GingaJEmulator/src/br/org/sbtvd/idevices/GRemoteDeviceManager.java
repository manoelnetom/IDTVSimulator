package br.org.sbtvd.idevices;

abstract public class GRemoteDeviceManager {
		
	protected static GRemoteDeviceManager instance;
	
	public static GRemoteDeviceManager getInstance() {
		return instance;
	}
	
	abstract public GRemoteDevice[] getActiveDeviceList();

}
