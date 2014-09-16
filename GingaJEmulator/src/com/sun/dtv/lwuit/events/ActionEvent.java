package com.sun.dtv.lwuit.events;

import com.sun.dtv.lwuit.Command;

public class ActionEvent extends Object
{
	private Object source;
	private int keyEvent;
	private boolean isConsumed;

	/**
	 * Creates a new instance of ActionEvent.
	 *
	 * 
	 * @param source - element for the action event
	 */
	public ActionEvent(Object source)
	{
		this.source = source;
		this.keyEvent = -1;
		this.isConsumed = false;
	}

	/**
	 * Creates a new instance of ActionEvent.
	 *
	 * 
	 * @param source - element for the action event
	 * @param keyEvent - the key that triggered the event
	 */
	public ActionEvent(Object source, int keyEvent)
	{
		this.source = source;
		this.keyEvent = keyEvent;
	}

	/**
	 * The element that triggered the action event, useful for decoupling event
	 * handling code.
	 *
	 * 
	 * 
	 * @return the element that triggered the action event
	 */
	public Object getSource()
	{
		return this.source;
	}

	/**
	 * If this event was triggered by a key press this method will return the
	 * appropriate keycode.
	 *
	 * 
	 * 
	 * @return the key that triggered the event
	 */
	public int getKeyEvent()
	{
		return this.keyEvent;
	}

	/**
	 * If this event was sent as a result of a command action this method returns
	 * that command.
	 *
	 * 
	 * 
	 * @return the command action that triggered the action event
	 */
	public Command getCommand()
	{
		if (source instanceof Command) {
			return (Command)this.source;
		}

		return null;
	}

	/**
	 * Consume the event indicating that it was handled thus preventing other action
	 * listeners from handling/receiving the event.
	 *
	 * 
	 */
	public void consume()
	{
		this.isConsumed = true;
	}

	/**
	 * Returns true if the event was consumed thus indicating that it was handled.
	 * This prevents other action listeners from handling/receiving the event.
	 *
	 * 
	 * 
	 * @return true if event is consumed, false otherwise
	 */
	public boolean isConsumed()
	{
		return this.isConsumed;
	}

}
