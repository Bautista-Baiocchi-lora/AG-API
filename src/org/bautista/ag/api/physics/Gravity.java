package org.bautista.ag.api.physics;

public class Gravity {

	private final double velocity;
	private final double DEFAULT_VELOCITY = -0.07;
	private boolean enabled = true;

	public Gravity() {
		velocity = DEFAULT_VELOCITY;
	}

	public Gravity(final int velocity) {
		this.velocity = velocity > 0 ? (-velocity) : velocity;
	}

	public double getVelocity() {
		return velocity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

}
