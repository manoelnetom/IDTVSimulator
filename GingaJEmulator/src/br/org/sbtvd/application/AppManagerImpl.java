package br.org.sbtvd.application;

import com.sun.dtv.application.*;

import java.util.*;
import java.io.*;

public class AppManagerImpl extends AppManager {

  private static Vector applications;
  private static List listeners;
  private static List proxies;
 
	public AppManagerImpl() {
		applications = new Vector();
		listeners = new ArrayList();
		proxies = new ArrayList();
	}

	public void addApplication(Application app) {
		applications.add(app);
	}

	public Enumeration getApplications()
	{
		return applications.elements();
	}

	public Enumeration getActiveApplications()
	{
		Vector vector = new Vector();
		for(int i=0; i<proxies.size(); i++)
		{
			if(((ApplicationProxy)proxies.get(i)).getState() == ApplicationProxy.ACTIVE)
			{
				if(proxies.get(i) instanceof GingaJProxy)
				{
					vector.add(((GingaJProxy)proxies.get(i)).getApplication());
				}
			}
		}

		return vector.elements();
	}

	public Enumeration getApplications(AppFilter appFilter)
	{
		Vector vector = new Vector();

		for (Enumeration e=getApplications(); e.hasMoreElements(); ) {
			Application app = (Application)e.nextElement();

			if (appFilter.accepts(app)) {
				vector.add(app);
			}
		}

		return vector.elements();
	}

	public Enumeration getActiveApplications(AppFilter appFilter)
	{
		Vector vector = new Vector();

		for (Enumeration e=getActiveApplications(); e.hasMoreElements(); ) {
			Application app = (Application)e.nextElement();

			if (appFilter.accepts(app)) {
				vector.add(app);
			}
		}

		return vector.elements();
	}

	public String makeApplicationId(int organization, int applicationid)
	{
		return "" + Integer.toHexString(organization) + ":" + Integer.toHexString(applicationid);
	}

	public Application getApplication(String appId)
	{
		for (Enumeration e=applications.elements(); e.hasMoreElements(); ) {
			Application app = (Application)e.nextElement();

			if (app.getAppId().equals(appId) == true) {
				return app;
			}
		}

		return null;
	}

	public ApplicationProxy getApplicationProxy(String appId)
	{
		Application application = null;
		for(int i=0; i<applications.size(); i++)
		{
			if(((Application)applications.get(i)).getAppId().equals(appId))
			{
				application = (Application) applications.get(i);
				break;
			}
		}

		if(application == null)
		{
			return null; //We do not have this application registered
		}

		for(int i=0; i<proxies.size(); i++)
		{
			if(proxies.get(i) instanceof GingaJProxy)
			{
				if(((GingaJProxy)proxies.get(i)).getApplication().getAppId().equals(appId))
				{
					return (ApplicationProxy) proxies.get(i);
				}
			}
		}

		//If we are here, we have an application without an associated proxy
		//so we create a new proxy and add it to the proxies list
		ApplicationProxy proxy = new GingaJProxy(application);
		proxies.add(proxy);

		return proxy;
	}

	public Application fetch(String locator) throws IOException
	{
		throw new IOException();
	}

	public void unload(String appId)
	{
	}

	public void addListener(AppManagerListener listener)
	{
		if (listeners.contains((Object)listener) == false) {
			listeners.add((Object)listener);
		}
	}

	public void removeListener(AppManagerListener listener)
	{
		if (listeners.contains((Object)listener) == true) {
			listeners.remove((Object)listener);
		}
	}
  
}
