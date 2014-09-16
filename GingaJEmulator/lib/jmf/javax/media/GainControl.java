package javax.media;

public interface GainControl extends Control{


	// Get the current gain set for this object in dB.
	public abstract float getDB() ;
	//Get the current gain set for this object as a value between 0.0 and 1.0
	public abstract float getLevel();

	//Get the mute state of the signal associated with this GainControl.
	public abstract boolean getMute();
	//	Set the gain in decibels.
	public abstract void setDB(float dB); 
	//Set the gain using a floating point scale with values between 0.0 and 1.0.
	public abstract float setLevel(float level);

	//Mute or unmute the signal associated with this GainControl.
	public abstract void setMute(boolean mute);
	//void addGainChangeListener(GainChangeListener listener);
	public abstract void addGainChangeListener(GainChangeListener listener);
	//void removeGainChangeListener(GainChangeListener listener);
	public abstract void removeGainChangeListener(GainChangeListener listener);

}

