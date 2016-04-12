package org.bautista.ag.api.locatable.collisions;

public enum CollisionFlag {

	TOP(0), BOTTOM(1), LEFT(2), RIGHT(3);

	private int index;

	CollisionFlag(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
