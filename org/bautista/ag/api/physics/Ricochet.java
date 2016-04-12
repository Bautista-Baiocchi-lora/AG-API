package org.bautista.ag.api.physics;

public class Ricochet {
	private final boolean enabled;
	private final double velocityChange;
	public static final double NONE = 0;
	public static final double SLIGHT_DECREASE = -1;
	public static final double SLIGHT_INCREASE = 1;

	public Ricochet(double velocityChange, boolean enabled) {
		this.velocityChange = velocityChange;
		this.enabled = enabled;
	}

	public double applyRicochet(double velocity) {
		if (velocityChange < 0) {
			if (velocity < 0 ? velocity - velocityChange > 0 : velocity + velocityChange < 0) {
				return 0;
			}
			return velocity < 0 ? (velocity - velocityChange) : (velocity + velocityChange);
		} else if (velocityChange > 0) {
			return velocity < 0 ? (velocity - velocityChange) : (velocity + velocityChange);
		}
		return 0;
	}

	public Ricochet() {
		this(0, false);
	}

	public boolean isEnabled() {
		return enabled && velocityChange != 0;
	}

	public double getVelocityChange() {
		return velocityChange;
	}
}
