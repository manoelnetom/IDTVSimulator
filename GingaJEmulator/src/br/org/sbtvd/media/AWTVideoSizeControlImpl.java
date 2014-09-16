package br.org.sbtvd.media;

import java.awt.Component;
import java.awt.Dimension;

import javax.tv.media.AWTVideoSize;
import javax.tv.media.AWTVideoSizeControl;

public class AWTVideoSizeControlImpl implements AWTVideoSizeControl {

	private AWTVideoSize size = new AWTVideoSize(
		new java.awt.Rectangle(0, 0, 1280, 720),
		new java.awt.Rectangle(0, 0, 1280, 720));
	
	private AWTVideoSize defaultSize = new AWTVideoSize(
		new java.awt.Rectangle(0, 0, 1280, 720),
		new java.awt.Rectangle(0, 0, 1280, 720));

	public AWTVideoSize getSize() {
		return this.size;
	}

	public AWTVideoSize getDefaultSize() {
		return this.defaultSize;
	}

	public Dimension getSourceVideoSize() {
		return this.size.getSource().getSize();
		//return new Dimension(1280, 720);
	}

	public boolean setSize(AWTVideoSize videoSize) {
		this.size = videoSize;

		return true;
	}

	public AWTVideoSize checkSize(AWTVideoSize videoSize) {
		videoSize.getDestination().setBounds((int) videoSize.getDestination().x,
			(int) videoSize.getDestination().y,
			(int) videoSize.getDestination().width,
			(int) videoSize.getDestination().height);
		return videoSize;
	}

	public Component getControlComponent() {
		return null;
	}
}
