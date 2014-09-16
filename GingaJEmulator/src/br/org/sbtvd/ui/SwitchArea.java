package br.org.sbtvd.ui;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Graphics;

import com.sun.dtv.lwuit.events.FocusListener;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;

import com.sun.dtv.lwuit.plaf.Style;

public final class SwitchArea extends Component {

	public SwitchArea() {
		setUIID("SwitchArea");
	}

	public void addFocusListener(FocusListener l) {
	}

	//SwitchArea component does not support animation
	public boolean animate() {
		return false;
	}

	public boolean contains(int x, int y) {
		return super.contains(x, y);
	}

	public int getAnimationMode() {
		return super.getAnimationMode();
	}

	public int getDelay() {
		return super.getDelay();
	}

	public Rectangle getBounds() {
		return super.getBounds();
	}

	public Object getClientProperty(String key) {
		return super.getClientProperty(key);
	}

	public int getRepetitionMode() {
		return super.getRepetitionMode();
	}

	public int getScrollAnimationSpeed() {
		return super.getScrollAnimationSpeed();
	}

	public boolean isRunning() {
		return false;
	}

	public boolean isScrollVisible() {
		return false;
	}

	protected boolean isScrollable() {
		return false;
	}

	public boolean isScrollableX() {
		return false;
	}

	public boolean isScrollableY() {
		return false;
	}

	public boolean isSmoothScrolling() {
		return false;
	}

	public void jumpTo(int position) {
	}

	public void setAnimationMode(int mode) {
	}

	public void setDelay(int n) {
	}

	public void setIsScrollVisible(boolean isScrollVisible) {
	}

	public void setRepetitionMode(int n) {
	}

	public void setScrollAnimationSpeed(int animationSpeed) {
	}

	public void setSmoothScrolling(boolean smoothScrolling) {
	}

	public void start() {
	}

	public void stop() {
	}

	//SwitchArea does not support focus functionalities
	public Component getNextFocusDown() {
		return null;
	}

	public Component getNextFocusLeft() {
		return null;
	}

	public Component getNextFocusRight() {
		return null;
	}

	public Component getNextFocusUp() {
		return null;
	}

	public boolean hasFocus() {
		return false;
	}

	public boolean isFocusPainted() {
		return false;
	}

	public boolean isFocusable() {
		return false;
	}

	public void keyPressed(int keyCode) {
	}

	public void keyReleased(int keyCode) {
	}

	public void keyRepeated(int keyCode) {
	}

	public void longKeyPress(int keyCode) {
	}

	public void removeFocusListener(FocusListener l) {
	}

	public void requestFocus() {
	}

	public void setFocus(boolean focused) {
	}

	public void setFocusPainted(boolean focusPainted) {
	}

	public void setFocusable(boolean focusable) {
	}

	public void setHandlesInput(boolean handlesInput) {
	}

	public void setNextFocusDown(Component nextFocusDown) {
	}

	public void setNextFocusLeft(Component nextFocusLeft) {
	}

	public void setNextFocusRight(Component nextFocusRight) {
	}

	public void setNextFocusUp(Component nextFocusUp) {
	}

	//Paint methods
	public void repaint() {
	}

	public void repaint(int x, int y, int w, int h) {
	}

	public void repaint(Component cmp) {
	}

	public void paint(Graphics g) {
	}

	public void paintBackgrounds(Graphics g) {
	}

	public void setPreferredSize(Dimension d) {
	}

	public void setSize(Dimension d) {
	}

	public void setVisible(boolean visible) {
	}

	public void setWidth(int width) {
	}

	public void setX(int x) {
	}

	public void setY(int y) {
	}

	public void setStyle(Style style) {
	}

	public void styleChanged(String propertyName, Style source) {
	}

	public void update(Graphics g) {
	}
}
