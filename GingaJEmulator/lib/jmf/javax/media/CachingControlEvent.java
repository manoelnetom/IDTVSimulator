package javax.media;

public class CachingControlEvent extends ControllerEvent {

	public CachingControlEvent(Controller from,
	                           CachingControl cacheControl,
	                           long progress) {
		super(from);
	}

	public long getContentProgress() {
		return 0;
	}
}
