package javax.media;

public interface Duration {

	public static final Time DURATION_UNBOUNDED = null;
	public static final Time DURATION_UNKNOWN = null;

	public abstract Time getDuration();

}
