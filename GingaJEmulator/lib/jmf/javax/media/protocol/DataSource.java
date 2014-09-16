package javax.media.protocol;
import javax.media.MediaLocator;

public abstract class DataSource extends Object implements Controls, javax.media.Duration {
	protected MediaLocator locator;
	public DataSource() {}

	public DataSource(javax.media.MediaLocator source) {
		this.locator = source;
	}

	public void setLocator(javax.media.MediaLocator source) {
		this.locator = source;
	}
	public javax.media.MediaLocator getLocator() {
		return this.locator;
	}

	protected void initCheck() {}

	public abstract String getContentType();

	public abstract void connect() throws java.io.IOException;
	public abstract void disconnect();
	public abstract void start() throws java.io.IOException;
	public abstract void stop() throws java.io.IOException;
}
