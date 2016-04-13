package org.bautista.ag.api.background.scroll;

public enum ScrollType {

	RIGHT(ScrollDirection.RIGHT), LEFT(ScrollDirection.LEFT), UP(ScrollDirection.UP), DOWN(
			ScrollDirection.DOWN), HORIZONTAL(ScrollDirection.RIGHT, ScrollDirection.LEFT), VERTICAL(ScrollDirection.UP,
					ScrollDirection.DOWN), ANY(ScrollDirection.RIGHT, ScrollDirection.LEFT, ScrollDirection.UP,
							ScrollDirection.DOWN), NONE(ScrollDirection.NONE);

	private ScrollDirection[] directions;

	ScrollType(final ScrollDirection... directions) {
		this.directions = directions;
	}

	public ScrollDirection[] getDirections() {
		return directions;
	}

	public boolean isValidDirection(final ScrollDirection direction) {
		for (final ScrollDirection d : directions) {
			if (d == direction) {
				return true;
			}
		}
		return false;
	}

}
