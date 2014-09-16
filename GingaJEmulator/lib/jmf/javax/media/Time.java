package javax.media;

public class Time extends Object {

	public static final long ONE_SECOND = 1000000000;
	protected long sec;
	public Time(long nano) {
		sec = nano;
	}

	public Time(double seconds) {
		this.sec = this.secondsToNanoseconds(seconds);
	}

	protected long secondsToNanoseconds(double seconds) {
		return this.sec*Time.ONE_SECOND;
	}

	public long getNanoseconds() {
		return sec;
	}

	public double getSeconds() {
		return this.sec/Time.ONE_SECOND;
	}

}
