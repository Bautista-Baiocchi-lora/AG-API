package org.bautista.ag.api.objects;

import org.bautista.ag.api.locatable.Position;

import javafx.scene.image.Image;

public class SceneObject extends GameObject {

	public SceneObject(Image image, Position position) {
		super(image, position, false);
	}

}
