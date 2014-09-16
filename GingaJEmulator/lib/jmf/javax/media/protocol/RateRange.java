package javax.media.protocol;

public class RateRange extends Object {
	private float init;
	private float min;
    private float max;
    private boolean isExact;
    private float rate;
	public RateRange(RateRange r) {
		this.init = r.init;
		this.min = r.min;
		this.max = r.max;
		this.isExact = r.isExact;
		
	}

	public RateRange(float init,
	                 float min,
	                 float max,
	                 boolean isExact) {
		this.init = init;
		this.min = min;
		this.max = max;
		this.isExact = isExact;
	}

	public float setCurrentRate(float rate) {
		float ret = this.rate;
		this.rate = rate;
		return ret;
	}

	public float getCurrentRate() {
		return this.rate;
	}

	public float getMinimumRate() {
		return 0;
	}

	public float getMaximumRate() {
		return 0;
	}

	public boolean isExact() {
		return false;
	}

}
