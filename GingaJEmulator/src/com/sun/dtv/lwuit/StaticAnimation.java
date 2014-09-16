package com.sun.dtv.lwuit;

import com.sun.dtv.lwuit.animations.Animation;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.geom.Rectangle;

import com.sun.dtv.ui.Animated;

import java.io.DataInputStream;

import java.io.IOException;

import java.util.TimerTask;
import java.util.Vector;

public class StaticAnimation extends IndexedImage implements Animation,
		Animated {
	private Frame[] frames;

	/**
	 * The current frame doesn't point directly at the frames array since the
	 * first frame is the image itself
	 * 
	 */
	private int currentFrame;

	private long animationStartTime;
	private int totalAnimationTime;
	private boolean loop;
	private int repetitionMode = Animated.LOOP;
	private int animationMode = Animated.REPEATING;
	private int delay;
	private int position = 0;
	private boolean running = false;

	/**
	 * Invoked with the first frame value
	 */
	StaticAnimation(int width, int height, int[] palette, byte[] data) {
		super(width, height, palette, data);
	}

	/**
	 * Creates an animation from the given stream, this method is used
	 * internally by Resources normally you should not create static animations
	 * manually.
	 * 
	 * 
	 * @param data
	 *            - input stream from which the animation is loaded
	 * @return An instance of a static animation
	 * @throws IOException
	 *             - if stream cannot be opened
	 */
	public static StaticAnimation createAnimation(DataInputStream data) 	throws IOException {
		// read the length of the palette;
		byte pSize = data.readByte();
		int[] palette = new int[pSize & 0xff];
		for (int iter = 0; iter < palette.length; iter++) {
			palette[iter] = data.readInt();
		}
		int width = data.readShort();
		int height = data.readShort();
		int numberOfFrames = data.readByte() & 0xff;
		int totalAnimationTime = Math.max(1, data.readInt());
		boolean loop = data.readBoolean();
		byte[] array = new byte[width * height];
		data.readFully(array);

		// create the first frame of the animation
		StaticAnimation animation = new StaticAnimation(width, height, palette,
				array);
		animation.frames = new Frame[numberOfFrames - 1];
		animation.totalAnimationTime = totalAnimationTime;
		animation.loop = loop;
		int currentTime = 0;
		byte[] lastKeyframe = array;
		Vector rowNumbers = new Vector();
		Vector rowValues = new Vector();

		// read the rest of the frames in the animation
		for (int iter = 1; iter < numberOfFrames; iter++) {
			currentTime += data.readInt();
			// if this is a keyframe then just read the data else read the
			// specific
			// modified row offsets
			if (data.readBoolean()) {
				array = new byte[width * height];
				data.readFully(array);
				animation.frames[iter - 1] = new Frame(currentTime, array);
				lastKeyframe = array;
				rowValues.removeAllElements();
				rowNumbers.removeAllElements();
			} else {
				boolean drawPrevious = data.readBoolean();
				int nextRow = data.readShort();

				while (nextRow != -1) {
					byte[] rowData = new byte[width];
					data.readFully(rowData);

					// if this row was already modified in a previous frame we
					// can remove
					// the old row
					Integer rowInteger = new Integer(nextRow);
					int index = rowNumbers.indexOf(rowInteger);
					if (index > -1) {
						rowNumbers.removeElementAt(index);
						rowValues.removeElementAt(index);
					}

					rowNumbers.addElement(rowInteger);
					rowValues.addElement(rowData);
					nextRow = data.readShort();
				}
				animation.frames[iter - 1] = new Frame(currentTime,
						lastKeyframe, rowNumbers, rowValues, drawPrevious);
			}
		}

		return animation;
	}
	
	/**
	 * Overriden to allow animation frames
	 * 
	 */
	void drawImage(Graphics g, int x, int y) {
		if (currentFrame == 0) {
			//super.drawImage(g, x, y);
			return;
		}

		int width = getWidth();
		int lastLine = getHeight();
		int clipY = g.getClipY();
		int clipBottomY = g.getClipHeight() + clipY;
		int firstLine = 0;

		if (clipY > y) {
			firstLine = clipY - y;
		}
		if (clipBottomY < y + getHeight()) {
			lastLine = clipBottomY - y;
		}

		Frame f = frames[currentFrame - 1];
		byte[] imageDataByte = f.getKeyFrame();
		int[] palette = getPalette();
		int rgb[] = new int[width * (lastLine - firstLine)];

		// we draw based on the last keyframe and if a row was modified then we
		// will draw that row rather than the keyframe
		for (int line = firstLine; line < lastLine; line += 1) {
			byte[] lineArray = f.getModifiedRow(line);

			if (lineArray != null) {
				for (int position = 0; position < width; position++) {
					int i = lineArray[position] & 0xff;

					rgb[(line - firstLine) * width + position] = palette[i];
				}
			} else {
				// if(!f.isDrawPrevious()) {
				int currentPos = line * width;
				for (int position = 0; position < width; position++) {
					int i = imageDataByte[position + currentPos] & 0xff;
					rgb[(line - firstLine) * width + position] = palette[i];
				}
				// }
			}
		}

		Image image = Image.createImage(rgb, width, lastLine - firstLine);

		g.drawImage(image, x, y);
	}

	/**
	 * Returns the number of frames in the animation including the initial
	 * frame.
	 * 
	 * 
	 * 
	 * @return the frame count
	 */
	public int getFrameCount() {
		return frames.length + 1;
	}

	/**
	 * The time in which the given frame should appear.
	 * 
	 * 
	 * @param frame
	 *            - must be a number bigger than -1 and smaller than
	 *            getFrameCount()
	 * @return the time in milliseconds for the frame to appear
	 */
	public int getFrameTime(int frame) {
		if (frame == 0) {
			return 0;
		}
		return frames[frame - 1].time;
	}

	/**
	 * Returns the duration for the entire animation used to determine when to
	 * loop.
	 * 
	 * 
	 * 
	 * @return total animation time in milliseconds
	 */
	public int getTotalAnimationTime() {
		return totalAnimationTime;
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/animations/Animation.html#animate()">Animation</A></CODE>
	 * </B></DD> Allows the animation to reduce "repaint" calls when it returns
	 * false. It is called once for every frame.
	 * 
	 * 
	 * @return true if a repaint is desired or false if no repaint is necessary
	 * @see animate in interface Animation
	 */
	public boolean animate() {
		if (!isRunning()) 
		{
			return false;
		}
		
		long currentTime = System.currentTimeMillis();
		int position = (int) (currentTime - animationStartTime);
		if (loop) {
			position %= totalAnimationTime;
		} else {
			if (position > totalAnimationTime) {
				return false;
			}
		}

		// special case for last frame
		if (currentFrame == frames.length) 
		{
			if (position >= totalAnimationTime || position < frames[frames.length - 1].getTime()) 
			{
				currentFrame = 0;
				return true;
			}
		} 
		else {
			if (position >= frames[currentFrame].getTime()) {
				currentFrame++;
				if (currentFrame > frames.length + 1) {
					currentFrame = 0;
				}
				return true;
			}
		}

		return false;
	}

	/**
	 * Restarts the animation.
	 * 
	 * 
	 */
	public void restart() {
		animationStartTime = System.currentTimeMillis();
	}

	/**
	 * <B>Description copied from interface:
	 * <CODE><A HREF="../../../../com/sun/dtv/lwuit/animations/Animation.html#paint(com.sun.dtv.lwuit.Graphics)">Animation</A></CODE>
	 * </B></DD> Draws the animation, within a component the standard paint
	 * method would be invoked since it bares the exact same signature.
	 * 
	 * 
	 * @see paint in interface Animation
	 */
	public void paint(Graphics g) {
		drawImage(g, 0, 0);
	}

	/**
	 * Indicates whether the animation will run in a loop or run only once.
	 * 
	 * 
	 * 
	 * @return true if animation runs in loop, false otherwise
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * Indicates whether the animation will run in a loop or run only once.
	 * 
	 * 
	 * @param loop
	 *            - indicates whether animation should run in loop or not
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * Returns a scaled version of this image using the given width and height,
	 * this is a fast algorithm that preserves translucent information.
	 * 
	 * 
	 * @param width
	 *            - width for the scaling
	 * @param height
	 *            - height of the scaled image
	 * @return new image instance scaled to the given height and width
	 * @see scaled in class Image
	 */
	public Image scaled(int width, int height) {
		int srcWidth = getWidth();
		int srcHeight = getHeight();

		// no need to scale
		if (srcWidth == width && srcHeight == height) {
			return this;
		}

		// save the previous keyframe all the time this allows us to swap a
		// keyframe
		// based on its pointer rather than scale it twice
		byte[] lastKeyFrame = getImageDataByte();

		// scale the first frame
		StaticAnimation result = new StaticAnimation(width, height,
				getPalette(), scaleArray(lastKeyFrame, width, height));
		result.loop = loop;
		result.totalAnimationTime = totalAnimationTime;
		result.animationStartTime = animationStartTime;
		result.currentFrame = currentFrame;
		result.frames = new Frame[frames.length];

		byte[] lastKeyFrameAfterScale = result.getImageDataByte();

		int yRatio = (srcHeight << 16) / height;
		int xRatio = (srcWidth << 16) / width;

		// now we need to traverse all the frames and scale them one by one
		for (int iter = 0; iter < frames.length; iter++) {
			byte[] currentKeyFrame = frames[iter].getKeyFrame();
			if (currentKeyFrame != lastKeyFrame) {
				lastKeyFrame = currentKeyFrame;
				currentKeyFrame = scaleArray(currentKeyFrame, width, height);
				lastKeyFrameAfterScale = currentKeyFrame;
			}
			result.frames[iter] = new Frame(frames[iter].getTime(),
					lastKeyFrameAfterScale, frames[iter], xRatio, yRatio,
					width, height);
		}
		return result;
	}

	/**
	 * Returns true if this is an animated image.
	 * 
	 * 
	 * @return true if image is animation
	 * @see isAnimation in class Image
	 */
	public boolean isAnimation() {
		return true;
	}

	/**
	 * Starts an animation. Please consider that an animation is in the stopped
	 * mode per default, so it has to be started explicitly once it has been
	 * created. In case the animation is already running, a call to
	 * <code>start()</code> causes a restart of the animation.
	 * 
	 * 
	 * @see start in interface Animated
	 */
	public void start() {
		animationStartTime = System.currentTimeMillis();
		running = true;
		
	}

	/**
	 * Stops an animation. In case the animation is already stopped, the call to
	 * <code>stop()</code> has no effect.
	 * 
	 * 
	 * @see stop in interface Animated
	 */
	public void stop() 
	{
		animationStartTime = 0;
		running = false;
	}
	
	/**
	 * Obtains the running mode of an animation. Returns true if the animation
	 * is running, false otherwise.
	 * 
	 * 
	 * @return true if the animation is running, false otherwise
	 * @see isRunning in interface Animated
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Forces an animation to jump to the position indicated by the parameter.
	 * An animation can be considered as a row of images, the parameter of this
	 * method provides the index within this row. If the animation is stopped, a
	 * call to this method causes the animation to show the image at the
	 * indicated position, but not to start. If the animation is running, a call
	 * to this method causes the animation to jump to the image at the indicated
	 * position and continue running immediately.
	 * 
	 * 
	 * @param position
	 *            - the index in the row of images building the animation to
	 *            which the animation is forced to jump
	 * @see jumpTo in interface Animated
	 */
	public void jumpTo(int position) {
		this.position = position;
	}

	/**
	 * Obtains the current position the animation is at the point of time of th
	 * method call. An animation can be considered as a row of images, this
	 * method provides the index within this row.
	 * 
	 * 
	 * @return the current position of the image within the row of images
	 *         building this animation
	 * @see getPosition in interface Animated
	 */
	public int getPosition() {
		return this.position;
	}

	Rectangle getDirtyRegion() {
		int frame = currentFrame;
		if (frame == 0) {
			return null;
		}
		if (frame == frames.length) {
			frame = 0;
		}
		int[] modified = frames[frame].modifiedRowOffsets;
		Rectangle rect = new Rectangle(0, frames[frame].smallestChangedRow,
				new Dimension(getWidth(), frames[frame].highestChangedRow
						- frames[frame].smallestChangedRow));
		return rect;
	}

	/**
	 * Determines how often the animation is repeated once it has been started.
	 * 
	 * 
	 * @param n
	 *            - number of repetitions to be determined. This can be any
	 *            number larger than zero or LOOP alternatively. LOOP means that
	 *            the animation runs in an endless loop until the stop() method
	 *            has been called.
	 * @see setRepetitionMode in interface Animated
	 * @see getRepetitionMode()
	 */
	public void setRepetitionMode(int n) {
		this.repetitionMode = n;
	}

	/**
	 * Returns the repetition mode of this animation in form of a number. The
	 * number reads how many repetitions have been determined for this
	 * animation.
	 * 
	 * 
	 * @return number of repetitions determined for this animation or LOOP which
	 *         means the animation runs in an endless loop until the stop()
	 *         method has been called.
	 * @see getRepetitionMode in interface Animated
	 * @see setRepetitionMode(int)
	 */
	public int getRepetitionMode() {
		return this.repetitionMode;
	}

	/**
	 * Determines the delay after every image when running this animation.
	 * 
	 * 
	 * @param n
	 *            - delay in milliseconds
	 * @see setDelay in interface Animated
	 * @see getDelay()
	 */
	public void setDelay(int n) {
		this.delay = n;
	}

	/**
	 * Obtains the delay after every image for this animation when running.
	 * 
	 * 
	 * @return the delay in milliseconds
	 * @see getDelay in interface Animated
	 * @see setDelay(int)
	 */
	public int getDelay() {
		return this.delay;
	}

	/**
	 * Sets the animation mode for this animation. The animation mode determines
	 * how the animation is running: always forwards, or forwards and backwards
	 * alternating.
	 * 
	 * 
	 * @param mode
	 *            - the animation mode. Possible values are: REPEATING and
	 *            ALTERNATING.
	 * @see setAnimationMode in interface Animated
	 * @see getAnimationMode()
	 */
	public void setAnimationMode(int mode) {
		this.animationMode = mode;
	}

	/**
	 * Obtains the animation mode for this animation. The animation mode
	 * determines how the animation is running: always forwards, or forwards and
	 * backwards alternating.
	 * 
	 * 
	 * @return the animation mode. Possible values are: REPEATING and
	 *         ALTERNATING.
	 * @see getAnimationMode in interface Animated
	 * @see setAnimationMode(int)
	 */
	public int getAnimationMode() {
		return this.animationMode;
	}

	/**
	 * Represents a frame within the animation that is not the first frame
	 */
	static class Frame {
		/**
		 * Offset since the beginning of the animation
		 */
		private int time;

		private byte[] keyFrame;

		/**
		 * Relevant only for standard frames, this represents the rows that were
		 * modified for this specific frame
		 */
		byte[][] modifiedRows;
		int[] modifiedRowOffsets;
		private boolean drawPrevious;
		int smallestChangedRow;
		int highestChangedRow;

		public Frame(int time, byte[] keyFrame, Vector rowNumbers,
				Vector rowValues, boolean drawPrevious) {
			this.time = time;
			this.keyFrame = keyFrame;
			this.drawPrevious = drawPrevious;
			initArrays(rowNumbers, rowValues);
		}

		private void initArrays(Vector rowNumbers, Vector rowValues) {
			modifiedRowOffsets = new int[rowNumbers.size()];
			modifiedRows = new byte[modifiedRowOffsets.length][];
			for (int iter = 0; iter < modifiedRowOffsets.length; iter++) {
				modifiedRowOffsets[iter] = ((Integer) rowNumbers
						.elementAt(iter)).intValue();
				modifiedRows[iter] = (byte[]) rowValues.elementAt(iter);
			}
			smallestChangedRow = modifiedRowOffsets[0];
			highestChangedRow = modifiedRowOffsets[0];
			for (int i = 1; i < modifiedRowOffsets.length; i++) {
				smallestChangedRow = Math.min(smallestChangedRow,
						modifiedRowOffsets[i]);
				highestChangedRow = Math.max(highestChangedRow,
						modifiedRowOffsets[i]);
			}
		}

		public Frame(int time, byte[] keyFrame) {
			this.time = time;
			this.keyFrame = keyFrame;
			modifiedRowOffsets = new int[0];
		}

		/**
		 * This constructor is used for scaling the original frame according to
		 * the given ratios
		 */
		public Frame(int time, byte[] keyFrame, Frame original, int xRatio,
				int yRatio, int width, int height) {
			this.time = time;
			this.keyFrame = keyFrame;

			// if the original was a keyframe then no work...
			if (original.modifiedRowOffsets.length == 0) {
				modifiedRowOffsets = original.modifiedRowOffsets;
				return;
			}
			Vector newRows = new Vector();
			Vector newValues = new Vector();

			int xPos = xRatio / 2;
			int yPos = yRatio / 2;

			for (int y = 0; y < height; y++) {
				int srcY = yPos >> 16;

				// do we have the row at srcY???
				int rowAtY = -1;
				for (int iter = 0; iter < original.modifiedRowOffsets.length; iter++) {
					if (original.modifiedRowOffsets[iter] == srcY) {
						rowAtY = iter;
						break;
					}
				}
				if (rowAtY != -1) {
					byte[] newRow = new byte[width];
					for (int x = 0; x < width; x++) {
						int srcX = xPos >> 16;
						newRow[x] = original.modifiedRows[rowAtY][srcX];
						xPos += xRatio;
					}
					newRows.addElement(new Integer(y));
					newValues.addElement(newRow);
				}
				yPos += yRatio;
				xPos = xRatio / 2;
			}

			initArrays(newRows, newValues);
		}

		public boolean isDrawPrevious() {
			return drawPrevious;
		}

		public int getTime() {
			return time;
		}

		private byte[] getModifiedRow(int row) {
			for (int iter = 0; iter < modifiedRowOffsets.length; iter++) {
				if (modifiedRowOffsets[iter] == row) {
					return modifiedRows[iter];
				}
			}
			return null;
		}

		private byte[] getKeyFrame() {
			return keyFrame;
		}

		private void setKeyFrame(byte[] keyFrame) {
			this.keyFrame = keyFrame;
		}
	}
}
