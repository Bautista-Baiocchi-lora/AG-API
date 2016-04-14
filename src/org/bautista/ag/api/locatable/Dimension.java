package org.bautista.ag.api.locatable;

import org.bautista.ag.api.objects.GameObject;

public class Dimension {

	private final double width;
	private final double height;

	public Dimension(final double width, final double height) {
		this.width = width;
		this.height = height;
	}

	public boolean contains(final double x, final double y) {
		return contains(x, y, 0);
	}

	public boolean contains(final double x, final double y, final double buffer) {
		return (x <= (width + buffer)) && (x >= (1 + buffer)) && (y <= (height + buffer)) && (y >= (1 + buffer));
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

}
