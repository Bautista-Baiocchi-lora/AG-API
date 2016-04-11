package org.bautista.ag.api;

import java.util.ArrayList;

import org.bautista.ag.api.locatable.Dimension;
import org.bautista.ag.api.objects.GameObject;
import org.bautista.ag.api.objects.SceneObject;
import org.bautista.ag.api.objects.Sprite;
import org.bautista.ag.api.physics.Gravity;
import org.bautista.ag.api.physics.Ricochet;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Environment extends Stage {

	private static Environment instance;
	private final Background background;
	private final Gravity gravity;
	private final boolean infinite;
	private final Ricochet ricochet;
	private final ArrayList<GameObject> gameObjects;
	private final ArrayList<Sprite> sprites;
	private final ArrayList<SceneObject> sceneObjects;

	private Environment(Builder builder) {
		instance = this;
		gameObjects = new ArrayList<GameObject>();
		sprites = new ArrayList<Sprite>();
		sceneObjects = new ArrayList<SceneObject>();
		this.background = builder.background;
		this.gravity = builder.gravity;
		this.infinite = builder.infinite;
		this.ricochet = builder.ricochet;

		setScene(background);
	}

	public void initialize() {
		show();
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

	public boolean isInfinite() {
		return infinite;
	}

	public Dimension getBackgroundDimension() {
		return background.getDimension();
	}

	public Ricochet getRicochet() {
		return ricochet;
	}

	public boolean hasRicochet() {
		return ricochet.isEnabled();
	}

	public boolean hasGravity() {
		return gravity.isEnabled();
	}

	public Gravity getGravity() {
		return gravity;
	}

	public void add(GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.add((Sprite) gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.add((SceneObject) gameObject);
		}
		gameObjects.add(gameObject);
	}

	public void remove(GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.remove((Sprite) gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.remove((SceneObject) gameObject);
		}
		gameObjects.remove(gameObject);
	}

	public static Environment getInstance() {
		return instance;
	}

	public static class Builder {
		private Ricochet ricochet;
		private final Gravity gravity;
		private final Background background;
		private boolean infinite;

		public Builder(Background background, Gravity gravity) {
			this.background = background;
			this.gravity = gravity;
		}

		public Builder infinite(boolean infinite) {
			this.infinite = infinite;
			return this;
		}

		public Builder ricochet(Ricochet ricochet) {
			if (ricochet == null) {
				ricochet = new Ricochet();
			}
			this.ricochet = ricochet;
			return this;
		}

		public Environment build() {
			return new Environment(this);
		}
	}

}
