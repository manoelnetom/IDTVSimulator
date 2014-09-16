package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.ui.ViewOnlyComponent;
public class RadioButton extends Button
{
	private boolean selected = false;

	/**
	 * The group in which this button is a part
	 */
	private ButtonGroup group;

	/**
	 * Constructs a radio with the given text.
	 *
	 * 
	 * @param text - to display next to the button
	 */
	public RadioButton(String text)
	{
		this(text, null);
	}

	/**
	 * Creates an empty radio button.
	 *
	 * 
	 */
	public RadioButton()
	{
		this("", null);
	}

	/**
	 * Constructs a radio with the given icon.
	 *
	 * 
	 * @param icon - icon to show next to the button
	 */
	public RadioButton(Image icon)
	{
		this("", icon);
	}

	/**
	 * Constructs a radio with the given text and icon.
	 *
	 * 
	 * @param text - to display next to the button
	 * @param icon - icon to show next to the button
	 */
	public RadioButton(String text, Image icon)
	{
		super(text, icon);
		setUIID("RadioButton");
		setHorizontalAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT);
		setVerticalAlignment(ViewOnlyComponent.VERTICAL_ALIGN_CENTER);
	}


	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#toString()">Component</A></CODE></B></DD>
	 * Overriden to return a useful value for debugging purposes.
	 * Overrides Object.toString.
	 *
	 * 
	 * @return a string representation of this component
	 * @see toString in class Component
	 */
	public String toString()
	{
		return "com.sun.dtv.lwuit.RadioButton";
	}

	/**
	 * Returns true if the radio button is selected.
	 *
	 * 
	 * 
	 * @return true if the radio button is selected
	 */
	public boolean isSelected()
	{
		return selected;
	}

	/**
	 * Selects the current radio button.
	 *
	 * 
	 * @param selected - value for selection
	 */
	public void setSelected(boolean selected)
	{
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
	public void paint( Graphics g)
	{
		UIManager.getInstance().getLookAndFeel().drawRadioButton(g, this);
	}

	/**
	 * @inheritDoc
	 *
	 */
	protected Dimension calcPreferredSize(){
		return UIManager.getInstance().getLookAndFeel().getRadioButtonPreferredSize(this);
	}

	/**
	 * Setting a new button group
	 * 
	 * @param group a new button group
	 */
	void setGroup(ButtonGroup group) {
		this.group = group;
	}

	/**
	 * @inheritDoc
	 *
	 */
	void fireActionEvent() {
		if(group != null) {
			group.setSelected(this);
		}else{
			setSelected(!selected);
		}
		super.fireActionEvent();
	}
}
