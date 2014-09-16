package br.org.sbtvd.ui;

import br.org.sbtvd.ui.ColorCoding;

import com.sun.dtv.ui.*;
import com.sun.dtv.lwuit.geom.*;

public class VideoCapabilities extends Capabilities {

	VideoCapabilities() {
		supportedScreenResolutions = new Dimension[1];
		supportedPlaneAspectRatios = new Dimension[1];
		supportedPixelAspectRatios = new Dimension[1];

		supportedScreenResolutions[0] = new Dimension(1920, 1080);
		supportedPlaneAspectRatios[0] = new Dimension(16, 9);
		supportedPixelAspectRatios[0] = new Dimension(1, 1);

		bitsPerPixel = -1;
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
		return false;
	}

	public boolean isRealAlphaBlendingSupported()
	{
		return false;
	}

	public boolean isImageRenderingSupported()
	{
		return false;
	}
	
	public boolean isJPEGRenderingSupported()
	{
		return false;
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
		return true;
	}

	public int getBitsPerPixel() throws SetupException
	{
		throw new SetupException();
	}

	public int getColorCodingModel() throws SetupException
	{
		throw new SetupException();
	}

	public boolean isWidgetRenderingSupported()
	{
		return false;
	}
	
	public boolean isGraphicsRenderingSupported() {
		return false;
	}

}
