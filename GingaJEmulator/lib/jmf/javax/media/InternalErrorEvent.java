package javax.media;

public class InternalErrorEvent extends ControllerErrorEvent {

	public InternalErrorEvent(Controller from) {
		super(from);
	}

	public InternalErrorEvent(Controller from, String message) {
		super(from,message);
	}

}
