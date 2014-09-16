package javax.tv.locator;

public abstract class LocatorFactory extends java.lang.Object {
	
	private static LocatorFactory instance = null;

	protected LocatorFactory() {
	}

	public static LocatorFactory getInstance() {
		if (instance == null) {
			instance = new com.sun.tv.LocatorFactoryImpl();
		}
		
		return instance;
	}

	public abstract Locator createLocator(java.lang.String locatorString) throws MalformedLocatorException;

	public abstract Locator[] transformLocator( Locator source) throws javax.tv.locator.InvalidLocatorException;

}
