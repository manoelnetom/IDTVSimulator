package br.org.sbtvd.net.tuning;

import com.sun.dtv.transport.TransportStream;

public class Channel {

    private TransportStream ts;
    private String name;
    private int virtualChannelNumber;

    Channel(TransportStream ts, String name, int virtualNumber)
    {
	this.ts = ts;
	this.name = name;
	this.virtualChannelNumber = virtualNumber;
    }

    public TransportStream getTransportStream()
    {
	return ts;
    }

    public String getTransportStreamName()
    {
	return name;
    }

    public int getRemoteControlKeyId()
    {
	return virtualChannelNumber;
    }
}
