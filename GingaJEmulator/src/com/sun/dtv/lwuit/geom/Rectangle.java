package com.sun.dtv.lwuit.geom;

public class Rectangle extends java.lang.Object
{
	private int x;
	private int y;
	private Dimension size;

	/**
	 * Creates a new instance of Rectangle.
	 *
	 * 
	 */
	public Rectangle()
	{
		x = 0;
		y = 0;
		this.size = new Dimension(0, 0);
	}

	/**
	 * Creates a new instance of Rectangle at position (x, y) and with
	 * predefine dimension.
	 *
	 * 
	 * @param x - the x coordinate of the rectangle.
	 * @param y - the y coordinate of the rectangle.
	 * @param size - the Dimension of the rectangle.
	 */
	public Rectangle(int x, int y, Dimension size)
	{
		this.x = x;
		this.y = y;
		this.size = new Dimension(size.getWidth(), size.getHeight());
	}

	/**
	 * Creates a new instance of Rectangle at position (x, y) and with
	 * predefined width and height.
	 *
	 * 
	 * @param x - the x coordinate of the rectangle.
	 * @param y - the y coordinate of the rectangle.
	 * @param w - the width of the rectangle.
	 * @param h - the height of the rectangle.
	 */
	public Rectangle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.size = new Dimension(w, h);
	}

	/**
	 * Creates a new instance of Rectangle with parameters taken from an
	 * instance of <code>java.awt.Rectangle</code>.
	 * This is a convenience constructor for improvement of the AWT integration.
	 *
	 * 
	 * @param r - java.awt.Rectnagle instance to take data from.
	 */
	public Rectangle(java.awt.Rectangle r)
	{
		java.awt.Point p = r.getLocation();
		java.awt.Dimension d = r.getSize();

		this.x = p.x;
		this.y = p.y;
		this.size = new Dimension(d.width, d.height);
	}

	/**
	 * Create a new instance of Rectangle at a position specified by the
	 * <A HREF="../../../../../com/sun/dtv/lwuit/geom/Point.html" title="class in com.sun.dtv.lwuit.geom"><CODE>Point</CODE></A> parameter, and with the predefined
	 * width and height.
	 *
	 * 
	 * @param p - the Point specifying the upper left corner of the rectangle
	 * @param w - the width of the rectangle.
	 * @param h - the height of the rectangle.
	 */
	public Rectangle(Point p, int w, int h)
	{
		this.x = p.getX();
		this.y = p.getY();
		this.size = new Dimension(w, h);
	}

	/**
	 * Return the dimension of the rectangle.
	 *
	 * 
	 * 
	 * @return the size of the rectangle.
	 */
	public Dimension getSize()
	{
		return size;
	}

	/**
	 * Return the x coordinate of the rectangle.
	 *
	 * 
	 * 
	 * @return the x coordinate of the rectangle.
	 * @see setX(int)
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Return the y coordinate of the rectangle.
	 *
	 * 
	 * 
	 * @return the y coordinate of the rectangle.
	 * @see setY(int)
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Sets the x position of the rectangle.
	 *
	 * 
	 * @param x - the x coordinate of the rectangle.
	 * @see getX()
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Sets the y position of the rectangle.
	 *
	 * 
	 * @param y - the y coordinate of the rectangle.
	 * @see getY()
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Checks whether or not this Rectangle entirely contains the specified
	 * Rectangle.
	 *
	 * 
	 * @param rect - the specified Rectangle.
	 *
	 * @return true if the Rectangle is contained entirely inside this Rectangle; false otherwise.
	 */
	public boolean contains(Rectangle rect)
	{
		Dimension d = rect.getSize();

		return contains(rect.getX(), rect.getY(), d.getWidth(), d.getHeight());
	}

	/**
	 * Checks whether this Rectangle entirely contains the Rectangle
	 * at the specified location (rX, rY) with the specified
	 * dimensions (rWidth, rHeight).
	 *
	 * 
	 * @param rX - the specified x coordinate.
	 * @param rY - the specified y coordinate.
	 * @param rWidth - the width of the Rectangle.
	 * @param rHeight - the height of the Rectangle.
	 * @return true if the Rectangle specified by (rX, rY, rWidth, rHeight) is entirely enclosed inside this Rectangle; false otherwise.
	 */
	public boolean contains(int rX, int rY, int rWidth, int rHeight)
	{
		int w = getSize().getWidth();
		int h = getSize().getHeight();
		
		if ((w | h | rWidth | rHeight) < 0) {
			return false;
		}
		
		int x = this.x;
		int y = this.y;
		
		if (rX < x || rY < y) {
			return false;
		}
		
		w += x;
		rWidth += rX;
		
		if (rWidth <= rX) {
			if (w >= x || rWidth > w) return false;
		} else {
			if (w >= x && rWidth > w) return false;
		}
		
		h += y;
		rHeight += rY;
		
		if (rHeight <= rY) {
			if (h >= y || rHeight > h) return false;
		} else {
			if (h >= y && rHeight > h) return false;
		}
		
		return true;
	}

	/**
	 * Checks whether or not this Rectangle contains the point at the specified
	 * location (rX, rY).
	 *
	 * 
	 * @param rX - the specified x coordinate.
	 * @param rY - the specified y coordinate.
	 * @return true if the point (rX, rY) is inside this Rectangle; false otherwise.
	 */
	public boolean contains(int rX, int rY)
	{
		int w = getSize().getWidth();
		int h = getSize().getHeight();

		if ((w | h) < 0) {
			return false;
		}
		
		int x = this.x;
		int y = this.y;
		
		if (rX < x || rY < y) {
			return false;
		}

		w += x;
		h += y;
		
		return ((w < x || w > rX) && (h < y || h > rY));
	}

	/**
	 * Determines whether or not this Rectangle and the specified Rectangle
	 * location (x, y) with the specified dimensions (width, height),
	 * intersect. Two rectangles intersect if their intersection is nonempty.
	 *
	 * 
	 * @param x - the specified x coordinate.
	 * @param y - the specified y coordinate.
	 * @param width - the width of the Rectangle.
	 * @param height - the height of the Rectangle.
	 * @return true if the specified Rectangle and this Rectangle intersect; false otherwise.
	 */
	public boolean intersects(int x, int y, int width, int height)
	{
		int tw = getSize().getWidth();
		int th = getSize().getHeight();
		int rw = width;
		int rh = height;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		int tx = this.x;
		int ty = this.y;
		int rx = x;
		int ry = y;
		
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	/**
	 * Determines whether or not this Rectangle and the specified Rectangle
	 * location (x, y) with the specified dimensions (width, height),
	 * intersect. Two rectangles intersect if their intersection is nonempty.
	 *
	 * 
	 * @param rect - the Rectangle to check intersection with
	 * @return true if the specified Rectangle and this Rectangle intersect; false otherwise.
	 */
	public boolean intersects( Rectangle rect)
	{
		Dimension d = rect.getSize();

		return intersects(rect.getX(), rect.getY(), d.getWidth(), d.getHeight());
	}

	/**
	 * Helper method allowing us to determine if two coordinate sets intersect.
	 * This saves us the need of creating a rectangle object for a quick
	 * calculation.
	 *
	 * 
	 * @param tx - x coordinate of upper left corner of rectangle 1
	 * @param ty - y coordinate of upper left corner of rectangle 1
	 * @param tw - width of rectangle 1
	 * @param th - height of rectangle 1
	 * @param x - x coordinate of upper left corner of rectangle 2
	 * @param y - y coordinate of upper left corner of rectangle 2
	 * @param width - width of rectangle 2
	 * @param height - height of rectangle 2
	 * @return true if rectangles intersect
	 */
	public static boolean intersects(int tx, int ty, int tw, int th, int x, int y, int width, int height)
	{
		int rw = width;
		int rh = height;
		
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		
		int rx = x;
		int ry = y;
		
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	/**
	 * Class name.
	 *
	 * 
	 * @see toString in class Object
	 */
	public String toString()
	{
		return getClass().getName() + "[x=" + x + ", y=" + y + ", width=" + getSize().getWidth() + ", height=" + getSize().getHeight() + "]";
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rectangle other = (Rectangle) obj;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
