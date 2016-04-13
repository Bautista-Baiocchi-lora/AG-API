package org.bautista.ag.api.background.scroll;

public enum ScrollDirection {

	EAST(-2.0, 0), WEST(2.0, 1), NORTH(2.0, 2), SOUTH(-2.0, 3), NONE(0, 4);

	private double shift;
	private int index;

	ScrollDirection(final double shift, final int index) {
		this.shift = shift;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public double getShift() {
		return shift;
	}

}
