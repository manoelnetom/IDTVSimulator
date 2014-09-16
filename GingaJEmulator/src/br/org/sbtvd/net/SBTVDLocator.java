package br.org.sbtvd.net;

import javax.tv.locator.Locator;
//import javax.tv.locator.InvalidLocatorException;
import com.sun.dtv.locator.EntityLocator;
import javax.tv.locator.InvalidLocatorException;


public class SBTVDLocator extends com.sun.dtv.locator.EntityLocator{	
	
	private int networkId = 0;
	private int serviceId = 0;
	private int transportStreamId = 0;
	private int contentId = 0;
	private int eventId = 0;
	private int componentTag = 0;
	private int channelId = 0;
	private int[] arrayComponentTags = null;
	private String url = null;
	private String scheme = null;
	private String filePath = null;
	private String moduleName = null;
	private String resourceName = null;
	
	/*
     * Constructors
     */
	
	public SBTVDLocator(String url) throws InvalidLocatorException{
		
		super (url);
		this.url = url;
    	
	}
    
    public SBTVDLocator(String scheme, int onid, int tsid) throws InvalidLocatorException{
    
    	super (scheme + "://" + Integer.toHexString(onid) + "." + Integer.toHexString(tsid));
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	
    } 
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid) 
    		throws InvalidLocatorException{
    	
    	super (scheme + "://" + Integer.toHexString(onid) + "." + Integer.toHexString(tsid) + "."
    			+ Integer.toHexString(serviceid));
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid, 
    		int contentid) throws InvalidLocatorException{
       
    	super (scheme + "://" + Integer.toHexString(onid) + "." + Integer.toHexString(tsid) + "."
    			+ Integer.toHexString(serviceid) + ";" + Integer.toHexString(contentid));
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid, 
    		int contentid, int eventid) throws InvalidLocatorException{
    	
    	super (scheme + "://" + Integer.toHexString(onid) + "." + Integer.toHexString(tsid) + "."
    			+ Integer.toHexString(serviceid) + ";" + Integer.toHexString(contentid) + ";"
    			+ Integer.toHexString(eventid));
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid, 
    		int contentid, int eventid, int componenttag) throws InvalidLocatorException{
    	
    	super (scheme + "://" + Integer.toHexString(onid) + "." + Integer.toHexString(tsid) + "."
    			+ Integer.toHexString(serviceid) + ";" + Integer.toHexString(contentid) + ";"
    			+ Integer.toHexString(eventid) + "/" + componenttag);
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    	this.componentTag = componenttag;
    	
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid,
    		int contentid, int eventid, int[] componenttags) throws InvalidLocatorException{

    	super (scheme);
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    	this.arrayComponentTags = componenttags;
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid,
    		int contentid, int eventid, int[] componenttags, String filepath) 
    		throws InvalidLocatorException{
    	
    	super (scheme);
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    	this.arrayComponentTags = componenttags;
    	this.filePath = filepath;
    	
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid,
    		int contentid, int eventid, int componenttag, int channelid) 
    		throws InvalidLocatorException{
    	
    	super (scheme + "://" + Integer.toHexString(onid) + "." + Integer.toHexString(tsid) + "."
    			+ Integer.toHexString(serviceid) + ";" + Integer.toHexString(contentid) + ";"
    			+ Integer.toHexString(eventid) + "/" + componenttag + ";" + Integer.toHexString(channelid));
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    	this.componentTag = componenttag;
    	this.channelId = channelid;
    	
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid,
    		int contentid, int eventid, int componenttag, String modulename)
    		throws InvalidLocatorException{
    	
    	this(scheme, onid, tsid, serviceid, contentid, eventid, componenttag);
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    	this.componentTag = componenttag;
    	this.moduleName = modulename;
    	
    }
    
    public SBTVDLocator(String scheme, int onid, int tsid, int serviceid,
    		int contentid, int eventid, int componenttag, String modulename,
    		String resourcename) throws InvalidLocatorException{
    
    	this(scheme, onid, tsid, serviceid, contentid, eventid, componenttag);
    	this.scheme = scheme;
    	this.networkId = onid;
    	this.transportStreamId = tsid;
    	this.serviceId = serviceid;
    	this.contentId = contentid;
    	this.eventId = eventid;
    	this.componentTag = componenttag;
    	this.moduleName = modulename;
    	this.resourceName = resourcename;
    
    }
    
    /*
     * Methods
     */
    
    public int getChannelId(){
    	return this.channelId;
    }
    
    public int[] getComponentTags(){
    	return this.arrayComponentTags;
    }
    
    public int getContentId(){
    	return this.contentId;
    }
    
    public int getEventId(){
    	return this.eventId;
    }
    
    public String getFilePath(){
    	return this.filePath;
    }
    
    public String getModuleName(){
    	return this.moduleName;
    }
    
    public String getResourceName(){
    	return this.resourceName;
    }
    
    public String getScheme(){
    	return this.scheme;
    }
    
    public int getServiceId(){
    	return this.serviceId;
    }
    
    public int getTransportStreamId(){
    	return this.transportStreamId;
    }
    
    public int getOriginalNetworkId(){	
    	return this.networkId;
    }
    
    public String getURL(){
    	return this.url;
    		
    }
    
}
