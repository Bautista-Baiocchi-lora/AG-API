package org.bautista.ag.api.objects;

import org.bautista.ag.api.locatable.Position;

import javafx.scene.image.Image;

public class Sprite extends GameObject {

	private Sprite(Image image, Position position) {
		super(image, position, true);
	}

}
