package com.sun.dtv.ui;

public interface Animated {

	// Value for the animation mode.
	static int 	ALTERNATING  = 1;
	// Value for the repetition mode.
	static int 	LOOP         = 2;
	// Value for the animation mode.
	static int 	REPEATING    = 3;

	// Obtains the animation mode for this animation.
	public int getAnimationMode();

	// Obtains the delay after every image for this animation when running.
	int getDelay();

	// Obtains the current position the animation is at the point of time of th method call.
	int getPosition();

	// Returns the repetition mode of this animation in form of a number.
	int getRepetitionMode();

	// Obtains the running mode of an animation.
	boolean isRunning();

	// Forces an animation to jump to the position indicated by the parameter.
	void jumpTo(int position);

	// Sets the animation mode for this animation.
	void setAnimationMode(int mode);

	// Determines the delay after every image when running this animation.
	void setDelay(int n);

	// Determines how often the animation is repeated once it has been started.
	void setRepetitionMode(int n);

	// Starts an animation.
	void start();

	// Stops an animation.
	void stop();

}

