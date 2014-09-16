package javax.tv.media;

public class MediaSelectCARefusedEvent extends MediaSelectFailedEvent {
	public MediaSelectCARefusedEvent(javax.media.Controller source, javax.tv.locator.Locator[] selection) {
		super(source, selection);
	}

}
