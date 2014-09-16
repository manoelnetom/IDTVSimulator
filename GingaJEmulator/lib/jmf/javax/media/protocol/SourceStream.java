package javax.media.protocol;

public interface SourceStream extends Controls {

	public static final long LENGTH_UNKNOWN = -1;

	public abstract long getContentLength();
	public abstract boolean endOfStream();

}
