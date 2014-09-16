package javax.media;

public class StopTimeChangeEvent extends ControllerEvent {
	private Time stopTime;
	public StopTimeChangeEvent(Controller from, Time newStopTime) {
		super(from);
		this.stopTime = newStopTime;
	}

	public Time getStopTime() {
		return this.stopTime;
	}
}
