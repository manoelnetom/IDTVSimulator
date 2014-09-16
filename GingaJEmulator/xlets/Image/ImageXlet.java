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

public class ImageXlet extends Form implements Xlet {

	private XletContext context;
	private Image image;

	private int step = 8;
	private int ix = 100;
	private int iy = 100;
	private int idx = 1;
	private int idy = 1;

	public void initXlet(XletContext ctx) throws XletStateChangeException {
		System.out.println("**** DEBUG -- initXlet");

		this.context = ctx;

		setTitle("Image");
	}

	public void startXlet() throws XletStateChangeException {
		System.out.println("**** DEBUG -- startXlet");

		try {
			image = Image.createImage("images/test.png");
		} catch (IOException e) {
		}

		registerAnimated(this);

		show();
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(image, ix, iy);
	}

	public boolean animate() {
		ix = ix + idx*step;
		iy = iy + idy*step;

		if (ix < 0) {
			ix = 0;
			idx = -idx;
		}

		if (iy < 0) {
			iy = 0;
			idy = -idy;
		}

		if ((ix+image.getWidth()) > getWidth()) {
			ix = getWidth()-image.getWidth();
			idx = -idx;
		}

		if ((iy+image.getHeight()) > getHeight()) {
			iy = getHeight()-image.getHeight();
			idy = -idy;
		}

		return true;
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
