package javax.media;

public interface CachingControl extends Control {

	public static final long LENGTH_UNKNOWN = Long.MIN_VALUE;

	public abstract boolean isDownloading();

	public abstract long getContentLength();
	public abstract long getContentProgress();
	public abstract java.awt.Component getProgressBarComponent();
	public abstract java.awt.Component getControlComponent();

}
