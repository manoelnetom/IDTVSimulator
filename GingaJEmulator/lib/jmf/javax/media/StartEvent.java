package javax.media;

public class StartEvent extends TransitionEvent {

	public StartEvent(Controller from,
	                  int previous,
	                  int current,
	                  int target,
	                  Time mediaTime,
	                  Time tbTime) {
		super(from, previous, current, target);
	}

	public Time getMediaTime() {
		return null;
	}

	public Time getTimeBaseTime() {
		return null;
	}

}
