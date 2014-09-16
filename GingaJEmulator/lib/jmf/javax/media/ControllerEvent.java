package javax.media;

public class ControllerEvent implements MediaEvent {
    private Controller source;
	public ControllerEvent(Controller from) {
		source = from;
	}

	public Controller getSourceController() {
		return source;
	}

	public Object getSource() {
		return source;
	}

}
