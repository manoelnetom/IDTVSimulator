package javax.media;

public class StopEvent extends TransitionEvent {
    private Time mediaTime;
	public StopEvent(Controller from,
	                 int previous,
	                 int current,
	                 int target,
	                 Time mediaTime) {
		super(from, previous, current, target);
		this.mediaTime=mediaTime;
	}

	public Time getMediaTime() {
		return this.mediaTime;
	}
}
