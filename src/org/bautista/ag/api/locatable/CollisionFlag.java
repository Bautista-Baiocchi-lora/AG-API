package org.bautista.ag.api.locatable;

public enum CollisionFlag {

	NORTH(0), SOUTH(1), EAST(2), WEST(3), NONE(4);

	private int index;

	CollisionFlag(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}
