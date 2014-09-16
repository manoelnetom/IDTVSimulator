import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.ButtonGroup;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.RadioButton;
import com.sun.dtv.lwuit.TabbedPane;
import com.sun.dtv.lwuit.events.SelectionListener;
import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.layouts.BoxLayout;

class ALL implements SelectionListener{
	
	public void selectionChanged(int oldSelected, int newSelected) {
		System.out.println("**** DEBUG -- selectionChanged: from " + oldSelected + " to " + newSelected);
	}
	
}

public class TabbedRadioXlet extends Form implements Xlet {

	private XletContext context;	
	
	public void initXlet(XletContext ctx) throws XletStateChangeException {
		System.out.println("**** DEBUG -- initXlet");

		this.context = ctx;

		setTitle("TabbedRadio");

		TabbedPane tp = new TabbedPane();
		SelectionListener l = new ALL();
		
		tp.addTabsListener(l);
		
		Container tabPanel = new Container(new BoxLayout(BoxLayout.Y_AXIS));

		RadioButton radioButton1 = new RadioButton("Radio Button 1");
		RadioButton radioButton2 = new RadioButton("Radio Button 2");
		RadioButton radioButton3 = new RadioButton("Radio Button 3");
		ButtonGroup buttonGroup1 = new ButtonGroup();
		
		buttonGroup1.add(radioButton1);
		buttonGroup1.add(radioButton2);
		buttonGroup1.add(radioButton3);

		tabPanel.addComponent(radioButton1);
		tabPanel.addComponent(radioButton2);
		tabPanel.addComponent(radioButton3);

		RadioButton radioButtonA = new RadioButton("Radio Button A");
		RadioButton radioButtonB = new RadioButton("Radio Button B");
		ButtonGroup buttonGroup2 = new ButtonGroup();
		
		buttonGroup2.add(radioButtonA);
		buttonGroup2.add(radioButtonB);

		tabPanel.addComponent(radioButtonA);
		tabPanel.addComponent(radioButtonB);

		tp.addTab("Tab 1", new Label("LWUIT: TabbedPane"));
		tp.addTab("Tab 2", tabPanel);
		tabPanel.setVisible(true);
		tp.setVisible(true);
		
		addComponent(BorderLayout.CENTER, tp);
	}

	public void startXlet() throws XletStateChangeException {
		System.out.println("**** DEBUG -- startXlet");

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
