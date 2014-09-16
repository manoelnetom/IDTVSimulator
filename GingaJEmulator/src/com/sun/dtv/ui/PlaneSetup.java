package com.sun.dtv.ui;

import java.awt.Color;

import java.io.IOException;

import com.sun.dtv.lwuit.Graphics;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Point;
import com.sun.dtv.lwuit.geom.Rectangle;

public class PlaneSetup extends Object {
	protected Dimension screenResolution;
	protected Dimension planeAspectRatio;
	protected Dimension pixelAspectRatio;
	protected Dimension pixelResolution;
	protected Rectangle screenArea;
	protected Object videoSource;
	protected Object videoController;
	protected PlaneSetupPattern pattern;
	protected Plane plane;
	protected Color color;
	private boolean flickeringFilterAvailable = false;
	private boolean interlaced = false;
	private boolean videoMixing = false;
	private boolean graphicsMixing = false;
	private boolean matteSupported = false;
	private boolean imageScaling = false;

	/**
	 * Constructor
	 * 
	 */
	public PlaneSetup() {
	}

	/**
	 * This method has been created to avoid rounding errors appearing during
	 * conversion of pixel positions from one coordinate system towards another
	 * one when passing through normalized coordinates. As a direct conversion
	 * is not always possible, the return value may be null. Reasons for a
	 * failing conversion could be:
	 * <ul>
	 * <li>at least one of the two <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetups</CODE></A> is not pixel
	 * based</li>
	 * <li>one coordinate system is transformed into the other one using a
	 * non-linear function</li>
	 * <li>the transformation function is unknown for some reason</li>
	 * <li>the transformation function is not constant over the time</li>
	 * </ul>
	 * 
	 * The source position is interpreted in the coordinate system of the
	 * PlaneSetup object on which this method is called.
	 * 
	 * 
	 * @param destination
	 *            - the destination PlaneSetup.
	 * @param source
	 *            - the pixel position in this PlaneSetup.
	 * @return the position of the specified pixel position measured in the
	 *         destination coordinate system, or null if the conversion fails.
	 */
	public Point convertPoint(PlaneSetup destination, Point source) {
		Dimension d = destination.getScreenResolution();

		return new Point((source.getX() * d.getWidth())
				/ screenResolution.getWidth(), (source.getY() * d.getHeight())
				/ screenResolution.getHeight());
	}

	/**
	 * Return whether this setup includes filtering to reduce interlace flicker.
	 * 
	 * 
	 * 
	 * @return true if filtering is included, false otherwise.
	 */
	public boolean isFlickeringFilterAvailable() {
		return flickeringFilterAvailable;
	}

	/**
	 * Return whether this setup is interlaced.
	 * 
	 * 
	 * 
	 * @return true if this setup is interlaced, false otherwise.
	 */
	public boolean isInterlaced() {
		return interlaced;
	}

	/**
	 * Returns the offset between the origin of the pixel coordinate space of
	 * the specified <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>, and the
	 * origin of the current pixel coordinate space of this <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>. The offset
	 * is returned in the pixel coordinate space of the <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A> this method
	 * is called for.
	 * 
	 * 
	 * @param setup
	 *            - the target PlaneSetup
	 * @return the offset between the pixel coordinate space of the specified
	 *         PlaneSetup and the current pixel coordinate space of the origin
	 *         PlaneSetup. If the offset cannot be determined, null is returned.
	 */
	public Dimension getOffset(PlaneSetup setup) {
		Dimension origin = getPixelResolution(), local = setup
				.getPixelResolution();

		return new Dimension(origin.getWidth() - local.getWidth(),
				origin.getHeight() - local.getHeight());
	}

	/**
	 * Return the current screen resolution of this setup i.e. 960 x 540, 1920 x
	 * 1080, etc.
	 * 
	 * 
	 * 
	 * 
	 * @return a Dimension object specifying the screen resolution of the setup
	 */
	public Dimension getScreenResolution() {
		return this.screenResolution;
	}

	/**
	 * Return the aspect ratio of this setup as far as is known. i.e. 4:3, 16:9,
	 * etc.
	 * 
	 * This Dimension may be used to determine the pixel aspect ratio for given
	 * <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetups</CODE></A>.
	 * 
	 * 
	 * 
	 * @return a Dimension object specifying the aspect ratio of the setup
	 */
	public Dimension getPlaneAspectRatio() {
		return this.planeAspectRatio;
	}

	/**
	 * Return the pixel aspect ratio of this setup. Some examples are {16:15},
	 * {64:45}, {1:1}.
	 * 
	 * 
	 * 
	 * @return the aspect ratio of the pixels in this setup.
	 */
	public Dimension getPixelAspectRatio() {
		return this.pixelAspectRatio;
	}

	/**
	 * Return the resolution of this setup in pixels. The pixel coordinate
	 * system used is that of the plane associated with this setup.
	 * 
	 * 
	 * 
	 * @return the resolution of this setup in pixels.
	 */
	public Dimension getPixelResolution() {
		return this.pixelResolution;
	}

	/**
	 * Return the position and size of the plane associated with this setup on
	 * the <A HREF="../../../../com/sun/dtv/ui/Screen.html"
	 * title="class in com.sun.dtv.ui"><CODE>Screen</CODE></A> in screen
	 * coordinates.
	 * 
	 * 
	 * 
	 * @return the area on the Screen of this setup in screen coordinates.
	 */
	public Rectangle getScreenArea() {
		return this.screenArea;
	}

	/**
	 * Indicate whether the setup supports transparency in the graphics system
	 * such that the output of an underlying video decoder is visible. (This
	 * includes also video stills.) The return value can only be
	 * <code>true</code> if the return value of <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A>, called for
	 * the instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html"
	 * title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> associated
	 * with the plane, which is also currently associated with this setup, is
	 * also <code>true</code>. The following setups fulfill this requirement:
	 * <ul>
	 * <li>Setups with a well defined transformation between video / still video
	 * pixels and graphics pixels (e.g. pixels are the same size).</li>
	 * <li>Setups where an application displays graphics over video / still
	 * video, while the video / still video is used as a background. Therefore
	 * there is no transformation required between the two sets of pixels in
	 * such kind of setups.</li>
	 * </ul>
	 * 
	 * Applications may specify a particular video / still video setup with
	 * which mixing must be supported. In this case, the video / still video
	 * setup is specified as a video setup or still video setup, respectively.
	 * In cases where no specific video setup is required then it is not
	 * required to specify such a setup and null can be used instead.
	 * 
	 * 
	 * 
	 * @return true if video mixing is supported, false otherwise
	 */
	public boolean isVideoMixingSupported() {
		return videoMixing;
	}

	/**
	 * Indicates whether the plane setup supports the display of graphics in
	 * addition to video streams. This is true where the screen includes planes
	 * associated to both kinds of setups where the video pixels and graphics
	 * pixels are fully aligned (same size) as well as setups where they are
	 * displayed together but where a more complex relationship exists between
	 * the two pixel coordinate spaces. The return value can only be
	 * <code>true</code> if the return value of <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isVideoRenderingSupported()"
	 * ><CODE>Capabilities.isVideoRenderingSupported()</CODE></A>, called for
	 * the instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html"
	 * title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> associated
	 * with the plane, which is also currently associated with this setup, is
	 * also <code>true</code>.
	 * 
	 * 
	 * 
	 * @return true if graphics mixing is supported, false otherwise
	 */
	public boolean isGraphicsMixingSupported() {
		return graphicsMixing;
	}

	/**
	 * Indicate whether the mattes feature is supported by this setup. The
	 * return value can only be <code>true</code> if the return value of <A
	 * HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A>, called for
	 * the instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html"
	 * title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> associated
	 * with the plane, which is also currently associated with this setup, is
	 * also <code>true</code>.
	 * 
	 * 
	 * 
	 * @return true if mattes are supported, false otherwise
	 */
	public boolean isMatteSupported() {
		return matteSupported;
	}

	/**
	 * Indicate whether rapid image scaling is supported by this setup. The
	 * return value can only be <code>true</code> if the return value of <A
	 * HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A>, called for
	 * the instance of <A HREF="../../../../com/sun/dtv/ui/Capabilities.html"
	 * title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> associated
	 * with the plane, which is also currently associated with this setup, is
	 * also <code>true</code>.
	 * 
	 * 
	 * 
	 * @return true if rapid image scaling is supported, false otherwise
	 */
	public boolean isImageScalingSupported() {
		return imageScaling;
	}

	/**
	 * Indicate whether a Color Lookup Table is supported by this setup. The
	 * return value can only be <code>true</code> if the return value of at
	 * least one of the following methods, called for the instance of <A
	 * HREF="../../../../com/sun/dtv/ui/Capabilities.html"
	 * title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> associated
	 * with the plane, which is also currently associated with this setup, is
	 * also <code>true</code>:
	 * <ul>
	 * <li><A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A></li>
	 * <li><A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isWidgetRenderingSupported()"
	 * ><CODE>Capabilities.isWidgetRenderingSupported()</CODE></A></li>
	 * <li><A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isGraphicsRenderingSupported()"
	 * ><CODE>Capabilities.isGraphicsRenderingSupported()</CODE></A></li>
	 * </ul>
	 * .
	 * 
	 * 
	 * 
	 * @return true if the indexed color model is supported, false otherwise
	 */
	public boolean isIndexedColorModelSupported() {
		return false;
	}

	/**
	 * Indicate whether Direct Color model is supported by this setup. The
	 * return value can only be <code>true</code> if the return value of at
	 * least one of the following methods, called for the instance of <A
	 * HREF="../../../../com/sun/dtv/ui/Capabilities.html"
	 * title="class in com.sun.dtv.ui"><CODE>Capabilities</CODE></A> associated
	 * with the plane, which is also currently associated with this setup, is
	 * also <code>true</code>:
	 * <ul>
	 * <li><A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A></li>
	 * <li><A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isWidgetRenderingSupported()"
	 * ><CODE>Capabilities.isWidgetRenderingSupported()</CODE></A></li>
	 * <li><A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isGraphicsRenderingSupported()"
	 * ><CODE>Capabilities.isGraphicsRenderingSupported()</CODE></A></li>
	 * </ul>
	 * .
	 * 
	 * 
	 * 
	 * @return true if the direct color model is supported, false otherwise
	 */
	public boolean isDirectColorModelSupported() {
		return false;
	}

	/**
	 * Deliver the source of the video being presented by the plane associated
	 * with this PlaneSetup at this moment. The specification of the class to be
	 * returned is out of scope of this API. If no video is being presented,
	 * null is returned. The call to this method makes only sense if video
	 * rendering is supported by the plane associated with this setup, i.e. if
	 * the return value of <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isVideoRenderingSupported()"
	 * ><CODE>Capabilities.isVideoRenderingSupported()</CODE></A> is
	 * <code>true</code>. Otherwise an exception is thrown.
	 * 
	 * 
	 * 
	 * @return a reference to the source of the video
	 * @throws SecurityException
	 *             - if the application currently does not have sufficient
	 *             rights to get the VideoSource object
	 * @throws SetupException
	 *             - if the associated plane does not support video rendering
	 */
	public Object getVideoSource() throws SecurityException, SetupException {
		if(this.plane.getCapabilities().isVideoRenderingSupported())
		{
			return this.videoSource;
		}
		else
		{
			throw new SetupException();
		}
	}

	/**
	 * Deliver the presentation control object for the video currently shown by
	 * the plane associated with this setup. Null is returned if no video is
	 * being presented. A possible return object is e.g. a reference on a
	 * javax.media.Player instance which owns the resource. The call to this
	 * method makes only sense if video rendering is supported by the plane
	 * associated with this setup, i.e. if the return value of <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isVideoRenderingSupported()"
	 * ><CODE>Capabilities.isVideoRenderingSupported()</CODE></A> is
	 * <code>true</code>. Otherwise an exception is thrown.
	 * 
	 * 
	 * 
	 * @return the object which controls the presentation of the video
	 * @throws SecurityException
	 *             - if the application does not have sufficient rights to get
	 *             the VideoPlayer object.
	 * @throws SetupException
	 *             - if the associated plane does not support video rendering
	 */
	public Object getVideoController() throws SecurityException, SetupException {
		if(this.plane.getCapabilities().isVideoRenderingSupported())
		{
			return this.videoController;
		}
		else
		{
			throw new SetupException();
		}
	}

	/**
	 * Returns the <A HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetupPattern</CODE></A> object
	 * that describes and identifies this <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>. As a
	 * consequence, by programming
	 * 
	 * <pre>
	 * Plane.getBestSetup(PlaneSetup.getPattern())
	 * </pre>
	 * 
	 * one should get the original <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>
	 * 
	 * Features implemented in the <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A> will then be
	 * represented in the resulting pattern with <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED">
	 * <CODE>PlaneSetupPattern.REQUIRED</CODE></A> priority. Features not
	 * implemented there are represented with <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#REQUIRED_NOT">
	 * <CODE>PlaneSetupPattern.REQUIRED_NOT</CODE></A> priority. Preferences
	 * unset by the platform will appear with <A
	 * HREF="../../../../com/sun/dtv/ui/PlaneSetupPattern.html#DONT_CARE">
	 * <CODE>PlaneSetupPattern.DONT_CARE</CODE></A> priority.
	 * 
	 * 
	 * 
	 * @return an PlaneSetupPattern object which both describes and identifies
	 *         this PlaneSetup.
	 */
	public PlaneSetupPattern getPattern() {
		return this.pattern;
	}

	/**
	 * Returns the <A HREF="../../../../com/sun/dtv/ui/Plane.html"
	 * title="class in com.sun.dtv.ui"><CODE>Plane</CODE></A> associated with
	 * this <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>.
	 * 
	 * 
	 * 
	 * @return the Plane object that is associated with this PlaneSetup.
	 */
	public Plane getPlane() {
		return this.plane;
	}

	/**
	 * Provide the currently valid background color of this plane if any.
	 * Calling this method does not require the Plane to be reserved beforehand.
	 * The value returned is not guaranteed to be the one set by the last call
	 * to <A HREF=
	 * "../../../../com/sun/dtv/ui/PlaneSetup.html#setColor(java.awt.Color)">
	 * <CODE>setColor</CODE></A> since platforms may offer a different color
	 * space for backgrounds and the value actually used will be returned. If no
	 * background color is set for this setup, null is returned.
	 * 
	 * 
	 * 
	 * @return the current Color of this setup
	 * @see setColor(java.awt.Color)
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Set the valid color for the background of the plane associated with this
	 * setup to a new value. Passing a non-opaque color is illegal. If the
	 * available color resolution for backgrounds is not sufficient for the
	 * given value, the platform may approximate it to the nearest available.
	 * The <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html#getColor()">
	 * <CODE>getColor</CODE></A> method will then return the value actually in
	 * use.
	 * 
	 * 
	 * @param color
	 *            - the color to be used for the background
	 * @throws SecurityException
	 *             - if the application has not currently reserved the Plane
	 *             currently associated with this PlaneSetup or this PlaneSetup
	 *             is not the currently valid one of the reserved Plane.
	 * @throws SetupException
	 *             - if the specified color is illegal for this platform.
	 * @see getColor()
	 */
	public void setColor(Color color) throws SecurityException, SetupException {
		this.color = color;
	}

	/**
	 * Display a (background) image on the associated Plane. This method will
	 * block while the image data is loaded, if this hasn't been done already.
	 * Depending on the platform, the image
	 * <ul>
	 * <li>is scaled to fit or</li>
	 * <li>it is cropped (where too large) or</li>
	 * <li>it is repeated (where too small).</li>
	 * </ul>
	 * The position of the image is also platform-dependent. If the image is not
	 * scaled to fit, the previous color set using <A HREF=
	 * "../../../../com/sun/dtv/ui/PlaneSetup.html#setColor(java.awt.Color)">
	 * <CODE>setColor</CODE></A> may be visible in the remaining area of the
	 * Plane.
	 * 
	 * The image will be removed if the <A HREF=
	 * "../../../../com/sun/dtv/ui/PlaneSetup.html#setColor(java.awt.Color)">
	 * <CODE>setColor</CODE></A> method is called afterwards.
	 * 
	 * If a call to the <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A> returns
	 * <code>false</code> for the plane associated with this setup, a call to
	 * this method causes a SetupException to be thrown.
	 * 
	 * 
	 * @param image
	 *            - the image to be displayed.
	 * @throws IOException
	 *             - if the data for the com.sun.dtv.lwuit.Image cannot be not
	 *             loaded.
	 * @throws IllegalArgumentException
	 *             - if the com.sun.dtv.lwuit.Image does not contain an image in
	 *             a supported image encoding format
	 * @throws SecurityException
	 *             - if the Plane associated with this PlaneSetup has not been
	 *             reserved by the calling application
	 * @throws SetupException
	 *             - if the PlaneSetup is not the current setup for its Plane,
	 *             or if it is not appropriate for rendering images.
	 * @throws NullPointerException
	 *             - if the argument image is null
	 */
	public void displayImage(Image image) 
		throws IOException, IllegalArgumentException, SecurityException, NullPointerException, SetupException 
	{
		if (image == null) 
		{
			throw new NullPointerException("The argument image is null");
		}
		if(plane == null || plane.getCapabilities().isImageRenderingSupported() == false)
		{
			throw new SetupException();
		}
		else
		{
			Rectangle screen = this.getScreenArea();
			Dimension d = screen.getSize();
			Image i = image.scaled(d.getWidth(), d.getHeight());
			i.getGraphics().drawImage(i,screen.getX(),screen.getY());
		}
		
	}

	/**
	 * Display an (background) image to cover a particular area of the
	 * associated Plane. This method will block while the image data is loaded,
	 * if this hasn't been done already. Depending on the platform, the image
	 * <ul>
	 * <li>is scaled to fit or</li>
	 * <li>it is cropped (where too large) or</li>
	 * <li>it is repeated (where too small).</li>
	 * </ul>
	 * The position of the image within the rectangle is also platform-
	 * dependent. If the image is not scaled to fit into the rectangle, or the
	 * rectangle does not cover the entire Plane area, the previous color set
	 * using <A HREF=
	 * "../../../../com/sun/dtv/ui/PlaneSetup.html#setColor(java.awt.Color)">
	 * <CODE>setColor</CODE></A> may be visible in the remaining area of the
	 * Plane.
	 * 
	 * The image will be removed if the <A HREF=
	 * "../../../../com/sun/dtv/ui/PlaneSetup.html#setColor(java.awt.Color)">
	 * <CODE>setColor</CODE></A> method is called afterwards.
	 * 
	 * If a call to the <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A> returns
	 * <code>false</code> for the plane associated with this setup, a call to
	 * this method causes a SetupException to be thrown.
	 * 
	 * 
	 * @param image
	 *            - the image to be displayed
	 * @param rectangle
	 *            - the area of the Plane the image should be shown in
	 * @throws IOException
	 *             - if the data for the com.sun.dtv.lwuit.Image cannot be not
	 *             loaded.
	 * @throws IllegalArgumentException
	 *             - if the com.sun.dtv.lwuit.Image does not contain an image in
	 *             a supported image encoding format
	 * @throws SecurityException
	 *             - if the Plane associated with this PlaneSetup has not been
	 *             reserved by the calling application
	 * @throws SetupException
	 *             - if the PlaneSetup is not the current setup for its Plane,
	 *             or if it is not appropriate for rendering images..
	 * @throws NullPointerException
	 *             - if either or both of the arguments are null
	 */
	public void displayImage(Image image, Rectangle rectangle) 
		throws IOException, IllegalArgumentException, SecurityException, NullPointerException, SetupException 
	{
	}

	int find_closest_palette_color(int color) {
		int r = (color >> 16) & 0xff, g = (color >> 8) & 0xff, b = (color >> 0) & 0xff;

		if (r > 128) {
			r = 255;
		} else {
			r = 0;
		}

		if (g > 128) {
			g = 255;
		} else {
			g = 0;
		}

		if (b > 128) {
			b = 255;
		} else {
			b = 0;
		}

		return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
	}

	/**
	 * Adapts a given image if necessary in order to gain compatibility with the
	 * currently valid <A HREF="../../../../com/sun/dtv/ui/PlaneSetup.html"
	 * title="class in com.sun.dtv.ui"><CODE>PlaneSetup</CODE></A>. If the type
	 * of the input image allows it, this may involve dithering to a restricted
	 * color palette. If no adaptation is necessary, the original image will be
	 * returned If a call to the <A HREF=
	 * "../../../../com/sun/dtv/ui/Capabilities.html#isImageRenderingSupported()"
	 * ><CODE>Capabilities.isImageRenderingSupported()</CODE></A> returns
	 * <code>false</code> for the plane associated with this setup, a call to
	 * this method causes a SetupException to be thrown.
	 * 
	 * 
	 * @param input
	 *            - the image to be modified if necessary
	 * @param dither
	 *            - a boolean that indicates whether or not the type of the
	 *            input image allows dithering
	 * @return an image adapted in a way enabling optimal presentation on the
	 *         Plane associated with this PlaneSetup, or the original image if
	 *         no adaptation has been necessary.
	 * @throws SetupException
	 *             - if the PlaneSetup is not appropriate for rendering images.
	 */
	public Image adaptImage(Image input, boolean dither) throws SetupException {
		if (plane.getCapabilities().isImageRenderingSupported() == false) {
			throw new SetupException();
		}

		return input;
	}
	
}
