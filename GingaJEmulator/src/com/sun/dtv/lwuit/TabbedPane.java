package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Transition;
import com.sun.dtv.lwuit.events.SelectionListener;
import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.list.ListCellRenderer;

import com.sun.dtv.lwuit.plaf.Border;
import com.sun.dtv.lwuit.plaf.LookAndFeel;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;

import com.sun.dtv.lwuit.list.DefaultListModel;

import java.util.Enumeration;
import java.util.Hashtable;

public class TabbedPane extends Container {
	private Transition transitionRight;
	private Transition transitionLeft;
	private Container contentPane = new Container(new BorderLayout());
	private List tabsList = new List();
	private Hashtable tabsTable = new Hashtable();
	private int tabPlacement = -1;
	private int tabPlaneBorder = 1;
	private int lastListX;
	private int lastListY;
	/**
	 * Creates an empty <code>TabbedPane</code> with a default tab placement of
	 * <code>Component.TOP</code>.
	 */
	public TabbedPane() 
	{
		this(TOP);
	}

	public TabbedPane(int tabPlacement) 
	{
		super(new BorderLayout());
		contentPane.setUIID("TabbedPane");
		//this.setFocusable(true);
		//setting a border
		Border border = new Border();
		getStyle().setFgSelectionColor(getStyle().getFgColor());
		border.setFocusedInstance(Border.createDoubleBorder(5));
		contentPane.getStyle().setBorder(border);
		//defining a background painter for Tabbedpane
		contentPane.getStyle().setBgPainter(new Painter() 
		{
			public void paint(Graphics g, Rectangle rect) 
			{
				LookAndFeel look = UIManager.getInstance().getLookAndFeel();
				look.drawTabbedPaneContentPane
				(
						TabbedPane.this, g, rect,
						tabsList.getPreferredSize(), tabsList.size(),
						tabsList.getSelectedIndex(),
						tabsList.getElementSize(true),
						tabsList.getScrollX(), tabsList.getScrollY()
				);
			}
		});
		super.addComponent(BorderLayout.CENTER, contentPane);
		setTabPlacement(tabPlacement);
		Style tabsListStyle = tabsList.getStyle();
		tabsListStyle.setPadding(0, 0, 0, 0);
		tabsListStyle.setMargin(0, 0, 0, 0);
		tabsListStyle.setBorder(null);
		
		tabsList.setListCellRenderer(new TabsRenderer());
		tabsList.setItemGap(0);
		tabsList.setIsScrollVisible(false);
		tabsList.addSelectionListener(new SelectionListener() 
		{
			public void selectionChanged(int oldSelected, int newSelected) {
				if (oldSelected == newSelected) return;
				Component c = (Component) tabsList.getModel().getItemAt(newSelected);
				Transition t = transitionLeft;
				if (oldSelected < newSelected) 
					t = transitionRight;
				if (c != null) 
				{
					if (t == null || contentPane.getComponentCount() == 0) 
					{
						contentPane.removeAll();
						Component cmp = (Component) tabsTable.get(c);
						contentPane.addComponent(BorderLayout.CENTER,cmp);
						if (isInitialized()) revalidate();
						else  setShouldCalcPreferredSize(true);
					} 
					else{
						Component cmp = (Component) tabsTable.get(c);
						contentPane.replace(contentPane.getComponentAt(0),cmp, t);
					}
				}
			}
		});
	}
	public boolean animate() 
	{
	    boolean result = super.animate();
	    int x = tabsList.getScrollX();
	    int y = tabsList.getScrollY();
	    if(lastListY != y || lastListX != x) 
	    {
	    	result = true;
	        lastListX = x;
	        lastListY = y;
	    }
	    return result;
	 }
	/**
     * Sets the tab placement for this tabbedpane.
     * Possible values are:<ul>
     * <li><code>Component.TOP</code>
     * <li><code>Component.BOTTOM</code>
     * <li><code>Component.LEFT</code>
     * <li><code>Component.RIGHT</code>
     * </ul>
     * The default value, if not set, is <code>Component.TOP</code>.
     * 
     * @param tabPlacement - the placement for the tabs relative to the content
     */
    public void setTabPlacement(int tabPlacement) 
    {
    	if (tabPlacement != TOP && tabPlacement != LEFT && tabPlacement != BOTTOM && tabPlacement != RIGHT) 
    	{
            throw new IllegalArgumentException("illegal tab placement: must be TOP, BOTTOM, LEFT, or RIGHT");
        }
        if (this.tabPlacement == tabPlacement)
            return;
        this.tabPlacement = tabPlacement;
        removeComponent(tabsList);
        if (tabPlacement == TOP || tabPlacement == BOTTOM) 
        {
            tabsList.setOrientation(List.HORIZONTAL);
            if (tabPlacement == TOP) 
            	super.addComponent(BorderLayout.NORTH, tabsList);
            else if (tabPlacement == BOTTOM) 
                super.addComponent(BorderLayout.SOUTH, tabsList);
        } 
        else 
        {	// LEFT Or RIGHT
            tabsList.setOrientation(List.VERTICAL);
            if (tabPlacement == LEFT) 
            	super.addComponent(BorderLayout.WEST, tabsList);
            else
            	super.addComponent(BorderLayout.EAST, tabsList);
        }
        tabsList.setShouldCalcPreferredSize(true);
        contentPane.setShouldCalcPreferredSize(true);
        revalidate();
    }
    /**
     * Adds a <code>component</code> 
     * represented by a <code>title</code> and/or <code>icon</code>,
     * either of which can be <code>null</code>.
     * Cover method for <code>insertTab</code>.
     * 
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * 
     * @see #insertTab
     * @see #removeTabAt
     */
    public void addTab(String title, Image icon, Component component, int index) 
    {
        insertTab(title, icon, component, index);
    }
    /**
     * Adds a <code>component</code> 
     * represented by a <code>title</code> and no <code>icon</code>.
     * Cover method for <code>insertTab</code>.
     * 
     * @param title the title to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * 
     * @see #insertTab
     * @see #removeTabAt
     */
    public void addTab(String title, Component component) 
    {
        insertTab(title, null, component, tabsList.size());
    }
    /**
     * Inserts a <code>component</code>, at <code>index</code>,
     * represented by a <code>title</code> and/or <code>icon</code>,
     * either of which may be <code>null</code>.
     * Uses java.util.Vector internally, see <code>insertElementAt</code>
     * for details of insertion conventions. 
     *
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component The component to be displayed when this tab is clicked.
     * @param index the position to insert this new tab 
     *
     * @see #addTab
     * @see #removeTabAt
     */
    public void insertTab(String title, Image icon, Component component, int index) 
    {
        checkIndex(index);
        if (component != null) 
        {
        	Button b = new Button(title != null ? title : "", icon);
        	DefaultListModel model = ((DefaultListModel) tabsList.getModel());
        	model.addItemAtIndex(b, index);
        	tabsTable.put(b, component);
        	if (tabsList.size() == 1) 
        		contentPane.addComponent(BorderLayout.CENTER, component);
       }

    }
    /**
     * This method adds a listener to the tabs. 
     * @param listener - a selection listener to gets the selection events*/
    public void addTabsListener(SelectionListener listener)
    {
    	tabsList.addSelectionListener(listener);
    }
    /**
     * Returns the currently selected index for this tabbedpane.
     * Returns -1 if there is no currently selected tab.
     *
     * @return the index of the selected tab
     */
    public int getSelectedIndex() 
    {
        return tabsList.getSelectedIndex();
    }
    /**
    * Updates the information about the tab details
    * 
    * @param title the title to be displayed in this tab
    * @param icon the icon to be displayed in this tab
    * @param index the position to insert this new tab 
    */
    public void setTabTitle(String title, Image icon, int index) 
    {
    	checkIndex(index);
    	Button b = (Button)tabsList.getModel().getItemAt(index);
    	DefaultListModel model = ((DefaultListModel) tabsList.getModel());
    	b.setText(title);
    	b.setIcon(icon);
    	model.setItem(index, b);
    }
    
    /**
     * Removes the tab at <code>index</code>.
     * After the component associated with <code>index</code> is removed,
     * its visibility is reset to true to ensure it will be visible
     * if added to other containers.
     * @param index the index of the tab to be removed
     * @exception IndexOutOfBoundsException if index is out of range 
     *            (index < 0 || index >= tab count)
     *
     * @see #addTab
     * @see #insertTab  
     */
    public void removeTabAt(int index) 
    {
    	checkIndex(index);
        Object key = tabsList.getModel().getItemAt(index);
        DefaultListModel model =((DefaultListModel) tabsList.getModel());
        model.removeItem(index);
        tabsTable.remove(key);
    }
    /**
     * The prototype is optionally used in calculating the size of an individual
     * tab and is recommended for performance reasons. You should invoke it with a String
     * representing the width/height which will be used to calculate
     * the size required for each element in the list.\
     * <p>This operation is not essential and if it isn't invoked the size of the first
     * few tabs is used to determine the overall size of a tab.
     * <p>This allows the size calculations to work across look and feels and allows
     * developers to predetermin size for the tabs. 
     * <p>e.g. For tabs which you would like to always be 5 characters wide
     * you can use a prototype "XXXXX" which would use the preferred size of the XXXXX 
     * String to determine the size of the tabs..
     * 
     * @param title a string to determine the size.
     */
    public void setTabTitlePrototype(String title)
    {
    	tabsList.setRenderingPrototype(title);
    }
    /**
     * Returns the number of tabs in this <code>tabbedpane</code>.
     *
     * @return an integer specifying the number of tabbed pages
     */
    public int getTabCount() 
    {
        return tabsList.size();
    }
    /**
     * The TabbedPane surrounded border width
     * 
     * @return The TabbedPane surrounded border width
     */
    public int getTabbedPaneBorderWidth() 
    {
        return tabPlaneBorder;
    }
    /**
     * Setting the TabbedPane surrounded border width
     * 
     * @param tabbedPaneBorderWidth TabbedPane surrounded border width
     */
    public void setTabbedPaneBorderWidth(int tabbedPaneBorderWidth) 
    {
    	if(tabPlaneBorder < 1)
    		return;
    	else 
    		this.tabPlaneBorder = tabbedPaneBorderWidth;
    }
    /**
     *  Sets the padding of the tabbed pane.
        @param top - value for top
        @param bottom - value for bottom
        @param left - value for left side
        @param right - value for right side
     */
    public void setPadding(int top, int bottom, int left, int right) 
    {
        if (contentPane != null) 
        {
            contentPane.getStyle().setPadding(top, bottom, left, right);
        }
    }
    /**
     *  Sets the padding of the tabbed pane.
        @param orientation - orientation of the padding
        @param gap - gap of the padding
     */
    public void setPadding(int orientation, int gap) 
    {
        if (contentPane != null) 
        {
            contentPane.getStyle().setPadding(orientation, gap);
        }
    }

    /**
     * Sets the selected index for this tabbedpane. The index must be a valid 
     * tab index.
     * @param index the index to be selected
     * @throws IndexOutOfBoundsException if index is out of range 
     * (index < 0 || index >= tab count)
     */
    public void setSelectedIndex(int index) 
    {
        if (index < 0 || index >= tabsList.size()) 
        {
            throw new IndexOutOfBoundsException("Index: "+index+", Tab count: "+tabsList.size());
        }
        tabsList.setSelectedIndex(index);
    }
    /**
     * Returns the tab at <code>index</code>.
     * 
     * @param index the index of the tab to be removed
     * @exception IndexOutOfBoundsException if index is out of range 
     *            (index < 0 || index >= tab count)
     * @return the component at the given tab location
     * @see #addTab
     * @see #insertTab  
     */
    public Component getTabComponentAt(int index) 
    {
        checkIndex(index);
        return (Component) tabsTable.get(((Component) tabsList.getModel().getItemAt(index)));
    }
    /**
     * Returns the index of the tab for the specified component.
     * Returns -1 if there is no tab for this component.
     *
     * @param component the component for the tab
     * @return the first tab which matches this component, or -1
     *		if there is no tab for this component
     */
    public int indexOfComponent(Component component) 
    {
        for (int i = 0; i < getTabCount(); i++) 
        {
            Component c = (Component) tabsList.getModel().getItemAt(i);
            Component content = (Component) tabsTable.get(c);    
            if(component.equals(content))
            {
                return i;
            }
        }
        return -1;
    }
    /**
     * Returns the placement of the tabs for this tabbedpane.
     * 
     * @return the tab placement value
     * @see #setTabPlacement
     */
    public int getTabPlacement() 
    {
        return tabPlacement;
    }
    /**
     * Indicates the transition to use when switching between tabs from right to left. 
     * 
     * @param transitionLeft transition to use when switching tabs or null to avoid transition
     */
    public void setTransitionLeft(Transition transitionLeft)
    {
        this.transitionLeft = transitionLeft;
    }

    /**
     * Indicates the transition to use when switching between tabs from right to left. 
     * 
     * @return the transition towards the left direction
     */
    public Transition getTransitionLeft() 
    {
        return transitionLeft;
    }
    
    /**
     * Indicates the transition to use when switching between tabs from left to right. 
     * 
     * @param transitionRight transition to use when switching tabs or null to avoid transition
     */
    public void setTransitionRight(Transition transitionRight) 
    {
        this.transitionRight = transitionRight;
    }

    /**
     * Indicates the transition to use when switching between tabs from left to right. 
     * 
     * @return the transition towards the right direction
     */
    public Transition getTransitionRight() 
    {
        return transitionRight;
    }
    
    /**
     * A simple setter to determine if this Component can get focused.
     * Overrides: setFocusable in class Component 
     * @param b - indicate whether this component can get focused*/
    public void setFocusable(boolean b) 
    {
        if(tabsList != null) 
        {
            tabsList.setFocusable(b);
        }
        super.setFocusable(b);
    }
    
    /** 
     * Changes the current component to the focused component, will work only for 
     * a component that belongs to a parent form.
     * Overrides RequestFocus in class Component*/
    public void requestFocus()
    {
    	tabsList.requestFocus();
    }
    
    /** 
     * Makes sure the component is up to date with the current style object. 
     * Overrides refreshTheme in class Container*/
    public void refreshTheme()
	{
		Painter p = contentPane.getStyle().getBgPainter();
        super.refreshTheme();
        contentPane.getStyle().setBgPainter(p);
        Enumeration e = tabsTable.elements();
        while(e.hasMoreElements()) 
        {
            Component c = (Component)e.nextElement();
            c.refreshTheme();
        }
	}
    
    public boolean hasFocus() 
    {
        if(tabsList != null) 
        {
            return tabsList.hasFocus();
        }
        else return super.hasFocus();
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
    	UIManager.getInstance().getLookAndFeel().drawTabbedPane(g, this);
    }
    
    public String toString() 
    {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf('.') + 1);
        return className + "[x=" + getX() + " y=" + getY() + " width=" +
                getWidth() + " height=" + getHeight() + ", tab placement = " +
                tabPlacement + ", tab count = " + getTabCount() +
                ", selected index = " + getSelectedIndex() + "]";
    }
    
    public void setVisible(boolean visible)
    {
    	super.setVisible(visible);
    	if(contentPane != null)
    	{
    		contentPane.setVisible(visible);
    	}
    }
    
    private void checkIndex(int index) 
    {
        if (index < 0 || index > tabsList.size()) 
        {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }
    protected Dimension calcPreferredSize() 
    {
        int maxContentW = 0;
        int maxContentH = 0;
        int maxW = 0, maxH = 0;
        int preferredW,preferredH;

        for (int i = 0; i < tabsList.size(); i++) 
        {
            Component tabsComp = (Component) tabsList.getModel().getItemAt(i);
            Component contentComp = (Component) tabsTable.get(tabsComp);
            	
            preferredW = contentComp.getPreferredSize().getWidth();
            preferredH = contentComp.getPreferredSize().getHeight();
            if (preferredW > maxContentW) 
            {
                maxContentW = preferredW;
            }
            if (preferredH > maxContentH) 
            {
                maxContentH = preferredH;
            }
        }
        if (tabPlacement == TOP || tabPlacement == BOTTOM) 
        {
            maxW = maxContentW;
            maxH = tabsList.getPreferredSize().getHeight() + maxContentH;
        } 
        else 
        {
            maxW = tabsList.getPreferredSize().getWidth() + maxContentW;
            maxH = maxContentH;
        }
        return new Dimension(maxW, maxH);
    }
    
    class TabsRenderer implements ListCellRenderer 
    {
    	public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) 
    	{             
    		
    		Button button;
            if(value == null || (!(value instanceof Button))) 
            {
                button = new Button("" + value);
            }
            else
            {
            	button = (Button) value;
            }
            LookAndFeel look = UIManager.getInstance().getLookAndFeel();
            Component cmp = look.getTabbedPaneCell
            (
            		TabbedPane.this, button.getText(), button.getIcon(), isSelected,
                    list.hasFocus(), list.getStyle(), TabbedPane.this.getStyle(),
                    list.getScrollX(), list.getScrollY(),
                    list.getPreferredSize(), contentPane.getBounds().getSize()
            ); 
            return cmp;
        }
        public Component getListFocusComponent(List list) 
        {
        	return null;
        }
    }
}
