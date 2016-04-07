package org.bautista.ag.api;

import java.awt.Dimension;
import java.util.ArrayList;

import org.bautista.ag.api.objects.GameObject;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Background extends Canvas {

	private final GraphicsContext graphics;
	private final ArrayList<GameObject> gameObjects;

	public Background(Dimension dimension) {
		super(dimension.getWidth(), dimension.getHeight());
		graphics = this.getGraphicsContext2D();
		gameObjects = new ArrayList<GameObject>();
	}

	protected void add(GameObject gameObject) {
		gameObjects.add(gameObject);
		renderGraphics();
	}

	protected void remove(GameObject gameObject) {
		gameObjects.remove(gameObject);
		renderGraphics();
	}

	private void clearGraphics() {
		graphics.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	private void renderGraphics() {
		clearGraphics();
		for (GameObject gameObject : gameObjects) {
			gameObject.renderGraphics(graphics);
		}
	}

}
