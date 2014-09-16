package javax.media.protocol;
import javax.media.Duration;

public class URLDataSource extends PullDataSource {

	protected java.net.URLConnection conn;
	protected ContentDescriptor contentType;
	protected SourceStream sources[];
	protected boolean connected;
	private URLPullSourceStream urlSource;

	protected URLDataSource() {
		conn=null;
		contentType=null;
		connected = false;
		urlSource = null;
		
	}

	public URLDataSource(java.net.URL url) throws java.io.IOException {
		conn= url.openConnection();
		contentType= new ContentDescriptor(conn.getContentType());
		connected = false;
		
	}

	public PullSourceStream[] getStreams() {
		PullSourceStream ret[] = new PullSourceStream[1];
		ret[1] = urlSource;
		return ret;
	}

	public void connect() throws java.io.IOException {
		conn.connect();
		this.connected = true;
		urlSource = new URLPullSourceStream(conn.getInputStream());
	}

	public void disconnect() {
	 this.connected = false;
	}

	public String getContentType() {
		return contentType.getContentType();
	}

	public void start() throws java.io.IOException {}

	public void stop() throws java.io.IOException {}

	public javax.media.Time getDuration() {
		return Duration.DURATION_UNKNOWN;
	}
	public Object getControl(String controlType) {
		// TODO Auto-generated method stub
		return null;
	}
	public Object[] getControls() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
