package javax.media;

public class RateChangeEvent extends ControllerEvent {
	float rate;
	public RateChangeEvent(Controller from, float newRate) {
		super(from);
		this.rate = newRate;
	}

	public float getRate() {
		return this.rate;
	}

}
