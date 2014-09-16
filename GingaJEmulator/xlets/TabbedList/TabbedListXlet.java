import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.List;
import com.sun.dtv.lwuit.TabbedPane;
import com.sun.dtv.lwuit.layouts.BorderLayout;

public class TabbedListXlet extends Form implements Xlet {

	private XletContext context;	
	
	public void initXlet(XletContext ctx) throws XletStateChangeException {
		System.out.println("**** DEBUG -- initXlet");

		this.context = ctx;

		setTitle("TabbedList");
	}

	public void startXlet() throws XletStateChangeException {
		System.out.println("**** DEBUG -- startXlet");

		TabbedPane tabs = new TabbedPane();

		List list1 = new List(new String[] {"Item 1", "Item 2", "Item 3"});
		List list2 = new List(new String[] {"Item 4", "Item 5", "Item 6"});
		List list3 = new List(new String[] {"Item 5", "Item 6", "Item 7"});
		List list4 = new List(new String[] {"Item 8", "Item 9", "Item 10"});

		tabs.addTab("Tab 1", list1);
		tabs.addTab("Tab 2", list2);
		tabs.addTab("Tab 3", list3);
		tabs.addTab("Tab 4", list4);

		tabs.setVisible(true);

		addComponent(BorderLayout.CENTER, tabs);
		show();
	}

	public void pauseXlet() {
	}

	public void destroyXlet(boolean unconditional) throws XletStateChangeException {
		if (!unconditional) {
			throw new XletStateChangeException();
		}

		context.notifyDestroyed();
	}

}
