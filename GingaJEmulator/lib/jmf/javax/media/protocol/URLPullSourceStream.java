package javax.media.protocol;

import javax.media.protocol.*;
import java.io.IOException;
import java.io.InputStream;

public class URLPullSourceStream implements PullSourceStream {

	private InputStream in;

	public URLPullSourceStream(InputStream in) {
		this.in = in;
	}

	public int read(byte[] buffer, int offset, int length) throws IOException {
		return in.read(buffer, offset, length);

	}

	public boolean willReadBlock() {
		try {
			if (in.available() == 0)
				return true;
			return false;
		} catch (IOException ioe) {
			return false;
		}
	}

	public boolean endOfStream() {
		try {

			if (in.available() == 0)
				return true;
			return false;
		} catch (IOException ioe) {
			return false;
		}
	}

	public long getContentLength() {
		// TODO Auto-generated method stub
		return 0;
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
