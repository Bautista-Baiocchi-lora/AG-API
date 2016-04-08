package org.bautista.ag.api;

import java.util.ArrayList;

import org.bautista.ag.api.locatable.Dimension;
import org.bautista.ag.api.objects.GameObject;
import org.bautista.ag.api.objects.SceneObject;
import org.bautista.ag.api.objects.Sprite;
import org.bautista.ag.api.physics.Gravity;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Environment extends Stage {

	private static Environment instance;
	private final Background background;
	private Gravity gravity;
	private final Scene scene;
	private final Group root;
	private final ArrayList<GameObject> gameObjects;
	private final ArrayList<Sprite> sprites;
	private final ArrayList<SceneObject> sceneObjects;

	public Environment(Background background) {
		instance = this;
		gameObjects = new ArrayList<GameObject>();
		sprites = new ArrayList<Sprite>();
		sceneObjects = new ArrayList<SceneObject>();
		this.background = background;
		this.gravity = new Gravity();
		root = new Group();
		scene = new Scene(root);

		root.getChildren().add(background);
		setScene(scene);
		show();
		initialize();
	}

	public void initialize() {
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (gravity.isEnabled()) {
					simulateGravity();
				}
				updateObjects();
				background.renderGraphics(gameObjects);
			}

		}.start();
	}

	private void updateObjects() {
		for (GameObject object : gameObjects) {
			object.update();
		}
	}

	private void simulateGravity() {
		for (GameObject object : gameObjects) {
			if (object.isElevated()) {
				object.setYVelocity(object.getYVelocity() + gravity.getVelocity());
			}
		}
	}

	public Dimension getBackgroundDimension() {
		return background.getDimension();
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void add(GameObject gameObject) {
		gameObjects.add(gameObject);
	}

	public void remove(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}

	public static Environment getInstance() {
		return instance;
	}

}
