package javax.media;

public class MediaTimeSetEvent extends ControllerEvent {
	private Time mediaTime;
	public MediaTimeSetEvent(Controller from, Time newMediaTime) {
		super(from);
		this.mediaTime = newMediaTime;
	}
	 public Time getMediaTime(){
		 return this.mediaTime;
	 }

}
