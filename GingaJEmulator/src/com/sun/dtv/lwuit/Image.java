package com.sun.dtv.lwuit;

import java.awt.Color;

import java.io.IOException;
import java.io.InputStream;

import java.awt.Toolkit;

import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;

public class Image extends Object {

    private java.awt.Image awtImage;
    private com.sun.dtv.lwuit.Graphics graphics;

    /**
     * Creates a new instance of <A HREF="../../../../com/sun/dtv/lwuit/Image.html" title="class in com.sun.dtv.lwuit"><CODE>Image</CODE></A> out of the
     * <code>java.awt.Image</code> to be wrapped.
     *
     * 
     * @param image - the java.awt.Image to be wrapped
     */
    public Image(java.awt.Image image) {
        awtImage = image;

        try {
            graphics = new com.sun.dtv.lwuit.Graphics((java.awt.Graphics2D) image.getGraphics());
        } catch (java.lang.IllegalAccessError e) {
            graphics = null;
        } catch (java.lang.UnsupportedOperationException e) {
            graphics = null;
        } catch (Exception e) {
            graphics = null;
        }
    }

    public void flush() {
        awtImage.flush();
    }

    /**
     * If this is a mutable image a graphics object allowing us to draw on it
     * is returned.
     *
     * 
     * 
     * @return Graphics object allowing us to manipulate the content of a mutable image
     */
    public com.sun.dtv.lwuit.Graphics getGraphics() {
        return this.graphics;
    }

    public Object getProperty(String name, ImageObserver observer) {
        return awtImage.getProperty(name, observer);
    }

    public ImageProducer getSource() {
        return awtImage.getSource();
    }

    public int getWidth(ImageObserver observer) {
        return awtImage.getWidth(observer);
    }

    public int getHeight(ImageObserver observer) {
        return awtImage.getHeight(observer);
    }

    /**
     * Extracts a subimage from the given image allowing us to breakdown a single large image
     * into multiple smaller images in RAM, this actually creates a standalone version
     * of the image for use.
     *
     * 
     * @param x - x coordinate of upper left corner
     * @param y - y coordinate of upper left corner
     * @param width - the width of internal images
     * @param height - the height of internal images
     * @param processAlpha - alphaComposite value
     * @return An array of all the possible images that can be created from the source
     * @throws IOException
     */
    public Image subImage(int x, int y, int width, int height, boolean processAlpha) {
        // we use the getRGB API rather than the mutable image API to allow translucency to
        // be maintained in the newly created image
        int[] arr = new int[width * height];

        getRGB(arr, 0, x, y, width, height);

        com.sun.dtv.lwuit.Image i = new com.sun.dtv.lwuit.Image(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, arr, 0, width)));

        if (!Display.getInstance().isInitialized()) {
            Display.getInstance().init();
        }

        java.awt.MediaTracker tracker = new java.awt.MediaTracker(Display.getInstance().getImplementation().getFrame());
        try {
            tracker.addImage(i.getAWTImage(), 0);
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return i;
    }

    /**
     * Obtains ARGB pixel data from the specified region of this image and 
     * stores it in the provided array of integers. Each pixel value is 
     * stored in 0xAARRGGBB format, where the high-order byte contains the 
     * alpha channel and the remaining bytes contain color components for red, 
     * green and blue, respectively. The alpha channel specifies the opacity of 
     * the pixel, where a value of 0x00  represents a pixel that is fully 
     * transparent and a value of 0xFF  represents a fully opaque pixel. 
     * The rgb information contained within the image, this method ignors 
     * rotation and mirroring in some/most situations and cannot be 
     * used in such cases.
     * 
     * @param rgbData an array of integers in which the ARGB pixel data is 
     * stored
     * @param offset the index into the array where the first ARGB value is 
     * stored
     * @param scanlength the relative offset in the array between 
     * corresponding pixels in consecutive rows of the region
     * @param x the x-coordinate of the upper left corner of the region
     * @param y the y-coordinate of the upper left corner of the region
     * @param width the width of the region
     * @param height the height of the region
     */
    void getRGB(int[] rgbData, int offset, int x, int y, int width, int height) {
        if (rgbData.length < width * height) {
            return;
        }

        PixelGrabber pg = new PixelGrabber(awtImage, x, y, width, height, false);

        try {
            pg.grabPixels();

            int src[] = (int[]) pg.getPixels();

            System.arraycopy(src, 0, rgbData, 0, src.length);
        } catch (InterruptedException e) {
        }
    }

    private int round(double d) {
        double f = Math.floor(d);
        double c = Math.ceil(d);

        if (c - d < d - f) {
            return (int) c;
        }

        return (int) f;
    }

    /**
     * Returns an instance of this image rotated by the given number of degrees. By default 90 degree
     * angle divisions are supported, anything else is implementation dependent. This method assumes
     * a square image. Notice that it is inefficient in the current implementation to rotate to
     * non-square angles,
     *E.g. rotating an image to 45, 90 and 135 degrees is inefficient. Use rotate to 45, 90
     * and then rotate the 45 to another 90 degrees to achieve the same effect with less memory.
     *
     * 
     * @param degrees - A degree in right angle must be larger than 0 and up to 359 degrees
     * @return new image instance with the closest possible rotation
     */
    public Image rotate(int degrees) {
        int width = getWidth();
        int height = getHeight();
        int[] arr = new int[width * height];
        int[] dest = new int[arr.length];
        int centerX = width / 2;
        int centerY = height / 2;
        double radians = Math.toRadians(-degrees);
        double cosDeg = Math.cos(radians);
        double sinDeg = Math.sin(radians);

        getRGB(arr, 0, 0, 0, width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int x2 = round(cosDeg * (x - centerX) - sinDeg * (y - centerY) + centerX);
                int y2 = round(sinDeg * (x - centerX) + cosDeg * (y - centerY) + centerY);
                if (!(x2 < 0 || y2 < 0 || x2 >= width || y2 >= height)) {
                    int destOffset = x2 + y2 * width;
                    if (destOffset >= 0 && destOffset < dest.length) {
                        dest[x + y * width] = arr[destOffset];
                    }
                }
            }
        }

        return createImage(dest, width, height);
    }

    /**
     * Creates a new image instance with the alpha channel of opaque/translucent
     * pixels within the image using the new alpha value. Transparent (alpha == 0)
     * pixels remain transparent. All other pixels will have the new alpha value.
     *
     * 
     * @param alpha - New value for the entire alpha channel
     * @return Translucent/Opaque image based on the alpha value and the pixels of this image
     */
    public Image modifyAlpha(byte alpha) {
        int width = getWidth();
        int height = getHeight();
        int size = width * height;
        int[] arr = new int[size];

        getRGB(arr, 0, 0, 0, width, height);

        int alphaInt = (((int) alpha) << 24) & 0xff000000;

        for (int iter = 0; iter < size; iter++) {
            if ((arr[iter] & 0xff000000) != 0) {
                arr[iter] = (arr[iter] & 0xffffff) | alphaInt;
            }
        }

        return createImage(arr, width, height);
    }

    /**
     * Creates a new image instance with the alpha channel of opaque/translucent
     * pixels within the image using the new alpha value. Transparent (alpha == 0)
     * pixels remain transparent. All other pixels will have the new alpha value.
     *
     * 
     * @param alpha - New value for the entire alpha channel
     * @param removeColor - pixels matching this color are made transparent (alpha channel ignored)
     * @return Translucent/Opaque image based on the alpha value and the pixels of this image
     */
    public Image modifyAlpha(byte alpha, Color removeColor) {
        int width = getWidth();
        int height = getHeight();
        int size = width * height;
        int[] arr = new int[size];

        getRGB(arr, 0, 0, 0, width, height);

        int alphaInt = (((int) alpha) << 24) & 0xff000000;
        int remove = (removeColor.getRed() << 16) | (removeColor.getGreen() << 8) | (removeColor.getBlue() << 0);

        for (int iter = 0; iter < size; iter++) {
            if ((arr[iter] & 0xff000000) != 0) {
                arr[iter] = (arr[iter] & 0xffffff) | alphaInt;

                if (remove == (0xffffff & arr[iter])) {
                    arr[iter] = 0x00000000;
                }
            }
        }

        return createImage(arr, width, height);
    }

    /**
     * Creates an image from the given path.
     *
     * 
     * @param path - path where to find image information
     * @return newly created image object
     * @throws IOException
     */
    public static Image createImage(String path) throws IOException {
        java.awt.Image img = Toolkit.getDefaultToolkit().createImage(xjava.io.FileInputStream.getBaseDirectory(new File(path)));

        if (!Display.getInstance().isInitialized()) {
            Display.getInstance().init();
        }

        java.awt.MediaTracker tracker = new java.awt.MediaTracker(Display.getInstance().getImplementation().getFrame());

        try {
            tracker.addImage(img, 0);
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return createImage(img);
    }

    /**
     * Creates an image from an InputStream.
     *
     * 
     * @param stream - a given InputStream
     * @return an Image object
     * @throws IOException
     */
    public static Image createImage(InputStream stream) throws IOException {
        if (stream == null || stream.available() <= 0) {
            return null;
        }

        try {
            byte[] imageArray = new byte[stream.available()];
            int off = 0;
            int size = 1024;

            while ((size = stream.read(imageArray, off, size)) > 0) {
                off = off + size;
            }

            if (!Display.getInstance().isInitialized()) {
                Display.getInstance().init();
            }

            java.awt.MediaTracker tracker = new java.awt.MediaTracker(Display.getInstance().getImplementation().getFrame());
            Image img = createImage(java.awt.Toolkit.getDefaultToolkit().createImage(imageArray, 0, off));
            try {
                tracker.addImage(img.getAWTImage(), 0);
                tracker.waitForAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return img;
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }

        return null;
    }

    /**
     * Creates an image from an RGB image.
     *
     * 
     * @param rgb - the RGB image array data
     * @param width - the image width
     * @param height - the image height
     * @return an image from an RGB image
     */
    public static Image createImage(int[] rgb, int width, int height) {
        if (!Display.getInstance().isInitialized()) {
            Display.getInstance().init();
        }

        java.awt.MediaTracker tracker = new java.awt.MediaTracker(Display.getInstance().getImplementation().getFrame());
        Image img = createImage(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, rgb, 0, width)));
        try {
            tracker.addImage(img.getAWTImage(), 0);
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return img;
    }

    /**
     * Creates a buffered Image.
     *
     * 
     * @param width - the image width
     * @param height - the image height
     * @return an image in a given width and height dimension
     */
    public static Image createImage(int width, int height) {
        java.awt.Image img = Display.getInstance().getImplementation().getFrame().createImage(width, height);

        if (!Display.getInstance().isInitialized()) {
            Display.getInstance().init();
        }

        java.awt.MediaTracker tracker = new java.awt.MediaTracker(Display.getInstance().getImplementation().getFrame());

        try {
            tracker.addImage(img, 0);
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return createImage(img);
    }

    /**
     * Creates an image from a given byte array data.
     *
     * 
     * @param bytes - the array of image data in a supported image format
     * @param offset - the offset of the start of the data in the array
     * @param len - the length of the data in the array
     * @return an Image object
     */
    public static Image createImage(byte[] bytes, int offset, int length) {
        java.awt.Image awtImage = Toolkit.getDefaultToolkit().createImage(bytes, offset, length);

        if (!Display.getInstance().isInitialized()) {
            Display.getInstance().init();
        }

        java.awt.MediaTracker tracker = new java.awt.MediaTracker(Display.getInstance().getImplementation().getFrame());

        try {
            tracker.addImage(awtImage, 0);
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Image(awtImage);
    }

    /**
     * Creates an image from the <code>java.awt.Image</code> to be wrapped.
     *
     * 
     * @param image - the java.awt.Image to be wrapped
     * @return the created instance of Image
     */
    public static Image createImage(java.awt.Image image) {
        return new com.sun.dtv.lwuit.Image(image);
    }

    /**
     * Returns the width of the image.
     *
     * 
     * 
     * @return the width of the image
     */
    public int getWidth() {
        return awtImage.getWidth(null);
    }

    /**
     * Returns the height of the image.
     *
     * 
     * 
     * @return the height of the image
     */
    public int getHeight() {
        return awtImage.getHeight(null);
    }

    /**
     * Returns the content of this image as a newly created ARGB array.
     *
     * 
     * 
     * @return new array instance containing the ARGB data within this image
     */
    public int[] getRGB() {
        int size = getWidth() * getHeight();

        if (size <= 0) {
            return null;
        }

        int[] rgbArray = new int[size];

        getRGB(rgbArray, 0, 0, 0, getWidth(), getHeight());

        return rgbArray;
    }

    /**
     * Scales the image to the given width while updating the height based on the
     * aspect ratio of the width.
     *
     * 
     * @param width - the given new image width
     * @return an Image object
     */
    public Image scaledWidth(int width) {
        float ratio = ((float) width) / ((float) getWidth());

        return scaled(width, (int) (getHeight() * ratio));
    }

    /**
     * Scales the image to the given height while updating the width based on the
     * aspect ratio of the height.
     *
     * 
     * @param height - the given new image height
     * @return an Image object
     */
    public Image scaledHeight(int height) {
        float ratio = ((float) height) / ((float) getHeight());

        return scaled((int) (getWidth() * ratio), height);
    }

    /**
     * Scales the image while maintaining the aspect ratio to the smaller size
     * image.
     *
     * 
     * @param width - the given new image width
     * @param height - the given new image height
     * @return an Image object
     */
    public Image scaledSmallerRatio(int width, int height) {
        float hRatio = ((float) height) / ((float) getHeight());
        float wRatio = ((float) width) / ((float) getWidth());

        if (hRatio < wRatio) {
            return scaled(width, (int) (getHeight() * hRatio));
        } else {
            return scaled((int) (getWidth() * wRatio), height);
        }
    }

    /**
     * Returns a scaled version of this image using the given width and height,
     * this is a fast algorithm that preserves translucent information.
     *
     * 
     * @param width - width for the scaling
     * @param height - height of the scaled image
     * @return new image instance scaled to the given height and width
     */
    public Image scaled(int width, int height) {
        if ((width <= 0 && height <= 0) || (width == getWidth() && height == getHeight())) {
            return this;
        }

        if (width == -1) {
            return scaledHeight(height);
        }

        if (height == -1) {
            return scaledWidth(width);
        }

        Image i = Image.createImage(width, height);

        i.getGraphics().drawImage(this, 0, 0, width, height, null);

        return i;
    }

    /**
     * Obtains the <code>java.awt.Image</code> wrapped by this <A HREF="../../../../com/sun/dtv/lwuit/Image.html" title="class in com.sun.dtv.lwuit"><CODE>Image</CODE></A>.
     *
     * 
     * 
     * @return the wrapped java.awt.Image
     */
    public java.awt.Image getAWTImage() {
        return (java.awt.Image) awtImage;
    }

    /**
     * Returns true if this is an animated image.
     *
     * 
     * 
     * @return true if image is animation
     */
    public boolean isAnimation() {
        return false;
    }

    /**
     * Indicates whether this image is opaque or not.
     *
     * 
     * 
     * @return true if the image is completely opaque which allows for some heavy optimizations
     */
    public boolean isOpaque() {
        int[] rgb = getRGB();
        for (int iter = 0; iter < rgb.length; iter++) {
            if ((rgb[iter] & 0xff000000) != 0xff000000) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a mask from the given image, a mask can be used to apply an arbitrary
     * alpha channel to any image. A mask is derived from the blue channel (LSB) of
     * the given image.
     * The generated mask can be used with the apply mask method.
     * 
     * @return mask object that can be used with applyMask
     */
    public Object createMask() {
        int[] rgb = getRGB();
        byte[] mask = new byte[rgb.length];
        for (int iter = 0; iter < rgb.length; iter++) {
            mask[iter] = (byte) (rgb[iter] & 0xff);
        }
        return new IndexedImage(getWidth(), getHeight(), null, mask);
    }

    /**
     * Applies the given alpha mask onto this image and returns the resulting image
     * see the createMask method for indication on how to convert an image into an alpha
     * mask.
     * 
     * @param mask mask object created by the createMask() method.
     * @param x starting x where to apply the mask
     * @param y starting y where to apply the mask
     * @return image masked based on the given object
     */
    public Image applyMask(Object mask, int x, int y) {
        int[] rgb = getRGB();
        byte[] maskData = ((IndexedImage) mask).getImageDataByte();
        int mWidth = ((IndexedImage) mask).getWidth();
        int mHeight = ((IndexedImage) mask).getHeight();
        int imgWidth = getWidth();
        int aWidth = imgWidth - x;
        int aHeight = getHeight() - y;
        if (aWidth > mWidth) {
            aWidth = mWidth;
        }
        if (aHeight > mHeight) {
            aHeight = mHeight;
        }

        for (int xPos = 0; xPos < aWidth; xPos++) {
            for (int yPos = 0; yPos < aHeight; yPos++) {
                int aX = x + xPos;
                int aY = y + yPos;
                int imagePos = aX + aY * imgWidth;
                int maskAlpha = maskData[aX + aY * mWidth] & 0xff;
                maskAlpha = (maskAlpha << 24) & 0xff000000;
                rgb[imagePos] = (rgb[imagePos] & 0xffffff) | maskAlpha;

            }
        }
        return createImage(rgb, imgWidth, getHeight());
    }

    /**
     * Applies the given alpha mask onto this image and returns the resulting image
     * see the createMask method for indication on how to convert an image into an alpha
     * mask.
     *
     * @param mask mask object created by the createMask() method.
     * @return image masked based on the given object
     * @throws IllegalArgumentException if the image size doesn't match the mask size
     */
    public Image applyMask(Object mask) {
        int[] rgb = getRGB();
        byte[] maskData = ((IndexedImage) mask).getImageDataByte();
        int mWidth = ((IndexedImage) mask).getWidth();
        int mHeight = ((IndexedImage) mask).getHeight();
        if (mWidth != getWidth() || mHeight != getHeight()) {
            throw new IllegalArgumentException("Mask and image sizes don't match");
        }
        for (int iter = 0; iter < maskData.length; iter++) {
            int maskAlpha = maskData[iter] & 0xff;
            maskAlpha = (maskAlpha << 24) & 0xff000000;
            rgb[iter] = (rgb[iter] & 0xffffff) | maskAlpha;
        }
        return createImage(rgb, mWidth, mHeight);
    }
}
