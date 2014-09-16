package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.animations.Transition;

import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.layouts.FlowLayout;
import com.sun.dtv.lwuit.layouts.Layout;

import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.plaf.LookAndFeel;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;

import com.sun.dtv.ui.Matte;
import com.sun.dtv.ui.MatteEnabled;
import com.sun.dtv.ui.MatteException;
import com.sun.dtv.ui.event.RemoteControlEvent;

import java.util.Enumeration;
import java.util.Vector;

public class Container extends Component implements MatteEnabled
{
	protected Layout layout;
	protected java.util.Vector components = new java.util.Vector();
	
	private Matte matte;

	private boolean shouldLayout = true;
	private boolean scrollableX;
	private boolean scrollableY;
	private int scrollIncrement = 20;

	private java.util.Vector cmpTransitions;

	/**
	 * Constructs a new Container with a new layout manager.
	 *
	 * 
	 * @param layout - the specified layout manager
	 */
	public Container( Layout layout)
	{
		super();
		setUIID("Container");
		this.layout = layout;
		LookAndFeel laf = UIManager.getInstance().getLookAndFeel();
		this.visible = true;//false;
		setFocusable(false);
		setSmoothScrolling(laf.isDefaultSmoothScrolling());
	}

	/**
	 * Constructs a new Container, with a <A HREF="../../../../com/sun/dtv/lwuit/layouts/FlowLayout.html" title="class in com.sun.dtv.lwuit.layouts"><CODE>FlowLayout</CODE></A>.
	 *
	 * 
	 */
	public Container()
	{
		this(new FlowLayout());
	}
	
	/**
	 * Returns the layout manager responsible for arranging this container.
	 *
	 * 
	 * @return the container layout manager
	 * @see setLayout(com.sun.dtv.lwuit.layouts.Layout)
	 */
	public Layout getLayout()
	{
		return this.layout;
	}

	/**
	 * Sets the layout manager responsible for arranging this container.
	 *
	 * 
	 * @param layout - the specified layout manager
	 * @see getLayout()
	 */
	public void setLayout( Layout layout)
	{
		this.layout = layout;
	}

	/**
	 * Same as setShouldCalcPreferredSize(true) but made accessible for
	 * layout managers.
	 *
	 * 
	 */
	public void invalidate()
	{
		setShouldCalcPreferredSize(true);
	}

	/**
	 * @inheritDoc
	 * 
	 */
	protected void setShouldCalcPreferredSize(boolean shouldCalcPreferredSize) {
		super.setShouldCalcPreferredSize(shouldCalcPreferredSize);
		shouldLayout = shouldCalcPreferredSize;
		Enumeration enums = components.elements();
		if (shouldLayout) {
			while (enums.hasMoreElements()) {
				Component cmp = (Component) enums.nextElement();
				if (cmp instanceof Container) {
					((Container) cmp).setShouldCalcPreferredSize(shouldCalcPreferredSize);
				}
			}
		}
		Form f = getComponentForm();
		if (f != null) {
			f.clearFocusVectors();
		}
	}

	/**
	 * Returns the width for layout manager purposes, this takes scrolling
	 * into consideration unlike the getWidth method.
	 * 
	 * 
	 * @return the layout width
	 */
	public int getLayoutWidth()
	{
		Dimension d = getPreferredSize();

		if (isScrollableX()) {
			int scrollH = UIManager.getInstance().getLookAndFeel().getHorizontalScrollHeight();
			return Math.max(getWidth() + scrollH, d.getWidth() + scrollH);
		} else {
			Container parent = getScrollableParent();
			if (parent != null && parent.isScrollableX()) {
				return Math.max(getWidth(), d.getWidth());
			}
			int width = getWidth();
			if (width <= 0) {
				return d.getWidth();
			}
			return width;
		}
	}

	/**
	 * Returns a parent container that is scrollable or null if no parent is 
	 * scrollable.
	 * 
	 * @return a parent container that is scrollable or null if no parent is 
	 * scrollable.
	 */
	private Container getScrollableParent() {
		Container parent = getParent();
		while (parent != null) {
			if (parent.isScrollable()) {
				return parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	/**
	 * Returns the height for layout manager purposes, this takes scrolling
	 * into consideration unlike the getWidth method.
	 *
	 * 
	 * @return the layout height
	 */
	public int getLayoutHeight()
	{
		Dimension d = getPreferredSize();

		if (isScrollableY()) {
			int scrollW = UIManager.getInstance().getLookAndFeel().getVerticalScrollWidth();
			return Math.max(getHeight() + scrollW, d.getHeight() + scrollW);
		} else {
			Container parent = getScrollableParent();
			if (parent != null && parent.isScrollableY()) {
				return Math.max(getHeight(), d.getHeight());
			}
			int height = getHeight();
			if (height <= 1) {
				return d.getHeight();
			}
			return height;
		}
	}

	/**
	 * Adds a Component to the Container.
	 *
	 * 
	 * @param cmp - the component to be added
	 */
	public void addComponent(Component cmp)
	{
		try {
			// helper check for a common mistake...
			if (layout instanceof BorderLayout) {
				throw new IllegalArgumentException("Cannot add component to BorderLayout Container without constraint parameter");
			}

			insertComponentAt(components.size(), cmp);
			
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e);
		}
	}

	/**
	 * Adds a Component to the Container.
	 *
	 * 
	 * @param constraints - this method is useful when the Layout requires a constraint such as the BorderLayout. 
	 * In this case you need to specify an additional data when you add a Component, such as "CENTER", "NORTH"...
	 * @param cmp - component to add
	 */
	public void addComponent(Object constraints, Component cmp)
	{
		try {
			insertComponentAt(components.size(), cmp);
			layout.addLayoutComponent(constraints, cmp, this);
			
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e);
		}
	}

	protected void insertComponentAt(int index, Component cmp) {
		cmp.setParent(this);
		components.insertElementAt(cmp, index);
		setShouldCalcPreferredSize(true);
		if (isInitialized()) {
			cmp.initComponentImpl();
		}
		Form f = getComponentForm();
		if (f != null) {
			f.clearFocusVectors();
		}
	}

	protected void insertFormAt(int index, Component cmp) {
		cmp.setParent(this);
		components.insertElementAt(cmp, index);
		setShouldCalcPreferredSize(true);
		if (isInitialized()) {
			cmp.initComponentImpl();
		}
		Form f = getComponentForm();
		if (f != null) {
			f.clearFocusVectors();
		}
	}

	/**
	 * This method adds the Component at a specific index location in the Container
	 * Components array.
	 *
	 * 
	 * @param index - location to insert the Component
	 * @param cmp - the Component to add
	 * @throws ArrayIndexOutOfBoundsException - if index is out of bounds
	 * @throws IllegalArgumentException - if Component is already contained or the cmp is a Form Component
	 */
	public void addComponent(int index, Component cmp)
	{
		if (cmp.getParent() != null) {
			throw new IllegalArgumentException("Component is already contained in Container: " + cmp.getParent());
		}
		if (cmp instanceof Form) {
			throw new IllegalArgumentException("A form cannot be added to a container");
		}

		insertComponentAt(index, cmp);
	}

	private boolean isParentOf(Component c) {
		c = c.getParent();
		
		if (c == null || c instanceof Form) {
			return false;
		}

		return (c == this) || isParentOf(c);
	}

	/**
	 * This method replaces the current Component with the next Component.
	 * Current Component must be contained in this Container.
	 * This method return immediately.
	 *
	 * 
	 * @param current - a Component to remove from the Container
	 * @param next - a Component that replaces the current Component
	 * @param t - a Transition between the add and removal of the Components a Transition can be null
	 */
	public void replace( Component current, Component next, Transition t)
	{
		replaceComponents(current, next, t, false);
	}

	void replace(final Component current, final Component next) {
		int index = components.indexOf(current);
		boolean currentFocused = false;
		if (current.getComponentForm() != null) {
			Component currentF = current.getComponentForm().getFocused();
			currentFocused = currentF == current;
			if (!currentFocused && current instanceof Container && currentF != null && ((Container) current).isParentOf(currentF)) {
				currentFocused = true;
			}
		}
		if (layout instanceof BorderLayout) {
			Object constraint = layout.getComponentConstraint(current);
			removeComponent(current);
			layout.addLayoutComponent(constraint, next, Container.this);
		} else {
			removeComponent(current);
		}
		next.setParent(null);
		if (index < 0) {
			index = 0;
		}
		insertComponentAt(index, next);
		if (currentFocused) {
			if (next.isFocusable()) {
				next.requestFocus();
			} else {
				if (next instanceof Container) {
					((Container) next).requestFocusChild();
				}
			}
		}
	}

	private void replaceComponents(final Component current, final Component next, final Transition t, boolean wait) {
		if (!contains(current)) {
			throw new IllegalArgumentException("Component " + current + " is not contained in this Container");
		}
		if (t == null || !isVisible() || getComponentForm() == null) {
			replace(current, next);
			return;
		}

		next.setX(current.getX());
		next.setY(current.getY());
		next.setWidth(current.getWidth());
		next.setHeight(current.getHeight());
		next.setParent(this);
		if (next instanceof Container) {
			((Container) next).layoutContainer();
		}

		final Anim anim = new Anim(this, current, next, t);

		// Causes a problem with replace being called on a form which is not visible
		// or in the process of switching... Animation will never be called and won't
		// remove the block
		// block events as long as the transition is animating
		Display.getInstance().blockEvents(true);

		// register the transition animation
		getComponentForm().registerAnimated((com.sun.dtv.ui.Animated)anim);
		//wait until animation has finished
		if (wait) {
			Display.getInstance().invokeAndBlock(new Runnable() {

				public void run() {
					while (!anim.isFinished()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
				}
			});
		}
	}

	/**
	 * removes a Component from the Container.
	 *
	 * 
	 * @param cmp - the removed component
	 */
	public void removeComponent( Component cmp)
	{
		if(cmp == null){
			return;
		}
		Form parentForm = cmp.getComponentForm();

		if (layout != null) {
			layout.removeLayoutComponent(cmp);
		}

		cmp.deinitializeImpl();
		components.removeElement(cmp);
		cmp.setParent(null);
		cmp.setShouldCalcPreferredSize(true);
		if (parentForm != null) {
			if (parentForm.getFocused() == cmp || cmp instanceof Container && ((Container) cmp).contains(parentForm.getFocused())) {
				parentForm.setFocused(null);
			}
			parentForm.clearFocusVectors();
			if (cmp.isSmoothScrolling()) {
				parentForm.deregisterAnimated(cmp);
			}
		}
		setShouldCalcPreferredSize(true);
	}

	/**
	 * remove all Components from container.
	 *
	 * 
	 */
	public void removeAll()
	{
		Form parentForm = getComponentForm();
		if (parentForm != null) {
			Component focus = parentForm.getFocused();
			if (focus != null && contains(focus)) {
				parentForm.setFocused(null);
			}
		}
		Object[] arr = new Object[components.size()];
		components.copyInto(arr);

		for (int i = 0; i < arr.length; i++) {
			removeComponent((Component) arr[i]);
		}
	}

	/**
	 * Re-layout the container, this is useful when we modify the container hierarchy and
	 * need to redo the layout.
	 *
	 * 
	 */
	public void revalidate()
	{
		setShouldCalcPreferredSize(true);
		Form root = getComponentForm();
		if (root != null) {
			root.layoutContainer();
			root.repaint();
		} else {
			layoutContainer();
			repaint();
		}
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
	 * @see paint in class Component
	 */
	public void paint(Graphics g)
	{
		layoutContainer();
		
		g.clipRect(getX(), getY(), getWidth(), getHeight());
		g.translate(getX(), getY());

		Enumeration enums = components.elements();
				
		int oX = g.getClipX();
		int oY = g.getClipY();
		int oWidth = g.getClipWidth();
		int oHeight = g.getClipHeight();

		int scrollX = 0;
		int scrollY = 0;
		
		if (isScrollableX()) {
			scrollX = getScrollX();
		}

		if (isScrollableY()) {
			scrollY = getScrollY();
		}
			
		g.translate(-scrollX, -scrollY);

		while (enums.hasMoreElements()) {
			Component cmp = (Component) enums.nextElement();

			if (cmp.isVisible() == true) {
				g.setClip(cmp.getX(), cmp.getY(), cmp.getWidth()+scrollX, cmp.getHeight()+scrollY);
				cmp.paint(g);
				// g.setClip(oX, oY, oWidth, oHeight);
			}
		}
					
		g.translate(scrollX, scrollY);
		g.setClip(0, 0, oWidth, oHeight);

		if (isScrollVisible()) {
			paintScrollbars(g);
		}

		g.translate(-getX(), -getY());
		
		if (isBorderPainted()) {
			paintBorder(g);
		}
	}

	/**
	 * Performs the layout of the container if a layout is necessary.
	 *
	 * 
	 */
	public void layoutContainer()
	{
		if (shouldLayout) {
			shouldLayout = false;
			doLayout();
		}
	}

	/**
	 * Lays out the container.
	 *
	 * 
	 */
	public void doLayout()
	{
		if (layout == null) {
			return;
		}

		layout.layoutContainer(this);
		int count = getComponentCount();
		for (int i = 0; i < count; i++) {
			Component c = getComponentAt(i);
			if (c instanceof Container) {
				((Container) c).doLayout();
			}
		}
	}

	/**
	 * Returns the number of components.
	 *
	 * 
	 * 
	 * @return the Component count
	 */
	public int getComponentCount()
	{
		return components.size();
	}

	/**
	 * Returns the Component at a given index.
	 *
	 * 
	 * @param index - of the Component you wish to get
	 * @return a Component
	 * @throws ArrayIndexOutOfBoundsException - if an invalid index was given.
	 */
	public Component getComponentAt(int index)
	{
		return (Component) components.elementAt(index);
	}

	/**
	 * Returns the Component index in the Container.
	 *
	 * 
	 * @param cmp - the component to search for
	 * @return the Component index in the Container or -1 if not found
	 */
	public int getComponentIndex( Component cmp)
	{
		int count = getComponentCount();
		for (int i = 0; i <
				count; i++) {
			Component c = getComponentAt(i);
			if (c.equals(cmp)) {
				return i;
			}
				}
		return -1;
	}

	/**
     * Determines the scroll increment size of this Container.
     * This value is in use when the current foucs element within this Container
     * is larger than this Container size.
     *
     * @param scrollIncrement the size in pixels.
     */
    public void setScrollIncrement(int scrollIncrement) {
        this.scrollIncrement = scrollIncrement;
    }

    /**
     * Gets the Container scroll increment
     *
     * @return the scroll increment in pixels.
     */
    public int getScrollIncrement() {
        return scrollIncrement;
    }

	/**
     * This method scrolls the Container if Scrollable towards the given 
     * Component based on the given direction.
     * 
     * @param direction is the direction of the navigation (Display.GAME_UP, 
     * Display.GAME_DOWN, ...) 
     * @param next the Component to move the scroll towards.
     * 
     * @return true if next Component is now visible.
     */    
		boolean moveScrollTowards(int direction, Component next) {
        if (isScrollable()) {
            Component current = null;
            Form f = getComponentForm();
            if (f != null) {
                current = f.getFocused();
            }

            int position = f.getFocusPosition(current);
            boolean edge = false;
            boolean currentLarge = false;
            boolean scrollOutOfBounds = false;
            
            int x = getScrollX();
            int y = getScrollY();
            int w = getWidth();
            int h = getHeight();

            switch (direction) {
                case RemoteControlEvent.VK_UP:
                    y = getScrollY() - scrollIncrement;
                    edge = (position == 0);
                    currentLarge = (current != null && current.getVisibleBounds().getSize().getHeight() > getHeight());
                    scrollOutOfBounds = y < 0;
                    if(scrollOutOfBounds){
                        y = 0;
                    }
                    break;
                case RemoteControlEvent.VK_DOWN:
                    y = getScrollY() + scrollIncrement;
                    edge = (position == f.getFocusCount() - 1);
                    currentLarge = (current != null && current.getVisibleBounds().getSize().getHeight() > getHeight());
                    scrollOutOfBounds = y > getScrollDimension().getHeight() - getHeight();
                    if(scrollOutOfBounds){
                        y = getScrollDimension().getHeight() - getHeight();
                    }
                    break;
                case RemoteControlEvent.VK_RIGHT:
                    x = getScrollX() + scrollIncrement;
                    edge = (position == f.getFocusCount() - 1);
                    currentLarge = (current != null && current.getVisibleBounds().getSize().getWidth() > getWidth());
                    scrollOutOfBounds = x > getScrollDimension().getWidth() - getWidth();
                    if(scrollOutOfBounds){
                        x = getScrollDimension().getWidth() - getWidth();
                    }
                    break;
                case RemoteControlEvent.VK_LEFT:
                    x = getScrollX() - scrollIncrement;
                    edge = (position == 0);
                    currentLarge = (current != null && current.getVisibleBounds().getSize().getWidth() > getWidth());
                    scrollOutOfBounds = x < 0;
                    if(scrollOutOfBounds){
                        x = 0;
                    }
                    break;
            }

            //if the Form doesn't contain a focusable Component simply move the 
            //viewport by pixels
            if(next == null || next == this){
                scrollRectToVisible(new Rectangle(x, y, w, h), this);
                return false;
            }
            
            boolean nextIntersects = contains(next) && Rectangle.intersects(next.getAbsoluteX(),
                    next.getAbsoluteY(),
                    next.getWidth(),
                    next.getHeight(),
                    getAbsoluteX() + x,
                    getAbsoluteY() + y,
                    w,
                    h);
            
	    Rectangle rect = new Rectangle(getAbsoluteX() + getScrollX(), getAbsoluteY() + getScrollY(), w, h);
	    Rectangle nextRect = new Rectangle(next.getAbsoluteX(),
		    next.getAbsoluteY(), next.getWidth(), next.getHeight());

            if ((nextIntersects && !currentLarge && !edge) || (rect.contains(nextRect))) {
                scrollComponentToVisible(next);
                return true;
            } else {
                if (!scrollOutOfBounds) {
                    scrollRectToVisible(new Rectangle(x, y, w, h), this);
                    //if after moving the scroll the current focus is out of the
                    //view port and the next focus is in the view port move 
                    //the focus
                    if (nextIntersects && !Rectangle.intersects(current.getAbsoluteX(),
                            current.getAbsoluteY(),
                            current.getWidth(),
                            current.getHeight(),
                            getAbsoluteX() + x,
                            getAbsoluteY() + y,
                            w,
                            h)) {
                        return true;
                    }
                    return false;
                } else {
                    scrollComponentToVisible(next);
                    return true;
                }
            }

        }

        return true;
    }

    /**
     * Returns a Component that exists in the given x, y coordinates by traversing
     * component objects and invoking contains
     * 
     * @param x absolute screen location
     * @param y absolute screen location
     * @return a Component if found, null otherwise
     * @see Component#contains
     */
    Component getComponentAt(int x, int y) {
        int count = getComponentCount();
        for (int i = count - 1; i >= 0; i--) {
            Component cmp = getComponentAt(i);
            if (cmp.contains(x, y)) {
                if (cmp instanceof Container) {
                    return ((Container) cmp).getComponentAt(x, y);
                }
                return cmp;
            }

        }
        if (contains(x, y)) {
            return this;
        }
        return null;
    }
	/**
	 * Returns true if the given component is within the hierarchy of this container.
	 *
	 * 
	 * @param cmp - a Component to check
	 * @return true if this Component contains in this Container
	 */
	public boolean contains( Component cmp)
	{
		boolean found = false;
		int count = getComponentCount();
		for (int i = 0; i < count; i++) {
			Component c = getComponentAt(i);
			if (c.equals(cmp)) {
				return true;
			}

			if (c instanceof Container) {
				found = ((Container) c).contains(cmp);
				if (found) {
					return true;
				}

			}
		}
		return false;
	}

    /**
     * Makes sure the component is visible in the scroll if this container is 
     * scrollable
     * 
     * @param c the component that will be scrolling for visibility
     */
    protected void scrollComponentToVisible(Component c) {
        if (isScrollable()) {
            if (c != null) {
                if (c.getParent() != null) {
                    // special case for the first component to allow the user to scroll all the 
                    // way to the top
                    Form f = getComponentForm();
                    if (f != null && f.getFocusPosition(c) == 0) {
                        scrollRectToVisible(new Rectangle(0, 0, c.getX() + c.getWidth(), c.getY() + c.getHeight()), c);
                        return;
                    }
                }
                scrollRectToVisible(c.getBounds(), c);
            }
        }

    }

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerPressed(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer pressed event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerPressed in class Component
	 */
	public void pointerPressed(int x, int y)
	{
		if (!isDragActivated()) {
			Component cmp = getComponentAt(x, y);
			if (cmp == this) {
				super.pointerPressed(x, y);
			} else if (cmp != null) {
				cmp.pointerPressed(x, y);
			}
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#refreshTheme()">Component</A></CODE></B></DD>
	 * Makes sure the component is up to date with the current style object.
	 *
	 * 
	 * @see refreshTheme in class Component
	 */
	public void refreshTheme()
	{
		super.refreshTheme();
		Enumeration enums = components.elements();
		while (enums.hasMoreElements()) {
			Component cmp = (Component) enums.nextElement();
			cmp.refreshTheme();
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#isScrollableX()">Component</A></CODE></B></DD>
	 * Indicates whether the component should/could scroll on the X axis.
	 *
	 * 
	 * @return whether the component is scrollable on the X axis
	 * @see isScrollableX in class Component
	 */
	public boolean isScrollableX()
	{
		return scrollableX && getScrollDimension().getWidth() > getWidth();
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#isScrollableY()">Component</A></CODE></B></DD>
	 * Indicates whether the component should/could scroll on the Y axis.
	 *
	 * 
	 * @return whether the component is scrollable on the X axis
	 * @see isScrollableY in class Component
	 */
	public boolean isScrollableY()
	{
		return scrollableY && getScrollDimension().getHeight() > getHeight();
	}

	/**
	 * Sets whether the component should/could scroll on the X axis.
	 *
	 * 
	 * @param scrollableX - whether the component should/could scroll on the X axis
	 */
	public void setScrollableX(boolean scrollableX)
	{
		this.scrollableX = scrollableX;
	}

	/**
	 * Sets whether the component should/could scroll on the Y axis.
	 *
	 * 
	 * @param scrollableY - whether the component should/could scroll on the Y axis
	 */
	public void setScrollableY(boolean scrollableY)
	{
		this.scrollableY = scrollableY;
	}

	/**
	 * The equivalent of calling both setScrollableY and setScrollableX.
	 *
	 * 
	 * @param scrollable - whether the component should/could scroll on the  X and Y axis
	 */
	public void setScrollable(boolean scrollable)
	{
		setScrollableX(scrollable);
		setScrollableY(scrollable);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#setCellRenderer(boolean)">Component</A></CODE></B></DD>
	 * Used as an optimization to mark that this component is currently being
	 * used as a cell renderer.
	 *
	 * 
	 * @param cellRenderer - indicate whether this component is currently being used as a cell renderer
	 * @see setCellRenderer in class Component
	 */
	public void setCellRenderer(boolean cellRenderer)
	{
		if (isCellRenderer() != cellRenderer) {
			super.setCellRenderer(cellRenderer);
			int size = getComponentCount();
			for (int iter = 0; iter <
					size; iter++) {
				getComponentAt(iter).setCellRenderer(cellRenderer);
					}
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#setMatte(com.sun.dtv.ui.Matte)">MatteEnabled</A></CODE></B></DD>
	 * Adds an <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> to the component implementing this
	 * interface in order to enable matte compositing. If there is already a
	 * Matte assigned to the component, and this Matte is animated, it has to be
	 * stopped before any call to this method.
	 *
	 * 
	 * @param matte - the Matte to be assigned to the component. At any point of time there can only be one matte associated to the component, that's way any matte that has been associated before will be overriden by a call to this method. The Matte parameter can also be null, in this case there is no matte associated with the component after the call, even if there had been one before.
	 * @throws MatteException - if the Matte has an unsupported type, the platform does not support mattes at all, or an animated matte is associated with the component and is still running
	 * @see setMatte in interface MatteEnabled
	 * @see MatteEnabled.getMatte()
	 */
	public void setMatte( Matte matte) throws MatteException
	{
		this.matte = matte;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#getMatte()">MatteEnabled</A></CODE></B></DD>
	 * Return the <A HREF="../../../../com/sun/dtv/ui/Matte.html" title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> currently associated with the
	 * component implementing this interface.
	 *
	 * 
	 * @return the Matte currently associated with the component or null if there is none
	 * @see getMatte in interface MatteEnabled
	 * @see MatteEnabled.setMatte(com.sun.dtv.ui.Matte)
	 */
	public Matte getMatte()
	{
		return this.matte;
	}

	private boolean requestFocusChild() {
		for (int iter = 0; iter < getComponentCount(); iter++) {
			Component c = getComponentAt(iter);
			if (c.isFocusable()) {
				c.requestFocus();
				return true;
			}
			if (c instanceof Container && ((Container) c).requestFocusChild()) {
				return true;
			}
		}
		return false;
	}
	
	protected Dimension calcPreferredSize() {
		if (layout == null) {
			return new Dimension(getWidth(), getHeight());
		}

		Dimension d = layout.getPreferredSize(this);
		Style style = getStyle();
		
		if(style.getBorder() != null && d.getWidth() != 0 && d.getHeight() != 0) {
			d.setWidth(d.getWidth());
			d.setHeight(d.getHeight());
		}
	
		return d;
	}

	class Anim implements Animation {

		private Transition t;
		private Component current;
		private Component next;
		private boolean started = false;
		private Container thisContainer;
		private boolean finished = false;
		private Form parent;

		public Anim(Container thisContainer, Component current, Component next, Transition t) {
			this.t = t;
			this.next = next;
			this.current = current;
			this.thisContainer = thisContainer;
			this.parent = thisContainer.getComponentForm();
		}

		public boolean animate() {
			if (!started) {
				t.init(current, next);
				t.initTransition();
				started = true;
				if (cmpTransitions == null) {
					cmpTransitions = new Vector();
				}
				cmpTransitions.addElement(this);
			}
			boolean notFinished = t.animate();
			if (!notFinished) {
				destroy();
				cmpTransitions.removeElement(this);
			}
			return notFinished;
		}

		public void destroy() {
			parent.deregisterAnimated(this);
			next.setParent(null);
			thisContainer.replace(current, next);
			//release the events blocking
			Display.getInstance().blockEvents(false);
			t.cleanup();
			parent.revalidate();
			finished = true;
		}

		public void paint(Graphics g) {
			t.paint(g);
		}

		public boolean isFinished() {
			return finished;
		}
	}
}
