package br.org.sbtvd.ui;

import com.sun.dtv.ui.*;
import com.sun.dtv.resources.*;

public class GraphicPlane extends Plane {

	public GraphicPlane() {
		super("GraphicPlane");

		capabilities = new GraphicCapabilities();
		setups = new PlaneSetup[1];
		setups[0] = new GraphicPlaneSetup(this);
		currentSetup = getDefaultSetup();

                if(capabilities.isGraphicsRenderingSupported() || capabilities.isWidgetRenderingSupported()) {
		    this.dtvContainer = new GraphicDTVContainer();

                    this.dtvContainer.setLayout(null);
                    this.dtvContainer.setSize(currentSetup.getScreenResolution());
                    this.dtvContainer.setVisible(true);
                    this.dtvContainer.setPlane(this);
                    this.dtvContainer.setType(DTVContainer.GRAPHICS);
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
			if(planes[i].getID().equals("GraphicPlane")){
				planes[i].reserve(force, timeoutms, listener);
				return planes[i];
			}
		}
		
		return null;
	}

}
