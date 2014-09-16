import java.awt.BorderLayout;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.TextArea;
import com.sun.dtv.lwuit.layouts.Layout;

public class KeyboardXlet extends Form implements Xlet {

	private XletContext context;	
	
	public void initXlet(XletContext ctx) throws XletStateChangeException {
		this.context = ctx;
	}

	public void startXlet() throws XletStateChangeException {
		setTitle("Keyboard");
		
		TextArea textArea = new TextArea();

		textArea.setConstraint(TextArea.URL); // ANY, EMAILADDR, NUMERIC, PHONENUMBER, URL, DECIMAL, PASSWORD
		
		addComponent(BorderLayout.CENTER, textArea);
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
