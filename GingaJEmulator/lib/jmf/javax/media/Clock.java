package javax.media;

public interface Clock {
	public static final Time RESET = null;

	public abstract void setTimeBase(TimeBase master) throws IncompatibleTimeBaseException;
	public abstract void syncStart(Time at);
	public abstract void stop();
	public abstract void setStopTime(Time stopTime);
	public abstract Time getStopTime();
	public abstract void setMediaTime(Time now);
	public abstract Time getMediaTime();
	public abstract long getMediaNanoseconds();
	public abstract Time getSyncTime();
	public abstract TimeBase getTimeBase();
	public abstract Time mapToTimeBase(Time t) throws ClockStoppedException;
	public abstract float getRate();
	public abstract float setRate(float factor);

}
