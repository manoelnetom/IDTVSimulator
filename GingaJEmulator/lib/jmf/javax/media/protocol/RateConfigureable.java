package javax.media.protocol;

public interface RateConfigureable {

	public abstract RateConfiguration[] getRateConfigurations();

	public abstract RateConfiguration setRateConfiguration(RateConfiguration config);
}
