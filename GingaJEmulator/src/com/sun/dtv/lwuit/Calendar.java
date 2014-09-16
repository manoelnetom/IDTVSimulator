package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.geom.Dimension;

import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.events.DataChangedListener;

import com.sun.dtv.lwuit.plaf.UIManager;
import com.sun.dtv.lwuit.plaf.Style;

import com.sun.dtv.lwuit.layouts.*;

import com.sun.dtv.lwuit.list.DefaultListModel;
import com.sun.dtv.lwuit.list.ListModel;

import com.sun.dtv.lwuit.util.EventDispatcher;

import java.awt.Color;

import java.util.*;
import com.sun.dtv.ui.ViewOnlyComponent;
public class Calendar extends Container {
	private static final String[] MONTHS =  
    { 
		"Jan", "Feb", "Mar","Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" 
	};
	private static final String[] DAYS = 
	{ 
		"Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday" 
	};
	private static final String[] LABELS = 
	{ 
		"Su", "M", "Tu", "W", "Th", "F","Sa" 
	};
	static final long MINUTE = 1000 * 60;
	static final long HOUR = MINUTE * 60;
	static final long DAY = HOUR * 24;
	static final long WEEK = DAY * 7;
	private TimeZone tmz;
	private long[] dates = new long[42];
	private ComboBox month;
	private ComboBox year;
	private MonthView mv;
	private boolean changesSelectedDateEnabled = true;
	private EventDispatcher dispatcher = new EventDispatcher();
	private EventDispatcher dataChangeListeners = new EventDispatcher();
	/**
	 * Creates a new instance of Calendar set to the given date based on time
	 * since epoch (the java.util.Date convention)
	 * 
	 * @param time
	 *            time since epoch
	 * @param tmz
	 *            a reference timezone
	 */
	public Calendar(long time, TimeZone tmz) 
	{
		super(new BorderLayout());
		this.tmz = tmz;
		setUIID("Calendar");
		setFocusable(true);
		Container upper = new Container(new FlowLayout(Component.CENTER));

		month = new ComboBox();
		year = new ComboBox();
		mv = new MonthView(time);
		//
		Vector months = new Vector();
        for (int i = 0; i < MONTHS.length; i++) 
        {
            months.addElement("" + getLocalizedMonth(i));
        }
        ListModel monthsModel = new DefaultListModel(months);
        int selected = months.indexOf(getLocalizedMonth(mv.getMonth()));
        month.setModel(monthsModel);
        month.setSelectedIndex(selected);
        month.addActionListener(mv);
        
        java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
        cal.setTime(new java.util.Date(time));
        month.getStyle().setBgTransparency(0);
        int y = cal.get(java.util.Calendar.YEAR);
        Vector years = new Vector();
        for (int i = 2100; i > 1900; i--) 
        {
            years.addElement("" + i);
        }
        ListModel yearModel = new DefaultListModel(years);
        selected = years.indexOf("" + y);
        year.setModel(yearModel);
        year.setSelectedIndex(selected);
        year.getStyle().setBgTransparency(0);
        year.addActionListener(mv);
        Container cnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container dateCnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        dateCnt.setUIID("CalendarDate");
        
		dateCnt.addComponent(month);
        dateCnt.addComponent(year);
        cnt.addComponent(dateCnt);
        upper.addComponent(cnt);
        
        month.setVisible(true);
        year.setVisible(true);
        
		dateCnt.setVisible(true);
		cnt.setVisible(true);
		upper.setVisible(true);
		mv.setVisible(true);
		setVisible(true);
		
        addComponent(BorderLayout.NORTH, upper);
        addComponent(BorderLayout.CENTER, mv);
       
	}
	/**
	 * Creates a new instance of Calendar set to the given date based on time
	 * since epoch (the java.util.Date convention)
	 * 
	 * @param time
	 *            time since epoch
	 */
	public Calendar(long time) 
	{
		this(time, java.util.TimeZone.getDefault());
	}

	/**
	 * Constructs a calendar with the current date and time
	 */
	public Calendar() 
	{
		this(System.currentTimeMillis());
	}
	private String getLocalizedMonth(int i) 
	{
		Hashtable t = UIManager.getInstance().getResourceBundle();
	    String text = MONTHS[i];
	    if (t != null) 
	    {
	    	Object o = t.get("Calendar." + text);
	        if (o != null) 
	        	text = (String) o;
	    }
	    return text;
	}
	
   
    /**
     * This method updates the Button day.
     *
     * @param dayButton the button to be updated
     * @param day the new button day
     */
    protected void updateButtonDayDate(Button dayButton, int currentMonth, int day) 
    {
        dayButton.setText("" + day);
    }
    void componentChanged() 
    {
        java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
        cal.set(java.util.Calendar.YEAR, mv.getYear());
        cal.set(java.util.Calendar.MONTH, mv.getMonth());
        cal.set(java.util.Calendar.DAY_OF_MONTH, mv.getDayOfMonth());
        month.getParent().revalidate();
    }
    /**
     * Returns the time for the current calendar.
     *
     * @return the time for the current calendar.
     */
    public long getSelectedDay() 
    {	
        return mv.getSelectedDay();
    }
    /**
     * Return the date object matching the current selection
     *
     * @return the date object matching the current selection
     */
    public Date getDate() {
        return new Date(mv.getSelectedDay());
    }
    /**
	 * Sets the style of the month view component within the calendar.
	 *
	 * 
	 * @param s - a Style object specifying the month view style value
	 * @see getMonthViewStyle()
	 */
	public void setMonthViewStyle( Style s)
	{
		mv.setStyle(s);
	}
	/**
	 * Changes the Component Style by replacing the Component Style with the given Style.
	 *
	 * 
	 * @param s - the component Style object
	 * @see setStyle in class Component
	 * @see Component.getStyle()
	 */
	public void setStyle( Style s)
	{
		super.setStyle(s);
		month.setStyle(s);
		year.setStyle(s);
	}
	/**
	 * Sets the style of the month view component within the calendar.
	 *
	 * 
	 * @return a Style object representing the month view style value
	 * @see setMonthViewStyle(com.sun.dtv.lwuit.plaf.Style)
	 */
	public Style getMonthViewStyle()
	{
		return mv.getStyle();
	}
	/**
	 * Makes sure the component is up to date with the current style object.
	 *
	 * 
	 * @see refreshTheme in class Container
	 */
	public void refreshTheme()
	{
		mv.refreshTheme();
	}
	/**
	 * Fires when a change is made to the month view of this component.
	 *
	 * 
	 * @param l - an ActionListener object
	 * @see removeActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void addActionListener(ActionListener l)
	{
		mv.addActionListener(l);
	}

	/**
	 * Fires when a change is made to the month view of this component.
	 *
	 * 
	 * @param l - an ActionListener object
	 * @see addActionListener(com.sun.dtv.lwuit.events.ActionListener)
	 */
	public void removeActionListener(ActionListener l)
	{
		mv.removeActionListener(l);
	}
	public void setVisible(boolean visible){
		super.setVisible(visible);
		
	}
	public void paint(Graphics g)
	{
		super.paint(g);
	}
	/**
     * This method creates the Day title Component for the Month View
     *
     * @param day the relevant day values are 0-6 where 0 is sunday.
     * @return a Label that corresponds to the relevant Day
     */
    protected Label createDayTitle(int day, Style style) 
    {
        String value = UIManager.getInstance().localize("Calendar." + DAYS[day], LABELS[day]);
        Label dayh = new Label(value);
        dayh.setUIID("CalendarTitle");
        dayh.setFocusable(false);
        dayh.setEndsWith3Points(false);
        dayh.setTickerEnabled(false);
        dayh.setVerticalAlignment(ViewOnlyComponent.VERTICAL_ALIGN_CENTER);
        dayh.setHorizontalAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER);
        if(style != null)
        {
        	dayh.setStyle(style);
        }
        return dayh;
    }
    /**
     * This method creates the Day Button Component for the Month View
     *
     * @return a Button that corresponds to the Days Components
     */
    protected Button createDay() 
    {
        Button day = new Button();
        day.setAlignment(ViewOnlyComponent.HORIZONTAL_ALIGN_CENTER);
        day.setUIID("CalendarDay");
        day.setEndsWith3Points(false);
        day.setTickerEnabled(false);
        //day.setFocusable(false);
        Style style = day.getStyle();
        style.setFgSelectionColor(new Color(255,0,0));
        style.setBgSelectionColor(new Color(0,0,128),true);
        style.setBorder(null);
        return day;
    }
    void focusGainedInternal() 
	{
    	mv.focusGainedInternal();
	}
	/**
	 * MonthView */
	class MonthView extends Container implements ActionListener 
	{
		private long currentDay;
		private Button[] buttons = new Button[42];
		private Button selected;
		private long selectedDay = -1;
		private final int DAY_SPACE_H = 15;

		public MonthView(long time) 
		{
            super(new GridLayout(7, 7));
            setUIID("MonthView");
            
            Style dayStyle = new Style(getStyle());
            Font f = getStyle().getFont();
            int fix = f.stringWidth("22") / 2, fh = f.getHeight(), labelH = (fh + DAY_SPACE_H);
			int w = getWidth(), dayWidth = (w / 7);
			
			dayStyle.setFgColor(getStyle().getFgSelectionColor());
    		dayStyle.setPadding(0, 0, fix+2, 0);
    		dayStyle.setMargin(0, 0, 0, 0);
    		dayStyle.setBgColor(new Color(0, 0, 128));
    		dayStyle.setFgColor(new Color(255, 255, 255));
    		for (int iter = 0; iter < DAYS.length; iter++) 
    		{
    			Label dayLabel = createDayTitle(iter,dayStyle);
    			Dimension d = dayLabel.getPreferredSize();
                addComponent(dayLabel);
            }
    		for (int iter = 0; iter < buttons.length; iter++) 
    		{
                 buttons[iter] = createDay();
                 addComponent(buttons[iter]);
                 if (iter <= 7) 
                 {
                     buttons[iter].setNextFocusUp(year);
                 }
                 buttons[iter].addActionListener(this);
             }
             setCurrentDay(time);
             
             buttons[getDayOfMonth()].getStyle().setBgColor(new Color(0,0,128));
              
        }

		void focusGainedInternal() 
		{
			super.focusLostInternal();
			setHandlesInput(true);
			buttons[getDayOfMonth()].requestFocus();
			
		}
		public void setHandlesInput(boolean b) 
		{
			Form f = getComponentForm();
			if (f != null) 
			{
				// prevent the calendar from losing focus if its the only element
				// or when the user presses fire and there is no other component
				super.setHandlesInput(b || f.isSingleFocusMode());
			} else {
				super.setHandlesInput(b);
			}
		}
//		public void paintTitles(Graphics g)
//		{
//			LookAndFeel look = UIManager.getInstance().getLookAndFeel();
//			Font f = getStyle().getFont();
//			int fh = f.getHeight();
//			int labelH = (fh + DAY_SPACE_H);
//			int w = getWidth();
//			int dayWidth = (w / 7);
//			
//			g.setColor(getStyle().getBgColor());
//			g.fillRect(getX() + 2, getY() + 2, getWidth() - 4, labelH-4);
//
//			g.setColor(getStyle().getFgColor());
//			g.setFont(getStyle().getFont());
//			
//        	int fix = f.stringWidth("22") / 2;
//        	Style dayStyle = new Style(getStyle());
//    		dayStyle.setFgColor(getStyle().getFgSelectionColor());
//    		dayStyle.setPadding(0, 0, fix+2, 0);
//    		dayStyle.setMargin(0, 0, 0, 0);
//    		g.setColor(getStyle().getFgColor());
//    		
//            for (int iter = 0; iter < DAYS.length; iter++) 
//            {
//				int dx = (dayWidth * iter);
//				Label tmp = createDayTitle(iter, dayStyle);
//				//tmp.setSize(tmp.getPreferredSize());
//				addComponent(tmp);
//				tmp.paint(g);
//            }
////            paintDates(g,f,dayWidth,labelH,fix,fh);
//		}
//		void paintDates( Graphics g, Font f, int dayWidth, int labelH, int fix, int fh)
//		{
//			int h = getHeight();
//			int ch = h - labelH;
//			int sats = 0;
//			int dayHeight = (ch / 6);
//			int w = f.stringWidth("22");
//			LookAndFeel look = UIManager.getInstance().getLookAndFeel();
//			
//			for (int iter = 0, dx = 0, dy = 0; iter < buttons.length; iter++) 
//            {
//				int dow = getDayOfMonth();
//				dx = getX() + (dayWidth * (dow - 1)) + fix;
//				dy = getY() + ((sats) * dayHeight) + labelH+2;
//				buttons[iter].setSize(buttons[iter].getPreferredSize());
//				
//				if (iter + 1 == getDayOfMonth() ) 
//				{
//					Style style = buttons[iter].getStyle();
//					style.setBgColor(new Color(211,211,211));
//					look.drawButton(g, buttons[iter]);
//				} 
//				else 
//				{
//					look.drawButton(g, buttons[iter]);
//				}
//            }
//		}
		// GETS
		public int getDayOfMonth() 
		{
			java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
			cal.setTime(new Date(currentDay));
			return cal.get(java.util.Calendar.DAY_OF_MONTH);
		}

		public int getMonth() 
		{
			java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
			cal.setTime(new Date(currentDay));
			return cal.get(java.util.Calendar.MONTH);
		}

		public int getYear() 
		{
			java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
			cal.setTime(new Date(currentDay));
			return cal.get(java.util.Calendar.YEAR);
		}

		public void incrementMonth() 
		{
			int month = getMonth();
			month++;
			int year = getYear();
			if (month > java.util.Calendar.DECEMBER) 
			{
				month = java.util.Calendar.JANUARY;
				year++;
			}
			setMonth(year, month);
		}
	    public void decrementMonth() 
	    {
	    	int month = getMonth();
	        month--;
	        int year = getYear();
	        if (month < java.util.Calendar.JANUARY) 
	        {
	        	month = java.util.Calendar.DECEMBER;
	            year--;
	        }
	        setMonth(year, month);
	    }
	   
		private long getSelectedDay() 
		{
			return selectedDay;
		}
		public void setSelectedDay(long selectedDay)
		{
            java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
            cal.setTime(new Date(selectedDay));
            cal.set(java.util.Calendar.HOUR, 1);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 1);
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.set(java.util.Calendar.SECOND, 0);
            cal.set(java.util.Calendar.MILLISECOND, 0);
            this.selectedDay = cal.getTime().getTime();
        }
		public void setCurrentDay(long day) 
		{
			setCurrentDay(day, false);
		}
		private void setCurrentDay(long day, boolean force) 
		{
            repaint();
            java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
            cal.setTime(new Date(currentDay));
            cal.set(java.util.Calendar.HOUR, 1);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 1);
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.set(java.util.Calendar.SECOND, 0);
            cal.set(java.util.Calendar.MILLISECOND, 0);

            int yearOld = cal.get(java.util.Calendar.YEAR);
            int monthOld = cal.get(java.util.Calendar.MONTH);
            int dayOld = cal.get(java.util.Calendar.DAY_OF_MONTH);

            cal.setTime(new Date(day));
            cal.set(java.util.Calendar.HOUR, 1);
            cal.set(java.util.Calendar.HOUR_OF_DAY, 1);
            cal.set(java.util.Calendar.MINUTE, 0);
            cal.set(java.util.Calendar.SECOND, 0);
            cal.set(java.util.Calendar.MILLISECOND, 0);
            int yearNew = cal.get(java.util.Calendar.YEAR);
            int monthNew = cal.get(java.util.Calendar.MONTH);
            int dayNew = cal.get(java.util.Calendar.DAY_OF_MONTH);
            year.setSelectedItem("" + yearNew);
            month.setSelectedIndex(monthNew);

            if (yearNew != yearOld || monthNew != monthOld || dayNew != dayOld || force) 
            {
                currentDay = cal.getTime().getTime();
                if(selectedDay == -1)
                	selectedDay = currentDay;
                int month = cal.get(java.util.Calendar.MONTH);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                long startDate = cal.getTime().getTime();
                int dow = cal.get(java.util.Calendar.DAY_OF_WEEK);
                cal.setTime(new Date(cal.getTime().getTime() - DAY));
                cal.set(java.util.Calendar.HOUR, 1);
                cal.set(java.util.Calendar.HOUR_OF_DAY, 1);
                cal.set(java.util.Calendar.MINUTE, 0);
                cal.set(java.util.Calendar.SECOND, 0);
                cal.set(java.util.Calendar.MILLISECOND, 0);
                int lastDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
                int i = 0;
                if(dow > java.util.Calendar.SUNDAY)
                {
                    //last day of previous month
                    while (dow > java.util.Calendar.SUNDAY) 
                    {
                        cal.setTime(new Date(cal.getTime().getTime() - DAY));
                        dow = cal.get(java.util.Calendar.DAY_OF_WEEK);
                    }
                    for (int previousMonthSunday = cal.get(java.util.Calendar.DAY_OF_MONTH); i <= lastDay - previousMonthSunday; i++) 
                    {
                        buttons[i].setUIID("CalendarDay");
                        buttons[i].setEnabled(false);
                        buttons[i].setText("" + (previousMonthSunday + i));
                    }
                }
                //last day of current month
                cal.set(java.util.Calendar.MONTH, (month + 1) % 12);
                cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
                cal.setTime(new Date(cal.getTime().getTime() - DAY));

                lastDay = cal.get(java.util.Calendar.DAY_OF_MONTH);

                int j;
                for (j = i; j < buttons.length && (j - i + 1) <= lastDay; j++)
                {
                    buttons[j].setEnabled(true);
                    dates[j] = startDate;
                    if(dates[j] == selectedDay)
                    {
                        buttons[j].setUIID("CalendarSelectedDay");
                        selected = buttons[j];
                    }
                    else buttons[j].setUIID("CalendarDay");
                    updateButtonDayDate(buttons[j], month, j - i + 1);
                    startDate += DAY;
                }
                for (int d = 1; j < buttons.length; j++) 
                {
                    buttons[j].setUIID("CalendarDay");
                    buttons[j].setEnabled(false);
                    buttons[j].setText("" + d++);
                }
            }
        }
		private void setMonth(int year, int month) 
		{
			java.util.Calendar cal = java.util.Calendar.getInstance(tmz);
	        cal.setTimeZone(TimeZone.getDefault());
	        cal.set(java.util.Calendar.MONTH, month);
	        cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
	        cal.set(java.util.Calendar.YEAR, year);
	        Date date = cal.getTime();
	        long d = date.getTime();
            // if this is past the last day of the month (e.g. going from January 31st
	        // to Febuary) we need to decrement the day until the month is correct
	        while (cal.get(java.util.Calendar.MONTH) != month) 
	        {
	        	d -= DAY;
	        	cal.setTime(new Date(d));
	        }
	        setCurrentDay(d);
		}
		protected void fireActionEvent() 
		{
            componentChanged();
            super.fireActionEvent();
            dispatcher.fireActionEvent(new ActionEvent(Calendar.this));
        }
		public void addActionListener(ActionListener l) {
            dispatcher.addListener(l);
        }

        public void removeActionListener(ActionListener l) {
            dispatcher.removeListener(l);
        }

        /**
         * Allows tracking selection changes in the calendar in real time
         *
         * @param l listener to add
         */
        public void addDataChangeListener(DataChangedListener l) {
            dataChangeListeners.addListener(l);
        }

        /**
         * Allows tracking selection changes in the calendar in real time
         *
         * @param l listener to remove
         */
        public void removeDataChangeListener(DataChangedListener l) {
            dataChangeListeners.removeListener(l);
        }
		//actionPerformed
		 public void actionPerformed(ActionEvent evt) 
		 {
	            Object src = evt.getSource();
	            if(src instanceof ComboBox)
	            {
	                setMonth(Integer.parseInt((String)year.getSelectedItem()), month.getSelectedIndex());
	                componentChanged();
	                return;
	            }
	            
	            
	            if(changesSelectedDateEnabled)
	            {
	            	
	                for (int iter = 0; iter < buttons.length; iter++) 
	                {
	                    if (src == buttons[iter]) 
	                    {
	                        selected.setUIID("CalendarDay");
	                        buttons[iter].setUIID("CalendarSelectedDay");
	                        selectedDay = dates[iter];
	                        selected = buttons[iter];
	                        fireActionEvent();
	                        if (!getComponentForm().isSingleFocusMode()) 
	                        	setHandlesInput(false);
	                        return;
	                    }
	                }
	            }
	        }// END - actionPerformed
	}// END - MounthView 
}// END - CALENDAR
