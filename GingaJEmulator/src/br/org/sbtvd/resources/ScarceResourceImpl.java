package br.org.sbtvd.resources;

import java.util.LinkedList;
import com.sun.dtv.resources.ResourceTypeListener;
import com.sun.dtv.resources.ScarceResource;
import com.sun.dtv.resources.ScarceResourceListener;
import com.sun.dtv.resources.TimeoutException;

public abstract class ScarceResourceImpl implements ScarceResource{
	
	public ScarceResourceImpl()
	{
	}

	public ScarceResourceImpl(ScarceResourceListener listener)
	{
	}

	public void reserve(boolean force, long timeoutms, ScarceResourceListener listener) 
		throws IllegalArgumentException, TimeoutException, SecurityException 
	{
	}

	public void release() {
	}

	public boolean isAvailable() {
		return true;
	}

	abstract protected void notifyScarceResourceTypeListeners(boolean reserved);
	
	public static void addResourceTypeListener(ResourceTypeListener listener) throws NullPointerException{
	}

	public static void removeResourceTypeListener(ResourceTypeListener listener) throws NullPointerException{
	}
}
