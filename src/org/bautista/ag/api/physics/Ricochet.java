package org.bautista.ag.api.physics;

public class Ricochet {
	public static final double NONE = 0;
	public static final double SLIGHT_DECREASE = -0.3;
	public static final double SLIGHT_INCREASE = 0.3;
	private final double velocityChange;

	public Ricochet() {
		this(0);
	}

	public Ricochet(final double velocityChange) {
		this.velocityChange = velocityChange;
	}

	public double applyRicochet(final double velocity) {
		if (velocityChange < 0) {
			if (velocity < 0 ? (velocity - velocityChange) > 0 : (velocity + velocityChange) < 0) {
				return 0;
			}
			return velocity < 0 ? (velocity - velocityChange) : (velocity + velocityChange);
		} else if (velocityChange > 0) {
			return velocity < 0 ? (velocity - velocityChange) : (velocity + velocityChange);
		}
		return 0;
	}

	public double getVelocityChange() {
		return velocityChange;
	}

	public boolean isEnabled() {
		return velocityChange != 0;
	}
}
