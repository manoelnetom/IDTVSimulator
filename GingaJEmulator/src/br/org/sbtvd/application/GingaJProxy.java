package br.org.sbtvd.application;

import com.sun.dtv.application.*;
import com.sun.dtv.io.FileProperties;
import javax.tv.xlet.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.tv.graphics.TVContainer;
import net.beiker.xletview.classloader.MainClassLoader;
import net.beiker.xletview.classloader.XletClassLoader;
import net.beiker.xletview.media.BackgroundLayer;
import net.beiker.xletview.media.ScreenContainer;

public class GingaJProxy implements ApplicationProxy {

    private List listeners;
    private Application application;
    private XletContext context;
    private javax.tv.xlet.Xlet javaTVXlet = null;
    private javax.microedition.xlet.Xlet microeditionXlet = null;
    private Class loadedClass;
    private int state,
            oldstate;
    private boolean failed;

    public GingaJProxy(Application application) {
        this.application = application;

        state = NOT_LOADED;
        oldstate = NOT_LOADED;
        failed = false;

        listeners = new ArrayList();
    }

    Application getApplication() {
        return this.application;
    }

    public int getState() {
        return state;
    }

    public void pause() {
        try {
            if (javaTVXlet != null) {
                javaTVXlet.pauseXlet();
            } else {
                microeditionXlet.pauseXlet();
            }

            oldstate = state;
            state = PAUSED;
        } catch (Exception e) {
            e.printStackTrace();

            failed = true;
        }
    }

    public void resume() {
        try {
            if (javaTVXlet != null) {
                javaTVXlet.initXlet(context);
            } else {
                microeditionXlet.initXlet((javax.microedition.xlet.XletContext) context);
            }

            oldstate = state;
            state = ACTIVE;
        } catch (Exception e) {
            e.printStackTrace();

            failed = true;
        }
    }

    public void init(XletContext context) throws Exception {
    }

    public void start(String[] parameters) {
        try {
            load();
            pause();

            oldstate = state;
            state = ACTIVE;

            if (javaTVXlet != null) {
                javaTVXlet.initXlet(context);
                javaTVXlet.startXlet();
            } else {
                microeditionXlet.initXlet((javax.microedition.xlet.XletContext) context);
                microeditionXlet.startXlet();
            }
        } catch (Exception e) {
            e.printStackTrace();

            failed = true;
            state = NOT_LOADED;
        }
    }

    public void stop() {
        if (state == DESTROYED || oldstate == DESTROYED) {
            return;
        }

        try {
            if (javaTVXlet != null) {
                javaTVXlet.destroyXlet(true);
            } else {
                microeditionXlet.destroyXlet(true);
            }

            oldstate = state;
            state = DESTROYED;
        } catch (Exception e) {
            e.printStackTrace();

            failed = true;
        }
    }

    public void addListener(AppProxyListener listener) {
        if (listeners.contains((Object) listener) == false) {
            listeners.add((Object) listener);
        }
    }

    public void removeListener(AppProxyListener listener) {
        if (listeners.contains((Object) listener) == true) {
            listeners.remove((Object) listener);
        }
    }

    public void dispatchEvent(int newstate) {
        for (java.util.Iterator i = listeners.iterator(); i.hasNext();) {
            AppProxyListener listener = (AppProxyListener) i.next();

            listener.appStateChange(application, state, newstate, failed);

            if (newstate == ApplicationProxy.DESTROYED) {
                break;
            }
        }
    }

    public void load() throws Exception {
        ApplicationImpl impl = (ApplicationImpl) application;
        String dsmccDirectory = "";
        String mainclass = (String) impl.getDocument();
        String paths = (String) impl.getClasspath();
        String basedirectory = (String) impl.getBaseDirectory();
        String extraclasspath = (String) br.org.sbtvd.config.Settings.getInstance().getProperty("extra.class.path");

        Vector v = new Vector();

        System.setProperty("application.basedirectory", basedirectory);

        try {
            String classpath[] = null;

            // separando os paths por dois pontos
            String s = paths;

            if (extraclasspath != null) {
                s = extraclasspath + ":" + s;
            }

            if (s != null) {
                StringTokenizer token = new StringTokenizer(s, ":");

                classpath = new String[token.countTokens()];

                int k = 0;
                for (; token.hasMoreTokens();) {
                    String t = token.nextToken();

                    if (t.equals("") == false) {
                        classpath[k++] = t;
                    }
                }

                File file;

                // colocando paths no classpath
                for (int i = 0; i < classpath.length; i++) {
                    try {
                        v.add(new URL(classpath[i]));
                    } catch (MalformedURLException mue) {
                        file = new File(classpath[i]);

                        if (file.isDirectory()) {
                            v.add(new URL("file:///" + file.getCanonicalPath() + "/"));
                        } else {
                            v.add(new URL("file:///" + file.getCanonicalPath()));
                        }
                    }
                }
            }

            System.out.println("GingaJProxy:: System.getProperty(application.basedirectory) = " + System.getProperty("application.basedirectory"));

            MainClassLoader mainClassLoader = (MainClassLoader) getClass().getClassLoader();

            /*
             for (Enumeration e = v.elements(); e.hasMoreElements();) {
             classLoader.addClassPath(e.nextElement().toString());
             }
             */

            URL urls[] = new URL[mainClassLoader.getURLs().length + v.size()];
            int k = 0;

            for (int i = 0; i < mainClassLoader.getURLs().length; i++) {
                urls[k++] = mainClassLoader.getURLs()[i];
            }

            for (Enumeration e = v.elements(); e.hasMoreElements();) {
                urls[k++] = (URL) e.nextElement();
            }

            XletClassLoader classLoader = new XletClassLoader(urls);

            loadedClass = classLoader.loadClass(mainclass);

            System.out.println("GingaJProxy:: System.getProperty(application.basedirectory) = " + System.getProperty("application.basedirectory"));
            try {
                // Jeff::
                System.out.println(loadedClass);
                System.out.println(loadedClass.getClasses().length);
                System.out.println(loadedClass.getDeclaredClasses().length);
                System.out.println(loadedClass.getGenericSuperclass());
                javaTVXlet = (javax.tv.xlet.Xlet) loadedClass.newInstance();
            } catch (java.lang.ClassCastException e) {
                e.printStackTrace();
                microeditionXlet = (javax.microedition.xlet.Xlet) loadedClass.newInstance();
            }

            StringTokenizer token = new StringTokenizer(impl.getAppId(), ":");

            int oid = Integer.parseInt(token.nextToken(), 16);
            int aid = Integer.parseInt(token.nextToken(), 16);
            System.setProperty("application.oid", "" + oid);
            System.setProperty("application.aid", "" + aid);

            File oidFile = new File(System.getProperty("com.sun.dtv.persistent.root") + "/" + oid);
            if (!oidFile.exists()) {
                oidFile.mkdir();
            }

            File aidFile = new File(System.getProperty("com.sun.dtv.persistent.root") + "/" + oid + "/" + aid);
            if (!aidFile.exists()) {
                aidFile.mkdir();
            }

            context = new br.org.sbtvd.application.XletContextImpl(this, oid, aid);
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GingaJProxy other = (GingaJProxy) obj;
        if (this.application != other.application && (this.application == null || !this.application.equals(other.application))) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.application != null ? this.application.hashCode() : 0);
        return hash;
    }
}
