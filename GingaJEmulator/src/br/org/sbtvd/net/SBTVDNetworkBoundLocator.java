package br.org.sbtvd.net;

import com.sun.dtv.locator.NetworkBoundLocator;
//import javax.tv.locator.InvalidLocatorException;
import javax.tv.locator.InvalidLocatorException;

/**
 ** 
 * @author jonatas
 * 
 */
public class SBTVDNetworkBoundLocator extends NetworkBoundLocator {

	private int networkId = 0;
	private SBTVDLocator locator = null;

	/**
	 * 
	 * @param unboundLocator
	 * @param networkId
	 * @throws InvalidLocatorException
	 */
	public SBTVDNetworkBoundLocator (SBTVDLocator unboundLocator, int nid)
			throws InvalidLocatorException{
	
		super(unboundLocator.getURL());
		locator = unboundLocator;
		networkId = nid;
	}

	/**
	 * 
	 * @return
	 */
	public int getNetworkId() {

		return networkId;

	}

}
