package com.sun.dtv.lwuit.animations;

public class Motion extends Object
{
	private static final int LINEAR = 0;
	private static final int SPLINE = 1;
	private static final int FRICTION = 2;

	private int motionType;
	private int sourceValue;
	private int destinationValue;
	private int duration;
	private long startTime;
	private float initVelocity,  friction;

	/**
	 * Construct a point/destination motion.
	 *
	 * 
	 * @param sourceValue - starting value
	 * @param destinationValue - destination value
	 * @param duration - motion duration
	 */
	protected Motion(int sourceValue, int destinationValue, int duration)
	{
		this.sourceValue = sourceValue;
		this.destinationValue = destinationValue;
		this.duration = duration;
	}

	/**
	 * Construct a velocity motion.
	 *
	 * 
	 * @param sourceValue - starting value
	 * @param initVelocity - initial velocity
	 * @param friction - degree of friction
	 */
	protected Motion(int sourceValue, float initVelocity, float friction)
	{
		this.sourceValue = sourceValue;
		this.initVelocity = initVelocity;
		this.friction = friction;
		duration = (int) ((Math.abs(initVelocity)) / friction);
	}

	/**
	 * Creates a linear motion starting from source value all the way to
	 * destination value.
	 *
	 * 
	 * @param sourceValue - the number from which we are starting (usually indicating animation start position)
	 * @param destinationValue - the number to which we are heading (usually indicating animation destination)
	 * @param duration - the length in milliseconds of the motion (time it takes to get from sourceValue to destinationValue)
	 * @return a Motion object
	 */
	public static Motion createLinearMotion(int sourceValue, int destinationValue, int duration)
	{
		Motion l = new Motion(sourceValue, destinationValue, duration);
		l.motionType = LINEAR;
		return l;
	}

	/**
	 * Creates a spline motion starting from source value all the way to
	 * destination value.
	 *
	 * 
	 * @param sourceValue - the number from which we are starting (usually indicating animation start position)
	 * @param destinationValue - the number to which we are heading (usually indicating animation destination)
	 * @param duration - the length in milliseconds of the motion (time it takes to get from sourceValue to destinationValue)
	 * @return a Motion object
	 */
	public static Motion createSplineMotion(int sourceValue, int destinationValue, int duration)
	{
		Motion spline = new Motion(sourceValue, destinationValue, duration);
		spline.motionType = SPLINE;
		return spline;
	}

	/**
	 * Creates a friction motion starting from source with initial speed and
	 * the friction.
	 *
	 * 
	 * @param sourceValue - the number from which we are starting (usually indicating animation start position)
	 * @param initVelocity - the starting velocity
	 * @param friction - the motion friction
	 * @return a Motion object
	 */
	public static Motion createFrictionMotion(int sourceValue, float initVelocity, float friction)
	{
		Motion frictionMotion = new Motion(sourceValue, initVelocity, friction);
		frictionMotion.motionType = FRICTION;
		return frictionMotion;
	}

	/**
	 * Sets the start time to the current time.
	 *
	 * 
	 */
	public void start()
	{
		startTime = System.currentTimeMillis();
	}

	/**
	 * Returns true if the motion has run its course and has finished meaning
	 * the current time is greater than startTime + duration.
	 *
	 * 
	 * 
	 * @return true if System.currentTimeMillis() > duration + startTime
	 */
	public boolean isFinished()
	{
		return System.currentTimeMillis() > duration + startTime;
	}

	private int getSplineValue() {
		//make sure we reach the destination value.
		if(isFinished()){
			return destinationValue;
		}
		float totalTime = duration;
		float currentTime = (int) (System.currentTimeMillis() - startTime);
		currentTime = Math.min(currentTime, totalTime);
		int p = Math.abs(destinationValue - sourceValue);
		float centerTime = totalTime / 2;
		float l = p / (centerTime * centerTime);
		int x;
		if (sourceValue < destinationValue) {
			if (currentTime > centerTime) {
				x = sourceValue + (int) (l * (-centerTime * centerTime + 2 * centerTime * currentTime -
							currentTime * currentTime / 2));
			} else {
				x = sourceValue + (int) (l * currentTime * currentTime / 2);
			}
		} else {
			currentTime = totalTime - currentTime;
			if (currentTime > centerTime) {
				x = destinationValue + (int) (l * (-centerTime * centerTime + 2 * centerTime * currentTime -
							currentTime * currentTime / 2));
			} else {
				x = destinationValue + (int) (l * currentTime * currentTime / 2);
			}
		}
		return x;
	}

	/**
	 * Returns the value for the motion for the current clock time.
	 * The value is dependent on the Motion type.
	 *
	 * 
	 * 
	 * @return a value that is relative to the source value
	 */
	public int getValue()
	{
		if (motionType == SPLINE) {
			return getSplineValue();
		}else if(motionType == FRICTION){
			return getFriction();
		}else{
			return getLinear();
		}
	}

	private int getLinear() {
		//make sure we reach the destination value.
		if(isFinished()){
			return destinationValue;
		}
		float totalTime = duration;
		float currentTime = (int) (System.currentTimeMillis() - startTime);
		int dis = destinationValue - sourceValue;
		int val = (int)(sourceValue + (currentTime / totalTime * dis));

		if(destinationValue < sourceValue) {
			return Math.max(destinationValue, val);
		} else {
			return Math.min(destinationValue, val);
		}
	}

	private int getFriction() {
		int time = (int) (System.currentTimeMillis() - startTime);
		int retVal = 0;

		retVal = (int)((Math.abs(initVelocity) * time) - (friction * (((float)time * time) / 2)));
		if (initVelocity < 0) {
			retVal *= -1;
		}
		retVal += (int) sourceValue;
		return retVal;
	}

	/**
	 * The number from which we are starting (usually indicating animation start
	 * position).
	 *
	 * 
	 * 
	 * @return the start position
	 * @see setSourceValue(int)
	 */
	public int getSourceValue()
	{
		return this.sourceValue;
	}

	/**
	 * The number from which we are starting (usually indicating animation start
	 * position).
	 *
	 * 
	 * @param sourceValue - the start position
	 * @see getSourceValue()
	 */
	public void setSourceValue(int sourceValue)
	{
		this.sourceValue = sourceValue;
	}

}
