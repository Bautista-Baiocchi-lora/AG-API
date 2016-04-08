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

	public boolean contains(double x, double y) {
		return x < width && y < height;
	}

	public boolean contains(GameObject gameObject) {
		return contains(gameObject.getBoundary().getMaxX(), 0)
				&& contains(gameObject.getBoundary().getMinX(), 0)
				&& contains(0, gameObject.getBoundary().getMaxY())
				&& contains(0, gameObject.getBoundary().getMinY());
	}

}
