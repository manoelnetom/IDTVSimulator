package br.org.sbtvd.net.tuning;

import com.sun.dtv.tuner.Tuner;
import com.sun.dtv.tuner.TunerListener;
import com.sun.dtv.tuner.TuningException;

import com.sun.dtv.transport.*;

import java.util.ArrayList;

public class ChannelManager {

    public static ChannelManager channelManager = new ChannelManager();

    public static ChannelManager getInstance()
    {
	return channelManager;
    }

    public int getNumberOfChannels()
    {
	Tuner[] tuners = Tuner.getInstances();

	return tuners[0].getAvailableTransportStreams().length;
    }

    public Channel[] getChannels()
    {
        return null;
    }

    public void tuneChannel(Channel ch, TunerListener listener) throws TuningException
    {
	Tuner.getInstances()[0].tune(ch.getTransportStream());
    }

    public void tuneNextChannel(TunerListener listener) throws TuningException
    {
	throw new TuningException("Operation not permitted");
    }

    public void tunePreviousChannel(TunerListener listener) throws TuningException
    {
	throw new TuningException("Operation not permitted");
    }
}
