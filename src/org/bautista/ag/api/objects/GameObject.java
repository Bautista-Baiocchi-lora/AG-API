package org.bautista.ag.api.objects;

import org.bautista.ag.api.Environment;
import org.bautista.ag.api.locatable.Position;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject {

	private Image image;
	private Position position;
	private final boolean movable;
	private double xVelocity;
	private double yVelocity;

	public GameObject(Image image, Position position, boolean movable) {
		this.image = image;
		this.position = position;
		this.movable = movable;
	}

	public void move(Position position) {
		if (movable) {
			if (!Environment.getInstance().getBackgroundDimension().contains(
					(position.getX() + (getBoundary().getMaxX() - position.getX())), 0)
					&& xVelocity != 0) {
				xVelocity = 0;
			}
			if (!Environment.getInstance().getBackgroundDimension().contains(
					position.getX(), 0) && xVelocity != 0) {
				xVelocity = 0;
			} else if (!Environment.getInstance().getBackgroundDimension().contains(0,
					(position.getY() + (getBoundary().getMaxY() - position.getY())))
					&& yVelocity != 0) {
				yVelocity = 0;
			} else if (!Environment.getInstance().getBackgroundDimension().contains(0,
					position.getY()) && yVelocity != 0) {
				yVelocity = 0;
			} else {
				this.position = position;
			}
		}
	}

	public void move(double x, double y) {
		move(new Position(x, y));
	}

	public void renderGraphics(GraphicsContext gc) {
		gc.drawImage(image, position.getX(), position.getY());
	}

	public boolean isMovable() {
		return movable;
	}

	public Position getPosition() {
		return position;
	}

	public double getX() {
		return position.getX();
	}

	public void setX(double x) {
		position.setX(x);
	}

	public double getY() {
		return position.getY();
	}

	public void setY(double y) {
		position.setY(y);
	}

	public void setPosition(Position position) {
		this.position = position;
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
		return getBoundary().getMaxY() < Environment.getInstance()
				.getBackgroundDimension().getHeight();
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(position.getX(), position.getY(), image.getWidth(),
				image.getHeight());
	}

	public boolean intersects(GameObject gameObject) {
		return gameObject.getBoundary().intersects(this.getBoundary());
	}

	public void update() {
		move((getX() + getXVelocity()), (getY() - getYVelocity()));
	}

}
