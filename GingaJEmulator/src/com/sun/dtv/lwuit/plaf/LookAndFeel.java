package com.sun.dtv.lwuit.plaf;

import java.awt.Color;

import com.sun.dtv.lwuit.*;
import com.sun.dtv.lwuit.animations.Transition;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;
import com.sun.dtv.lwuit.list.ListCellRenderer;

public abstract class LookAndFeel extends Object
{
	private Transition defaultFormTransitionIn;
	private Transition defaultFormTransitionOut;
	private Transition defaultMenuTransitionIn;
	private Transition defaultMenuTransitionOut;
	private Transition defaultDialogTransitionIn;
	private Transition defaultDialogTransitionOut;
	private boolean reverseSoftButtons;
	private ListCellRenderer menuRenderer;
	private Image[] menuIcons;
	private Color defaultFormTintColor;
	private Color disableColor;
	
	private int defaultSmoothScrollingSpeed;
	private boolean defaultSmoothScrolling;
    private boolean fadeScrollBar = true;
    private int scrollOpacity = 0xff;
    
    
    private boolean fadeScrollEdge;
    private int fadeScrollEdgeLength = 15;
    private Image fadeScrollTop;
    private Image fadeScrollBottom;
    private Image fadeScrollRight;
    private Image fadeScrollLeft;
    private int fadeScrollEdgeStartAlpha = 0x999999;
    private int fadeScrollEdgeEndAlpha = 0;
    
    private Component verticalScroll;
    private Component horizontalScroll;
    private Component verticalScrollThumb;
    private Component horizontalScrollThumb;
	/**
	 * Constructor
	 * 
	 */
	public LookAndFeel()
	{
		defaultFormTintColor = new Color(0x0, 0x0, 0x0, 0xff);
		disableColor = new Color(0xcc, 0xcc, 0xcc, 0xff);
		menuIcons = new Image[3];
		defaultSmoothScrolling = true;
		defaultSmoothScrollingSpeed = 150;
		fadeScrollTop = null;
		fadeScrollBottom = null;
		fadeScrollRight = null;
		fadeScrollLeft = null;
		fadeScrollEdge = true;
	}

	/**
	 * Every component binds itself to the look and feel thus allowing the look
	 * and feel to customize the component.  Binding occurs at the end of the
	 * constructor when the component is in a valid state and ready to be used.
	 * Notice that a component might be bound twice or more and it is the
	 * responsibility of the LookAndFeel to protect against that.
	 *
	 * 
	 * @param cmp - component instance that may be customized by the look and feel
	 */
	public void bind(Component cmp)
	{
		System.out.println("com.sun.dtv.lwuit.plaf.LookAndFeel.bind(): method not implemented");
	}

	/**
	 * Invoked when a look and feel is removed, allows a look and feel to release
	 * resources related to binding components.
	 *
	 * 
	 * @see bind(Component)
	 */
	public void uninstall()
	{
		System.out.println("com.sun.dtv.lwuit.plaf.LookAndFeel.uninstall(): method not implemented");
	}

	/**
	 * Invoked for drawing a button widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param b - the button
	 */
	public abstract void drawButton(Graphics g, Button b);

	/**
	 * Invoked for drawing a checkbox widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param cb - the check box
	 */
	public abstract void drawCheckBox(Graphics g, CheckBox cb);

	/**
	 * Invoked for drawing a combo box widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param cb - the combo box
	 */
	public abstract void drawComboBox(Graphics g, ComboBox cb);

	/**
	 * Invoked for drawing a label widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param l - the label
	 */
	public abstract void drawLabel(Graphics g, Label l);

	/**
	 * Invoked for drawing a list widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param l - the list
	 */
	public abstract void drawList( Graphics g, List l);

	/**
	 * Invoked for drawing a month view widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param cal - the calendar
	 * @param mv - the month view component
	 */
	public abstract void drawMonthView(Graphics g, Calendar cal, Component mv);

	/**
	 * Returns the day of the month in the month view at the given relative
	 * component X/Y offsets. This is important for touch screen interaction.
	 *
	 * 
	 * @param x - x coordinate of offset
	 * @param y - y coordinate of offset
	 * @param cal - the calendar
	 * @param mv - the month view component
	 * @return the day of the month
	 */
	public abstract long findDayAt(int x, int y, Calendar cal, Component mv);

	/**
	 * Invoked for drawing the radio button widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param rb - the radio button
	 */
	public abstract void drawRadioButton(Graphics g, RadioButton rb);

	/**
	 * Invoked for drawing the text area widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param ta - the text area
	 */
	public abstract void drawTextArea(Graphics g, TextArea ta);

	/**
	 * Draws the text field without its cursor which is drawn in a separate method.
	 * Input mode indication can also be drawn using this method.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param tf - the text field
	 */
	public abstract void drawTextField(Graphics g, TextField tf);

	/**
	 * Draws the cursor of the text field, blinking is handled simply by avoiding
	 * a call to this method.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param tf - the text field
	 */
	public abstract void drawTextFieldCursor(Graphics g, TextField tf);

	/**
	 * Invoked for drawing the Tab Pane widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param tp - the tabbed pane
	 */
	public abstract void drawTabbedPane(Graphics g, TabbedPane tp);

	/**
	 * Returns the preferred size for the button.
	 *
	 * 
	 * @param b - the button
	 * @return the preferred size for the button
	 */
	public abstract Dimension getButtonPreferredSize(Button b);

	/**
	 * Returns the preferred size for the checkbox.
	 *
	 * 
	 * @param cb - the checkbox
	 * @return the preferred size for the checkbox
	 */
	public abstract Dimension getCheckBoxPreferredSize(CheckBox cb);

	/**
	 * Returns the preferred size for the label.
	 *
	 * 
	 * @param l - the label
	 * @return the preferred size for the label
	 */
	public abstract Dimension getLabelPreferredSize(Label l);

	/**
	 * Returns the preferred size for the list.
	 *
	 * 
	 * @param l - the list
	 * @return the preferred size for the list
	 */
	public abstract Dimension getListPreferredSize(List l);

	/**
	 * Returns the preferred size for the month view component.
	 *
	 * 
	 * @param mv - the month view component
	 * @return the preferred size for the month view component
	 */
	public abstract Dimension getMonthViewPreferredSize(Component mv);

	/**
	 * Returns the preferred size for the radio button.
	 *
	 * 
	 * @param rb - the radio button
	 * @return the preferred size for the radio button
	 */
	public abstract Dimension getRadioButtonPreferredSize(RadioButton rb);

	/**
	 * Returns the preferred size for the text area.
	 *
	 * 
	 * @param ta - the text area
	 * @return the preferred size for the text area
	 */
	public abstract Dimension getTextAreaPreferredSize( TextArea ta);

	/**
	 * Returns the preferred size for the text field.
	 *
	 * 
	 * @param tf - the text field
	 * @return the preferred size for the text field
	 */
	public abstract Dimension getTextFieldPreferredSize( TextField tf);

	/**
	 * Returns the preferred size for the combo box.
	 *
	 * 
	 * @param box - the combo box
	 * @return the preferred size for the combo box
	 */
	public abstract Dimension getComboBoxPreferredSize( ComboBox box);
	private void initScroll() {
        verticalScroll = new Label();
        verticalScroll.setUIID("Scroll");
        horizontalScroll = new Label();
        horizontalScroll.setUIID("HorizontalScroll");
        verticalScrollThumb = new Label();;
        verticalScrollThumb.setUIID("ScrollThumb");
        horizontalScrollThumb = new Label();
        horizontalScrollThumb.setUIID("HorizontalScrollThumb");
    }
	
	private void drawBackgroundImage(Graphics g, Style s, int x, int y, int width, int height) 
	{
		if(width == 0 || height == 0) {
			return;
		}

		Image bgImage = s.getBgImage();

		if(bgImage != null) {
			if (s.isScaleImage()) {
				if (bgImage.getWidth() != width || bgImage.getHeight() != height) {
					bgImage = bgImage.scaled(width, height);
				}

				g.drawImage(bgImage, x, y);
			} else {
				int iW = bgImage.getWidth();
				int iH = bgImage.getHeight();

				for (int xPos = 0; xPos < width; xPos += iW) {
					for (int yPos = 0; yPos < height; yPos += iH) {
						g.drawImage(bgImage, x + xPos, y + yPos);
					}
				}
			}
		}
	}

	/**
	 * Sets the foreground color and font for a generic component, reuse-able
	 * by most component drawing code.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param c - the component
	 */
	public void setFG(Graphics g, Component c)
	{
		Style s = c.getStyle();
		
		g.setFont(s.getFont());
		
		if(c.isEnabled()) {
			if(c.hasFocus() && c.isFocusPainted()){
				g.setColor(s.getFgSelectionColor());
			}else{
				g.setColor(s.getFgColor());
			}
		} else {
			g.setColor(Color.BLACK);
		}
	}
	
	public void setBG(Graphics g, Component c)
	{
		Style s = c.getStyle();
		
		g.setFont(s.getFont());
		
		if(c.isEnabled()) {
			if(c.hasFocus() && c.isFocusPainted()){
				Color color = s.getBgSelectionColor();
				g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), s.getBgTransparency()+128));
			}else{
				Color color = s.getBgColor();
				g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), s.getBgTransparency()+128));
			}
		} else {
			Color color = s.getBgColor();
			disableColor = color.darker();
			Color newColor = new Color(disableColor.getRed(), disableColor.getGreen(), disableColor.getBlue(), s.getBgTransparency()+128);
			g.setColor(newColor);
		}
	}

	/**
	 * Returns the default width of a vertical scroll bar.
	 *
	 * 
	 * 
	 * @return default width of a vertical scroll bar
	 */
	public int getVerticalScrollWidth()
	{
		if(verticalScroll == null) 
		{
            initScroll();
        }
		Style scrollStyle = verticalScroll.getStyle();
		return scrollStyle.getMargin(Component.LEFT) + scrollStyle.getMargin(Component.RIGHT) + scrollStyle.getPadding(Component.LEFT) + scrollStyle.getPadding(Component.RIGHT);
	}

	/**
	 * Returns the default height of a horizontal scroll bar.
	 *
	 * 
	 * @return default height of a horizontal scroll bar
	 */
	public int getHorizontalScrollHeight()
	{
		if(horizontalScroll == null) 
		{
            initScroll();
        }
		Style s = UIManager.getInstance().getComponentStyle("Scroll");
		return s.getMargin(Component.TOP) + s.getMargin(Component.BOTTOM) + s.getPadding(Component.TOP) + s.getPadding(Component.BOTTOM);
	}

	/**
	 * Draws and return the TabbedPane cell component (renderer)
	 * according to each tab orientation, the borders are getting draws.
	 *
	 * 
	 * @param tp - the TabbedPane
	 * @param text - the cell text
	 * @param icon - the cell icon image
	 * @param isSelected - is the cell is the selected one
	 * @param cellHasFocus - is the cell has focus
	 * @param cellStyle - the cell Style object
	 * @param tabbedPaneStyle - the TabbedPane Style object
	 * @param cellOffsetX - the offset when the cell is on TOP or BOTTOM orientation
	 * @param cellOffsetY - the offset when the cell is on LEFT or RIGHT orientation
	 * @param cellsPreferredSize - the total cells PreferredSize
	 * @param contentPaneSize - the contentPaneSize
	 * @return A TabbedPane cell component
	 */
	public abstract Component getTabbedPaneCell( TabbedPane tp, String text, Image icon, boolean isSelected, boolean cellHasFocus, Style cellStyle, Style tabbedPaneStyle, int cellOffsetX, int cellOffsetY, Dimension cellsPreferredSize, Dimension contentPaneSize);

	/**
	 * Draws and return the TabbedPane content pane painter.
	 *
	 * 
	 * @param tp - the TabbedPane
	 * @param g - the content pane graphics
	 * @param rect - the content pane painting rectangle area
	 * @param cellsPreferredSize - the total cells PreferredSize
	 * @param numOfTabs - number of tabs
	 * @param selectedTabIndex - the selected tab index
	 * @param tabsSize - the tabs size
	 * @param cellOffsetX - the offset when the cell is on TOP or BOTTOM orientation
	 * @param cellOffsetY - the offset when the cell is on LEFT or RIGHT orientation
	 */
	public abstract void drawTabbedPaneContentPane( TabbedPane tp, Graphics g, Rectangle rect, Dimension cellsPreferredSize, int numOfTabs, int selectedTabIndex, Dimension tabsSize, int cellOffsetX, int cellOffsetY);

	/**
	 * Retrieves the default animation that will draw the transition for
	 * entering a form.
	 *
	 * 
	 * 
	 * @return a Transition object
	 * @see setDefaultFormTransitionIn(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getDefaultFormTransitionIn()
	{
		return defaultFormTransitionIn;
	}

	/**
	 * Allows us to define a default animation that will draw the transition for
	 * entering a form.
	 *
	 * 
	 * @param defaultFormTransitionIn - the default animation
	 * @see getDefaultFormTransitionIn()
	 */
	public void setDefaultFormTransitionIn( Transition defaultFormTransitionIn)
	{
		this.defaultFormTransitionIn = defaultFormTransitionIn;
	}

	/**
	 * Retrieves the default animation that will draw the transition for
	 * entering a form.
	 *
	 * 
	 * 
	 * @return a Transition object
	 * @see setDefaultFormTransitionOut(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getDefaultFormTransitionOut()
	{
		return defaultFormTransitionOut;
	}

	/**
	 * Allows us to define a default animation that will draw the transition for
	 * exiting a form.
	 *
	 * 
	 * @param defaultFormTransitionOut - the default animation
	 * @see getDefaultFormTransitionOut()
	 */
	public void setDefaultFormTransitionOut( Transition defaultFormTransitionOut)
	{
		this.defaultFormTransitionOut = defaultFormTransitionOut;
	}

	/**
	 * Retrieves default animation that will draw the transition for
	 * entering a Menu.
	 *
	 * 
	 * 
	 * @return a Transition object
	 * @see setDefaultMenuTransitionIn(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getDefaultMenuTransitionIn()
	{
		return defaultMenuTransitionIn;
	}

	/**
	 * Allows us to define a default animation that will draw the transition for
	 * entering a Menu.
	 *
	 * 
	 * @param defaultMenuTransitionIn - the default animation
	 * @see getDefaultMenuTransitionIn()
	 */
	public void setDefaultMenuTransitionIn( Transition defaultMenuTransitionIn)
	{
		this.defaultMenuTransitionIn = defaultMenuTransitionIn;
	}

	/**
	 * Retrieves default animation that will draw the transition for
	 * exiting a Menu.
	 *
	 * 
	 * 
	 * @return a Transition object
	 * @see setDefaultMenuTransitionOut(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getDefaultMenuTransitionOut()
	{
		return defaultMenuTransitionOut;
	}

	/**
	 * Allows us to define a default animation that will draw the transition for
	 * exiting a Menu.
	 *
	 * 
	 * @param defaultMenuTransitionOut - the default animation
	 * @see getDefaultMenuTransitionOut()
	 */
	public void setDefaultMenuTransitionOut( Transition defaultMenuTransitionOut)
	{
		this.defaultMenuTransitionOut = defaultMenuTransitionOut;
	}

	/**
	 * Retrieves default animation that will draw the transition for
	 * entering a dialog.
	 *
	 * 
	 * 
	 * @return a Transition object
	 * @see setDefaultDialogTransitionIn(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getDefaultDialogTransitionIn()
	{
		return defaultDialogTransitionIn;
	}

	/**
	 * Allows us to define a default animation that will draw the transition for
	 * entering a dialog.
	 *
	 * 
	 * @param defaultDialogTransitionIn - the default animation
	 * @see getDefaultDialogTransitionIn()
	 */
	public void setDefaultDialogTransitionIn( Transition defaultDialogTransitionIn)
	{
		this.defaultDialogTransitionIn = defaultDialogTransitionIn;
	}

	/**
	 * Retrieves the default animation that will draw the transition for
	 * exiting a dialog.
	 *
	 * 
	 * 
	 * @return a Transition object
	 * @see setDefaultDialogTransitionOut(com.sun.dtv.lwuit.animations.Transition)
	 */
	public Transition getDefaultDialogTransitionOut()
	{
		return defaultDialogTransitionOut;
	}

	/**
	 * Allows us to define a default animation that will draw the transition for
	 * exiting a dialog.
	 *
	 * 
	 * @param defaultDialogTransitionOut - the default animation
	 * @see getDefaultDialogTransitionOut()
	 */
	public void setDefaultDialogTransitionOut( Transition defaultDialogTransitionOut)
	{
		this.defaultDialogTransitionOut = defaultDialogTransitionOut;
	}

	/**
	 * Tint color is set when a form is partially covered be it by a menu or by a
	 * dialog. A look and feel can override this default value.
	 *
	 * 
	 * 
	 * @return the tint color
	 * @see setDefaultFormTintColor(java.awt.Color)
	 */
	public Color getDefaultFormTintColor()
	{
		return defaultFormTintColor;
	}

	/**
	 * Tint color is set when a form is partially covered be it by a menu or by a
	 * dialog. A look and feel can override this default value.
	 *
	 * 
	 * @param defaultFormTintColor - the tint color
	 * @see getDefaultFormTintColor()
	 */
	public void setDefaultFormTintColor( Color defaultFormTintColor)
	{
		this.defaultFormTintColor = defaultFormTintColor;
	}

	/**
	 * This color is used to paint disable mode text color.
	 *
	 * 
	 * 
	 * @return the color value
	 * @see setDisableColor(java.awt.Color)
	 */
	public Color getDisableColor()
	{
		return disableColor;
	}

	/**
	 * Simple setter to disable color.
	 *
	 * 
	 * @param disableColor - the disable color value
	 * @see getDisableColor()
	 */
	public void setDisableColor( Color disableColor)
	{
		this.disableColor = disableColor;
	}

	/**
	 * Indicates whether lists and containers should have smooth scrolling by
	 * default.
	 *
	 * 
	 * 
	 * @return true if smooth scrolling is active
	 * @see setDefaultSmoothScrolling(boolean)
	 */
	public boolean isDefaultSmoothScrolling()
	{
		return defaultSmoothScrolling;
	}

	/**
	 * Indicates whether lists and containers should have smooth scrolling by
	 * default.
	 *
	 * 
	 * @param defaultSmoothScrolling - Indicates whether lists and containers  should have smooth scrolling by default
	 * @see isDefaultSmoothScrolling()
	 */
	public void setDefaultSmoothScrolling(boolean defaultSmoothScrolling)
	{
		this.defaultSmoothScrolling = defaultSmoothScrolling;
	}

	/**
	 * Indicates the default speed for smooth scrolling.
	 *
	 * 
	 * 
	 * @return the default speed
	 * @see setDefaultSmoothScrollingSpeed(int)
	 */
	public int getDefaultSmoothScrollingSpeed()
	{
		return defaultSmoothScrollingSpeed;
	}

	/**
	 * Indicates the default speed for smooth scrolling.
	 *
	 * 
	 * @param defaultSmoothScrollingSpeed - the default speed
	 * @see getDefaultSmoothScrollingSpeed()
	 */
	public void setDefaultSmoothScrollingSpeed(int defaultSmoothScrollingSpeed)
	{
		if(defaultSmoothScrollingSpeed < 1) return;
		else this.defaultSmoothScrollingSpeed = defaultSmoothScrollingSpeed;
	}

	/**
	 * Indicates whether softbuttons should be reversed from their default
	 * orientation.
	 *
	 * 
	 * 
	 * @return true is reverse soft button is active
	 * @see setReverseSoftButtons(boolean)
	 */
	public boolean isReverseSoftButtons()
	{
		return reverseSoftButtons;
	}

	/**
	 * Indicates whether softbuttons should be reversed from their default
	 * orientation.
	 *
	 * 
	 * @param reverseSoftButtons - Indicates whether softbuttons should be  reversed from their default orientation
	 * @see isReverseSoftButtons()
	 */
	public void setReverseSoftButtons(boolean reverseSoftButtons)
	{
		this.reverseSoftButtons = reverseSoftButtons;
	}

	/**
	 * Returns the Menu default renderer.
	 *
	 * 
	 * 
	 * @return the Menu default renderer
	 * @see setMenuRenderer(com.sun.dtv.lwuit.list.ListCellRenderer)
	 */
	public ListCellRenderer getMenuRenderer()
	{
		return menuRenderer;
	}

	/**
	 * Sets the Menu default renderer.
	 *
	 * 
	 * @param menuRenderer - the Menu default renderer
	 * @see getMenuRenderer()
	 */
	public void setMenuRenderer(ListCellRenderer menuRenderer)
	{
		this.menuRenderer = menuRenderer;
	}

	/**
	 * Sets globally the Menu icons.
	 *
	 * 
	 * @param select - the select icon
	 * @param cancel - the cancel icon
	 * @param menu - the menu icon
	 * @see getMenuIcons()
	 */
	public void setMenuIcons(Image select, Image cancel, Image menu) 
	{
		menuIcons[0] = select;
		menuIcons[1] = cancel;
		menuIcons[2] = menu;
	}

	/**
	 * Simple getter for the menu icons.
	 *
	 * 
	 * 
	 * @return an Image array at size of 3, where the first is the select image the second is the cancel image and the last is the menu image.
	 * @see setMenuIcons(com.sun.dtv.lwuit.Image, com.sun.dtv.lwuit.Image, com.sun.dtv.lwuit.Image)
	 */
	public Image[] getMenuIcons()
	{
		return menuIcons;
	}

	private void drawScroll(Graphics g, Component c, float offsetRatio, float blockSizeRatio, boolean isVertical, int x, int y, int width, int height, Component scroll) 
	{
		Style s = UIManager.getInstance().getComponentStyle("Scroll");

		Painter p = s.getBgPainter();
		
		g.setClip(c.getX(), c.getY(), c.getWidth(), c.getHeight());

		if(p != null) {
			p.paint(g, new Rectangle(x, y, width, height));
		} else {
			g.setColor(s.getBgColor());
			g.fillRect(x, y, width, height, s.getBgTransparency());
		}
		
		if (isVertical == false) {
			int offset = (int)(width * offsetRatio);
			int blockSize = (int)(width * blockSizeRatio);

			g.setColor(s.getFgColor());
			g.fillRoundRect(x + offset, y, blockSize, height, 10, 10);
		} else {
			int offset = (int)(height * offsetRatio);
			int blockSize = (int)(height * blockSizeRatio);

			g.setColor(s.getFgColor());
			g.fillRoundRect(x, y + offset, width+10, blockSize, 10, 10);
		}
	}

	/**
	 * Draws a horizontal scroll bar in the given component.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param c - the component
	 * @param offsetRatio - the offset ratio
	 * @param blockSizeRatio - the block size ratio
	 */
	public void drawHorizontalScroll( Graphics g, Component c, float offsetRatio, float blockSizeRatio)
	{
		int x = c.getX();
		int y = c.getY() + c.getHeight() - getHorizontalScrollHeight();

		drawScroll(g, c, offsetRatio, blockSizeRatio, false, x, y, c.getWidth(), getHorizontalScrollHeight(), horizontalScroll);
	}

	/**
	 * Draws a vertical scroll bar in the given component.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param c - the component
	 * @param offsetRatio - the offset ratio
	 * @param blockSizeRatio - the block size ratio
	 */
	public void drawVerticalScroll( Graphics g, Component c, float offsetRatio, float blockSizeRatio)
	{
		int x = c.getX() + c.getWidth() - getVerticalScrollWidth();
		int y = c.getY();
		
		drawScroll(g, c, offsetRatio, blockSizeRatio, true, x, y, getVerticalScrollWidth(), c.getHeight(), verticalScroll);
	}

}
