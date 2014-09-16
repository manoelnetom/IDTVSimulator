package br.org.sbtvd.application;

import com.sun.dtv.application.*;

import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.beiker.xletview.media.ScreenContainer;

public class XletRunner implements AppProxyListener {
  
	private ApplicationImpl attr = null;
	private ApplicationProxy proxy = null;

	static
	{
		System.setProperty("com.sun.dtv.persistent.root", "/ginga/");
		System.setProperty("com.sun.dtv.version", "1.0");

		//Environment variables accordingly to Ginga-J specification and operational guideline
		System.setProperty("system.gingaj_profile", "A");
		System.setProperty("system.gingaj_version", "1.0.0");
		System.setProperty("system.gingancl_version", "1.0.0");
		System.setProperty("system.internet_access", "true");
		System.setProperty("com.sun.dtv.net.default.timeout", "4");
	}

	public XletRunner(int oid, int aid, String type, String code, String document, String basedirectory, String classpath) {
		System.out.println("Java:: XletRunner init");

		attr = new ApplicationImpl(oid, aid, document, classpath, basedirectory, null, Application.APP_TYPE_JAVA, Integer.parseInt(code), false, true, true);
	}

	public synchronized void stop() {
		System.out.println("Java:: XletRunner stopping Xlet");

		proxy.removeListener(this);

		proxy.stop();
                
		com.sun.dtv.ui.Device device = com.sun.dtv.ui.Device.getInstance();
		com.sun.dtv.ui.Screen currentScreen = device.getDefaultScreen();
		com.sun.dtv.ui.event.UserInputEventManager manager = com.sun.dtv.ui.event.UserInputEventManager.getUserInputEventManager(currentScreen);

		manager.removeAll();

		com.sun.dtv.ui.DTVContainer.removeAllReferences();
                
                //TODO: [felipe] foi alterado para sumir com a camada de emulação, o xletview não consegue matar o xlet!!!
                ScreenContainer.getInstance().setEmulatorLayerVisible(false);
                
                //System.exit(0);

	}

	// start()
	public synchronized boolean start() {
		System.out.println("Java:: XletRunner starting Xlet");

		try {
			AppManagerImpl database = (AppManagerImpl)AppManager.getInstance();

			database.addApplication(attr);

			proxy = database.getApplicationProxy(attr.getAppId());
			
			if (proxy != null) {
				proxy.addListener(this);

				Thread thread = new Thread()  {
					public void run() {
						try {
							proxy.start(null);
						} catch (Exception e) {
							System.out.println("Java:: XletRunner thread starting error");
                                                        e.printStackTrace();
						}
					}
				};
				
				thread.start();

				return true;
			}
                        
                        
                        
		} catch (Exception e) {
			System.out.println("Java:: XletRunner starting error");
                        e.printStackTrace();
		}
		
		return false;
	}

	public void appStateChange(Application app, int oldstate, int newstate, boolean failed) {
		System.out.println("XletRunner:: stateChanged()");
	
		if (newstate == ApplicationProxy.DESTROYED) {
			stop();
		}
	}

	public void updateStatus(int status) {
		if (status == GingaJProxy.DESTROYED) {
			stop();
		}
	}

	public static void main(String args[]) {
		for (int i=0; i<args.length; i++) {
			System.out.println(":" + i + " " + args[i]);
		}

		XletRunner xlet = new XletRunner(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2], args[3], args[4], args[5], args[6]);
            
//                XletRunner xlet = new XletRunner(12345, 12345, "1", "1", "ClimaXlet", "build/classes/xlet", "build/classes/xlet");
            
		xlet.start();
	}

}

