package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.animations.Transition;
import com.sun.dtv.lwuit.animations.CommonTransitions;

import com.sun.dtv.lwuit.events.StyleListener;
import com.sun.dtv.lwuit.events.ActionListener;

import com.sun.dtv.lwuit.layouts.Layout;

import com.sun.dtv.lwuit.list.ListCellRenderer;

import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.plaf.LookAndFeel;

import com.sun.dtv.lwuit.events.ActionEvent;

import com.sun.dtv.lwuit.layouts.BorderLayout;

import com.sun.dtv.lwuit.geom.*;

import com.sun.dtv.resources.ScarceResource;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;

import java.awt.Color;

import java.util.*;

import com.sun.dtv.ui.*;
import com.sun.dtv.ui.event.RemoteControlEvent;
import com.sun.dtv.ui.ViewOnlyComponent;

public class Form extends Container implements StyleListener, MatteEnabled {
	private Container contentPane = new Container(new BorderLayout());

	protected boolean focusScrolling;

	private Painter glassPane;
	private Label title;
	protected MenuBar menuBar = new MenuBar();
	private Command selectCommand;
	private Command defaultCommand;
	private Component dragged;
	private Popup current;

	/**
	 * Indicates the command that is defined as the back command out of this
	 * form. A back command can be used both to map to a hardware button (e.g.
	 * on the Sony Ericsson devices) and by elements such as transitions etc. to
	 * change the behavior based on direction (e.g. slide to the left to enter
	 * screen and slide to the right to exit with back).
	 */
	private Command backCommand;

	/**
	 * Indicates the command that is defined as the clear command out of this
	 * form similar in spirit to the back command
	 */
	private Command clearCommand;

	/**
	 * Contains a list of components that would like to animate their state
	 */
	private Vector animatableComponents;

	/**
	 * This member holds the left soft key value
	 */
	static int leftSK = -6;

	/**
	 * This member holds the right soft key value
	 */
	static int rightSK = -7;

	/**
	 * This member holds the 2nd right soft key value this is used for different
	 * BB devices
	 */
	static int rightSK2 = -7;

	/**
	 * This member holds the back command key value
	 */
	static int backSK = -11;
	/**
	 * This member holds the clear command key value
	 */
	static int clearSK = -8;
	static int backspaceSK = -8;
	// private FormSwitcher formSwitcher;
	private Component lastFocused;
	protected Component focused;
	private Component focusCycleRoot;
	private Vector mediaComponents;

	/**
	 * This member allows us to define an animation that will draw the
	 * transition for entering this form. A transition is an animation that
	 * would occur when switching from one form to another.
	 */
	private Transition transitionInAnimator;
	/**
	 * This member allows us to define a an animation that will draw the
	 * transition for exiting this form. A transition is an animation that would
	 * occur when switching from one form to another.
	 */
	private Transition transitionOutAnimator;
	/**
	 * a listener that is invoked when a command is clicked allowing multiple
	 * commands to be handled by a single block
	 */
	private ActionListener commandListener;
	/**
	 * Relevant for modal forms where the previous form should be rendered
	 * underneath
	 */
	private Form previousForm;
	/**
	 * Indicates that this form should be tinted when painted
	 */
	private boolean tint;
	/**
	 * Default color for the screen tint when a dialog or a menu is shown
	 */
	private Color tintColor;
	/**
	 * Allows us to cache the next focus component ordered from top to down,
	 * this vector is guaranteed to have all focusable children in it.
	 */
	private Vector focusDownSequence;
	/**
	 * Allows us to cache the next focus component ordered from left to right,
	 * this vector is guaranteed to have all focusable children in it.
	 */
	private Vector focusRightSequence;
	/**
	 * Listeners for key release events
	 */
	private Hashtable keyListeners;
	/**
	 * Listeners for game key release events
	 */
	private Hashtable gameKeyListeners;

	/**
	 * Indicates whether focus should cycle within the form
	 */
	private boolean cyclicFocus = true;

	private int animationMode = REPEATING;
	private int animationDelay = 1000;
	private int animationPosition = 0;
	private int animationRepetitionMode = LOOP;
	Thread animationThread = null;

	/**
	 * Default constructor creates a simple form.
	 * 
	 * 
	 */
	public Form() {
		this("");
		
	}

	/**
	 * Sets the title after invoking the constructor.
	 * 
	 * 
	 * @param title
	 *            - the form title
	 */
	public Form(String title) {
		super(new BorderLayout());
		setUIID("Form");
		Style formStyle = getStyle();

		if(!Display.getInstance().isInitialized())
		{
			Display.getInstance().init();
		}
		
		Screen screen = Screen.getCurrentScreen();
		Plane planes[] = screen.getAllPlanes();

		if (planes != null) {
			Plane plane = null;
			PlaneSetup setup = null;

			for (int i = 0; i < planes.length; i++) {
                            System.out.println("!!: " + planes[i]);
				Capabilities cap = planes[i].getCapabilities();
				if (cap.isGraphicsRenderingSupported()) {
					plane = planes[i];
					setup = plane.getCurrentSetup();
					break;
				}
			}

			Dimension d = setup.getScreenResolution();

			int w = d.getWidth()
					- (formStyle.getMargin(Component.LEFT) + formStyle
							.getMargin(Component.RIGHT)), h = d.getHeight()
					- (formStyle.getMargin(Component.TOP) + formStyle
							.getMargin(Component.BOTTOM));

			setSize(setup.getScreenResolution());
			setPreferredSize(new Dimension(w, h));
		}

		this.title = new Label(title);
		this.title.setHorizontalAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER);
		this.title.setSize(new Dimension(1920, 60));
		
		if(title.length() != 0){
			super.addComponent(BorderLayout.NORTH, this.title);
		}
		
		menuBar = new MenuBar();

		cyclicFocus = true;

		//super.addComponent(BorderLayout.NORTH, this.title);
		super.addComponent(BorderLayout.CENTER, contentPane);
		// super.addComponent(BorderLayout.SOUTH, menuBar);

		contentPane.setSize(new com.sun.dtv.lwuit.geom.Dimension(getWidth(), getHeight()));
		contentPane.setVisible(isVisible());
		contentPane.setIsScrollVisible(true);
		contentPane.setScrollableX(true);
		contentPane.setScrollableY(true);

		focusCycleRoot = contentPane;
		LookAndFeel laf = UIManager.getInstance().getLookAndFeel();

		initLaf(laf);
		tintColor = laf.getDefaultFormTintColor();
		formStyle.setBgTransparency(0);
	}

	/**
	 * Adds Component to the Form's Content Pane.
	 * 
	 * 
	 * @param cmp
	 *            - the added param
	 * @see addComponent in class Container
	 */
	public void addComponent(Component cmp) {
		contentPane.addComponent(cmp);
		setShouldCalcPreferredSize(true);
		initFocused();
	}

	/**
	 * Sets the style of the menu bar programmatically.
	 * 
	 * 
	 * @param s
	 *            - new style
	 * @see getSoftButtonStyle()
	 */
	public void setSoftButtonStyle(Style s) {
		menuBar.setStyle(s);
	}

	/**
	 * Retrieves the style of the menu bar programmatically.
	 * 
	 * 
	 * 
	 * @return a Style object representing the soft button style value
	 * @see setSoftButtonStyle(com.sun.dtv.lwuit.plaf.Style)
	 */
	public Style getSoftButtonStyle() {
		return menuBar.getStyle();
	}

	/**
	 * Allows a developer that doesn't derive from the form to draw on top of
	 * the form regardless of underlying changes or animations. This is useful
	 * for watermarks or special effects (such as tinting) it is also useful for
	 * generic drawing of validation errors etc... A glass pane is generally
	 * transparent or translucent and allows the the UI bellow to be seen.
	 * 
	 * 
	 * @param glassPane
	 *            - a new glass pane to install. It is generally recommended to
	 *            use a painter chain if more than one painter is required.
	 * @see getGlassPane()
	 */
	public void setGlassPane(Painter glassPane) {
		this.glassPane = glassPane;

		repaint();
	}

	/**
	 * Allows a developer that doesn't derive from the form to draw on top of
	 * the form regardless of underlying changes or animations. This is useful
	 * for watermarks or special effects (such as tinting) it is also useful for
	 * generic drawing of validation errors etc... A glass pane is generally
	 * transparent or translucent and allows the the UI bellow to be seen.
	 * 
	 * 
	 * 
	 * @return the instance of the glass pane for this form
	 * @see setGlassPane(com.sun.dtv.lwuit.Painter),
	 *      PainterChain.installGlassPane(Form, com.sun.dtv.lwuit.Painter)
	 */
	public Painter getGlassPane() {
		return glassPane;
	}

	/**
	 * Sets the style of the title programmatically.
	 * 
	 * 
	 * @param s
	 *            - new style
	 * @see getTitleStyle()
	 */
	public void setTitleStyle(Style s) {
		title.setStyle(s);
	}

	/**
	 * Allows modifying the title attributes beyond style (like setting
	 * icon/alignment etc).
	 * 
	 * 
	 * 
	 * @return the component representing the title for the form
	 */
	public Label getTitleComponent() {
		return title;
	}

	/**
	 * Add a key listener to the given keycode for a callback when the key is
	 * released.
	 * 
	 * 
	 * @param keyCode
	 *            - code on which to send the event
	 * @param listener
	 *            - listener to invoke when the key code released.
	 * @see removeKeyListener(int, com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void addKeyListener(int keyCode, ActionListener listener) {
		if (keyListeners == null) {
			keyListeners = new Hashtable();
		}
		addKeyListener(keyCode, listener, keyListeners);
	}

	/**
	 * Removes a key listener from the given keycode.
	 * 
	 * 
	 * @param keyCode
	 *            - code on which the event is sent
	 * @param listener
	 *            - listener instance to remove
	 * @see addKeyListener(int, com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void removeKeyListener(int keyCode, ActionListener listener) {
		if (keyListeners == null) {
			return;
		}
		removeKeyListener(keyCode, listener, keyListeners);
	}

	/**
	 * Removes a game key listener from the given game keycode.
	 * 
	 * 
	 * @param keyCode
	 *            - code on which the event is sent
	 * @param listener
	 *            - listener instance to remove
	 * @see addGameKeyListener(int, com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void removeGameKeyListener(int keyCode, ActionListener listener) {
		if (gameKeyListeners == null) {
			return;
		}
		removeKeyListener(keyCode, listener, gameKeyListeners);
	}

	/**
	 * Invoked to allow subclasses of form to handle a command from one point
	 * rather than implementing many command instances. All commands selected on
	 * the form will trigger this method implicitly.
	 * 
	 * @param cmd
	 *            the form commmand object
	 */
	protected void actionCommand(Command cmd) {
	}

	/**
	 * This method shows the form as a modal alert allowing us to produce a
	 * behavior of an alert/dialog box. This method will block the calling
	 * thread even if the calling thread is the EDT. Notice that this method
	 * will not release the block until dispose is called even if show() from
	 * another form is called!
	 * <p>
	 * Modal dialogs Allow the forms "content" to "hang in mid air" this is
	 * especially useful for dialogs where you would want the underlying form to
	 * "peek" from behind the form.
	 * 
	 * @param top
	 *            space in pixels between the top of the screen and the form
	 * @param bottom
	 *            space in pixels between the bottom of the screen and the form
	 * @param left
	 *            space in pixels between the left of the screen and the form
	 * @param right
	 *            space in pixels between the right of the screen and the form
	 * @param includeTitle
	 *            whether the title should hang in the top of the screen or be
	 *            glued onto the content pane
	 * @param modal
	 *            indictes if this is a modal or modeless dialog true for modal
	 *            dialogs
	 */
	void showModal(int top, int bottom, int left, int right,
			boolean includeTitle, boolean modal) {
		Display.getInstance().flushEdt();
		if (previousForm == null) {
			previousForm = Display.getInstance().getCurrent();

			// special case for application opening with a dialog before any
			// form is shown
			if (previousForm == null) {
				previousForm = new Form();
				previousForm.show();
			}
			previousForm.tint = true;
		}
		Painter p = getStyle().getBgPainter();
		if (top > 0 || bottom > 0 || left > 0 || right > 0) {
			if (includeTitle) {
				title.getStyle().setMargin(top, 0, left, right);
				contentPane.getStyle().setMargin(0, bottom, left, right);
			} else {
				contentPane.getStyle().setMargin(top, bottom, left, right);
			}
			BGPainter b = new BGPainter(this, p);
			b.setIgnorCoordinates(true);
			getStyle().setBgPainter(b);
			b.setPreviousForm(previousForm);
		}

		if (modal) {
			// called to display a dialog and wait for modality
			initFocused();
			if (getTransitionOutAnimator() == null
					&& getTransitionInAnimator() == null) {
				initLaf(UIManager.getInstance().getLookAndFeel());
			}
			initComponentImpl();
			Display.getInstance().setCurrent(this);
			onShow();
			Display.getInstance().invokeAndBlock(new RunnableWrapper(this, p));
		} else {
			initFocused();
			if (getTransitionOutAnimator() == null
					&& getTransitionInAnimator() == null) {
				initLaf(UIManager.getInstance().getLookAndFeel());
			}
			initComponentImpl();
			Display.getInstance().setCurrent(this);
			onShow();
		}
	}

	/**
	 * Adds a component to the vector in the appropriate location based on its
	 * focus order
	 * 
	 */
	private void addSortedComponentDown(Vector components, Component c) {
		int componentCount = components.size();
		int componentY = c.getAbsoluteY();

		int bestSpot = 0;

		Component scrollableParent = findScrollableAncestor(c);

		// find components in the same column and add the component either at
		// the end of the line or at its start
		for (int iter = 0; iter < componentCount; iter++) {
			Component current = (Component) components.elementAt(iter);

			// this component is in the same column...
			Component currentScrollParent = findScrollableAncestor(current);
			if (currentScrollParent == scrollableParent) {
				if (isInSameColumn(current, c)) {
					int currentY = current.getAbsoluteY();
					if (currentY > componentY) {
						continue;
					}
					bestSpot = iter + 1;
					continue;
				}
			} else {
				Component tempScrollableParent = scrollableParent;
				if (scrollableParent == null) {
					tempScrollableParent = c;
				}
				Component tempCurrentScrollParent = currentScrollParent;
				if (currentScrollParent == null) {
					tempCurrentScrollParent = current;
				}
				if (tempCurrentScrollParent.getAbsoluteY() > tempScrollableParent
						.getAbsoluteY()) {
					continue;
				}
				if (isInSameColumn(tempCurrentScrollParent,
						tempScrollableParent)) {
					bestSpot = iter + 1;
					continue;
				}
			}
			if (current.getAbsoluteX() < c.getAbsoluteX()) {
				bestSpot = iter + 1;
			}
		}

		components.insertElementAt(c, bestSpot);
	}

	/**
	 * Adds a component to the vector in the appropriate location based on its
	 * focus order
	 */
	private void addSortedComponent(Vector components, Component c,
			boolean toTheRight) {
		if (toTheRight) {
			addSortedComponentRight(components, c);
		} else {
			addSortedComponentDown(components, c);
		}
	}

	/**
	 * Request focus for a form child component
	 * 
	 * @param cmp
	 *            the form child component
	 */
	void requestFocus(Component cmp) {
		if (cmp.isFocusable() && contains(cmp)) {
			scrollComponentToVisible(cmp);
			setFocused(cmp);
		}
	}

	/**
	 * The default version of show modal shows the dialog occupying the center
	 * portion of the screen.
	 */
	void showModal() {
		showDialog(true);
	}

	/**
	 * Allows subclasses to bind functionality that occurs immediately after a
	 * specific form or dialog appears on the screen
	 */
	protected void onShow() {
	}

	/**
	 * Add a game key listener to the given game key for a callback when the key
	 * is released.
	 * 
	 * 
	 * @param keyCode
	 *            - code on which to send the event
	 * @param listener
	 *            - listener to invoke when the key code released.
	 * @see removeGameKeyListener(int, com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void addGameKeyListener(int keyCode, ActionListener listener) {
		if (gameKeyListeners == null) {
			gameKeyListeners = new Hashtable();
		}
		addKeyListener(keyCode, listener, gameKeyListeners);
	}

	/**
	 * Resets the cache focus vectors, this is a good idea when we remove or add
	 * an element to the layout.
	 */
	void clearFocusVectors() {
		focusDownSequence = null;
		focusRightSequence = null;
	}

	/**
	 * Sets the current dragged Component
	 */
	void setDraggedComponent(Component dragged) {
		this.dragged = dragged;
	}

	/**
	 * Returns the number of buttons on the menu bar for use with
	 * getSoftButton().
	 * 
	 * 
	 * 
	 * @return an int representing the soft button count value
	 */
	public int getSoftButtonCount() {
		// return this.softButtonCount;
		return 0;
	}

	/**
	 * Returns the button representing the softbutton, this allows modifying
	 * softbutton attributes and behavior programmatically rather than by using
	 * the command API. Notice that this API behavior is fragile since the
	 * button mapped to a particular offset might change based on the command
	 * API.
	 * 
	 * 
	 * @param offset
	 *            - the offset the soft button is mapped to
	 * @return a button that can be manipulated
	 */
	public Button getSoftButton(int offset) {
		return menuBar.getSoftButtons()[offset];
	}

	/**
	 * Returns the style of the title.
	 * 
	 * 
	 * 
	 * @return a Style object representing the title style value
	 * @see setTitleStyle(com.sun.dtv.lwuit.plaf.Style)
	 */
	public Style getTitleStyle() {
		return title.getStyle();
	}

	/**
	 * Default command is invoked when a user presses fire, this functionality
	 * works well in some situations but might collide with elements such as
	 * navigation and combo boxes. Use with caution.
	 * 
	 * 
	 * @param defaultCommand
	 *            - a Command object specifying the default command value
	 * @see getDefaultCommand()
	 */
	public void setDefaultCommand(Command defaultCommand) {
		this.defaultCommand = defaultCommand;
	}

	/**
	 * Default command is invoked when a user presses fire, this functionality
	 * works well in some situations but might collide with elements such as
	 * navigation and combo boxes. Use with caution.
	 * 
	 * 
	 * 
	 * @return a Command object representing the default command value
	 * @see setDefaultCommand(com.sun.dtv.lwuit.Command)
	 */
	public Command getDefaultCommand() {
		if (selectCommand != null) {
			return selectCommand;
		}
		return defaultCommand;
	}

	/**
	 * Indicates the command that is defined as the clear command in this form.
	 * A clear command can be used both to map to a "clear" hardware button if
	 * such a button exists.
	 * 
	 * 
	 * @param clearCommand
	 *            - a Command object specifying the clear command value
	 * @see getClearCommand()
	 */
	public void setClearCommand(Command clearCommand) {
		this.clearCommand = clearCommand;
	}

	/**
	 * Indicates the command that is defined as the clear command in this form.
	 * A clear command can be used both to map to a "clear" hardware button if
	 * such a button exists.
	 * 
	 * 
	 * 
	 * @return a Command object representing the clear command value
	 * @see setClearCommand(com.sun.dtv.lwuit.Command)
	 */
	public Command getClearCommand() {
		return clearCommand;
	}

	/**
	 * The default version of show dialog shows the dialog occupying the center
	 * portion of the screen.
	 */
	void showDialog(boolean modal) {
		showModal(0, 0, 0, 0, true, modal);
	}

	/**
	 * Indicates the command that is defined as the back command out of this
	 * form. A back command can be used both to map to a hardware button (e.g.
	 * on the Sony Ericsson devices) and by elements such as transitions etc. to
	 * change the behavior based on direction (e.g. slide to the left to enter
	 * screen and slide to the right to exit with back).
	 * 
	 * 
	 * @param backCommand
	 *            - a Command object specifying the back command value
	 * @see getBackCommand()
	 */
	public void setBackCommand(Command backCommand) {
		this.backCommand = backCommand;
	}

	/**
	 * Indicates the command that is defined as the back command out of this
	 * form. A back command can be used both to map to a hardware button (e.g.
	 * on the Sony Ericsson devices) and by elements such as transitions etc. to
	 * change the behavior based on direction (e.g. slide to the left to enter
	 * screen and slide to the right to exit with back).
	 * 
	 * 
	 * @return a Command object representing the back command value
	 * @see setBackCommand(com.sun.dtv.lwuit.Command)
	 */
	public Command getBackCommand() {
		return backCommand;
	}

	/**
	 * This method returns the Content pane instance.
	 * 
	 * 
	 * 
	 */
	public Container getContentPane() {
		return contentPane;
	}

	/**
	 * Removes all Components from the Content Pane.
	 * 
	 * 
	 * @see removeAll in class Container
	 */
	public void removeAll() {
		contentPane.removeAll();
	}

	/**
	 * Sets the background image to show behind the form.
	 * 
	 * 
	 * @param bgImage
	 *            - the background image
	 */
	public void setBgImage(Image bgImage) {
		getStyle().setBgImage(bgImage);
	}

	/**
	 * <B>Description copied from class:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/Container.html#setLayout(com.sun.dtv.lwuit.layouts.Layout)">Container</A></CODE>
	 * </B></DD> Sets the layout manager responsible for arranging this
	 * container.
	 * 
	 * 
	 * @param layout
	 *            - the specified layout manager
	 * @see setLayout in class Container
	 * @see Container.getLayout()
	 */
	public void setLayout(Layout layout) {
		contentPane.setLayout(layout);
	}

	/**
	 * Sets the Form title to the given text.
	 * 
	 * 
	 * @param title
	 *            - the form title
	 * @see getTitle()
	 */
	public void setTitle(String title) {
		this.title.setText(title);
		if(title.length() != 0){
			super.addComponent(BorderLayout.NORTH, this.title);
		}else{
			super.removeComponent(this.title);
		}
		
		repaint();
	}

	/**
	 * Returns the Form title text.
	 * 
	 * 
	 * 
	 * @return a String representing the title value
	 * @see setTitle(java.lang.String)
	 */
	public String getTitle() {
		return title.getText();
	}

	/**
	 * Adds Component to the Form's Content Pane.
	 * 
	 * 
	 * @param constraints
	 *            - this method is useful when the Layout requires a constraint
	 *            such as the BorderLayout. In this case you need to specify an
	 *            additional data when you add a Component, such as "CENTER",
	 *            "NORTH"...
	 * @param cmp
	 *            - component to add
	 * @see addComponent in class Container
	 */
	public void addComponent(Object constraints, Component cmp) 
	{
		contentPane.addComponent(constraints, cmp);
		setShouldCalcPreferredSize(true);
		initFocused();
	}

	/**
	 * Adds Component to the Form's Content Pane.
	 * 
	 * 
	 * @param index
	 *            - index where to add the component
	 * @param cmp
	 *            - the added param
	 * @see addComponent in class Container
	 */
	public void addComponent(int index, Component cmp) {
		contentPane.addComponent(index, cmp);
		setShouldCalcPreferredSize(true);
		initFocused();
	}

	/**
	 * <B>Description copied from class:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/Container.html#replace(com.sun.dtv.lwuit.Component, com.sun.dtv.lwuit.Component, com.sun.dtv.lwuit.animations.Transition)">Container</A></CODE>
	 * </B></DD> This method replaces the current Component with the next
	 * Component. Current Component must be contained in this Container. This
	 * method return immediately.
	 * 
	 * 
	 * @param current
	 *            - a Component to remove from the Container
	 * @param next
	 *            - a Component that replaces the current Component
	 * @param t
	 *            - a Transition between the add and removal of the Components a
	 *            Transition can be null
	 * @see replace in class Container
	 */
	public void replace(Component current, Component next, Transition t) {
		contentPane.replace(current, next, t);
		setShouldCalcPreferredSize(true);
	}

	/**
	 * Removes a component from the Form's Content Pane.
	 * 
	 * 
	 * @param cmp
	 *            - the component to be removed
	 * @see removeComponent in class Container
	 */
	public void removeComponent(Component cmp) {
		contentPane.removeComponent(cmp);
		setShouldCalcPreferredSize(true);
	}

	/**
	 * The given component is interested in animating its appearance and will
	 * start receiving callbacks when it is visible in the form allowing it to
	 * animate its appearance. This method would not register a component
	 * instance more than once.
	 * 
	 * 
	 * @param cmp
	 *            - component that would be animated
	 */
	public void registerAnimated(Animated cmp) {
		if (animatableComponents == null) {
			animatableComponents = new Vector();
		}
		if (!animatableComponents.contains(cmp)) {
			animatableComponents.addElement(cmp);
		}
	}

	/**
	 * Returns the offset of the component within the up/down focus sequence
	 * 
	 * @return offset between 0 and number of components or -1 for an error
	 */
	int getFocusPosition(Component c) {
		initFocusDown();
		return focusDownSequence.indexOf(c);
	}

	/**
	 * Indicate that cmp would no longer like to receive animation events.
	 * 
	 * 
	 * @param cmp
	 *            - component that would no longer receive animation events
	 */
	public void deregisterAnimated(Animation cmp) {
		if (animatableComponents != null) {
			animatableComponents.removeElement(cmp);
		}
	}

	/**
	 * <B>Description copied from class:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#refreshTheme()">Component</A></CODE>
	 * </B></DD> Makes sure the component is up to date with the current style
	 * object.
	 * 
	 * 
	 * @see refreshTheme in class Container
	 */
	public void refreshTheme() {
		// when changing the theme when a title/menu bar is not visible the
		// refresh
		// won't apply to them. We need to protect against this occurance.
		if (menuBar != null && menuBar.getParent() == null) {
			menuBar.refreshTheme();
		}
		if (title != null && title.getParent() == null) {
			title.refreshTheme();
		}
		super.refreshTheme();
	}

	/**
	 * Exposing the background painting for the benefit of animations.
	 * 
	 * 
	 * @param g
	 *            - the form graphics
	 */
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
	}

	/**
	 * This property allows us to define a an animation that will draw the
	 * transition for entering this form. A transition is an animation that
	 * would occur when switching from one form to another.
	 * 
	 * 
	 * 
	 * @return the Form in transition
	 * @see setTransitionInAnimator(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getTransitionInAnimator() {
		return transitionInAnimator;
	}

	/**
	 * This property allows us to define a an animation that will draw the
	 * transition for entering this form. A transition is an animation that
	 * would occur when switching from one form to another.
	 * 
	 * 
	 * @param transitionInAnimator
	 *            - the Form in transition
	 * @see getTransitionInAnimator()
	 */
	public void setTransitionInAnimator(Transition transitionInAnimator) {
		this.transitionInAnimator = transitionInAnimator;
	}

	/**
	 * This property allows us to define a an animation that will draw the
	 * transition for exiting this form. A transition is an animation that would
	 * occur when switching from one form to another.
	 * 
	 * 
	 * 
	 * @return the Form out transition
	 * @see setTransitionOutAnimator(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getTransitionOutAnimator() {
		return transitionOutAnimator;
	}

	/**
	 * This property allows us to define a an animation that will draw the
	 * transition for exiting this form. A transition is an animation that would
	 * occur when switching from one form to another.
	 * 
	 * 
	 * @param transitionOutAnimator
	 *            - the Form out transition
	 * @see getTransitionOutAnimator()
	 */
	public void setTransitionOutAnimator(Transition transitionOutAnimator) {
		this.transitionOutAnimator = transitionOutAnimator;
	}

	/**
	 * A listener that is invoked when a command is clicked allowing multiple
	 * commands to be handled by a single block.
	 * 
	 * 
	 * @param commandListener
	 *            - the command action listener
	 */
	public void setCommandListener(ActionListener commandListener) {
		this.commandListener = commandListener;
	}

	void initLaf(LookAndFeel laf) {
		transitionOutAnimator = laf.getDefaultFormTransitionOut();
		transitionInAnimator = laf.getDefaultFormTransitionIn();
	}

	/**
	 * Displays the current form on the screen.
	 * 
	 * 
	 */
	public void show() {
		if (transitionOutAnimator == null && transitionInAnimator == null) {
			initLaf(UIManager.getInstance().getLookAndFeel());
		}
		initFocused();
		onShow();
		setVisible(true);

		Plane planes[] = Screen.getCurrentScreen().getAllPlanes();
		if (planes != null) {
			Plane plane = null;

			for (int i = 0; i < planes.length; i++) {
				if (planes[i].getID().equals("GraphicPlane")) {
					plane = planes[i];
					break;
				}
			}

			DTVContainer.getDTVContainer(plane).addComponent(this);
			try {
				if (plane.isAvailable()) {
					plane.reserve(true, -1, new ScarceResourceListener() {

						public boolean releaseRequested(ScarceResource resource) {
							return false;
						}

						public void releaseForced(ScarceResource resource) {
						}

						public void released(ScarceResource resource) {
						}
					});
				}
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (TimeoutException ex) {
				ex.printStackTrace();
			} catch (SecurityException ex) {
				ex.printStackTrace();
			}
		}
		
		//Accordingly to Operational Guideline
		this.getParent().setVisible(true);
		start();
		repaint();
	}

	/**
	 * @inheritDoc
	 * 
	 */
	public void repaint(Component cmp) {
		if (isVisible()) {
			if (current != null) {
				Component c = cmp;
				while (c != null) {
					if (c == current.getContents()) {
						Display.getInstance().repaint(cmp);
						return;
					}
					c = c.getParent();
				}
			} else {
				Display.getInstance().repaint(cmp);
			}
		}
	}

	private void addKeyListener(int keyCode, ActionListener listener,
			Hashtable keyListeners) {
		if (keyListeners == null) {
			keyListeners = new Hashtable();
		}
		Integer code = new Integer(keyCode);
		Vector vec = (Vector) keyListeners.get(code);
		if (vec == null) {
			vec = new Vector();
			vec.addElement(listener);
			keyListeners.put(code, vec);
			return;
		}
		if (!vec.contains(listener)) {
			vec.addElement(listener);
		}
	}

	private void removeKeyListener(int keyCode, ActionListener listener,
			Hashtable keyListeners) {
		if (keyListeners == null) {
			return;
		}
		Integer code = new Integer(keyCode);
		Vector vec = (Vector) keyListeners.get(code);
		if (vec == null) {
			return;
		}
		vec.removeElement(listener);
		if (vec.size() == 0) {
			keyListeners.remove(code);
		}
	}

	/**
	 * Indicates that scrolling through the component should work as an
	 * animation.
	 * 
	 * 
	 * @param smoothScrolling
	 *            - indicates if a component uses smooth scrolling
	 * @see setSmoothScrolling in class Component
	 */
	public void setSmoothScrolling(boolean smoothScrolling) {
		if (contentPane != null) {
			contentPane.setSmoothScrolling(smoothScrolling);
		}
	}

	/**
	 * Indicates that scrolling through the component should work as an
	 * animation.
	 * 
	 * 
	 * @return whether this component use smooth scrolling
	 * @see isSmoothScrolling in class Component
	 */
	public boolean isSmoothScrolling() {
		return contentPane.isSmoothScrolling();
	}

	/**
	 * Scroll animation speed in milliseconds allowing a developer to slow down
	 * or accelerate the smooth animation mode.
	 * 
	 * 
	 * @return scroll animation speed in milliseconds
	 * @see getScrollAnimationSpeed in class Component
	 * @see setScrollAnimationSpeed(int)
	 */
	public int getScrollAnimationSpeed() {
		return contentPane.getScrollAnimationSpeed();
	}

	/**
	 * Scroll animation speed in milliseconds allowing a developer to slow down
	 * or accelerate the smooth animation mode.
	 * 
	 * 
	 * @param animationSpeed
	 *            - scroll animation speed in milliseconds
	 * @see setScrollAnimationSpeed in class Component
	 * @see getScrollAnimationSpeed()
	 */
	public void setScrollAnimationSpeed(int animationSpeed) {
		contentPane.setScrollAnimationSpeed(animationSpeed);
	}

	/**
	 * Returns the Component Form or null if this Component is not added to a
	 * form.
	 * 
	 * 
	 * @return the Component Form
	 * @see getComponentForm in class Component
	 */
	public final Form getComponentForm() {
		return this;
	}

	/**
	 * Sets the focused component and fires the appropriate events to make it
	 * so.
	 * 
	 * 
	 * @param focused
	 *            - the newly focused component or null for no focus
	 * @see getFocused()
	 */
	public void setFocused(Component focused) {
		if (this.focused == focused && focused != null) {
			this.focused.repaint();
			return;
		}
		Component oldFocus = this.focused;
		this.focused = focused;

		if (oldFocus != null) {
			oldFocus.setFocus(false);
			oldFocus.fireFocusLost();
			fireFocusLost(oldFocus);
			if (oldFocus.getParent() != null) {
				oldFocus.repaint();
			}
		}
		// a listener might trigger a focus change event essentially
		// invalidating focus so we shouldn't break that
		if (focused != null && this.focused == focused) {
			focused.setFocus(true);
			focused.fireFocusGained();
			fireFocusGained(focused);
			focused.repaint();
		}
	}

	void addSelectCommand() {
		if (Display.getInstance().isThirdSoftButton()) {
			if (selectCommand == null) {
				selectCommand = new Command(UIManager.getInstance().localize(
						"select", "Select"));
			}
			addCommand(selectCommand);
		}
	}

	void removeSelectCommand() {
		/*
		 * if (Display.getInstance().isThirdSoftButton()) {
		 * removeCommand(selectCommand); }
		 */
	}

	/**
	 * Returns the current focus component for this form.
	 * 
	 * 
	 * 
	 * @return the current focus component for this form
	 * @see setFocused(com.sun.dtv.lwuit.Component)
	 */
	public Component getFocused() {
		return focused;
	}

	/**
	 * If this Component is focused this method is invoked when the user presses
	 * and holds the key.
	 * 
	 * 
	 * @param keyCode
	 *            - the key code value to indicate a physical key.
	 * @see longKeyPress in class Component
	 */
	public void longKeyPress(int keyCode) {
		if (focused != null) {
			if (focused.getComponentForm() == this) {
				focused.longKeyPress(keyCode);
			}
		}
	}

	/**
	 * If this Component is focused, the key pressed event will call this
	 * method.
	 * 
	 * 
	 * @param keyCode
	 *            - the key code value to indicate a physical key.
	 * @see keyPressed in class Component
	 */
	public void keyPressed(int keyCode) {
		if (focused != null) {
			if(focused.isEnabled())
			{
				focused.keyPressed(keyCode);
			}
			if(focused.handlesInput())
			{
				return;
			} else {
				if (keyCode == RemoteControlEvent.VK_LEFT
					||
					keyCode == RemoteControlEvent.VK_RIGHT
					||
					keyCode == RemoteControlEvent.VK_UP
					||
					keyCode == RemoteControlEvent.VK_DOWN) {
					this.updateFocus(keyCode);
				}
			}
		} else {
			initFocused();
			if(focused == null) {
				contentPane.moveScrollTowards(keyCode, null);
				return;
			}
		}
	}

	protected void updateFocus(int keyCode) {
		Component focused = getFocused();
		switch (keyCode) {
			case RemoteControlEvent.VK_DOWN: {
				Component down = focused.getNextFocusDown();
				if (down != null && down.getComponentForm() == this) {
					focused = down;
				} else {
					initFocusDown();
					int i = focusDownSequence.indexOf(focused) + 1;
					if (focusDownSequence.size() > 0) {
						if (i == focusDownSequence.size()) {
							if (cyclicFocus) {
								i = 0;
							} else {
								i = focusDownSequence.size() - 1;
							}
						}
						focused = (Component) focusDownSequence.elementAt(i);
					}
				}
				break;
			}
			case RemoteControlEvent.VK_UP: {
				Component up = focused.getNextFocusUp();
				if (up != null && up.getComponentForm() == this) {
					focused = up;
				} else {
					initFocusDown();
					if (focusDownSequence.size() > 0) {
						int i = focusDownSequence.indexOf(focused) - 1;
						if (i < 0) {
							if (cyclicFocus) {
								i = focusDownSequence.size() - 1;
							} else {
								i = 0;
							}
						}
						focused = (Component) focusDownSequence.elementAt(i);
					}
				}
				break;
			}
			case RemoteControlEvent.VK_RIGHT: {
				Component right = focused.getNextFocusRight();
				if (right != null && right.getComponentForm() == this) {
					focused = right;
				} else {
					initFocusRight();
					if (focusRightSequence.size() > 0) {
						int i = focusRightSequence.indexOf(focused) + 1;
						if (i == focusRightSequence.size()) {
							if (cyclicFocus) {
								i = 0;
							} else {
								i = focusRightSequence.size() - 1;
							}
						}
						focused = (Component) focusRightSequence.elementAt(i);
					}
				}
				break;
			}
			case RemoteControlEvent.VK_LEFT: {
				Component left = focused.getNextFocusLeft();
				if (left != null && left.getComponentForm() == this) {
					focused = left;
				} else {
					initFocusRight();
					if (focusRightSequence.size() > 0) {
						int i = focusRightSequence.indexOf(focused) - 1;
						if (i < 0) {
							if (cyclicFocus) {
								i = focusRightSequence.size() - 1;
							} else {
								i = 0;
							}
						}
						focused = (Component) focusRightSequence.elementAt(i);
					}
				}
				break;
			}
			default:
				return;
		}

		//if focused is now visible we need to give it the focus.
		if (isFocusScrolling()) {
			setFocused(focused);
			if (focused != null) {
				scrollComponentToVisible(focused);
			}
		} else {
			if (moveScrollTowards(keyCode, focused)) {
				setFocused(focused);
				scrollComponentToVisible(focused);
			}
		}
	}

	/**
	 * Indicates whether lists and containers should scroll only via focus and thus "jump" when
	 * moving to a larger component as was the case in older versions of LWUIT.
	 *
	 * @return the value of focusScrolling
	 */
	public boolean isFocusScrolling() {
		return focusScrolling;
	}

	/**
     	 * @inheritDoc
	 */
	boolean moveScrollTowards(int direction, Component c) {
		//if the current focus item is in a scrollable Container
		//try and move it first
		Component current = getFocused();
		if (current != null) {
			Container parent;
			if (current instanceof Container) {
				parent = (Container) current;
			} else {
				parent = current.getParent();
			}
			while (parent != null) {
				if (parent.isScrollable()) {
					return parent.moveScrollTowards(direction, c);
				}
				parent = parent.getParent();
			}
		}

		return true;
	}

	int getFocusCount() {
		initFocusDown();
		return focusDownSequence.size();
	}

	/**
	 * If this Component is focused, the key released event will call this
	 * method.
	 * 
	 * 
	 * @param keyCode
	 *            - the key code value to indicate a physical key.
	 * @see keyReleased in class Component
	 */
	public void keyReleased(int keyCode) {
		if (focused != null) {
			if (focused.getComponentForm() == this) {
				focused.keyReleased(keyCode);
			}
		}

		/*
		 * TODO:: int game = Display.getInstance().getGameAction(keyCode); if
		 * (keyCode == leftSK || (keyCode == rightSK || keyCode == rightSK2) ||
		 * keyCode == backSK || keyCode == clearSK || keyCode == backspaceSK ||
		 * (Display.getInstance().isThirdSoftButton() && game ==
		 * Display.GAME_FIRE)) { menuBar.keyReleased(keyCode); return; }
		 * 
		 * //Component focused = focusManager.getFocused(); if (focused != null)
		 * { if (focused.getComponentForm() == this) {
		 * focused.keyReleased(keyCode); } }
		 * 
		 * // prevent the default action from stealing the behavior from the
		 * popup/combo box... if (game == Display.GAME_FIRE && current == null)
		 * { Command defaultCmd = getDefaultCommand(); if (defaultCmd != null) {
		 * defaultCmd.actionPerformed(new ActionEvent(defaultCmd, keyCode));
		 * actionCommandImpl(defaultCmd); } } fireKeyEvent(keyListeners,
		 * keyCode); fireKeyEvent(gameKeyListeners, game);
		 */
	}

	/**
	 * If this Component is focused, the key repeat event will call this method.
	 * Calls key pressed/released by default.
	 * 
	 * 
	 * @param keyCode
	 *            - the key code value to indicate a physical key.
	 * @see keyRepeated in class Component
	 */
	public void keyRepeated(int keyCode) {
		if (focused != null) {
			if (focused.getComponentForm() == this) {
				focused.longKeyPress(keyCode);
			}
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
	 * @see pointerPressed in class Container
	 */
	public void pointerPressed(int x, int y) {
		// if there is no popup on the screen an click is relevant to the menu
		// bar.
		if (focusCycleRoot.equals(contentPane) && menuBar.contains(x, y)) {
			Component cmp = menuBar.getComponentAt(x, y);
			if (cmp != null) {
				cmp.pointerPressed(x, y);
			}
			return;
		}

		// if the visible cycle root is a popup, and the user clicked out of the
		// popup bounds
		// make the popup invisible.
		if (!focusCycleRoot.equals(contentPane)
				&& !focusCycleRoot.contains(x, y)) {
			if (current != null) {
				current.setVisible(false);
			}

			return;
		}

		if (focusCycleRoot instanceof Container) {
			Component cmp = ((Container) focusCycleRoot).getComponentAt(x, y);
			if (cmp != null && cmp.isFocusable()) {
				setFocused(cmp);
				cmp.pointerPressed(x, y);
				cmp.repaint();
			}
		} else {
			focusCycleRoot.pointerPressed(x, y);
			focusCycleRoot.repaint();
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
	 * @see pointerDragged in class Component
	 */
	public void pointerDragged(int x, int y) {
		if (dragged != null) {
			dragged.pointerDragged(x, y);
			return;
		}

		if (focusCycleRoot instanceof Container) {
			Component cmp = ((Container) focusCycleRoot).getComponentAt(x, y);
			if (cmp != null) {
				if (cmp.isFocusable()) {
					setFocused(cmp);
				}
				cmp.pointerDragged(x, y);
				cmp.repaint();
			}
		} else {
			focusCycleRoot.pointerDragged(x, y);
			focusCycleRoot.repaint();
		}
	}

	/**
	 * Returns true if there is only one focusable member in this form. This is
	 * useful so setHandlesInput would always be true for this case.
	 * 
	 * 
	 * 
	 * @return true if there is one focusable component in this form, false for
	 *         0 or more
	 */
	public boolean isSingleFocusMode() {
		initFocusDown();
		return focusDownSequence.size() == 1;
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
	 * @see pointerReleased in class Component
	 */
	public void pointerReleased(int x, int y) {
		if (dragged == null) {
			// if the pointer was released on the menu invoke the appropriate
			// soft button.
			if (focusCycleRoot.equals(contentPane) && menuBar.contains(x, y)) {
				Component cmp = menuBar.getComponentAt(x, y);
				if (cmp != null) {
					cmp.pointerReleased(x, y);
				}
				return;
			}

			// if the popup is showing and the pointer was released out of the
			// popup bounds dismiss the popup
			if (!focusCycleRoot.equals(contentPane)
					&& !focusCycleRoot.contains(x, y)) {
				if (current != null) {
					current.setVisible(false);
				}
				focusCycleRoot = contentPane;
				if (lastFocused != null) {
					setFocused(lastFocused);
				} else {
					initFocused();
				}
				return;
			}

			Component cmp = null;
			if (focusCycleRoot instanceof Container) {
				cmp = ((Container) focusCycleRoot).getComponentAt(x, y);
				if (cmp != null && cmp.isFocusable()) {
					setFocused(cmp);
				}
			} else {
				cmp = focusCycleRoot;
			}
			if (cmp != null) {
				cmp.pointerReleased(x, y);
				cmp.repaint();
			}
		} else {
			dragged.pointerReleased(x, y);
			dragged = null;
		}
	}

	/**
	 * Sets whether the component should/could scroll on the Y axis.
	 * 
	 * 
	 * @param scrollableY
	 *            - whether the component should/could scroll on the Y axis
	 * @see setScrollableY in class Container
	 */
	public void setScrollableY(boolean scrollableY) {
		getContentPane().setScrollableY(scrollableY);
	}

	/**
	 * Sets whether the component should/could scroll on the X axis.
	 * 
	 * 
	 * @param scrollableX
	 *            - whether the component should/could scroll on the X axis
	 * @see setScrollableX in class Container
	 */
	public void setScrollableX(boolean scrollableX) {
		getContentPane().setScrollableX(scrollableX);
	}

	/**
	 * Adds a command to the menu bar softkeys or into the menu dialog, this
	 * version of add allows us to place a command in an arbitrary location.
	 * This allows us to force a command into the softkeys when order of command
	 * addition can't be changed.
	 * 
	 * 
	 * @param cmd
	 *            - the Form command to be added
	 * @param offset
	 *            - position in which the command is added
	 */
	public void addCommand(Command cmd, int offset) {
		menuBar.addCommand(cmd, offset);
	}

	/**
	 * A helper method to check the amount of commands within the form menu.
	 * 
	 * 
	 * 
	 * @return an int representing the command count value
	 */
	public int getCommandCount() {
		return menuBar.getCommandCount();
	}

	/**
	 * Returns the command occupying the given index.
	 * 
	 * 
	 * @param index
	 *            - offset of the command
	 * @return the command at the given index
	 */
	public Command getCommand(int index) {
		return menuBar.getCommand(index);
	}
	
	public Vector getCommandList(){
		return menuBar.getCommandList();
	}

	/**
	 * Adds a command to the menu bar softkeys. The Commands are placed in the
	 * order they are added. If the Form has 1 Command it will be placed on the
	 * right. If the Form has 2 Commands the first one that was added will be
	 * placed on the right and the second one will be placed on the left. If the
	 * Form has more then 2 Commands the first one will stay on the left and a
	 * Menu will be added with all the remain Commands.
	 * 
	 * 
	 * @param cmd
	 *            - the Form command to be added
	 */
	public void addCommand(Command cmd) {
		menuBar.addCommand(cmd);
	}

	/**
	 * Removes the command from the menu bar softkeys.
	 * 
	 * 
	 * @param cmd
	 *            - the Form command to be removed
	 */
	public void removeCommand(Command cmd) {
		menuBar.removeCommand(cmd);
	}

	/**
	 * Makes sure the component is visible in the scroll if this container is
	 * scrollable.
	 * 
	 * 
	 * @param c
	 *            - the component to be visible
	 */
	public void scrollComponentToVisible(Component c) {
		Container parent = c.getParent();
		while (parent != null) {
			if (parent.isScrollable()) {
				parent.scrollComponentToVisible(c);
				return;
			}
			parent = parent.getParent();
		}
	}

	/**
	 * Determine the cell renderer used to render menu elements for theming the
	 * look of the menu options.
	 * 
	 * 
	 * @param menuCellRenderer
	 *            - the menu cell renderer
	 */
	public void setMenuCellRenderer(ListCellRenderer menuCellRenderer) {
		menuBar.setMenuCellRenderer(menuCellRenderer);
	}

	/**
	 * Clear menu commands from the menu bar.
	 * 
	 * 
	 */
	public void removeAllCommands() {
		menuBar.removeAllCommands();
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
	 * @see paint in class Container
	 */
	public void paint(Graphics g) {
		super.paintBackground(g);
		super.paint(g);
		if (tint) {
			g.setColor(tintColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/**
	 * Invoked to allow subclasses of form to handle a command from one point
	 * rather than implementing many command instances
	 */
	private void actionCommandImpl(Command cmd) {
		if (cmd == null) {
			return;
		}

		if (cmd != selectCommand) {
			if (commandListener != null) {
				commandListener.actionPerformed(new ActionEvent(cmd));
			}
			actionCommand(cmd);
		} else {
			Component c = getFocused();
			if (c != null) {
				c.fireClicked();
			}
		}
	}

	/**
	 * Returns true if the given dest component is in the column of the source
	 * component
	 */
	private boolean isInSameColumn(Component source, Component dest) {
		return Rectangle.intersects(source.getAbsoluteX(),
				source.getAbsoluteY(), source.getWidth(), Integer.MAX_VALUE,
				dest.getAbsoluteX(), dest.getAbsoluteY(), dest.getWidth(),
				dest.getHeight());
	}

	/**
	 * Returns true if the given dest component is in the row of the source
	 * component
	 */
	private boolean isInSameRow(Component source, Component dest) {
		return Rectangle.intersects(source.getAbsoluteX(),
				source.getAbsoluteY(), Integer.MAX_VALUE, source.getHeight(),
				dest.getAbsoluteX(), dest.getAbsoluteY(), dest.getWidth(),
				dest.getHeight());
	}

	/**
	 * Returns the first scrollable ancestor for this component or null if no
	 * such ancestor exists
	 */
	private Component findScrollableAncestor(Component c) {
		c = c.getParent();
		if (c == null || c.isScrollable()) {
			return c;
		}
		return findScrollableAncestor(c);
	}

	/**
	 * Adds a component to the vector in the appropriate location based on its
	 * focus order
	 */
	private void addSortedComponentRight(Vector components, Component c) {
		int componentCount = components.size();
		int componentX = c.getAbsoluteX();

		int bestSpot = 0;

		Component scrollableParent = findScrollableAncestor(c);

		// find components in the same row and add the component either at the
		// end
		// of the line or at its start
		for (int iter = 0; iter < componentCount; iter++) {
			Component current = (Component) components.elementAt(iter);

			// this component is in the same row...
			Component currentScrollParent = findScrollableAncestor(current);
			if (currentScrollParent == scrollableParent) {
				if (isInSameRow(current, c)) {
					int currentX = current.getAbsoluteX();
					if (currentX > componentX) {
						continue;
					}
					bestSpot = iter + 1;
					continue;
				}
			} else {
				Component tempScrollableParent = scrollableParent;
				if (scrollableParent == null) {
					tempScrollableParent = c;
				}
				Component tempCurrentScrollParent = currentScrollParent;
				if (currentScrollParent == null) {
					tempCurrentScrollParent = current;
				}
				if (tempCurrentScrollParent.getAbsoluteX() > tempScrollableParent
						.getAbsoluteX()) {
					continue;
				}
				if (isInSameRow(tempCurrentScrollParent, tempScrollableParent)) {
					bestSpot = iter + 1;
					continue;
				}
			}
			if (current.getAbsoluteY() < c.getAbsoluteY()) {
				bestSpot = iter + 1;
			}
		}

		components.insertElementAt(c, bestSpot);
	}

	/**
	 * Finds all focusable components in the hierarchy
	 */
	private void findAllFocusable(Container c, Vector v, boolean toTheRight) {
		int size = c.getComponentCount();

		for (int iter = 0; iter < size; iter++) {
			Component current = c.getComponentAt(iter);
			if (current instanceof Container) {
				findAllFocusable((Container) current, v, toTheRight);
			}
			if (current.isFocusable()) {
				addSortedComponent(v, current, toTheRight);
			}
		}
	}

	synchronized void initFocusRight() {
		if (focusRightSequence == null) {
			focusRightSequence = new Vector();
			findAllFocusable(contentPane, focusRightSequence, true);
		}
	}

	synchronized void initFocusDown() {
		if (focusDownSequence == null) {
			focusDownSequence = new Vector();
			findAllFocusable(contentPane, focusDownSequence, false);
		}
	}

	void initFocused() {
		if (focused == null) {
			layoutContainer();
			initFocusDown();
			if (focusDownSequence.size() > 0) {
				setFocused((Component) focusDownSequence.elementAt(0));
			}
		}
	}

	/**
	 * The equivalent of calling both setScrollableY and setScrollableX.
	 * 
	 * 
	 * @param scrollable
	 *            - whether the component should/could scroll on the X and Y
	 *            axis
	 * @see setScrollable in class Container
	 */
	public void setScrollable(boolean scrollable) {
		contentPane.setScrollable(scrollable);
	}

	/**
	 * Toggles visibility of the component.
	 * 
	 * 
	 * @param visible
	 *            - true if component is visible; otherwise false
	 * @see setVisible in class Component
	 */
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);

		if (contentPane != null) {
			contentPane.setVisible(visible);
		}

		if (mediaComponents != null) {
			for (int i = 0; i < mediaComponents.size(); i++) {
				Component mediaCmp = (Component) mediaComponents.elementAt(i);
				mediaCmp.setVisible(visible);
			}
		}
	}

	/**
	 * Default color for the screen tint when a dialog or a menu is shown.
	 * 
	 * 
	 * 
	 * @return the tint color when a dialog or a menu is shown
	 * @see setTintColor(java.awt.Color)
	 */
	public Color getTintColor() {
		return tintColor;
	}

	/**
	 * Default color for the screen tint when a dialog or a menu is shown.
	 * 
	 * 
	 * @param tintColor
	 *            - the tint color when a dialog or a menu is shown
	 * @see getTintColor()
	 */
	public void setTintColor(Color tintColor) {
		this.tintColor = tintColor;
	}

	/**
	 * Sets the menu transitions for showing/hiding the menu. Can be null.
	 * 
	 * 
	 * @param transitionIn
	 *            - the transition that will play when the menu appears
	 * @param transitionOut
	 *            - the transition that will play when the menu is folded
	 */
	public void setMenuTransitions(Transition transitionIn,
			Transition transitionOut) {
		menuBar.setTransitions(transitionIn, transitionOut);
	}

	/**
	 * If this method returns true the EDT won't go to sleep indefinitely
	 * 
	 * @return true is form has animation; otherwise false
	 */
	boolean hasAnimations() {
		return animatableComponents != null && animatableComponents.size() > 0;
	}

	/**
	 * This method is only invoked when the underlying canvas for the form gets
	 * a size changed event. This method will trigger a relayout of the Form.
	 * This method will get the callback only if this Form is the Current Form
	 * 
	 * @param w
	 *            the new width of the Form
	 * @param h
	 *            the new height of the Form
	 */
	protected void sizeChanged(int w, int h) {
		setSize(new Dimension(w, h));
		setShouldCalcPreferredSize(true);
		doLayout();
		repaint();
	}

	/**
	 * Registering media component to this Form, that like to receive animation
	 * events
	 * 
	 * @param mediaCmp
	 *            the Form media component to be registered
	 */
	void registerMediaComponent(Component mediaCmp) {
		if (mediaComponents == null) {
			mediaComponents = new Vector();
		}
		if (!mediaComponents.contains(mediaCmp)) {
			mediaComponents.addElement(mediaCmp);
		}
	}

	/**
	 * Used by the implementation to prevent flickering when flushing the double
	 * buffer
	 * 
	 * @return true if the form has media components within it
	 */
	public final boolean hasMedia() {
		return mediaComponents != null && mediaComponents.size() > 0;
	}

	/**
	 * Indicate that cmp would no longer like to receive animation events
	 * 
	 * @param cmp
	 *            component that would no longer receive animation events
	 */
	void deregisterMediaComponent(Component mediaCmp) {
		mediaComponents.removeElement(mediaCmp);
	}

	/**
	 * Allows the display to skip the menu dialog if that is the current form
	 * 
	 */
	Form getPreviousForm() {
		return previousForm;
	}

	/**
	 * Creates the list component containing the commands within the given
	 * vector used for showing the menu dialog
	 * 
	 * @param commands
	 *            list of command objects
	 * @return List object
	 */
	protected List createCommandList(Vector commands) {
		List l = new List(commands);
		l.setStyle(UIManager.getInstance().getComponentStyle("CommandList"));
		((Component) l.getRenderer()).setStyle(UIManager.getInstance()
				.getComponentStyle("Command"));
		l.setFixedSelection(List.FIXED_NONE_CYCLIC);
		return l;
	}

	/**
	 * Displays the given popup on top of the current form
	 * 
	 * @param pop
	 *            the displayed popup
	 */
	void showPopup(Popup pop) {
		if (current == null || !current.equals(pop)) {
			current = pop;
			if (contains(focused)) {
				lastFocused = focused;
			}
			focusCycleRoot = pop.getContents();
			if (focusCycleRoot.isFocusable()) {
				setFocused(focusCycleRoot);
			} else {
				// Shai: focus patch for animation bug in combo boxes might need
				// to rethink some concepts about popups
				setFocused(((Container) focusCycleRoot).getComponentAt(0));
				// initFocused();
			}
		}
		focusCycleRoot.repaint();
	}

	/**
	 * Hides the popup from the current form
	 */
	void hidePopups() {
		if (current != null) {
			current = null;
			// contentPane.removeComponent(focusCycleRoot);
			focusCycleRoot = contentPane;
			setFocused(lastFocused);
			repaint();
		}
	}

	Popup getPopup(){
		return current;
	}

	/**
	 * Invoked by display to restore the menu after transition
	 * 
	 * @see hideMenu
	 */
	void restoreMenu() {
		if (menuBar.getParent() == null) {
			super.addComponent(BorderLayout.SOUTH, menuBar);
		}
	}

	/**
	 * Makes sure all animations are repainted so they would be rendered in
	 * every frame
	 */
	void repaintAnimations() {
		// prevent animations from playing with a popup
		if (animatableComponents != null && isVisible()) {
			// we don't save size() in a varible since the animate method may
			// deregister the animation thus invalidating the size
			for (int iter = 0; iter < animatableComponents.size(); iter++) {
				Animation c = (Animation) animatableComponents.elementAt(iter);
				if (c.animate()) {
					if (c instanceof Component) {
						Rectangle rect = ((Component) c).getDirtyRegion();
						if (rect != null) {
							((Component) c).repaint(rect.getX(), rect.getY(),
									rect.getSize().getWidth(), rect.getSize()
											.getHeight());
						} else {
							((Component) c).repaint();
						}
					} else {
						Display.getInstance().repaint(c);
					}
				}
			}
		}
	}

	/**
	 * Works only for modal forms by returning to the previous form
	 */
	public void dispose() {
		disposeImpl();
	}

	/**
	 * Works only for modal forms by returning to the previous form
	 */
	void disposeImpl() {
		if (previousForm != null) {
			previousForm.tint = false;

			if (previousForm instanceof Dialog) {
				if (!((Dialog) previousForm).isDisposed()) {
					previousForm.setVisible(true);
					Display.getInstance().setCurrent(previousForm);
				}
			} else {
				previousForm.setVisible(true);
				Display.getInstance().setCurrent(previousForm);
			}

			// enable GC to cleanup the previous form if no longer referenced
			previousForm = null;
		}

		Plane planes[] = Screen.getCurrentScreen().getAllPlanes();
		if (planes != null) {
			Plane plane = null;

			for (int i = 0; i < planes.length; i++) {
				if (planes[i].getID().equals("GraphicPlane")) {
					plane = planes[i];
					break;
				}
			}

			DTVContainer.getDTVContainer(plane).removeComponent(this);
		}
	}

	/**
	 * Allows an individual form to reverse the layout direction of the
	 * softbuttons
	 * 
	 * @return The value of
	 *         UIManager.getInstance().getLookAndFeel().isReverseSoftButtons()
	 */
	protected boolean isReverseSoftButtons() {
		return UIManager.getInstance().getLookAndFeel().isReverseSoftButtons();
	}

	/**
	 * A menu is implemented as a dialog, this method allows you to override
	 * dialog display in order to customize the dialog menu in various ways
	 * 
	 * @param menu
	 *            a dialog containing menu options that can be customized
	 * @return the command selected by the user in the dialog (not menu) Select
	 *         or Cancel
	 */
	protected Command showMenuDialog(Dialog menu) {
		int marginLeft = (int) (Form.this.getWidth() * 0.25f);
		int marginRight = 0;
		if (isReverseSoftButtons()) {
			marginRight = marginLeft;
			marginLeft = 0;
		}
		int height = Form.this.getHeight() / 3;
		return menu.show(height, 0, marginLeft, marginRight, true);
	}

	class MenuBar extends Container implements ActionListener {

		private Command menuCommand;
		private Vector commands = new Vector();
		private Button[] soft;
		private Command[] softCommand;
		private Button left;
		private Button right;
		private Button main;
		private ListCellRenderer menuCellRenderer;
		private Transition transitionIn;
		private Transition transitionOut;
		private List commandList;
		private Command selectMenuItem;
		private Command cancelMenuItem;
		private Style menuStyle;

		public MenuBar() {
			LookAndFeel lf = UIManager.getInstance().getLookAndFeel();
			menuStyle = UIManager.getInstance().getComponentStyle("Menu");
			menuCommand = new Command(UIManager.getInstance().localize("menu",
					"Menu"), lf.getMenuIcons()[2]);
			// use the slide transition by default
			if (lf.getDefaultMenuTransitionIn() != null
					|| lf.getDefaultMenuTransitionOut() != null) {
				transitionIn = lf.getDefaultMenuTransitionIn();
				transitionOut = lf.getDefaultMenuTransitionOut();
			} else {
				transitionIn = CommonTransitions.createSlide(
						CommonTransitions.SLIDE_VERTICAL, true, 300, true);
				transitionOut = CommonTransitions.createSlide(
						CommonTransitions.SLIDE_VERTICAL, false, 300, true);
			}
			menuCellRenderer = lf.getMenuRenderer();

			soft = new Button[] { createSoftButton() };

			if (left != null) {
				left.setAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT);
				right.setAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT);
			}

			softCommand = new Command[soft.length];
		}

		/**
		 * Updates the command mapping to the softbuttons
		 */
		private void updateCommands() {
			if (soft.length > 1) {
				soft[0].setText("");
				soft[1].setText("");
				soft[0].setIcon(null);
				soft[1].setIcon(null);
				int commandSize = commands.size();
				if (soft.length > 2) {
					soft[2].setText("");
					if (commandSize > 2) {
						if (commandSize > 3) {
							softCommand[2] = menuCommand;
						} else {
							softCommand[2] = (Command) commands
									.elementAt(commands.size() - 3);
						}
						soft[2].setText(softCommand[2].getCommandName());
						soft[2].setIcon(softCommand[2].getIcon());
					} else {
						softCommand[2] = null;
					}
				}
				if (commandSize > 0) {
					softCommand[0] = (Command) commands.elementAt(commands
							.size() - 1);
					soft[0].setText(softCommand[0].getCommandName());
					soft[0].setIcon(softCommand[0].getIcon());
					if (commandSize > 1) {
						if (soft.length == 2 && commandSize > 2) {
							softCommand[1] = menuCommand;
						} else {
							softCommand[1] = (Command) commands
									.elementAt(commands.size() - 2);
						}
						soft[1].setText(softCommand[1].getCommandName());
						soft[1].setIcon(softCommand[1].getIcon());
					} else {
						softCommand[1] = null;
					}
				} else {
					softCommand[0] = null;
					softCommand[1] = null;
				}

				// we need to add the menu bar to an already visible form
				if (commandSize == 1) {
					if (Form.this.isVisible()) {
						Form.this.revalidate();
					}
				}
				repaint();
			}
		}

		/**
		 * Invoked when a softbutton is pressed
		 */
		public void actionPerformed(ActionEvent evt) {
			if (evt.isConsumed()) {
				return;
			}
			Object src = evt.getSource();
			if (commandList == null) {
				Button source = (Button) src;
				for (int iter = 0; iter < soft.length; iter++) {
					if (source == soft[iter]) {
						if (softCommand[iter] == menuCommand) {
							showMenu();
							return;
						}
						if (softCommand[iter] != null) {
							ActionEvent e = new ActionEvent(softCommand[iter]);
							softCommand[iter].actionPerformed(e);
							if (!e.isConsumed()) {
								actionCommandImpl(softCommand[iter]);
							}
						}
						return;
					}
				}
			} else {
				// the list for the menu sent the event
				List l = commandList;
				if (src instanceof Button) {
					for (int iter = 0; iter < soft.length; iter++) {
						if (src == soft[iter]) {
							Container parent = l.getParent();
							while (parent != null) {
								if (parent instanceof Dialog) {
									((Dialog) parent)
											.actionCommand(softCommand[iter]);
									return;
								}
								parent = parent.getParent();
							}
						}
					}
				}
				Command c = (Command) l.getSelectedItem();
				Container parent = l.getParent();
				while (parent != null) {
					if (parent instanceof Dialog) {
						((Dialog) parent).actionCommand(c);
						return;
					}
					parent = parent.getParent();
				}
			}

		}

		private Button createSoftButton() {
			Button b = new Button();
			b.addActionListener(this);
			b.setFocusPainted(false);
			b.setFocusable(false);
			b.setBorderPainted(false);
			updateSoftButtonStyle(b);
			return b;
		}

		private void updateSoftButtonStyle(Button b) {
			Style s = new Style(getStyle());
			b.setStyle(s);
			s.setBgImage(null);
			s.setBorder(null);
			s.setMargin(2, 2, 2, 2);
			s.setPadding(2, 2, 2, 2);
			s.setBgTransparency(0);
		}

		public void setStyle(Style style) {
			super.setStyle(style);
			for (int i = 0; i < soft.length; i++) {
				updateSoftButtonStyle(soft[i]);
			}
		}

		/**
		 * Prevents scaling down of the menu when there is no text on the menu
		 * bar
		 */
		protected Dimension calcPreferredSize() {
			if (soft.length > 1) {
				Dimension d = super.calcPreferredSize();
				if ((soft[0].getText() == null || soft[0].getText().equals(""))
						&& (soft[1].getText() == null || soft[1].getText()
								.equals("")) && soft[0].getIcon() == null
						&& soft[1].getIcon() == null) {
					d.setHeight(0);
				}
				return d;
			}
			return super.calcPreferredSize();
		}

		/**
		 * Sets the menu transitions for showing/hiding the menu, can be null...
		 */
		public void setTransitions(Transition transitionIn,
				Transition transitionOut) {
			this.transitionIn = transitionIn;
			this.transitionOut = transitionOut;
		}

		private void showMenu() {
			final Dialog d = new Dialog();
			d.setDialogStyle(menuStyle);
			d.setMenu(true);
			menuStyle.removeStyleListener(d.getContentPane());

			d.setTransitionInAnimator(transitionIn);
			d.setTransitionOutAnimator(transitionOut);
			d.setLayout(new BorderLayout());
			d.setScrollable(false);
			((Form) d).menuBar.commandList = createCommandList(commands);
			if (menuCellRenderer != null) {
				((Form) d).menuBar.commandList
						.setListCellRenderer(menuCellRenderer);
			}
			d.getContentPane().getStyle().setPadding(0, 0, 0, 0);
			d.addComponent(BorderLayout.CENTER, ((Form) d).menuBar.commandList);
			LookAndFeel lf = UIManager.getInstance().getLookAndFeel();
			selectMenuItem = new Command(UIManager.getInstance().localize(
					"select", "Select"), lf.getMenuIcons()[0]);
			cancelMenuItem = new Command(UIManager.getInstance().localize(
					"cancel", "Cancel"), lf.getMenuIcons()[1]);

			if (Display.getInstance().isThirdSoftButton()) {
				d.addCommand(selectMenuItem);
				d.addCommand(cancelMenuItem);
			} else {
				d.addCommand(cancelMenuItem);
				d.addCommand(selectMenuItem);
			}

			d.setClearCommand(cancelMenuItem);
			d.setBackCommand(cancelMenuItem);

			((Form) d).menuBar.commandList
					.addActionListener(((Form) d).menuBar);
			Command result = showMenuDialog(d);
			if (result != cancelMenuItem) {
				Command c = null;
				if (result == selectMenuItem) {
					c = (Command) ((Form) d).menuBar.commandList
							.getSelectedItem();
				} else {
					c = result;
				}
				// menu item was handled internally in a touch interface
				if (c != null) {
					ActionEvent e = new ActionEvent(c);
					c.actionPerformed(e);
					actionCommandImpl(c);
				}
			}
			((Form) d).menuBar.commandList
					.removeActionListener(((Form) d).menuBar);
			Form upcoming = Display.getInstance().getCurrentUpcoming();
			if (upcoming == Form.this) {
				d.disposeImpl();
			} else {
				Form.this.tint = (upcoming instanceof Dialog);
			}
		}

		public Button[] getSoftButtons() {
			return soft;
		}

		public void addCommand(Command cmd) {
			// prevent duplicate commands which might happen in some edge cases
			// with the select command
			if (commands.contains(cmd)) {
				return;
			}
			// special case for default commands which are placed at the end and
			// aren't overriden later
			if (soft.length > 2 && cmd == getDefaultCommand()) {
				commands.addElement(cmd);
			} else {
				commands.insertElementAt(cmd, 0);
			}
			updateCommands();
		}

		/**
		 * Returns the command occupying the given index
		 * 
		 * @param index
		 *            offset of the command
		 * @return the command at the given index
		 */
		public Command getCommand(int index) {
			return (Command) commands.elementAt(index);
		}

		public int getCommandCount() {
			return commands.size();
		}

		public void addCommand(Command cmd, int index) {
			// prevent duplicate commands which might happen in some edge cases
			// with the select command
			if (commands.contains(cmd)) {
				return;
			}
			commands.insertElementAt(cmd, index);
			updateCommands();
		}

		public void removeAllCommands() {
			commands.removeAllElements();
			updateCommands();
		}

		public void removeCommand(Command cmd) {
			commands.removeElement(cmd);
			updateCommands();
		}

		public void setMenuCellRenderer(ListCellRenderer menuCellRenderer) {
			this.menuCellRenderer = menuCellRenderer;
		}

		public Style getMenuStyle() {
			return menuStyle;
		}

		public void keyPressed(int keyCode) {
			if (commands.size() > 0) {
				if (keyCode == leftSK) {
					if (left != null) {
						left.pressed();
					}
				} else {
					// it might be a back command or the fire...
					if ((keyCode == rightSK || keyCode == rightSK2)) {
						if (right != null) {
							right.pressed();
						}
					} else {
						if (Display.getInstance().getGameAction(keyCode) == Display.GAME_FIRE) {
							main.pressed();
						}
					}
				}
			}
		}

		public void keyReleased(int keyCode) {
			if (commands.size() > 0) {
				if (soft.length < 2 && keyCode == leftSK) {
					List l = commandList;
					if (l != null) {
						Container parent = l.getParent();
						while (parent != null) {
							if (parent instanceof Dialog
									&& ((Dialog) parent).isMenu()) {
								return;
							}
							parent = parent.getParent();
						}
					}
					showMenu();
					return;
				} else {
					if (keyCode == leftSK) {
						if (left != null) {
							left.released();
						}
						return;
					} else {
						// it might be a back command...
						if ((keyCode == rightSK || keyCode == rightSK2)) {
							if (right != null) {
								right.released();
							}
							return;
						} else {
							if (Display.getInstance().getGameAction(keyCode) == Display.GAME_FIRE) {
								main.released();
								return;
							}
						}
					}
				}
			}

			// allows a back/clear command to occur regardless of whether the
			// command was added to the form
			Command c = null;
			if (keyCode == backSK) {
				// the back command should be invoked
				c = getBackCommand();
			} else {
				if (keyCode == clearSK || keyCode == backspaceSK) {
					c = getClearCommand();
				}
			}
			if (c != null) {
				c.actionPerformed(new ActionEvent(c, keyCode));
				actionCommandImpl(c);
			}
		}

		public void refreshTheme() {
			super.refreshTheme();
			if (menuStyle.isModified()) {
				menuStyle.merge(UIManager.getInstance().getComponentStyle(
						"Menu"));
			} else {
				menuStyle = UIManager.getInstance().getComponentStyle("Menu");
			}
			if (menuCellRenderer != null) {
				List tmp = new List();
				tmp.setListCellRenderer(menuCellRenderer);
				tmp.refreshTheme();
			}
			for (int iter = 0; iter < soft.length; iter++) {
				soft[iter].getStyle().merge(getStyle());
			}

			revalidate();
		}
		
		Vector getCommandList(){
			return commands;
		}

	}

}
