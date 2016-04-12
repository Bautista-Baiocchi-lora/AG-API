package org.bautista.ag.api;

import java.util.ArrayList;

import org.bautista.ag.api.background.Background;
import org.bautista.ag.api.background.scroll.ScrollType;
import org.bautista.ag.api.objects.GameObject;
import org.bautista.ag.api.objects.SceneObject;
import org.bautista.ag.api.objects.Sprite;
import org.bautista.ag.api.physics.Gravity;
import org.bautista.ag.api.physics.Ricochet;

import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

public class Environment extends Stage {

	private final Background background;
	private final Gravity gravity;
	private final ScrollType scrollType;
	private final Ricochet ricochet;
	private final ArrayList<GameObject> gameObjects;
	private final ArrayList<Sprite> sprites;
	private final ArrayList<SceneObject> sceneObjects;
	private final ArrayList<GameObject> unrenderedGameObjects;

	private Environment(Builder builder) {
		gameObjects = new ArrayList<GameObject>();
		sprites = new ArrayList<Sprite>();
		sceneObjects = new ArrayList<SceneObject>();
		unrenderedGameObjects = new ArrayList<GameObject>();
		this.background = builder.background;
		this.gravity = builder.gravity;
		this.scrollType = builder.scrollType;
		this.ricochet = builder.ricochet;

		setScene(background);
	}

	protected void initialize() {
		show();
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (gravity.isEnabled()) {
					simulateGravity();
				}
				// scroll here
				updateObjects();
				if (!unrenderedGameObjects.isEmpty()) {
					background.renderGraphics(unrenderedGameObjects);
					unrenderedGameObjects.clear();
				}
			}

		}.start();
	}

	private void updateObjects() {
		for (GameObject object : gameObjects) {
			object.update();
		}
	}

	private void simulateGravity() {
		for (GameObject object : sprites) {
			if (object.isElevated() && object.isMovable()) {
				object.setYVelocity(object.getYVelocity() + gravity.getVelocity());
			}
		}
	}

	protected Background getBackground() {
		return background;
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

	public boolean hasScroll() {
		return scrollType != ScrollType.NONE;
	}

	public ScrollType getScrollType() {
		return scrollType;
	}

	protected void add(GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.add((Sprite) gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.add((SceneObject) gameObject);
		}
		gameObjects.add(gameObject);
		unrenderedGameObjects.add(gameObject);
	}

	protected void remove(GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.remove(gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.remove(gameObject);
		}
		gameObjects.remove(gameObject);
	}

	public static class Builder {
		private Ricochet ricochet;
		private final Gravity gravity;
		private final Background background;
		private ScrollType scrollType;

		public Builder(Background background, Gravity gravity, ScrollType scrollType) {
			this.background = background;
			this.gravity = gravity;
			this.scrollType = scrollType;
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
