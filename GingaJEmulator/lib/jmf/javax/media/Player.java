package javax.media;

public interface Player extends MediaHandler, Controller, Duration {

	public abstract java.awt.Component getVisualComponent();

	public abstract GainControl getGainControl();
	public abstract java.awt.Component getControlPanelComponent();

	public abstract void start();

	public abstract void addController(Controller newController) throws IncompatibleTimeBaseException;
	public abstract void removeController(Controller oldController);

}
