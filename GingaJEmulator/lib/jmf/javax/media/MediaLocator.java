package javax.media;

import java.net.MalformedURLException;
import java.net.URL;

public class MediaLocator extends Object {

	URL url;
	String locatorString;

	public MediaLocator(String locatorString)
	{
		this.locatorString = locatorString;

		try
		{
			this.url = new URL(locatorString);
		}
		catch(MalformedURLException exc)
		{
			this.url = null;
		}
	}

	public MediaLocator(URL url)
	{
		this.url = url;

		this.locatorString = this.url.toExternalForm();
	}

	public java.net.URL getURL() throws java.net.MalformedURLException {
		if(this.url == null)
		{
			this.url = new URL(this.locatorString);
		}

		return this.url;
	}

	public String getProtocol() {
		if(this.url == null)
		{
			return "";
		}

		return this.url.getProtocol();
	}

	public String getRemainder() {
		if(this.url == null)
		{
			return "";
		}

		String remainder = this.url.toExternalForm();
		String protocol = this.getProtocol();

		remainder = remainder.substring(protocol.length(), remainder.length());

		return remainder;
	}

	public String toString() {
		if(this.url == null)
		{
			return this.locatorString;
		}
		else
		{
			return this.url.toString();
		}
	}

	public String toExternalForm()
	{
		if(this.url == null)
		{
			return this.locatorString;
		}
		else
		{
			return this.url.toExternalForm();
		}
	}

}
