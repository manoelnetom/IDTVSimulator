package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.list.ListModel;
import com.sun.dtv.lwuit.list.DefaultListModel;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.list.DefaultListCellRenderer;

import com.sun.dtv.ui.event.RemoteControlEvent;

import java.util.Vector;

public class ComboBox extends List
{

	private Popup popup;
	private List contained;
	private Component popupContent;

	/**
	 * Constructs an empty combo box.
	 *
	 * 
	 */
	public ComboBox()
	{
		this(new DefaultListModel());
	}

	/**
	 * Creates a new instance of ComboBox.
	 *
	 * 
	 * @param items - set of items placed into the combo box model
	 */
	public ComboBox(Vector items)
	{
		this(new DefaultListModel(items));
	}

	/**
	 * Creates a new instance of ComboBox.
	 *
	 * 
	 * @param items - set of items placed into the combo box model
	 */
	public ComboBox(Object[] items)
	{
		this(new DefaultListModel(items));
	}

	/**
	 * Creates a new instance of ComboBox.
	 *
	 * 
	 * @param model - the model for the combo box elements and selection
	 */
	public ComboBox(ListModel model)
	{
		super(model);
		setUIID("ComboBox");
		((DefaultListCellRenderer)super.getRenderer()).setShowNumbers(false);
		setInputOnFocus(true);
		setIsScrollVisible(false);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#getBaseline(int, int)">Component</A></CODE></B></DD>
	 * The baseline for the component text according to which it should be aligned
	 * with other components for best visual look.
	 *
	 * 
	 * @param width - the component width
	 * @param height - the component height
	 * @return baseline value from the top of the component
	 * @see getBaseline in class Component
	 */
	public int getBaseline(int width, int height)
	{
		Component selected;
		if(getRenderingPrototype() != null) {
			selected = getRenderer().getListCellRendererComponent(this, getRenderingPrototype(), 0, true);
		}
		if(getModel().getSize() > 0) {
			selected = getRenderer().getListCellRendererComponent(this, getModel().getItemAt(0), 0, true);
		} else {
			selected = getRenderer().getListCellRendererComponent(this, "XXXXXXXXXXX", 0, true);
		}
		return getHeight() - getStyle().getPadding(BOTTOM) - selected.getStyle().getPadding(BOTTOM);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/List.html#setSelectedIndex(int)">List</A></CODE></B></DD>
	 * Sets the current selected offset in the list, by default this
	 * implementation will scroll the list to the selection if the selection
	 * is outside of the screen.
	 *
	 * 
	 * @param selection - the current selected offset in the list
	 * @see setSelectedIndex in class List
	 * @see List.getSelectedIndex()
	 */
	public void setSelectedIndex(int selection)
	{
		super.setSelectedIndex(selection, false);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/List.html#setSelectedIndex(int, boolean)">List</A></CODE></B></DD>
	 * Sets the current selected offset in the list.
	 *
	 * 
	 * @param selection - the current selected offset in the list
	 * @param scroll - indicates whether scrolling to selection should occur if the selection is outside of view
	 * @see setSelectedIndex in class List
	 * @see List.getSelectedIndex()
	 */
	public void setSelectedIndex(int selection, boolean scroll)
	{
		super.setSelectedIndex(selection, false);
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/List.html#setModel(com.sun.dtv.lwuit.list.ListModel)">List</A></CODE></B></DD>
	 * Replaces/sets the model underlying the list.
	 *
	 * 
	 * @param m - the new model underlying the list
	 * @see setModel in class List
	 * @see List.getModel()
	 */
	public void setModel(ListModel m)
	{
		super.setModel(m);

		if (contained != null) {
			contained.setModel(m);
		}
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#setHandlesInput(boolean)">Component</A></CODE></B></DD>
	 * Prevents key events from being grabbed for focus traversal. E.g. a list component
	 * might use the arrow keys for internal navigation so it will switch this flag to
	 * true in order to prevent the focus manager from moving to the next component.
	 *
	 * 
	 * @param handlesInput - indicates whether key events can be grabbed for focus traversal
	 * @see setHandlesInput in class List
	 */
	public void setHandlesInput(boolean handlesInput)
	{
		super.setHandlesInputParent(handlesInput);
		/*getPopup().setVisible(handlesInput);
		if (isSmoothScrolling()) {
			if (handlesInput) {
				getComponentForm().registerAnimated(((Container) popupContent).getComponentAt(0));
			} else {
				getComponentForm().deregisterAnimated(((Container) popupContent).getComponentAt(0));
			}
		}*/
	}

	/**
	 * <B>Description copied from class: <CODE><A HREF="../../../../com/sun/dtv/lwuit/Component.html#pointerReleased(int, int)">Component</A></CODE></B></DD>
	 * If this Component is focused, the pointer released event
	 * will call this method.
	 *
	 * 
	 * @param x - the pointer x coordinate
	 * @param y - the pointer y coordinate
	 * @see pointerReleased in class List
	 */
	public void pointerReleased(int x, int y)
	{
		setHandlesInput(true);
	}

	public void setOrientation(int orientation){
		super.setOrientation(VERTICAL);
	}

	public void keyPressed(int keyCode){
		if(keyCode == RemoteControlEvent.VK_CONFIRM){
			Popup popup = getPopup();
			popup.setVisible(true);
		} else if(keyCode == RemoteControlEvent.VK_LEFT || keyCode == RemoteControlEvent.VK_RIGHT){
			super.keyPressed(keyCode);
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
	 * @see paint in interface ViewOnlyComponent
	 * @see paint in class List
	 */
	public void paint( Graphics g)
	{
		UIManager.getInstance().getLookAndFeel().drawComboBox(g, this);
	}

	/**
	 * @inheritDoc
	 *
	 */
	protected Dimension calcPreferredSize() {
		return UIManager.getInstance().getLookAndFeel().getComboBoxPreferredSize(this);
	}

	private Component getPopupContent() {
		contained = new List(getModel()){

			public void keyPressed(int keyCode) {
				if(keyCode == RemoteControlEvent.VK_LEFT || keyCode == RemoteControlEvent.VK_RIGHT){
					return;
				}
				super.keyPressed(keyCode);
			}

			public void keyReleased(int keyCode) {
				if(keyCode == RemoteControlEvent.VK_LEFT || keyCode == RemoteControlEvent.VK_RIGHT){
					return;
				}
				super.keyReleased(keyCode);
			}
		};
		contained.setSmoothScrolling(isSmoothScrolling());
		contained.setFixedSelection(FIXED_NONE);
		contained.setListCellRenderer(getRenderer());
		contained.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setHandlesInput(!handlesInput());
				if(popup != null){
					popup.setVisible(false);
				}
				fireActionEvent();
			}
		});

		//Container cnt = new Container(new BorderLayout());
		//cnt.addComponent(BorderLayout.CENTER, contained);
		contained.setStyle(UIManager.getInstance().getComponentStyle("ComboBoxPopup"));
		contained.setSize(contained.getPreferredSize());
		popupContent = contained;
		return popupContent;
	}


	private Popup getPopup() {
		if (popup == null) {
			popup = new Popup(getComponentForm(), getPopupContent());
		} else {
			contained.setListCellRenderer(getRenderer());
			contained.setModel(getModel());
		}
		int cHeight = popupContent.getHeight();
		int fHeight = getComponentForm().getHeight();
		int y = getAbsoluteY() + getHeight();
		int height = cHeight;//Math.min(getComponentForm().getHeight() - y, popupContent.getPreferredSize().getHeight());

		if(y + height > fHeight){
			y = getAbsoluteY() - height;
		}

		if(y < 0){
			y = 0;
		}

		popupContent.setHeight(height);
		popupContent.setX(getAbsoluteX());
		popupContent.setY(y);
		popupContent.setWidth(getWidth());
		popupContent.setFixedPosition(true);
		Style s = popupContent.getStyle();
		s.merge(getStyle());
		s.setBgTransparency(0xFF);
		s.setBorder(null);
		s.setMargin(0, 0, 0, 0);
		s.setPadding(0, 0, 0, 0);
		return popup;
	}
	
}
