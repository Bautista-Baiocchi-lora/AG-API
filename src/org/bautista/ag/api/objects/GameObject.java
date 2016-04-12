package org.bautista.ag.api.objects;

import org.bautista.ag.api.GameEngine;
import org.bautista.ag.api.locatable.Position;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject extends ImageView {

	private Image image;
	private final boolean movable;
	private double xVelocity;
	private double yVelocity;
	private Position position;

	public GameObject(Image image, Position position, boolean movable) {
		super(image);
		this.image = image;
		this.movable = movable;
		this.position = position;
		reposition(position);
	}

	private void updateLocation() {
		if (movable) {
			reposition((getX() + xVelocity), (getY() - yVelocity), position.getZ());
		}
	}

	public void reposition(double x, double y, double z) {
		reposition(new Position(x, y, z));
	}

	public void reposition(Position position) {
		this.position = position;
		setX(position.getX());
		setY(position.getY());
	}

	public boolean isMovable() {
		return movable;
	}

	public Position getPosition() {
		return new Position(getX(), getY());
	}

	public boolean isMoving() {
		return xVelocity != 0 || yVelocity != 0;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public boolean isElevated() {
		return getBoundary().getMaxY() < GameEngine.getInstance().getBackground().getHeight();
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(getX(), getY(), image.getWidth(), image.getHeight());
	}

	public boolean intersects(GameObject gameObject) {
		return gameObject.getBoundary().intersects(this.getBoundary())
				&& gameObject.getPosition().getZ() == this.getPosition().getZ();
	}

	public void update() {
		updateLocation();
	}

}
