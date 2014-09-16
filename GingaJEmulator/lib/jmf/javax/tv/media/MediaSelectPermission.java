package javax.tv.media;

import javax.tv.locator.Locator;
import javax.tv.locator.LocatorFactory;
import javax.tv.locator.MalformedLocatorException;

public final class MediaSelectPermission extends java.security.Permission
		implements java.io.Serializable {
	private java.lang.String actions;

	Locator locator;

	public MediaSelectPermission(javax.tv.locator.Locator locator) {
		super(locator.toString());
		this.locator = locator;
	}

	public MediaSelectPermission(java.lang.String locator,
			java.lang.String actions) {
		super(locator.toString());
		LocatorFactory lf = LocatorFactory.getInstance();
		try {
			this.locator = lf.createLocator(locator);
		} catch (MalformedLocatorException mle) {
		}

	}

	public boolean implies(java.security.Permission p) {
		if (this.equals(p)|| this.locator.equals("*"))
			return true;
		
			return false;
	}

	public boolean equals(java.lang.Object other) {
		if (other instanceof MediaSelectPermission){
			MediaSelectPermission mp = (MediaSelectPermission)other;
			if(this.locator.equals(mp.locator))
				return true;
		}
		return false;
	}

	public int hashCode() {
		return 0;
	}

	public java.lang.String getActions() {
		return this.actions;
	}

}
