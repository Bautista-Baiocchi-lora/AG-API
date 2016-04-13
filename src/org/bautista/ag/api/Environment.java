package org.bautista.ag.api;

import java.util.ArrayList;

import org.bautista.ag.api.background.Background;
import org.bautista.ag.api.background.scroll.ScrollDirection;
import org.bautista.ag.api.background.scroll.ScrollType;
import org.bautista.ag.api.locatable.Boundary;
import org.bautista.ag.api.locatable.CollisionFlag;
import org.bautista.ag.api.objects.GameObject;
import org.bautista.ag.api.objects.SceneObject;
import org.bautista.ag.api.objects.Sprite;
import org.bautista.ag.api.physics.Gravity;
import org.bautista.ag.api.physics.Ricochet;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;

public class Environment extends Stage {

	private final Background background;
	private final Gravity gravity;
	private final ScrollType scrollType;
	private final Ricochet ricochet;
	private final ArrayList<GameObject> gameObjects;
	private final ArrayList<Sprite> sprites;
	private final ArrayList<SceneObject> sceneObjects;
	private final ArrayList<Boundary> collisionBoundaries;
	private final ArrayList<GameObject> unrenderedGameObjects;

	private Environment(final Builder builder) {
		gameObjects = new ArrayList<GameObject>();
		sprites = new ArrayList<Sprite>();
		sceneObjects = new ArrayList<SceneObject>();
		collisionBoundaries = new ArrayList<Boundary>();
		unrenderedGameObjects = new ArrayList<GameObject>();
		background = builder.background;
		gravity = builder.gravity;
		scrollType = builder.scrollType;
		ricochet = builder.ricochet;
		collisionBoundaries.add(
				new Boundary(background.getX(), background.getY(), background.getWidth(), background.getHeight()));

		setScene(background);
	}

	protected void add(final GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.add((Sprite) gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.add((SceneObject) gameObject);
		}
		collisionBoundaries.add(gameObject.getBoundary());
		gameObjects.add(gameObject);
		unrenderedGameObjects.add(gameObject);
	}

	protected Background getBackground() {
		return background;
	}

	public Gravity getGravity() {
		return gravity;
	}

	public Ricochet getRicochet() {
		return ricochet;
	}

	public ScrollType getScrollType() {
		return scrollType;
	}

	public boolean hasGravity() {
		return gravity.isEnabled();
	}

	public boolean hasRicochet() {
		return ricochet.isEnabled();
	}

	public boolean hasScroll() {
		return scrollType != ScrollType.NONE;
	}

	protected void initialize() {
		show();
		new AnimationTimer() {

			@Override
			public void handle(final long now) {
				if (gravity.isEnabled()) {
					applyGravity();
				}
				moveSprites();
				updateObjects();
				if (!unrenderedGameObjects.isEmpty()) {
					background.renderGraphics(unrenderedGameObjects);
					unrenderedGameObjects.clear();
				}
			}

		}.start();
	}

	protected void remove(final GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.remove(gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.remove(gameObject);
		}
		collisionBoundaries.remove(gameObject.getBoundary());
		gameObjects.remove(gameObject);
	}

	private void applyGravity() {
		for (final GameObject object : sprites) {
			if (object.isElevated() && object.isMovable()) {
				object.setYVelocity(object.getYVelocity() + gravity.getVelocity());
			}
		}
	}

	private void moveSprites() {
		for (GameObject object : sprites) {
			// gets the direction of the collision on the object
			CollisionFlag side = object.getCollisionFlag(collisionBoundaries);
			if (side != CollisionFlag.NONE && object.isMovable()) {
				switch (side) {
				case EAST:
					object.setXVelocity(ricochet.applyRicochet(-1 * object.getXVelocity()));
					break;
				case WEST:
					object.setXVelocity(ricochet.applyRicochet(-1 * object.getXVelocity()));
					break;
				case NORTH:
					object.setYVelocity(ricochet.applyRicochet(-1 * object.getYVelocity()));
					break;
				case SOUTH:
					object.setYVelocity(ricochet.applyRicochet(-1 * object.getYVelocity()));
					break;
				}
			}
		}
	}

	private void updateObjects() {
		for (final GameObject object : gameObjects) {
			object.update();
		}
	}

	public static class Builder {
		private Ricochet ricochet;
		private final Gravity gravity;
		private final Background background;
		private final ScrollType scrollType;

		public Builder(final Background background, final Gravity gravity, final ScrollType scrollType) {
			this.background = background;
			this.gravity = gravity;
			this.scrollType = scrollType;
		}

		public Environment build() {
			return new Environment(this);
		}

		public Builder ricochet(Ricochet ricochet) {
			if (ricochet == null) {
				ricochet = new Ricochet();
			}
			this.ricochet = ricochet;
			return this;
		}
	}

}
