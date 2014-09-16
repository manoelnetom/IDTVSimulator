package br.org.sbtvd.ui;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.animations.Transition;

import com.sun.dtv.ui.DTVContainer;

public class SwitchingDTVContainer extends DTVContainer {
    
    public SwitchingDTVContainer()
    {
	super();
	this.setType(DTVContainer.SWITCHING);
    }

    public void addComponent(Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.addComponent(cmp);
	}
    }

    public void addComponent(Object constraints, Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.addComponent(constraints, cmp);
	}
    }

    public void addToBack(Component component) {
	if(component instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.addToBack(component);
	}
    }

    public void addToFront(Component component) {
	if(component instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.addToFront(component);
	}
    }

    public void pop(Component component) {
	if(component instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.pop(component);
	}
    }

    public void popInFrontOf(Component move, Component behind) {
	if(move instanceof br.org.sbtvd.ui.SwitchArea && behind instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.popInFrontOf(move, behind);
	}
    }

    public void popToFront(Component component) {
	if(component instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.popToFront(component);
	}
    }

    public void push(Component component) {
	if(component instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.push(component);
	}
    }

    public void pushBehind(Component move, Component front) {
	if(move instanceof br.org.sbtvd.ui.SwitchArea && front instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.pushBehind(move, front);
	}
    }

    public void pushToBack(Component component) {
	if(component instanceof br.org.sbtvd.ui.SwitchArea)
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
	if(cmp instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.addComponent(index, cmp);
	}
    }

    public boolean contains(Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    return super.contains(cmp);
	}

	return false;
    }

    public void pointerPressed(int x, int y) {
    }

    public void removeComponent(Component cmp) {
	if(cmp instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.removeComponent(cmp);
	}
    }

    public void replace(Component current, Component next, Transition t) {
	if(current instanceof br.org.sbtvd.ui.SwitchArea && next instanceof br.org.sbtvd.ui.SwitchArea)
	{
	    super.replace(current, next, t);
	}
    }

    public void requestFocus()
    {
    }
}
