package com.sun.dtv.lwuit;

import java.util.List;
import java.util.ArrayList;

public class ButtonGroup
{
	private List buttons;
	private RadioButton selected;

	/**
	 * Creates a new instance of ButtonsGroup.
	 *
	 * 
	 */
	public ButtonGroup()
	{
		buttons = new ArrayList();

		selected = null;
	}

	/**
	 * Adds a RadioButton to the group.
	 *
	 * 
	 * @param rb - a RadioButton to add
	 */
	public void add(RadioButton rb)
	{
		if (!buttons.contains((Object)rb)) {
			rb.setGroup(this);
			buttons.add((Object)rb);
		}
	}

	/**
	 * Removes a RadioButton from the group.
	 *
	 * 
	 * @param rb - a RadioButton to remove
	 */
	public void remove(RadioButton rb)
	{
		clearSelection();

		if (buttons.contains((Object)rb)) {
			buttons.remove((Object)rb);
		}
	}

	/**
	 * Clears the selection such that none of the buttons in the ButtonGroup are selected.
	 *
	 * 
	 */
	public void clearSelection()
	{
		if (selected != null) {
			selected.setSelected(false);
		}
	}

	/**
	 * Returns the number of buttons in the group.
	 *
	 * 
	 * @return number of radio buttons in the group
	 */
	public int getButtonCount()
	{
		return buttons.size();
	}

	/**
	 * Returns whether a radio button in the group is selected.
	 * 
	 * 
	 * @return true if a selection was made in the radio button group
	 */
	public boolean isSelected()
	{
		if (selected != null) {
			return selected.isSelected();
		}

		return false;
	}

	/**
	 * Return the index of the selected button within the group.
	 *
	 * 
	 * @return the index of the selected button within the group
	 */
	public int getSelectedIndex()
	{
		if (selected != null) {
			return buttons.indexOf(selected);
		}

		return -1;
	}

	/**
	 * Returns the radio button at the given group index.
	 *
	 * 
	 * @param index - offset within the group starting with 0 and no larger than getButtonCount()
	 * @return the radio button instance
	 */
	public RadioButton getRadioButton(int index)
	{
		if (index >= 0 && index < buttons.size()) {
			return (RadioButton)buttons.get(index);
		}

		return null;
	}

	/**
	 * Selects the given radio button.
	 *
	 * 
	 * @param rb - the radio button to set as selected
	 */
	public void setSelected(RadioButton rb)
	{
		if (selected != null) {
			selected.setSelected(false);
		}else{
			for(int i = 0; i < buttons.size(); i++){
				selected = ((RadioButton)buttons.get(i));
				if(selected.isSelected()){
					selected.setSelected(false);
					break;
				}
			}
		}
		
		selected = rb;

		if(!selected.isSelected()){
			selected.setSelected(true);
		}
		
	}

	/**
	 * Sets the selected Radio button by index.
	 *
	 * 
	 * @param index - the index of the radio button to mark as selected
	 */
	public void setSelected(int index)
	{
		if (index >= 0 && index < buttons.size()) {
			setSelected((RadioButton)buttons.get(index));
		}
	}

}
