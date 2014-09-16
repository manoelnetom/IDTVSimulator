package br.org.sbtvd.ui;

import br.org.sbtvd.ui.ColorCoding;

import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.ui.Capabilities;
import com.sun.dtv.ui.SetupException;

public class StillCapabilities extends Capabilities {

	StillCapabilities() {
		supportedScreenResolutions = new Dimension[1];
		supportedPlaneAspectRatios = new Dimension[1];
		supportedPixelAspectRatios = new Dimension[1];

		supportedScreenResolutions[0] = new Dimension(1920, 1080);
		supportedPlaneAspectRatios[0] = new Dimension(16, 9);
		supportedPixelAspectRatios[0] = new Dimension(1, 1);

		bitsPerPixel = 16;
		colorCodingModel = ColorCoding.ARGB8888;
	}

	public Dimension[] getSupportedScreenResolutions()
	{
		return this.supportedScreenResolutions;
	}

	public Dimension[] getSupportedPlaneAspectRatios()
	{
		return this.supportedPlaneAspectRatios;
	}

	public Dimension[] getSupportedPixelAspectRatios()
	{
		return this.supportedPixelAspectRatios;
	}

	public boolean isAlphaBlendingSupported()
	{
		return true;
	}

	public boolean isRealAlphaBlendingSupported()
	{
		return false;
	}

	public boolean isImageRenderingSupported()
	{
		return true;
	}
	
	public boolean isJPEGRenderingSupported()
	{
		return true;
	}

	public boolean isPNGRenderingSupported()
	{
		return false;
	}

	public boolean isGIFRenderingSupported()
	{
		return false;
	}

	public boolean isVideoRenderingSupported()
	{
		return false;
	}

	public int getBitsPerPixel() throws SetupException
	{
		return this.bitsPerPixel;
	}

	public int getColorCodingModel() throws SetupException
	{
		return this.colorCodingModel;
	}

	public boolean isWidgetRenderingSupported()
	{
		return true;
	}
	
	public boolean isGraphicsRenderingSupported() {
		return false;
	}
	
}
