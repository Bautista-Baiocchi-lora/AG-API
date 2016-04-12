package org.bautista.ag.api.objects;

import org.bautista.ag.api.GameEngine;
import org.bautista.ag.api.background.scroll.ScrollDirection;
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
			// hit right
			if (!GameEngine.getInstance().getBackground().getDimension().contains((getBoundary().getMaxX() + xVelocity),
					getY())) {
				if (!GameEngine.getInstance().getEnvironment().getScrollType()
						.isValidDirection(ScrollDirection.RIGHT)) {
					xVelocity = GameEngine.getInstance().getEnvironment().hasRicochet()
							? GameEngine.getInstance().getEnvironment().getRicochet().applyRicochet(-1 * xVelocity) : 0;
				} else {
					// request scroll
				}
				// hit left
			} else if (!GameEngine.getInstance().getBackground().getDimension().contains((getX() + xVelocity),
					getY())) {
				if (!GameEngine.getInstance().getEnvironment().getScrollType().isValidDirection(ScrollDirection.LEFT)) {
					xVelocity = GameEngine.getInstance().getEnvironment().hasRicochet()
							? GameEngine.getInstance().getEnvironment().getRicochet().applyRicochet(-1 * xVelocity) : 0;
				} else {
					// request scroll
				}
				// hit bottom
			} else if (!GameEngine.getInstance().getBackground().getDimension().contains(getX(),
					getBoundary().getMaxY() - yVelocity)) {
				if (!GameEngine.getInstance().getEnvironment().getScrollType().isValidDirection(ScrollDirection.DOWN)) {
					yVelocity = GameEngine.getInstance().getEnvironment().hasRicochet()
							? GameEngine.getInstance().getEnvironment().getRicochet().applyRicochet(-1 * yVelocity) : 0;
				} else {
					// request scroll
				}
				// hit top
			} else if (!GameEngine.getInstance().getBackground().getDimension().contains(getX(),
					(getY() - yVelocity))) {
				if (!GameEngine.getInstance().getEnvironment().getScrollType().isValidDirection(ScrollDirection.UP)) {
					yVelocity = GameEngine.getInstance().getEnvironment().hasRicochet()
							? GameEngine.getInstance().getEnvironment().getRicochet().applyRicochet(-1 * yVelocity) : 0;
				} else {
					// request scroll
				}
			}
			reposition((getX() + xVelocity), (getY() - yVelocity));
		}
	}

	public void reposition(double x, double y) {
		setX(x);
		setY(y);
	}

	public void reposition(Position position) {
		reposition(position.getX(), position.getY());
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
		return gameObject.getBoundary().intersects(this.getBoundary());
	}

	public void update() {
		updateLocation();
	}

}
