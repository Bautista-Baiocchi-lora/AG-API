package org.bautista.ag.api;

import java.util.ArrayList;

import org.bautista.ag.api.objects.GameObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Environment extends Canvas {

	private static Environment instance;
	private final GraphicsContext graphics;
	private final ArrayList<GameObject> gameObjects;

	private Environment() {
		graphics = this.getGraphicsContext2D();
		gameObjects = new ArrayList<GameObject>();
	}

	public void add(GameObject gameObject) {
		gameObjects.add(gameObject);
		renderGraphics();
	}

	public void remove(GameObject gameObject) {
		gameObjects.remove(gameObject);
		renderGraphics();
	}

	private void renderGraphics() {
		for (GameObject gameObject : gameObjects) {
			gameObject.renderGraphics(graphics);
		}
	}

	public static Environment getInstance() {
		return instance == null ? instance = new Environment() : instance;
	}

}
