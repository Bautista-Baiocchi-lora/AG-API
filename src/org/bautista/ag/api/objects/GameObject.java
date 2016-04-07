package org.bautista.ag.api.objects;

import org.bautista.ag.api.locatable.Position;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {

	private Image image;
	private Position position;
	private boolean movable;

	public GameObject(Image image, Position position, boolean movable) {
		this.image = image;
		this.position = position;
		this.movable = movable;
	}

	public void renderGraphics(GraphicsContext gc) {
		gc.drawImage(image, position.getX(), position.getY());
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public Position getPosition() {
		return position;
	}

	public void setX(int x) {
		position.setX(x);
	}

	public void setY(int y) {
		position.setY(y);
	}

	public void setZ(int z) {
		position.setZ(z);
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Rectangle2D getBoundary() {
		return new Rectangle2D(position.getX(), position.getY(), image.getWidth(),
				image.getHeight());
	}

	public boolean intersects(GameObject gameObject) {
		return gameObject.getBoundary().intersects(this.getBoundary());
	}

}
