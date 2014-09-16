package javax.media.protocol;

public class ContentDescriptor extends Object {

	public static final String CONTENT_UNKNOWN = "Content Unknown";

	protected String typeName;

	public ContentDescriptor(String cdName) {
		this.typeName = cdName;
	}

	public String getContentType() {
		return this.typeName;
	}

	public static final String mimeTypeToPackageName(String mimeType) {
		String ret = mimeType;
		ret.toLowerCase();
		ret.replace('/', '.');
		
		return ret;
	}

}
