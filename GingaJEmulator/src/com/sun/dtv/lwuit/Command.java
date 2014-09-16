package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;

public class Command extends Object implements ActionListener
{
	private String command;
	private Image icon;
	private int id;

	/**
	 * Creates a new instance of Command.
	 *
	 * 
	 * @param command - the string that will be placed on the Soft buttons\Menu
	 */
	public Command(String command)
	{
		this.command = command;
	}

	/**
	 * Creates a new instance of Command.
	 *
	 * 
	 * @param command - the string that will be placed on the Soft buttons\Menu
	 * @param icon - the icon representing the command
	 */
	public Command(String command, Image icon)
	{
		this.command = command;
		this.icon = icon;
	}

	/**
	 * Creates a new instance of Command.
	 *
	 * 
	 * @param command - the string that will be placed on the Soft buttons\Menu
	 * @param id - user defined ID for a command simplifying switch statement code working with a command
	 */
	public Command(String command, int id)
	{
		this.command = command;
		this.id = id;
	}

	/**
	 * Creates a new instance of Command.
	 *
	 * 
	 * @param command - the string that will be placed on the Soft buttons\Menu
	 * @param icon - the icon representing the command
	 * @param id - user defined ID for a command simplifying switch statement code working with a command
	 */
	public Command(String command, Image icon, int id)
	{
		this.command = command;
		this.icon = icon;
		this.id = id;
	}

	/**
	 * Return the command ID.
	 *
	 * 
	 * @return the command ID
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Gets the Command Name.
	 * 
	 * 
	 * @return the Command name
	 */
	public String getCommandName()
	{
		return this.command;
	}

	/**
	 * Returns the icon representing the command.
	 *
	 * 
	 * @return an icon representing the command
	 */
	public Image getIcon()
	{
		return this.icon;
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * 
	 * @return Returns a string representation of the object
	 * @see toString in class Object
	 */
	public String toString()
	{
		return command + ":" + icon + ":" + id;
	}

	/**
	 * Compare two commands.
	 *
	 * 
	 * @param obj - a Command Object to compare
	 * @return true if the obj has the same command name
	 * @see equals in class Object
	 */
	public boolean equals(Object obj)
	{
		Command o = (Command)obj;

		return command.equals(o.getCommandName()) == true;
	}

	/**
	 * @see hashCode in class Object
	 *
	 */
	public int hashCode()
	{
		return command.hashCode();
	}

	/**
	 * This method is called when the soft button/Menu item is clicked.
	 *
	 * 
	 * @param evt - the Event Object
	 * @see actionPerformed in interface ActionListener
	 */
	public void actionPerformed(ActionEvent evt) {
		System.out.println("com.sun.dtv.lwuit.Command.actionPerformed(): method not implemented");
	}

}
