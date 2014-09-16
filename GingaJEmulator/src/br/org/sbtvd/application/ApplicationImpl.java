package br.org.sbtvd.application;

import com.sun.dtv.application.*;

import javax.tv.locator.*;

public class ApplicationImpl implements Application {

	private String identifier;
	private String document;
	private String classpath;
	private String basedir;
	private String icon;
	private int oid;
	private int aid;
	private int type;
	private int code;
	private boolean isServiceBound;
	private boolean isStartable;
	private boolean isVisible;

	public ApplicationImpl(int oid, int aid, String document, String classpath, String basedir, String icon, int type, int code, boolean serviceBound, boolean startable, boolean visible) {
		System.out.println("Application:: [aid=" + aid + ", oid=" + oid + ", type=" + type + ", code=" + code + ", base=" + basedir + ", main=" + document + ", classpath=" + classpath + ", icon=" + icon + "]");

		this.oid = oid;
		this.aid = aid;
		this.document = document;
		this.classpath = classpath;
		this.basedir = basedir;
		this.icon = icon;
		this.type = type;
		this.isServiceBound = serviceBound;
		this.isStartable = startable;
		this.isVisible = visible;
	}

	public String getAppId() {
		return com.sun.dtv.application.AppManager.getInstance().makeApplicationId(oid, aid);
	}

	public int getIconFlags() {
		// Refer to ABNT NBR 15606-3:2007 Table 54 for Icon flag bits
		return 0;
	}

	public Locator getIconLocator() {
		return null; // new javax.tv.locator.LocatorFactory.getInstance().createLocator(icon);
	}

	public String getIxcFullyQualifiedName(String name, int scope) {
		return null;
	}

	public String getName() {
		return document;
	}

	public String getName(String locale) {
		return document;
	}

	public String[] getNameLocales() {
		return new String[0];
	}

	public int getPriority() {
		return 10;
	}

	public String[] getProfiles() {
		return new String[0];
	}

	public Locator getServiceLocator() {
		return null;
	}

	public int getType() {
		return type;
	}

	public short[] getVersion(String appProfile) {
		return new short[0];
	}

	public boolean isServiceBound() {
		return isServiceBound;
	}

	public boolean isStartable() {
		return isStartable;
	}

	public boolean isVisible() {
		return isVisible;
	}

	// added by jeff
	public String getDocument() {
		return document;
	}

	public String getClasspath() {
		return classpath;
	}

	public String getBaseDirectory() {
		return basedir;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ApplicationImpl other = (ApplicationImpl) obj;
		if (this.oid != other.oid) {
			return false;
		}
		if (this.aid != other.aid) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + this.oid;
		hash = 41 * hash + this.aid;
		return hash;
	}


}
