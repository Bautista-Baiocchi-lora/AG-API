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
		gameObjects.remove(gameObject);
	}

	private void applyGravity() {
		for (final GameObject object : sprites) {
			if (object.isElevated() && object.isMovable()) {
				object.setYVelocity(object.getYVelocity() + gravity.getVelocity());
			}
		}
	}

	private CollisionFlag getHorizontalCollisionFlag(Sprite sprite) {
		for (GameObject object : gameObjects) {
			if (sprite != object && object.getBoundary().intersects(sprite.getBoundary())) {
				if (this.getX() < object.getX()) {
					return CollisionFlag.EAST;
				} else if (sprite.getBoundary().getMaxX() > object.getBoundary().getMaxX()) {
					return CollisionFlag.WEST;
				}
			}
		}
		return CollisionFlag.NONE;
	}

	private CollisionFlag getVerticalCollisionFlag(Sprite sprite) {
		for (GameObject object : gameObjects) {
			if (sprite != object && object.getBoundary().intersects(sprite.getBoundary())) {
				if (sprite.getBoundary().getMaxY() > object.getBoundary().getMaxY()) {
					return CollisionFlag.NORTH;
				} else if (sprite.getY() < object.getY()) {
					return CollisionFlag.SOUTH;
				}
			}
		}
		return CollisionFlag.NONE;
	}

	private CollisionFlag getWallCollisionFlag(Sprite sprite) {
		// hit right
		if (!background.getDimension().contains((sprite.getBoundary().getMaxX() + sprite.getXVelocity()),
				sprite.getY())) {
			if (!scrollType.isValidDirection(ScrollDirection.EAST)) {
				return CollisionFlag.EAST;
			}
			// hit left
		}
		if (!background.getDimension().contains((sprite.getX() + sprite.getXVelocity()), sprite.getY())) {
			if (!scrollType.isValidDirection(ScrollDirection.WEST)) {
				return CollisionFlag.WEST;
			}
			// hit bottom
		}
		if (!background.getDimension().contains(sprite.getX(),
				sprite.getBoundary().getMaxY() - sprite.getYVelocity())) {
			if (!scrollType.isValidDirection(ScrollDirection.SOUTH)) {
				return CollisionFlag.SOUTH;
			}
			// hit top
		}
		if (!background.getDimension().contains(sprite.getX(), (sprite.getY() - sprite.getYVelocity()))) {
			if (!GameEngine.getInstance().getEnvironment().getScrollType().isValidDirection(ScrollDirection.NORTH)) {
				return CollisionFlag.NORTH;
			}
		}
		return CollisionFlag.NONE;
	}

	private ArrayList<CollisionFlag> getCollisionFlags(Sprite sprite) {
		ArrayList<CollisionFlag> flags = new ArrayList<CollisionFlag>();
		flags.add(getVerticalCollisionFlag(sprite));
		flags.add(getHorizontalCollisionFlag(sprite));
		flags.add(getWallCollisionFlag(sprite));
		return flags;
	}

	private void moveSprites() {
		for (Sprite sprite : sprites) {
			ArrayList<CollisionFlag> collisions = getCollisionFlags(sprite);
			if (sprite.isMovable()) {
				for (CollisionFlag flag : collisions) {
					switch (flag) {
					case NONE:
						break;
					case EAST:
					case WEST:
						sprite.setXVelocity(ricochet.applyRicochet(-1 * sprite.getXVelocity()));
						break;
					case NORTH:
					case SOUTH:
						sprite.setYVelocity(ricochet.applyRicochet(-1 * sprite.getYVelocity()));
						break;
					}
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
