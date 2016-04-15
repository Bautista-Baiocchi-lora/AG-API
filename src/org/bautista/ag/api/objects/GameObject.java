package org.bautista.ag.api.objects;

import java.util.ArrayList;

import org.bautista.ag.api.GameEngine;
import org.bautista.ag.api.locatable.CollisionFlag;
import org.bautista.ag.api.locatable.Position;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject extends ImageView {

	private final Image image;
	private final boolean movable;
	private double xVelocity;
	private double yVelocity;
	private Position position;

	public GameObject(final Image image, final Position position, final boolean movable) {
		super(image);
		this.image = image;
		this.movable = movable;
		this.position = position;
		reposition(position);
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(getX(), getY(), image.getWidth(), image.getHeight());
	}

	public Position getPosition() {
		return position;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public boolean intersects(final GameObject gameObject) {
		return gameObject.getBoundary().intersects(getBoundary())
				&& (gameObject.getPosition().getZ() == getPosition().getZ());
	}

	public boolean isElevated() {
		return getBoundary().getMaxY() < GameEngine.getInstance().getBackground().getHeight();
	}

	public boolean isMovable() {
		return movable;
	}

	public boolean isMoving() {
		return xVelocity != 0 || yVelocity != 0;
	}

	public void reposition(final double x, final double y, final double z) {
		reposition(new Position(x, y, z));
	}

	public void reposition(final Position position) {
		this.position = position;
		setX(position.getX());
		setY(position.getY());
	}

	public void setXVelocity(final double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void setYVelocity(final double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public void update() {
		updateLocation();
	}

	private void updateLocation() {
		if (movable) {
			reposition((getX() + xVelocity), (getY() - yVelocity), position.getZ());
		}
	}

}
