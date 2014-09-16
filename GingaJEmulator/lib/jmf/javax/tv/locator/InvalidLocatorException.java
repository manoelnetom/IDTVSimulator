package javax.tv.locator;

public class InvalidLocatorException extends java.lang.Exception {

	private Locator invalidLocator;

	public InvalidLocatorException(Locator locator) {
		invalidLocator = locator;
	}

	public InvalidLocatorException(Locator locator, java.lang.String reason) {
		super(reason);
		invalidLocator = locator;
	}

	public Locator getInvalidLocator() {
		return this.invalidLocator;
	}

}
