package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.geom.Rectangle;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.animations.Motion;
import com.sun.dtv.lwuit.events.FocusListener;
import com.sun.dtv.lwuit.events.StyleListener;
import com.sun.dtv.lwuit.plaf.Border;
import com.sun.dtv.lwuit.plaf.LookAndFeel;
import com.sun.dtv.lwuit.plaf.UIManager;

import com.sun.dtv.ui.Animated;

import java.awt.Color;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Component implements Animation, Animated, StyleListener {

    /**
     * Baseline resize behavior constant used to properly align components.
     * Indicates as the size of the component changes the baseline remains a
     * fixed distance from the top of the component.
     * 
     * 
     * <A HREF=
     * "../../../../constant-values.html#com.sun.dtv.lwuit.Component.BRB_CONSTANT_ASCENT"
     * >Constant Field Values</A></DL>
     * 
     */
    public static final int BRB_CONSTANT_ASCENT = 1;
    /**
     * Baseline resize behavior constant used to properly align components.
     * Indicates as the size of the component changes the baseline remains a
     * fixed distance from the bottom of the component.
     * 
     * 
     * <A HREF=
     * "../../../../constant-values.html#com.sun.dtv.lwuit.Component.BRB_CONSTANT_DESCENT"
     * >Constant Field Values</A></DL>
     * 
     */
    public static final int BRB_CONSTANT_DESCENT = 2;
    /**
     * Baseline resize behavior constant used to properly align components.
     * Indicates as the size of the component changes the baseline remains a
     * fixed distance from the center of the component.
     * 
     * 
     * <A HREF=
     * "../../../../constant-values.html#com.sun.dtv.lwuit.Component.BRB_CENTER_OFFSET"
     * >Constant Field Values</A></DL>
     * 
     */
    public static final int BRB_CENTER_OFFSET = 3;
    /**
     * Baseline resize behavior constant used to properly align components.
     * Indicates as the size of the component changes the baseline can not be
     * determined using one of the other constants.
     * 
     * 
     * <A HREF=
     * "../../../../constant-values.html#com.sun.dtv.lwuit.Component.BRB_OTHER"
     * >Constant Field Values</A></DL>
     * 
     */
    public static final int BRB_OTHER = 4;
    /**
     * Indicates a Component center alignment.
     * 
     */
    public static final int CENTER = 4;
    /**
     * Box-orientation constant used to specify the top of a box.
     * 
     */
    public static final int TOP = 0;
    /**
     * Box-orientation constant used to specify the left side of a box.
     * 
     */
    public static final int LEFT = 1;
    /**
     * Box-orientation constant used to specify the bottom of a box.
     * 
     */
    public static final int BOTTOM = 2;
    /**
     * Box-orientation constant used to specify the right side of a box.
     * 
     */
    public static final int RIGHT = 3;
    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the down key
     */
    private Component nextFocusDown;
    private Component nextFocusUp;
    /**
     * Indicates whether component is enabled or disabled
     * 
     */
    private boolean enabled = true;
    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the right key
     */
    private Component nextFocusRight;
    private Component nextFocusLeft;
    protected boolean visible = true;
    /**
     * Used as an optimization to mark that this component is currently being
     * used as a cell renderer
     */
    private boolean cellRenderer;
    /**
     * Indicates that this component is fixed into place and not affected by
     * scrolling of the parent container. This is applicable for components such
     * as menus etc...
     */
    private boolean fixedPosition;
    private Rectangle bounds = new Rectangle(0, 0, new Dimension(0, 0));
    private List focusListeners = new ArrayList();
    private boolean sizeRequestedByUser = false;
    private Dimension preferredSize;
    private boolean scrollSizeRequestedByUser = false;
    private Dimension scrollSize;
    private Style style;
    private Container parent;
    private boolean focused = false;
    private boolean focusPainted = true;
    private boolean handlesInput = false;
    private boolean shouldCalcPreferredSize = true;
    private boolean focusable = true;
    private String uuid = "Component";
    /**
     * Indicates that moving through the component should work as an animation
     * 
     */
    private boolean smoothScrolling = false;
    /**
     * Animation speed in milliseconds allowing a developer to slow down or
     * accelerate the smooth animation mode
     */
    private int animationSpeed;
    private Motion animationMotion;
    private Motion draggedMotion;
    /**
     * Allows us to flag a drag operation in action thus preventing the mouse
     * pointer release event from occurring.
     */
    private boolean dragActivated;
    private int initialScrollY = -1;
    private int destScrollY = -1;
    private int lastScrollY;
    private int beforeLastScrollY;
    private long[] lastTime = new long[2];
    private int[] lastDragged = new int[2];
    private int pLastDragged = 0;
    //scroll
    private boolean shouldCalcScrollSize = true;
    private int scrollX;
    private int scrollY;
    private boolean isScrollVisible = true;
    /**
     * Indicates if the component is in the initialized state, a component is
     * initialized when its initComponent() method was invoked. The initMethod
     * is invoked before showing the component to the user.
     */
    private boolean initialized;
    private Hashtable clientProperties;
    private java.awt.Rectangle dirtyRegion = null;
    private Object dirtyRegionLock = new Object();
    private int position;
    private int repetitionMode;
    private int animationMode;
    private int delay;

    /**
     * Creates a new instance of Component.
     * 
     */
    protected Component() {
        style = UIManager.getInstance().getComponentStyle(getUIID());
        LookAndFeel laf = UIManager.getInstance().getLookAndFeel();
        animationSpeed = laf.getDefaultSmoothScrollingSpeed();
    }

    private void initStyle() {
        style = UIManager.getInstance().getComponentStyle(getUIID());
        if (style != null) {
            style.addStyleListener(this);
            style.setBgPainter(new BGPainter());
        }
    }

    /**
     * Unique identifier for a component, must be overriden for a component so a
     * style can be applied to the component
     * 
     * @return unique string identifying this component for the style sheet
     */
    public String getUIID() {
        return this.uuid;
    }

    public void setUIID(String uuid) {
        String tmpUuid = this.uuid;
        this.uuid = uuid;
        if (tmpUuid != null && !tmpUuid.equals(uuid)) {
            initStyle();
        }
    }

    /**
     * Returns the current component x location relatively to its parent
     * container.
     * 
     * 
     * @return the current x coordinate of the components origin
     * @see setX(int)
     */
    public int getX() {
        return bounds.getX();
    }

    /**
     * Returns the component y location relatively to its parent container.
     * 
     * 
     * @return the current y coordinate of the components origin
     * @see setY(int)
     */
    public int getY() {
        return bounds.getY();
    }

    /**
     * Returns whether the component is visible or not.
     * 
     * 
     * @return true if component is visible; otherwise false
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Indicates if the component is in the initialized state, a component is
     * initialized when its initComponent() method was invoked. The initMethod
     * is invoked before showing the component to the user.
     * 
     * @return true if the component is in the initialized state
     */
    protected boolean isInitialized() {
        return initialized;
    }

    /**
     * Cleansup the initialization flags in the hierachy, notice that paint
     * calls might still occur after deinitilization mostly to perform
     * transitions etc.
     * <p>
     * However interactivity, animation and event tracking code can and probably
     * should be removed by this method.
     */
    void deinitializeImpl() {
        if (isInitialized()) {
            setInitialized(false);
            setDirtyRegion(null);
            deinitialize();
        }
    }

    /**
     * Indicates if the component is in the initialized state, a component is
     * initialized when its initComponent() method was invoked. The initMethod
     * is invoked before showing the component to the user.
     * 
     * @param initialized
     *            Indicates if the component is in the initialized state
     */
    protected void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    /**
     * Invoked to indicate that the component initialization is being reversed
     * since the component was detached from the container hierarchy. This
     * allows the component to deregister animators and cleanup after itself.
     * This method is the opposite of the initComponent() method.
     */
    protected void deinitialize() {
        // TODO::
    }

    /**
     * Internal method indicating whether we are in the middle of a drag
     * 
     * @return true if we are in the middle of a drag; otherwise false
     */
    boolean isDragActivated() {
        return dragActivated;
    }

    /**
     * Sets the Component Parent. This method should not be called by the user.
     * 
     * @param parent
     *            the parent container
     */
    void setParent(Container parent) {
        this.parent = parent;
    }

    private void initScrollMotion() {
        Motion m = Motion.createLinearMotion(initialScrollY, destScrollY,
                getScrollAnimationSpeed());
        setAnimationMotion(m);
        m.start();
    }

    /**
     * Allows defining the physics for the animation motion behavior directly by
     * plugging in an alternative motion object
     * 
     * @param motion
     *            new motion object
     */
    private void setAnimationMotion(Motion motion) {
        animationMotion = motion;
    }

    /**
     * This method is useful since it is not a part of the public API yet allows
     * a component within this package to observe focus events without
     * implementing a public interface or creating a new class
     */
    void focusGainedInternal() {
    }

    /**
     * This method is useful since it is not a part of the public API yet allows
     * a component within this package to observe focus events without
     * implementing a public interface or creating a new class
     */
    void focusLostInternal() {
    }

    /**
     * Client properties allow the association of meta-data with a component,
     * this is useful for some applications that construct GUI's on the fly and
     * need to track the connection between the UI and the data.
     * 
     * 
     * @param key
     *            - the key used for putClientProperty
     * @return the value set to putClientProperty or null if no value is set to
     *         the property
     */
    public Object getClientProperty(String key) {
        if (clientProperties == null) {
            return null;
        }
        return clientProperties.get(key);
    }

    /**
     * Client properties allow the association of meta-data with a component,
     * this is useful for some applications that construct GUI's on the fly and
     * need to track the connection between the UI and the data. Setting the
     * value to null will remove the client property from the component.
     * 
     * 
     * @param key
     *            - arbitrary key for the property
     * @param value
     *            - the value assigned to the given client property
     */
    public void putClientProperty(String key, Object value) {
        if (clientProperties == null) {
            if (value == null) {
                return;
            }
            clientProperties = new Hashtable();
        }
        if (value == null) {
            clientProperties.remove(key);
            if (clientProperties.size() == 0) {
                clientProperties = null;
            }
        } else {
            clientProperties.put(key, value);
        }
    }

    /**
     * Toggles visibility of the component.
     * 
     * 
     * @param visible
     *            - true if component is visible; otherwise false
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Returns the component width.
     * 
     * 
     * @return the component width
     * @see setWidth(int)
     */
    public int getWidth() {
        return bounds.getSize().getWidth();
    }

    /**
     * Returns the component height.
     * 
     * 
     * @return the component height
     * @see setHeight(int)
     */
    public int getHeight() {
        return bounds.getSize().getHeight();
    }

    /**
     * Sets the Component x location relative to the parent container, this
     * method is exposed for the purpose of external layout managers and should
     * not be invoked directly.
     * 
     * 
     * @param x
     *            - the current x coordinate of the components origin
     * @see getX()
     */
    public void setX(int x) {
        bounds.setX(x);
    }

    /**
     * Sets the Component y location relative to the parent container, this
     * method is exposed for the purpose of external layout managers and should
     * not be invoked directly.
     * 
     * 
     * @param y
     *            - the current y coordinate of the components origin
     * @see getY()
     */
    public void setY(int y) {
        bounds.setY(y);
    }

    /**
     * The baseline for the component text according to which it should be
     * aligned with other components for best visual look.
     * 
     * 
     * @param width
     *            - the component width
     * @param height
     *            - the component height
     * @return baseline value from the top of the component
     */
    public int getBaseline(int width, int height) {
        return height - getStyle().getPadding(BOTTOM);
    }

    /**
     * Returns a constant indicating how the baseline varies with the size of
     * the component.
     * 
     * 
     * @return one of BRB_CONSTANT_ASCENT, BRB_CONSTANT_DESCENT,
     *         BRB_CENTER_OFFSET or BRB_OTHER
     */
    public int getBaselineResizeBehavior() {
        return BRB_OTHER;
    }

    /**
     * Sets the Component Preferred Size, there is no guarantee the Component
     * will be sized at its Preferred Size. The final size of the component may
     * be smaller than its preferred size or even larger than the size.<br>
     * The Layout manager can take this value into consideration, but there is
     * no guarantee or requirement.
     * 
     * 
     * @param d
     *            - the component dimension
     * @see getPreferredSize()
     */
    public void setPreferredSize(Dimension d) {
        preferredSize().setWidth(d.getWidth());
        preferredSize().setHeight(d.getHeight());
        sizeRequestedByUser = true;

        setSize(d);
    }

    /**
     * Returns the Component Preferred Size, there is no guarantee the Component
     * will be sized at its Preferred Size. The final size of the component may
     * be smaller than its preferred size or even larger than the size.<br>
     * The Layout manager can take this value into consideration, but there is
     * no guarantee or requirement.
     * 
     * 
     * @return the component preferred size
     * @see setPreferredSize(com.sun.dtv.lwuit.geom.Dimension)
     */
    public Dimension getPreferredSize() {
        return preferredSize();
    }

    /**
     * Makes sure the component is visible in the scroll if this container is
     * scrollable
     * 
     * @param rect
     *            the rectangle that need to be visible
     * @param coordinateSpace
     *            the component according to whose coordinates rect is defined.
     *            Rect's x/y are relative to that component (they are not
     *            absolute).
     */
    protected void scrollRectToVisible(Rectangle rect, Component coordinateSpace) {
        scrollRectToVisible(rect.getX(), rect.getY(),
                rect.getSize().getWidth(), rect.getSize().getHeight(),
                coordinateSpace);
    }

    /**
     * Makes sure the component is visible in the scroll if this container is
     * scrollable
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param coordinateSpace
     *            the component according to whose coordinates rect is defined.
     *            Rect's x/y are relative to that component (they are not
     *            absolute).
     */
    protected void scrollRectToVisible(int x, int y, int width, int height,
            Component coordinateSpace) {
        if (isScrollable()) {
            int scrollPosition = getScrollY();
            Style s = getStyle();
            int w = getWidth() - s.getPadding(LEFT) - s.getPadding(RIGHT);
            int h = getHeight() - s.getPadding(TOP) - s.getPadding(BOTTOM);
            Rectangle view;
            if (isSmoothScrolling() && destScrollY > -1) {
                view = new Rectangle(getScrollX(), destScrollY, w, h);
            } else {
                view = new Rectangle(getScrollX(), getScrollY(), w, h);
            }
            int relativeX = x;
            int relativeY = y;
            // component needs to be in absolute coordinates...
            Container parent = null;
            if (coordinateSpace != null) {
                parent = coordinateSpace.getParent();
            }
            if (parent == this) {
                if (view.contains(x, y, width, height)) {
                    return;
                }
            } else {
                while (parent != this) {
                    // mostly a special case for list
                    if (parent == null) {
                        relativeX = x;
                        relativeY = y;
                        break;
                    }
                    relativeX += parent.getX();
                    relativeY += parent.getY();
                    parent = parent.getParent();
                }
                if (view.contains(relativeX, relativeY, width, height)) {
                    return;
                }
            }
            if (isScrollableX()) {
                if (getScrollX() > relativeX) {
                    setScrollX(relativeX);
                }
                int rightX = relativeX + width - s.getPadding(LEFT)
                        - s.getPadding(RIGHT);
                if (getScrollX() + w < rightX) {
                    setScrollX(getScrollX() + (rightX - (getScrollX() + w)));
                } else {
                    if (getScrollX() > relativeX) {
                        setScrollX(relativeX);
                    }
                }
            }
            if (isScrollableY()) {
                if (getScrollY() > relativeY) {
                    scrollPosition = relativeY;
                }
                int bottomY = relativeY + height - s.getPadding(TOP)
                        - s.getPadding(BOTTOM);
                if (getScrollY() + h < bottomY) {
                    scrollPosition = getScrollY()
                            + (bottomY - (getScrollY() + h));
                } else {
                    if (getScrollY() > relativeY) {
                        scrollPosition = relativeY;
                    }
                }
                // if (isSmoothScrolling())
                // {
                // initialScrollY = getScrollY();
                // destScrollY = scrollPosition;
                // initScrollMotion();
                // }
                // else
                // {
                setScrollY(scrollPosition);
                // }
            }
            repaint();
        } else {
            // try to move parent scroll if you are not scrollable
            Container parent = getParent();
            if (parent != null) {
                parent.scrollRectToVisible(
                        getAbsoluteX() - parent.getAbsoluteX() + x,
                        getAbsoluteY() - parent.getAbsoluteY() + y, width,
                        height, parent);
            }
        }
    }

    /**
     * Sets the Component width, this method is exposed for the purpose of
     * external layout managers and should not be invoked directly.<br>
     * If a user wishes to effect the component size setPreferredSize should be
     * used.
     * 
     * 
     * @param width
     *            - the width of the component
     * @see getWidth(), setPreferredSize(com.sun.dtv.lwuit.geom.Dimension)
     */
    public void setWidth(int width) {
        bounds.getSize().setWidth(width);
    }

    /**
     * Sets the Component height, this method is exposed for the purpose of
     * external layout managers and should not be invoked directly.<br>
     * If a user wishes to effect the component size setPreferredSize should be
     * used.
     * 
     * 
     * @param height
     *            - the height of the component
     * @see getHeight(), setPreferredSize(com.sun.dtv.lwuit.geom.Dimension)
     */
    public void setHeight(int height) {
        bounds.getSize().setHeight(height);
    }

    /**
     * Sets the Component size, this method is exposed for the purpose of
     * external layout managers and should not be invoked directly.<br>
     * If a user wishes to effect the component size setPreferredSize should be
     * used.
     * 
     * 
     * @param d
     *            - the component dimension
     * @see setPreferredSize(com.sun.dtv.lwuit.geom.Dimension)
     */
    public void setSize(com.sun.dtv.lwuit.geom.Dimension d) {
        bounds = new Rectangle(bounds.getX(), bounds.getY(), d);
    }

    /**
     * Returns the container in which this component is contained.
     * 
     * 
     * 
     * @return the parent container in which this component is contained
     */
    public Container getParent() {
        return this.parent;
    }

    /**
     * Allows subclasses to bind functionality that relies on fully initialized
     * and "ready for action" component state
     */
    protected void initComponent() {
    }

    void checkAnimation() {
        Image bgImage = getStyle().getBgImage();
        if (bgImage != null && bgImage.isAnimation()) {
            Form parent = getComponentForm();
            if (parent != null) {
                parent.registerAnimated(this);
            }
        }
    }

    /**
     * Invoked internally to initialize and bind the component
     */
    void initComponentImpl() {
        if (!initialized) {
            initialized = true;
            if (isSmoothScrolling() && isScrollable()) {
                getComponentForm().registerAnimated(this);
            }
            UIManager.getInstance().getLookAndFeel().bind(this);
            checkAnimation();
            initComponent();
        }
    }

    /**
     * Registers interest in receiving callbacks for focus gained events, a
     * focus event is invoked when the component accepts the focus. A special
     * case exists for the Form which sends a focus even for every selection
     * within the form.
     * 
     * 
     * @param l
     *            - listener interface implementing the observable pattern
     * @see removeFocusListener(com.sun.dtv.lwuit.events.FocusListener)
     */
    public void addFocusListener(FocusListener l) {
        focusListeners.add(l);
    }

    /**
     * Deregisters interest in receiving callbacks for focus gained events.
     * 
     * 
     * @param l
     *            - listener interface implementing the observable pattern
     * @see addFocusListener(com.sun.dtv.lwuit.events.FocusListener)
     */
    public void removeFocusListener(FocusListener l) {
        focusListeners.remove(l);
    }

    boolean isFixedPosition() {
        return fixedPosition;
    }

    protected Dimension calcPreferredSize() {
        return new Dimension(0, 0);
    }

    /**
     * Indicates whether the component should/could scroll by default a
     * component is not scrollable.
     * 
     * @return whether the component is scrollable
     */
    protected boolean isScrollable() {
        return isScrollableX() || isScrollableY();
    }

    /**
     * Indicates whether a border should be painted
     * 
     * @return if the border will be painted
     * @deprecated use getStyle().getBorder() != null
     */
    boolean isBorderPainted() {
        return getStyle().getBorder() != null;
    }

    /**
     * Paints the UI for the scrollbars on the component, this will be invoked
     * only for scrollable components. This method invokes the appropriate X/Y
     * versions to do all the work.
     * 
     * @param g
     *            the component graphics
     */
    protected void paintScrollbars(Graphics g) {
        if (isScrollableX()) {
            paintScrollbarX(g);
        }
        if (isScrollableY()) {
            paintScrollbarY(g);
        }
    }

    /**
     * Returns the Components dimension in scrolling, this is very similar to
     * the preferred size aspect only it represents actual scrolling limits.
     * 
     * @return the component actual size with all scrolling
     */
    public Dimension getScrollDimension() {
        if (!scrollSizeRequestedByUser && (scrollSize == null || shouldCalcScrollSize)) {
            scrollSize = calcScrollSize();
            shouldCalcScrollSize = false;
        }
        return scrollSize;
    }

    /**
     * Method that can be overriden to represent the actual size of the
     * component when it differs from the desireable size for the viewport
     * 
     * @return scroll size, by default this is the same as the preferred size
     */
    protected Dimension calcScrollSize() {
        return calcPreferredSize();
    }

    /**
     * Set the size for the scroll area
     * 
     * @param d
     *            dimension of the scroll area
     */
    public void setScrollSize(Dimension d) {
        scrollSize = d;
        scrollSizeRequestedByUser = true;
    }

    /**
     * Paints the UI for the scrollbar on the X axis, this method allows
     * component subclasses to customize the look of a scrollbar
     * 
     * @param g
     *            the component graphics
     */
    protected void paintScrollbarX(Graphics g) {
        float scrollW = getScrollDimension().getWidth();
        float offset = ((float) getScrollX()) / scrollW;
        float block = ((float) getWidth()) / scrollW;
        UIManager.getInstance().getLookAndFeel().drawHorizontalScroll(g, this, offset, block);
    }

    /**
     * Paints the UI for the scrollbar on the Y axis, this method allows
     * component subclasses to customize the look of a scrollbar
     * 
     * @param g
     *            the component graphics
     */
    protected void paintScrollbarY(Graphics g) {
        float scrollH = getScrollDimension().getHeight();
        float block = ((float) getHeight()) / scrollH;
        float offset; // = ((float) getScrollY()) / scrollH;

        // normalize the offset to avoid rounding errors to the bottom of the screen
        if (getScrollY() + getHeight() == scrollH) {
            offset = 1 - block;
        } else {
            offset = (((float) getScrollY() + getHeight()) / scrollH) - block;
        }

        UIManager.getInstance().getLookAndFeel().drawVerticalScroll(g, this, offset, block);
    }

    /**
     * Normally returns getStyle().getBorder() but some subclasses might use
     * this to programmatically replace the border in runtime e.g. for a pressed
     * border effect
     * 
     * @return the border that is drawn according to the current component state
     */
    protected Border getBorder() {
        Border b = getStyle().getBorder();
        if (hasFocus()) {
            if (b != null) {
                return b.getFocusedInstance();
            }
            return b;
        } else {
            return b;
        }
    }

    private void drawPainters(Graphics g, Component par, Component c, Rectangle bounds) {
        if (par == null) {
            return;
        }

        if (par.getStyle().getBgTransparency() != ((byte) 0xFF)) {
            drawPainters(g, par.getParent(), par, bounds);
        }

        if (par.isBorderPainted()) {
            Border b = par.getBorder();

            if (b.isBackgroundPainter()) {
                b.paintBorderBackground(g, par);
            }
        }

        Painter p = par.getStyle().getBgPainter();

        if (p != null) {
            p.paint(g, new Rectangle(0, 0, par.getWidth(), par.getHeight()));
        }
    }

    /**
     * Draws the component border if such a border exists. The border unlike the
     * content of the component will not be affected by scrolling for a
     * scrollable component.
     * 
     * @param g
     *            graphics context on which the border is painted
     */
    protected void paintBorder(Graphics g) {
        Border b = getBorder();
        if (b != null) {
            if (isFocusPainted() && hasFocus()) {
                g.setColor(getStyle().getFgSelectionColor());
            } else {
                g.setColor(getStyle().getFgColor());
            }
            b.paint(g, this);
        }
    }

    /**
     * This method paints all the parents Components Background.
     * 
     * 
     * @param g
     *            - the graphics object
     */
    public void paintBackgrounds(Graphics g) {
        Rectangle bounds = new Rectangle(getAbsoluteX(), getAbsoluteY(),
                getWidth(), getHeight());

        drawPainters(g, this.getParent(), this, bounds);
    }

    /**
     * Used to reduce coupling between the TextArea component and
     * display/implementation classes thus reduce the size of the hello world
     * MIDlet
     * 
     * @param text
     *            text after editing is completed
     */
    void onEditComplete(String text) {
    }

    /**
     * This method allows us to detect an action event internally without
     * implementing the action listener interface.
     */
    void fireActionEvent() {
    }

    /**
     * Fired when component gains focus
     * 
     */
    void fireFocusGained() {
        fireFocusGained(this);
    }

    /**
     * Fired when component lost focus
     * 
     */
    void fireFocusLost() {
        fireFocusLost(this);
    }

    /**
     * This method allows a component to indicate that it is interested in an
     * "implicit" select command to appear in the "fire" button when 3
     * softbuttons are defined in a device.
     * 
     * @return true if this is a selectable interaction
     */
    protected boolean isSelectableInteraction() {
        return false;
    }

    /**
     * Callback allowing a developer to track wheh the component gains focus
     * 
     */
    protected void focusGained() {
    }

    /**
     * Callback allowing a developer to track wheh the component loses focus
     */
    protected void focusLost() {
    }

    /**
     * Fired when component gains focus
     * 
     */
    void fireFocusGained(Component cmp) {
        if (cmp.isCellRenderer()) {
            return;
        }

        // focusListeners.fireFocus(cmp);
        for (Iterator i = focusListeners.iterator(); i.hasNext();) {
            FocusListener listener = (FocusListener) i.next();

            listener.focusGained(cmp);
        }

        focusGainedInternal();
        focusGained();
        if (isSelectableInteraction()) {
            Form f = getComponentForm();
            if (f != null) {
                f.addSelectCommand();
            }
        }
    }

    /**
     * Indicates whether a border should be painted
     * 
     * @param b
     *            true would cause the paintBorder method to be invoked false
     *            allows us to hide the border of the component without deriving
     *            the class
     * @deprecated use getStyle().setBorder() to null to disable borders or
     *             install a different border
     */
    void setBorderPainted(boolean b) {
        if (!b) {
            getStyle().setBorder(null);
        } else {
            getStyle().setBorder(Border.getDefaultBorder());
        }
    }

    /**
     * When working in 3 softbutton mode "fire" key (center softbutton) is sent
     * to this method in order to allow 3 button devices to work properly. When
     * overriding this method you should also override isSelectableInteraction
     * to indicate that a command is placed appropriately on top of the fire key
     * for 3 soft button phones.
     */
    protected void fireClicked() {
    }

    /**
     * Fired when component lost focus
     * 
     */
    void fireFocusLost(Component cmp) {
        if (cmp.isCellRenderer()) {
            return;
        }
        if (isSelectableInteraction()) {
            Form f = getComponentForm();
            if (f != null) {
                f.removeSelectCommand();
            }
        }

        // focusListeners.fireFocus(cmp);
        for (Iterator i = focusListeners.iterator(); i.hasNext();) {
            FocusListener listener = (FocusListener) i.next();

            listener.focusLost(cmp);
        }

        focusLostInternal();
        focusLost();
    }

    /**
     * Paints the background of the component, invoked with the clipping region
     * and appropriate scroll translation.
     * 
     * @param g
     *            the component graphics
     */
    protected void paintBackground(Graphics g) {
        if (isBorderPainted()) {
            Border b = getBorder();
            if (b != null && b.isBackgroundPainter()) {
                b.paintBorderBackground(g, this);
                return;
            }
        }
        if (getStyle().getBgPainter() != null) {
            getStyle().getBgPainter().paint(g,
                    new Rectangle(getX(), getY(), getWidth(), getHeight()));
        }
    }

    /**
     * Returns the absolute X location based on the component hierarchy, this
     * method calculates a location on the screen for the component rather than
     * a relative location as returned by getX().
     * 
     * 
     * @return the absolute x location of the component
     * @see getX()
     */
    public int getAbsoluteX() {
        int x = getX() - getScrollX();
        Container parent = getParent();
        if (parent != null) {
            x += parent.getAbsoluteX();
        }
        return x;
    }

    /**
     * Returns the absolute Y location based on the component hierarchy, this
     * method calculates a location on the screen for the component rather than
     * a relative location as returned by getY().
     * 
     * 
     * @return the absolute y location of the component
     * @see getY()
     */
    public int getAbsoluteY() {
        int y = getY() - getScrollY();
        Container parent = getParent();
        if (parent != null) {
            y += parent.getAbsoluteY();
        }
        return y;
    }

    /**
     * Paints this component as a root by going to all the parent components and
     * setting the absolute translation based on coordinates and scroll status.
     * Restores translation when the painting is finished.
     * 
     * 
     * @param g
     *            - the graphics to paint this Component on
     */
    public final void paintComponent(Graphics g) {
        paintComponent(g, true);
    }

    /**
     * Paints this component as a root by going to all the parent components and
     * setting the absolute translation based on coordinates and scroll status.
     * Restores translation when the painting is finished.
     * 
     * 
     * @param g
     *            - the graphics to paint this Component on
     * @param background
     *            - if true paints all parents background
     */
    public final void paintComponent(Graphics g, boolean background) {
        int clipX = g.getClipX();
        int clipY = g.getClipX();
        int clipW = g.getClipWidth();
        int clipH = g.getClipHeight();

        int tx = g.getTranslateX(),
                ty = g.getTranslateY();

        g.translate(-tx + getAbsoluteX(), -ty + getAbsoluteY());
        g.setClip(0, 0, getWidth(), getHeight());

        if (background) {
            paintBackgrounds(g);
        }

        paint(g);

        if (isBorderPainted()) {
            paintBorder(g);
        }

        Container parent = getParent();

        if (parent == null) {
            g.translate(+tx - getAbsoluteX(), +ty - getAbsoluteY());
            g.setClip(clipX, clipY, clipW, clipH);

            return;
        }

        Component component = this;

        if (((parent.getLayout() != null && parent.getLayout().isOverlapSupported()) || parent.getLayout() == null) && parent.getComponentIndex(this) >= 0) {
            int index = parent.getComponentIndex(this) + 1;

            g.translate(parent.getScrollX(), parent.getScrollY());

            for (int i = index; i < parent.getComponentCount(); i++) {
                Component cmp = (Component) parent.getComponentAt(i);

                if (cmp.isVisible() == true) {
                    g.translate(getX(), getY());
                    cmp.paint(g);
                    g.translate(-getX(), -getY());
                }
            }

            g.translate(-parent.getScrollX(), -parent.getScrollY());
        }

        g.translate(+tx - getAbsoluteX(), +ty - getAbsoluteY());

        Form parentForm = getComponentForm();
        if (parentForm != null) {
            Painter glass = parentForm.getGlassPane();
            if (glass != null) {
                glass.paint(g, parentForm.getBounds());
            }
        }

        g.setClip(clipX, clipY, clipW, clipH);
    }

    private Dimension preferredSize() {

        if (!sizeRequestedByUser
                && (shouldCalcPreferredSize || preferredSize == null)) {
            shouldCalcPreferredSize = false;
            preferredSize = calcPreferredSize();
        }
        return preferredSize;

    }

    public void update(Graphics g) {
        // TODO:: comentar esse metodo para eliminar as sujeiras da tela
        g.clearRect(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * This method paints the Component on the screen, it should be overriden by
     * subclasses to perform custom drawing or invoke the UI API's to let the
     * PLAF perform the rendering.
     * 
     * 
     * @param g
     *            - the component graphics
     * @see paint in interface Animation
     */
    public void paint(Graphics g) {
    }

    /**
     * Indicates whether the component should/could scroll on the X axis.
     * 
     * 
     * @return whether the component is scrollable on the X axis
     */
    public boolean isScrollableX() {
        return false;
    }

    /**
     * Indicates whether the component should/could scroll on the Y axis.
     * 
     * 
     * @return whether the component is scrollable on the X axis
     */
    public boolean isScrollableY() {
        return false;
    }

    /**
     * Indicates the X position of the scrolling, this number is relative to the
     * component position and so a position of 0 would indicate the x position
     * of the component.
     * 
     * 
     * @return the X position of the scrolling
     */
    public int getScrollX() {
        return this.scrollX;
    }

    /**
     * Indicates the Y position of the scrolling, this number is relative to the
     * component position and so a position of 0 would indicate the x position
     * of the component.
     * 
     * 
     * @return the Y position of the scrolling
     */
    public int getScrollY() {
        return this.scrollY;
    }

    /**
     * Returns the gap to be left for the bottom scrollbar on the X axis. This
     * method is used by layout managers to determine the room they should leave
     * for the scrollbar
     * 
     * 
     * @return the gap to be left for the bottom scrollbar on the X axis
     */
    public int getBottomGap() {
        if (isScrollableX() && isScrollVisible()) {
            return UIManager.getInstance().getLookAndFeel().getHorizontalScrollHeight();
        }
        return 0;
    }

    /**
     * Returns the gap to be left for the side scrollbar on the Y axis. This
     * method is used by layout managers to determine the room they should leave
     * for the scrollbar. (note: side scrollbar rather than left scrollbar is
     * used for a future version that would support bidi).
     * 
     * 
     * 
     * @return the gap to be left for the side scrollbar on the Y axis
     */
    public int getSideGap() {
        if (isScrollableY() && isScrollVisible()) {
            return UIManager.getInstance().getLookAndFeel().getVerticalScrollWidth();
        }
        return 0;
    }

    /**
     * Returns true if the given absolute coordinate is contained in the
     * Component.
     * 
     * 
     * @param x
     *            - the given absolute x coordinate
     * @param y
     *            - the given absolute y coordinate
     * @return true if the given absolute coordinate is contained in the
     *         Component; otherwise false
     */
    public boolean contains(int x, int y) {
        int absX = getAbsoluteX() + getScrollX();
        int absY = getAbsoluteY() + getScrollY();

        return (x >= absX && x < absX + getWidth() && y >= absY && y < absY
                + getHeight());
    }

    /**
     * Returns the component bounds which is sometimes more convenient than
     * invoking getX/Y/Width/Height. Bounds are relative to parent container.<br>
     * Changing values within the bounds can lead to unpredicted behavior.
     * 
     * 
     * @return the component bounds
     * @see getX(), getY()
     */
    public Rectangle getBounds() {
        return this.bounds;
    }

    /**
     * Returns the component bounds for scrolling which might differ from the
     * getBounds for large components e.g. list.
     * 
     * @see #getX
     * @see #getY
     * @return the component bounds
     */
    protected Rectangle getVisibleBounds() {
        return bounds;
    }

    /**
     * Returns true if this component can receive focus and is enabled.
     * 
     * 
     * @return true if this component can receive focus; otherwise false
     */
    public boolean isFocusable() {
        return focusable && enabled && isVisible();
    }

    /**
     * A simple setter to determine if this Component can get focused.
     * 
     * 
     * @param focusable
     *            - indicate whether this component can get focused
     */
    public void setFocusable(boolean focusable) {
        this.focusable = focusable;
        Form p = getComponentForm();
        if (p != null) {
            p.clearFocusVectors();
        }
    }

    /**
     * Indicates whether focus should be drawn around the component or whether
     * it will handle its own focus painting.
     * 
     * 
     * @return true if focus should be drawn around the component ; otherwise
     *         false
     */
    public boolean isFocusPainted() {
        return focusPainted;
    }

    /**
     * Indicates whether focus should be drawn around the component or whether
     * it will handle its own focus painting.
     * 
     * 
     * @param focusPainted
     *            - indicates whether focus should be drawn around the component
     */
    public void setFocusPainted(boolean focusPainted) {
        this.focusPainted = focusPainted;
    }

    /**
     * Prevents key events from being grabbed for focus traversal. E.g. a list
     * component might use the arrow keys for internal navigation so it will
     * switch this flag to true in order to prevent the focus manager from
     * moving to the next component.
     * 
     * 
     * @return true if key events are being used for focus traversal ; otherwise
     *         false
     */
    public boolean handlesInput() {
        return handlesInput;
    }

    /**
     * Prevents key events from being grabbed for focus traversal. E.g. a list
     * component might use the arrow keys for internal navigation so it will
     * switch this flag to true in order to prevent the focus manager from
     * moving to the next component.
     * 
     * 
     * @param handlesInput
     *            - indicates whether key events can be grabbed for focus
     *            traversal
     */
    public void setHandlesInput(boolean handlesInput) {
        this.handlesInput = handlesInput;
    }

    /**
     * Returns true if the component has focus.
     * 
     * 
     * @return true if the component has focus; otherwise false
     * @see setFocus(boolean)
     */
    public boolean hasFocus() {
        return focused;
    }

    /**
     * This flag doesn't really give focus, its a state that determines what
     * colors from the Style should be used when painting the component. Actual
     * focus is determined by the parent Container.
     * 
     * 
     * @param focused
     *            - sets the state that determines what colors from the Style
     *            should be used when painting a focused component
     * @see requestFocus()
     */
    public void setFocus(boolean focused) {
        this.focused = focused;
    }

    /**
     * Returns the Component Form or null if this Component is not added to a
     * form.
     * 
     * 
     * @return the Component Form
     */
    public Form getComponentForm() {
        Component parent = getParent();
        if (parent instanceof Form) {
            return (Form) parent;
        } else {
            if (parent == null) {
                return null;
            } else {
                return parent.getComponentForm();
            }
        }
    }

    /**
     * Repaint this Component, the repaint call causes a callback of the paint
     * method on the event dispatch thread.
     * 
     * 
     */
    public void repaint() {
        if (dirtyRegion != null) {
            setDirtyRegion(null);
        }
        repaint(this);
    }

    /**
     * Repaint the specified area of this Component.
     * 
     * 
     * @param x
     *            - the x coordinate of the upper left corner
     * @param y
     *            - the y coordinate of the upper left corner
     * @param w
     *            - the width of the area
     * @param h
     *            - the height of the area
     */
    public void repaint(int x, int y, int w, int h) {
        Rectangle rect;
        synchronized (dirtyRegionLock) {
            if (dirtyRegion == null) {
                rect = new Rectangle(x, y, w, h);
            } else {
                rect = new Rectangle(dirtyRegion);
                Dimension size = rect.getSize();

                int x1 = Math.min(rect.getX(), x);
                int y1 = Math.min(rect.getY(), y);

                int x2 = Math.max(x + w, rect.getX() + size.getWidth());
                int y2 = Math.max(y + h, rect.getY() + size.getHeight());

                rect.setX(x1);
                rect.setY(y1);
                size.setWidth(x2 - x1);
                size.setHeight(y2 - y1);
                setDirtyRegion(rect);
            }
        }
        setDirtyRegion(rect);

        repaint(this);
    }

    /**
     * Used as an optimization to mark that this component is currently being
     * used as a cell renderer
     * 
     * @return rtue is this component is currently being used as a cell renderer
     */
    boolean isCellRenderer() {
        return cellRenderer;
    }

    /**
     * Repaint the given component to the screen
     * 
     * @param cmp
     *            the given component on the screen
     */
    public void repaint(Component cmp) {
        if (isCellRenderer() || cmp.getWidth() <= 0 || cmp.getHeight() <= 0) {
            return;
        }

        // null parent repaint can happen when a component is removed and
        // modified which is common ofr a popup
        Component parent = getParent();
        if (parent != null) {
            parent.repaint(cmp);
        }
    }

    /**
     * If this Component is focused this method is invoked when the user presses
     * and holds the key.
     * 
     * 
     * @param keyCode
     *            - the key code value to indicate a physical key.
     */
    public void longKeyPress(int keyCode) {
    }

    /**
     * If this Component is focused, the key pressed event will call this
     * method.
     * 
     * 
     * @param keyCode
     *            - the key code value to indicate a physical key.
     */
    public void keyPressed(int keyCode) {
    }

    /**
     * If this Component is focused, the key released event will call this
     * method.
     * 
     * 
     * @param keyCode
     *            - the key code value to indicate a physical key.
     */
    public void keyReleased(int keyCode) {
    }

    /**
     * If this Component is focused, the key repeat event will call this method.
     * Calls key pressed/released by default.
     * 
     * 
     * @param keyCode
     *            - the key code value to indicate a physical key.
     */
    public void keyRepeated(int keyCode) {
        /*
         * int game = Display.getInstance().getGameAction(keyCode); if (game ==
         * Display.GAME_DOWN || game == Display.GAME_UP || game ==
         * Display.GAME_LEFT || game == Display.GAME_RIGHT) {
         * keyPressed(keyCode); keyReleased(keyCode); }
         */
    }

    /**
     * Scroll animation speed in milliseconds allowing a developer to slow down
     * or accelerate the smooth animation mode.
     * 
     * 
     * @return scroll animation speed in milliseconds
     * @see setScrollAnimationSpeed(int)
     */
    public int getScrollAnimationSpeed() {
        return animationSpeed;
    }

    /**
     * Scroll animation speed in milliseconds allowing a developer to slow down
     * or accelerate the smooth animation mode.
     * 
     * 
     * @param animationSpeed
     *            - scroll animation speed in milliseconds
     * @see getScrollAnimationSpeed()
     */
    public void setScrollAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    /**
     * Indicates that scrolling through the component should work as an
     * animation.
     * 
     * 
     * @return whether this component use smooth scrolling
     */
    public boolean isSmoothScrolling() {
        return smoothScrolling;
    }

    /**
     * Indicates that scrolling through the component should work as an
     * animation.
     * 
     * 
     * @param smoothScrolling
     *            - indicates if a component uses smooth scrolling
     */
    public void setSmoothScrolling(boolean smoothScrolling) {
        this.smoothScrolling = smoothScrolling;
        Form f = getComponentForm();
        if (f != null) {
            if (smoothScrolling && isScrollable()) {
                f.registerAnimated(this);
            } else {
                f.deregisterAnimated(this);
            }
        }
    }

    /**
     * If this Component is focused, the pointer dragged event will call this
     * method.
     * 
     * 
     * @param x
     *            - the pointer x coordinate
     * @param y
     *            - the pointer y coordinate
     */
    public void pointerDragged(int x, int y) {
        if (isScrollable() && isSmoothScrolling()) {
            int axisValue;
            if (isScrollableY()) {
                axisValue = y;
            } else {
                axisValue = x;
            }

            if (!dragActivated) {
                dragActivated = true;
                beforeLastScrollY = axisValue;
                lastScrollY = axisValue;
                getComponentForm().setDraggedComponent(this);
            }
            // save time and locations to create velocity when the
            // pointer is released
            long currentTime = System.currentTimeMillis();
            if (currentTime != lastTime[(pLastDragged + lastTime.length + 1)
                    % lastTime.length]) {
                lastTime[pLastDragged] = System.currentTimeMillis();
                lastDragged[pLastDragged] = axisValue;
                pLastDragged = (++pLastDragged) % lastTime.length;
            }

            beforeLastScrollY = lastScrollY;
            lastScrollY = axisValue;

            // we drag inversly to get a feel of grabbing a physical screen
            // and pulling it in the reverse direction of the drag
            if (isScrollableY()) {
                int scroll = getScrollY() + (beforeLastScrollY - axisValue);
                if (scroll >= 0
                        && scroll < getScrollDimension().getHeight()
                        - getHeight()) {
                    setScrollY(scroll);
                }
            } else {
                int scroll = getScrollX() + (beforeLastScrollY - axisValue);
                if (scroll >= 0
                        && scroll < getScrollDimension().getWidth()
                        - getWidth()) {
                    setScrollX(scroll);
                }
            }
        } else {
            // try to find a scrollable element until you reach the Form
            Component parent = getParent();
            if (!(parent instanceof Form)) {
                parent.pointerDragged(x, y);
            }
        }
    }

    /**
     * Indicates the X position of the scrolling, this number is relative to the
     * component position and so a position of 0 would indicate the x position
     * of the component.
     * 
     * @param scrollX
     *            the X position of the scrolling
     */
    protected void setScrollX(int scrollX) {
        this.scrollX = scrollX;
        if (!isSmoothScrolling()) {
            this.scrollX = Math.min(this.scrollX, getScrollDimension().getWidth() - getWidth());
            this.scrollX = Math.max(this.scrollX, 0);
        }
        if (isScrollableX()) {
            repaint();
        }
    }

    /**
     * Indicates the X position of the scrolling, this number is relative to the
     * component position and so a position of 0 would indicate the x position
     * of the component.
     * 
     * @param scrollY
     *            the Y position of the scrolling
     */
    protected void setScrollY(int scrollY) {
        this.scrollY = scrollY;
        if (!isSmoothScrolling()) {
            this.scrollY = Math.min(this.scrollY, getScrollDimension().getHeight() - getHeight());
            this.scrollY = Math.max(this.scrollY, 0);
        }
        if (isScrollableY()) {
            repaint();
        }
    }

    /**
     * If this Component is focused, the pointer pressed event will call this
     * method.
     * 
     * 
     * @param x
     *            - the pointer x coordinate
     * @param y
     *            - the pointer y coordinate
     */
    public void pointerPressed(int x, int y) {
        draggedMotion = null;
    }

    /**
     * If this Component is focused, the pointer released event will call this
     * method.
     * 
     * 
     * @param x
     *            - the pointer x coordinate
     * @param y
     *            - the pointer y coordinate
     */
    public void pointerReleased(int x, int y) {
        if (dragActivated) {
            long currentTime = System.currentTimeMillis();

            // replace x and y if this is an x scrolling container
            if (!isScrollableY()) {
                y = x;
            }

            if (currentTime != lastTime[(pLastDragged + lastTime.length + 1)
                    % lastTime.length]) {
                lastTime[pLastDragged] = System.currentTimeMillis();
                lastDragged[pLastDragged] = y;
                pLastDragged = (++pLastDragged) % lastTime.length;
            }
            float velocity = (float) (lastDragged[pLastDragged] - lastDragged[(pLastDragged
                    + lastDragged.length + 1)
                    % lastDragged.length])
                    / (lastTime[pLastDragged] - lastTime[(pLastDragged
                    + lastTime.length + 1)
                    % lastTime.length]);
            velocity = velocity * -1;

            if (isScrollableY()) {
                draggedMotion = Motion.createFrictionMotion(scrollY, velocity,
                        0.0004f);
            } else {
                draggedMotion = Motion.createFrictionMotion(scrollX, velocity,
                        0.0004f);
            }
            draggedMotion.start();
            dragActivated = false;
        }
    }

    /**
     * Returns the Component Style allowing us to manipulate the look of the
     * component.
     * 
     * 
     * 
     * @return the component Style object
     * @see setStyle(com.sun.dtv.lwuit.plaf.Style)
     */
    public Style getStyle() {
        return this.style;
    }

    /**
     * Changes the Component Style by replacing the Component Style with the
     * given Style.
     * 
     * 
     * @param style
     *            - the component Style object
     * @see getStyle()
     */
    public void setStyle(Style style) {
        if (this.style != null) {
            this.style.removeStyleListener(this);
        }
        this.style = style;
        this.style.addStyleListener(this);
        if (this.style.getBgPainter() == null) {
            this.style.setBgPainter(new BGPainter());
        }
        setShouldCalcPreferredSize(true);
        checkAnimation();
    }

    /**
     * Indicates the values within the component have changed and preferred size
     * should be recalculated
     * 
     * @param shouldCalcPreferredSize
     *            indicate whether this component need to recalculate his
     *            preferred size
     */
    protected void setShouldCalcPreferredSize(boolean shouldCalcPreferredSize) {
        if (!shouldCalcScrollSize) {
            this.shouldCalcScrollSize = shouldCalcPreferredSize;
        }
        if (shouldCalcPreferredSize != this.shouldCalcPreferredSize) {
            this.shouldCalcPreferredSize = shouldCalcPreferredSize;
            this.shouldCalcScrollSize = shouldCalcPreferredSize;
            if (shouldCalcPreferredSize && getParent() != null) {
                this.shouldCalcPreferredSize = shouldCalcPreferredSize;
                getParent().setShouldCalcPreferredSize(shouldCalcPreferredSize);
            }
        }
    }

    /**
     * Changes the current component to the focused component, will work only
     * for a component that belongs to a parent form.
     * 
     * 
     */
    public void requestFocus() {
        Form rootForm = getComponentForm();
        if (rootForm != null) {
            rootForm.requestFocus(this);
        }
    }

    /**
     * Overriden to return a useful value for debugging purposes. Overrides
     * Object.toString.
     * 
     * 
     * @return a string representation of this component
     * @see toString in class Object
     */
    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        return className + "[" + paramString() + "]";
    }

    /**
     * Returns a string representing the state of this component. This method is
     * intended to be used only for debugging purposes, and the content and
     * format of the returned string may vary between implementations. The
     * returned string may be empty but may not be <code>null</code>.
     * 
     * @return a string representation of this component's state
     */
    protected String paramString() {
        return "x=" + getX() + " y=" + getY() + " width=" + getWidth()
                + " height=" + getHeight();
    }

    /**
     * Allows defining the physics for the animation motion behavior directly by
     * plugging in an alternative motion object
     * 
     * @return the component motion object
     */
    private Motion getAnimationMotion() {
        return animationMotion;
    }

    /**
     * Makes sure the component is up to date with the current style object.
     * 
     * 
     */
    public void refreshTheme() {
        refreshTheme(getUIID());
    }

    /**
     * Makes sure the component is up to date with the given UIID
     * 
     * @param id
     *            The Style Id to update the Component with
     */
    protected void refreshTheme(String id) {
        if (style.isModified()) {
            style.merge(UIManager.getInstance().getComponentStyle(id));
        } else {
            setStyle(UIManager.getInstance().getComponentStyle(id));
        }
        checkAnimation();
        UIManager.getInstance().getLookAndFeel().bind(this);
    }

    /**
     * <B>Description copied from interface:
     * <CODE><A HREF="../../../../com/sun/dtv/lwuit/animations/Animation.html#animate()">Animation</A></CODE>
     * </B></DD> Allows the animation to reduce "repaint" calls when it returns
     * false. It is called once for every frame.
     * 
     * 
     * @return true if a repaint is desired or false if no repaint is necessary
     * @see animate in interface Animation
     */
    public boolean animate() {
        Image bgImage = getStyle().getBgImage();
        boolean animateBackground = bgImage != null && bgImage.isAnimation()
                && ((Animation) bgImage).animate();
        Motion m = getAnimationMotion();
        // preform regular scrolling
        if (m != null && destScrollY != -1 && destScrollY != getScrollY()) {
            // change the variable directly for efficiency both in removing
            // redundant
            // repaints and scroll checks
            scrollY = m.getValue();

            if (destScrollY == scrollY) {
                destScrollY = -1;
            }
            return true;
        }

        // preform the dragging motion if exists
        if (draggedMotion != null && !draggedMotion.isFinished()) {
            // change the variable directly for efficiency both in removing
            // redundant
            // repaints and scroll checks
            int dragVal = draggedMotion.getValue();
            if (isScrollableY()) {
                if (dragVal >= 0
                        && dragVal <= (getScrollDimension().getHeight() - getHeight())) {
                    scrollY = dragVal;
                    return true;
                }
            } else {
                if (dragVal >= 0
                        && dragVal <= (getScrollDimension().getWidth() - getWidth())) {
                    scrollX = dragVal;
                    return true;
                }
            }
        }

        if (animateBackground && bgImage instanceof StaticAnimation) {
            Rectangle dirty = ((StaticAnimation) bgImage).getDirtyRegion();
            if (dirty != null) {
                dirty.setX(getAbsoluteX());
                dirty.setY(getAbsoluteY() + dirty.getY());
            }
            setDirtyRegion(dirty);
        }

        return animateBackground;
    }

    /**
     * sets the Component dirty region
     * 
     * @param dirty
     */
    public void setDirtyRegion(Rectangle dirty) {
        synchronized (dirtyRegionLock) {
            if (dirty == null) {
                this.dirtyRegion = null;
            } else {
                dirtyRegion = new java.awt.Rectangle(dirty.getX(),
                        dirty.getY(), dirty.getSize().getWidth(), dirty.getSize().getHeight());
            }
        }
    }

    /**
     * gets the Component dirty region
     * 
     * @return
     */
    public Rectangle getDirtyRegion() {
        if (dirtyRegion != null) {
            return new com.sun.dtv.lwuit.geom.Rectangle(dirtyRegion);
        }

        return null;
    }

    /**
     * Used as an optimization to mark that this component is currently being
     * used as a cell renderer.
     * 
     * 
     * @param cellRenderer
     *            - indicate whether this component is currently being used as a
     *            cell renderer
     */
    public void setCellRenderer(boolean cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    /**
     * Indicate whether this component scroll is visible.
     * 
     * 
     * 
     * @return true is this component scroll is visible; otherwise false
     */
    public boolean isScrollVisible() {
        return isScrollVisible;
    }

    /**
     * Set whether this component scroll is visible.
     * 
     * 
     * @param isScrollVisible
     *            - Indicate whether this component scroll is visible
     */
    public void setIsScrollVisible(boolean isScrollVisible) {
        this.isScrollVisible = isScrollVisible;
    }

    /**
     * <B>Description copied from interface:
     * <CODE><A HREF="../../../../com/sun/dtv/lwuit/events/StyleListener.html#styleChanged(java.lang.String, com.sun.dtv.lwuit.plaf.Style)">StyleListener</A></CODE>
     * </B></DD> Invoked to indicate a change in a propertyName of a Style.
     * 
     * 
     * @param propertyName
     *            - the property name that was changed
     * @param source
     *            - The changed Style object
     * @see styleChanged in interface StyleListener
     */
    public void styleChanged(String propertyName, Style source) {
        // changing the Font, Padding, Margin may casue the size of the
        // Component to Change
        // therefore we turn on the shouldCalcPreferredSize flag
        if (!shouldCalcPreferredSize && propertyName.equals(Style.FONT)
                || propertyName.equals(Style.MARGIN)
                || propertyName.equals(Style.PADDING)) {
            setShouldCalcPreferredSize(true);
            Container parent = getParent();
            if (parent != null) {
                parent.revalidate();
            }
        }
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the down key.
     * 
     * 
     * @return the Component will receive focus next when traversing with the
     *         down key
     * @see setNextFocusDown(com.sun.dtv.lwuit.Component)
     */
    public Component getNextFocusDown() {
        return this.nextFocusDown;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the down key.
     * 
     * 
     * @param nextFocusDown
     *            - the Component which will receive focus next when traversing
     *            with the down key
     * @see getNextFocusDown()
     */
    public void setNextFocusDown(Component nextFocusDown) {
        this.nextFocusDown = nextFocusDown;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the up key.
     * 
     * 
     * @return the Component will receive focus next when traversing with the up
     *         key
     * @see setNextFocusUp(com.sun.dtv.lwuit.Component)
     */
    public Component getNextFocusUp() {
        return this.nextFocusUp;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the up key, this method doesn't affect the general focus
     * behavior.
     * 
     * 
     * @param nextFocusUp
     *            - the Component which will receive focus next when traversing
     *            with the up key
     * @see getNextFocusUp()
     */
    public void setNextFocusUp(Component nextFocusUp) {
        this.nextFocusUp = nextFocusUp;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the left key.
     * 
     * 
     * @return the Component will receive focus next when traversing with the
     *         left key
     * @see setNextFocusLeft(com.sun.dtv.lwuit.Component)
     */
    public Component getNextFocusLeft() {
        return this.nextFocusLeft;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the left key, this method doesn't affect the general
     * focus behavior.
     * 
     * 
     * @param nextFocusLeft
     *            - the Component which will receive focus next when traversing
     *            with the left key
     * @see getNextFocusLeft()
     */
    public void setNextFocusLeft(Component nextFocusLeft) {
        this.nextFocusLeft = nextFocusLeft;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the right key.
     * 
     * 
     * @return the Component will receive focus next when traversing with the
     *         right key
     * @see setNextFocusRight(com.sun.dtv.lwuit.Component)
     */
    public Component getNextFocusRight() {
        return this.nextFocusRight;
    }

    /**
     * Allows us to determine which component will receive focus next when
     * traversing with the right key.
     * 
     * 
     * @param nextFocusRight
     *            - the Component which will receive focus next when traversing
     *            with the right key
     * @see getNextFocusRight()
     */
    public void setNextFocusRight(Component nextFocusRight) {
        this.nextFocusRight = nextFocusRight;
    }

    /**
     * Indicates whether component is enabled or disabled thus allowing us to
     * prevent a component from receiving input events and indicate so visually.
     * 
     * 
     * @return true if this Component is enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Indicates whether component is enabled or disabled thus allowing us to
     * prevent a component from receiving input events and indicate so visually.
     * 
     * 
     * @param enabled
     *            - boolean to indicated whether the component should be enables
     *            or not
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Starts an animation. Please consider that an animation is in the stopped
     * mode per default, so it has to be started explicitly once it has been
     * created. In case the animation is already running, a call to
     * <code>start()</code> causes a restart of the animation.
     * 
     * 
     * @see start in interface Animated
     */
    public void start() {
    }

    /**
     * Stops an animation. In case the animation is already stopped, the call to
     * <code>stop()</code> has no effect.
     * 
     * 
     * @see stop in interface Animated
     */
    public void stop() {
    }

    /**
     * Obtains the running mode of an animation. Returns true if the animation
     * is running, false otherwise.
     * 
     * 
     * @return true if the animation is running, false otherwise
     * @see isRunning in interface Animated
     */
    public boolean isRunning() {
        return false;
    }

    /**
     * Forces an animation to jump to the position indicated by the parameter.
     * An animation can be considered as a row of images, the parameter of this
     * method provides the index within this row. If the animation is stopped, a
     * call to this method causes the animation to show the image at the
     * indicated position, but not to start. If the animation is running, a call
     * to this method causes the animation to jump to the image at the indicated
     * position and continue running immediately.
     * 
     * 
     * @param position
     *            - the index in the row of images building the animation to
     *            which the animation is forced to jump
     * @see jumpTo in interface Animated
     */
    public void jumpTo(int position) {
    }

    /**
     * Obtains the current position the animation is at the point of time of th
     * method call. An animation can be considered as a row of images, this
     * method provides the index within this row.
     * 
     * 
     * @return the current position of the image within the row of images
     *         building this animation
     * @see getPosition in interface Animated
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Determines how often the animation is repeated once it has been started.
     * 
     * 
     * @param n
     *            - number of repetitions to be determined. This can be any
     *            number larger than zero or LOOP alternatively. LOOP means that
     *            the animation runs in an endless loop until the stop() method
     *            has been called.
     * @see setRepetitionMode in interface Animated
     * @see getRepetitionMode()
     */
    public void setRepetitionMode(int n) {
        this.repetitionMode = n;
    }

    /**
     * Returns the repetition mode of this animation in form of a number. The
     * number reads how many repetitions have been determined for this
     * animation.
     * 
     * 
     * @return number of repetitions determined for this animation or LOOP which
     *         means the animation runs in an endless loop until the stop()
     *         method has been called.
     * @see getRepetitionMode in interface Animated
     * @see setRepetitionMode(int)
     */
    public int getRepetitionMode() {
        return this.repetitionMode;
    }

    /**
     * Determines the delay after every image when running this animation.
     * 
     * 
     * @param n
     *            - delay in milliseconds
     * @see setDelay in interface Animated
     * @see getDelay()
     */
    public void setDelay(int n) {
        this.delay = n;
    }

    /**
     * Obtains the delay after every image for this animation when running.
     * 
     * 
     * @return the delay in milliseconds
     * @see getDelay in interface Animated
     * @see setDelay(int)
     */
    public int getDelay() {
        return this.delay;
    }

    /**
     * Sets the animation mode for this animation. The animation mode determines
     * how the animation is running: always forwards, or forwards and backwards
     * alternating.
     * 
     * 
     * @param mode
     *            - the animation mode. Possible values are: REPEATING and
     *            ALTERNATING.
     * @see setAnimationMode in interface Animated
     * @see getAnimationMode()
     */
    public void setAnimationMode(int mode) {
        this.animationMode = mode;
    }

    /**
     * Obtains the animation mode for this animation. The animation mode
     * determines how the animation is running: always forwards, or forwards and
     * backwards alternating.
     * 
     * 
     * @return the animation mode. Possible values are: REPEATING and
     *         ALTERNATING.
     * @see getAnimationMode in interface Animated
     * @see setAnimationMode(int)
     */
    public int getAnimationMode() {
        return this.animationMode;
    }

    class BGPainter implements Painter {

        private Form parent;
        private Form previousTint;
        private boolean ignorCoordinates;
        private Painter painter;

        public BGPainter() {
        }

        public BGPainter(Form parent, Painter p) {
            this.painter = p;
            this.parent = parent;
        }

        public void setIgnorCoordinates(boolean ignorCoordinates) {
            this.ignorCoordinates = ignorCoordinates;
        }

        public void setPreviousForm(Form previous) {
            previousTint = previous;
        }

        public void paint(Graphics g, Rectangle rect) {
            if (painter != null) {
                if (previousTint != null) {
                    previousTint.paint(g);
                }
                Dimension d = rect.getSize();
                int width = d.getWidth();
                int height = d.getHeight();
                int x = rect.getX();
                int y = rect.getY();

                if (ignorCoordinates) {
                    // this is a special case for dialogs since they are
                    // "pushed" to
                    // a position in the screen and can't draw behind their
                    // title
                    // we need to still "pretend" that they own the screen...
                    x = 0;
                    y = 0;
                    width = parent.getWidth();
                    height = parent.getHeight();
                    int transY = g.getTranslateY();
                    g.translate(0, -transY);

                    painter.paint(g, new Rectangle(x, y, width, height));
                    g.translate(0, transY);
                } else {
                    painter.paint(g, new Rectangle(x, y, width, height));
                }
            } else {
                Style s = getStyle();
                int x = rect.getX();
                int y = rect.getY();
                int width = rect.getSize().getWidth();
                int height = rect.getSize().getHeight();
                if (width <= 0 || height <= 0) {
                    return;
                }
                Image bgImage = s.getBgImage();
                if (bgImage == null) {
                    try { // Jeff
                        if (hasFocus() && isFocusPainted()) {
                            g.setColor(s.getBgSelectionColor());
                            g.fillRect(x, y, width, height,
                                    s.getBgTransparency());
                            if (s.getBorder() != null) {
                                g.setColor(Color.WHITE);
                                g.drawRect(x, y, width, height);
                            }
                        } else {
                            g.setColor(s.getBgColor());
                            g.fillRect(x, y, width, height,
                                    s.getBgTransparency());
                            if (s.getBorder() != null) {
                                g.setColor(Color.WHITE);
                                g.drawRect(x, y, width, height);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (getStyle().isScaleImage()) {
                        if (bgImage.getWidth() != width
                                || bgImage.getHeight() != height) {
                            bgImage = bgImage.scaled(width, height);
                            s.setBgImage(bgImage, true);
                        }
                    } else {
                        int iW = bgImage.getWidth();
                        int iH = bgImage.getHeight();
                        for (int xPos = 0; xPos <= width; xPos += iW) {
                            for (int yPos = 0; yPos <= height; yPos += iH) {
                                g.drawImage(s.getBgImage(), x + xPos, y + yPos);
                            }
                        }
                        return;
                    }
                    g.drawImage(s.getBgImage(), x, y);
                }
            }
        }
    }

    /**
     * Indicates that this component is fixed into place and not affected by
     * scrolling of the parent container. This is applicable for components such
     * as menus etc...
     * 
     * @param fixedPosition
     *            whether this component is fixed into place and not affected by
     *            scrolling of the parent container
     */
    void setFixedPosition(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
    }

    final void paintInternal(Graphics g) {
        paintInternal(g, true);
    }

    final void paintInternal(Graphics g, boolean paintIntersects) {
        if (!isVisible()) {
            return;
        }
        int oX = g.getClipX();
        int oY = g.getClipY();
        int oWidth = g.getClipWidth();
        int oHeight = g.getClipHeight();
        if (bounds.intersects(oX, oY, oWidth, oHeight)) {
            g.setClip(getX(), getY(), getWidth(), getHeight());
            paintBackgrounds(g);
            if (isScrollable()) {
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                g.translate(-scrollX, -scrollY);
                paint(g);
                g.translate(scrollX, scrollY);
                if (isScrollVisible) {
                    paintScrollbars(g);
                }
            } else {
                paint(g);
            }
            if (isBorderPainted()) {
                paintBorder(g);
            }
            // paint all the intersecting Components above the Component
            if (paintIntersects && parent != null) {
                paintIntersectingComponentsAbove(g);
            }

            g.setClip(oX, oY, oWidth, oHeight);
        }
    }

    private void paintIntersectingComponentsAbove(Graphics g) {
        Container parent = getParent();
        Component component = this;
        Rectangle bounds = new Rectangle(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
        int tx = g.getTranslateX();
        int ty = g.getTranslateY();

        g.translate(-tx, -ty);
        while (parent != null) {
            g.translate(parent.getAbsoluteX() + parent.getScrollX(), parent.getAbsoluteY() + parent.getScrollY());
            //parent.paintIntersecting(g, component, bounds, true);
            g.translate(-parent.getAbsoluteX() - parent.getScrollX(), -parent.getAbsoluteY() - parent.getScrollY());
            component = parent;
            parent = parent.getParent();
        }
        g.translate(tx, ty);

    }
}
