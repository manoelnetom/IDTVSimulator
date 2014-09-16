package javax.media.protocol;

public interface Seekable {

	public abstract long seek(long where);
	public abstract long tell();
	public abstract boolean isRandomAccess();
}
