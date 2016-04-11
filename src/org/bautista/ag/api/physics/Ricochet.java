package org.bautista.ag.api.physics;

public class Ricochet {
	private final boolean enabled;
	private final double velocityChange;
	public static final double NONE = 0;
	public static final double MINIMAL_DECREASE = -1;
	public static final double MINIMAL_INCREASE = 10;

	public Ricochet(double velocityChange, boolean enabled) {
		this.velocityChange = velocityChange;
		this.enabled = enabled;
	}

	public Ricochet() {
		this(0, false);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public double getVelocityChange() {
		return velocityChange;
	}
}
