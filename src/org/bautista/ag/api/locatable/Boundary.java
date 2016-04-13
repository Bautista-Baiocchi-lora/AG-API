package org.bautista.ag.api.locatable;

import javafx.geometry.Rectangle2D;

public class Boundary extends Rectangle2D{

	public Boundary(double minX, double minY, double width, double height) {
		super(minX, minY, width, height);

	}
	
	public boolean intersectsBorder(Boundary boundary){
		if(this.intersects(boundary)){
			
		}
		return false;
	}

}
