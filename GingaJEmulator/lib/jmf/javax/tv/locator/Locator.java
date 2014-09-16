package javax.tv.locator;

public interface Locator {
	public java.lang.String toExternalForm();

	public boolean hasMultipleTransformations();

	public boolean equals(java.lang.Object o);

	public int hashCode();

	public java.lang.String toString();

}
