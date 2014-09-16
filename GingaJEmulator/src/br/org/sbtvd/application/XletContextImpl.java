package br.org.sbtvd.application;

import com.sun.dtv.application.*;

public class XletContextImpl implements javax.microedition.xlet.XletContext, javax.tv.xlet.XletContext {

  private ApplicationProxy proxy;
	private int oid;
	private int aid;
        
  public XletContextImpl(ApplicationProxy proxy, int oid, int aid) {
		this.proxy = proxy;
		this.oid = oid;
		this.aid = aid;
  }
  
  public void notifyDestroyed() {
		((GingaJProxy)proxy).dispatchEvent(ApplicationProxy.DESTROYED);
  }
  
  public void notifyPaused() {
		((GingaJProxy)proxy).dispatchEvent(ApplicationProxy.PAUSED);
  }
  
  public void resumeRequest() {
		((GingaJProxy)proxy).dispatchEvent(ApplicationProxy.ACTIVE);
  }

  public ClassLoader getClassLoader()
  {
	  return this.getClass().getClassLoader();
  }

  public java.awt.Container getContainer() throws javax.microedition.xlet.UnavailableContainerException
  {
  	return com.sun.dtv.lwuit.Display.getInstance().getRootContainer();
  }
  
  public Object getXletProperty(String key) {
	  if (key.equals("com.sun.dtv.orgid") == true) {
		  return new Integer(oid);
	  } else if (key.equals("com.sun.dtv.appid") == true) {
		  return new Integer(aid);
	  } else if(key.equals("com.sun.dtv.persistent.root")){
		  return System.getProperty(key);
	  } else if(key.equals("com.sun.dtv.version")){
		  return System.getProperty("com.sun.dtv.version");
	  } else if(key.equals("br.org.ginga.system.version")){
		  return System.getProperty("system.gingaj_version");
	  }
	  return null;
  }

}
