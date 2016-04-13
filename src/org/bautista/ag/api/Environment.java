package org.bautista.ag.api;

import java.util.ArrayList;

import org.bautista.ag.api.background.Background;
import org.bautista.ag.api.background.scroll.ScrollDirection;
import org.bautista.ag.api.background.scroll.ScrollType;
import org.bautista.ag.api.locatable.CollisionFlag;
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

	private Environment(final Builder builder) {
		gameObjects = new ArrayList<GameObject>();
		sprites = new ArrayList<Sprite>();
		sceneObjects = new ArrayList<SceneObject>();
		unrenderedGameObjects = new ArrayList<GameObject>();
		background = builder.background;
		gravity = builder.gravity;
		scrollType = builder.scrollType;
		ricochet = builder.ricochet;

		setScene(background);
	}

	protected void add(final GameObject gameObject) {
		if (gameObject instanceof Sprite) {
			sprites.add((Sprite) gameObject);
		} else if (gameObject instanceof SceneObject) {
			sceneObjects.add((SceneObject) gameObject);
		}
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
				moveObjects();
				// scroll here
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
		gameObjects.remove(gameObject);
	}

	private void applyGravity() {
		for (final GameObject object : sprites) {
			if (object.isElevated() && object.isMovable()) {
				object.setYVelocity(object.getYVelocity() + gravity.getVelocity());
			}
		}
	}

	private void moveObjects() {
		for (GameObject object : gameObjects) {
			CollisionFlag side = object.getCollisionFlag(gameObjects);
			if (side != CollisionFlag.NONE && object.isMovable()) {
				if (object.isMovable()) {
					System.out.println(side.toString());
					System.out.println("V-X: " + object.getXVelocity() + ", V-Y: " + object.getYVelocity());
				}
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
			// hit right
			if (!background.getDimension().contains((object.getBoundary().getMaxX() + object.getXVelocity()),
					object.getY())) {
				if (!scrollType.isValidDirection(ScrollDirection.RIGHT)) {
					object.setXVelocity(ricochet.applyRicochet(-1 * object.getXVelocity()));
				} else {
					// request scroll
				}
				// hit left
			} else if (!background.getDimension().contains((object.getX() + object.getXVelocity()), object.getY())) {
				if (!scrollType.isValidDirection(ScrollDirection.LEFT)) {
					object.setXVelocity(ricochet.applyRicochet(-1 * object.getXVelocity()));
				} else {
					// request scroll
				}
				// hit bottom
			} else if (!background.getDimension().contains(object.getX(),
					object.getBoundary().getMaxY() - object.getYVelocity())) {
				if (!scrollType.isValidDirection(ScrollDirection.DOWN)) {
					object.setYVelocity(ricochet.applyRicochet(-1 * object.getYVelocity()));
				} else {
					// request scroll
				}
				// hit top
			} else if (!background.getDimension().contains(object.getX(), (object.getY() - object.getYVelocity()))) {
				if (!GameEngine.getInstance().getEnvironment().getScrollType().isValidDirection(ScrollDirection.UP)) {
					object.setYVelocity(ricochet.applyRicochet(-1 * object.getYVelocity()));
				} else {
					// request scroll
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
