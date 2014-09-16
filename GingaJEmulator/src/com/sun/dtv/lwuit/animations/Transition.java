package com.sun.dtv.lwuit.animations;

import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Graphics;

import com.sun.dtv.ui.Animated;

public abstract class Transition extends Object implements Animation, Animated
{
	private Component source;
	private Component destination;
	private int repetitionMode = Animated.LOOP;
	private int animationMode = Animated.REPEATING;
	private int delay;

	/**
	 * Constructor
	 * 
	 */
	public Transition()
	{
	}

	/**
	 * Invoked to set the source and destination forms.
	 * This method should not be invoked by developers.
	 *
	 * 
	 * @param source - the source form from which the transition originates
	 * @param destination - the destination form to which the transition will lead
	 */
	public final void init( Component source, Component destination)
	{
		this.source = source;
		this.destination = destination;
		if (source != null && source instanceof Container) {
			((Container)source).layoutContainer();
		}
		if (destination != null && destination instanceof Container) {
			((Container)destination).layoutContainer();
		}
	}

	/**
	 * Callback thats invoked before a transition begins, the source form may be null
	 * for the first form in the application.
	 *
	 * 
	 */
	public void initTransition()
	{
	}

	/**
	 * Returns the destination form that should be set once animation is completed.
	 *
	 * 
	 * 
	 * @return the destination
	 */
	public final Component getDestination()
	{
		return this.destination;
	}

	/**
	 * Returns the source form which is the form from which the animation is
	 * starting.
	 * This may be null for the first form in the application.
	 *
	 * 
	 * 
	 * @return the source
	 */
	public final Component getSource()
	{
		return this.source;
	}

	/**
	 * Optional operation to cleanup the garbage left over by a running transition.
	 *
	 * 
	 */
	public void cleanup()
	{
		source = null;
		destination = null;

		System.gc();
	}

	/**
	 * Copies a transitions.
	 *
	 * 
	 * 
	 * @return the copied transition
	 */
	public abstract Transition copy();

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/animations/Animation.html#animate()">Animation</A></CODE></B></DD>
	 * Allows the animation to reduce "repaint" calls when it returns false. It is
	 * called once for every frame.
	 *
	 * 
	 * @return true if a repaint is desired or false if no repaint is necessary
	 * @see animate in interface Animation
	 */
	public abstract boolean animate();

	/**
	 * <B>Description copied from interface: <CODE><A HREF="../../../../../com/sun/dtv/lwuit/animations/Animation.html#paint(com.sun.dtv.lwuit.Graphics)">Animation</A></CODE></B></DD>
	 * Draws the animation, within a component the standard paint method would be
	 * invoked since it bares the exact same signature.
	 *
	 * 
	 * @see paint in interface Animation
	 */
	public abstract void paint( Graphics g);

	/**
	 * Starts an animation. Please consider that an animation is in the stopped
	 * mode per default, so it has to be started explicitly once it has been
	 * created.
	 * In case the animation is already running, a call to <code>start()</code>
	 * causes a restart of the animation.
	 *
	 * 
	 * @see start in interface Animated
	 */
	public void start()
	{
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
	}

	/**
	 * Obtains the running mode of an animation. Returns true if the animation
	 * is running, false otherwise.
	 *
	 * 
	 * @return true if the animation is running, false otherwise
	 * @see isRunning in interface Animated
	 */
	public boolean isRunning()
	{
		return false;
	}

	/**
	 * Forces an animation to jump to the position indicated by the parameter.
	 * An animation can be considered as a row of images, the parameter of this
	 * method provides the index within this row.
	 * If the animation is stopped, a call to this method causes the animation
	 * to show the image at the indicated position, but not to start.
	 * If the animation is running, a call to this method causes the animation
	 * to jump to the image at the indicated position and continue running
	 * immediately.
	 *
	 * 
	 * @param position - the index in the row of images building the animation to which the animation is forced to jump
	 * @see jumpTo in interface Animated
	 */
	public void jumpTo(int position)
	{
	}

	/**
	 * Obtains the current position the animation is at the point of time of
	 * th method call. An animation can be considered as a row of images, this
	 * method provides the index within this row.
	 *
	 * 
	 * @return the current position of the image within the row of images building this animation
	 * @see getPosition in interface Animated
	 */
	public int getPosition()
	{
		return 0;
	}

	/**
	 * Determines how often the animation is repeated once it has been started.
	 *
	 * 
	 * @param mode - number of repetitions to be determined. This can be any number larger than zero or LOOP alternatively. LOOP means that the animation runs in an endless loop until the stop() method has been called.
	 * @see setRepetitionMode in interface Animated
	 * @see getRepetitionMode()
	 */
	public void setRepetitionMode(int n)
	{
		this.repetitionMode = n;
	}

	/**
	 * Returns the repetition mode of this animation in form of a number. The
	 * number reads how many repetitions have been determined for this
	 * animation.
	 *
	 * 
	 * @return number of repetitions determined for this animation or LOOP which means the animation runs in an endless loop until the stop() method has been called.
	 * @see getRepetitionMode in interface Animated
	 * @see setRepetitionMode(int)
	 */
	public int getRepetitionMode()
	{
		return this.repetitionMode;
	}

	/**
	 * Determines the delay after every image when running this animation.
	 *
	 * 
	 * @param n - delay in milliseconds
	 * @see setDelay in interface Animated
	 * @see getDelay()
	 */
	public void setDelay(int n)
	{
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
	public int getDelay()
	{
		return this.delay;
	}

	/**
	 * Sets the animation mode for this animation. The animation mode determines
	 * how the animation is running: always forwards, or forwards and backwards
	 * alternating.
	 *
	 * 
	 * @param mode - the animation mode. Possible values are: REPEATING and ALTERNATING.
	 * @see setAnimationMode in interface Animated
	 * @see getAnimationMode()
	 */
	public void setAnimationMode(int mode)
	{
		this.animationMode = mode;
	}

	/**
	 * Obtains the animation mode for this animation. The animation mode
	 * determines how the animation is running: always forwards, or forwards and
	 * backwards alternating.
	 *
	 * 
	 * @return the animation mode. Possible values are: REPEATING and ALTERNATING.
	 * @see getAnimationMode in interface Animated
	 * @see setAnimationMode(int)
	 */
	public int getAnimationMode()
	{
		return this.animationMode;
	}

}
