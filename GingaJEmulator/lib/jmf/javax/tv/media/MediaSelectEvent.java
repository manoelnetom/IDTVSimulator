package javax.tv.media;

public abstract class MediaSelectEvent extends java.util.EventObject {
	protected javax.media.Controller controller;
	private javax.tv.locator.Locator[] selection;

	public MediaSelectEvent(javax.media.Controller controller, javax.tv.locator.Locator[] selection) {
		super(controller);
		this.selection = selection;
	}

	public javax.media.Controller getController() {
		return this.controller;
	}

	public javax.tv.locator.Locator[] getSelection() {
		return this.selection;
	}

}
