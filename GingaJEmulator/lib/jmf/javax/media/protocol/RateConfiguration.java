package javax.media.protocol;

public interface RateConfiguration {

	public abstract RateRange getRate();
	public abstract SourceStream[] getStreams();

}
