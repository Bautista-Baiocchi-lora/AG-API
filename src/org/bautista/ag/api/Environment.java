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
				System.out.println("Elevated");
				object.setYVelocity(object.getYVelocity() + gravity.getVelocity());
			}
		}
	}

	private CollisionFlag getHorizontalCollisionFlag(GameObject gameObject) {
		for (GameObject object : gameObjects) {
			if (gameObject != object && object.getBoundary().intersects(gameObject.getBoundary())) {
				if (gameObject.getX() < object.getX()) {
					return CollisionFlag.EAST;
				} else if (gameObject.getBoundary().getMaxX() > object.getBoundary().getMaxX()) {
					return CollisionFlag.WEST;
				}
			}
		}
		return CollisionFlag.NONE;
	}

	private CollisionFlag getVerticalCollisionFlag(GameObject gameObject) {
		for (GameObject object : gameObjects) {
			if (gameObject != object && object.getBoundary().intersects(gameObject.getBoundary())) {
				if (gameObject.getBoundary().getMaxY() > object.getBoundary().getMaxY()) {
					return CollisionFlag.NORTH;
				} else if (gameObject.getY() < object.getY()) {
					return CollisionFlag.SOUTH;
				}
			}
		}
		return CollisionFlag.NONE;
	}

	private CollisionFlag getWallCollisionFlag(GameObject gameObject) {
		// hit right
		if (!background.getDimension().contains((gameObject.getBoundary().getMaxX() + gameObject.getXVelocity()),
				gameObject.getY())) {
			if (!scrollType.isValidDirection(ScrollDirection.EAST)) {
				return CollisionFlag.EAST;
			}
			// hit left
		}
		if (!background.getDimension().contains((gameObject.getX() + gameObject.getXVelocity()), gameObject.getY())) {
			if (!scrollType.isValidDirection(ScrollDirection.WEST)) {
				return CollisionFlag.WEST;
			}
			// hit bottom
		}
		if (!background.getDimension().contains(gameObject.getX(),
				gameObject.getBoundary().getMaxY() - gameObject.getYVelocity())) {
			if (!scrollType.isValidDirection(ScrollDirection.SOUTH)) {
				return CollisionFlag.SOUTH;
			}
			// hit top
		}
		if (!background.getDimension().contains(gameObject.getX(), (gameObject.getY() - gameObject.getYVelocity()))) {
			if (!GameEngine.getInstance().getEnvironment().getScrollType().isValidDirection(ScrollDirection.NORTH)) {
				return CollisionFlag.NORTH;
			}
		}
		return CollisionFlag.NONE;
	}

	public ArrayList<CollisionFlag> getCollisionFlags(GameObject gameObject) {
		ArrayList<CollisionFlag> flags = new ArrayList<CollisionFlag>();
		flags.add(getHorizontalCollisionFlag(gameObject));
		flags.add(getWallCollisionFlag(gameObject));
		flags.add(getVerticalCollisionFlag(gameObject));
		return flags;
	}

	private void moveSprites() {
		for (Sprite sprite : sprites) {
			ArrayList<CollisionFlag> collisions = getCollisionFlags(sprite);
			if (sprite.isMovable()) {
				System.out.println("start.");
				for (CollisionFlag flag : collisions) {
					System.out.println(flag);
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
				System.out.println("X Velocity: "+sprite.getXVelocity()+", Y Velocity: "+sprite.getYVelocity());
				System.out.println("stop.");
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
