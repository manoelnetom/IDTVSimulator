import java.util.Date;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.List;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.events.SelectionListener;
import com.sun.dtv.lwuit.events.ActionEvent;

import java.util.Vector;

public class ListXlet extends Form implements Xlet, ActionListener, SelectionListener {

	private XletContext context;	

	private List list;
	
	public void initXlet(XletContext ctx) throws XletStateChangeException {
		this.context = ctx;

		setTitle("List");

		Vector elements = new Vector();

		elements.add("Item1");
		elements.add("Item2");
		elements.add("Item3");
		elements.add("Item4");
		elements.add("Item5");
		elements.add("Item6");

		list = new List(elements);
		list.addActionListener(this);
		list.addSelectionListener(this);
		list.setNumericKeyActions(true);

		addComponent(BorderLayout.CENTER, list);
	}
	
	public void startXlet() throws XletStateChangeException {
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

	public void actionPerformed(ActionEvent evt) {
		System.out.println(evt.getSource() + ", " + evt.getKeyEvent() + ", " + evt.getCommand());
	}

	public void selectionChanged(int oldSelected, int newSelected) {
		System.out.println("Old: " + oldSelected + "  New: " + newSelected);
	}

}
