package org.bautista.ag.test;

import org.bautista.ag.api.locatable.Position;
import org.bautista.ag.api.objects.Sprite;

import javafx.event.Event;
import javafx.scene.image.Image;

public class Ball extends Sprite {

	public Ball(Image image, Position position) {
		super(image, position, true);
	}

	@Override
	public void handle(Event event) {
		System.out.println(event.getEventType().getName());
	}

}
