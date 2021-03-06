package org.bautista.ag.api.objects;

import org.bautista.ag.api.locatable.Position;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

public abstract class Sprite extends GameObject implements EventHandler<Event> {

	public Sprite(final Image image, final Position position, final boolean focused) {
		super(image, position, true);
		super.addEventHandler(Event.ANY, this);
		super.setFocusTraversable(focused);
	}

}
