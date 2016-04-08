package org.bautista.ag.api;

import java.util.List;

import org.bautista.ag.api.locatable.Dimension;
import org.bautista.ag.api.objects.GameObject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Background extends Canvas {

	private final GraphicsContext graphics;
	private final Dimension dimension;

	public Background(Dimension dimension) {
		super(dimension.getWidth(), dimension.getHeight());
		this.dimension = dimension;
		graphics = this.getGraphicsContext2D();
	}

	private void clearGraphics() {
		graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	protected void renderGraphics(List<GameObject> gameObjects) {
		clearGraphics();
		for (GameObject gameObject : gameObjects) {
			gameObject.renderGraphics(graphics);
		}
	}

	public Dimension getDimension() {
		return dimension;
	}

}
