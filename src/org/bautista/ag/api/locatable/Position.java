package org.bautista.ag.api.locatable;

public class Position {

	private double x;
	private double y;
	private double z;

	public Position(double x, double y) {
		this(x, y, 0);
	}

	public Position(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getZ() {
		return z;
	}

	@Override
	public String toString() {
		return "X: " + x + ", Y: " + y + ", Z: " + z;
	}
}