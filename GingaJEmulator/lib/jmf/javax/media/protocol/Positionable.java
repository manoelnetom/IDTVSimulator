package javax.media.protocol;

public interface Positionable {

	public static final int RoundUp = 1;
	public static final int RoundDown = 2;
	public static final int RoundNearest = 3;

	public abstract javax.media.Time setPosition(javax.media.Time where,
	        int rounding);

	public abstract boolean isRandomAccess();
}
