package javax.media;

public class GainChangeEvent extends Object implements MediaEvent {

	private GainControl from;
	private boolean mute;
	private float db;
	private float level;
	public GainChangeEvent(GainControl from,
	                       boolean mute,
	                       float dB,
	                       float level) {
		this.from = from;
		this.mute = mute;
		this.db = dB;
		this.level = level;
	}

	public Object getSource() {
		return this.from;
	}

	public GainControl getSourceGainControl() {
		return this.from;
	}

	public float getDB() {
		return this.db;
	}

	public float getLevel() {
		return this.level;
	}

	public boolean getMute() {
		return this.mute;
	}

}
