package br.org.sbtvd.ui;

import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;

import com.sun.dtv.ui.Device;
import com.sun.dtv.ui.Plane;
import com.sun.dtv.ui.PlaneSetup;
import com.sun.dtv.ui.DTVContainer;

public class SwitchingPlane extends Plane {

	public SwitchingPlane() {
		super("SwitchingPlane");

		capabilities = new SwitchingCapabilities();
		setups = new PlaneSetup[1];
		setups[0] = new SwitchingPlaneSetup(this);
		currentSetup = getDefaultSetup();

                if(capabilities.isGraphicsRenderingSupported() || capabilities.isWidgetRenderingSupported()) {
		    this.dtvContainer = new SwitchingDTVContainer();

                    this.dtvContainer.setLayout(null);
                    this.dtvContainer.setSize(currentSetup.getScreenResolution());
                    this.dtvContainer.setVisible(true);
                    this.dtvContainer.setPlane(this);
                    this.dtvContainer.setType(DTVContainer.SWITCHING);
                    this.dtvContainer.getStyle().setBgColor(setups[0].getColor());
                }
                else
                {
                    this.dtvContainer = null;
                }
	}

	protected void notifyScarceResourceTypeListeners(boolean reserved) {
		for(int i = 0; i < resourceListeners.size(); i++){
			if(reserved){
				((ResourceTypeListener)resourceListeners.get(i)).reserved(0);
			}else{
				((ResourceTypeListener)resourceListeners.get(i)).released(1);
			}
		}
	}
	
	public static Plane reserveOne(boolean force, long timeoutms, ScarceResourceListener listener) throws IllegalArgumentException, TimeoutException
	{
		Plane[] planes = Device.getInstance().getDefaultScreen().getAllPlanes();
		
		for(int i = 0; i < planes.length; i++){
			if(planes[i].getID().equals("SwitchingPlane")){
				planes[i].reserve(force, timeoutms, listener);
				return planes[i];
			}
		}
		
		return null;
	}

}
