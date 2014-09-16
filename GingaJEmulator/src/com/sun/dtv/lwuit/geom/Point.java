package com.sun.dtv.lwuit.geom;

public class Point 
{
	private int x;
	private int y;

	/**
	 * Constructs and initializes a point at the origin (0, 0) of the
	 * coordinate space.
	 *
	 */
	public Point()
	{
		x = 0;
		y = 0;
	}

	/**
	 * Constructs and initializes a point at the specified (x, y) location in
	 * the coordinate space.
	 *
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs and initializes a point with the same location as the
	 * specified <code>java.awt.Point</code> object.
	 *
	 * 
	 * @param p the specified Point object
	 */
	public Point(java.awt.Point p)
	{
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Returns the x coordinate of this Point.
	 *
	 * 
	 * @return the x coordinate of this Point
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Returns the y coordinate of this Point.
	 *
	 * 
	 * @return the y coordinate of this Point
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Creates a new object of the same class and with the same contents as
	 * this object.
	 *
	 * 
	 * @return the new Point
	 */
	public Object clone()
	{
		return new Point(this.x, this.y);
	}

	/**
	 * Determines whether or not two points are equal.
	 *
	 * 
	 * @param obj - the Point to be compared to
	 *
	 * @return true if points are equal, false otherwise
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof Point) {
			Point p = (Point)obj;

			return (this.x == p.getX()) && (this.y == p.getY());
		}

		return super.equals(obj);
	}

	/**
	 * Returns the location of this point.
	 *
	 * 
	 * @return the location
	 *
	 * @see setLocation(int, int)
	 */
	public Point getLocation()
	{
		return new Point(this.x, this.y);
	}

	/**
	 * Returns the hashcode for this Point.
	 *
	 * 
	 * @return the hashcode
	 */
	public int hashCode()
	{
		int sum = x + y;

		return sum*(sum+1)/2+x;
	}

	/**
	 * Moves this point to the specified location in the (x, y) coordinate plane.
	 *
	 * 
	 * @param x x coordinate of the specified location
	 * @param y y coordinate of the specified location
	 */
	public void move(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Changes the point to have the specified location.
	 *
	 * 
	 * @param x - x coordinate of the specified location
	 * @param y - y coordinate of the specified location
	 *
	 * @see getLocation()
	 */
	public void setLocation(int x, int y)
	{
		move(x, y);
	}

	/**
	 * Sets the location of the point to the specified location.
	 *
	 * 
	 * @param p - the Point of the specified location
	 *
	 * @see getLocation()
	 */
	public void setLocation(Point p)
	{
		setLocation(p.getX(), p.getY());
	}

	/**
	 * Returns a string representation of this point and its location in the
	 * (x, y) coordinate space.
	 *
	 * 
	 * @return the String representation
	 * @see toString in class Object
	 */
	public String toString()
	{
		return getClass().getName() + "[x=" + x + ", y=" + y + "]";
	}

	/**
	 * Translates this point, at location (x, y), by dx along the x axis and dy
	 * along the y axis so that it now represents the point (x + dx, y + dy).
	 *
	 * 
	 * @param dx - translation value in x direction
	 * @param dy - translation value in y direction
	 */
	public void translate(int dx, int dy)
	{
		this.x += dx;
		this.y += dy;
	}
}
