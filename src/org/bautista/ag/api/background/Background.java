package org.bautista.ag.api.background;

import java.util.List;

import org.bautista.ag.api.locatable.Dimension;
import org.bautista.ag.api.objects.GameObject;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;

public class Background extends Scene {

	private final Dimension dimension;
	private final Group root;

	public Background(Group root, Dimension dimension) {
		super(root, dimension.getWidth(), dimension.getHeight());
		this.root = root;
		this.dimension = dimension;
	}

	public Background(Group root, Dimension dimension, Paint color) {
		super(root, dimension.getWidth(), dimension.getHeight(), color);
		this.root = root;
		this.dimension = dimension;
	}

	private void clearGraphics() {
		root.getChildren().clear();
	}

	public void renderGraphics(List<GameObject> gameObjects) {
		clearGraphics();
		for (GameObject gameObject : gameObjects) {
			root.getChildren().add(gameObject);
		}
	}

	public Dimension getDimension() {
		return dimension;
	}

}
