
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Image;

public class CabeloXlet extends Form implements Xlet {

    private XletContext context;
    private Image image;

    @Override
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        System.out.println("**** DEBUG -- initXlet");

        this.context = ctx;

        setTitle("Cabelo Teste");
    }

    @Override
    public void startXlet() throws XletStateChangeException {
        System.out.println("**** DEBUG -- startXlet");
        show();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void pauseXlet() {
    }

    @Override
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
        if (!unconditional) {
            throw new XletStateChangeException();
        }

        context.notifyDestroyed();
    }
}
