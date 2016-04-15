package org.bautista.ag.api.physics;

public class Ricochet {
	public static final double NONE = 1;
	public static final double SLIGHT_DECREASE = 0.90;
	public static final double SLIGHT_INCREASE = 1.05;
	private final double velocityChange;

	public Ricochet() {
		this(1);
	}

	public Ricochet(final double velocityChange) {
		this.velocityChange = velocityChange;
	}

	public double applyRicochet(final double velocity) {
		double newVelocity = velocity * velocityChange;
		if (velocityChange < 1 && (newVelocity < 0 && newVelocity >= -0.1) || (newVelocity > 0 && newVelocity <= 0.1)) {
			return 0;
		}
		return newVelocity;
	}

	public double getVelocityChange() {
		return velocityChange;
	}

	public boolean isEnabled() {
		return velocityChange != 1;
	}
}
