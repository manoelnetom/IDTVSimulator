import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;

import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.layouts.BorderLayout;

public class LabelXlet extends Form implements Xlet {

	private XletContext context;

	public void initXlet(XletContext ctx) throws XletStateChangeException {
		System.out.println("**** DEBUG -- initXlet");

		this.context = ctx;

		setTitle("Label");
	}

	public void startXlet() throws XletStateChangeException {
		System.out.println("**** DEBUG -- startXlet");

		Label label = new Label("");
		Label label2 = new Label("");
		
		label.setVerticalAlignment(Label.VERTICAL_ALIGN_TOP);
		label.setHorizontalAlignment(Label.HORIZONTAL_ALIGN_LEFT);

		String text =  "For some odd reason, work allows me to handle phone screens and interviews. Each time " +
			"I give an interview, I try to do three things. First I ask them about general programming questions. " +
			"This might be OO questions. It might be methodology questions. It might be design pattern questions. " +
			"Next I like to ask them more specific technologies questions, such as questions “how do you do ABC " +
			"in Flex? Java?”. Lastly I want to know what they do in their spare time. What books they read? Do they " +
			"code outside of work? How they go about researching new technologies? Etc.However, the place I find " +
			"people getting stuck are basic/general programming knowledge. Recently I conducted an interview, and " +
			"this person just missed every question I asked. I don’t mind if people miss questions. Sometimes, it " +
			"just takes some leading and they will get the correct answer. There are certain things though that if " +
			"you miss entirely, then we have a problem. This has inspired me for this new section that I would like " +
			"to call “Learn This”. These are topics that I find rather important for a potential candidate to know. " + 
			"There are a few past articles I could think about putting into this section, but I will start fresh.";

		label.setText(text);
		label.setEndsWith3Points(true);
		
		label2.setText(text);
		label2.setEndsWith3Points(false);
		
		addComponent(BorderLayout.NORTH, label);
		addComponent(BorderLayout.SOUTH, label2);

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
