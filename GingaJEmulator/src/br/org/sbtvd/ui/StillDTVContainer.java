package br.org.sbtvd.ui;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.animations.Transition;

import com.sun.dtv.ui.DTVContainer;

public class StillDTVContainer extends DTVContainer {

    public StillDTVContainer()
    {
	super();
	this.setType(DTVContainer.STILL);
    }

    public void addComponent(Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.addComponent(cmp);
	}
    }

    public void addComponent(Object constraints, Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.addComponent(constraints, cmp);
	}
    }

    public void addToBack(Component component) {
	if(component instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.addToBack(component);
	}
    }

    public void addToFront(Component component) {
	if(component instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.addToFront(component);
	}
    }

    public void pop(Component component) {
	if(component instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.pop(component);
	}
    }

    public void popInFrontOf(Component move, Component behind) {
	if(move instanceof br.org.sbtvd.ui.StillPicture && behind instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.popInFrontOf(move, behind);
	}
    }

    public void popToFront(Component component) {
	if(component instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.popToFront(component);
	}
    }

    public void push(Component component) {
	if(component instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.push(component);
	}
    }

    public void pushBehind(Component move, Component front) {
	if(move instanceof br.org.sbtvd.ui.StillPicture && front instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.pushBehind(move, front);
	}
    }

    public void pushToBack(Component component) {
	if(component instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.pushToBack(component);
	}
    }

    public void setBackgroundImage(Image image) {
    }

    public void setBackgroundImageRenderMode(int mode) {
    }

    public void setBackgroundMode(int mode) {
    }

    public void setToFront() {
    }

    public void addComponent(int index, Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.addComponent(index, cmp);
	}
    }

    public boolean contains(Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.StillPicture)
	{
	    return super.contains(cmp);
	}

	return false;
    }

    public void pointerPressed(int x, int y) {
    }

    public void removeComponent(Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.removeComponent(cmp);
	}
    }

    public void replace(Component current, Component next, Transition t) {
	if(current instanceof br.org.sbtvd.ui.StillPicture && next instanceof br.org.sbtvd.ui.StillPicture)
	{
	    super.replace(current, next, t);
	}
    }

    public void requestFocus()
    {
    }
}
