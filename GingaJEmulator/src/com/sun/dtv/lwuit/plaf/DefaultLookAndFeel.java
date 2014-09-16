package com.sun.dtv.lwuit.plaf;

import com.sun.dtv.lwuit.*;
import com.sun.dtv.lwuit.events.*;
import com.sun.dtv.lwuit.geom.*;
import com.sun.dtv.lwuit.list.*;
import com.sun.dtv.ui.ViewOnlyComponent;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

import java.awt.Color;

public class DefaultLookAndFeel extends LookAndFeel implements FocusListener
{
	private static final long DAY = 1000 * 60 * 60 * 24;

	private static int DAY_SPACE_W = 12;
	private static int DAY_SPACE_H = 15;

	private Image[] chkBoxImages = null;
	private Image comboImage = null;
	private Image[] rButtonImages = null;
	private long tickerSpeed = 50;
	private boolean tickWhenFocused = true;

	private java.util.List focusListeners;

	/**
	 * Creates a new instance of DefaultLookAndFeel.
	 *
	 * 
	 * 
	 */
	public DefaultLookAndFeel()
	{
		focusListeners = new ArrayList();
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
	 * @see bind in class LookAndFeel
	 */
	public void bind(Component cmp)
	{
		if (tickWhenFocused && cmp instanceof Label) {
			((Label)cmp).addFocusListener(this);

			if (focusListeners.contains((Object)cmp) == false) {
				focusListeners.add((Object)cmp);
			}
		}
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
		for (Iterator i=focusListeners.iterator(); i.hasNext(); ) {
			Label label = (Label)i.next();

			label.removeFocusListener(this);
		}
	}

	/**
	 * Gets the ticker speed.
	 *
	 * 
	 * 
	 * @return ticker speed in milliseconds
	 * @see setTickerSpeed(long)
	 */
	public long getTickerSpeed()
	{
		return this.tickerSpeed;
	}

	/**
	 * Sets the ticker speed.
	 *
	 * 
	 * @param tickerSpeed - the speed in milliseconds
	 * @see getTickerSpeed()
	 */
	public void setTickerSpeed(long tickerSpeed)
	{
		this.tickerSpeed = tickerSpeed;
	}

	/**
	 * This method allows to set all Labels, Buttons, CheckBoxes, RadioButtons
	 * to start ticking when the text is too long.
	 *
	 * 
	 * @param tickWhenFocused - boolean indicating whether those components should start ticking if necessary
	 */
	public void setTickWhenFocused(boolean tickWhenFocused)
	{
		this.tickWhenFocused = tickWhenFocused;
	}

	/**
	 * Sets images for checkbox checked/unchecked modes.
	 *
	 * 
	 * @param checked - the image to draw in order to represent a checked checkbox
	 * @param unchecked - the image to draw in order to represent an uncheck checkbox
	 */
	public void setCheckBoxImages( Image checked, Image unchecked)
	{
		if (checked == null || unchecked == null) {
			chkBoxImages = null;
		} else {
			chkBoxImages = new Image[]{unchecked, checked};
		}
	}

	/**
	 * Sets image for the combo box drop down drawing.
	 *
	 * 
	 * @param picker - picker image
	 */
	public void setComboBoxImage( Image picker)
	{
		this.comboImage = picker;
	}

	/**
	 * Sets images for radio button selected/unselected modes.
	 *
	 * 
	 * @param selected - the image to draw in order to represent a selected radio button
	 * @param unselected - the image to draw in order to represent an unselected radio button
	 */
	public void setRadioButtonImages( Image selected, Image unselected)
	{
		if (selected == null || unselected == null) {
			rButtonImages = null;
		} else {
			rButtonImages = new Image[]{unselected, selected};
		}
	}

	/**
	 * Invoked for drawing a button widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param b - the button
	 * @see drawButton in class LookAndFeel
	 */
	public void drawButton( Graphics g, Button b)
	{
		drawComponent(g, b, b.getIconFromState(), null, 0);
	}

	/**
	 * Invoked for drawing a checkbox widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param cb - the check box
	 * @see drawCheckBox in class LookAndFeel
	 */
	public void drawCheckBox( Graphics g, CheckBox cb)
	{
		if (chkBoxImages != null) {
			drawComponent(g, cb, cb.getIconFromState(), chkBoxImages[cb.isSelected() ? 1 : 0], 0);
		} else {
			Style style = cb.getStyle();
			int height = cb.getStyle().getFont().getHeight() + 15;
			
			drawComponent(g, cb, cb.getIconFromState(), null, height);

			Color gradientColor;
			if (cb.hasFocus()) {
				g.setColor(style.getFgSelectionColor());
				gradientColor = style.getBgSelectionColor();
			} else {
				g.setColor(style.getFgColor());
				gradientColor = style.getBgColor();
			}

			int width = height;

			int tX = cb.getX() + style.getPadding(Component.LEFT);
			int tY = cb.getY() + style.getPadding(Component.TOP) + (cb.getHeight() - style.getPadding(Component.TOP) - style.getPadding(Component.BOTTOM)) / 2 - height / 2;
			g.translate(tX, tY);
			int x = scaleCoordinate(1.04f, 16, width);
			int y = scaleCoordinate(4.0f, 16, height);
			int rectWidth = scaleCoordinate(10.9515f, 16, width);
			int rectHeight = scaleCoordinate(10f, 16, height);

			// brighten or darken the color slightly
			Color destColor = findDestColor(gradientColor);

			g.fillLinearGradient(gradientColor, destColor, x + 1, y + 1, rectWidth - 2, rectHeight - 1, false);
			g.drawRoundRect(x, y, rectWidth, rectHeight, 7, 7);

			if (cb.isSelected()) {
				Color color = g.getColor();
				g.setColor(new Color(0x11, 0x11, 0x11, 0xff));
				g.translate(0, 1);
				fillCheckbox(g, width, height);
				g.setColor(color);
				g.translate(0, -1);
				fillCheckbox(g, width, height);
			}
			g.translate(-tX, -tY);
		}
	}

	private static void fillCheckbox(Graphics g, int width, int height) {
		int x1 = scaleCoordinate(2.0450495f, 16, width);
		int y1 = scaleCoordinate(9.4227722f, 16, height);
		int x2 = scaleCoordinate(5.8675725f, 16, width);
		int y2 = scaleCoordinate(13.921746f, 16, height);
		int x3 = scaleCoordinate(5.8675725f, 16, width);
		int y3 = scaleCoordinate(11f, 16, height);
		g.fillTriangle(x1, y1, x2, y2, x3, y3);

		x1 = scaleCoordinate(14.38995f, 16, width);
		y1 = scaleCoordinate(0.88766801f, 16, height);
		g.fillTriangle(x1, y1, x2, y2, x3, y3);
	}

	private static int round(float x) {
		int rounded = (int) x;
		if (x - rounded > 0.5f) {
			return rounded + 1;
		}
		return rounded;
	}

	/**
	 * Takes a floating point coordinate on a virtual axis and rusterizes it to 
	 * a coordinate in the pixel surface. This is a very simple algorithm since
	 * anti-aliasing isn't supported.
	 * 
	 * @param coordinate a position in a theoretical plain
	 * @param plain the amount of space in the theoretical plain
	 * @param pixelSize the amount of pixels available on the screen
	 * @return the pixel which we should color
	 */
	private static int scaleCoordinate(float coordinate, float plain, int pixelSize) {
		return round(coordinate / plain * pixelSize);
	}

	/**
	 * Invoked for drawing a label widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param l - the label
	 * @see drawLabel in class LookAndFeel
	 */
	public void drawLabel( Graphics g, Label l)
	{		
		drawComponent(g, l, l.getIcon(), null, 0);
	}

	/**
	 * Invoked for drawing the radio button widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param rb - the radio button
	 * @see drawRadioButton in class LookAndFeel
	 */
	public void drawRadioButton( Graphics g, RadioButton rb)
	{
		if (rButtonImages != null) {
			drawComponent(g, rb, rb.getIconFromState(), rButtonImages[rb.isSelected() ? 1 : 0], 0);
		} else {
			Style style = rb.getStyle();
			int height = rb.getStyle().getFont().getHeight();
			
			drawComponent(g, rb, rb.getIconFromState(), null, height);
			
			if (rb.hasFocus()) {
				g.setColor(style.getFgSelectionColor());
			} else {
				g.setColor(style.getFgColor());
			}
			int x = rb.getX() + style.getPadding(Component.LEFT);
			int y = rb.getY();// + style.getPadding(Component.TOP);
			
			y += (rb.getHeight() - height - style.getPadding(Component.TOP) - style.getPadding(Component.BOTTOM)) / 2;
			
			g.drawArc(x, y, height, height, 0, 360);
			if (rb.isSelected()) {
				Color color = g.getColor();
				Color destColor = findDestColor(color);
				g.fillRadialGradient(color, destColor, x + 2, y + 2, height - 5, height - 5);
			}
		}
	}

	/**
	 * Invoked for drawing a combo box widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param cb - the combo box
	 * @see drawComboBox in class LookAndFeel
	 */
	public void drawComboBox( Graphics g, ComboBox cb)
	{
		int border = 2;
		Style style = cb.getStyle();
		int leftPadding = style.getPadding(Component.LEFT);
		int rightPadding = style.getPadding(Component.RIGHT);

		setFG(g, cb);

		ListModel model = cb.getModel();
		ListCellRenderer renderer = cb.getRenderer();
		Object value = model.getItemAt(model.getSelectedIndex());
		int comboImageWidth;
		if (comboImage != null) {
			comboImageWidth = comboImage.getWidth();
		} else {
			comboImageWidth = style.getFont().getHeight();
		}
		if (model.getSize() > 0) {
			Component cmp = renderer.getListCellRendererComponent(cb, value, model.getSelectedIndex(), cb.hasFocus());
			cmp.setX(cb.getX() + leftPadding);
			cmp.setY(cb.getY() + style.getPadding(Component.TOP));
			cmp.setWidth(cb.getWidth() - comboImageWidth - 2 * rightPadding - leftPadding);
			cmp.setHeight(cb.getHeight() - style.getPadding(Component.TOP) - style.getPadding(Component.BOTTOM));
			cmp.paint(g);
		}

		g.setColor(style.getBgColor());
		int y = cb.getY();
		int height = cb.getHeight();
		int width = comboImageWidth + border;
		int x = cb.getX() + cb.getWidth() - comboImageWidth - rightPadding - border;
		if (comboImage != null) {
			g.fillRect(x, y, width, height);
			g.drawImage(comboImage, x, y + height / 2 - comboImage.getHeight() / 2);
		} else {
			Color color = g.getColor();

			// brighten or darken the color slightly
			Color destColor = findDestColor(color);

			g.fillLinearGradient(g.getColor(), destColor, x, y, width, height, false);
			g.setColor(color);
			g.drawRect(x, y, width, height - 1);

			width--;
			height--;

			//g.drawRect(x, y, width, height);
			g.translate(x + 1, y + 1);
			g.setColor(new Color(0x11, 0x11, 0x11, 0xff));
			int x1 = 2;
			int y1 = height / 2 - 4;
			int x2 = width - 2;
			int y2 = y1;
			int x3 = width / 2;
			int y3 = height / 2 + 4;
			/*int x1 = scaleCoordinate(2.5652081f, 16, width);
			int y1 = scaleCoordinate(4.4753664f, 16, height);
			int x2 = scaleCoordinate(8.2872691f, 16, width);
			int y2 = scaleCoordinate(10f, 16, height);
			int x3 = scaleCoordinate(13.516078f, 16, width);
			int y3 = y1;*/
			g.fillTriangle(x1, y1, x2, y2, x3, y3);
			g.translate(-1, -1);
			if(cb.hasFocus()) {
				g.setColor(style.getFgSelectionColor());
			} else {
				g.setColor(style.getFgColor());
			}
			g.fillTriangle(x1, y1, x2, y2, x3, y3);
			//g.setColor(style.getFgColor());
			//g.fillTriangle(x1 + 2, y1 + 2, x2, y2 - 2, x3 - 2, y3 + 2);

			g.translate(-x, -y);
		}
	}

	/**
	 * Finds a suitable destination color for gradient values
	 */
	private Color findDestColor(Color color) {
		// brighten or darken the color slightly
		int sourceR = color.getRed();
		int sourceG = color.getGreen();
		int sourceB = color.getBlue();
		if (sourceR > 128 && sourceG > 128 && sourceB > 128) {
			// darken
			sourceR = Math.max(sourceR >> 1, 0);
			sourceG = Math.max(sourceG >> 1, 0);
			sourceB = Math.max(sourceB >> 1, 0);
		} else {
			// special case for black, since all channels are 0 it can't be brightened properly...
			if(color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) {
				return new Color(0x22, 0x22, 0x22, 0xff);
			}

			// brighten
			sourceR = Math.min(sourceR << 1, 0xff);
			sourceG = Math.min(sourceG << 1, 0xff);
			sourceB = Math.min(sourceB << 1, 0xff);
		}

		return new Color(sourceR, sourceG, sourceB, 0xff);
	}

	/**
	 * Invoked for drawing a list widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param l - the list
	 * @see drawList in class LookAndFeel
	 */
	public void drawList(Graphics g, List l)
	{
		Color tmp = l.getStyle().getBgColor();
		Color gradientColor = new Color(tmp.getRed(), tmp.getGreen(), tmp.getBlue(), 0xfa);
		Color destColor = findDestColor(gradientColor);
		
		g.fillLinearGradient(gradientColor, destColor, l.getX(), l.getY(), l.getWidth(), l.getHeight(), false);
	}

	/**
	 * Draws generic component border
	 *
	 */
	void drawBorder(Graphics g, Component c, Color color, int borderWidth) {
		drawBorder(g, c, color, color, borderWidth);
	}

	/**
	 * Draws generic component border
	 *
	 */
	void drawBorder(Graphics g, Component c, Color topAndRightColor, Color bottomAndLeftColor, int borderWidth) {
		g.setColor(topAndRightColor);     //Text Component upper border color
		g.fillRect(c.getX(), c.getY(), c.getWidth(),borderWidth);
		g.fillRect(c.getX(), c.getY(), borderWidth, c.getHeight());
		g.setColor(bottomAndLeftColor);     //Text Component lower border color
		g.fillRect(c.getX(), c.getY() + c.getHeight()-borderWidth, c.getWidth(), borderWidth);
		g.fillRect(c.getX() + c.getWidth()-borderWidth, c.getY() , borderWidth, c.getHeight());
	}

	/**
	 * Invoked for drawing a month view widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param cal - the calendar
	 * @param mv - the month view component
	 * @see drawMonthView in class LookAndFeel
	 */
	public void drawMonthView(Graphics g, com.sun.dtv.lwuit.Calendar cal, Component mv)
	{
		drawBorder(g, mv, new Color(0x020202), new Color(0x080808), 2);
		setFG(g, mv);

		Font f = mv.getStyle().getFont();
		int fh = f.getHeight();

		int w = mv.getWidth();
		int h = mv.getHeight();
		int labelH = (fh + DAY_SPACE_H);
		int ch = h - labelH;

		int dayWidth = (w / 7);
		int dayHeight = (ch / 6);

		long sd = cal.getSelectedDay();
		Date date = new Date();

		int fix = f.stringWidth("22") / 2;


		g.setColor(mv.getStyle().getBgColor());
		g.fillRect(mv.getX() + 2, mv.getY() + 2, mv.getWidth() - 4, labelH-4);

		g.setColor(mv.getStyle().getFgColor());
		g.setFont(mv.getStyle().getFont());

		String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", 
			"Friday", "Saturday"};
		String[] labels = {"Su", "M", "Tu", "W", "Th", "F", "Sa"};

		Style dayStyle = new Style(mv.getStyle());
		dayStyle.setFgColor(mv.getStyle().getFgSelectionColor());
		dayStyle.setPadding(0, 0, fix+2, 0);
		dayStyle.setMargin(0, 0, 0, 0);

		Label tmp = new Label();
		tmp.setStyle(dayStyle);
		tmp.setPreferredSize(new Dimension(dayWidth, labelH));
		for (int i = 0; i < labels.length; i++) {
			String day = UIManager.getInstance().localize(days[i], labels[i]);
			tmp.setText(day);
			int dx = (dayWidth * i);
			tmp.setX(mv.getX() + dx);
			tmp.setY(mv.getY());
			tmp.setSize(tmp.getPreferredSize());
			drawLabel(g, tmp);
		}
		paintDates(g, date, mv, sd, f, dayWidth, dayHeight,
				labelH, fix, fh, sd);
	}

	/**
	 * Invoked for drawing the text area widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param ta - the text area
	 * @see drawTextArea in class LookAndFeel
	 */
	public void drawTextArea( Graphics g, TextArea ta)
	{
		Color old = ta.getStyle().getBgColor();
		if(ta.isEnabled()) {
			g.setColor(old);
		} else {
			Color gray = old.darker();
			g.setColor(gray);
		}
		setBG(g, ta);
		g.fillRect(ta.getX(), ta.getY(), ta.getWidth(), ta.getHeight());
		
		g.setColor(old);
		
		setFG(g, ta);
		
		int line = ta.getLines();
		int oX = g.getClipX();
		int oY = g.getClipY();
		int oWidth = g.getClipWidth();
		int oHeight = g.getClipHeight();
		Font f = ta.getStyle().getFont();
		int fontHeight = f.getHeight();
		Border b = ta.getStyle().getBorder();

		if (b != null) {
			if(ta.hasFocus() ) {
				if (b.getFocusedInstance() != null) {
					b.getFocusedInstance().paint(g, ta);
				}	
			} else {
				b.paint(g, ta);
			}
		}
		
		for (int i = 0; i < line; i++) {
			int x = ta.getX() + ta.getStyle().getPadding(Component.LEFT);
			int y = ta.getY() + ta.getStyle().getPadding(Component.TOP) + (ta.getRowsGap() + fontHeight) * i;

			if (ta.isScrollableY() == true) {
				y = y - ta.getScrollY();
			}

			if(Rectangle.intersects(x, y, ta.getWidth(), fontHeight, oX, oY, oWidth, oHeight)) {
				String rowText = (String) ta.getTextAt(i);

				//display ******** if it is a password field
				String displayText = "";
				if ((ta.getConstraint() & TextArea.PASSWORD) != 0) {
					int length = rowText.length();

					if ((i+1) >= line) {
						if (length > 0) {
							length = length - 1;
						}
					}

					for (int j = 0; j < length; j++) {
						displayText += "*";
					}

					if ((i+1) >= line) {
						displayText += rowText.charAt(rowText.length() - 1);
					}
				} else {
					displayText = rowText;
				}

				g.drawString(displayText, x, y);
			}
		}
	}

	/**
	 * Returns the preferred size for the button.
	 *
	 * 
	 * @param b - the button
	 * @return the preferred size for the button
	 * @see getButtonPreferredSize in class LookAndFeel
	 */
	public Dimension getButtonPreferredSize( Button b)
	{
		return getPreferredSize(b, new Image[]{b.getIcon(), b.getRolloverIcon(), b.getPressedIcon()}, null);
	}

	/**
	 * Returns the preferred size for the checkbox.
	 *
	 * 
	 * @param cb - the checkbox
	 * @return the preferred size for the checkbox
	 * @see getCheckBoxPreferredSize in class LookAndFeel
	 */
	public Dimension getCheckBoxPreferredSize( CheckBox cb)
	{
		if (chkBoxImages != null) {
			return getPreferredSize(cb, new Image[]{cb.getIcon(), cb.getRolloverIcon(), cb.getPressedIcon()}, chkBoxImages[0]);
		}
		Dimension d = getPreferredSize(cb, new Image[]{cb.getIcon(), cb.getRolloverIcon(), cb.getPressedIcon()}, null);

		// allow for checkboxes without a string within them
		d.setHeight(Math.max(8, d.getHeight()));

		d.setWidth(d.getWidth() + cb.getHeight() + cb.getGap());
		
		return d;
	}

	/**
	 * Returns the preferred size for the label.
	 *
	 * 
	 * @param l - the label
	 * @return the preferred size for the label
	 * @see getLabelPreferredSize in class LookAndFeel
	 */
	public Dimension getLabelPreferredSize( Label l)
	{
		return getPreferredSize(l, new Image[]{l.getIcon()}, null);
	}

	private Dimension getPreferredSize(Label l, Image[] icons, Image stateImage) {
		int prefW = 0;
		int prefH = 0;

		Style style = l.getStyle();
		int gap = l.getGap();
		for (int i = 0; i < icons.length; i++) {
			Image icon = icons[i];
			if (icon != null) {
				prefW = Math.max(prefW, icon.getWidth());
				prefH = Math.max(prefH, icon.getHeight());
			}
		}
		String text = l.getText();
		Font font = style.getFont();
		if (font == null) {
			//System.out.println("Missing font for " + l);
			font = Font.getDefaultFont();
		}
		if (text != null && text.length() > 0) {
			//add the text size
			switch (l.getTextPosition()) {
				case Label.LEFT:
				case Label.RIGHT:
					prefW += font.stringWidth(text);
					prefH = Math.max(prefH, font.getHeight());
					break;
				case Label.BOTTOM:
				case Label.TOP:
					prefW = Math.max(prefW, font.stringWidth(text));
					prefH += font.getHeight();
					break;
			}
		}
		//add the state image(relevant for CheckBox\RadioButton)
		if (stateImage != null) {
			prefW += (stateImage.getWidth() + gap);
			prefH = Math.max(prefH, stateImage.getHeight());
		}


		if (icons[0] != null && text != null && text.length() > 0) {
			switch (l.getTextPosition()) {
				case Label.LEFT:
				case Label.RIGHT:
					prefW += gap;
					break;
				case Label.BOTTOM:
				case Label.TOP:
					prefH += gap;
					break;
			}
		}

		if (prefH != 0) {
			prefH += (style.getPadding(Component.TOP) + style.getPadding(Component.BOTTOM));
		}
		if (prefW != 0) {
			prefW += (style.getPadding(Component.RIGHT) + style.getPadding(Component.LEFT));
		}
		
		if(style.getBorder() != null){
			prefH += style.getBorder().arcHeight;
			prefW += style.getBorder().arcWidth;
		}
		
		return new Dimension(prefW, prefH);
	}

	/**
	 * Returns the preferred size for the list.
	 *
	 * 
	 * @param l - the list
	 * @return the preferred size for the list
	 * @see getListPreferredSize in class LookAndFeel
	 */
	public Dimension getListPreferredSize(List l)
	{
		int width = 0;
		int height = 0;
		int selectedHeight;
		int selectedWidth;
		ListModel model = l.getModel();
		// CHANGE:: corrigir o metodo DefaultLookAndFeel::getListPreferredSize():: procurar o equivalente de getMinElementHeight()
		// int numOfcomponents = Math.max(model.getSize(), l.getMinElementHeight());
		int numOfcomponents = model.getSize();
		Object prototype = l.getRenderingPrototype();
		if(prototype != null) {
			ListCellRenderer renderer = l.getRenderer();
			Component cmp = renderer.getListCellRendererComponent(l, prototype, 0, false);
			Dimension d = cmp.getPreferredSize();

			height = d.getHeight();
			width = d.getWidth();
			cmp = renderer.getListCellRendererComponent(l, prototype, 0, true);

			selectedHeight = Math.max(height, d.getHeight());
			selectedWidth = Math.max(width, d.getWidth());
		} else {
			int hightCalcComponents = Math.min(5, numOfcomponents);
			Object dummyProto = null;
			if(model.getSize() > 0) {
				dummyProto = model.getItemAt(0);
			}
			ListCellRenderer renderer = l.getRenderer();
			for (int i = 0; i < hightCalcComponents; i++) {
				Object value;
				if(i < model.getSize()) {
					value = model.getItemAt(i);
				} else {
					value = dummyProto;
				}
				Component cmp = renderer.getListCellRendererComponent(l, value, i, false);
				Dimension d = cmp.getPreferredSize();

				height = Math.max(height, d.getHeight());
				width = Math.max(width, d.getWidth());
			}
			selectedHeight = height;
			selectedWidth = width;
			if (model.getSize() > 0) {
				Object value = model.getItemAt(0);
				Component cmp = renderer.getListCellRendererComponent(l, value, 0, true);
				Dimension d = cmp.getPreferredSize();

				selectedHeight = Math.max(height, d.getHeight());
				selectedWidth = Math.max(width, d.getWidth());
			}
		}
		int verticalPadding = l.getStyle().getPadding(Component.TOP) + l.getStyle().getPadding(Component.BOTTOM);
		int horizontalPadding = l.getStyle().getPadding(Component.RIGHT) + l.getStyle().getPadding(Component.LEFT);

		if (numOfcomponents == 0) {
			return new Dimension(horizontalPadding, verticalPadding);
		}

		if(l instanceof ComboBox) {
			int boxWidth = l.getStyle().getFont().getHeight() + 2;
			return new Dimension(boxWidth + selectedWidth + horizontalPadding, selectedHeight + verticalPadding);
		} else {
			if (l.getOrientation() == List.VERTICAL) {
				return new Dimension(selectedWidth + horizontalPadding, selectedHeight + (height + l.getItemGap()) * (numOfcomponents) + verticalPadding);
			} else {
				return new Dimension(selectedWidth + (width + l.getItemGap()) * (numOfcomponents) + horizontalPadding, selectedHeight + verticalPadding + 6);
			}
		}
	}

	/**
	 * Returns the preferred size for the month view component.
	 *
	 * 
	 * @param mv - the month view component
	 * @return the preferred size for the month view component
	 * @see getMonthViewPreferredSize in class LookAndFeel
	 */
	public Dimension getMonthViewPreferredSize( Component mv)
	{
		Font f = mv.getStyle().getFont();
		int fh = f.getHeight();
		int dayWidth = f.stringWidth("22");

		int w = (dayWidth + DAY_SPACE_W) * 7;
		int h = (fh + DAY_SPACE_H) * 5;

		return new Dimension(w + mv.getStyle().getPadding(Component.RIGHT) + mv.getStyle().getPadding(Component.LEFT), h + mv.getStyle().getPadding(Component.TOP) + mv.getStyle().getPadding(Component.BOTTOM));
	}

	/**
	 * Returns the preferred size for the radio button.
	 *
	 * 
	 * @param rb - the radio button
	 * @return the preferred size for the radio button
	 * @see getRadioButtonPreferredSize in class LookAndFeel
	 */
	public Dimension getRadioButtonPreferredSize( RadioButton rb)
	{
		if (rButtonImages != null) {
			return getPreferredSize(rb, new Image[]{rb.getIcon(), rb.getRolloverIcon(), rb.getPressedIcon()}, rButtonImages[0]);
		}
		Dimension d = getPreferredSize(rb, new Image[]{rb.getIcon(), rb.getRolloverIcon(), rb.getPressedIcon()}, null);

		// allow for radio buttons without a string within them
		d.setHeight(Math.max(8, d.getHeight()));

		d.setWidth(d.getWidth() + rb.getStyle().getFont().getHeight());
		return d;
	}

	/**
	 * Returns the preferred size for the text area.
	 *
	 * 
	 * @param ta - the text area
	 * @return the preferred size for the text area
	 * @see getTextAreaPreferredSize in class LookAndFeel
	 */
	public Dimension getTextAreaPreferredSize(TextArea ta)
	{
		char widestchar = 'W';
		int prefW = 0;
		int prefH = 0;
		Font f = ta.getStyle().getFont();

		//if this is a text field the preferred size should be the text width
		if (ta.getRows() == 1) {
			prefW = f.stringWidth(ta.getText());
		} else {
			// W -> widest char
			prefW = f.charWidth(widestchar) * ta.getColumns();
		}
		int rows;
		rows = ta.getActualRows();
		prefH = (f.getHeight() + 2) * rows;
		int columns = ta.getColumns();
		String str = "";
		for (int iter = 0; iter < columns; iter++) {
			str += widestchar;
		}
		prefW = Math.max(prefW, f.stringWidth(str));
		prefH = Math.max(prefH, rows * f.getHeight());

		return new Dimension(prefW + ta.getStyle().getPadding(Component.RIGHT) + ta.getStyle().getPadding(Component.LEFT), prefH + ta.getStyle().getPadding(Component.TOP) + ta.getStyle().getPadding(Component.BOTTOM));
	}
	private void paintBorder(Graphics g, Label l)
	{
		Border b = l.getStyle().getBorder();
		if(b == null)
		{
			return; 
		}
		if(l.hasFocus() )
		{
			if (b.getFocusedInstance() != null) 
			{
				b.getFocusedInstance().paint(g, l);
				return;
			}	
		}
		else
			b.paint(g, l);
	}
	private void drawComponent(Graphics g, Label l, Image icon, Image stateIcon, int preserveSpaceForState) 
	{
		int x = l.getX();
		int y = l.getY();
		Color color = l.getStyle().getBgColor();
		if(l.isEnabled())
		{
			g.setColor(color);
		}
		else
		{
			Color gray = color.darker();
			g.setColor(gray);
		}
		setBG(g, l);
		
		g.fillRoundRect(x, y, l.getWidth(), l.getHeight(), 7, 7);
		
		if (l.getStyle().getBgImage() != null) 
		{
			g.drawImage(l.getStyle().getBgImage(), x, y, l.getWidth(), l.getHeight(), null);
		}

		paintBorder(g,l);

		setFG(g, l);
		
		int gap = l.getGap();
		
		int stateIconSize = 0;
		int stateIconYPosition = 0;
		
		String text = l.getText();
		Style style = l.getStyle();
		
		Font font = style.getFont();
		
		int fontHeight = 0;
		if (text == null) {
			text = "";
		}
		if(text.length() > 0){
			fontHeight = font.getHeight();
		}

		if (stateIcon != null) {
			stateIconSize = stateIcon.getWidth(); //square image width == height
			stateIconYPosition = l.getY() + l.getStyle().getPadding(Component.TOP) + (l.getHeight() - l.getStyle().getPadding(Component.TOP) - l.getStyle().getPadding(Component.BOTTOM)) / 2 - stateIconSize / 2;
			preserveSpaceForState = stateIconSize + gap;
			g.drawImage(stateIcon, l.getX() + l.getStyle().getPadding(Component.LEFT), stateIconYPosition);
		}
		
		//Calculating X value
		if(l.getAlignment() == ViewOnlyComponent.HORIZONTAL_ALIGN_LEFT)
		{
			x += l.getStyle().getPadding(Component.LEFT) + preserveSpaceForState
				+ (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcWidth/2 : 0);
		}
		else if (l.getAlignment() == ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER)
		{
			if(l.getTextPosition() == Label.LEFT || l.getTextPosition() == Label.RIGHT)
			{
				x += (l.getWidth() - (preserveSpaceForState +
						style.getPadding(Component.LEFT) +
						style.getPadding(Component.RIGHT) +
						font.stringWidth(text) + (icon != null ? icon.getWidth() : 0))  ) / 2;
				x = Math.max(x, l.getX() + style.getPadding(Component.LEFT) + preserveSpaceForState);
			}
			else
			{
				int aux = Math.max(font.stringWidth(text), (icon != null ? icon.getWidth() : 0));
				x += (l.getWidth() - (preserveSpaceForState +
						style.getPadding(Component.LEFT) +
						style.getPadding(Component.RIGHT) + aux) + (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcWidth : 0) ) / 2;
			}
		}
		else if (l.getAlignment() == ViewOnlyComponent.HORIZONTAL_ALIGN_RIGHT)
		{
			if(l.getTextPosition() == Label.LEFT || l.getTextPosition() == Label.RIGHT)
			{
				x += l.getWidth() - l.getStyle().getPadding(Component.RIGHT) - preserveSpaceForState
				- font.stringWidth(text) - (icon != null ? icon.getWidth() : 0)
				- (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcWidth/2 : 0);
			}
			else
			{
				int aux = Math.max(font.stringWidth(text), (icon != null ? icon.getWidth() : 0));
				x += l.getWidth() - l.getStyle().getPadding(Component.RIGHT) - preserveSpaceForState
				- aux - (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcWidth/2 : 0);
			}
		}
			
		//Calculating y value
		if(l.getVerticalAlignment() == ViewOnlyComponent.VERTICAL_ALIGN_TOP)
		{
			y += l.getStyle().getPadding(Component.TOP) + (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcHeight/2 : 0);
		}
		else if (l.getVerticalAlignment() == ViewOnlyComponent.VERTICAL_ALIGN_CENTER)
		{
			if(l.getTextPosition() == Label.TOP || l.getTextPosition() == Label.BOTTOM)
			{
				y += (l.getHeight() - (style.getPadding(Component.TOP) +
					style.getPadding(Component.BOTTOM) +
					fontHeight + (icon != null ? icon.getHeight() : 0) )) / 2;
			}
			else
			{
				int aux = Math.max(fontHeight, (icon != null ? icon.getHeight() : 0)); 
				y += (l.getHeight() - (style.getPadding(Component.TOP) + style.getPadding(Component.BOTTOM) + aux)) / 2;
			}
		}
		else if (l.getVerticalAlignment() == ViewOnlyComponent.VERTICAL_ALIGN_BOTTOM)
		{
			if(l.getTextPosition() == Label.TOP || l.getTextPosition() == Label.BOTTOM)
			{
				y += l.getHeight() - l.getStyle().getPadding(Component.BOTTOM) - fontHeight
				- (icon != null ? icon.getHeight() : 0)
				- (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcHeight/2 : 0);
			}
			else
			{
				int aux = Math.max(fontHeight, (icon != null ? icon.getHeight() : 0));
				y += l.getHeight() - l.getStyle().getPadding(Component.BOTTOM) - aux
				- (l.getStyle().getBorder() != null ? l.getStyle().getBorder().arcHeight/2 : 0);
			}
		}
		
		if(icon == null)
		{
			drawLabelText(g, l, text, x, y, l.getWidth());
		}
		else
		{
			if(l.getTextPosition() == Label.TOP)
			{
				drawLabelText(g, l, text, x, y, l.getWidth());
				g.drawImage(icon, x, y + font.getHeight() + gap);
			}
			else if(l.getTextPosition() == Label.BOTTOM)
			{
				drawLabelText(g, l, text, x, y + icon.getHeight() + gap, l.getWidth());
				g.drawImage(icon, x, y);
			}
			else if(l.getTextPosition() == Label.RIGHT)
			{
				drawLabelText(g, l, text, x + icon.getWidth() + gap , y, l.getWidth());
				g.drawImage(icon, x, y);
			}
			else if(l.getTextPosition() == Label.LEFT)
			{
				drawLabelText(g, l, text, x, y, l.getWidth());
				g.drawImage(icon, x + font.stringWidth(text) + gap , y);
			}
		}
	}

	/**
	 * Implements the drawString for the text component and adjust the valign
	 * assuming the icon is in one of the sides
	 */
	private int drawLabelStringValign(Graphics g, Label l, String str, int x, int y, int iconStringHGap, int iconHeight, int textSpaceW, int fontHeight, int preserveSpaceForState) {
		switch (l.getVerticalAlignment()) {
			case ViewOnlyComponent.VERTICAL_ALIGN_TOP:
				y = y - l.getHeight()/2 + ( (l.getStyle().getBorder() != null) ? l.getStyle().getBorder().arcHeight : 0);
				return drawLabelString(g, l, str, x, y, textSpaceW, preserveSpaceForState);
			case ViewOnlyComponent.VERTICAL_ALIGN_CENTER:
				return drawLabelString(g, l, str, x, y + iconHeight / 2 - fontHeight / 2, textSpaceW, preserveSpaceForState);
			default:
				return drawLabelString(g, l, str, x, y + l.getHeight()/2 - fontHeight/*- ((l.getStyle().getBorder() != null) ? l.getStyle().getBorder().arcHeight : 0)*/, textSpaceW, preserveSpaceForState);
		}
	}

	/**
	 * Implements the drawString for the text component and adjust the valign
	 * assuming the icon is in one of the sides
	 */
	private int drawLabelString(Graphics g, Label l, String text, int x, int y, int textSpaceW, int preserveSpaceForState) {
		Style style = l.getStyle();

		int cx = g.getClipX();
		int cy = g.getClipY();
		int cw = g.getClipWidth();
		int ch = g.getClipHeight();
		int txtX;

		if (l.getTextPosition() == l.RIGHT) {
			txtX = (l.getWidth() - style.getPadding(l.LEFT) - textSpaceW);
		} else {
			txtX = x + preserveSpaceForState;
		}

		g.clipRect(0, 0, l.getWidth(), l.getHeight());

		if (l.isTickerRunning()) {
			if (l.getShiftText() > 0) {
				if (l.getShiftText() > textSpaceW) {
					l.setShiftText(x - l.getX() - style.getFont().stringWidth(text));
				}
			} else if ((x) + l.getShiftText() + style.getFont().stringWidth(text) < txtX) {
				l.setShiftText(textSpaceW - (x - txtX));// + style.getFont().stringWidth(text));
			}
		}

		int drawnW = drawLabelText(g, l, text, x, y, textSpaceW);

		g.setClip(cx, cy, cw, ch);

		return drawnW;
	}

	/**
	 * Draws the text of a label
	 * 
	 * @param g graphics context
	 * @param l label component
	 * @param text the text for the label
	 * @param x position for the label
	 * @param y position for the label
	 * @param textSpaceW the width available for the component
	 * @return the space used by the drawing
	 */
	protected int drawLabelText(Graphics g, Label l, String text, int x, int y, int textSpaceW) {

		Font f = l.getStyle().getFont();
		if (!l.isTickerRunning()) {
			int txtW = f.stringWidth(text);

			//if there is no space to draw the text add ... at the end
			if (txtW > textSpaceW && textSpaceW > 0) {
				if (l.isEndsWith3Points()) {
					String points = "...";
					int index = 1;
					String tmp = text.substring(0, index);
					int pointsW = f.stringWidth(points);
					while (f.stringWidth(tmp) + pointsW < textSpaceW) {
						index++;
						if (index >= text.length()) {
							break;
						}
						tmp = text.substring(0, index);
					}
					tmp = text.substring(0, index - 1) + points;
					text = tmp;
				}

			}
		}

		g.drawString(text, l.getShiftText() + x, y);

		return Math.min(f.stringWidth(text), textSpaceW);
	}

	

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
	 * @see findDayAt in class LookAndFeel
	 */
	public long findDayAt(int x, int y, com.sun.dtv.lwuit.Calendar cal, com.sun.dtv.lwuit.Component mv)
	{
		Calendar calendar = Calendar.getInstance();
		long firstOfMonth = getMonthExtent(calendar, cal.getDate(), true);

		int w = mv.getWidth();

		Font f = mv.getStyle().getFont();
		int fh = f.getHeight();

		int labelH = (fh + DAY_SPACE_H);

		int dayWidth = (w / 7);
		long currentDay = firstOfMonth;
		calendar.setTime(new Date(currentDay));
		int dayX = (mv.getAbsoluteX() + (dayWidth * (calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1)));
		int dayY = mv.getAbsoluteY() + labelH;

		int h = mv.getHeight();
		int ch = h - labelH;

		int dayHeight = (ch / 6);

		while (!(dayX < x && dayX + dayWidth > x && dayY < y && dayY + dayHeight > y)) {
			currentDay += DAY;
			calendar.setTime(new Date(currentDay));
			dayX = (mv.getAbsoluteX() + (dayWidth * (calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1)));
			if (calendar.get(java.util.Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				dayX = mv.getAbsoluteX();
				dayY += dayHeight;
			}
			if (dayX > mv.getX() + mv.getWidth() && dayY > mv.getY() + mv.getHeight()) {
				return currentDay;
			}
		}
		return currentDay;
	}

	private void paintDates(Graphics g, Date date, Component mv, long sd, Font f,
			int dayWidth, int dayHeight, int labelH,
			int fix, int fh, long selectedDay) {
		int w = f.stringWidth("22");
		Calendar calendar = Calendar.getInstance();
		date.setTime(sd);
		long firstOfMonth = getMonthExtent(calendar, date, true);

		date.setTime(sd);
		long endOfMonth = getMonthExtent(calendar, date, false);

		long day = firstOfMonth;
		int d = 1;
		int sats = 0;
		int dx;
		g.setColor(mv.getStyle().getFgColor());
		do {
			date.setTime(day);
			calendar.setTime(date);
			String dayString = Integer.toString(d);

			int dow = calendar.get(Calendar.DAY_OF_WEEK);

			dx = mv.getX() + (dayWidth * (dow - 1)) + fix;//(d < 10 ? fix : 0);

			int dy = mv.getY() + ((sats) * dayHeight) + labelH+2;

			if (day == selectedDay) {
				g.setColor(mv.getStyle().getBgSelectionColor());
				g.fillRoundRect(dx - 2, dy - 2,
						w + 5, fh + 4, 4, 4);
				g.setColor(mv.getStyle().getFgSelectionColor());
				g.drawString(dayString, w / 2 - f.stringWidth(dayString) / 2 + dx + 2, dy);
				g.drawRoundRect(dx - 2, dy - 2,
						w + 5, fh + 4, 4, 4);
			} else {
				g.drawString(dayString, w / 2 - f.stringWidth(dayString) / 2 + dx + 2, dy);
			}

			g.setColor(mv.getStyle().getFgColor());


			day += DAY;
			d++;

			if (dow == Calendar.SATURDAY) {
				sats++;
				g.setColor(mv.getStyle().getBgColor());
				g.fillRect(mv.getX() + 2, dy + fh + 3, mv.getWidth() - 4, 1);
				g.setColor(mv.getStyle().getFgColor());
			}
		} while (day <= endOfMonth);
	}

	/*
	 * Finds the first or last day of the month prior or ahead of the given date.
	 */
	private long getMonthExtent(Calendar calendar, Date d, boolean first) {
		long day = d.getTime();
		d.setTime(day);
		calendar.setTime(d);
		long origDay = day;

		int currentMonth = calendar.get(Calendar.MONTH);
		int adjacentMonth;
		do {
			long adjacentDay = day + ((first ? -1 : 1) * DAY);
			d.setTime(adjacentDay);
			calendar.setTime(d);
			adjacentMonth = calendar.get(Calendar.MONTH);
			if (currentMonth == adjacentMonth) {
				day = adjacentDay;
			}
		} while (currentMonth == adjacentMonth);

		d.setTime(origDay);
		calendar.setTime(d);
		return day;
	}

	/**
	 * Returns the preferred size for the combo box.
	 *
	 * 
	 * @param cb - the combo box
	 * @return the preferred size for the combo box
	 * @see getComboBoxPreferredSize in class LookAndFeel
	 */
	public Dimension getComboBoxPreferredSize( ComboBox cb)
	{
		return getListPreferredSize(cb);
	}

	/**
	 * Invoked for drawing the Tab Pane widget.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param tp - the tabbed pane
	 * @see drawTabbedPane in class LookAndFeel
	 */
	public void drawTabbedPane( Graphics g, TabbedPane tp)
	{
		// TODO::
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
	 * @see getTabbedPaneCell in class LookAndFeel
	 */
	public Component getTabbedPaneCell( final TabbedPane tp, String text, Image icon, final boolean isSelected, 
			final boolean cellHasFocus, final Style cellStyle, final Style tabbedPaneStyle, final int cellOffsetX,
			final int cellOffsetY, final Dimension cellsPreferredSize, final Dimension contentPaneSize)
	{
		Label cell = new Label(text)
		{
			public void paint(Graphics g) 
			{
				int tPBorder = tp.getTabbedPaneBorderWidth();
				int focusMarkWidth = tPBorder * 2;
				int tabP = tp.getTabPlacement();
				// Initialize foreground colors before calling to super.paint()
				if (isSelected && cellHasFocus) 
				{
					focusMarkWidth = tPBorder * 3;
					this.getStyle().setFgSelectionColor(cellStyle.getFgSelectionColor());
				} 
				else 
				{
					this.getStyle().setFgColor(cellStyle.getFgColor());
				}
				super.paint(g);
				if (!isSelected) 
				{
					g.setColor(cellStyle.getFgColor().darker());
					g.fillRect(getX(), getY(), getWidth(), getHeight(), (byte) 0x2f);
				}
				// coloring back the focus mark line
				g.setColor(getStyle().getFgColor());
				if (tabP == Component.TOP || tabP == Component.BOTTOM) 
				{
					if (tabP == Component.TOP) 
					{
						if (isSelected) 
						{
							g.fillRect(getX(), getY() + tPBorder, getWidth(), focusMarkWidth);// north
						}
						g.setColor(tabbedPaneStyle.getFgColor());
						g.fillRect(getX(), getY(), getWidth(), tPBorder);//north line
						g.fillRect(getX(), getY(), tPBorder, getHeight());// west line
					} 
					else 
					{
						if (isSelected) 
						{
							g.fillRect(getX(), getY() + getHeight() - focusMarkWidth, getWidth(), focusMarkWidth);// south
						}
						g.setColor(tabbedPaneStyle.getFgColor());
						g.fillRect(getX(), getY() + getHeight() - tPBorder, getWidth(), tPBorder);//south line
						g.fillRect(getX(), getY(), tPBorder, getHeight());// west line
					}
					int x = getX() - cellOffsetX + getWidth();
					if (x == contentPaneSize.getWidth()) {
						g.fillRect(x + cellOffsetX - tPBorder, getY(), tPBorder, getHeight());// east line

					}
					if (cellsPreferredSize.getWidth() < contentPaneSize.getWidth() && (getX() + getWidth() == cellsPreferredSize.getWidth())) {
						g.fillRect(getX() + getWidth() - tPBorder, getY(), tPBorder, getHeight());
					}
				} else { // LEFT or RIGHT
					if (isSelected) {
						g.fillRect(getX(), getY() + tPBorder, getWidth(), focusMarkWidth);// north
					}
					g.setColor(tabbedPaneStyle.getFgColor());
					g.fillRect(getX(), getY(), getWidth(), tPBorder);
					int y = getY() - cellOffsetY + getHeight();
					if (y == contentPaneSize.getHeight()) {
						g.fillRect(getX(), y + cellOffsetY - tPBorder, getWidth(), tPBorder);
					}
					if (cellsPreferredSize.getHeight() < contentPaneSize.getHeight() && (getY() + getHeight() == cellsPreferredSize.getHeight())) {
						g.fillRect(getX(), getY() + getHeight() - tPBorder, getWidth(), tPBorder);//south line

					}
					if (tabP == Component.LEFT) {
						g.fillRect(getX(), getY(), tPBorder, getHeight());// west border

					} else {
						g.fillRect(getX() + getWidth() - tPBorder, getY(), tPBorder, getHeight());// east border

					}
				}
			}
		};

		cell.setCellRenderer(true);
		cell.getStyle().setBorder(null);
		cell.getStyle().setMargin(0, 0, 0, 0);
		cell.setIcon(icon);
		updateCellLook(tp, (Component) cell, isSelected);
		if (isSelected) 
		{
			cellStyle.setBgSelectionColor(cellStyle.getBgColor());
		}
		cell.setAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER);
		return cell;
	}

	private void updateCellLook(TabbedPane tp, Component c, boolean selected) {
		if (selected) {
			c.getStyle().setFgColor(tp.getStyle().getFgSelectionColor());
		} else {
			c.getStyle().setFgColor(tp.getStyle().getFgColor());
		}
		c.getStyle().setBgTransparency(tp.getStyle().getBgTransparency());
		c.getStyle().setFont(tp.getStyle().getFont());
	}

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
	 * @see drawTabbedPaneContentPane in class LookAndFeel
	 */
	public void drawTabbedPaneContentPane( TabbedPane tp, Graphics g, Rectangle rect, Dimension cellsPreferredSize, int numOfTabs,
			int selectedTabIndex, Dimension tabsSize, int cellOffsetX, int cellOffsetY)
	{
		int x = rect.getX();
		int y = rect.getY();
		int w = rect.getSize().getWidth();
		int h = rect.getSize().getHeight();
		int listPreferredW = cellsPreferredSize.getWidth();
		int listPreferredH = cellsPreferredSize.getHeight();
		int maxTabWidth = tabsSize.getWidth();
		int maxTabHeight = tabsSize.getHeight();
		int tPBorder = tp.getTabbedPaneBorderWidth();

		g.setColor(tp.getStyle().getBgColor());
		g.fillRect(x, y, w, h, tp.getStyle().getBgTransparency());

		// paint borders for TOP tab placement
		g.setColor(tp.getStyle().getFgColor());
		int tabP = tp.getTabPlacement();

		if (tabP == Component.TOP || tabP == Component.BOTTOM) {
			g.fillRect(x, y, tPBorder, h);// west border

			g.fillRect(x + w - tPBorder, y, tPBorder, h);// east border

			int relativeY = y;
			if (tabP == Component.BOTTOM) 
			{
				relativeY = y + h - tPBorder;
				g.fillRect(x, y, w, tPBorder);// north border

			} 
			else 
			{
				g.fillRect(x, y + h - tPBorder, w, tPBorder);// south border
			}
			if (listPreferredW < w) 
			{
				g.fillRect(listPreferredW - tPBorder, relativeY, w - listPreferredW, tPBorder);
			}
			for (int i = 0; i < numOfTabs; i++) 
			{
				if (i != selectedTabIndex) 
				{
					g.fillRect((x - cellOffsetX + (maxTabWidth * i)), relativeY, maxTabWidth + tPBorder, tPBorder);
				}
			}
		} else {//if (tabP == LEFT || tabP == RIGHT) {

			g.fillRect(x, y, w, tPBorder);// north border

			g.fillRect(x, y + h - tPBorder, w, tPBorder);// south border

			int relativeX = x;
			if (tabP == Component.RIGHT) {
				g.fillRect(x, y, tPBorder, h);// west border

				relativeX = x + w - tPBorder;
			} else {
				g.fillRect(x + w - tPBorder, y, tPBorder, h);// east border

			}
			if (listPreferredH < h) {
				g.fillRect(relativeX, y + listPreferredH - tPBorder, tPBorder, h - listPreferredH + tPBorder);
			}
			for (int i = 0; i < numOfTabs; i++) {
				if (i != selectedTabIndex) {
					g.fillRect(relativeX, (y - cellOffsetY + (maxTabHeight * i)), tPBorder, maxTabHeight + tPBorder);
				}
			}
		}
	}

	/**
	 * Draws the text field without its cursor which is drawn in a separate method.
	 * Input mode indication can also be drawn using this method.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param ta - the text field
	 * @see drawTextField in class LookAndFeel
	 */
	public void drawTextField(Graphics g, TextField ta)
	{
		int x = ta.getX();
		int y = ta.getY();

		setBG(g, ta);
		
		g.fillRect(x, y, ta.getWidth(), ta.getHeight());

		setFG(g, ta);
		
		Border b = ta.getStyle().getBorder();

		if (b != null) {
			if(ta.hasFocus() ) {
				if (b.getFocusedInstance() != null) {
					b.getFocusedInstance().paint(g, ta);
				}	
			} else {
				b.paint(g, ta);
			}
		}

		// display ******** if it is a password field
		String displayText = getTextFieldString(ta);
		Style style = ta.getStyle();
		Font f = ta.getStyle().getFont();
		int cursorCharPosition = ta.getCursorPosition();
		int cursorX = 0;
		int xPos = 0;
		
		x = 0;

		if (cursorCharPosition > 0) {
			xPos = f.stringWidth(displayText.substring(0, cursorCharPosition));
			cursorX = ta.getX() + style.getPadding(Component.LEFT) + xPos;
			if (ta.getWidth() > (f.getHeight() * 2) && cursorX >= ta.getWidth() - f.getHeight()) {
				while (x + xPos >= ta.getWidth() - f.getHeight() * 2) {
					x--;
				}
			}
		}
		//System.out.println("X real "+(ta.getX() + x + style.getPadding(Component.LEFT))+" getx "+ta.getX()+" x "+x+"style X"+style.getPadding(Component.LEFT));
		g.drawString(displayText, ta.getX() + x + style.getPadding(Component.LEFT),
				ta.getY() + style.getPadding(Component.TOP));

		// no point in showing the input mode when there is only one input mode...
		if(ta.getInputModeOrder() != null && ta.getInputModeOrder().length > 1) {
			String inputMode = ta.getInputMode();
			int inputModeWidth = f.stringWidth(inputMode);
			if (ta.handlesInput() && ta.getWidth() / 2 > inputModeWidth) {
				int drawXPos = ta.getX() + style.getPadding(Component.LEFT);
				//System.out.println("1:drawpos "+drawXPos);
				if (xPos < ta.getWidth() / 2) {
					// draw on the right side
					drawXPos = drawXPos + ta.getWidth() - inputModeWidth - style.getPadding(Component.RIGHT) - style.getPadding(Component.LEFT);
					//System.out.println("2:drawpos "+drawXPos);
				}
				g.setColor(style.getFgSelectionColor());
				g.fillRect(drawXPos, ta.getY() + style.getPadding(Component.TOP), inputModeWidth,
						ta.getHeight(), (byte) 140);
				g.setColor(style.getBgSelectionColor());
				g.drawString(inputMode, drawXPos, ta.getY() + style.getPadding(Component.TOP));
			}
		}
	}

	/**
	 * Similar to getText() but works properly with password fields
	 */
	private String getTextFieldString(TextField ta) {
		String text = (String) ta.getText();
		String displayText = "";
		if ((ta.getConstraint() & TextArea.PASSWORD) != 0) {
			// show the last character in a password field
			if (ta.isPendingCommit()) {
				if (text.length() > 0) {
					for (int j = 0; j < text.length() - 1; j++) {
						displayText += "*";
					}
					displayText += text.charAt(text.length() - 1);
				}
			} else {
				for (int j = 0; j < text.length(); j++) {
					displayText += "*";
				}
			}
		} else {
			displayText = text;
		}

		return displayText;
	}

	/**
	 * Returns the preferred size for the text field.
	 *
	 * 
	 * @param ta - the text field
	 * @return the preferred size for the text field
	 * @see getTextFieldPreferredSize in class LookAndFeel
	 */
	public Dimension getTextFieldPreferredSize( TextField ta)
	{
		return getTextAreaSize(ta, true);
	}

	public Dimension getTextAreaSize(TextArea ta, boolean pref) {
		char widestchar = 'W';
		int prefW = 0;
		int prefH = 0;
		Font f = ta.getStyle().getFont();

		//if this is a text field the preferred size should be the text width
		if (ta.getRows() == 1) {
			prefW = f.stringWidth(ta.getText());
		} else {
			prefW = f.charWidth(widestchar) * ta.getColumns();
		}
		int rows;
		if(pref) {
			rows = ta.getActualRows();
		} else {
			rows = ta.getLines();
		}
		prefH = (f.getHeight() + 2) * rows;
		int columns = ta.getColumns();
		String str = "";
		for (int iter = 0; iter < columns; iter++) {
			str += widestchar;
		}
		prefW = Math.max(prefW, f.stringWidth(str));
		prefH = Math.max(prefH, rows * f.getHeight());

		return new Dimension(prefW + ta.getStyle().getPadding(Component.RIGHT) + ta.getStyle().getPadding(Component.LEFT), prefH + ta.getStyle().getPadding(Component.TOP) + ta.getStyle().getPadding(Component.BOTTOM));
	}

	/**
	 * Draws the cursor of the text field, blinking is handled simply by avoiding
	 * a call to this method.
	 *
	 * 
	 * @param g - the Graphics object
	 * @param ta - the text field
	 * @see drawTextFieldCursor in class LookAndFeel
	 */
	public void drawTextFieldCursor( Graphics g, TextField ta)
	{
		Style style = ta.getStyle();
		Font f = style.getFont();

		// display ******** if it is a password field
		String displayText = getTextFieldString(ta);

		int xPos = 0;
		int cursorCharPosition = ta.getCursorPosition();
		if (cursorCharPosition > 0) {
			xPos = f.stringWidth(displayText.substring(0, cursorCharPosition));
		}
		int cursorX = ta.getX() + style.getPadding(Component.LEFT) + xPos;
		int cursorY = ta.getY() + style.getPadding(Component.TOP);
		int x = 0;
		if (ta.getWidth() > (f.getHeight() * 2) && cursorX >= ta.getWidth() - f.getHeight()) {
			while (x + xPos >= ta.getWidth() - f.getHeight() * 2) {
				x--;
			}
		}
		Color oldColor = g.getColor();
		g.setColor(style.getFgSelectionColor());
		g.drawLine(cursorX + x, cursorY, cursorX + x, cursorY + f.getHeight());
		g.setColor(oldColor);
	}

	/**
	 * Invoked when component gains focus.
	 *
	 * 
	 * @param cmp - the component that gains focus
	 * @see focusGained in interface FocusListener
	 */
	public void focusGained( Component cmp)
	{
		if(cmp instanceof Label) {
			Label l = (Label) cmp;
			Style style = l.getStyle();
			int txtW = style.getFont().stringWidth(l.getText());
			int textSpaceW = getAvaliableSpaceForText(l);
			if (txtW > textSpaceW && textSpaceW > 0) {
				((Label) cmp).startTicker(tickerSpeed, true);
			}
		}
	}

	private int getAvaliableSpaceForText(Label l) {
		Style style = l.getStyle();
		int textSpaceW = l.getWidth() - style.getPadding(Label.RIGHT) - style.getPadding(Label.LEFT);
		Image icon = l.getIcon();

		if (l instanceof Button) 
		{
			Button tmp = (Button) l;
			icon = tmp.getIconFromState();
		}

		if (icon != null && (l.getTextPosition() == Label.RIGHT || l.getTextPosition() == Label.LEFT)) {
			textSpaceW = textSpaceW - icon.getWidth();
		}
		int preserveSpaceForState = 0;

		if (l instanceof RadioButton) {
			if (rButtonImages != null) {
				int index = ((RadioButton) l).isSelected() ? 1 : 0;
				preserveSpaceForState = rButtonImages[index].getWidth();
			} else {
				int height = style.getFont().getHeight();
				preserveSpaceForState = height + ((RadioButton) l).getGap();
			}
		}

		if (l instanceof CheckBox) {
			if (chkBoxImages != null) {
				int index = ((CheckBox) l).isSelected() ? 1 : 0;
				preserveSpaceForState = chkBoxImages[index].getWidth();
			} else {
				int height = style.getFont().getHeight();
				preserveSpaceForState = height + ((CheckBox) l).getGap();
			}
		}

		textSpaceW = textSpaceW - preserveSpaceForState;
		return textSpaceW;
	}

	/**
	 * Invoked when component loses focus.
	 *
	 * 
	 * @param cmp - the component that lost focus
	 * @see focusLost in interface FocusListener
	 */
	public void focusLost( Component cmp)
	{
		if(cmp instanceof Label) {
			if (((Label) cmp).isTickerRunning()) {
				((Label) cmp).stopTicker();
			}
		}
	}

}
