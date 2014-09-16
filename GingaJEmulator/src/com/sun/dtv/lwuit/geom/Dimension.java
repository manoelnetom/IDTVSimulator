package com.sun.dtv.lwuit.geom;

public class Dimension extends java.lang.Object
{
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dimension other = (Dimension) obj;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	private int width;
	private int height;

	/**
	 * Creates a new instance of Dimension.
	 *
	 * 
	 */
	public Dimension()
	{
		width = 0;
		height = 0;
	}

	/**
	 * Creates a new instance of Dimension with parameters taken from an
	 * instance of <code>java.awt.Dimension</code>.
	 * This is a convenience constructor for improvement of the AWT integration.
	 *
	 * 
	 * @param d java.awt.Dimension instance to take data from.
	 */
	public Dimension(java.awt.Dimension d)
	{
		width = d.width;
		height = d.height;
	}

	/**
	 * Creates a new instance of Dimension with width and height.
	 *
	 * 
	 * @param width the dimension width.
	 * @param height the dimension height.
	 */
	public Dimension(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	/**
	 * Set the width of the dimension.
	 *
	 * 
	 * @param width the dimension width.
	 *
	 * @see getWidth()
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * Set the height of the dimension.
	 *
	 * 
	 * @param height the dimension height.
	 *
	 * @see getHeight()
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * Returns the width of the dimension.
	 *
	 * 
	 * @return width of the dimension.
	 *
	 * @see setWidth(int)
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Returns the height of the dimension.
	 * 
	 * 
	 * @return height of the dimension.
	 *
	 * @see setHeight(int)
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * Returns the printable form of this Dimension.
	 *
	 * 
	 * @return a String representation of this Dimension.
	 *
	 * @see toString()
	 */
	public String toString()
	{
		return getClass().getName() + "[width=" + width + ", height=" + height + "]";
	}

}

