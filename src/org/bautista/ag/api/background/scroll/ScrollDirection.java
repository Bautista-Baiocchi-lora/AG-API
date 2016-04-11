package org.bautista.ag.api.background.scroll;

public enum ScrollDirection {

	RIGHT(-2.0, 0), LEFT(2.0, 1), UP(2.0, 2), DOWN(-2.0, 3), NONE(0, 4);

	private double shift;
	private int index;

	ScrollDirection(double shift, int index) {
		this.shift = shift;
		this.index = index;
	}

	public double getShift() {
		return shift;
	}

	public int getIndex() {
		return index;
	}

}
