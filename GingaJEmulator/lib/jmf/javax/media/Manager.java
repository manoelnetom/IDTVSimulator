package javax.media;

import br.edu.ifba.VLCSPlayer;
import br.org.sbtvd.net.SBTVDLocator;

import com.sun.dtv.transport.TransportStream;
import com.sun.dtv.tuner.Tuner;

import java.io.IOException;

import java.net.URL;

import java.util.Vector;

import javax.media.protocol.DataSource;

import javax.tv.locator.InvalidLocatorException;

public final class Manager extends Object {
	public static final String UNKNOWN_CONTENT_NAME = "Unknown content";
        
	public static Player createPlayer(java.net.URL url) throws java.io.IOException, NoPlayerException {
		return createPlayer(new MediaLocator(url));
	}

	public static Player createPlayer(MediaLocator mediaLocator) throws java.io.IOException, NoPlayerException {            
                return new VLCSPlayer(mediaLocator.locatorString);
		//throw new NoPlayerException();
	}

	public static Player createPlayer(DataSource source) throws java.io.IOException, NoPlayerException {
		return createPlayer(source.getLocator());
	}

	public static DataSource createDataSource(URL sourceURL) throws java.io.IOException, NoDataSourceException {
		if(isMainVideoURL(sourceURL.toExternalForm()))
		{
			return null;
		} else {
			throw new NoDataSourceException();
		}
	}

	public static DataSource createDataSource(MediaLocator sourceLocator)
		throws java.io.IOException, NoDataSourceException {
		if(isMainVideoURL(sourceLocator.toExternalForm()))
		{
			return null;
		} else {
			throw new NoDataSourceException();
		}
	}

	public static TimeBase getSystemTimeBase() {
		return new br.org.sbtvd.media.TimeBaseImpl();
	}

	public static Vector getHandlerClassList(String contentName) {
		return new Vector();
	}

	public static Vector getDataSourceList(String protocolName)
	{
		Vector vector = new Vector();

		if(protocolName.equals("dtv") || protocolName.equals("sbtvd-ts"))
		{
			// TODO:: add source to vector
		}

		return vector;
	}
	
	private static boolean isMainVideoURL(String locatorString)
	{
		//Defending against non compliant implementations.
		//Review this latter
		if(locatorString.equals("sbtvd-ts://0") || locatorString.equals("sbtvd-ts://0.0"))
		{
			return true;
		}
		
		Tuner[] tuners = Tuner.getInstances();
		TransportStream currentTS = tuners[0].getCurrentTransportStream();

		SBTVDLocator sourceLocator;
		try
		{
			 sourceLocator = new SBTVDLocator(locatorString);
		}
		catch(InvalidLocatorException exc)
		{
			return false;
		}

		if(!sourceLocator.getScheme().equals("dtv") && !sourceLocator.getScheme().equals("sbtvd-ts"))
		{
			return false;
		}
		
		if(currentTS.getLocator().getOriginalNetworkId() == sourceLocator.getOriginalNetworkId()
		   &&
		   currentTS.getLocator().getTransportStreamId() == sourceLocator.getTransportStreamId()
		   &&
		   currentTS.getLocator().getServiceId() == sourceLocator.getServiceId())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
