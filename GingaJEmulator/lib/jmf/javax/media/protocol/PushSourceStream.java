package javax.media.protocol;

public interface PushSourceStream extends SourceStream {

	public abstract int read(byte buffer[],
	                         int offset,
	                         int length);

	public abstract int getMinimumTransferSize();

	public abstract void setTransferHandler(SourceTransferHandler transferHandler);
}
