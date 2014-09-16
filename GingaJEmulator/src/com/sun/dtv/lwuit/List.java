package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Motion;

import com.sun.dtv.lwuit.list.DefaultListModel;
import com.sun.dtv.lwuit.list.DefaultListCellRenderer;
import com.sun.dtv.lwuit.list.ListCellRenderer;
import com.sun.dtv.lwuit.list.ListModel;

import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.events.DataChangedListener;
import com.sun.dtv.lwuit.events.SelectionListener;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;

import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.Border;

import com.sun.dtv.ui.Matte;
import com.sun.dtv.ui.MatteException;
import com.sun.dtv.ui.TextLayoutManager;
import com.sun.dtv.ui.ViewOnlyComponent;
import com.sun.dtv.ui.event.RemoteControlEvent;

import java.awt.AWTEvent;

import java.util.Vector;
import java.util.Iterator;

public class List extends Component implements ViewOnlyComponent
{
	// Indicates the list isn't fixed and that selection is movable.
	public static final int FIXED_NONE = 0;
	// Indicates that the list is not fixed in place but cycles its elements.
	public static final int FIXED_NONE_CYCLIC = 1;
	// Indicates the list selection will only reach the edge when there are no more elements in the list.
	public static final int FIXED_NONE_ONE_ELEMENT_MARGIN_FROM_EDGE = 2;
	// Indicates the list selection is fixed into place at the top of the list or at the left of the list.
	public static final int FIXED_LEAD = 10;
	// Indicates the list selection is fixed into place at the bottom of the list or at the right of the list.
	public static final int FIXED_TRAIL = 11;
	// Indicates the list selection is fixed into place at the center of the list.
	public static final int FIXED_CENTER = 12;
	
	// Indicates the list orientation is VERTICAL.
	public static final int VERTICAL = 0;
	// Indicates the list orientation is HORIZONTAL.
	public static final int HORIZONTAL = 1;

	private Motion listMotion;
	private TextLayoutManager textLayoutManager;
	private java.util.List actionListeners;
	private Matte matte;
	private Listeners listener;
	private Dimension elemSize;
	private Dimension selectedElemSize;
	private Object selectedItem;
	private Object renderingPrototype;
	private ListModel model;
	private ListCellRenderer renderer;
	private int destination;
	private int orientation;
	private int initialFixedDrag;
	private int initialFixedSelection;
	private int animationPosition;
	private int selectedIndex;
	private int itemGap;
	private int fixedSelection;
	private int borderGap;
	private int interactionState;
	private int horizontalAlignment;
	private int scalingMode;
	private int verticalAlignment;
	private boolean numericKeyActions;
	private boolean inputOnFocus = true;
	private boolean paintFocusBehindList;
	private static Border selectionFocusBorder = Border.createLineBorder(1);

	private Image enabledGraphicContent, disabledGraphicContent;
	private String enabledText, disabledText;

	/**
	 * Creates a new instance of List.
	 *
	 * 
	 * @param items - set of items placed into the list model
	 */
	public List(Vector items)
	{
		this(new DefaultListModel(items));
	}

	/**
	 * Creates a new instance of List.
	 *
	 * 
	 * @param items - set of items placed into the list model
	 */
	public List(Object[] items)
	{
		this(new DefaultListModel(items));
	}

	/**
	 * Creates a new instance of List with an empty default model.
	 *
	 * 
	 */
	public List()
	{
		this(new DefaultListModel());
	}

	/**
	 * Creates a new instance of List with the given model.
	 *
	 * 
	 * @param model - the model instance
	 */
	public List(ListModel model)
	{
		setUIID("List");
		this.model = model;
		this.renderer = new DefaultListCellRenderer();
		this.actionListeners = new java.util.ArrayList();
		this.enabledGraphicContent = this.disabledGraphicContent = null;
		this.enabledText = this.disabledText = "";
	}

	/**
	 * @inheritDoc
	 *
	 */
	void initComponentImpl() {
		super.initComponentImpl();
		dataChanged(0, 0);

		// lazily bind listeners to prevent a memory leak in cases where models
		// are stored separately from view
		bindListeners();
	}

	/**
	 * @inheritDoc
	 */
	void deinitializeImpl() {
		super.deinitializeImpl();

		// cleanup to allow garbage collection even if the user keeps the model in
		// memory which is a valid use case
		if (listener != null) {
			model.removeDataChangedListener(listener);
			model.removeSelectionListener(listener);
			listener = null;
		}
	}

	/**
	 * Updates the animation constant to a new value based on a keypress
	 * 
	 * @param direction direction of the animation 1 or -1
	 */
	private void updateAnimationPosition(int direction) {
		if (animationPosition != 0) {
			return;
		}
		if (isSmoothScrolling()) {
			if (orientation == VERTICAL) {
				animationPosition += (direction * getElementSize(false).getHeight());
			} else {
				animationPosition += (direction * getElementSize(false).getWidth());
			}
			destination = Math.abs(animationPosition);
			initListMotion();
		}
	}

	private void initListMotion() {
		listMotion = Motion.createSplineMotion(0, destination, getScrollAnimationSpeed());
		listMotion.start();
	}

	/**
	 * @inheritDoc
	 */
	protected void initComponent() {
		if (isSmoothScrolling()) {
			getComponentForm().registerAnimated(this);
		}
	}

	/**
	 * Callback to allow subclasses to react to a change in the model
	 * 
	 * @param status the type data change; REMOVED, ADDED or CHANGED
	 * @param index item index in a list model
	 */
	protected void modelChanged(int status, int index) {
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
		return getScrollDimension().getHeight() > getHeight() && (fixedSelection <= FIXED_NONE_CYCLIC) && orientation == VERTICAL;
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
		return getScrollDimension().getWidth() > getWidth() && (fixedSelection <= FIXED_NONE_CYCLIC) && orientation != VERTICAL;
	}

	/**
	 * Returns the number of elements in the list, shorthand for
	 * getModel().getSize().
	 *
	 * 
	 * 
	 * @return the number of elements in the list
	 */
	public int size()
	{
		return model.getSize();
	}

	/**
	 * Returns the current selected offset in the list.
	 *
	 * 
	 * 
	 * @return the current selected offset in the list
	 * @see setSelectedIndex(int),  setSelectedIndex(int, boolean)
	 */
	public int getSelectedIndex()
	{
		return model.getSelectedIndex();
	}

	/**
	 * Sets the current selected offset in the list, by default this
	 * implementation will scroll the list to the selection if the selection
	 * is outside of the screen.
	 *
	 * 
	 * @param index - the current selected offset in the list
	 * @see getSelectedIndex()
	 */
	public void setSelectedIndex(int index)
	{
		setSelectedIndex(index, true);
	}

	/**
	 * Sets the current selected offset in the list.
	 *
	 * 
	 * @param index - the current selected offset in the list
	 * @param scrollToSelection - indicates whether scrolling to selection should occur if the selection is outside of view
	 * @see getSelectedIndex()
	 */
	public void setSelectedIndex(int index, boolean scrollToSelection)
	{
		if (index < 0) {
			throw new IllegalArgumentException("Selection index is negative:" + index);
		}
		model.setSelectedIndex(index);
		if (scrollToSelection) {
			Dimension size = getElementSize(false);
			Rectangle rect;
			if (getOrientation() == VERTICAL) {
				rect = new Rectangle(getX(), (size.getHeight() + itemGap) * Math.max(0, index), size);
			} else {
				rect = new Rectangle((size.getWidth() + itemGap) * Math.max(0, index), getY(), size);
			}
			scrollRectToVisible(rect);
		}
	}

	/**
	 * Calculates the size of an element based on a forumla or on rendering prototype
	 */
	private Dimension calculateElementSize(boolean selected) {
		if (renderingPrototype != null) {
			Component unselected = renderer.getListCellRendererComponent(this, renderingPrototype, 0, selected);
			return unselected.getPreferredSize();
		}
		int width = 0;
		int height = 0;
		int elements = Math.min(5, model.getSize());
		int marginX = 0;
		int marginY = 0;
		for (int iter = 0; iter < elements; iter++) {
			Component cmp = renderer.getListCellRendererComponent(this, model.getItemAt(iter), iter, selected);
			Dimension d = cmp.getPreferredSize();
			width = Math.max(width, d.getWidth());
			height = Math.max(height, d.getHeight());
			if(iter == 0){
				Style s = cmp.getStyle();
				marginY = s.getMargin(TOP) + s.getMargin(BOTTOM);
				marginX = s.getMargin(LEFT) + s.getMargin(RIGHT);
			}
		}

 		// INFO:: estah causando problemas na renderizacao com scroll; + marginY);
		return new Dimension(width, height);
		// return new Dimension(width + marginX, height + marginY);
	}

	protected Dimension calcPreferredSize() {
		return UIManager.getInstance().getLookAndFeel().getListPreferredSize(this);
	}

	/**
	 * Calculates the default size for elements on the list
	 * 
	 * @return the default dimension for elements in a list
	 */
	Dimension getElementSize(boolean selected) {
		if (selected) {
			if (selectedElemSize == null) {
				// don't keep element size if there are no elements and no prototype...
				if (renderingPrototype == null) {
					if (model.getSize() == 0) {
						// put a sensible value as default when there are no elements or rendering prototype
						return new Label("XXXXXX").getPreferredSize();
					}
				}
				selectedElemSize = calculateElementSize(true);
			}
			return selectedElemSize;
		} else {
			if (elemSize == null) {
				// don't keep element size if there are no elements and no prototype...
				if (renderingPrototype == null) {
					if (model.getSize() == 0) {
						// put a sensible value as default when there are no elements or rendering prototype
						return new Label("XXXXXX").getPreferredSize();
					}
				}
				elemSize = calculateElementSize(false);
			}
			return elemSize;
		}
	}

	/**
	 * Returns the current selected item in the list or null for no selection.
	 *
	 * 
	 * 
	 * @return the current selected item in the list
	 * @see setSelectedItem(java.lang.Object)
	 */
	public Object getSelectedItem()
	{
		int idx = model.getSelectedIndex();
		if (idx < model.getSize()) {
			return model.getItemAt(idx);
		}
		return null;
	}

	/**
	 * Sets the current selected item in the list.
	 *
	 * 
	 * @param item - the current selected item in the list
	 * @see getSelectedItem()
	 */
	public void setSelectedItem( Object item)
	{
		int size = model.getSize();
		for (int iter = 0; iter < size; iter++) {
			if (model.getItemAt(iter) == item) {
				model.setSelectedIndex(iter);
				break;
			}
		}
	}

	/**
	 * Returns the model underlying the list.
	 *
	 * 
	 * 
	 * @return the model underlying the list
	 * @see setModel(com.sun.dtv.lwuit.list.ListModel)
	 */
	public ListModel getModel()
	{
		return model;
	}

	void dataChanged(int status, int index) {
		setShouldCalcPreferredSize(true);
		elemSize = null;
		selectedElemSize = null;
		if (getSelectedIndex() >= model.getSize()) {
			setSelectedIndex(Math.max(model.getSize() - 1, 0));
		}
		modelChanged(status, index);
		repaint();
	}

	private void bindListeners() {
		if (listener == null) {
			listener = new Listeners();
			model.addDataChangedListener(listener);
			model.addSelectionListener(listener);
		}
	}

	/**
	 * Replaces/sets the model underlying the list.
	 *
	 * 
	 * @param model - the new model underlying the list
	 * @see getModel()
	 */
	public void setModel(ListModel model)
	{
		if (this.model != null) {
			setShouldCalcPreferredSize(true);
			this.model.removeDataChangedListener(listener);
			this.model.removeSelectionListener(listener);
			this.model = model;
			listener = null;

			// when replacing a model on a scrolled list reset the scrolling if necessary
			if (getScrollDimension().getHeight() < getScrollY() + getHeight()) {
				setScrollY(0);
			}
			if (getScrollDimension().getWidth() < getScrollX() + getWidth()) {
				setScrollX(0);
			}
		}
		this.model = model;
		if (isInitialized()) {
			bindListeners();
		}
		repaint();
	}

	/**
	 * Indicate whether pressing the number keys should trigger an action.
	 *
	 * 
	 * 
	 * @return true if pressing the number keys should trigger an action, false otherwise
	 */
	public boolean isNumericKeyActions()
	{
		return numericKeyActions;
	}

	/**
	 * Indicate whether pressing the number keys should trigger an action.
	 *
	 * 
	 * @param numericKeyActions - true to trigger an action on number keys
	 */
	public void setNumericKeyActions(boolean numericKeyActions)
	{
		this.numericKeyActions = numericKeyActions;
	}

	/**
	 * Sets the renderer which is used to draw list elements.
	 *
	 * 
	 * @param renderer - cell renderer instance
	 */
	public void setListCellRenderer( ListCellRenderer renderer)
	{
		if (this.renderer != null) {
			//calculate the item list size and the list size.
			elemSize = null;
			selectedElemSize = null;
			setShouldCalcPreferredSize(true);
		}
		this.renderer = renderer;
	}

	/**
	 * Returns the renderer which is used to draw list elements.
	 *
	 * 
	 * 
	 * @return the renderer which is used to draw list elements
	 */
	public ListCellRenderer getRenderer()
	{
		return this.renderer;
	}

	/**
	 * Returns the list orientation.
	 *
	 * 
	 * 
	 * @return the list orientation HORIZONTAL or VERTICAL
	 * @see setOrientation(int),  HORIZONTAL, VERTICAL
	 */
	public int getOrientation()
	{
		return this.orientation;
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
		ListCellRenderer r = getRenderer();
		if (renderingPrototype != null) {
			r.getListCellRendererComponent(this, renderingPrototype, 0, false).refreshTheme();
		} else {
			if (getModel().getSize() > 0) {
				r.getListCellRendererComponent(this, getModel().getItemAt(0), 0, false).refreshTheme();
			} else {
				r.getListCellRendererComponent(this, null, 0, false).refreshTheme();
			}
		}
		Component focus = r.getListFocusComponent(this);
		if (focus != null) {
			focus.refreshTheme();
		}
		super.refreshTheme();
	}

	/**
	 * Sets the list orientation HORIZONTAL or VERTICAL.
	 *
	 * 
	 * @param orientation - the list orientation HORIZONTAL or VERTICAL
	 * @see getOrientation(),  HORIZONTAL, VERTICAL
	 */
	public void setOrientation(int orientation)
	{
		this.orientation = orientation;
	}

	/**
	 * Makes sure the selected index is visible if it is not in the current view
	 * rect the list will scroll so it fits within.
	 *
	 * 
	 * @param rect - the rectangle area to scroll to
	 */
	public void scrollRectToVisible( Rectangle rect)
	{
		if (fixedSelection <= FIXED_NONE_CYCLIC) {
			//Dimension elemSize = getElementSize();
			if (orientation == VERTICAL) {
				super.scrollRectToVisible(new Rectangle(getScrollX(), rect.getY(), rect.getSize().getWidth(), rect.getSize().getHeight() + itemGap), this);
			} else {
				super.scrollRectToVisible(new Rectangle(rect.getX(), getScrollY(), rect.getSize().getWidth() + itemGap, rect.getSize().getHeight()), this);
			}
		}
	}

	void setHandlesInputParent(boolean b) {
		super.setHandlesInput(b);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#setHandlesInput(boolean)">Component</A></CODE></B></DD>
	 * Prevents key events from being grabbed for focus traversal. E.g. a list component
	 * might use the arrow keys for internal navigation so it will switch this flag to
	 * true in order to prevent the focus manager from moving to the next component.
	 *
	 * 
	 * @param b - indicates whether key events can be grabbed for focus traversal
	 * @see setHandlesInput in class Component
	 */
	public void setHandlesInput(boolean b)
	{
		Form f = getComponentForm();
		if (f != null) {
			// prevent the list from losing focus if its the only element
			// or when the user presses fire and there is no other component
			super.setHandlesInput(b || f.isSingleFocusMode());
		} else {
			super.setHandlesInput(b);
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#keyReleased(int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the key released event
	 * will call this method.
	 *
	 * 
	 * @param keyCode - the key code value to indicate a physical key.
	 * @see keyReleased in class Component
	 */
	public void keyReleased(int keyCode)
	{
		/*
		// other events are in keyReleased to prevent the next event from reaching the next form
		int gameAction = Display.getInstance().getGameAction(keyCode);
		if (gameAction == Display.GAME_FIRE) {
			boolean h = handlesInput();
			setHandlesInput(!h);
			if (h) {
				fireActionEvent();
			}
			repaint();
			return;
		}

		if (numericKeyActions && gameAction != Display.GAME_LEFT &&
				gameAction != Display.GAME_RIGHT && gameAction != Display.GAME_UP &&
				gameAction != Display.GAME_DOWN) {
			if (keyCode >= '1' && keyCode <= '9') {
				int offset = keyCode - '1';
				if (offset < getModel().getSize()) {
					setSelectedIndex(offset);
					fireActionEvent();
				}
			}
		}
		*/
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#keyPressed(int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the key pressed event
	 * will call this method.
	 *
	 * 
	 * @param keyCode - the key code value to indicate a physical key.
	 * @see keyPressed in class Component
	 */
	public void keyPressed(int keyCode)
	{
		// scrolling events are in keyPressed to provide immediate feedback
		if (!handlesInput()) {
			return;
		}

		int up, down;
		if(orientation == VERTICAL){
			up = RemoteControlEvent.VK_UP;
			down = RemoteControlEvent.VK_DOWN;
			if (keyCode == RemoteControlEvent.VK_RIGHT || keyCode == RemoteControlEvent.VK_LEFT){
				setHandlesInput(false);
			}

		} else {
			up = RemoteControlEvent.VK_LEFT;
			down = RemoteControlEvent.VK_RIGHT;
			if (keyCode == RemoteControlEvent.VK_UP || keyCode == RemoteControlEvent.VK_DOWN){
				setHandlesInput(false);
			}
		}
		int selectedIndex = model.getSelectedIndex();
		if (keyCode == up) {
			selectedIndex--;
			if (selectedIndex < 0) {
				if (fixedSelection != FIXED_NONE && fixedSelection != FIXED_NONE_ONE_ELEMENT_MARGIN_FROM_EDGE) {
					selectedIndex = size() - 1;
				} else {
					selectedIndex = 0;
				}
			}
		} else if (keyCode == down) {
			selectedIndex++;
			if (selectedIndex >= size()) {
				if (fixedSelection != FIXED_NONE && fixedSelection != FIXED_NONE_ONE_ELEMENT_MARGIN_FROM_EDGE) {
					selectedIndex = 0;
				} else {
					selectedIndex = size() - 1;
				}
			}
		} else if (keyCode == RemoteControlEvent.VK_CONFIRM){
			fireActionEvent();
		} else if(numericKeyActions){
			if(keyCode >= '1' && keyCode <= '9'){
				int off = keyCode - '1';
				if (off < getModel().getSize()) {
					setSelectedIndex(off);
					fireActionEvent();
					repaint();
					return;
				}
			}
		}

		if (selectedIndex != model.getSelectedIndex()) {
			model.setSelectedIndex(selectedIndex);
			updateAnimationPosition(keyCode == RemoteControlEvent.VK_DOWN ? 1 : -1);
			if (fixedSelection == FIXED_NONE || fixedSelection == FIXED_NONE_CYCLIC) {
				selectElement(selectedIndex);
			}
			if (fixedSelection == FIXED_NONE_ONE_ELEMENT_MARGIN_FROM_EDGE) {
				// are we going down?
				if (keyCode == RemoteControlEvent.VK_DOWN) {
					selectElement(Math.min(selectedIndex + 1, getModel().getSize() - 1));
				} else {
					selectElement(Math.max(selectedIndex - 1, 0));
				}
			}
		}
		repaint();
	}

	private void selectElement(int selectedIndex) {
		Dimension size = getElementSize(false);
		Rectangle rect;
		if (getOrientation() != HORIZONTAL) {
			rect = new Rectangle(getX(), (size.getHeight() + itemGap) * selectedIndex, getElementSize(true));
		} else {
			int x = (size.getWidth() + itemGap) * selectedIndex;
			if(isScrollableX()){
				x = getScrollDimension().getWidth() - x - (size.getWidth() + itemGap);
			}
			rect = new Rectangle(x, getY(), getElementSize(true));
		}
		scrollRectToVisible(rect);
	}

	private void paintFocus(Graphics g, int width, Rectangle pos, Dimension rendererSize) 
	{
		calculateComponentPosition(getSelectedIndex(), width, pos, rendererSize, getElementSize(true), true);
		Dimension size = pos.getSize();

		Component cmp = renderer.getListFocusComponent(this);
		if (cmp != null) {
			cmp.setCellRenderer(true);
			if(this.hasFocus()){
				cmp.getStyle().setBorder(selectionFocusBorder);
			}
			int x = pos.getX();
			int y = pos.getY();

			// prevent focus animation from working during a drag operation
			// prevent focus animation from working during a drag operation
            if (orientation != HORIZONTAL) {
                y -= (animationPosition);
            } else {
                x -= (animationPosition);
            }
			renderComponent(g, cmp, x, y, size.getWidth(), size.getHeight());
			cmp.getStyle().setBorder(null);
		}

	}

	/**
	 * Renders the current component on the screen
	 */
	private void renderComponent(Graphics g, Component cmp, int x, int y, int width, int height) {
		Style s = cmp.getStyle();
		int left = s.getMargin(LEFT);
		int top =  s.getMargin(TOP);
		cmp.setWidth(width - left - s.getMargin(RIGHT));
		cmp.setHeight(height - top - s.getMargin(BOTTOM));
		cmp.setX(x + left);
		cmp.setY(y + top);
		cmp.paintInternal(g);
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
	 * @see paint in class Component
	 */
	public void paint( Graphics g)
	{
		UIManager.getInstance().getLookAndFeel().drawList(g, this);

		Style style = getStyle();
		int width = getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT) - getSideGap();
		if (isScrollableX()) {
			width = Math.max(width, getScrollDimension().getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT) - getSideGap());
		}
		int numOfcomponents = model.getSize();
		if (numOfcomponents == 0) {
			return;
		}
		int xTranslate = getX();
		int yTranslate = getY();
		g.translate(xTranslate, yTranslate);
		Rectangle pos = new Rectangle();
		Dimension rendererSize = getElementSize(false);

		int selection = model.getSelectedIndex();

		if (animationPosition != 0 && fixedSelection >= FIXED_NONE_CYCLIC) {
			// selection should never move during animation of fixed elements
			selection = -1;

			if (orientation == VERTICAL) {
				yTranslate += animationPosition;
				g.translate(0, animationPosition);
			} else {
				xTranslate += animationPosition;
				g.translate(animationPosition, 0);
			}
		}

		int clipX = g.getClipX();
		int clipY = g.getClipY();
		int clipWidth = g.getClipWidth();
		int clipHeight = g.getClipHeight();

		//System.out.println("ClipX: " + g.getClipX() + " ClipY: " + g.getClipY() + " ClipWidth: " + g.getClipWidth() + " ClipHeight: " + g.getClipHeight());

		if (paintFocusBehindList) {
			paintFocus(g, width, pos, rendererSize);
		}

		//System.out.println("ClipX: " + g.getClipX() + " ClipY: " + g.getClipY() + " ClipWidth: " + g.getClipWidth() + " ClipHeight: " + g.getClipHeight());
		// this flag is for preformance improvements
		// if we figured out that the list items are not visible anymore
		// we should break from the List loop
		boolean shouldBreak = false;

		// improve performance for browsing the end of a very large list
		int startingPoint = 0;
		if (fixedSelection <= FIXED_NONE_CYCLIC) {
			startingPoint = Math.max(0, pointerSelect(clipX + getAbsoluteX(), clipY + getAbsoluteY()) - 1);
		}

		for (int i = startingPoint; i < numOfcomponents; i++) {
			// skip on the selected
			if (i == getSelectedIndex() && animationPosition == 0) {
				continue;
			}
			calculateComponentPosition(i, width, pos, rendererSize, getElementSize(true), i <= getSelectedIndex());

			// if the renderer is in the clipping region
			if (pos.intersects(clipX, clipY, clipWidth, clipHeight)) {
				Object value = model.getItemAt(i);
				Component cmp = renderer.getListCellRendererComponent(this, value, i, false);
				cmp.setCellRenderer(true);
				Dimension size = pos.getSize();
				renderComponent(g, cmp, pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
				//System.out.println("PosX: " + pos.getX() + " PosY: " + pos.getY());
		//System.out.println("ClipX: " + g.getClipX() + " ClipY: " + g.getClipY() + " ClipWidth: " + g.getClipWidth() + " ClipHeight: " + g.getClipHeight());
				shouldBreak = true;
			} else {
				//this is relevant only if the List is not fixed.
				if (shouldBreak && (fixedSelection <= FIXED_NONE_CYCLIC)) {
					break;
				}
			}
		}
		if (!paintFocusBehindList) {
			paintFocus(g, width, pos, rendererSize);
		//System.out.println("ClipX: " + g.getClipX() + " ClipY: " + g.getClipY() + " ClipWidth: " + g.getClipWidth() + " ClipHeight: " + g.getClipHeight());
		} else {
			calculateComponentPosition(getSelectedIndex(), width, pos, rendererSize, getElementSize(true), true);
		}
		Dimension size = pos.getSize();
		//if the animation has finished draw the selected element
		if (animationPosition == 0 && model.getSize() > 0) {
			Component selected = renderer.getListCellRendererComponent(this, model.getItemAt(selection), selection, true);
			renderComponent(g, selected, pos.getX(), pos.getY(), size.getWidth(), size.getHeight());
			//System.out.println("Selected: PosX: " + pos.getX() + " PosY: " + pos.getY());
		//System.out.println("ClipX: " + g.getClipX() + " ClipY: " + g.getClipY() + " ClipWidth: " + g.getClipWidth() + " ClipHeight: " + g.getClipHeight());
		}

		g.translate(-xTranslate, -yTranslate);
	}

	/**
	 * Invoked to indicate interest in future selection events.
	 *
	 * 
	 * @param l - the selection listener to be added
	 * @see removeSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)
	 */
	public void addSelectionListener( SelectionListener l)
	{
		model.addSelectionListener(l);
	}

	/**
	 * Invoked to indicate no further interest in future selection events.
	 *
	 * 
	 * @param l - the selection listener to be removed
	 * @see addSelectionListener(com.sun.dtv.lwuit.events.SelectionListener)
	 */
	public void removeSelectionListener(SelectionListener l)
	{
		model.removeSelectionListener(l);
	}

	/**
	 * Allows binding a listener to user selection actions.
	 *
	 * 
	 * @param l - the action listener to be added
	 * @see removeActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void addActionListener(ActionListener l)
	{
		if (actionListeners.contains((Object)l) == false) {
			actionListeners.add((Object)l);
		}
	}

	/**
	 * Allows binding a listener to user selection actions.
	 *
	 * 
	 * @param l - the action listener to be removed
	 * @see addActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void removeActionListener(ActionListener l)
	{
		if (actionListeners.contains((Object)l) == true) {
			actionListeners.remove((Object)l);
		}
	}

	/**
	 * A list can start handling input implicitly upon gaining focus, this can
	 * make for a more intuitive UI when no other focus elements exist or when
	 * their use case is infrequent. However, it might be odd in some cases
	 * where the list "steals" focus.
	 *
	 * 
	 * @param inputOnFocus - true is a list can start handling input  implicitly upon gaining focus
	 */
	public void setInputOnFocus(boolean inputOnFocus)
	{
		this.inputOnFocus = inputOnFocus;
	}

	/**
	 * This method determines if the animated focus is drawn on top of the List
	 * or behind the List when moving.
	 *
	 * 
	 * @param paintFocusBehindList - true for behind, false for on top
	 */
	public void setPaintFocusBehindList(boolean paintFocusBehindList)
	{
		this.paintFocusBehindList = paintFocusBehindList;
	}

	/**
	 * @inheritDoc
	 *
	 */
	void focusGainedInternal() {
		super.focusGainedInternal();
		if (inputOnFocus) {
			setHandlesInput(true);
		}
	}

	/**
	 * @inheritDoc
	 *
	 */
	void focusLostInternal() {
		super.focusLostInternal();
	}

	
	/**
	 * Returns the gap between items.
	 *
	 * 
	 * 
	 * @return the gap between items
	 * @see setItemGap(int)
	 */
	public int getItemGap()
	{
		return this.itemGap;
	}

	/**
	 * Set the gap between items.
	 *
	 * 
	 * @param itemGap - the gap between items
	 * @see getItemGap()
	 */
	public void setItemGap(int itemGap)
	{
		this.itemGap = itemGap;
	}

	/**
	 * The rendering prototype is optionally used in calculating the size of the
	 * List and is recommended for performance reasons. You should invoke it with an object
	 * representing a theoretical value in the list which will be used to calculate
	 * the size required for each element in the list.
	 *This allows list size calculations to work across look and feels and allows
	 * developers to predetermine size for list elements.
	 *e.g. For a list of Strings which you would like to always be 5 characters wide
	 * you can use a prototype "XXXXX" which would use the preferred size of the XXXXX
	 * String to determine the size of the list element. E.g. for a list of dates you can use
	 * new Date(30, 12, 00) etc..
	 *
	 * 
	 * @param renderingPrototype - a value that can be passed to the renderer to indicate the preferred size of a list component.
	 * @see getRenderingPrototype()
	 */
	public void setRenderingPrototype( Object renderingPrototype)
	{
		this.renderingPrototype = renderingPrototype;
	}

	/**
	 * See set rendering prototype.
	 *
	 * 
	 * 
	 * @return the value of the rendering prototype
	 * @see setRenderingPrototype(java.lang.Object)
	 */
	public Object getRenderingPrototype()
	{
		return this.renderingPrototype;
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerDragged(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer dragged event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerDragged in class Component
	 */
	public void pointerDragged(int x, int y)
	{
		if (isSmoothScrolling()) {
			if(fixedSelection <= FIXED_NONE_CYCLIC) {
				super.pointerDragged(x, y);
			} else {
				if(getModel().getSize() > 2) {
					// a fixed list is not scrollable we need to implement dragging ourselves...
					int offset;
					int componentSize;

					if(orientation == VERTICAL) {
						offset = y - initialFixedDrag;
						componentSize = getElementSize(false).getHeight();
					} else {
						offset = x - initialFixedDrag;
						componentSize = getElementSize(false).getWidth();
					}


					int elementSelection = initialFixedSelection + (offset / componentSize) % getModel().getSize();
					if(elementSelection < 0) {
						elementSelection += getModel().getSize();
					}
					getModel().setSelectedIndex(elementSelection);
				}


				int numOfcomponents = getModel().getSize();
				Style style = getStyle();
				int width = getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT) - getSideGap();
				if (isScrollableX()) {
					width = Math.max(width, getScrollDimension().getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT) - getSideGap());
				}
				Dimension rendererSize = getElementSize(false);
				Dimension selectedSize = getElementSize(true);
				Rectangle pos = new Rectangle();
				for (int i = 0; i < numOfcomponents; i++) {
					calculateComponentPosition(i, width, pos, rendererSize, selectedSize, true);
					if (pos.contains(x, y)) {
						model.setSelectedIndex(i);
						break;
					}
				}
			}
		} else {
			model.setSelectedIndex(pointerSelect(x, y));
		}
	}

	/**
	 * Allows us to recalculate the bounds of a coordinate to make it "loop" back
	 * into view
	 * 
	 * @param offset either x or y coordinate
	 * @param totalSize the total width or height of the list with all the elements (including scroll)
	 * @param viewSize the size visible to the user
	 * @param componentSize the size of the component
	 * @return offset after manipulation if such manipulation was performed
	 */
	private int recalcOffset(int offset, int totalSize, int viewSize, int componentSize) {
		if (offset + animationPosition % componentSize > viewSize) {
			offset -= totalSize;
		} else {
			if (offset + animationPosition % componentSize < 1 - componentSize) {
				offset += totalSize;
			}
		}
		return offset;
	}

	/**
	 * Calculates the desired bounds for the component and returns them within the
	 * given rectangle.
	 */
	private void calculateComponentPosition(int index, int defaultWidth, Rectangle rect, Dimension rendererSize, Dimension selectedSize, boolean beforeSelected) {
		Style style = getStyle();
		int initialY = style.getPadding(TOP);
		int initialX = style.getPadding(LEFT);
		int selection = getSelectedIndex();
		Dimension d = rect.getSize();
		int selectedDiff;

		// the algorithm illustrated here is very simple despite the "mess" of code...
		// The idea is that if we have a "fixed" element we just add up the amount of pixels
		// to get it into its place in the screen (nothing for top obviously). 
		// In order to cause the list to be cyclic we just subtract the list size
		// which will cause the bottom elements to "return" from the top.
		if (orientation == VERTICAL) {
			int height = rendererSize.getHeight();
			selectedDiff = selectedSize.getHeight() - height;
			rect.setX(initialX);
			d.setHeight(height);
			d.setWidth(defaultWidth);
			int y = 0;
			int listHeight = getHeight() - style.getPadding(TOP) - style.getPadding(BOTTOM);
			int totalHeight = (height + itemGap) * getModel().getSize() + selectedDiff;
			switch (fixedSelection) {
				case FIXED_CENTER:
					y = listHeight / 2 - (height + itemGap + selectedDiff) / 2 +
						(index - selection) * (height + itemGap);
					if (!beforeSelected) {
						y += selectedDiff;
					}
					y = recalcOffset(y, totalHeight, listHeight, height + itemGap);
					break;
				case FIXED_TRAIL:
					y = listHeight - (height + itemGap + selectedDiff);
				case FIXED_LEAD:
					y += (index - selection) * (height + itemGap);
					if (index - selection > 0) {
						y += selectedDiff;
					}
					y = recalcOffset(y, totalHeight, listHeight, height + itemGap);
					break;
				default:
					y = index * (height + itemGap);
					if (!beforeSelected) {
						y += selectedDiff;
					}
					break;
			}
			rect.setY(y + initialY);
			if (index == selection) {
				d.setHeight(d.getHeight() + selectedDiff);
			}

		} else {
			int width = rendererSize.getWidth();
			selectedDiff = selectedSize.getWidth() - width;
			rect.setY(initialY);
			d.setHeight(getHeight() - style.getPadding(TOP) - style.getPadding(BOTTOM));
			d.setWidth(width);
			int x = 0;
			int listWidth = getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT);
			int totalWidth = (width + itemGap) * getModel().getSize() + selectedDiff;
			switch (fixedSelection) {
				case FIXED_CENTER:
					x = listWidth / 2 - (width + itemGap + selectedDiff) / 2 +
						(index - selection) * (width + itemGap);
					if (!beforeSelected) {
						x += selectedDiff;
					}
					x = recalcOffset(x, totalWidth, listWidth, width + itemGap);
					break;
				case FIXED_TRAIL:
					x = listWidth - (width + itemGap + selectedDiff);
				case FIXED_LEAD:
					x += (index - selection) * (width + itemGap);
					if (index - selection > 0) {
						x += selectedDiff;
					}
					x = recalcOffset(x, totalWidth, listWidth, width + itemGap);
					break;
				default:
					x = index * (width + itemGap);
					if (!beforeSelected) {
						x += selectedDiff;
					}
					break;
			}
			rect.setX(initialX + x);
			if (index == selection) {
				d.setWidth(d.getWidth() + selectedDiff);
			}
		}
	}

	private int pointerSelect(int x, int y) {
		int selectedIndex = -1;
		int numOfcomponents = getModel().getSize();
		Style style = getStyle();

		Dimension rendererSize = getElementSize(false);
		Dimension selectedSize = getElementSize(true);

		Rectangle pos = new Rectangle();
		int width = getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT) - getSideGap();
		if (isScrollableX()) {
			width = Math.max(width, getScrollDimension().getWidth() - style.getPadding(RIGHT) - style.getPadding(LEFT) - getSideGap());
		}
		y = y - getAbsoluteY();
		x = x - getAbsoluteX();

		if (fixedSelection <= FIXED_NONE_CYCLIC) {
			calculateComponentPosition(getSelectedIndex(), width, pos, rendererSize, getElementSize(true), true);

			if (orientation == VERTICAL) {
				if(y < pos.getY()){
					selectedIndex = y / (rendererSize.getHeight() + itemGap);
				}else{
					int current = getSelectedIndex();
					if(y < pos.getY() + selectedSize.getHeight()){
						selectedIndex = current;
					}else{
						selectedIndex = (current+1) + (y - (pos.getY() + selectedSize.getHeight()))/(rendererSize.getHeight() + itemGap);
					}
				}                
			} else {
				if(x < pos.getX()){
					selectedIndex = x / (rendererSize.getWidth() + itemGap);
				}else{
					int current = getSelectedIndex();
					if(x < pos.getX() + selectedSize.getWidth()){
						selectedIndex = current;
					}else{
						selectedIndex = (current+1) + (x - (pos.getX() + selectedSize.getWidth()))/(rendererSize.getWidth() + itemGap);
					}
				}                

			}
		} else {
			for (int i = 0; i < numOfcomponents; i++) {
				calculateComponentPosition(i, width, pos, rendererSize, selectedSize, true);
				if (pos.contains(x, y)) {
					selectedIndex = i;
					break;
				}
			}
		}

		if (selectedIndex < 0 || selectedIndex >= size()) {
			return -1;
		}
		return selectedIndex;
	}

	/**
	 * @inheritDoc
	 *
	 */
	protected void fireActionEvent() {
		if(isEnabled()){
			for (Iterator i=actionListeners.iterator(); i.hasNext(); ) {
				ActionListener listener = (ActionListener)i.next();
				listener.actionPerformed(new ActionEvent(this));
			}
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerReleased(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer released event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerReleased in class Component
	 */
	public void pointerReleased(int x, int y)
	{
		if (isDragActivated()) {
			super.pointerReleased(x, y);
			return;
		}
		int current = model.getSelectedIndex();
		int selection = pointerSelect(x, y);
		if (selection > -1) {
			model.setSelectedIndex(selection);
			/*
			if(fireOnClick){
				fireActionEvent();
			}else
			*/{
				if (current == selection) {
					fireActionEvent();
				}
			}
		}
	}

	/**
	 * Allows adding an element to a list if the underlying model supports this, notice that
	 * it is an optional operation and if the model does not support it (default list model does)
	 * then this operation may failed.
	 *
	 * 
	 * @param item - the item to be added to a list model
	 */
	public void addItem(Object item)
	{
		model.addItem(item);
	}
        
	/**
	 * Indicates whether selection is fixable to place in which case all the
	 * elements in the list move and selection stays in place.
	 *
	 * 
	 * 
	 * @return one of: FIXED_NONE, FIXED_TRAIL, FIXED_LEAD, FIXED_CENTER, FIXED_NONE_CYCLIC
	 * @see setFixedSelection(int)
	 */
	public int getFixedSelection()
	{
		return this.fixedSelection;
	}

	/**
	 * Indicates whether selection is fixable to place in which case all the
	 * elements in the list move and selection stays in place.
	 *
	 * 
	 * @param fixedSelection - one of: FIXED_NONE, FIXED_TRAIL, FIXED_LEAD,  FIXED_CENTER, FIXED_NONE_CYCLIC
	 * @see getFixedSelection()
	 */
	public void setFixedSelection(int fixedSelection)
	{
		this.fixedSelection = fixedSelection;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/lwuit/animations/Animation.html#animate()">Animation</A></CODE></B></DD>
	 * Allows the animation to reduce "repaint" calls when it returns false. It is
	 * called once for every frame.
	 *
	 * 
	 * @return true if a repaint is desired or false if no repaint is necessary
	 * @see animate in interface Animation
	 * @see animate in class Component
	 */
	public boolean animate()
	{
		// parent is performing the animation we shouldn't do anything in this case
		// this is the scrolling animation which we don't want to interfear with
		boolean parentFinished = super.animate();
		if (animationPosition != 0) {
			if (animationPosition < 0) {
				animationPosition = Math.min(listMotion.getValue() - destination, 0);
			} else {
				animationPosition = Math.max(destination - listMotion.getValue(), 0);
			}
			return true;
		}
		return parentFinished;
	}

	/**
	 * Setting the surrounding border gap.
	 *
	 * 
	 * @param borderGap - number of pixels for the gap
	 * @see getBorderGap()
	 */
	public void setBorderGap(int borderGap)
	{
		this.borderGap = borderGap;
	}

	/**
	 * Getting the surrounding border gap.
	 *
	 * 
	 * 
	 * @return border gap in pixels
	 * @see setBorderGap(int)
	 */
	public int getBorderGap()
	{
		return this.borderGap;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setTextContent(java.lang.String, int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Assigns text content to this component which is state-dependent.
	 *
	 * 
	 * @param text - the text content, or null (removes text content which had been assigned before)
	 * @param state - the state of the component for which this content should be displayed.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see setTextContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getTextContent(int)
	 */
	public void setTextContent(String text, int state) throws IllegalArgumentException
	{
		if(state == ViewOnlyComponent.STATE_ENABLED){
			this.enabledText = text;
		} else if(state == ViewOnlyComponent.STATE_DISABLED){
			this.disabledText = text;
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setGraphicContent(com.sun.dtv.lwuit.Image, int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Assigns graphical content to this component which is state-dependent.
	 *
	 * 
	 * @param image - the graphical content, or null (removes graphical content which had been assigned before)
	 * @param state - the state of the component for which this content should be displayed.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see setGraphicContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getGraphicContent(int)
	 */
	public void setGraphicContent(Image image, int state) throws IllegalArgumentException
	{
		if(state == ViewOnlyComponent.STATE_ENABLED){
			this.enabledGraphicContent = image;
		} else if(state == ViewOnlyComponent.STATE_DISABLED){
			this.disabledGraphicContent = image;
		} else{
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setAnimateContent(com.sun.dtv.lwuit.Image[], int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Assigns an array of graphical content to be used for animation to this
	 * component which is state-dependent.
	 *
	 * 
	 * @param images - the graphical content array, or null (removes any graphical content which had been assigned before)
	 * @param state - the state of the component for which this content should be displayed.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see setAnimateContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getAnimateContent(int)
	 */
	public void setAnimateContent(Image[] images, int state) throws IllegalArgumentException
	{
		//TODO implement setAnimateContent
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getTextContent(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Returns the text content for this component, depending on the current
	 * state.
	 *
	 * 
	 * @param state - The state for which content is to be retrieved.
	 * @return The text content associated with the specified state, or null if there is none.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see getTextContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setTextContent(java.lang.String, int)
	 */
	public String getTextContent(int state) throws IllegalArgumentException
	{
		if(state == ViewOnlyComponent.STATE_ENABLED){
			return enabledText;
		} else if(state == ViewOnlyComponent.STATE_DISABLED){
			return disabledText;
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getGraphicContent(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Returns the graphical content for this component, depending on the
	 * current state.
	 *
	 * 
	 * @param state - The state for which content is to be retrieved.
	 * @return The graphical content associated with the specified state, or null if there is none.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see getGraphicContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setGraphicContent(com.sun.dtv.lwuit.Image, int)
	 */
	public Image getGraphicContent(int state) throws IllegalArgumentException
	{
		if(state == ViewOnlyComponent.STATE_ENABLED){
			return enabledGraphicContent;
		} else if(state == ViewOnlyComponent.STATE_DISABLED){
			return disabledGraphicContent;
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getAnimateContent(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Returns the animated content for this component, depending on the
	 * current state.
	 *
	 * 
	 * @param state - The state for which content is to be retrieved.
	 * @return The graphical content associated with the specified state, or null if there is none.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see getAnimateContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setAnimateContent(com.sun.dtv.lwuit.Image[], int)
	 */
	public Image[] getAnimateContent(int state) throws IllegalArgumentException
	{
		return null;
		//TODO implement getAnimateContent
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setInteractionState(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Set the current state of the component with respect to interaction.
	 *
	 * 
	 * @param state - the interaction state for this component.
	 * @throws IllegalArgumentException - if the state parameter does not represent a state
	 * @see setInteractionState in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getInteractionState()
	 */
	public void setInteractionState(int state) throws IllegalArgumentException
	{
		if(state != ViewOnlyComponent.STATE_ENABLED && state != ViewOnlyComponent.STATE_DISABLED){
			throw new IllegalArgumentException("Invalid state");
		}
		this.interactionState = state;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getInteractionState()">ViewOnlyComponent</A></CODE></B></DD>
	 * Return the current interaction state of the component.
	 *
	 * 
	 * @return the current interaction state of the component.
	 * @see getInteractionState in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setInteractionState(int)
	 */
	public int getInteractionState()
	{
		return this.interactionState;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setTextLayoutManager(com.sun.dtv.ui.TextLayoutManager)">ViewOnlyComponent</A></CODE></B></DD>
	 * Sets the text layout manager that should be used for text layout
	 * in this component.
	 *
	 * 
	 * @param manager - the TextLayoutManager
	 * @see setTextLayoutManager in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getTextLayoutManager()
	 */
	public void setTextLayoutManager(TextLayoutManager manager)
	{
		this.textLayoutManager = manager;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getTextLayoutManager()">ViewOnlyComponent</A></CODE></B></DD>
	 * Gets the text layout manager that is currently in use for text layout
	 * in this component.
	 *
	 * 
	 * @return the TextLayoutManager
	 * @see getTextLayoutManager in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setTextLayoutManager(com.sun.dtv.ui.TextLayoutManager)
	 */
	public TextLayoutManager getTextLayoutManager()
	{
		return this.textLayoutManager;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setHorizontalAlignment(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Set the horizontal alignment for any state-based content in this
	 * component.
	 *
	 * 
	 * @param alignment - the horizontal alignment mode, one of ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT, ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER, ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT or ViewOnlyComponent.HORIZONTAL_ALIGN_JUSTIFIED.
	 * @see setHorizontalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getHorizontalAlignment()
	 */
	public void setHorizontalAlignment(int alignment)
	{
		if (alignment != HORIZONTAL_ALIGN_LEFT && alignment != HORIZONTAL_ALIGN_CENTER 
		    && alignment != HORIZONTAL_ALIGN_RIGHT && alignment != HORIZONTAL_ALIGN_JUSTIFIED) 
		{
			throw new IllegalArgumentException("Alignment can't be set to "
					+ alignment);
		}
		this.horizontalAlignment = alignment;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getHorizontalAlignment()">ViewOnlyComponent</A></CODE></B></DD>
	 * Retrieve the horizontal alignment for any state-based content in this
	 * component.
	 *
	 * 
	 * @return the current horizontal alignment mode, one of ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT, ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER, ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT or ViewOnlyComponent.HORIZONTAL_ALIGN_JUSTIFIED.
	 * @see getHorizontalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setHorizontalAlignment(int)
	 */
	public int getHorizontalAlignment()
	{
		return this.horizontalAlignment;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setScalingMode(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Set the scaling mode for this component.
	 *
	 * 
	 * @param scaling - the scaling mode, one of ViewOnlyComponent.SCALE_NO, ViewOnlyComponent.SCALE_NO_ASPECT_PROOF or ViewOnlyComponent.SCALE_ASPECT_PROOF.
	 * @see setScalingMode in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getScalingMode()
	 */
	public void setScalingMode(int scaling)
	{
		this.scalingMode = scaling;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getScalingMode()">ViewOnlyComponent</A></CODE></B></DD>
	 * Retrieve the scaling mode for this component.
	 *
	 * 
	 * @return the current scaling mode, one of ViewOnlyComponent.SCALE_NO, ViewOnlyComponent.SCALE_NO_ASPECT_PROOF or ViewOnlyComponent.SCALE_ASPECT_PROOF.
	 * @see getScalingMode in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setScalingMode(int)
	 */
	public int getScalingMode()
	{
		return this.scalingMode;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#isDoubleBuffered()">ViewOnlyComponent</A></CODE></B></DD>
	 * Returns true if double buffering is available and activated.
	 *
	 * 
	 * @return true if double buffering is available and activated, and false otherwise
	 * @see isDoubleBuffered in interface ViewOnlyComponent
	 */
	public boolean isDoubleBuffered()
	{
		return false;
		//TODO implement isDoubleBuffered
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#isOpaque()">ViewOnlyComponent</A></CODE></B></DD>
	 * Returns true if the whole area of the Component (as returned by the
	 * <code>com.sun.dtv.lwuit.Component#getBounds</code> method, is opaque.
	 *
	 * 
	 * @return true if all the pixels within the area defined by the com.sun.dtv.lwuit.Component#getBounds method are opaque, i.e. all pixels are painted in an opaque color, false otherwise.
	 * @see isOpaque in interface ViewOnlyComponent
	 */
	public boolean isOpaque()
	{
		return false;
		//TODO implement isOpaque
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#processEvent(java.awt.AWTEvent)">ViewOnlyComponent</A></CODE></B></DD>
	 * Handle the specified AWTEvent.
	 *
	 * 
	 * @param event - a java.awt.AWTEvent to handle.
	 * @see processEvent in interface ViewOnlyComponent
	 */
	public void processEvent( AWTEvent event)
	{
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
	public void setMatte(Matte matte) throws MatteException
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

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setVerticalAlignment(int)">ViewOnlyComponent</A></CODE></B></DD>
	 * Set the vertical alignment for any state-based content in this
	 * component.
	 *
	 * 
	 * @param valign - the vertical alignment mode, one of ViewOnlyComponent.VERTICAL_ALIGN_TOP, ViewOnlyComponent.VERTICAL_ALIGN_CENTER, ViewOnlyComponent.VERTICAL_ALIGN_BOTTOM or ViewOnlyComponent.VERTICAL_ALIGN_JUSTIFIED.
	 * @see setVerticalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getVerticalAlignment()
	 */
	public void setVerticalAlignment(int valign)
	{
		if (valign != VERTICAL_ALIGN_CENTER && valign != VERTICAL_ALIGN_TOP && valign != VERTICAL_ALIGN_BOTTOM) {
			throw new IllegalArgumentException("Alignment can't be set to "
					+ valign);
		}
		this.verticalAlignment = valign;
	}

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getVerticalAlignment()">ViewOnlyComponent</A></CODE></B></DD>
	 * Retrieve the vertical alignment for any state-based content in this
	 * component.
	 *
	 * 
	 * @return the current horizontal alignment mode, one of ViewOnlyComponent.VERTICAL_ALIGN_TOP, ViewOnlyComponent.VERTICAL_ALIGN_CENTER, ViewOnlyComponent.VERTICAL_ALIGN_BOTTOM or ViewOnlyComponent.VERTICAL_ALIGN_JUSTIFIED.
	 * @see getVerticalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setVerticalAlignment(int)
	 */
	public int getVerticalAlignment()
	{
		return this.verticalAlignment;
	}

	private class Listeners implements DataChangedListener, SelectionListener {

		public void dataChanged(int status, int index) {
			List.this.dataChanged(status, index);
		}

		public void selectionChanged(int oldSelected, int newSelected) {
			repaint();
		}
	}

}
