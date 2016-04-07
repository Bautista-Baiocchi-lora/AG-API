package org.bautista.ag.api.objects;

import org.bautista.ag.api.locatable.Position;

import javafx.scene.image.Image;

public abstract class Sprite extends GameObject {
	private double xVelocity;
	private double yVelocity;

	public Sprite(Image image, Position position) {
		super(image, position, true);
	}

	public boolean isMoving() {
		return xVelocity != 0 || yVelocity != 0;
	}

	public void setVelocity(int xVelocity, int yVelocity) {
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}

}
