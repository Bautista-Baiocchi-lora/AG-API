package org.bautista.ag;

import org.bautista.ag.api.locatable.Position;
import org.bautista.ag.api.objects.Sprite;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.event.Event;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

public class LLama extends Sprite {

	public LLama(Image image, Position position) {
		super(image, position);
	}

	@Override
	public void handle(Event event) {
		System.out.println(event.getEventType().getName());
	}

}
