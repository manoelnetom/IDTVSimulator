package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.ui.ViewOnlyComponent;
public class CheckBox extends Button
{
	
	private boolean selected = false;

	/**
	 * Constructs a checkbox with no text.
	 *
	 * 
	 */
	public CheckBox() {
		this("");
	}

	/**
	 * Constructs a checkbox with the given text.
	 *
	 * 
	 * @param text - to display next to the checkbox
	 */
	public CheckBox(String text)
	{
		this(text, null);
	}

	/**
	 * Constructs a checkbox with the given icon.
	 *
	 * 
	 * @param icon - icon to display next to the checkbox
	 */
	public CheckBox(Image icon) {
		this("", icon);
	}

	/**
	 * Constructs a checkbox with the given text and icon.
	 *
	 * 
	 * @param text - to display next to the checkbox
	 * @param icon - icon to display next to the text
	 */
	public CheckBox(String text, Image icon) {
		super(text,icon);
		setUIID("CheckBox");
		setHorizontalAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT);
		setVerticalAlignment(ViewOnlyComponent.VERTICAL_ALIGN_CENTER);
	}
	
	public String toString()
	{
		return "com.sun.dtv.lwuit.CheckBox";
	}

	/**
	 * Return true if the checkbox is selected.
	 *
	 * 
	 * 
	 * @return true if the checkbox is selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Selects the current checkbox.
	 *
	 * 
	 * @param selected - value for selection
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		
		repaint();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#paint(com.sun.dtv.lwuit.Graphics)">Component</A></CODE></B></DD>
	 * This method paints the Component on the screen, it should be overriden
	 * by subclasses to perform custom drawing or invoke the UI API's to let
	 * the PLAF perform the rendering.
	 *
	 * 
	 * @param g - the component graphics
	 * @see paint in interface Animation
	 * @see paint in interface ViewOnlyComponent
	 * @see paint in class Button
	 */
	public void paint(Graphics g) {
		UIManager.getInstance().getLookAndFeel().drawCheckBox(g, this);
	}

	/**
	 *
	 *
	 */
	protected Dimension calcPreferredSize() {
		return UIManager.getInstance().getLookAndFeel().getCheckBoxPreferredSize(this);
	}
	
	void fireActionEvent() {
		setSelected(!selected);
		super.fireActionEvent();
	}

}
