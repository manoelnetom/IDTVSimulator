package com.sun.dtv.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.sun.dtv.lwuit.Display;
import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Point;

import java.util.*;

class StackContainer{
	public Plane plane;
	public DTVContainer container;
}

public class DTVContainer extends Container {

	// Constant to be used in the setBackgroundMode method.
	public static final int BACKGROUND_FILL = 1;
	// Constant to be used in the setBackgroundMode method.
	public static final int BACKGROUND_NO_FILL = 0;
	
	// Constant to be used in the setBackgroundImageRenderMode method.
	public static final int BACKGROUND_IMAGE_CENTERED = 2;
	// Constant to be used in the setBackgroundImageRenderMode method.
	public static final int BACKGROUND_IMAGE_NONE = 0;
	// Constant to be used in the setBackgroundImageRenderMode method.
	public static final int BACKGROUND_IMAGE_STRETCHED = 1;
	// Constant to be used in the setBackgroundImageRenderMode method.
	public static final int BACKGROUND_IMAGE_TILED = 3;

	private static Stack containers = new Stack();

	private java.util.List windowListeners = new ArrayList();

	private int backgroundMode = BACKGROUND_NO_FILL;
	private int backgroundImageRenderMode = BACKGROUND_IMAGE_NONE;
	private Image backgroundImage;
	private Plane plane;

        public static final int SWITCHING = 0;
        public static final int STILL = 1;
        public static final int GRAPHICS = 2;
        public static final int VIDEO = 3;
        
        private int dtvContainerType = -1;
        
	protected DTVContainer(){
		super();
                
                this.dtvContainerType = GRAPHICS;
	}
        
        public void setType(int type)
        {
            this.dtvContainerType = type;
        }
        
	public void setPlane(Plane plane){
		this.plane = plane;
	}
        
	public void addComponent(Component cmp)
	{
		if (layout instanceof com.sun.dtv.lwuit.layouts.BorderLayout) {
			throw new IllegalArgumentException("Cannot add component to BorderLayout Container without constraint parameter");
		}

		//Accordingly to Operational Guideline
		if(cmp instanceof Form)
		{
		    if(Display.getInstance().getCurrent() != null)
		    {
			Display.getInstance().getCurrent().setVisible(false);
		    }

		    Display.getInstance().setCurrent((Form) cmp);
		}

		insertFormAt(components.size(), cmp);
	}

	public void addComponent(Object constraints, Component cmp)
	{
		//Accordingly to Operational Guideline
		if(cmp instanceof Form)
		{
		    if(Display.getInstance().getCurrent() != null)
		    {
			Display.getInstance().getCurrent().setVisible(false);
		    }
		    
		    Display.getInstance().setCurrent((Form) cmp);
		}

		layout.addLayoutComponent(constraints, cmp, this);
		insertFormAt(components.size(), cmp);
	}

	/**
	 * Adds a Component to this DTVContainer behind all previously added components.
	 *
	 */
	public void addToBack(Component component) {
		insertFormAt(0, component);
	}

	/**
	 * Adds a Component to this DTVContainer directly in front of all previously added components.
	 *
	 */
	public void addToFront(Component component) {
		insertFormAt(components.size(), component);
	}

	/**
	 * Add a listener to receive any java.awt.event.WindowEvents sent to this DTVContainer.
	 *
	 */
	public void addWindowListener(WindowListener listener) {
		if (windowListeners.contains((Object)listener) == false) {
			windowListeners.add((Object)listener);
		}
	}

	/**
	 * Remove a listener from this DTVContainer so that it no longer receives any java.awt.event.WindowEvents.
	 *
	 */
	public void removeWindowListener(WindowListener listener) {
		if (windowListeners.contains((Object)listener) == true) {
			windowListeners.remove((Object)listener);
		}
	}

	/**
	 * Consumes a java.awt.event.WindowEvents for this DTVContainer.
	 *
	 */
	public void consumeWindowEvent(WindowEvent event) {

	}

	/**
	 * Disposes of this DTVContainer.
	 *
	 */
	public void dispose() {
		if (backgroundImage != null) {
			// backgroundImage.dispose();
		}
	}

	/**
	 * Get the background image assigned to this DTVContainer if any.
	 *
	 */
	public Image getBackgroundImage() {
		return backgroundImage;

	}

	/**
	 * Obtains the rendering mode for a background image as specified using the setBackgroundImageRenderMode method.
	 *
	 */
	public int getBackgroundImageRenderMode() {
		return backgroundImageRenderMode;

	}

	/**
	 * Obtains the background mode as specified using the setBackgroundMode method.
	 *
	 */
	public int getBackgroundMode() {
		return backgroundMode;

	}
	
	public int getType(){
		return this.dtvContainerType;
	}

	/**
	 * Returns a DTVContainer that best corresponds to the input DTVContainerPattern, 
	 * or null if such an DTVContainer cannot be found.
	 *
	 */
	public static DTVContainer getBestDTVContainer(DTVContainerPattern pattern) {
            Plane[] planes = Plane.getInstances();
            
            DTVContainer dtv=null, ret=null;
            int value=0, preValue=0, required=0;
            DTVContainerPattern dtvPattern=null;
            
            for(int i=0; i<planes.length; i++)
            {
                dtv = planes[i].getDTVContainer();
                dtvPattern = dtv.getDTVContainerPattern();
                
                //DIMENSION
                if(pattern.getPriority(DTVContainerPattern.DTV_CONTAINER_DIMENSION)==PlaneSetupPattern.REQUIRED){
                    if(!pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_DIMENSION).equals(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_DIMENSION)))
                    {
                        //We discard this container because it does not satisfies a required preference
                        continue;
                    }
                }
                else if(pattern.getPriority(DTVContainerPattern.DTV_CONTAINER_DIMENSION)==PlaneSetupPattern.PREFERRED)
                {
                    if(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_DIMENSION).equals(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_DIMENSION)))
                    {
                        //Adds a point to this container because it satifies a preferred preference
                        value++;
                    }
                }
                else
                {
                    //Adds a point to this container because the priority for the preference is "DONT_CARE"
                    value++;
                }
                
                //LOCATION
                
                if(pattern.getPriority(DTVContainerPattern.DTV_CONTAINER_LOCATION)==PlaneSetupPattern.REQUIRED){
                    if(!pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_LOCATION).equals(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_LOCATION)))
                    {
                        //We discard this container because it does not satisfies a required preference
                        continue;
                    }
                }
                else if(pattern.getPriority(DTVContainerPattern.DTV_CONTAINER_LOCATION)==PlaneSetupPattern.PREFERRED)
                {
                    if(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_LOCATION).equals(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_LOCATION)))
                    {
                        //Adds a point to this container because it satifies a preferred preference
                        value++;
                    }
                }
                else
                {
                    //Adds a point to this container because the priority for the preference is "DONT_CARE"
                    value++;
                }
                
                //PLANE_SETUP
                
                if(pattern.getPriority(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP)==PlaneSetupPattern.REQUIRED){
                    if((pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP) != null)
                        &&
                        !pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP).equals(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP)))
                    {
                        //We discard this container because it does not satisfies a required preference
                        continue;
                    }
                }
                else if(pattern.getPriority(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP)==PlaneSetupPattern.PREFERRED)
                {
                    if(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP).equals(pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP)))
                    {
                        //Adds a point to this container because it satifies a preferred preference
                        value++;
                    }
                }
                else
                {
                    //Adds a point to this container because the priority for the preference is "DONT_CARE"
                    value++;
                }
                
                //We give priority to the Graphic DTVContainer
                if(dtv.getType() == DTVContainer.GRAPHICS)
                {
                    if(value >= preValue)
                    {
                        preValue=value;
                        value=0;
                        ret = dtv;
                    }
                }
                else
                {//It is not a Graphic DTVContainer, so we select only if it has bigger value
                    if(value>preValue)
                    {
                        preValue=value;
                        value=0;
                        ret = dtv;
                    }
                }
            }
            
            return ret;
	}
        
	/**
	 * Returns an DTVContainerPattern closest to to the input DTVContainerPattern and at the same time 
	 * specifying a DTVContainer which could be successfully created on the underlying platform at the 
	 * time this method is called.
	 */
	public static DTVContainerPattern getBestDTVContainerPattern(DTVContainerPattern pattern) {
		DTVContainer dtv= DTVContainer.getBestDTVContainer(pattern);
		if(dtv==null){
			return null;
		}
		return dtv.getDTVContainerPattern();
	}
		

	/**
	 * Returns the DTVContainer the caller is displayed within.
	 *
	 */
	public static DTVContainer getCurrentDTVContainer() {
		return Plane.getCurrentPlane().getDTVContainer();
	}

	/**
	 * Create a DTVContainer for the specified Plane.
	 *
	 */
	public static DTVContainer getDTVContainer(Plane plane) {
            DTVContainer dtvContainer = plane.getDTVContainer();
            if(dtvContainer == null)
            {
                throw new IllegalArgumentException();
            }
            else
            {
                return dtvContainer;
            }
	}

	public static void removeAllReferences() {
	    Plane[] planes = Plane.getInstances();
	    DTVContainer dtvContainer = null;
	    for(int i=0; i<planes.length; i++)
	    {
	    	dtvContainer = planes[i].getDTVContainer();
	    	if(dtvContainer != null){
	    		dtvContainer.removeAll();
	    	}
	    }

	    Display.getInstance().getImplementation().getFrame().repaint();
	}

	/**
	 * Create a DTVContainer for the specified Plane, which can be used as an Xlet container.
	 *
	 */
	public static DTVContainer getDTVContainer(Plane plane, java.awt.Container container) {
		Plane planes[] = Screen.getCurrentScreen().getAllPlanes(), graphicPlane = null;
		for (int i = 0; i < planes.length; i++) {
			if (planes[i].getID().equals("GraphicPlane")) {
				graphicPlane = planes[i];
				break;
			}
		}
		
		if(plane != graphicPlane){
			throw new IllegalArgumentException();
		}
		
		if(container != Display.getInstance().getRootContainer()){
			throw new IllegalArgumentException();
		}
		
		DTVContainer dtv = DTVContainer.getDTVContainer(graphicPlane);
		
		return dtv;
	}

	/**
	 * Returns a DTVContainerPattern describing this DTVContainer.
	 *
	 */
	public DTVContainerPattern getDTVContainerPattern() {
		
		
		DTVContainerPattern pattern = new DTVContainerPattern();
		pattern.setPreference(DTVContainerPattern.DTV_CONTAINER_DIMENSION, this.getPreferredSize(),PlaneSetupPattern.PREFERRED);
		pattern.setPreference(DTVContainerPattern.DTV_CONTAINER_LOCATION, new Point(this.getX(),this.getY()),PlaneSetupPattern.PREFERRED);
		pattern.setPreference(DTVContainerPattern.DTV_CONTAINER_PLANE_SETUP, this.plane.getCurrentSetup(), PlaneSetupPattern.REQUIRED);
		return pattern;
	}

	/**
	 * Obtains the Component contained by this DTVContainer which currently owns the input focus.
	 *
	 */
	public Component getFocusOwnerComponent() {
		return Display.getInstance().getCurrent();
	}

	/**
	 * Obtains whether all drawing operations performed for this DTVContainer are automatically double buffered.
	 *
	 */
	public boolean isDoubleBuffered() {
		return false;

	}

	/**
	 * Obtains whether the full area of the DTVContainer as given by the Component.getBounds() method, is fully opaque.
	 *
	 */
	public boolean isOpaque() {
		return false;

	}

	void delay(int s) {
		try {
			Thread.sleep(s);
		} catch (Exception e) {
		}
	}

	private int removeComponentIndex(Component component) {
		int i = 0;

		for (Enumeration e=components.elements(); e.hasMoreElements(); ) {
			if (component == e.nextElement()) {
				components.remove(component);

				return i;
			}

			i++;
		}

		return -1;
	}

	/**
	 * Moves the specified Component one level up in the Z-order.
	 *
	 */
	public void pop(Component component) {
		int index = removeComponentIndex(component);

		if (index < 0) {
			return;
		}

		index++;

		if (components.size() > 0 && index > components.size()-1) {
			index = components.size()-1;
		}

		insertFormAt(index, component);
	}

	/**
	 * Moves the specified Component move directly in front of the Component behind.
	 *
	 */
	public void popInFrontOf(Component move, Component behind) {
		int mindex = removeComponentIndex(move);

		if (mindex < 0) {
			return;
		}

		int bindex = removeComponentIndex(behind);

		if (bindex < 0) {
			insertFormAt(mindex, move);

			return;
		}

		insertFormAt(bindex, move);
		insertFormAt(bindex, behind);
	}

	/**
	 * Brings the specified Component to the front of the Z-order in this DTVContainer.
	 *
	 */
	public void popToFront(Component component) {
		int index = removeComponentIndex(component);

		if (index < 0) {
			return;
		}

		addToFront(component);
	}

	/**
	 * Moves the specified Component one level down in the Z-order.
	 *
	 */
	public void push(Component component) {
		int index = removeComponentIndex(component);

		if (index < 0) {
			return;
		}

		index--;

		if (index < 0) {
			index = 0;
		}

		insertFormAt(index, component);
	}

	/**
	 * Moves the specified Component move directly behind the Component front.
	 *
	 */
	public void pushBehind(Component move, Component front) {
		int mindex = removeComponentIndex(move);

		if (mindex < 0) {
			return;
		}

		int bindex = removeComponentIndex(front);

		if (bindex < 0) {
			insertFormAt(mindex, move);

			return;
		}

		insertFormAt(bindex, front);
		insertFormAt(bindex, move);
	}

	/**
	 * Brings the specified Component to the back of the Z-order in this DTVContainer.
	 *
	 */
	public void pushToBack(Component component) {
		int index = removeComponentIndex(component);

		if (index < 0) {
			return;
		}

		addToBack(component);
	}

	/**
	 * Resizes this DTVContainer so that it best corresponds to the input DTVContainerPattern, or do nothing 
	 * if not necessary.
	 *
	 */
	public DTVContainerPattern resize(DTVContainerPattern pattern) {
		this.setSize((Dimension)pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_DIMENSION));
		Point point = ((Point)pattern.getPreferenceValue(DTVContainerPattern.DTV_CONTAINER_LOCATION));
		this.setX(point.getX());
		this.setY(point.getY());
		Display.getInstance().getRootContainer().setBounds(getX(), getY(), getWidth(), getHeight());
		this.repaint();
		return pattern;
	}

	/**
	 * Sets a background image to be painted in the background of the DTVContainer.
	 *
	 */
	public void setBackgroundImage(Image image) {
		backgroundImage = image;
	}

	/**
	 * Set the rendering mode of a background image assigned to this DTVContainer.
	 *
	 */
	public void setBackgroundImageRenderMode(int mode) {
		backgroundImageRenderMode = mode;
	}

	/**
	 * Set the background mode of this DTVContainer.
	 *
	 */
	public void setBackgroundMode(int mode) {
		backgroundMode = mode;
	}

	/**
	 * Brings this DTVContainer to the front.
	 *
	 */
	public void setToFront() {
		
		DTVContainer.containers.remove(DTVContainer.containers.search(this));
		DTVContainer.containers.push(this);
	}

	/**
	 * Set visibility of the DTVContainer to true or false, depended on the argument's value.
	 *
	 */
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}

}

