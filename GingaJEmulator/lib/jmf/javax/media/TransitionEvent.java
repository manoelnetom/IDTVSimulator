package javax.media;

public class TransitionEvent extends ControllerEvent {

	private int current,target;
	public TransitionEvent(Controller from,
	                       int previous,
	                       int current,
	                       int target) {
		super(from);
		this.current=current;
		this.target=target;
	}

	public int getCurrentState() {
		return this.current;
	}

	public int getTargetState() {
		return this.target;
	}

}
