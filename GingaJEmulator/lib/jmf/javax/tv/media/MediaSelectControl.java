package javax.tv.media;

public interface MediaSelectControl extends javax.media.Control {
	public void select(javax.tv.locator.Locator component) throws javax.tv.locator.InvalidLocatorException, javax.tv.service.selection.InvalidServiceComponentException, javax.tv.service.selection.InsufficientResourcesException, java.lang.SecurityException;

	public void select(javax.tv.locator.Locator[] components) throws javax.tv.locator.InvalidLocatorException, javax.tv.service.selection.InvalidServiceComponentException, javax.tv.service.selection.InsufficientResourcesException, java.lang.SecurityException;

	public void add
		(javax.tv.locator.Locator component) throws javax.tv.locator.InvalidLocatorException, javax.tv.service.selection.InvalidServiceComponentException, javax.tv.service.selection.InsufficientResourcesException, java.lang.SecurityException;

	public void remove
		(javax.tv.locator.Locator component) throws javax.tv.locator.InvalidLocatorException, javax.tv.service.selection.InvalidServiceComponentException, java.lang.SecurityException;

	public void replace(javax.tv.locator.Locator fromComponent, javax.tv.locator.Locator toComponent) throws javax.tv.locator.InvalidLocatorException, javax.tv.service.selection.InvalidServiceComponentException, javax.tv.service.selection.InsufficientResourcesException, java.lang.SecurityException;

	public void addMediaSelectListener( MediaSelectListener listener);

	public void removeMediaSelectListener( MediaSelectListener listener);

	public javax.tv.locator.Locator[] getCurrentSelection();

}
