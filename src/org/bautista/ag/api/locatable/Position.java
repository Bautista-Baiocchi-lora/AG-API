package org.bautista.ag.api.locatable;

public class Position {

	private double x;
	private double y;
	private double z;

	public Position(final double x, final double y) {
		this(x, y, 0);
	}

	public Position(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setZ(final double z) {
		this.z = z;
	}

	@Override
	public boolean equals(Object position) {
		if (!(position instanceof Position)) {
			return false;
		}
		Position p = (Position) position;
		return p.getX() == getX() && p.getY() == getY() && p.getZ() == getZ();
	}

	@Override
	public String toString() {
		return "X: " + x + ", Y: " + y + ", Z: " + z;
	}
}