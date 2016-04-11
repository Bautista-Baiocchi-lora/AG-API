package org.bautista.ag.api.locatable;

import org.bautista.ag.api.objects.GameObject;

public class Dimension {

	private double width;
	private double height;

	public Dimension(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public boolean contains(double x, double y, double buffer) {
		return x < (width + buffer) && x > (1 + buffer) && y < (height + buffer)
				&& y > (1 + buffer);
	}

	public boolean contains(double x, double y) {
		return contains(x, y, 0);
	}

	public boolean contains(GameObject gameObject) {
		return contains(gameObject.getBoundary().getMaxX(), 1)
				&& contains(gameObject.getBoundary().getMinX(), 1)
				&& contains(1, gameObject.getBoundary().getMaxY())
				&& contains(1, gameObject.getBoundary().getMinY());
	}

}
