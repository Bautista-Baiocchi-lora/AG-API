package org.bautista.ag.api.physics;

public class Gravity {

	private final double velocity;
	private final double DEFAULT_VELOCITY = -0.07;
	private boolean enabled = true;

	public Gravity(int velocity) {
		this.velocity = velocity > 0 ? (-velocity) : velocity;
	}

	public Gravity() {
		this.velocity = DEFAULT_VELOCITY;
	}

	public double getVelocity() {
		return velocity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
