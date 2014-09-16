package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.geom.*;
import com.sun.dtv.lwuit.plaf.DefaultLookAndFeel;
import com.sun.dtv.lwuit.plaf.LookAndFeel;
import com.sun.dtv.lwuit.plaf.UIManager;

import com.sun.dtv.ui.ViewOnlyComponent;
import com.sun.dtv.ui.TextLayoutManager;
import com.sun.dtv.ui.Matte;
import com.sun.dtv.ui.MatteException;

import java.util.Hashtable;

import java.awt.AWTEvent;

public class Label extends Component implements ViewOnlyComponent {
	private String enabledText = "";
	private String disabledText = "";

	private Image enabledIcon;
	private Image disabledIcon;

	private int hAlignment = ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT;
	private int valign = ViewOnlyComponent.VERTICAL_ALIGN_TOP;
	private int textPosition = RIGHT;
	private int gap = 2;
	private int shiftText = 0;
	private boolean tickerRunning = false;
	private boolean tickerEnabled = true;
	private long tickerStartTime;
	private long tickerDelay;
	private boolean rightToLeft;
	private boolean endsWith3Points = true;
	private int interactionState;

	private TextLayoutManager textLayoutManager;
	private Matte matte;
	private int scalingMode;

	/**
	 * Constructs a new label with the specified string of text, left justified.
	 * 
	 * 
	 * @param text
	 *            - the string that the label presents.
	 */
	public Label(String text) {
		this.enabledText = text;
		this.disabledText = text;
		interactionState = ViewOnlyComponent.STATE_ENABLED;
		localize();
		setFocusable(false);
		setUIID("Label");

	}

	/**
	 * Construct an empty label.
	 * 
	 * 
	 */
	public Label() {
		this("");
	}

	/**
	 * Constructs a new label with the specified icon.
	 * 
	 * 
	 * @param icon
	 *            - the image that the label presents.
	 */
	public Label(Image icon) {
		this("");

		this.enabledIcon = icon;
		this.disabledIcon = icon;
	}

	/**
	 * <B>Description copied from class:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#getBaselineResizeBehavior()">Component</A></CODE>
	 * </B></DD> Returns a constant indicating how the baseline varies with the
	 * size of the component.
	 * 
	 * 
	 * @return one of BRB_CONSTANT_ASCENT, BRB_CONSTANT_DESCENT,
	 *         BRB_CENTER_OFFSET or BRB_OTHER
	 * @see getBaselineResizeBehavior in class Component
	 */
	public int getBaselineResizeBehavior() {
		switch (valign) {
		case TOP:
			return BRB_CONSTANT_ASCENT;
		case BOTTOM:
			return BRB_CONSTANT_DESCENT;
		case CENTER:
			return BRB_CENTER_OFFSET;
		default:
			return BRB_OTHER;
		}
	}

	/**
	 * Sets the Label text. Convenience method, similar to #setTextContent
	 * 
	 * 
	 * @param text
	 *            - the string that the label presents.
	 * @see getText()
	 */
	public void setText(String text) {
		this.enabledText = text;
		this.disabledText = text;

		localize();
		setShouldCalcPreferredSize(true);
		repaint();
	}

	/**
	 * Returns the label text. Convenience method, similar to #getTextContent
	 * 
	 * 
	 * 
	 * @return the label text
	 * @see setText(java.lang.String)
	 */
	public String getText() {
		return this.getTextContent(this.getInteractionState());
	}

	private void localize() {
		this.enabledText = UIManager.getInstance().localize(this.getText(),
				this.getText());
		this.disabledText = UIManager.getInstance().localize(this.getText(),
				this.getText());
	}

	private void localizeEnabled() {
		this.enabledText = UIManager.getInstance().localize(
				this.getTextContent(ViewOnlyComponent.STATE_ENABLED),
				this.getTextContent(ViewOnlyComponent.STATE_ENABLED));
	}

	private void localizeDisabled() {
		this.disabledText = UIManager.getInstance().localize(
				this.getTextContent(ViewOnlyComponent.STATE_DISABLED),
				this.getTextContent(ViewOnlyComponent.STATE_DISABLED));
	}

	void initComponentImpl() {
		super.initComponentImpl();
		if (hasFocus()) {
			LookAndFeel lf = UIManager.getInstance().getLookAndFeel();
			if (lf instanceof DefaultLookAndFeel) {
				((DefaultLookAndFeel) lf).focusGained(this);
			}
		}
	}

	/**
	 * Sets the Label icon. Convenience method, similar to #setGraphicsContent
	 * 
	 * 
	 * @param icon
	 *            - the image that the label presents.
	 * @see getIcon()
	 */
	public void setIcon(Image icon) {
		this.enabledIcon = icon;
		this.disabledIcon = icon;
		setShouldCalcPreferredSize(true);
		checkAnimation();
		repaint();

	}

	void checkAnimation() {
		super.checkAnimation();
		if (this.getIcon() != null && this.getIcon().isAnimation()) {
			Form parent = getComponentForm();
			if (parent != null) {
				parent.registerAnimated(this);
			}
		}
	}

	/**
	 * Returns the labels icon. Convenience method, similar to
	 * #getGraphicsContent
	 * 
	 * 
	 * 
	 * @return the labels icon
	 * @see setIcon(com.sun.dtv.lwuit.Image)
	 */
	public Image getIcon() {
		return this.getGraphicContent(this.getInteractionState());
	}

	/**
	 * Convenience method, similar to #setHorizontalAlignment
	 * 
	 * 
	 */
	public void setAlignment(int align) {
		setHorizontalAlignment(align);
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setVerticalAlignment(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Set the vertical alignment for any state-based content in this
	 * component.
	 * 
	 * 
	 * @param valign
	 *            - the vertical alignment mode, one of
	 *            ViewOnlyComponent.VERTICAL_ALIGN_TOP,
	 *            ViewOnlyComponent.VERTICAL_ALIGN_CENTER,
	 *            ViewOnlyComponent.VERTICAL_ALIGN_BOTTOM or
	 *            ViewOnlyComponent.VERTICAL_ALIGN_JUSTIFIED.
	 * @see setVerticalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getVerticalAlignment()
	 */
	public void setVerticalAlignment(int alignment) {
		if (alignment != VERTICAL_ALIGN_CENTER && alignment != VERTICAL_ALIGN_TOP && alignment != VERTICAL_ALIGN_BOTTOM) {
			throw new IllegalArgumentException("Alignment can't be set to "
					+ alignment);
		}
		this.valign = alignment;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getVerticalAlignment()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Retrieve the vertical alignment for any state-based content in
	 * this component.
	 * 
	 * 
	 * @return the current horizontal alignment mode, one of
	 *         ViewOnlyComponent.VERTICAL_ALIGN_TOP,
	 *         ViewOnlyComponent.VERTICAL_ALIGN_CENTER,
	 *         ViewOnlyComponent.VERTICAL_ALIGN_BOTTOM or
	 *         ViewOnlyComponent.VERTICAL_ALIGN_JUSTIFIED.
	 * @see getVerticalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setVerticalAlignment(int)
	 */
	public int getVerticalAlignment() {
		return valign;
	}

	/**
	 * Returns the alignment of the Label. Convenience method, similar to 
	 * #getHorizontalAlignment
	 * Returns: the current alignment mode, one of 
	 * ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT, ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER, 
	 * ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT or ViewOnlyComponent.HORIZONTAL_ALIGN_JUSTIFIED.
	 * See Also:setAlignment(int)
	 */
	public int getAlignment() {
		return getHorizontalAlignment();
	}

	/**
	 * Sets the position of the text relative to the icon if exists.
	 * 
	 * 
	 * @param textPosition
	 *            - alignment value (LEFT, RIGHT, BOTTOM or TOP)
	 * @see getTextPosition(), Component.LEFT, Component.RIGHT,
	 *      Component.BOTTOM, Component.TOP
	 */
	public void setTextPosition(int textPosition) {
		if (textPosition != Component.LEFT && textPosition != Component.RIGHT
				&& textPosition != Component.BOTTOM && textPosition != Component.TOP) {
			throw new IllegalArgumentException("Text position can't be set to "
					+ textPosition);
		}
		this.textPosition = textPosition;
	}

	/**
	 * Returns The position of the text relative to the icon.
	 * 
	 * 
	 * 
	 * @return The position of the text relative to the icon, one of: LEFT,
	 *         RIGHT, BOTTOM, TOP
	 * @see setTextPosition(int), Component.LEFT, Component.RIGHT,
	 *      Component.BOTTOM, Component.TOP
	 */
	public int getTextPosition() {
		return textPosition;
	}

	/**
	 * Set the gap in pixels between the icon/text to the Label boundaries.
	 * 
	 * 
	 * @param gap
	 *            - the gap in pixels
	 * @see getGap()
	 */
	public void setGap(int gap) {
		this.gap = gap;
	}

	/**
	 * Returns the gap in pixels between the icon/text to the Label boundaries.
	 * 
	 * 
	 * 
	 * @return the gap in pixels between the icon/text to the Label boundaries
	 * @see setGap(int)
	 */
	public int getGap() {
		return this.gap;
	}

	/**
	 * <B>Description copied from class:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#paint(com.sun.dtv.lwuit.Graphics)">Component</A></CODE>
	 * </B></DD> This method paints the Component on the screen, it should be
	 * overriden by subclasses to perform custom drawing or invoke the UI API's
	 * to let the PLAF perform the rendering.
	 * 
	 * 
	 * @param g
	 *            - the component graphics
	 * @see paint in interface Animation
	 * @see paint in interface ViewOnlyComponent
	 * @see paint in class Component
	 */
	public void paint(Graphics g) {
		UIManager.getInstance().getLookAndFeel().drawLabel(g, this);
	}

	/**
	 * Simple getter to return how many pixels to shift the text inside the
	 * Label.
	 * 
	 * 
	 * @return number of pixels to shift
	 * @see setShiftText(int)
	 */
	public int getShiftText() {
		return this.shiftText;
	}

	/**
	 * This method shifts the text from it's position in pixels. The value can
	 * be positive/negative to move the text to the right/left.
	 * 
	 * 
	 * @param shiftText
	 *            - The number of pixels to move the text
	 * @see getShiftText()
	 */
	public void setShiftText(int shiftText) {
		this.shiftText = shiftText;
	}

	/**
	 * This method will start the text ticker.
	 * 
	 * 
	 * @param delay
	 *            - the delay in milliseconds between animation intervals
	 * @param rightToLeft
	 *            - if true move the text to the left
	 */
	public void startTicker(long delay, boolean rightToLeft) {
		// return if ticker is not enabled
		if (!tickerEnabled) {
			return;
		}

		if (!isCellRenderer()) {
			Form parent = getComponentForm();
			if (parent == null) {
				throw new IllegalArgumentException(	"This method cannot be called"	+ " before the Label is fully initialized");
			}
			parent.registerAnimated(this);
		}

		tickerStartTime = System.currentTimeMillis();
		tickerDelay = delay;
		tickerRunning = true;
		this.rightToLeft = rightToLeft;
	}

	/**
	 * Stops the text ticker.
	 * 
	 * 
	 */
	public void stopTicker() {
		tickerRunning = false;
		setShiftText(0);
	}

	/**
	 * Returns true if the ticker is running.
	 * 
	 * 
	 * 
	 * @return true if the ticker is running
	 */
	public boolean isTickerRunning() {
		return tickerRunning;
	}

	/**
	 * Sets the Label to allow ticking of the text. By default is true.
	 * 
	 * 
	 * @param tickerEnabled
	 *            - indicate whether ticker is enabled
	 */
	public void setTickerEnabled(boolean tickerEnabled) {
		this.tickerEnabled = tickerEnabled;
	}

	/**
	 * This method return true if the ticker is enabled on this Label.
	 * 
	 * 
	 * @return true if ticker is enabled, false otherwise
	 */
	public boolean isTickerEnabled() {
		return tickerEnabled;
	}

	/**
	 * If the Label text is too long fit the text to the widget and add "..."
	 * points at the end. By default this is set to true.
	 * 
	 * 
	 * @param endsWith3Points
	 *            - true if text should add "..." at the end
	 */
	public void setEndsWith3Points(boolean endsWith3Points) {
		this.endsWith3Points = endsWith3Points;
	}

	/**
	 * Simple getter.
	 * 
	 * 
	 * 
	 * @return true if this Label adds "..." when the text is too long
	 */
	public boolean isEndsWith3Points() {
		return endsWith3Points;
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
	 * @see animate in class Component
	 */
	public boolean animate() {
		boolean animateTicker = false;

		if (tickerRunning
				&& tickerStartTime + tickerDelay < System.currentTimeMillis()) {
			tickerStartTime = System.currentTimeMillis();
			if (rightToLeft) {
				shiftText -= 2;
			} else {
				shiftText += 2;
			}
			animateTicker = true;
		}
		// if we have an animated icon then just let it do its thing...
		boolean val = this.getIcon() != null && this.getIcon().isAnimation() && ((Animation) this.getIcon()).animate();
		boolean parent = super.animate();
		return val || parent || animateTicker;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setTextContent(java.lang.String, int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Assigns text content to this component which is
	 * state-dependent.
	 * 
	 * 
	 * @param text
	 *            - the text content, or null (removes text content which had
	 *            been assigned before)
	 * @param state
	 *            - the state of the component for which this content should be
	 *            displayed.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see setTextContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getTextContent(int)
	 */
	public void setTextContent(String text, int state) throws IllegalArgumentException 
	{
		if (state == ViewOnlyComponent.STATE_ENABLED) {
			this.enabledText = text;
			localizeEnabled();
		} else if (state == ViewOnlyComponent.STATE_DISABLED) {
			this.disabledText = text;
			localizeDisabled();
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setGraphicContent(com.sun.dtv.lwuit.Image, int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Assigns graphical content to this component which is
	 * state-dependent.
	 * 
	 * 
	 * @param image
	 *            - the graphical content, or null (removes graphical content
	 *            which had been assigned before)
	 * @param state
	 *            - the state of the component for which this content should be
	 *            displayed.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see setGraphicContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getGraphicContent(int)
	 */
	public void setGraphicContent(Image image, int state) throws IllegalArgumentException 
	{
		if (state == ViewOnlyComponent.STATE_ENABLED) 
		{
			this.enabledIcon = image;
		} 
		else if (state == ViewOnlyComponent.STATE_DISABLED) 
		{
			this.disabledIcon = image;
		}
		else 
		{
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setAnimateContent(com.sun.dtv.lwuit.Image[], int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Assigns an array of graphical content to be used for animation
	 * to this component which is state-dependent.
	 * 
	 * 
	 * @param images
	 *            - the graphical content array, or null (removes any graphical
	 *            content which had been assigned before)
	 * @param state
	 *            - the state of the component for which this content should be
	 *            displayed.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see setAnimateContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getAnimateContent(int)
	 */
	public void setAnimateContent(Image[] images, int state)
			throws IllegalArgumentException {
		// TODO:: implement setAnimateContent
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getTextContent(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Returns the text content for this component, depending on the
	 * current state.
	 * 
	 * 
	 * @param state
	 *            - The state for which content is to be retrieved.
	 * @return The text content associated with the specified state, or null if
	 *         there is none.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see getTextContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setTextContent(java.lang.String, int)
	 */
	public String getTextContent(int state) throws IllegalArgumentException {
		if (state == ViewOnlyComponent.STATE_ENABLED) {
			return this.enabledText;
		} else if (state == ViewOnlyComponent.STATE_DISABLED) {
			return this.disabledText;
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getGraphicContent(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Returns the graphical content for this component, depending on
	 * the current state.
	 * 
	 * 
	 * @param state
	 *            - The state for which content is to be retrieved.
	 * @return The graphical content associated with the specified state, or
	 *         null if there is none.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see getGraphicContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setGraphicContent(com.sun.dtv.lwuit.Image, int)
	 */
	public Image getGraphicContent(int state) throws IllegalArgumentException {
		if (state == ViewOnlyComponent.STATE_ENABLED) {
			return this.enabledIcon;
		} else if (state == ViewOnlyComponent.STATE_DISABLED) {
			return this.disabledIcon;
		} else {
			throw new IllegalArgumentException("Invalid state");
		}
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getAnimateContent(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Returns the animated content for this component, depending on
	 * the current state.
	 * 
	 * 
	 * @param state
	 *            - The state for which content is to be retrieved.
	 * @return The graphical content associated with the specified state, or
	 *         null if there is none.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see getAnimateContent in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setAnimateContent(com.sun.dtv.lwuit.Image[], int)
	 */
	public Image[] getAnimateContent(int state) throws IllegalArgumentException {
		// TODO:: implement getAnimateContent
		return null;
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
		super.setEnabled(enabled);
		if(enabled)
			setInteractionState(ViewOnlyComponent.STATE_ENABLED);
		else
			setInteractionState(ViewOnlyComponent.STATE_DISABLED);
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setInteractionState(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Set the current state of the component with respect to
	 * interaction.
	 * 
	 * 
	 * @param state
	 *            - the interaction state for this component.
	 * @throws IllegalArgumentException
	 *             - if the state parameter does not represent a state
	 * @see setInteractionState in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getInteractionState()
	 */
	public void setInteractionState(int state) throws IllegalArgumentException {
		if (state != ViewOnlyComponent.STATE_ENABLED
				&& state != ViewOnlyComponent.STATE_DISABLED) {
			throw new IllegalArgumentException(
					"The state parameter does not represent a state");
		}

		this.interactionState = state;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getInteractionState()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Return the current interaction state of the component.
	 * 
	 * 
	 * @return the current interaction state of the component.
	 * @see getInteractionState in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setInteractionState(int)
	 */
	public int getInteractionState() {
		return this.interactionState;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setTextLayoutManager(com.sun.dtv.ui.TextLayoutManager)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Sets the text layout manager that should be used for text
	 * layout in this component.
	 * 
	 * 
	 * @param manager
	 *            - the TextLayoutManager
	 * @see setTextLayoutManager in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getTextLayoutManager()
	 */
	public void setTextLayoutManager(TextLayoutManager manager) {
		this.textLayoutManager = manager;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getTextLayoutManager()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Gets the text layout manager that is currently in use for text
	 * layout in this component.
	 * 
	 * 
	 * @return the TextLayoutManager
	 * @see getTextLayoutManager in interface ViewOnlyComponent
	 * @see 
	 *      ViewOnlyComponent.setTextLayoutManager(com.sun.dtv.ui.TextLayoutManager
	 *      )
	 */
	public TextLayoutManager getTextLayoutManager() {
		return this.textLayoutManager;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setHorizontalAlignment(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Set the horizontal alignment for any state-based content in
	 * this component.
	 * 
	 * 
	 * @param alignment
	 *            - the horizontal alignment mode, one of
	 *            ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT,
	 *            ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER,
	 *            ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT or
	 *            ViewOnlyComponent.HORIZONTAL_ALIGN_JUSTIFIED.
	 * @see setHorizontalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getHorizontalAlignment()
	 */
	public void setHorizontalAlignment(int alignment) {
		if (alignment != HORIZONTAL_ALIGN_LEFT && alignment != HORIZONTAL_ALIGN_CENTER 
		    && alignment != HORIZONTAL_ALIGN_RIGHT && alignment != HORIZONTAL_ALIGN_JUSTIFIED) 
		{
			throw new IllegalArgumentException("Alignment can't be set to "
					+ alignment);
		}
		this.hAlignment = alignment;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getHorizontalAlignment()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Retrieve the horizontal alignment for any state-based content
	 * in this component.
	 * 
	 * 
	 * @return the current horizontal alignment mode, one of
	 *         ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT,
	 *         ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER,
	 *         ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT or
	 *         ViewOnlyComponent.HORIZONTAL_ALIGN_JUSTIFIED.
	 * @see getHorizontalAlignment in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setHorizontalAlignment(int)
	 */
	public int getHorizontalAlignment() {
		return this.hAlignment;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#setScalingMode(int)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Set the scaling mode for this component.
	 * 
	 * 
	 * @param scaling
	 *            - the scaling mode, one of ViewOnlyComponent.SCALE_NO,
	 *            ViewOnlyComponent.SCALE_NO_ASPECT_PROOF or
	 *            ViewOnlyComponent.SCALE_ASPECT_PROOF.
	 * @see setScalingMode in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.getScalingMode()
	 */
	public void setScalingMode(int scaling) {
		this.scalingMode = scaling;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#getScalingMode()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Retrieve the scaling mode for this component.
	 * 
	 * 
	 * @return the current scaling mode, one of ViewOnlyComponent.SCALE_NO,
	 *         ViewOnlyComponent.SCALE_NO_ASPECT_PROOF or
	 *         ViewOnlyComponent.SCALE_ASPECT_PROOF.
	 * @see getScalingMode in interface ViewOnlyComponent
	 * @see ViewOnlyComponent.setScalingMode(int)
	 */
	public int getScalingMode() {
		return this.scalingMode;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#isDoubleBuffered()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Returns true if double buffering is available and activated.
	 * 
	 * 
	 * @return true if double buffering is available and activated, and false
	 *         otherwise
	 * @see isDoubleBuffered in interface ViewOnlyComponent
	 */
	public boolean isDoubleBuffered() {
		return true;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#isOpaque()">ViewOnlyComponent</A></CODE>
	 * </B></DD> Returns true if the whole area of the Component (as returned by
	 * the <code>com.sun.dtv.lwuit.Component#getBounds</code> method, is opaque.
	 * 
	 * 
	 * @return true if all the pixels within the area defined by the
	 *         com.sun.dtv.lwuit.Component#getBounds method are opaque, i.e. all
	 *         pixels are painted in an opaque color, false otherwise.
	 * @see isOpaque in interface ViewOnlyComponent
	 */
	public boolean isOpaque() {
		return true;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/ViewOnlyComponent.html#processEvent(java.awt.AWTEvent)">ViewOnlyComponent</A></CODE>
	 * </B></DD> Handle the specified AWTEvent.
	 * 
	 * 
	 * @param event
	 *            - a java.awt.AWTEvent to handle.
	 * @see processEvent in interface ViewOnlyComponent
	 */
	public void processEvent(AWTEvent event) {
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#setMatte(com.sun.dtv.ui.Matte)">MatteEnabled</A></CODE>
	 * </B></DD> Adds an <A HREF="../../../../com/sun/dtv/ui/Matte.html"
	 * title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> to the
	 * component implementing this interface in order to enable matte
	 * compositing. If there is already a Matte assigned to the component, and
	 * this Matte is animated, it has to be stopped before any call to this
	 * method.
	 * 
	 * 
	 * @param matte
	 *            - the Matte to be assigned to the component. At any point of
	 *            time there can only be one matte associated to the component,
	 *            that's way any matte that has been associated before will be
	 *            overriden by a call to this method. The Matte parameter can
	 *            also be null, in this case there is no matte associated with
	 *            the component after the call, even if there had been one
	 *            before.
	 * @throws MatteException
	 *             - if the Matte has an unsupported type, the platform does not
	 *             support mattes at all, or an animated matte is associated
	 *             with the component and is still running
	 * @see setMatte in interface MatteEnabled
	 * @see MatteEnabled.getMatte()
	 */
	public void setMatte(Matte matte) throws MatteException {
		this.matte = matte;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/ui/MatteEnabled.html#getMatte()">MatteEnabled</A></CODE>
	 * </B></DD> Return the <A HREF="../../../../com/sun/dtv/ui/Matte.html"
	 * title="interface in com.sun.dtv.ui"><CODE>Matte</CODE></A> currently
	 * associated with the component implementing this interface.
	 * 
	 * 
	 * @return the Matte currently associated with the component or null if
	 *         there is none
	 * @see getMatte in interface MatteEnabled
	 * @see MatteEnabled.setMatte(com.sun.dtv.ui.Matte)
	 */
	public Matte getMatte() {
		return this.matte;
	}

	protected Dimension calcPreferredSize() {
		return UIManager.getInstance().getLookAndFeel().getLabelPreferredSize(this);
	}

}
