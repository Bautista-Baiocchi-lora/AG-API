package org.bautista.ag.test;

import org.bautista.ag.api.locatable.Position;
import org.bautista.ag.api.objects.SceneObject;

import javafx.event.Event;
import javafx.scene.image.Image;

public class Square extends SceneObject {

	public Square(final Image image, final Position position) {
		super(image, position);
	}

	@Override
	public void handle(final Event event) {
		System.out.println("Square");
	}

}
