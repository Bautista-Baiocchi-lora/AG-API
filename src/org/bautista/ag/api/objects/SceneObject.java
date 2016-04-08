package org.bautista.ag.api.objects;

import org.bautista.ag.api.locatable.Position;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.scene.image.Image;

public class SceneObject extends GameObject {

	public SceneObject(Image image, Position position) {
		super(image, position, false);
	}

}
