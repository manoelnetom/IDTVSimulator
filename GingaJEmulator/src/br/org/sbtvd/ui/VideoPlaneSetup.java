package br.org.sbtvd.ui;

import java.awt.Color;

import com.sun.dtv.transport.TransportStream;
import com.sun.dtv.tuner.Tuner;
import com.sun.dtv.ui.*;

import com.sun.dtv.lwuit.*;
import com.sun.dtv.lwuit.geom.*;

import java.io.*;

import javax.media.Manager;
import javax.media.NoPlayerException;

public class VideoPlaneSetup extends PlaneSetup {

	public VideoPlaneSetup(Plane p) {
		planeAspectRatio = new Dimension(16, 9);
		pixelAspectRatio = new Dimension(1, 1);
		pixelResolution = new Dimension(1, 1);
		videoSource = null;
		videoController = null;
		pattern = createPattern();
		plane = p;
		color = new Color(0x00, 0x00, 0x00);
		
		screenResolution = new Dimension(1920, 1080);
		screenArea = new Rectangle(0, 0, screenResolution);

		//Review in MStar installation
		Tuner tuner = Tuner.getInstances()[0];
		TransportStream ts = tuner.getCurrentTransportStream();
		try
		{
			videoController = Manager.createPlayer(ts.getLocator().getMediaLocator());
		}
		catch(IOException exc)
		{
			;//do nothing here
		}
		catch(NoPlayerException exc)
		{
			;//do nothing here
		}
	}

	public Point convertPoint( PlaneSetup destination, Point source)
	{
		return null;
	}

	public boolean isFlickeringFilterAvailable()
	{
		return false;
	}

	public boolean isInterlaced()
	{
		return false;
	}

	public Dimension getOffset( PlaneSetup setup)
	{
		return new Dimension(0, 0);
	}

	public boolean isVideoMixingSupported()
	{
		return false;
	}

	public boolean isGraphicsMixingSupported()
	{
		return false;
	}

	public boolean isMatteSupported()
	{
		return false;
	}

	public boolean isImageScalingSupported()
	{
		return true;
	}

	public boolean isIndexedColorModelSupported()
	{
		return false;
	}

	public boolean isDirectColorModelSupported()
	{
		return false;
	}

	public void displayImage( Image image) throws IOException, IllegalArgumentException, SecurityException, NullPointerException, SetupException
	{
	}

	public void displayImage( Image image, Rectangle rectangle) throws IOException, IllegalArgumentException, SecurityException, NullPointerException, SetupException
	{
	}

	public Image adaptImage( Image input, boolean dither) throws SetupException
	{
		return null;
	}
	
	private PlaneSetupPattern createPattern(){
		PlaneSetupPattern pattern = new PlaneSetupPattern();
		
		pattern.setPreference(PlaneSetupPattern.NO_BACKGROUND_IMPACT, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.NO_GRAPHICS_IMPACT, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.NO_SUBTITLE_IMPACT, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.NO_VIDEO_IMPACT, PlaneSetupPattern.REQUIRED_NOT);
		pattern.setPreference(PlaneSetupPattern.NO_STILLVIDEO_IMPACT, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.INTERLACED_DISPLAY, PlaneSetupPattern.DONT_CARE);
		pattern.setPreference(PlaneSetupPattern.FLICKER_FILTERING, PlaneSetupPattern.PREFERRED);
		pattern.setPreference(PlaneSetupPattern.VIDEO_GRAPHICS_PIXEL_ALIGNED, PlaneSetupPattern.DONT_CARE);
		pattern.setPreference(PlaneSetupPattern.PIXEL_ASPECT_RATIO, pixelAspectRatio, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.PIXEL_RESOLUTION, pixelResolution, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.CHANGEABLE_SINGLE_COLOR, PlaneSetupPattern.DONT_CARE);
		pattern.setPreference(PlaneSetupPattern.STILL_IMAGE, PlaneSetupPattern.DONT_CARE);
		pattern.setPreference(PlaneSetupPattern.VIDEO_MIXING, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.GRAPHICS_MIXING, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.MATTE_SUPPORT, PlaneSetupPattern.DONT_CARE);
		pattern.setPreference(PlaneSetupPattern.IMAGE_SCALING_SUPPORT, PlaneSetupPattern.REQUIRED);
		pattern.setPreference(PlaneSetupPattern.INDEXED_COLOR_SUPPORT, PlaneSetupPattern.PREFERRED);
		pattern.setPreference(PlaneSetupPattern.DIRECT_COLOR_SUPPORT, PlaneSetupPattern.REQUIRED);
		
		return pattern;
	}

}
