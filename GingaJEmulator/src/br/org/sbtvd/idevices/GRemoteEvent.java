package br.org.sbtvd.idevices;

public class GRemoteEvent {
	
	public static final int AUDIO_REQUEST	= 1;
	public static final int VIDEO_REQUEST	= 2;
	public static final int PICTURE_REQUEST	= 3;
	public static final int FILE_TRANSFER	= 4;
	public static final int NUMBER_DIALED	= 5;
	
	public static final int AUDIO_DATA		= 100;
	public static final int VIDEO_DATA		= 101;
	public static final int PICTURE_DATA	= 102;
	public static final int KEY_DATA		= 103;
	
	private java.lang.Object source;
	private int type;
	private byte[] data;
	private String description;
	private boolean successful;
	
	public GRemoteEvent(java.lang.Object source, int type, byte[] data, String description, boolean successful) {
		this.source = source;
		this.type = type;
		this.data = data;
		this.description = description;
		this.successful = successful;
	}
	
	public java.lang.Object getSource() {
		return source;
	}
	
	public int getType() {
		return type;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isSuccessful() {
		return successful;
	}

}
