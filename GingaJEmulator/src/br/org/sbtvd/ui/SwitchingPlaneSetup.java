package br.org.sbtvd.ui;

import java.awt.Color;

import java.io.IOException;

import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Point;
import com.sun.dtv.lwuit.geom.Rectangle;

import com.sun.dtv.ui.Plane;
import com.sun.dtv.ui.PlaneSetup;
import com.sun.dtv.ui.PlaneSetupPattern;
import com.sun.dtv.ui.SetupException;

public class SwitchingPlaneSetup extends PlaneSetup {

	public SwitchingPlaneSetup(Plane p) {
		planeAspectRatio = new Dimension(16, 9);
		pixelAspectRatio = new Dimension(1, 1);
		pixelResolution = new Dimension(1, 1);
		videoSource = null;
		videoController = null;
		pattern = createPattern();
		plane = p;
		color = Color.BLACK;
		
		screenResolution = new Dimension(1920, 1080);
		screenArea = new Rectangle(0, 0, screenResolution);
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
		pattern.setPreference(PlaneSetupPattern.NO_VIDEO_IMPACT, PlaneSetupPattern.REQUIRED);
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
