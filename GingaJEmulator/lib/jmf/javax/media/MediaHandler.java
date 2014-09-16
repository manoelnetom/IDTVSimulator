package javax.media;

public interface MediaHandler {

	public abstract void setSource(javax.media.protocol.DataSource source)
	throws java.io.IOException, IncompatibleSourceException;

}
