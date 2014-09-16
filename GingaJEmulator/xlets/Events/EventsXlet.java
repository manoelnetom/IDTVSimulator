import java.io.IOException;

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.BorderLayout;
import com.sun.dtv.ui.event.KeyEvent;

public class EventsXlet extends Form implements Xlet {

	private XletContext context;
	private Image image;

	private int step = 8;
	private int ix = 100;
	private int iy = 100;

	public void initXlet(XletContext ctx) throws XletStateChangeException {
		System.out.println("**** DEBUG -- initXlet");

		this.context = ctx;

		setTitle("Events");
	}

	public void startXlet() throws XletStateChangeException {
		System.out.println("**** DEBUG -- startXlet");

		try {
			image = Image.createImage("images/test.png");
		} catch (IOException e) {
		}

		show();
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(image, ix, iy);
	}

	public void keyPressed(int code) {
		System.out.println("Key: " + code);

		if (code == KeyEvent.VK_LEFT) {
			ix = ix - step;
		} else if (code == KeyEvent.VK_RIGHT) {
			ix = ix + step;
		} else if (code == KeyEvent.VK_UP) {
			iy = iy - step;
		} else if (code == KeyEvent.VK_DOWN) {
			iy = iy + step;
		}

		if (ix < 0) {
			ix = 0;
		}

		if (iy < 0) {
			iy = 0;
		}

		if ((ix+image.getWidth()) > getWidth()) {
			ix = getWidth()-image.getWidth();
		}

		if ((iy+image.getHeight()) > getHeight()) {
			iy = getHeight()-image.getHeight();
		}

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
