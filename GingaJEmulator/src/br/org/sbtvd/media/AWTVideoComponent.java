package br.org.sbtvd.media;

import java.awt.Graphics;
import java.awt.Point;

import com.sun.dtv.lwuit.AWTComponent;

public class AWTVideoComponent extends java.awt.Component{
	private AWTComponent awtComponent;

	public void setWrapperComponent(AWTComponent awtComponent) {
		this.awtComponent = awtComponent;
	}
}
