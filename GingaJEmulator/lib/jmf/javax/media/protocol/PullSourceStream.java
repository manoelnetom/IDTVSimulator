package javax.media.protocol;

public interface PullSourceStream extends SourceStream {

	public abstract boolean willReadBlock();

	public abstract int read(byte buffer[],
	                         int offset,
	                         int length) throws java.io.IOException;
}
