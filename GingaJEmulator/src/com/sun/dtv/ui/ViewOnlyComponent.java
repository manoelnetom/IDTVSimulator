package com.sun.dtv.ui;

import java.awt.AWTEvent;

import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Image;

public interface ViewOnlyComponent extends MatteEnabled{
    
	//Constant to be used with the setHorizontalAlignment method of ViewOnlyComponent.
	public static final int HORIZONTAL_ALIGN_LEFT = 0;
	//Constant to be used with the setHorizontalAlignment method of ViewOnlyComponent.
	public static final int HORIZONTAL_ALIGN_CENTER = 1;
	//Constant to be used with the setHorizontalAlignment method of ViewOnlyComponent.
	public static final int HORIZONTAL_ALIGN_RIGHT = 2;
	//Constant to be used with the setHorizontalAlignment method of ViewOnlyComponent.
	public static final int HORIZONTAL_ALIGN_JUSTIFIED = 3;
	//Constant to be used with the setVerticalAlignment method of ViewOnlyComponent.
	public static final int VERTICAL_ALIGN_TOP = 4;
	//Constant to be used with the setVerticalAlignment method of ViewOnlyComponent.
	public static final int VERTICAL_ALIGN_CENTER = 5;
	//Constant to be used with the setVerticalAlignment method of ViewOnlyComponent.
	public static final int VERTICAL_ALIGN_BOTTOM = 6;
	//Constant to be used with the setVerticalAlignment method of ViewOnlyComponent.
	public static final int VERTICAL_ALIGN_JUSTIFIED = 7;
	//Constant to be used with the setScalingMode method of ViewOnlyComponent.
	public static final int SCALE_NO = 8;
	//Constant to be used with the setScalingMode method of ViewOnlyComponent.
	public static final int SCALE_ASPECT_PROOF = 9;
	// Constant to be used with the setScalingMode method of ViewOnlyComponent.
	public static final int SCALE_NO_ASPECT_PROOF = 10;

	// Constant to be used with several methods of ViewOnlyComponent requiring a state parameter.
	public static final int STATE_DISABLED = 0;
	// Constant to be used with several methods of ViewOnlyComponent requiring a state parameter.
	public static final int	STATE_ENABLED = 1;

	/**
	 * Returns the animated content for this component, depending on the current state.
	 *
	 */
	public Image[] getAnimateContent(int state);

	/**
	 * Returns the graphical content for this component, depending on the current state.
	 *
	 */
	public Image getGraphicContent(int state);

	/**
	 * Retrieve the horizontal alignment for any state-based content in this component.
	 *
	 */
	public int getHorizontalAlignment();

	/**
	 * Return the current interaction state of the component.
	 *
	 */
	public int getInteractionState();

	/**
	 * Retrieve the scaling mode for this component.
	 *
	 */
	public int getScalingMode();

	/**
	 * Returns the text content for this component, depending on the current state.
	 *
	 */
	public String getTextContent(int state);

	/**
	 * Gets the text layout manager that is currently in use for text layout in this component.
	 *
	 */
	public TextLayoutManager getTextLayoutManager();

	/**
	 * Retrieve the vertical alignment for any state-based content in this component.
	 *
	 */
	public int 	getVerticalAlignment();

	/**
	 * Returns true if double buffering is available and activated.
	 *
	 */
	public boolean isDoubleBuffered();

	/**
	 * Returns true if the whole area of the Component (as returned by the com.sun.dtv.lwuit.Component#getBounds 
	 * method, is opaque.
	 *
	 */
	public boolean isOpaque();

	/**
	 * Method to paint the component.
	 *
	 */
	public void paint(Graphics g);

	/**
	 * Handle the specified AWTEvent.
	 *
	 */
	public void processEvent(AWTEvent event);

	/**
	 * Assigns an array of graphical content to be used for animation to this component which is state-dependent.
	 *
	 */
	public void setAnimateContent(Image[] images, int state);

	/**
	 * Assigns graphical content to this component which is state-dependent.
	 *
	 */
	public void setGraphicContent(Image image, int state);

	/**
	 * Set the horizontal alignment for any state-based content in this component.
	 *
	 */
	public void setHorizontalAlignment(int alignment);

	/**
	 * Set the current state of the component with respect to interaction.
	 *
	 */
	public void setInteractionState(int state);

	/**
	 * Set the scaling mode for this component.
	 *
	 */
	public void 	setScalingMode(int scaling);

	/**
	 * Assigns text content to this component which is state-dependent.
	 *
	 */
	public void 	setTextContent(String text, int state);

	/**
	 * Sets the text layout manager that should be used for text layout in this component.
	 *
	 */
	public  void 	setTextLayoutManager(TextLayoutManager manager);

	/**
	 * Set the vertical alignment for any state-based content in this component.
	 *
	 */
	public void 	setVerticalAlignment(int alignment);

}
