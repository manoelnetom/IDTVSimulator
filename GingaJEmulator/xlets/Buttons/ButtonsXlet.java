import java.awt.Color;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Button;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.lwuit.layouts.Layout;
import com.sun.dtv.lwuit.plaf.Style;
import com.sun.dtv.lwuit.plaf.UIManager;

public class ButtonsXlet extends Form implements Xlet {

	private XletContext context;	
	
	public void initXlet(XletContext ctx) throws XletStateChangeException {
		System.out.println("**** DEBUG -- initXlet");

		setTitle("Buttons");

		this.context = ctx;
	}
	
	public void startXlet() throws XletStateChangeException {
		System.out.println("**** DEBUG -- startXlet");
		
		Style style = new Style();
		style.setBgColor(new Color(255, 0, 0));
		style.setBgSelectionColor(new Color(0, 255, 0));
		
		Style style1 = new Style();
		style1.setBgColor(new Color(255, 0, 0));
		style1.setBgSelectionColor(new Color(0, 255, 0));
		
		UIManager.getInstance().setComponentStyle("Button", style);
		Button bCenter = new Button("Center");
		addComponent(BorderLayout.CENTER, bCenter);

		show();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		UIManager.getInstance().setComponentStyle("Button", style1);
		Button bNorth = new Button("North");
		addComponent(BorderLayout.NORTH, bNorth);
		
		repaint();
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

