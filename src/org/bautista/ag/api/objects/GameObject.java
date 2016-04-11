package org.bautista.ag.api.objects;

import org.bautista.ag.api.Environment;
import org.bautista.ag.api.locatable.Position;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameObject extends ImageView {

	private Image image;
	private final boolean movable;
	private double xVelocity;
	private double yVelocity;

	public GameObject(Image image, Position position, boolean movable) {
		super(image);
		this.image = image;
		this.movable = movable;
		setX(position.getX());
		setY(position.getY());
	}

	private void updateLocation() {
		if (movable) {
			double nextY = (getY() - yVelocity);
			double nextX = (getX() + xVelocity);
			// hit right
			if (!Environment.getInstance().getBackgroundDimension().contains((getBoundary().getMaxX() + xVelocity),
					getY())) {
				xVelocity = Environment.getInstance().hasRicochet()
						? Environment.getInstance().getRicochet().applyRicochet(-1 * xVelocity) : 0;
				// hit left
			} else if (!Environment.getInstance().getBackgroundDimension().contains(nextX, getY())) {
				xVelocity = Environment.getInstance().hasRicochet()
						? Environment.getInstance().getRicochet().applyRicochet(-1 * xVelocity) : 0;
				// hit bottom
			} else if (!Environment.getInstance().getBackgroundDimension().contains(getX(),
					getBoundary().getMaxY() - yVelocity)) {
				yVelocity = Environment.getInstance().hasRicochet()
						? Environment.getInstance().getRicochet().applyRicochet(-1 * yVelocity) : 0;
				// hit top
			} else if (!Environment.getInstance().getBackgroundDimension().contains(getX(), nextY)) {
				yVelocity = Environment.getInstance().hasRicochet()
						? Environment.getInstance().getRicochet().applyRicochet(-1 * yVelocity) : 0;
			}
			reposition((getX() + xVelocity), (getY() - yVelocity), false);
		}
	}

	public void reposition(double x, double y, boolean scroll) {
		if (!scroll) {
			setX(x);
			setY(y);
		} else if (Environment.getInstance().getBackgroundDimension().contains(x, y)) {
			setX(x);
			setY(y);
		}
	}

	public void reposition(Position position, boolean scroll) {
		reposition(position.getX(), position.getY(), scroll);
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
		return getBoundary().getMaxY() < Environment.getInstance().getBackgroundDimension().getHeight();
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(getX(), getY(), image.getWidth(), image.getHeight());
	}

	public boolean intersects(GameObject gameObject) {
		return gameObject.getBoundary().intersects(this.getBoundary());
	}

	public void update() {
		updateLocation();
	}

}
