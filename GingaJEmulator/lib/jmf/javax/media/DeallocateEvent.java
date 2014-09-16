package javax.media;

public class DeallocateEvent extends StopEvent {

	public DeallocateEvent(Controller from,
	                       int previous,
	                       int current,
	                       int target,
	                       javax.media.Time mediaTime) {
		super(from, previous, current, target, mediaTime);
	}

}
